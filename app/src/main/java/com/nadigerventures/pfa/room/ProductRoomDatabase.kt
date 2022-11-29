package com.nadigerventures.pfa.room

import android.content.Context
import android.util.Log
import androidx.room.*
import com.nadigerventures.pfa.utility.DateConverters
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory


@Database(entities = [(Product::class)], version = 1)
@TypeConverters(DateConverters::class)
abstract class ProductRoomDatabase: RoomDatabase() {

    abstract fun accountProductStoreDao(): ProductStoreDao
    companion object {
        private val TAG = "ProductRoomDatabase"
        private var INSTANCE: ProductRoomDatabase? = null

        fun getInstance(context: Context,password: String): ProductRoomDatabase {
            Log.e(TAG,"getInstance is called ")
            synchronized(this) {
                val supportFactory = SupportFactory(SQLiteDatabase.getBytes(password.toCharArray()))
                var instance = INSTANCE
                if(instance == null) {
                //    try {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            ProductRoomDatabase::class.java,
                            "Account_database.db"
                        ).fallbackToDestructiveMigration()
                            .openHelperFactory(supportFactory)
                            .build()
                 //   } catch(e:Exception) {
                 //       when(e) {
                 //           is net.sqlcipher.database.SQLiteException ->{
                 //               INSTANCE = null
                 //           }
                 //       }
                 //   }
                    Log.e(TAG,"getInstance is finished ")
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}