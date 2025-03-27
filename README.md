# Util Security Logger

**Util Security Logger** is a Spring Boot library designed to automatically mask sensitive properties at runtime. Any property whose key starts with `app.` is automatically replaced with `******`, so you don't have to manually hide or manage secrets in your code.

## Overview

This library provides an automatic way to protect sensitive configuration properties. It uses several mechanisms to ensure that properties with keys beginning with `app.` are masked before they can be used or exposed:

- **EnvironmentPostProcessor**: Processes the environment early during application startup to mask properties.
- **PropertyFilterConfig**: Uses a `@PostConstruct` method to scan and mask sensitive properties after bean creation.
- **PropertyService**: Loads all environment properties into a centralized service with sensitive values replaced by `******`.
- **LogFilter**: A servlet filter that logs any attempts to access sensitive request parameters.

## Features

- **Automatic Masking**: All properties starting with `app.` are automatically replaced with `******`.
- **Early Environment Processing**: Uses an EnvironmentPostProcessor to mask sensitive properties before beans are created.
- **Centralized Property Access**: Provides a service (`PropertyService`) to retrieve masked properties.
- **Security Logging**: Monitors and logs any HTTP requests that try to access sensitive properties.

## Installation

### Maven Dependency

After publishing the library to your Maven repository, add the dependency to your project’s `pom.xml`:

```xml
<dependency>
  <groupId>top.anyel</groupId>
  <artifactId>util-security-logger</artifactId>
  <version>1.0</version>
</dependency>
```

### Repository Configuration

If you're using GitHub Packages, configure your Maven `settings.xml` with your credentials and repository URL as follows:

```xml
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">
  <localRepository>${user.home}/.m2/repository</localRepository>
  <activeProfiles>
    <activeProfile>github</activeProfile>
  </activeProfiles>
  <profiles>
    <profile>
      <id>github</id>
      <repositories>
        <repository>
          <id>central</id>
          <url>https://repo1.maven.org/maven2</url>
        </repository>
        <repository>
          <id>github</id>
          <url>https://maven.pkg.github.com/anyel-top/internal-logger</url>
          <snapshots>
            <enabled>true</enabled>
          </snapshots>
        </repository>
      </repositories>
    </profile>
  </profiles>
  <servers>
    <server>
      <id>github</id>
      <username>your-github-username</username>
      <password>your-personal-access-token</password>
    </server>
  </servers>
</settings>
```

## Usage

Once added as a dependency, the library automatically integrates with your Spring Boot application:

- **No Manual Configuration Required**:  
  Simply include the dependency. The `EnvironmentPostProcessor` registered in the `META-INF/spring.factories` file ensures that any property with a key starting with `app.` is masked.

- **Accessing Properties**:  
  Use the `PropertyService` to retrieve properties in a secure, masked format:

  ```java
  import top.anyel.logger.service.PropertyService;
  
  public class Example {
      public void showProperty() {
          String prop = PropertyService.getProperty("app.database.password");
          System.out.println("Masked property: " + prop); // Will print "******"
      }
  }
  ```

- **Security Monitoring**:  
  The `LogFilter` intercepts HTTP requests and logs warnings if an attempt is made to access a sensitive property as a parameter.

## How It Works

1. **ControllerInjector**
    - Provides static access to `PropertyService` as a bean.
    - **Note**: While it doesn't directly hide secrets, it makes the service available globally.

2. **PropertyFilterConfig**
    - After bean initialization, scans all property sources and replaces values of properties starting with `app.` with `******`.

3. **PropertyFilterEnvironmentPostProcessor**
    - Runs before the Spring context is refreshed.
    - Masks sensitive properties early in the application lifecycle to prevent exposure in beans.

4. **LogFilter**
    - Intercepts incoming HTTP requests.
    - Logs warnings for any request parameters starting with `app.`, adding an extra layer of security.

5. **PropertyService**
    - Loads all properties from the environment.
    - Masks sensitive properties and stores them in a centralized, read-only map for secure access.

## Contributing

Contributions are welcome! Please follow the standard GitHub workflow: fork, create a branch, commit your changes, and open a pull request.

## License

This project is licensed under the terms specified in the [LICENSE](LICENSE) file.
