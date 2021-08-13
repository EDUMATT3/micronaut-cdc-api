package com.example.authorregister

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.client.annotation.Client

@Client("\${address.consult.url}")
interface AddressClient {

    @Get("/{zipcode}/json/")
    fun consult(@PathVariable zipcode: String): HttpResponse<AddressResponse>
}
