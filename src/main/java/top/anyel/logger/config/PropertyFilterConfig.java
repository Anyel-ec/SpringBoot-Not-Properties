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

    private final StandardEnvironment environment;

    public PropertyFilterConfig(StandardEnvironment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void filterSensitiveProperties() {
        for (PropertySource<?> propertySource : environment.getPropertySources()) {
            if (propertySource instanceof EnumerablePropertySource) {
                String[] propertyNames = ((EnumerablePropertySource<?>) propertySource).getPropertyNames();
                Arrays.stream(propertyNames)
                        .filter(name -> name.startsWith("app."))
                        .forEach(name -> System.setProperty(name, "******"));
            }
        }
    }
}
