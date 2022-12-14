package com.nadigerventures.pfa.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nadigerventures.pfa.repository.ProductRepository
import com.nadigerventures.pfa.room.Product
import com.nadigerventures.pfa.room.ProductRoomDatabase
import com.nadigerventures.pfa.room.ProductUpdate
import com.nadigerventures.pfa.securityProvider.DataStoreHolder
import com.nadigerventures.pfa.ui.screens.dateFormat
import com.nadigerventures.pfa.utility.DataStoreConst
import com.nadigerventures.pfa.utility.DateUtility
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*
import kotlin.math.abs

/*
Within the FinProductViewModel.kt file, modify the class declaration to accept an application
context instance together with some properties and an initializer block as outlined below.
 The application context, represented by the Android Context class, is used in application code
 to gain access to the application resources at runtime.
 In addition, a wide range of methods may be called on an application’s context to gather
 information and make changes to the application’s environment.
  In this case, the application context is required when creating a database and will be passed
  into the view model from within the activity later in the chapter:
* */
class FinProductViewModel(application: Application): ViewModel() {

   // val allAccounts:kotlinx.coroutines.flow.Flow<List<Product>>
   private val TAG = "FinProductViewModel"
    val allAccounts: LiveData<List<Product>>
    private val repository: ProductRepository
    val searchResults: MutableLiveData<List<Product>>

    init {
        val passCode = getPassCode(application.applicationContext)

        val finProductDb = ProductRoomDatabase.getInstance(application,passCode)
      //  Log.i(TAG,"ProductRoomDatabase.getInstance done  = ")
        val finProductStoreDao = finProductDb.accountProductStoreDao()
        repository = ProductRepository(finProductStoreDao)
        allAccounts = finProductStoreDao.all()
        searchResults = repository.searchResults
    }

    private fun getPassCode(context: Context):String {
        val dataStoreProvider = DataStoreHolder.getDataStoreProvider(context,
            DataStoreConst.SECURE_DATASTORE,true)
        var isRegistrationComplete:Boolean? = null
        var passCode:String? = null
        runBlocking {
            passCode = dataStoreProvider.getString(DataStoreConst.DB_PASSCODE).first()
          //  Log.i(TAG,"Retrived passcode from data store  = $passCode")
        }

        if(passCode == null) {
            // is registration Done
            passCode = UUID.randomUUID().toString()+abs((0..999999999999).random()).toString()
           // Log.i(TAG,"Generated new passcode = $passCode")
            runBlocking {
                dataStoreProvider.putString(DataStoreConst.DB_PASSCODE,passCode!!)
            }
        }
        return passCode!!
    }

    fun insertFinProduct(product: Product) {
        repository.insertProduct(product)
    }

    fun updateFinProduct(productUpdate: ProductUpdate) {
       // Log.e("FinProductViewModel.kt","updateFinProduct API finished ${productUpdate.investorName}")
        repository.updateProduct(productUpdate)
        for(i in allAccounts.value!!) {
            if(i.accountNumber == productUpdate.accountNumber) {
                i.investorName = productUpdate.investorName
                i.financialInstitutionName = productUpdate.financialInstitutionName
                i.productType = productUpdate.productType
                i.investmentAmount = productUpdate.investmentAmount
                i.investmentDate = productUpdate.investmentDate
                i.maturityDate = productUpdate.maturityDate
                i.maturityAmount = productUpdate.maturityAmount
                i.interestRate = productUpdate.interestRate
                i.nomineeName = productUpdate.nomineeName
                break
            }
        }
    }

    fun deleteFinProduct(accountNum: String) {
        repository.deleteProduct(accountNum)
    }

    fun findProductBasedOnAccountNumberFromLocalCache(accountNum: String):Product? {
        if(allAccounts.value == null) return null
        for(i in allAccounts.value!!) {
            if(i.accountNumber == accountNum) return i
        }
        return null
    }

    fun findProductsBasedOnFinanceInstituteName(financeInstituteName: String,operation:String) {
        when (operation) {
            "=" -> {
                repository.findProductsHavingFinanceInstituteName(financeInstituteName)
            }
            "!=" -> {
                repository.findProductsHavingFinanceInstituteNameNotEqualTo(financeInstituteName)
            }
        }
    }

    fun findProductsBasedOnAccountNumber(accountNum: String,operation:String="=") {
        when (operation) {
            "=" -> {
                repository.findProductsHavingAccountNumber(accountNum)
            }
            "!=" -> {
                repository.findProductsHavingAccountNumberNotEqualTo(accountNum)
            }
        }
    }

    fun findProductsBasedOnProductType(productType: String,operation:String) {
        when(operation) {
            "=" -> {
                repository.findProductsHavingProductType(productType)
            }
            "!=" -> {
                repository.findProductsHavingProductTypeNotEqualTo(productType)
            }
        }
    }


    fun findProductsBasedOnInvestorName(name: String,operation:String) {

        when(operation) {
            "=" -> {
                repository.findProductsHavingInvestorName(name)
            }
            "!=" -> {
                repository.findProductsHavingInvestorNameNotEqualTo(name)
            }
        }
       // Log.e("FinProductViewModel.kt","findProductsBasedOnInvestorName API finished ${searchResults.value}")
    }

    fun findProductsBasedOnNomineeName(name: String,operation:String) {
        when(operation) {
            "=" -> {
                repository.findProductsHavingNomineeName(name)
            }
            "!=" -> {
                repository.findProductsHavingNomineeNameNotEqualTo(name)
            }
        }

        //Log.e("FinProductViewModel.kt","findProductsBasedOnNomineeName API finished ${searchResults.value}")
    }

    //
    fun findProductsHavingInvestmentAmount(investmentAmount: String,
                                            operation:String) {
        val investmentAmountInInt = investmentAmount.toDouble()
       // Log.e("FinProductViewModel.kt","investmentAmountInString = $investmentAmount")
       // Log.e("FinProductViewModel.kt","investmentAmountInInt = $investmentAmountInInt")
        when(operation) {
            "=" -> {
                repository.findProductsHavingInvestmentAmount(investmentAmountInInt)
            }
            "!=" -> {
                repository.findProductsHavingInvestmentAmountNotEqualTo(investmentAmountInInt)
            }
            ">=" -> {
                repository.findProductsHavingInvestmentAmountGraterThanOrEqualTo(investmentAmountInInt)
            }
            ">" -> {
                repository.findProductsHavingInvestmentAmountGraterThan(investmentAmountInInt)
            }
            "<" -> {
                repository.findProductsHavingInvestmentAmountLessThan(investmentAmountInInt)
            }
            "<=" -> {
                repository.findProductsHavingInvestmentAmountLessThanOrEqualTo(investmentAmountInInt)
            }
        }
    }

    fun findProductsHavingMaturityAmount(maturityAmount: String,
                                           operation:String) {
        val maturityAmountInInt = maturityAmount.replace(",", "").toDouble()
       // Log.e("FinProductViewModel.kt","investmentAmountInString = $maturityAmount")
       // Log.e("FinProductViewModel.kt","investmentAmountInInt = $maturityAmountInInt")
        when(operation) {
            "=" -> {
                repository.findProductsHavingMaturityAmount(maturityAmountInInt)
            }
            "!=" -> {
                repository.findProductsHavingMaturityAmountNotEqualTo(maturityAmountInInt)
            }
            ">=" -> {
                repository.findProductsHavingMaturityAmountGraterThanOrEqualTo(maturityAmountInInt)
            }
            ">" -> {
                repository.findProductsHavingMaturityAmountGraterThan(maturityAmountInInt)
            }
            "<" -> {
                repository.findProductsHavingMaturityAmountLessThan(maturityAmountInInt)
            }
            "<=" -> {
                repository.findProductsHavingMaturityAmountLessThanOrEqualTo(maturityAmountInInt)
            }
        }
    }

    fun findProductsHavingMaturityDate(maturityDate: String,
                                         operation:String) {
        val maturityDateInCalendar = DateUtility.getCalendar(maturityDate,dateFormat)
        //Log.e("FinProductViewModel.kt","maturityDate = $maturityDate")
       // Log.e("FinProductViewModel.kt","maturityDateInCalendar = $maturityDateInCalendar")
        when(operation) {
            "=" -> {
                repository.findProductsHavingMaturityDateEqualTo(maturityDateInCalendar)
            }
            "!=" -> {
                repository.findProductsHavingMaturityDateNotEqualTo(maturityDateInCalendar)
            }
            ">=" -> {
                repository.findProductsHavingMaturityDateGraterThanOrEqualTo(maturityDateInCalendar)
            }
            ">" -> {
                repository.findProductsHavingMaturityDateGraterThan(maturityDateInCalendar)
            }
            "<" -> {
                repository.findProductsHavingMaturityDateLessThan(maturityDateInCalendar)
            }
            "<=" -> {
                repository.findProductsHavingMaturityDateLessThanOrEqualTo(maturityDateInCalendar)
            }
        }
    }

    fun findProductsHavingInvestmentDate(investmentDate: String,
                                       operation:String) {
        val investmentDateInCalendar = DateUtility.getCalendar(investmentDate,dateFormat)
        //Log.e("FinProductViewModel.kt","maturityDate = $maturityDate")
        // Log.e("FinProductViewModel.kt","maturityDateInCalendar = $maturityDateInCalendar")
        when(operation) {
            "=" -> {
                repository.findProductsHavingInvestmentDateEqualTo(investmentDateInCalendar)
            }
            "!=" -> {
                repository.findProductsHavingInvestmentDateNotEqualTo(investmentDateInCalendar)
            }
            ">=" -> {
                repository.findProductsHavingInvestmentDateGraterThanOrEqualTo(investmentDateInCalendar)
            }
            ">" -> {
                repository.findProductsHavingInvestmentDateGraterThan(investmentDateInCalendar)
            }
            "<" -> {
                repository.findProductsHavingInvestmentDateLessThan(investmentDateInCalendar)
            }
            "<=" -> {
                repository.findProductsHavingInvestmentDateLessThanOrEqualTo(investmentDateInCalendar)
            }
        }
    }



    fun findProductsHavingInterestRate(interestRate: String,
                                         operation:String) {
        val interestRate = interestRate.toFloat()

        when(operation) {
            "=" -> {
                repository.findProductsHavingInterestRateEqualTo(interestRate)
            }
            "!=" -> {
                repository.findProductsHavingInterestRateNotEqualTo(interestRate)
            }
            ">=" -> {
                repository.findProductsHavingInterestRateGraterThanOrEqualTo(interestRate)
            }
            ">" -> {
                repository.findProductsHavingInterestRateGraterThan(interestRate)
            }
            "<" -> {
                repository.findProductsHavingInterestRateLessThan(interestRate)
            }
            "<=" -> {
                repository.findProductsHavingInterestRateLessThanOrEqualTo(interestRate)
            }
        }
    }

    private fun getDaysFromString(str:String):String {
        var endIndex:Int = str.length-" Days".length

        return str.subSequence(0,endIndex).toString()
    }

    fun findProductsHavingDepositPeriod(depositPeriod: String,
                                       operation:String) {

        val depositPeriodInInt = getDaysFromString(depositPeriod).toInt()
       // Log.e("DepositPeriod","$depositPeriodInInt")
        when(operation) {
            "=" -> {
                repository.findProductsHavingDepositPeriodEqualTo(depositPeriodInInt)
            }
            "!=" -> {
                repository.findProductsHavingDepositPeriodNotEqualTo(depositPeriodInInt)
            }
            ">=" -> {
                repository.findProductsHavingDepositPeriodGraterThanOrEqualTo(depositPeriodInInt)
            }
            ">" -> {
                repository.findProductsHavingDepositPeriodGraterThan(depositPeriodInInt)
            }
            "<" -> {
                repository.findProductsHavingDepositPeriodLessThan(depositPeriodInInt)
            }
            "<=" -> {
                repository.findProductsHavingDepositPeriodLessThanOrEqualTo(depositPeriodInInt)
            }
        }
    }



    fun deleteProduct(accountNum: String) {
        repository.deleteProduct(accountNum)
    }
}