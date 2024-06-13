package com.example.machinetest.dao
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import android.content.Context

@Database(entities = [BannerSliderEntity::class,
    BannerSingleEntity::class ,
    CategoryEntity::class ,
    ProductEntity::class,
    ContentEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class) // Add this if you need type converters
abstract class HomePageDatabase : RoomDatabase() {
    abstract fun homePageDao(): HomePageDao

    companion object {
        @Volatile
        private var INSTANCE: HomePageDatabase? = null

        fun getDatabase(context: Context): HomePageDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HomePageDatabase::class.java,
                    "homepage_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
