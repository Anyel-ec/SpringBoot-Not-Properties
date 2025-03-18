package top.anyel.logger.controller;

/*
 * Author: Anyel EC
 * Github: https://github.com/Anyel-ec
 * Creation date: 18/03/2025
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SecureController {

    private static final Logger logger = LoggerFactory.getLogger(SecureController.class);
    private final Environment environment;

    public SecureController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/config")
    public String getConfig(@RequestParam String key) {
        if (key.startsWith("app.")) {
            logger.warn("Intento de acceso a propiedad bloqueada: {}", key);
            return "Acceso denegado.";
        }
        return environment.getProperty(key, "No encontrado");
    }
}