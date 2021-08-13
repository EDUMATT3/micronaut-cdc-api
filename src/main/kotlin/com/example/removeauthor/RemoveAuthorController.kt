package com.example.removeauthor

import com.example.authorregister.AuthorRepository
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import io.micronaut.http.annotation.PathVariable

@Controller("author")
class RemoveAuthorController(private val authorRepository: AuthorRepository) {

    @Delete("{id}")
    fun remove(@PathVariable id: Long) : HttpResponse<Any> {

        val existsById: Boolean = authorRepository.existsById(id)

        if (!existsById){
            return HttpResponse.notFound()
        }

        authorRepository.deleteById(id)

        return HttpResponse.ok()
    }
}