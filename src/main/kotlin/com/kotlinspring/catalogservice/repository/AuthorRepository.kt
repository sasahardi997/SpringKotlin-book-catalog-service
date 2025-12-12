package com.kotlinspring.catalogservice.repository

import com.kotlinspring.catalogservice.domain.Author
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AuthorRepository : CrudRepository<Author, Long>{
}