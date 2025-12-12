package com.kotlinspring.catalogservice

import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

//Singleton
@Testcontainers
object PostgresTestContainerConfig {

    @Container
    val instance: PostgreSQLContainer<Nothing> = PostgreSQLContainer<Nothing>(DockerImageName.parse("postgres:15")).apply {
        withDatabaseName("catalogdb")
        withUsername("test")
        withPassword("test")
    }
}

