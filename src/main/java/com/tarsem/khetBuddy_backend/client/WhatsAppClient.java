package com.tarsem.khetBuddy_backend.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Component
public class WhatsAppClient {

    private final RestTemplate restTemplate;

    @Value("${whatsapp.api.url}")
    private String baseUrl;

    @Value("${whatsapp.token}")
    private String token;

    @Value("${whatsapp.phone.number.id}")
    private String phoneNumberId;

    public WhatsAppClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendTemplate(String phone, String templateName, List<String> params) {

        String apiUrl = baseUrl + "/" + phoneNumberId + "/messages";

        Map<String, Object> payload = new HashMap<>();
        payload.put("messaging_product", "whatsapp");
        payload.put("to", formatPhone(phone));
        payload.put("type", "template");

        Map<String, Object> template = new HashMap<>();
        template.put("name", templateName);
        template.put("language", Map.of("code", "hi"));

        if (params != null && !params.isEmpty()) {

            List<Map<String, Object>> parameters = params.stream()
                    .map(p -> {
                        Map<String, Object> param = new HashMap<>();
                        param.put("type", "text");
                        param.put("text", p);
                        return param;
                    })
                    .toList();

            Map<String, Object> body = new HashMap<>();
            body.put("type", "body");
            body.put("parameters", parameters);

            template.put("components", List.of(body));
        }

        payload.put("template", template);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.postForEntity(apiUrl, new HttpEntity<>(payload, headers), String.class);
    }

    private String formatPhone(String phone) {
        if (phone == null || phone.isBlank()) {
            throw new IllegalArgumentException("Phone number is required");
        }

        // Remove spaces, dashes, etc.
        phone = phone.replaceAll("[^0-9]", "");

        // Already has country code
        if (phone.startsWith("91") && phone.length() == 12) {
            return phone;
        }

        // Local 10-digit number → add 91
        if (phone.length() == 10) {
            return "91" + phone;
        }

        throw new IllegalArgumentException("Invalid phone number format: " + phone);
    }
    public void sendTemplateWithHeader(
            String phone,
            String templateName,
            List<String> headerParams,
            List<String> bodyParams
    ) {

        String apiUrl = baseUrl + "/" + phoneNumberId + "/messages";

        Map<String, Object> payload = new HashMap<>();
        payload.put("messaging_product", "whatsapp");
        payload.put("to", formatPhone(phone));
        payload.put("type", "template");

        Map<String, Object> template = new HashMap<>();
        template.put("name", templateName);
        template.put("language", Map.of("code", "hi"));

        List<Map<String, Object>> components = new ArrayList<>();

        // Header Component
        if (headerParams != null && !headerParams.isEmpty()) {

            List<Map<String, Object>> headerParameters = headerParams.stream()
                    .map(param -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("type", "text");
                        map.put("text", param);
                        return map;
                    })
                    .toList();

            Map<String, Object> header = new HashMap<>();
            header.put("type", "header");
            header.put("parameters", headerParameters);

            components.add(header);
        }

        // Body Component
        if (bodyParams != null && !bodyParams.isEmpty()) {

            List<Map<String, Object>> bodyParameters = bodyParams.stream()
                    .map(param -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("type", "text");
                        map.put("text", param);
                        return map;
                    })
                    .toList();

            Map<String, Object> body = new HashMap<>();
            body.put("type", "body");
            body.put("parameters", bodyParameters);

            components.add(body);
        }

        template.put("components", components);

        payload.put("template", template);

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        headers.setContentType(MediaType.APPLICATION_JSON);

        restTemplate.postForEntity(
                apiUrl,
                new HttpEntity<>(payload, headers),
                String.class
        );
    }
}