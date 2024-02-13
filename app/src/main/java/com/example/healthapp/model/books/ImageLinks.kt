package com.example.healthapp.model.books

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ImageLinks (

  @SerializedName("smallThumbnail" ) var smallThumbnail : String? = null,
  @SerializedName("thumbnail"      ) var thumbnail      : String? = null

)