package com.example.authorregister

import io.micronaut.http.HttpHeaders
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import javax.inject.Inject


@MicronautTest
internal class AuthorRegisterControllerTest {

    @Inject
    lateinit var addressClient: AddressClient

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    internal fun `Should be register a new author`() {

        val authorRegisterRequest = AuthorRegisterRequest(
            "fulano de tal",
            "fulano@email.com",
            "any desc",
            "10111-222",
            "24"
        )

        val addressResponse = AddressResponse("Rua fulano de tal", "Sumacity", "SP")

        Mockito.`when`(addressClient.consult(authorRegisterRequest.zipcode))
            .thenReturn(HttpResponse.ok(addressResponse))

        val request = HttpRequest.POST("/authors", authorRegisterRequest)

        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.CREATED, response.status)
        assertTrue(response.headers.contains(HttpHeaders.LOCATION))
        assertTrue(response.header(HttpHeaders.LOCATION)!!.matches("/authors/\\d".toRegex()))
    }

    @MockBean(AddressClient::class)
    fun addressClient(): AddressClient = Mockito.mock(AddressClient::class.java)
}