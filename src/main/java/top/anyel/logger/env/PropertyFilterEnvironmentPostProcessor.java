package top.anyel.logger.env;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;

/*
 * Author: Anyel EC
 * Github: https://github.com/Anyel-ec
 * Creation date: 27/03/2025
 */

public class PropertyFilterEnvironmentPostProcessor implements EnvironmentPostProcessor {

    /*
     * This EnvironmentPostProcessor allows you to modify the Environment before
     * the Spring ApplicationContext is refreshed.
     *
     * It iterates over the property sources and for each property that starts with "app.",
     * it replaces its value with "******".
     *
     * Importance for hiding secrets: HIGH.
     * By processing the environment early, it ensures that sensitive properties
     * are masked before they are used anywhere else in the application.
     */

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        for (PropertySource<?> propertySource : environment.getPropertySources()) {
            if (propertySource instanceof EnumerablePropertySource<?> enumerablePropertySource) {
                for (String propertyName : enumerablePropertySource.getPropertyNames()) {
                    if (propertyName.startsWith("app.")) {
                        // Overwrites the property value with "******"
                        System.setProperty(propertyName, "******");
                    }
                }
            }
        }
    }
}