package com.example.common

import com.example.authorregister.Author
import io.micronaut.core.annotation.AnnotationValue
import io.micronaut.validation.validator.constraints.ConstraintValidator
import io.micronaut.validation.validator.constraints.ConstraintValidatorContext
import javax.inject.Singleton
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.validation.Constraint
import kotlin.reflect.KClass

@MustBeDocumented
@Target(AnnotationTarget.FIELD, AnnotationTarget.CONSTRUCTOR)
@Retention(AnnotationRetention.RUNTIME)
@Constraint(validatedBy = [UniqueValueValidator::class])
annotation class UniqueValue(
    val field: String,
    val entity: KClass<*>,
    val message: String = "Value already exists"
)

@Singleton
class UniqueValueValidator(private val manager: EntityManager) : ConstraintValidator<UniqueValue, String> {

    private lateinit var entity: KClass<*>
    private lateinit var field: String

    override fun initialize(annotation: UniqueValue?) {
        this.entity = annotation!!.entity
        this.field = annotation.field
    }

    override fun isValid(
        value: String?,
        annotationMetadata: AnnotationValue<UniqueValue>,
        context: ConstraintValidatorContext
    ): Boolean {

        if (value.isNullOrBlank()) return true

        return query(value)
    }

    private fun query(value: String?): Boolean = manager
        .createQuery("SELECT CASE WHEN COUNT(e) > 0 THEN false ELSE true END FROM ${entity.qualifiedName} e WHERE e.${field} = '${value}'", Boolean::class.java)
        .singleResult

}
