package com.kotlinspring.catalogservice.domain.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class BookDTO(
    val id: Long?,

    @field:NotBlank("Book name cannot be blank")
    val name: String,

    @field:NotBlank("Genre cannot be blank")
    val genre: String,

    @field:NotNull("Author id must not be null")
    val authorId: Long? = null
)