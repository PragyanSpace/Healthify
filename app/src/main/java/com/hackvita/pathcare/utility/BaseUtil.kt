package com.hackvita.pathcare.utility

import android.util.Patterns
import java.util.regex.Pattern


object BaseUtil {

    fun isValidPhoneNo(phoneNo: String?): Boolean {
        val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
        if (phoneNo?.matches(regex) == true) {
            val a = ((phoneNo?.trim()?.get(0).toString()?.toInt()) ?: 0 >= 6)
            val b = ((phoneNo?.trim()?.length) ?: 0 == 10)
            return a && b
        }
        return false
    }

     fun isValidEmail(email: String): Boolean {
         val pattern: Pattern = Patterns.EMAIL_ADDRESS
         return pattern.matcher(email).matches()
    }

}