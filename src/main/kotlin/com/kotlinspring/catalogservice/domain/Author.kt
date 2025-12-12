package com.kotlinspring.catalogservice.domain

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "authors")
data class Author(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Long?,

    var name: String,

    @OneToMany(
        mappedBy = "author",
        cascade = [CascadeType.ALL],
        orphanRemoval = true
    )
    val books: List<Book> = mutableListOf()
)
