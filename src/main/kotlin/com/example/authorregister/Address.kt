package com.example.authorregister

import javax.persistence.Embeddable
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Embeddable
class Address(
    val number: String,
    val zipcode: String,
    val street: String,
    val city: String,
    val state: String
)
