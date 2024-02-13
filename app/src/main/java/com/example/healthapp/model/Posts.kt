package com.example.healthapp.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// Модель получаемых данных
@Serializable
data class Posts(
    // val id: Int,
    val name: String,
    val type: String,
    val description: String,
    @SerialName("img_src") val imgSrc: String
)