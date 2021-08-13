package com.example.authorregister

import com.fasterxml.jackson.annotation.JsonProperty

class AddressResponse(
    @JsonProperty("logradouro")
    val street: String,
    @JsonProperty("localidade")
    val city: String,
    @JsonProperty("uf")
    val state: String
){
    fun toAddress(number: String, zipcode: String): Address{
        return Address(number, zipcode, street, city, state)
    }
}