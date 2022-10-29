package com.amitnadiger.myinvestment.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amitnadiger.myinvestment.repository.ProductRepository
import com.amitnadiger.myinvestment.room.HistoryProductDatabase
import com.amitnadiger.myinvestment.room.Product
import com.amitnadiger.myinvestment.room.ProductRoomDatabase
import com.amitnadiger.myinvestment.room.ProductUpdate
import com.amitnadiger.myinvestment.securityProvider.DataStoreHolder
import com.amitnadiger.myinvestment.utility.DataStoreConst
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.math.abs



/*
Within the FinHistoryViewModel.kt file, modify the class declaration to accept an application
context instance together with some properties and an initializer block as outlined below.
 The application context, represented by the Android Context class, is used in application code
 to gain access to the application resources at runtime.
 In addition, a wide range of methods may be called on an application’s context to gather
 information and make changes to the application’s environment.
  In this case, the application context is required when creating a database and will be passed
  into the view model from within the activity later in the chapter:
* */
class FinHistoryViewModel(application: Application): ViewModel() {

    // val allAccounts:kotlinx.coroutines.flow.Flow<List<Product>>
    private val TAG = "FinHistoryViewModel"
    val allAccounts: LiveData<List<Product>>
    private val repository: ProductRepository


    init {
        val passCode = getPassCode(application.applicationContext)
        val historyProductDb = HistoryProductDatabase.getInstance(application, passCode)
        val historyProductStoreDao = historyProductDb.historyProductStoreDao()
        repository = ProductRepository(historyProductStoreDao)
        allAccounts = historyProductStoreDao.all()
    }

    private fun getPassCode(context: Context): String {
        val dataStoreProvider = DataStoreHolder.getDataStoreProvider(
            context,
            DataStoreConst.SECURE_DATASTORE, true
        )
        var passCode: String? = null
        runBlocking {
            passCode = dataStoreProvider.getString(DataStoreConst.DB_PASSCODE).first()
            Log.i(TAG, "Retrived passcode from data store  = $passCode")
        }
        return passCode!!
    }

    fun insertFinProduct(product: Product) {
        Log.i(TAG, "Inserting the Product   = ${product.investorName}")
        repository.insertProduct(product)
    }

    fun deleteFinProduct(accountNum: String) {
        repository.deleteProduct(accountNum.toLong())
    }

    fun deleteAllFromHistory() {
        repository.deleteAllProduct()
    }

    fun findProductBasedOnAccountNumberFromLocalCache(accountNum: String):Product? {
        if(allAccounts.value == null) return null
        for(i in allAccounts.value!!) {
            if(i.accountNumber == accountNum) return i
        }
        return null
    }
}
