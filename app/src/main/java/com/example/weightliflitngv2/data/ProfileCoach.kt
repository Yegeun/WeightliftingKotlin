package com.example.weightliflitngv2.data

import com.google.firebase.database.Exclude

data class ProfileCoach (
    @get:Exclude
    var id: String? = null,
    var email:String? = null
//    @get:Exclude
//    var isDeleted: Boolean = false * to make a delete function
) {
    override fun equals(other: Any?): Boolean { // check if has the same id or not
        return if (other is ProfileCoach) {
            other.id == id
        } else false
    }

}