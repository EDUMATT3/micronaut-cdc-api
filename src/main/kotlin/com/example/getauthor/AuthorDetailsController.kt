package com.example.getauthor

import com.example.authorregister.Author
import com.example.authorregister.AuthorRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import javax.transaction.Transactional

@Controller("/authors")
class AuthorDetailsController(private val authorRepository: AuthorRepository) {

    @Get
    @Transactional //a classe e o metodo precisam estar open(herdado e sobreescrito), o micronaut quando for escrever o bytecode faz isso
    fun getAuthor(@QueryValue(defaultValue = "") email: String): HttpResponse<List<AuthorDetailsResponse>>{
        val authors: List<Author>

        if (email.isBlank()){
            authors = authorRepository.findAll()
        }else{
            authors = authorRepository.findByEmail(email)
            if (authors.isEmpty()) return HttpResponse.notFound()
        }

        val response = authors.map(::AuthorDetailsResponse)

        return HttpResponse.ok(response)
    }

}