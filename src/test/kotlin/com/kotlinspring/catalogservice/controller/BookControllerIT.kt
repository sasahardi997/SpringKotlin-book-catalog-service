package com.kotlinspring.catalogservice.controller

import com.kotlinspring.catalogservice.PostgresTestContainerConfig
import com.kotlinspring.catalogservice.SpringContext
import com.kotlinspring.catalogservice.domain.Author
import com.kotlinspring.catalogservice.domain.Book
import com.kotlinspring.catalogservice.domain.dto.BookDTO
import com.kotlinspring.catalogservice.exception.BookServiceErrorResponse
import com.kotlinspring.catalogservice.repository.AuthorRepository
import com.kotlinspring.catalogservice.repository.BookRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertNotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext
import kotlin.test.Test
import kotlin.test.assertEquals

class BookControllerIT: SpringContext() {

    @Autowired
    private lateinit var bookRepository: BookRepository

    @Autowired
    private lateinit var authorRepository: AuthorRepository

    private lateinit var webTestClient: WebTestClient

    private val BOOKS_URL = "/v1/books"

    @BeforeEach
    fun setUp(context: WebApplicationContext) {
        authorRepository.deleteAll()
        bookRepository.deleteAll()
        webTestClient = MockMvcWebTestClient.bindToApplicationContext(context).build()
    }

    @Test
    fun `should return dto with id if created successfully`() {
        val author = authorRepository.save(Author(id = null, name = "George Orwell"))
        val bookDTO = BookDTO(null, name = "1984", genre = "Novel", author.id)

        val result = webTestClient.post()
            .uri(BOOKS_URL)
            .bodyValue(bookDTO)
            .exchange()
            .expectStatus().isCreated
            .expectBody(BookDTO::class.java)
            .returnResult()
            .responseBody

        assertNotNull(result)
        assertNotNull(result.id)
        assertEquals("1984", result.name)
        assertEquals("Novel", result.genre)
    }

    @Test
    fun `should return error 400 when book name is empty`() {
        val bookDTO = BookDTO(null, name = "", genre = "Novel")

        webTestClient.post()
            .uri(BOOKS_URL)
            .bodyValue(bookDTO)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `should return error 400 when genre is empty`() {
        val bookDTO = BookDTO(null, name = "1984", genre = "")

        webTestClient.post()
            .uri(BOOKS_URL)
            .bodyValue(bookDTO)
            .exchange()
            .expectStatus().isBadRequest
    }

    @Test
    fun `should return map with both fields printed when both are empty`() {
        val bookDTO = BookDTO(null, name = "", genre = "")

        val errors = webTestClient.post()
            .uri(BOOKS_URL)
            .bodyValue(bookDTO)
            .exchange()
            .expectStatus().isBadRequest
            .expectBody(Map::class.java)
            .returnResult()
            .responseBody as Map<*, *>

        assertEquals("Book name cannot be blank", errors["name"])
        assertEquals("Genre cannot be blank", errors["genre"])
    }

    @Test
    fun `should return all the books from database`() {
        val author = authorRepository.save(Author(id = null, name = "George Orwell"))
        val book1984 = Book(null, name = "1984", genre = "Novel", author)
        val bookIkigai = Book(null, name = "Ikigai", genre = "Popular psychology", author)
        bookRepository.saveAll(listOf(book1984, bookIkigai))

        val list = webTestClient.get()
            .uri(BOOKS_URL)
            .exchange()
            .expectStatus().isOk
            .expectBodyList(BookDTO::class.java)
            .returnResult()
            .responseBody

        assertNotNull(list)
        assertEquals(2, list.size)

    }

    @Test
    fun `should update an existing book`() {
        val author = authorRepository.save(Author(id = null, name = "George Orwell"))
        val book1984 = Book(null, name = "1984", genre = "Novel", author)
        val savedBook = bookRepository.save(book1984)

        val bookDTO = BookDTO(savedBook.id, name = "1984", genre = "Dystopia Novel", author.id)

        val result = webTestClient.put()
            .uri("$BOOKS_URL/${savedBook.id}")
            .bodyValue(bookDTO)
            .exchange()
            .expectStatus().isOk
            .expectBody(BookDTO::class.java)
            .returnResult()
            .responseBody

        assertNotNull(result)
        assertEquals("Dystopia Novel", result.genre)
    }

    @Test
    fun `should return not found when book does not exist`() {
        val id = 999L
        val bookDTO = BookDTO(id = id, name = "1984", genre = "Dystopia Novel", authorId = id)

        val result = webTestClient.put()
            .uri("$BOOKS_URL/$id")
            .bodyValue(bookDTO)
            .exchange()
            .expectStatus().isNotFound
            .expectBody(BookServiceErrorResponse::class.java)
            .returnResult()
            .responseBody

        assertNotNull(result)
        assertEquals(404, result.errorCode)
        assertEquals("Book with id $id not found.", result.message)
    }

    @Test
    fun `delete an existing book should return 204 status`() {
        val author = authorRepository.save(Author(id = null, name = "George Orwell"))
        val book1984 = Book(null, name = "1984", genre = "Novel", author)
        val savedBook = bookRepository.save(book1984)

        webTestClient.delete()
            .uri("$BOOKS_URL/${savedBook.id}")
            .exchange()
            .expectStatus().isNoContent
    }

    @Test
    fun `delete should return 404 when book does not exist`() {
        webTestClient.delete()
            .uri("$BOOKS_URL/999")
            .exchange()
            .expectStatus().isNotFound
    }
}