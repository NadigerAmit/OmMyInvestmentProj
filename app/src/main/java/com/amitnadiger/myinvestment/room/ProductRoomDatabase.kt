package com.amitnadiger.myinvestment.room

import android.content.Context
import androidx.room.*
import com.amitnadiger.myinvestment.utility.DateConverters
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory


@Database(entities = [(Product::class)], version = 1)
@TypeConverters(DateConverters::class)
abstract class ProductRoomDatabase: RoomDatabase() {

    abstract fun accountProductStoreDao(): ProductStoreDao
    companion object {
        private var INSTANCE: ProductRoomDatabase? = null

        fun getInstance(context: Context,password: String): ProductRoomDatabase {
            synchronized(this) {
                val supportFactory = SupportFactory(SQLiteDatabase.getBytes(password.toCharArray()))
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        ProductRoomDatabase::class.java,
                        "Account_database.db"
                    ).fallbackToDestructiveMigration()
                        .openHelperFactory(supportFactory)
                    .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}