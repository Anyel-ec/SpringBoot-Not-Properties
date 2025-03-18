package top.anyel.logger.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class PropertyService {

    private static final Logger logger = LoggerFactory.getLogger(PropertyService.class);

    private final ConfigurableEnvironment environment;
    private static final Map<String, String> propertiesMap = new HashMap<>();

    public PropertyService(ConfigurableEnvironment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void loadProperties() {
        System.out.println("🔍 Cargando propiedades globalmente...");

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
                    System.out.println("🔹 Propiedad cargada: " + key + " = " + (key.startsWith("app.") ? "******" : value));
                }
            }
        }

        System.out.println("✅ Propiedades cargadas correctamente.");
    }

    public static Map<String, String> getAllProperties() {
        return Collections.unmodifiableMap(propertiesMap);
    }

    public static String getProperty(String key) {
        return propertiesMap.getOrDefault(key, "No encontrado");
    }
}
