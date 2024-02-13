package com.example.healthapp.model.books

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class Offers (

  @SerializedName("finskyOfferType" ) var finskyOfferType : Int?         = null,
  @SerializedName("listPrice"       ) var listPrice       : ListPrice?   = ListPrice(),
  @SerializedName("retailPrice"     ) var retailPrice     : RetailPrice? = RetailPrice()

)