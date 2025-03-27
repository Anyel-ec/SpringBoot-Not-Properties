package top.anyel.logger.config;

import jakarta.annotation.PostConstruct;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/*
 * Author: Anyel EC
 * Github: https://github.com/Anyel-ec
 * Creation date: 18/03/2025
 */
@Component
public class PropertyFilterConfig {

    /*
     * This component filters sensitive properties after bean construction.
     * It iterates through the environment’s property sources and, for any property
     * whose name starts with "app.", it sets its value to "******".
     *
     * Importance for hiding secrets: HIGH.
     * This class actively masks sensitive properties from being exposed in the runtime.
     */

    private final StandardEnvironment environment;

    public PropertyFilterConfig(StandardEnvironment environment) {
        this.environment = environment;
    }

    // After the bean is constructed, this method filters out sensitive properties.
    @PostConstruct
    public void filterSensitiveProperties() {
        for (PropertySource<?> propertySource : environment.getPropertySources()) {
            if (propertySource instanceof EnumerablePropertySource) {
                String[] propertyNames = ((EnumerablePropertySource<?>) propertySource).getPropertyNames();
                // For every property starting with "app.", replace its value with "******"
                Arrays.stream(propertyNames)
                        .filter(name -> name.startsWith("app."))
                        .forEach(name -> System.setProperty(name, "******"));
            }
        }
    }
}
