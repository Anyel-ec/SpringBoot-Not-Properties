package top.anyel.logger.controller;

/*
 * Author: Anyel EC
 * Github: https://github.com/Anyel-ec
 * Creation date: 18/03/2025
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.anyel.logger.service.PropertyService;

import java.util.HashMap;
import java.util.Map;
@RestController
public class SecureController {


    private static final Logger logger = LoggerFactory.getLogger(SecureController.class);

    // Inyección de valores desde application.properties
    @Value("${app.database.password:1111}")
    private String databasePassword;

    @Value("${app.service.apikey:1111}")
    private String serviceApiKey;

    @GetMapping("/show-config")
    public Map<String, String> showConfig() {
        logger.info("📌 Mostrando valores inyectados (ocultando sensibles)");

        Map<String, String> response = new HashMap<>();
        response.put("app.database.password", databasePassword);  // Ocultamos la contraseña
        logger.info("🔑 Mostrando la API key");
        logger.info(serviceApiKey);
        response.put("app.service.apikey", serviceApiKey); // Mostramos la API key

        return response;
    }

    @GetMapping("/config")
    public Map<String, String> getAllConfig() {
        logger.warn("⚠ Intento de mostrar propiedades de application.properties (las sensibles están ocultas)");
        return PropertyService.getAllProperties();
    }
}