package com.example.machinetest.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.TypeConverter
import com.example.machinetest.model.Content
import com.example.machinetest.model.HomePageContent
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


// Entity for Content
@Entity(
    tableName = "content_table",
    foreignKeys = [
        ForeignKey(
            entity = BannerSliderEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["id"],
            childColumns = ["parentId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["parentId"])]
)
data class ContentEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String? = null,
    val imageUrl: String? = null,
    val sku: String? = null,
    val productName: String? = null,
    val productImage: String? = null,
    val productRating: Int? = null,
    val actualPrice: String? = null,
    val offerPrice: String? = null,
    val discount: String? = null,
    val parentId: Int,
    val parentType: String // Use this to differentiate parent types in business logic
)

// Entity for BannerSlider.
@Entity(tableName = "banner_slider_table")
data class BannerSliderEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val title: String
)

// Entity for BannerSingle, does not have associated contents.
@Entity(tableName = "banner_single_table")
data class BannerSingleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val title: String,
    val imageUrl: String
)

// Entity for Category.
@Entity(tableName = "category_table")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val title: String
)

// Entity for Product.
@Entity(tableName = "product_table")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String,
    val title: String
)

@Dao
interface HomePageDao {

    // Insert methods for parent entities
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBannerSlider(bannerSlider: BannerSliderEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBannerSingle(bannerSingle: BannerSingleEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: CategoryEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: ProductEntity): Long

    // Insert method for content entity
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContent(content: ContentEntity)

    // Query methods to get parent entities along with their contents
    @Transaction
    @Query("SELECT * FROM banner_slider_table")
    fun getBannerSlidersWithContents(): List<BannerSliderWithContents>

    @Transaction
    @Query("SELECT * FROM banner_single_table")
    fun getAllBannerSingles(): List<BannerSingleEntity>

    @Transaction
    @Query("SELECT * FROM category_table")
    fun getCategoriesWithContents(): List<CategoryWithContents>

    @Transaction
    @Query("SELECT * FROM product_table")
    fun getProductsWithContents(): List<ProductWithContents>
}

// Define data classes for relations
data class BannerSliderWithContents(
    @Embedded val bannerSlider: BannerSliderEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "parentId",
        entity = ContentEntity::class
    )
    val contents: List<ContentEntity>
)

data class CategoryWithContents(
    @Embedded val category: CategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "parentId",
        entity = ContentEntity::class
    )
    val contents: List<ContentEntity>
)

data class ProductWithContents(
    @Embedded val product: ProductEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "parentId",
        entity = ContentEntity::class
    )
    val contents: List<ContentEntity>
)

class Converters {
    @TypeConverter
    fun fromContentList(value: List<Content>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Content>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toContentList(value: String): List<Content> {
        val gson = Gson()
        val type = object : TypeToken<List<Content>>() {}.type
        return gson.fromJson(value, type)
    }
}