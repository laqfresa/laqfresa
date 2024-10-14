package org.lq.internal.helper.format;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;

import java.io.IOException;

@ApplicationScoped
public class JsonFormatter {

    public String formatJson(Object obj) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        /* Use objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        if it is required to print with line break */
        return objectMapper.writeValueAsString(obj);
    }
}
