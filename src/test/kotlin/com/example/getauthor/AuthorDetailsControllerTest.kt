package com.example.getauthor

import com.example.authorregister.Address
import com.example.authorregister.Author
import com.example.authorregister.AuthorRepository
import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest
internal class AuthorDetailsControllerTest{

    @Inject
    private lateinit var authorRepository: AuthorRepository

    @Inject
    @field:Client("/")
    private lateinit var client: HttpClient

    private lateinit var author: Author

    @BeforeEach
    internal fun setUp() {
        val address = Address("24", "13181300", "Rua fulano de tal", "Sumacity", "SP")
        author = Author("fulano de tal", "fulano@email.com", "any desc", address)
        authorRepository.save(author)
    }

    @AfterEach
    internal fun tearDown() = authorRepository.deleteAll()

    @Test
    internal fun  `should be return details of a author` (){

        val request: HttpRequest<Any> = HttpRequest.GET<Any?>("/authors?email=${author.email}")
        val response = client.toBlocking().exchange(request, Argument.listOf(AuthorDetailsResponse::class.java))

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertEquals(response.body()!!.size, 1, "response list size should be 1")
        assertEquals(response.body()!![0].name, author.name)
    }

}