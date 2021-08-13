package com.example.authorregister

import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest(
    rollback = false, //default=true
    transactionMode = TransactionMode.SINGLE_TRANSACTION, //default=SEPARATE_TRANSACIONS
    transactional = false // default true
)
class AuthorRepositoryTest {

    @Inject
    lateinit var authorRepository: AuthorRepository

    //default: setup, teardown e tests possuem transação própria
    //para ser a mesma... transactionMode = TransactionMode.SINGLE_TRANSACTION

    //default: micronaut commit a transação no @BeforeEach e @AfterEach
    @BeforeEach
    internal fun setUp() = authorRepository.deleteAll()

    @AfterEach
    internal fun tearDown() = authorRepository.deleteAll()

    //default: faz o roolback em CADA test, não seria preciso o setup e teardown
    //para não ocorrer o roolback... roolback = false
    @Test
    fun `should be insert a new author`(){
        val address = Address("24", "13181300", "Rua fulano de tal", "Sumacity", "SP")
        val author = Author("fulano de tal", "fulano@email.com", "any desc", address)

        authorRepository.save(author)

        assertEquals(1L, authorRepository.count())
    }
}