package com.kotlinspring.catalogservice

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
abstract class SpringContext {

    //We can use this approach or with application.yaml
//    companion object {
//        @JvmStatic
//        @DynamicPropertySource
//        fun registerProperties(registry: DynamicPropertyRegistry) {
//            registry.add("spring.datasource.url") { PostgresTestContainerConfig.instance.jdbcUrl }
//            registry.add("spring.datasource.username") { PostgresTestContainerConfig.instance.username }
//            registry.add("spring.datasource.password") { PostgresTestContainerConfig.instance.password }
//        }
//    }
}