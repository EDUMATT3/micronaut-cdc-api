package com.example.authorregister

import com.example.common.UniqueValue
import io.micronaut.core.annotation.Introspected
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Introspected
data class AuthorRegisterRequest(
    @field:NotBlank val name: String,
    @field:NotBlank /*@field:Email @field:UniqueValue(entity = Author::class, field = "email")*/ val email: String,
    @field:NotBlank @field:Size(max = 400) val description: String,
    @field:NotBlank val zipcode: String,
    @field:NotBlank val houseNumber: String
) {
    fun toAuthor (addressResponse: AddressResponse): Author {
        val address = addressResponse.toAddress(houseNumber, zipcode)
        return Author(name, email, description, address,)
    }
}
