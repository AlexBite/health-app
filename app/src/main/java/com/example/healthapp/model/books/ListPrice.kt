package com.example.healthapp.model.books

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ListPrice (
  @SerializedName("amountInMicros" ) var amountInMicros : Long?    = null,
  @SerializedName("amount" ) var amount : Float?    = null,
  @SerializedName("currencyCode"   ) var currencyCode   : String? = null

)