package org.lq.internal.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Form;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.lq.internal.domain.combo.*;
import org.lq.internal.domain.detailOrder.DetailOrder;
import org.lq.internal.domain.ingredient.Ingredient;
import org.lq.internal.domain.ingredient.IngredientDTO;
import org.lq.internal.domain.product.Product;
import org.lq.internal.helper.exception.PVException;
import org.lq.internal.repository.ComboRepository;
import org.lq.internal.repository.DetailComboRepository;
import org.lq.internal.repository.ProductRepository;
import org.lq.internal.repository.TypeDiscountRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class ComboService {

    private static final Logger LOG = Logger.getLogger(ComboService.class);

    @Inject
    ComboRepository comboRepository;

    @Inject
    DetailComboRepository detailComboRepository;

    @Inject
    TypeDiscountRepository typeDiscountRepository;

    @Inject
    ProductRepository productRepository;

    public List<Combo> getCombo() throws PVException {
        LOG.infof("@getCombo SERV > Start service to obtain the combos");

        List<Combo> combos = comboRepository.listAll();
        LOG.infof("@getCombo SERV > Retrieved list of combos");

        if (combos.isEmpty()) {
            LOG.warnf("@getCombo SERV > No combos found");
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron combos");
        }

        for (Combo combo : combos) {
            LOG.infof("@getCombo SERV > Fetching detail combos for combo ID %d", combo.getIdCombo());
            List<DetailCombo> detailCombos = detailComboRepository.list("idCombo", combo.getIdCombo());

            for (DetailCombo detailCombo : detailCombos) {
                List<Product> productList = new ArrayList<>();
                Product product = productRepository.findById((long) detailCombo.getIdProduct());
                productList.add(product);
                detailCombo.setProducts(productList);
            }

            LOG.infof("@getCombo SERV > Found %d detail combos for combo ID %d", detailCombos.size(), combo.getIdCombo());
            combo.setDetailCombos(detailCombos);
        }

        LOG.infof("@getCombo SERV > Finish service to obtain the combos");
        return combos;
    }


    public List<Combo> getComboActive() throws PVException {
        LOG.infof("@getComboActive SERV > Start service to obtain active combos");

        List<Combo> combos = comboRepository.findComboActive();
        LOG.infof("@getComboActive SERV > Retrieved list of active combos");

        if (combos.isEmpty()) {
            LOG.warnf("@getComboActive SERV > No active combos found");
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron combos activos.");
        }

        for (Combo combo : combos) {
            LOG.infof("@getCombo SERV > Fetching detail combos for combo ID %d", combo.getIdCombo());
            List<DetailCombo> detailCombos = detailComboRepository.list("idCombo", combo.getIdCombo());

            for (DetailCombo detailCombo : detailCombos) {
                List<Product> productList = new ArrayList<>();
                Product product = productRepository.findById((long) detailCombo.getIdProduct());
                productList.add(product);
                detailCombo.setProducts(productList);
            }

            LOG.infof("@getCombo SERV > Found %d detail combos for combo ID %d", detailCombos.size(), combo.getIdCombo());
            combo.setDetailCombos(detailCombos);
        }

        LOG.infof("@getComboActive SERV > Finish service to obtain active combos");
        return combos;
    }

    public void saveCombo(ComboDTO comboDTO) throws PVException {
        LOG.infof("@saveCombo SERV > Start service to save a new combo");

        LOG.infof("@saveCombo SERV > Creating TypeDiscount entity from DTO");

        TypeDiscount typeDiscount = TypeDiscount.builder()
                .name(comboDTO.getNameDiscount())
                .build();
        typeDiscountRepository.persist(typeDiscount);

        Combo combo = Combo.builder()
                .idTypeDiscount(typeDiscount.getIdTypeDiscount())
                .name(comboDTO.getName())
                .value(comboDTO.getValue())
                .description(comboDTO.getDescription())
                .status(String.valueOf(Status.ACTIVO))
                .build();

        comboRepository.persist(combo);

        for (DetailCombo detailComboDTO : comboDTO.getDetailCombos()) {
            DetailCombo detailCombo = DetailCombo.builder()
                    .idProduct(detailComboDTO.getIdProduct())
                    .idCombo(combo.getIdCombo())
                    .build();

            detailComboRepository.persist(detailCombo);
        }
        comboRepository.persist(combo);

        LOG.infof("@saveCombo SERV > Combo saved successfully with ID %d", combo.getIdCombo());
    }

    public void updateCombo(ComboUpdateDTO comboDTO) throws PVException {
        LOG.infof("@updateCombo SERV > Start service to update combo with ID %d", comboDTO.getIdCombo());

        LOG.infof("@updateCombo SERV > Retrieving existing Combo with ID %d", comboDTO.getIdCombo());
        Combo existingCombo = comboRepository.findById((long) comboDTO.getIdCombo());
        if (existingCombo == null) {
            LOG.errorf("@updateCombo SERV > Combo with ID %d not found", comboDTO.getIdCombo());
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "Combo no encontrado");
        }

        LOG.infof("@updateCombo SERV > Updating details of Combo with ID %d", comboDTO.getIdCombo());
        existingCombo.setName(comboDTO.getName());
        existingCombo.setValue(comboDTO.getValue());
        existingCombo.setDescription(comboDTO.getDescription());
        existingCombo.setStatus(String.valueOf(Status.ACTIVO));

        LOG.infof("@updateCombo SERV > Retrieving or creating TypeDiscount for Combo with ID %d", comboDTO.getIdCombo());
        TypeDiscount typeDiscount = typeDiscountRepository.findById((long) existingCombo.getIdTypeDiscount());
        if (!typeDiscount.getName().equals(comboDTO.getNameDiscount())) {
            typeDiscount.setName(comboDTO.getNameDiscount());
            typeDiscountRepository.persist(typeDiscount);
        }

        existingCombo.setIdTypeDiscount(typeDiscount.getIdTypeDiscount());
        comboRepository.persist(existingCombo);

        LOG.infof("@updateCombo SERV > Updating DetailCombos for Combo with ID %d", comboDTO.getIdCombo());

        List<DetailCombo> currentDetailCombos = detailComboRepository.find("idCombo", existingCombo.getIdCombo()).list();

        Set<Integer> newProductIds = comboDTO.getDetailCombos().stream()
                .map(DetailCombo::getIdProduct)
                .collect(Collectors.toSet());

        for (DetailCombo currentDetailCombo : currentDetailCombos) {
            if (!newProductIds.contains(currentDetailCombo.getIdProduct())) {
                LOG.infof("@updateCombo SERV > Removing DetailCombo with ID %d for Combo with ID %d", currentDetailCombo.getIdDetailCombo(), comboDTO.getIdCombo());
                detailComboRepository.delete(currentDetailCombo);
            }
        }

        for (DetailCombo detailComboDTO : comboDTO.getDetailCombos()) {
            DetailCombo existingDetailCombo = detailComboRepository.find("idCombo = ?1 AND idProduct = ?2", existingCombo.getIdCombo(), detailComboDTO.getIdProduct()).firstResult();

            if (existingDetailCombo == null) {
                LOG.infof("@updateCombo SERV > Creating new DetailCombo for Combo with ID %d", comboDTO.getIdCombo());
                DetailCombo newDetailCombo = DetailCombo.builder()
                        .idProduct(detailComboDTO.getIdProduct())
                        .idCombo(existingCombo.getIdCombo())
                        .build();
                detailComboRepository.persist(newDetailCombo);
            } else {
                LOG.infof("@updateCombo SERV > DetailCombo already exists for Combo with ID %d, updating it", comboDTO.getIdCombo());
                existingDetailCombo.setIdProduct(detailComboDTO.getIdProduct());
                detailComboRepository.persist(existingDetailCombo);
            }
        }

        LOG.infof("@updateCombo SERV > Combo updated successfully with ID %d", existingCombo.getIdCombo());
    }

    public void deactivateCombo(Long comboId) throws PVException {
        LOG.infof("@deactivateCombo SERV > Start service to change status combo with ID %d", comboId);

        LOG.infof("@deactivateCombo SERV > Retrieving existing Combo with ID %d", comboId);
        Combo existingCombo = comboRepository.findById(comboId);
        if (existingCombo == null) {
            LOG.warnf("@deactivateCombo SERV > No combo found with ID %d", comboId);
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "El combo no fue encontrado.");
        }

        if (existingCombo.getStatus().equals(Status.ACTIVO.toString())){
            existingCombo.setStatus(String.valueOf(Status.INACTIVO));
        } else {
            existingCombo.setStatus(String.valueOf(Status.ACTIVO));
        }

        LOG.infof("@deactivateCombo SERV > Persisting change status Combo with ID %d", comboId);

        comboRepository.persist(existingCombo);

        LOG.infof("@deactivateCombo SERV > Combo with ID %d change status successfully", comboId);
    }
}
