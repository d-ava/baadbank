package com.example.baadbank.util

open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    //returns the content and prevents its use again

    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    //returns the content, even if its already been handled

    fun peekContent(): T = content

}