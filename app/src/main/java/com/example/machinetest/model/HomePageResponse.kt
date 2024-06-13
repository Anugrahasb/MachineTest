package com.example.machinetest.model

import com.google.gson.annotations.SerializedName

sealed class HomePageContent {
    data class BannerSlider(
        @SerializedName("type") val type: String,
        @SerializedName("title") val title: String,
        @SerializedName("contents") val contents: List<Content>
    ) : HomePageContent()

    data class BannerSingle(
        @SerializedName("type") val type: String,
        @SerializedName("title") val title: String,
        @SerializedName("image_url") val imageUrl: String
    ) : HomePageContent()

    data class Category(
        @SerializedName("type") val type: String,
        @SerializedName("title") val title: String,
        @SerializedName("contents") val contents: List<Content>
    ) : HomePageContent()

    data class Product(
        @SerializedName("type") val type: String,
        @SerializedName("title") val title: String,
        @SerializedName("contents") val contents: List<Content>
    ) : HomePageContent()

    data class Unknown(
        @SerializedName("type") val type: String
    ) : HomePageContent()
}

data class Content(
    @SerializedName("title") val title: String? = null,
    @SerializedName("image_url") val imageUrl: String? = null,
    @SerializedName("sku") val sku: String? = null,
    @SerializedName("product_name") val productName: String? = null,
    @SerializedName("product_image") val productImage: String? = null,
    @SerializedName("product_rating") val productRating: Int? = null,
    @SerializedName("actual_price") val actualPrice: String? = null,
    @SerializedName("offer_price") val offerPrice: String? = null,
    @SerializedName("discount") val discount: String? = null
)

