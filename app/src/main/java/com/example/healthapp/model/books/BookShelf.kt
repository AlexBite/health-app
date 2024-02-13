package com.example.healthapp.model.books

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class BookShelf (

  @SerializedName("kind"       ) var kind       : String?          = null,
  @SerializedName("totalItems" ) var totalItems : Int?             = null,
  @SerializedName("items"      ) var items      : ArrayList<Items> = arrayListOf()

)