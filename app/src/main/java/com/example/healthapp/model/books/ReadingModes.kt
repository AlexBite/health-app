package com.example.healthapp.model.books

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ReadingModes (
  @SerializedName("pageCount" ) var pageCount : Int? = null,
  @SerializedName("text"  ) var text  : Boolean? = null,
  @SerializedName("image" ) var image : Boolean? = null

)