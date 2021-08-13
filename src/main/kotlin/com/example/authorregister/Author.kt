package com.example.authorregister

import io.micronaut.data.annotation.DateCreated
import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.Embedded
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Author(
    val name: String,
    val email: String,
    var description: String,
    @field:Embedded val address: Address
){
    @Id
    @GeneratedValue
    var id: Long? = null

    @DateCreated
    lateinit var createdAt: LocalDateTime
}
