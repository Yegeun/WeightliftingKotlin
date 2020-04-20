package com.example.weightliflitngv2.data

import com.google.firebase.database.Exclude
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

fun main(args: Array<String>) {

    val current = LocalDateTime.now()

    val formatter = DateTimeFormatter.BASIC_ISO_DATE
    val formatted = current.format(formatter)

}
data class Athlete(
    @get:Exclude
    var id: String? = null,
    var coach: String? = null,
    var name: String? = null,
    var email: String? = null,
    var dated: Date?= null, // could be todays date
    @get:Exclude
    var isDeleted: Boolean = false
)   {
    override fun equals(other: Any?): Boolean { // check if has the same id or not
        return if (other is Athlete) {
            other.id == id
        } else false
    }

    override fun hashCode(): Int { // create a hash code
        var result = id?.hashCode() ?: 0
        result = 31 * result + (coach?.hashCode() ?: 0)
        result = 31 * result + (email?.hashCode() ?: 0)
        result = 31 * result + (name?.hashCode() ?: 0)
        result = 31 * result + (dated?.hashCode() ?: 0)
        return result
    }
}

