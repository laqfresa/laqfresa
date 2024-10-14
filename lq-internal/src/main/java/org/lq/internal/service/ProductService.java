package org.lq.internal.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.lq.internal.domain.combo.Combo;
import org.lq.internal.domain.combo.Status;
import org.lq.internal.domain.detailProduct.DetailProduct;
import org.lq.internal.domain.product.Product;
import org.lq.internal.domain.product.ProductDTO;
import org.lq.internal.domain.size.Size;
import org.lq.internal.helper.exception.PVException;
import org.lq.internal.repository.DetailProductRepository;
import org.lq.internal.repository.ProductRepository;
import org.lq.internal.repository.SizeRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class ProductService {

    private final Logger LOG = Logger.getLogger(ProductService.class);

    @Inject
    ProductRepository productRepository;

    @Inject
    DetailProductRepository detailProductRepository;

    @Inject
    SizeRepository sizeRepository;

    public List<Product> getProducts() throws PVException {
        LOG.infof("@getProducts SERV > Start service to obtain the products");

        List<Product> products = productRepository.listAll();
        LOG.infof("@getProducts SERV > Retrieved list of products");

        if (products.isEmpty()) {
            LOG.warnf("@getProducts SERV > No products found");
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron productos");
        }

        for (Product product : products) {
            LOG.infof("@getProducts SERV > Fetching detail products for product ID %d", product.getIdProduct());
            List<DetailProduct> detailProducts = detailProductRepository.list("idProduct", product.getIdProduct());

            Size size = sizeRepository.findById((long) product.getSize());

            LOG.infof("@getProducts SERV > Found %d detail products for product ID %d", detailProducts.size(), product.getIdProduct());
            product.setDetailProduct(detailProducts);
            product.setSizeDetail(size);
        }

        LOG.infof("@getProducts SERV > Finish service to obtain the products");
        return products;
    }

    public Product getProductNumber(long numberProduct) throws PVException {
        LOG.infof("@getProductNumber SERV > Start service to obtain the product with number %d", numberProduct);

        LOG.infof("@getProductNumber SERV > Searching for product with number %d", numberProduct);
        Product product = productRepository.findById(numberProduct);

        if (product == null) {
            LOG.warnf("@getProductNumber SERV > No product found with number %d", numberProduct);
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontró ningún producto con el número ingresado");
        }

        LOG.infof("@getProductNumber SERV > Fetching detail products for product ID %d", numberProduct);
        List<DetailProduct> detailProducts = detailProductRepository.list("idProduct", product.getIdProduct());

        LOG.infof("@getProductNumber SERV > Found %d detail products for product ID %d", detailProducts.size(), numberProduct);
        product.setDetailProduct(detailProducts);

        LOG.infof("@getProductNumber SERV > Product with number %d obtained successfully", numberProduct);
        LOG.infof("@getProductNumber SERV > Finish service to obtain the product with number %d", numberProduct);
        return product;
    }

    public void saveProduct(ProductDTO productDTO) throws PVException {
        LOG.infof("@saveProduct SERV > Start service to save a new product");

        LOG.infof("@saveProduct SERV > Creating product entity from DTO");
        Product product = Product.builder()
                .name(productDTO.getName())
                .size(productDTO.getSize())
                .description(productDTO.getDescription())
                .value(productDTO.getValue())
                .quantityPremium(productDTO.getQuantityPremium())
                .quantitySalsa(productDTO.getQuantitySalsa())
                .quantityClasic(productDTO.getQuantityClasic())
                .status(String.valueOf(Status.ACTIVO))
                .build();

        LOG.infof("@saveProduct SERV > Persisting product with name %s", productDTO.getName());
        productRepository.persist(product);

        for (DetailProduct detailProduct : productDTO.getDetailProduct()) {
            LOG.infof("@saveProduct SERV > Creating detail product entity from DTO for product ID %d", product.getIdProduct());
            DetailProduct detailProductSave = DetailProduct.builder()
                    .idProduct(product.getIdProduct())
                    .idIngredient(detailProduct.getIdIngredient())
                    .quantity(detailProduct.getQuantity())
                    .build();

            LOG.infof("@saveProduct SERV > Persisting detail product for product ID %d and ingredient ID %d", product.getIdProduct(), detailProduct.getIdIngredient());
            detailProductRepository.persist(detailProductSave);
        }

        LOG.infof("@saveProduct SERV > Product saved successfully with name %s", productDTO.getName());
    }

    public void updateProduct(ProductDTO productDTO) throws PVException {
        LOG.infof("@updateProduct SERV > Start service to update product with number %d", productDTO.getIdProduct());

        LOG.infof("@updateProduct SERV > Searching for existing product with number %d", productDTO.getIdProduct());
        Product existingProduct = productRepository.findById((long) productDTO.getIdProduct());
        if (existingProduct == null) {
            LOG.warnf("@updateProduct SERV > No product found with number %d", productDTO.getIdProduct());
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "El producto no fue encontrado.");
        }

        LOG.infof("@updateProduct SERV > Updating product entity with number %d", productDTO.getIdProduct());
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setValue(productDTO.getValue() != null ? productDTO.getValue() : 0L);
        existingProduct.setSize(productDTO.getSize());
        existingProduct.setQuantityPremium(productDTO.getQuantityPremium());
        existingProduct.setQuantityClasic(productDTO.getQuantityClasic());
        existingProduct.setQuantitySalsa(productDTO.getQuantitySalsa());
        existingProduct.setStatus(productDTO.getStatus());

        LOG.infof("@updateProduct SERV > Persisting updated product with number %d", productDTO.getIdProduct());
        productRepository.persist(existingProduct);

        LOG.infof("@updateProduct SERV > Fetching existing detail products for product ID %d", productDTO.getIdProduct());
        List<DetailProduct> existingDetailProducts = detailProductRepository.list("idProduct", existingProduct.getIdProduct());

        Set<Integer> newIngredientIds = productDTO.getDetailProduct().stream()
                .map(DetailProduct::getIdIngredient)
                .collect(Collectors.toSet());

        for (DetailProduct existingDetailProduct : existingDetailProducts) {
            if (!newIngredientIds.contains(existingDetailProduct.getIdIngredient())) {
                LOG.infof("@updateProduct SERV > Removing DetailProduct with ID %d for product with ID %d", existingDetailProduct.getIdDetailProduct(), productDTO.getIdProduct());
                detailProductRepository.delete(existingDetailProduct);
            }
        }

        for (DetailProduct detailProductDTO : productDTO.getDetailProduct()) {
            DetailProduct existingDetailProduct = detailProductRepository.find("idProduct = ?1 AND idIngredient = ?2", existingProduct.getIdProduct(), detailProductDTO.getIdIngredient()).firstResult();

            if (existingDetailProduct == null) {
                LOG.infof("@updateProduct SERV > Creating new DetailProduct for ingredient ID %d", detailProductDTO.getIdIngredient());
                DetailProduct newDetailProduct = DetailProduct.builder()
                        .idProduct(existingProduct.getIdProduct())
                        .idIngredient(detailProductDTO.getIdIngredient())
                        .quantity(detailProductDTO.getQuantity())
                        .build();
                detailProductRepository.persist(newDetailProduct);
            } else {
                LOG.infof("@updateProduct SERV > DetailProduct already exists for product with ID %d, updating it", productDTO.getIdProduct());
                existingDetailProduct.setQuantity(detailProductDTO.getQuantity());
                detailProductRepository.persist(existingDetailProduct);
            }
        }

        LOG.infof("@updateProduct SERV > Product with number %d updated successfully", productDTO.getIdProduct());
    }

    public void deactivateProduct(long numberProduct) throws PVException {
        LOG.infof("@deactivateProduct SERV > Start service to deactivate product with ID %d", numberProduct);

        LOG.infof("@deactivateProduct SERV > Retrieving existing product with ID %d", numberProduct);
        Product product = productRepository.findById(numberProduct);
        if (product == null) {
            LOG.warnf("@deactivateProduct SERV > No product found with ID %d", numberProduct);
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "El producto no fue encontrado.");
        }

        if (product.getStatus().equals(Status.ACTIVO.toString())){
            product.setStatus(String.valueOf(Status.INACTIVO));
        } else {
            product.setStatus(String.valueOf(Status.ACTIVO));
            LOG.infof("@deactivateProduct SERV > Deactivating product with ID %d", numberProduct);
        }

        LOG.infof("@deactivateProduct SERV > Persisting product with ID %d", numberProduct);
        productRepository.persist(product);

        LOG.infof("@deactivateProduct SERV > Product with ID %d change status successfully", numberProduct);
    }
}
