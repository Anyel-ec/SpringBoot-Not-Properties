package top.anyel.logger.service;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/*
 * Author: Anyel EC
 * Github: https://github.com/Anyel-ec
 * Creation date: 18/03/2025
 */

@Slf4j
@Service
public class PropertyService {

    /*
     * This service loads all properties from the Spring Environment and stores them
     * in a static map. For any property starting with "app.", it stores "******" instead
     * of the actual value.
     *
     * It provides methods to retrieve all properties or a specific property.
     *
     * Importance for hiding secrets: HIGH.
     * This service centralizes property loading and ensures that sensitive properties
     * are masked. This prevents accidental exposure of secrets.
     */
    private final ConfigurableEnvironment environment;
    private static final Map<String, String> propertiesMap = new HashMap<>();

    public PropertyService(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void loadProperties() {
        log.info("🔍 Cargando propiedades globalmente...");

        for (PropertySource<?> propertySource : environment.getPropertySources()) {
            if (propertySource instanceof EnumerablePropertySource<?> enumerablePropertySource) {
                for (String key : enumerablePropertySource.getPropertyNames()) {
                    String value = environment.getProperty(key, "No encontrado");

                    // Ocultar cualquier propiedad que comience con "app."
                    if (key.startsWith("app.")) {
                        propertiesMap.put(key, "******");
                    } else {
                        propertiesMap.put(key, value);
                    }

                    // Mostrar en consola para depuración
                    log.info("🔹 Propiedad cargada: " + key + " = " + (key.startsWith("app.") ? "******" : value));
                }
            }
        }

        log.info("✅ Propiedades cargadas correctamente.");
    }

    public static Map<String, String> getAllProperties() {
        return Collections.unmodifiableMap(propertiesMap);
    }

    public static String getProperty(String key) {
        return propertiesMap.getOrDefault(key, "No encontrado");
    }
}
