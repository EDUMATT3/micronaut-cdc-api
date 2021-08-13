package com.example.getauthor

import com.example.authorregister.AddressResponse
import com.example.authorregister.Author

class AuthorDetailsResponse(author: Author) {

    val name: String = author.name
    val email: String = author.email
    val description: String = author.description
    val address = author.address
}
