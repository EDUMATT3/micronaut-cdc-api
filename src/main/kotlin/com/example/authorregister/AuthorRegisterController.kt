package com.example.authorregister

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.http.uri.UriBuilder
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import javax.validation.Valid

@Validated
@Controller("/authors")
class AuthorRegisterController(
    private val authorRepository: AuthorRepository,
    private val addressClient: AddressClient
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Post
    fun register(@Body @Valid request: AuthorRegisterRequest): HttpResponse<Any> {

        //todo melhorar a lógica do client, outras respostas etc...
        val addressResponse: AddressResponse = addressClient.consult(request.zipcode).body()!!

        val author = request.toAuthor(addressResponse)
        authorRepository.save(author)

        logger.info("registered author with id: {}", author.id)

        val uriLocation = UriBuilder.of("/authors/{id}")
            .expand(mutableMapOf("id" to author.id))

        return HttpResponse.created(uriLocation)
    }

}