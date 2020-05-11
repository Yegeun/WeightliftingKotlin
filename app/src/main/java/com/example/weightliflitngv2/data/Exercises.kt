package com.example.weightliflitngv2.data

import com.google.firebase.database.Exclude

data class Exercises (
    @get:Exclude
    var id: String? = null,
    var exercise1:String? = null,
    var weight1:String? = null,
    var set1:String? = null,
    var rep1:String? = null,
    var exercise2:String? = null,
    var weight2:String? = null,
    var set2:String? = null,
    var rep2:String? = null,
    var exercise3:String? = null,
    var weight3:String? = null,
    var set3:String? = null,
    var rep3:String? = null,
    var exercise4:String? = null,
    var weight4:String? = null,
    var set4:String? = null,
    var rep4:String? = null
) {
    override fun equals(other: Any?): Boolean { // check if has the same id or not
        return if (other is Exercises) {
            other.id == id
        } else false
    }
}