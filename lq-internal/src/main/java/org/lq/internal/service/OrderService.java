package org.lq.internal.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.lq.internal.domain.detailOrder.DetailOrder;
import org.lq.internal.domain.detailProduct.DetailProduct;
import org.lq.internal.domain.ingredient.DetailAdditional;
import org.lq.internal.domain.ingredient.IngredientData;
import org.lq.internal.domain.order.Order;
import org.lq.internal.domain.order.OrderDTO;
import org.lq.internal.domain.order.OrderStatus;
import org.lq.internal.domain.product.Product;
import org.lq.internal.helper.exception.PVException;
import org.lq.internal.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class OrderService {

    private final Logger LOG = Logger.getLogger(ProductService.class);

    @Inject
    OrderRepository orderRepository;

    @Inject
    DetailOrderRepository detailOrderRepository;

    @Inject
    DetailAdditionalRepository detailAdditionalRepository;

    @Inject
    ProductRepository productRepository;

    @Inject
    DetailProductRepository detailProductRepository;

    @Inject
    IngredientDataRepository ingredientDataRepository;

    public List<Order> getOrders() {
        List<Order> orderList = orderRepository.listAll();

        if (orderList.isEmpty()) {
            LOG.warnf("@getOrdersPending SERV > No orders found");
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron pedidos pendientes");
        }

        for (Order order : orderList) {
            LOG.infof("@getOrdersPending SERV > Fetching detail orders for order ID %d", order.getIdOrder());

            List<DetailOrder> detailOrders = detailOrderRepository.findByIdOrderWithProduct(order.getIdOrder());

            for (DetailOrder detailOrder : detailOrders) {
                Product product = detailOrder.getProduct();
                if (product != null) {
                    LOG.infof("@getProducts SERV > Fetching detail products for product ID %d", product.getIdProduct());
                    List<DetailProduct> detailProducts = detailProductRepository.list("idProduct", product.getIdProduct());

                    LOG.infof("@getProducts SERV > Found %d detail products for product ID %d", detailProducts.size(), product.getIdProduct());
                    product.setDetailProduct(detailProducts);
                }

                LOG.infof("@getOrdersPending SERV > Fetched product details for detail order ID %d", detailOrder.getIdDetailOrder());

                List<DetailAdditional> detailAdditionals = detailAdditionalRepository.find("idDetailOrder", detailOrder.getIdDetailOrder()).list();
                detailOrder.setDetailAdditionals(detailAdditionals);

                LOG.infof("@getOrdersPending SERV > Fetched %d detail additionals for detail order ID %d", detailAdditionals.size(), detailOrder.getIdDetailOrder());
            }

            order.setDetailOrders(detailOrders);
            LOG.infof("@getOrdersPending SERV > Found %d detail orders for order ID %d", detailOrders.size(), order.getIdOrder());
        }

        LOG.infof("@getOrdersPending SERV > Finish service to obtain the orders");
        return orderList;
    }

    public int createOrder(OrderDTO orderDTO) {
        LOG.infof("@createOrder SERV > Start service to create the order with data: %s", orderDTO);

        Order order = Order.builder()
                .creationDate(orderDTO.getCreationDate())
                .total(orderDTO.getTotal())
                .idUser(orderDTO.getIdUser())
                .status(OrderStatus.PENDIENTE)
                .discount(orderDTO.getDiscount())
                .build();

        LOG.infof("@createOrder SERV > Persisting order: %s", order);
        orderRepository.persist(order);

        List<DetailOrder> persistedDetailOrders = new ArrayList<>();
        for (DetailOrder detailOrderDTO : orderDTO.getDetailOrders()) {

            Product existingProduct = productRepository.findById((long) detailOrderDTO.getProduct().getIdProduct());

            DetailOrder detailOrder = DetailOrder.builder()
                    .idOrder(order.getIdOrder())
                    .value(detailOrderDTO.getValue())
                    .nameCustomer(detailOrderDTO.getNameCustomer())
                    .quantity(detailOrderDTO.getQuantity())
                    .product(existingProduct)
                    .observation(detailOrderDTO.getObservation())
                    .build();

            LOG.infof("@createOrder SERV > Persisting detail order: %s", detailOrder);
            detailOrderRepository.persist(detailOrder);
            persistedDetailOrders.add(detailOrder);

            if (detailOrderDTO.getDetailAdditionals() != null) {
                for (DetailAdditional detailAdditionalDTO : detailOrderDTO.getDetailAdditionals()) {
                    DetailAdditional detailAdditional = DetailAdditional.builder()
                            .idDetailOrder(detailOrder.getIdDetailOrder())
                            .ingredientId(detailAdditionalDTO.getIngredientId())
                            .build();

                    LOG.infof("@createOrder SERV > Persisting detail additional: %s", detailAdditional);
                    detailAdditionalRepository.persist(detailAdditional);
                }
            }

        }

        LOG.infof("@createOrder SERV > End service to create the order");
        return order.getIdOrder();
    }


    public void updateOrderStatus(long orderId) throws PVException {
        LOG.infof("@updateOrderStatus SERV > Start service to update the status of order ID %d to %s", orderId, OrderStatus.COMPLETADO.toString());

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            LOG.warnf("@updateOrderStatus SERV > Order ID %d not found", orderId);
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "Pedido no encontrado");
        }

        order.setStatus(OrderStatus.COMPLETADO);
        orderRepository.persist(order);

        LOG.infof("@updateOrderStatus SERV > Updated status of order ID %d to %s", orderId, OrderStatus.COMPLETADO.toString());
    }

    public void cancelOrderStatus(long orderId) throws PVException {
        LOG.infof("@updateOrderStatus SERV > Start service to update the status of order ID %d to %s", orderId, OrderStatus.COMPLETADO.toString());

        Order order = orderRepository.findById(orderId);
        if (order == null) {
            LOG.warnf("@updateOrderStatus SERV > Order ID %d not found", orderId);
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "Pedido no encontrado");
        }

        order.setStatus(OrderStatus.CANCELADO);
        orderRepository.persist(order);

        LOG.infof("@updateOrderStatus SERV > Updated status of order ID %d to %s", orderId, OrderStatus.COMPLETADO.toString());
    }

    public List<Order> ordersPending() {
        List<Order> orderList = orderRepository.findOrdersPending();

        if (orderList.isEmpty()) {
            LOG.warnf("@getOrdersPending SERV > No orders found");
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron pedidos pendientes");
        }

        for (Order order : orderList) {
            LOG.infof("@getOrdersPending SERV > Fetching detail orders for order ID %d", order.getIdOrder());

            List<DetailOrder> detailOrders = detailOrderRepository.findByIdOrderWithProduct(order.getIdOrder());

            for (DetailOrder detailOrder : detailOrders) {
                Product product = detailOrder.getProduct();
                if (product != null) {
                    LOG.infof("@getProducts SERV > Fetching detail products for product ID %d", product.getIdProduct());
                    List<DetailProduct> detailProducts = detailProductRepository.list("idProduct", product.getIdProduct());

                    LOG.infof("@getProducts SERV > Found %d detail products for product ID %d", detailProducts.size(), product.getIdProduct());
                    product.setDetailProduct(detailProducts);
                }

                LOG.infof("@getOrdersPending SERV > Fetched product details for detail order ID %d", detailOrder.getIdDetailOrder());

                List<DetailAdditional> detailAdditionals = detailAdditionalRepository.find("idDetailOrder", detailOrder.getIdDetailOrder()).list();
                detailOrder.setDetailAdditionals(detailAdditionals);

                LOG.infof("@getOrdersPending SERV > Fetched %d detail additionals for detail order ID %d", detailAdditionals.size(), detailOrder.getIdDetailOrder());
            }

            order.setDetailOrders(detailOrders);
            LOG.infof("@getOrdersPending SERV > Found %d detail orders for order ID %d", detailOrders.size(), order.getIdOrder());
        }

        LOG.infof("@getOrdersPending SERV > Finish service to obtain the orders");
        return orderList;
    }

    public List<Order> ordersCompleted() {
        List<Order> orderList = orderRepository.findOrdersCompleted();

        if (orderList.isEmpty()) {
            LOG.warnf("@ordersCompleted SERV > No orders found");
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron pedidos completados");
        }

        for (Order order : orderList) {
            LOG.infof("@ordersCompleted SERV > Fetching detail orders for order ID %d", order.getIdOrder());

            List<DetailOrder> detailOrders = detailOrderRepository.findByIdOrderWithProduct(order.getIdOrder());

            for (DetailOrder detailOrder : detailOrders) {
                Product product = detailOrder.getProduct();
                if (product != null) {
                    List<DetailProduct> detailProducts = detailProductRepository.list("idProduct", product.getIdProduct());
                    product.setDetailProduct(detailProducts);
                }

                List<DetailAdditional> detailAdditionals = detailAdditionalRepository.find("idDetailOrder", detailOrder.getIdDetailOrder()).list();
                for (DetailAdditional detailAdditional : detailAdditionals) {
                    IngredientData ingredient = ingredientDataRepository.findById((long) detailAdditional.getIngredientId());
                    detailAdditional.setIngredient(ingredient);
                }
                detailOrder.setDetailAdditionals(detailAdditionals);
            }

            order.setDetailOrders(detailOrders);
            LOG.infof("@ordersCompleted SERV > Found %d detail orders for order ID %d", detailOrders.size(), order.getIdOrder());
        }

        LOG.infof("@ordersCompleted SERV > Finish service to obtain the orders");
        return orderList;
    }

    public Order ordersPendingNumber(long orderId) {

        Optional<Order> orderOptional = orderRepository.findOrdersPendingNumber(orderId);

        if (orderOptional.isEmpty()) {
            LOG.warnf("@updateOrderStatus SERV > Order ID %d not found", orderId);
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "Pedido pendiente no encontrado");
        }

        Order order = orderOptional.get();

        if (order.getStatus().equals(OrderStatus.PENDIENTE)){
            order.setStatus(OrderStatus.PROCESO);
            orderRepository.persist(order);
        } else if (order.getStatus().equals(OrderStatus.PROCESO)){
            LOG.warnf("@updateOrderStatus SERV > The order is already in process. %s", orderId);
            throw new PVException(Response.Status.BAD_REQUEST.getStatusCode(), "El pedido ya se encuentra en proceso.");
        }

        List<DetailOrder> detailOrders = detailOrderRepository.findByIdOrderWithProduct(order.getIdOrder());

        for (DetailOrder detailOrder : detailOrders) {
            Product product = detailOrder.getProduct();
            if (product != null) {
                LOG.infof("@getProducts SERV > Fetching detail products for product ID %d", product.getIdProduct());
                List<DetailProduct> detailProducts = detailProductRepository.list("idProduct", product.getIdProduct());

                for (DetailProduct detailProduct : detailProducts) {
                    IngredientData ingredient = ingredientDataRepository.findById((long) detailProduct.getIdIngredient());
                    detailProduct.setIngredient(ingredient);
                }

                LOG.infof("@getProducts SERV > Found %d detail products for product ID %d", detailProducts.size(), product.getIdProduct());
                product.setDetailProduct(detailProducts);
            }

            LOG.infof("@getOrdersPending SERV > Fetched product details for detail order ID %d", detailOrder.getIdDetailOrder());

            List<DetailAdditional> detailAdditionals = detailAdditionalRepository.find("idDetailOrder", detailOrder.getIdDetailOrder()).list();
            for (DetailAdditional detailAdditional : detailAdditionals) {
                IngredientData ingredient = ingredientDataRepository.findById((long) detailAdditional.getIngredientId());
                detailAdditional.setIngredient(ingredient);
            }
            detailOrder.setDetailAdditionals(detailAdditionals);

            LOG.infof("@getOrdersPending SERV > Fetched %d detail additionals for detail order ID %d", detailAdditionals.size(), detailOrder.getIdDetailOrder());
        }

        order.setDetailOrders(detailOrders);
        LOG.infof("@getOrdersPending SERV > Found %d detail orders for order ID %d", detailOrders.size(), order.getIdOrder());

        LOG.infof("@getOrdersPending SERV > Finish service to obtain the orders");
        return order;
    }

    public Order ordersProgressNumber(long orderId) {

        Optional<Order> orderOptional = orderRepository.findOrdersProcesoNumber(orderId);

        if (orderOptional.isEmpty()) {
            LOG.warnf("@updateOrderStatus SERV > Order ID %d not found", orderId);
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "Pedido en proceso no encontrado");
        }

        Order order = orderOptional.get();

        List<DetailOrder> detailOrders = detailOrderRepository.findByIdOrderWithProduct(order.getIdOrder());

        for (DetailOrder detailOrder : detailOrders) {
            Product product = detailOrder.getProduct();
            if (product != null) {
                LOG.infof("@getProducts SERV > Fetching detail products for product ID %d", product.getIdProduct());
                List<DetailProduct> detailProducts = detailProductRepository.list("idProduct", product.getIdProduct());

                for (DetailProduct detailProduct : detailProducts) {
                    IngredientData ingredient = ingredientDataRepository.findById((long) detailProduct.getIdIngredient());
                    detailProduct.setIngredient(ingredient);
                }

                LOG.infof("@getProducts SERV > Found %d detail products for product ID %d", detailProducts.size(), product.getIdProduct());
                product.setDetailProduct(detailProducts);
            }

            LOG.infof("@getOrdersPending SERV > Fetched product details for detail order ID %d", detailOrder.getIdDetailOrder());

            List<DetailAdditional> detailAdditionals = detailAdditionalRepository.find("idDetailOrder", detailOrder.getIdDetailOrder()).list();
            for (DetailAdditional detailAdditional : detailAdditionals) {
                IngredientData ingredient = ingredientDataRepository.findById((long) detailAdditional.getIngredientId());
                detailAdditional.setIngredient(ingredient);
            }
            detailOrder.setDetailAdditionals(detailAdditionals);

            LOG.infof("@getOrdersPending SERV > Fetched %d detail additionals for detail order ID %d", detailAdditionals.size(), detailOrder.getIdDetailOrder());
        }

        order.setDetailOrders(detailOrders);
        LOG.infof("@getOrdersPending SERV > Found %d detail orders for order ID %d", detailOrders.size(), order.getIdOrder());

        LOG.infof("@getOrdersPending SERV > Finish service to obtain the orders");
        return order;
    }
}
