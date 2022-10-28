package com.amitnadiger.myinvestment.securityProvider

import kotlinx.coroutines.flow.Flow

interface IDataStore {
     suspend fun getBool(key:String): Flow<Boolean?>
     suspend fun getInt(key:String): Flow<Int?>
     suspend fun getString(key:String): Flow<String?>
     suspend fun getDouble(key:String): Flow<Double?>
     suspend fun getLong(key:String): Flow<Long?>
     suspend fun getFloat(key:String): Flow<Float?>

     suspend fun putString(key:String,value:String)
     suspend fun putInt(key:String,value:Int)
     suspend fun putLong(key:String,value:Long)
     suspend fun putBool(key:String,value:Boolean)
     suspend fun putDouble(key:String,value:Double)
     suspend fun putFloat(key:String,value:Float)
}