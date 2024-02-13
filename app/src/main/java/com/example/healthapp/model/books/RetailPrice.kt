package com.example.healthapp.model.books

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RetailPrice (

  @SerializedName("amountInMicros" ) var amountInMicros : Int?    = null,
  @SerializedName("amount" ) var amount : Float?    = null,
  @SerializedName("currencyCode"   ) var currencyCode   : String? = null

)