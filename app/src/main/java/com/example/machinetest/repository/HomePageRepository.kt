package com.example.machinetest.repository

import android.content.Context
import com.example.machinetest.api.ApiService
import com.example.machinetest.api.RESTResult
import com.example.machinetest.api.customTryRESTCall
import com.example.machinetest.dao.BannerSingleEntity
import com.example.machinetest.dao.ContentEntity
import com.example.machinetest.dao.HomePageDao
import com.example.machinetest.dao.HomePageDatabase
import com.example.machinetest.model.*
import com.example.machinetest.network.NetworkUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HomePageRepository(
    private val context: Context,
    private val apiService: ApiService,
) {
    private val homePageDao: HomePageDao = HomePageDatabase.getDatabase(context).homePageDao()

    suspend fun getHomePageData(): RESTResult<List<HomePageContent>> {
        return if (NetworkUtil.isNetworkAvailable(context)) {
            customTryRESTCall {
                apiService.getHomePageData().let {
                    // Save data to db if response is successful
                    if (it.isSuccessful) {
                        it.body()?.let { data -> saveHomePageData(data) }
                    }
                    it
                }
            }
        } else {
            // Load data from local database if network is not available
            RESTResult.Success(loadLocalData(homePageDao))
        }
    }

    private suspend fun saveHomePageData(contents: List<HomePageContent>) {
        withContext(Dispatchers.IO) {
            contents.forEach { content ->
                when (content) {
                    is HomePageContent.BannerSingle -> {
                        homePageDao.insertBannerSingle(
                            BannerSingleEntity(
                                type = content.type,
                                title = content.title,
                                imageUrl = content.imageUrl
                            )
                        )
                    }

                    is HomePageContent.BannerSlider -> {
//                        val id = homePageDao.insertBannerSlider(
//                            BannerSliderEntity(
//                                type = content.type,
//                                title = content.title
//                            )
//                        )
//                        insertContentList(content.contents, id.toInt(), "BannerSlider")
                    }

                    is HomePageContent.Category -> {
//                        val id = homePageDao.insertCategory(
//                            CategoryEntity(
//                                type = content.type,
//                                title = content.title
//                            )
//                        )
//                        insertContentList(content.contents, id.toInt(), "Category")
                    }

                    is HomePageContent.Product -> {
//                        val id = homePageDao.insertProduct(
//                            ProductEntity(
//                                type = content.type,
//                                title = content.title
//                            )
//                        )
//                        insertContentList(content.contents, id.toInt(), "Product")
                    }

                    is HomePageContent.Unknown -> {
                        println("Unknown type: ${content.type}")
                    }
                }
            }
        }
    }

    private fun insertContentList(contents: List<Content>, parentId: Int, parentType: String) {
        contents.forEach {
            homePageDao.insertContent(
                ContentEntity(
                    title = it.title,
                    imageUrl = it.imageUrl,
                    sku = it.sku,
                    productName = it.productName,
                    productImage = it.productImage,
                    productRating = it.productRating,
                    actualPrice = it.actualPrice,
                    offerPrice = it.offerPrice,
                    discount = it.discount,
                    parentId = parentId,
                    parentType = parentType
                )
            )
        }
    }
    // Add this function to your repository to load local data.

    suspend fun loadLocalData(homePageDao: HomePageDao): List<HomePageContent> {
        val bannerSliders = homePageDao.getBannerSlidersWithContents().map {
            HomePageContent.BannerSlider(
                type = it.bannerSlider.type,
                title = it.bannerSlider.title,
                contents = it.contents.map { content ->
                    Content(
                        title = content.title,
                        imageUrl = content.imageUrl,
                        sku = content.sku,
                        productName = content.productName,
                        productImage = content.productImage,
                        productRating = content.productRating,
                        actualPrice = content.actualPrice,
                        offerPrice = content.offerPrice,
                        discount = content.discount
                    )
                }
            )
        }
        val bannerSingles = homePageDao.getAllBannerSingles().map {
            HomePageContent.BannerSingle(
                type = it.type,
                title = it.title,
                imageUrl = it.imageUrl
            )
        }
        val categories = homePageDao.getCategoriesWithContents().map {
            HomePageContent.Category(
                type = it.category.type,
                title = it.category.title,
                contents = it.contents.map { content ->
                    Content(
                        title = content.title,
                        imageUrl = content.imageUrl,
                        sku = content.sku,
                        productName = content.productName,
                        productImage = content.productImage,
                        productRating = content.productRating,
                        actualPrice = content.actualPrice,
                        offerPrice = content.offerPrice,
                        discount = content.discount
                    )
                }
            )
        }
        val products = homePageDao.getProductsWithContents().map {
            HomePageContent.Product(
                type = it.product.type,
                title = it.product.title,
                contents = it.contents.map { content ->
                    Content(
                        title = content.title,
                        imageUrl = content.imageUrl,
                        sku = content.sku,
                        productName = content.productName,
                        productImage = content.productImage,
                        productRating = content.productRating,
                        actualPrice = content.actualPrice,
                        offerPrice = content.offerPrice,
                        discount = content.discount
                    )
                }
            )
        }
        return bannerSliders + bannerSingles + categories + products
    }
}

