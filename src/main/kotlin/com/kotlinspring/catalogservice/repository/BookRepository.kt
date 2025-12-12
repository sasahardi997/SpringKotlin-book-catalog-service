package com.kotlinspring.catalogservice.repository

import com.kotlinspring.catalogservice.domain.Book
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface BookRepository: JpaRepository<Book, Long> {

    fun findByNameContaining(name: String): List<Book>

    @Query(
        "SELECT * FROM books " +
                "WHERE name like %?1%"
        , nativeQuery = true)
    fun findBookByName(name: String): List<Book>
}