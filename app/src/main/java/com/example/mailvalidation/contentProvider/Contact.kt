package com.example.mailvalidation.contentProvider

import java.io.Serializable

data class Contact(
    val displayName: String?,
    val phoneNumbers: List<Number?>?
)
data class Number(
    val no: String?
)
