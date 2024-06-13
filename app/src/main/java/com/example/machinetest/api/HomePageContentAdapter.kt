package com.example.machinetest.api

import com.example.machinetest.model.HomePageContent
import com.google.gson.*
import java.lang.reflect.Type

class HomePageContentAdapter : JsonDeserializer<HomePageContent> {
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): HomePageContent {
        val jsonObject = json.asJsonObject
        val type = jsonObject.get("type").asString

        return when (type) {
            "banner_slider" -> context.deserialize<HomePageContent.BannerSlider>(jsonObject, HomePageContent.BannerSlider::class.java)
            "banner_single" -> context.deserialize<HomePageContent.BannerSingle>(jsonObject, HomePageContent.BannerSingle::class.java)
            "catagories" -> context.deserialize<HomePageContent.Category>(jsonObject, HomePageContent.Category::class.java)
            "products" -> context.deserialize<HomePageContent.Product>(jsonObject, HomePageContent.Product::class.java)
            else -> {
                // Log or handle the unknown type appropriately
                // For now, let's return a default implementation
                HomePageContent.Unknown(type)
            }
        }
    }
}
