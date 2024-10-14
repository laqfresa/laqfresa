package org.lq.internal.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.lq.internal.domain.combo.Status;
import org.lq.internal.domain.detailProduct.DetailProduct;
import org.lq.internal.domain.ingredient.*;
import org.lq.internal.helper.exception.PVException;
import org.lq.internal.repository.DetailProductRepository;
import org.lq.internal.repository.IngredientDataRepository;
import org.lq.internal.repository.IngredientRepository;
import org.lq.internal.repository.IngredientTypeRepository;

import java.util.Arrays;
import java.util.List;

@ApplicationScoped
public class IngredientService {

    private static final Logger LOG = Logger.getLogger(IngredientService.class);

    @Inject
    IngredientDataRepository ingredientDataRepository;

    @Inject
    IngredientRepository ingredientRepository;

    @Inject
    IngredientTypeRepository ingredientTypeRepository;

    @Inject
    DetailProductRepository detailProductRepository;

    public List<IngredientData> getIngredient() throws PVException {
        LOG.infof("@getIngredient SERV > Start service to obtain the ingredients");

        List<IngredientData> ingredientData = ingredientDataRepository.listAll();
        LOG.infof("@getIngredient SERV > Retrieved list of ingredients");

        if (ingredientData.isEmpty()) {
            LOG.warnf("@getIngredient SERV > No ingredients found");
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron ingredientes.");
        }

        LOG.infof("@getIngredient SERV > Finish service to obtain the ingredients");
        return ingredientData;
    }


    public List<IngredientData> getIngredientToppings() throws PVException {
        LOG.infof("@getIngredient SERV > Start service to obtain the ingredients");
        List<IngredientData> ingredientData = ingredientDataRepository.findActiveIngredients();
        LOG.infof("@getIngredient SERV > Retrieved list of ingredients");

        if (ingredientData.isEmpty()) {
            LOG.warnf("@getIngredient SERV > No ingredients found");
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron ingredientes.");
        }

        LOG.infof("@getIngredient SERV > Finish service to obtain the ingredients");
        return ingredientData;
    }

    public void saveIngredient(IngredientDTO ingredientDTO) throws PVException {
        LOG.infof("@saveIngredient SERV > Start service to save a new ingredient");

        LOG.infof("@saveIngredient SERV > Creating ingredient entity from DTO");

        Ingredient ingredient = Ingredient.builder()
                .ingredientType(ingredientDTO.getIngredientType())
                .name(ingredientDTO.getName())
                .active(true)
                .build();

        LOG.infof("@saveIngredient SERV > Persisting ingredient");
        ingredientRepository.persist(ingredient);

        LOG.infof("@saveIngredient SERV > Ingredient saved successfully with ID %s", ingredient.getIngredientId());
    }

    public void updateIngredient(IngredientUpdateDTO ingredientDTO) throws PVException {
        LOG.infof("@updateIngredient SERV > Start service to update ingredient with ID %d", ingredientDTO.getIngredientId());

        Ingredient existingIngredient = ingredientRepository.findById((long) ingredientDTO.getIngredientId());
        if (existingIngredient == null) {
            LOG.warnf("@updateIngredient SERV > Ingredient with ID %d not found", ingredientDTO.getIngredientId());
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "Ingrediente no encontrado.");
        }

        existingIngredient.setName(ingredientDTO.getName());
        existingIngredient.setIngredientType(ingredientDTO.getIngredientType());
        existingIngredient.setActive(ingredientDTO.getActive());
        LOG.infof("@updateIngredient SERV > Updating ingredient with ID %d", ingredientDTO.getIngredientId());
        ingredientRepository.persist(existingIngredient);

        LOG.infof("@updateIngredient SERV > Ingredient with ID %d updated successfully", ingredientDTO.getIngredientId());
    }

    public void deleteIngredient(Long ingredientId) throws PVException {
        LOG.infof("@deleteIngredient SERV > Start service to delete ingredient with ID %d", ingredientId);

        Ingredient existingIngredient = ingredientRepository.findById(ingredientId);
        if (existingIngredient == null) {
            LOG.warnf("@deleteIngredient SERV > Ingredient with ID %d not found", ingredientId);
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "Ingrediente no encontrado.");
        }

        LOG.infof("@deleteIngredient SERV > Checking if ingredient with ID %d is used in any product", ingredientId);
        List<DetailProduct> detailProductsUsingIngredient = detailProductRepository.list("idIngredient", ingredientId);
        if (!detailProductsUsingIngredient.isEmpty()) {
            LOG.warnf("@deleteIngredient SERV > Ingredient with ID %d is used in one or more products", ingredientId);
            throw new PVException(Response.Status.CONFLICT.getStatusCode(), "El ingrediente está siendo utilizado en uno o más productos y no se puede desactivar.");
        }

        LOG.infof("@deleteIngredient SERV > Deactivating ingredient with ID %d", ingredientId);
        if (existingIngredient.isActive()){
            existingIngredient.setActive(false);
        } else {
            existingIngredient.setActive(true);
        }

        ingredientRepository.persist(existingIngredient);

        LOG.infof("@deleteIngredient SERV > Ingredient with ID %d deactivated successfully", ingredientId);
    }


    public List<TypeIngredient> getIngredientType() throws PVException {
        LOG.info("@getIngredientType SERV > Start service to obtain the ingredient types");

        List<TypeIngredient> ingredientTypeData = ingredientTypeRepository.listAll();

        if (ingredientTypeData.isEmpty()) {
            LOG.warn("@getIngredientType SERV > No ingredient types found");
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron tipos de ingredientes.");
        }

        LOG.info("@getIngredientType SERV > Successfully obtained ingredient types");
        return ingredientTypeData;
    }

    public void saveIngredientType(IngredientTypeDTO ingredientTypeDTO) throws PVException {
        LOG.info("@saveIngredientType SERV > Start service to save a new ingredient type");

        LOG.info("@saveIngredientType SERV > Creating ingredient type entity from DTO");

        TypeIngredient ingredientType = TypeIngredient.builder()
                .name(ingredientTypeDTO.getName())
                .active(true)
                .value(ingredientTypeDTO.getValue())
                .build();

        LOG.info("@saveIngredientType SERV > Persisting ingredient type");
        ingredientTypeRepository.persist(ingredientType);

        LOG.infof("@saveIngredientType SERV > Ingredient type saved successfully with ID: %s", ingredientType.getIngredientTypeId());
    }

    public void updateIngredientType(IngredientTypeUpdateDTO ingredientTypeDTO) throws PVException {
        LOG.infof("@updateIngredientType SERV > Start service to update ingredient type with ID %d", ingredientTypeDTO.getIngredientTypeId());

        TypeIngredient existingIngredientType = ingredientTypeRepository.findById((long) ingredientTypeDTO.getIngredientTypeId());
        if (existingIngredientType == null) {
            LOG.warnf("@updateIngredientType SERV > Ingredient type with ID %d not found", ingredientTypeDTO.getIngredientTypeId());
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "Tipo de ingrediente no encontrado.");
        }

        existingIngredientType.setName(ingredientTypeDTO.getName());
        existingIngredientType.setActive(ingredientTypeDTO.getActive());
        existingIngredientType.setValue(ingredientTypeDTO.getValue());

        LOG.infof("@updateIngredientType SERV > Updating ingredient type with ID %d", ingredientTypeDTO.getIngredientTypeId());
        ingredientTypeRepository.persist(existingIngredientType);

        LOG.infof("@updateIngredientType SERV > Ingredient type with ID %d updated successfully", ingredientTypeDTO.getIngredientTypeId());
    }

    public void deleteIngredientType(Long ingredientTypeId) throws PVException {
        LOG.infof("@deleteIngredientType SERV > Start service to delete ingredient type with ID %d", ingredientTypeId);

        TypeIngredient existingIngredientType = ingredientTypeRepository.findById(ingredientTypeId);
        if (existingIngredientType == null) {
            LOG.warnf("@deleteIngredientType SERV > Ingredient type with ID %d not found", ingredientTypeId);
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "Tipo de ingrediente no encontrado.");
        }

        if (existingIngredientType.isActive()){
            existingIngredientType.setActive(false);
        } else {
            existingIngredientType.setActive(true);
        }

        LOG.infof("@deleteIngredientType SERV > Deactivating ingredient type with ID %d", ingredientTypeId);


        ingredientTypeRepository.persist(existingIngredientType);

        LOG.infof("@deleteIngredientType SERV > Ingredient type with ID %d deleted successfully", ingredientTypeId);
    }
}
