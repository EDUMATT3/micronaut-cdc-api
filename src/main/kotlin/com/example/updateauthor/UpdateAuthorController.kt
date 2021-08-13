package com.example.updateauthor

import com.example.authorregister.Author
import com.example.authorregister.AuthorRepository
import com.example.getauthor.AuthorDetailsResponse
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Put
import java.util.*
import javax.transaction.Transactional

@Controller("authors")
class UpdateAuthorController(val authorRepository: AuthorRepository) {

    @Put("{id}")
    @Transactional
    fun update(@PathVariable id: Long, description: String) : HttpResponse<Any>{

        val possibleAuthor: Optional<Author> = authorRepository.findById(id)

        if (possibleAuthor.isEmpty) {
            return HttpResponse.notFound()
        }

        val author = possibleAuthor.get()

        author.description = description

        //authorRepository.update(author)
        //Não precisa pq o objeto esta em estado de managed e o transaction o atualiza de forma mais implicita

        return HttpResponse.ok(AuthorDetailsResponse(author))
    }
}