package com.example.healthapp.model.books

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Pdf (

  @SerializedName("isAvailable"  ) var isAvailable  : Boolean? = null,
  @SerializedName("acsTokenLink" ) var acsTokenLink : String?  = null

)