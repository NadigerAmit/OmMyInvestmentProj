package com.amitnadiger.myinvestment.room

import android.content.Context
import androidx.room.*
import com.amitnadiger.myinvestment.utility.DateConverters


@Database(entities = [(Product::class)], version = 1)
@TypeConverters(DateConverters::class)
abstract class ProductRoomDatabase: RoomDatabase() {

    abstract fun accountProductStoreDao(): ProductStoreDao
    companion object {
        private var INSTANCE: ProductRoomDatabase? = null

        fun getInstance(context: Context): ProductRoomDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProductRoomDatabase::class.java,
                        "Account+database"
                    ).fallbackToDestructiveMigration()
                    .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}