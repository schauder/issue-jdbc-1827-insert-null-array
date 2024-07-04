package be.testcontainertest;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertTrue;


@Testcontainers
@Tag("MssqlTest")
public abstract class MssqlContainerBaseTest {

    private static final String MSSQL_DOCKER_IMAGE = "mcr.microsoft.com/mssql/server:2019-latest";

    @Container
    protected static final MSSQLServerContainer<?> MSSQL_SERVER_CONTAINER = new MSSQLServerContainer<>(
            DockerImageName.parse(MSSQL_DOCKER_IMAGE)
                    .asCompatibleSubstituteFor("mcr.microsoft.com/mssql/server")
    ).acceptLicense();

    @DynamicPropertySource
    static void mssqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", MSSQL_SERVER_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MSSQL_SERVER_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MSSQL_SERVER_CONTAINER::getPassword);
        registry.add("spring.datasource.driver-class-name", MSSQL_SERVER_CONTAINER::getDriverClassName);
    }

    @Test
    void isContainerRunning_test() {
        assertTrue(MSSQL_SERVER_CONTAINER.isRunning());
    }
}