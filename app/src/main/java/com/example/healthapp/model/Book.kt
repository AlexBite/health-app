package com.example.healthapp.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    //val id: String?,
    val title: String?,
    val previewLink: String?,
    val imageLink: String?,
    val description: String?
)
