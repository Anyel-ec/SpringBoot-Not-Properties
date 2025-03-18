package top.anyel.logger.config;

/*
 * Author: Anyel EC
 * Github: https://github.com/Anyel-ec
 * Creation date: 18/03/2025
 */
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import top.anyel.logger.service.PropertyService;

@Configuration
public class ControllerInjector {

    private static PropertyService staticPropertyService;

    public ControllerInjector(PropertyService propertyService) {
        staticPropertyService = propertyService;
    }

    @Bean
    public static PropertyService getPropertyService() {
        return staticPropertyService;
    }
}
