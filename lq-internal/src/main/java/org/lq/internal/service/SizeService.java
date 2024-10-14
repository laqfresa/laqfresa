package org.lq.internal.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.lq.internal.domain.size.Size;
import org.lq.internal.helper.exception.PVException;
import org.lq.internal.repository.SizeRepository;

import java.util.List;

@ApplicationScoped
public class SizeService {

    @Inject
    SizeRepository sizeRepository;

    public List<Size> getSizes() throws PVException {
        List<Size> sizes = sizeRepository.listAll();

        if (sizes.isEmpty()) {
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron tamaños");
        }

        return sizes;
    }

    public Size getSizeNumber(long numberSize) throws PVException{

        Size size = sizeRepository.findById(numberSize);

        if(size == null){
            throw new PVException(Response.Status.NOT_FOUND.getStatusCode(),
                    "No se encontro ningún tamaño con el número ingresado");
        }
        return size;
    }
}
