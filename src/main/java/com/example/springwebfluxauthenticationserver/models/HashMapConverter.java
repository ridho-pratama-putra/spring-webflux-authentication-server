package com.example.springwebfluxauthenticationserver.models;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;

public class HashMapConverter implements AttributeConverter<Map<String, Object>, String> {

    Logger logger = LoggerFactory.getLogger(HashMapConverter.class);

    @Override
    public String convertToDatabaseColumn(Map<String, Object> customerInfo) {

        String customerInfoJson = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            customerInfoJson = objectMapper.writeValueAsString(customerInfo);
        } catch (final JsonProcessingException e) {
            logger.error("JSON writing error", e);
        }

        return customerInfoJson;
    }

    @Override
    public Map<String, Object> convertToEntityAttribute(String customerInfoJSON) {

        Map<String, Object> customerInfo = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            customerInfo = objectMapper.readValue(customerInfoJSON, new TypeReference<HashMap<String, Object>>() {});
        } catch (final IOException e) {
            logger.error("JSON reading error", e);
        }

        return customerInfo;
    }
}