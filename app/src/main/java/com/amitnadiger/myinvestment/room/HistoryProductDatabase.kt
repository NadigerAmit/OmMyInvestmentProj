package com.amitnadiger.myinvestment.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.amitnadiger.myinvestment.utility.DateConverters
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

@Database(entities = [(Product::class)], version = 1)
@TypeConverters(DateConverters::class)
abstract class HistoryProductDatabase: RoomDatabase() {

    abstract fun historyProductStoreDao(): ProductStoreDao
    companion object {
        private var INSTANCE: HistoryProductDatabase? = null

        fun getInstance(context: Context, password: String): HistoryProductDatabase {
            synchronized(this) {
                val supportFactory = SupportFactory(SQLiteDatabase.getBytes(password.toCharArray()))
                var instance = INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryProductDatabase::class.java,
                        "product_history_database.db"
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