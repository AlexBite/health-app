package com.example.healthapp.model.books

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class IndustryIdentifiers (

  @SerializedName("type"       ) var type       : String? = null,
  @SerializedName("identifier" ) var identifier : String? = null

)