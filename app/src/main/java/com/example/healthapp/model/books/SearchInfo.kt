package com.example.healthapp.model.books

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SearchInfo (

  @SerializedName("textSnippet" ) var textSnippet : String? = null

)