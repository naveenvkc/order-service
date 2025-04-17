package com.polarbookshop.orderservice.persistence;

import com.polarbookshop.orderservice.common.OrderStatus;
import com.polarbookshop.orderservice.config.DataConfig;
import com.polarbookshop.orderservice.model.OrderRequest;
import com.polarbookshop.orderservice.operation.SubmitOrderOperation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
//@DataJpaTest(properties = {
//        "spring.datasource.url=jdbc:postgresql://localhost:5432/polardb_order?currentSchema=polar_bookshop",
//        "spring.datasource.username=user",
//        "spring.datasource.password=password"
//})
@Import(DataConfig.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@ActiveProfiles("integration")
@Testcontainers
public class OrderRepositoryJpaTests {

    @Container
    static PostgreSQLContainer<?> postgresql =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:14.4"));

    @Autowired
    private OrderRepository orderRepository;

//    @Autowired
//    private JdbcAggregateTemplate jdbcAggregateTemplate;

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", OrderRepositoryJpaTests::jdbcUrl);
        registry.add("spring.datasource.username", postgresql::getUsername);
        registry.add("spring.datasource.password", postgresql::getPassword);
        //registry.add("spring.flyway.url", postgresql::getJdbcUrl);
    }

    private static String jdbcUrl() {
        return String.format("jdbc:postgresql://%s:%s/%s?currentSchema=polar_bookshop", postgresql.getHost(),
                postgresql.getMappedPort(PostgreSQLContainer.POSTGRESQL_PORT),
                postgresql.getDatabaseName());
    }

    @Test
    void createRejectedOrder() {
        OrderRequest order = OrderRequest.builder()
                .isbn("1234567890")
                .quantity(3)
                .build();
        var rejectedOrder = SubmitOrderOperation.buildRejectedOrder(order);
        var createdOrder = orderRepository.save(rejectedOrder);
        assertThat(createdOrder.getStatus()).isEqualTo(OrderStatus.REJECTED.toString());
    }
}
