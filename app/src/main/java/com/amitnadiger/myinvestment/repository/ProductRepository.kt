package com.amitnadiger.myinvestment.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amitnadiger.myinvestment.room.Product
import com.amitnadiger.myinvestment.room.ProductStoreDao
import com.amitnadiger.myinvestment.room.ProductUpdate
import kotlinx.coroutines.*
import java.util.*


/*
The repository class will be responsible for interacting with the Room database on behalf of the
ViewModel and will need to provide methods that use the DAO to insert, delete and query product records.
 Except for the getAllAccounts() DAO method (which returns a LiveData object) these database operations
 will need to be performed on separate threads from the main thread.
**/

class ProductRepository(private val productStoreDao: ProductStoreDao) {
    val TAG = "ProductRepository"
    var searchResults = MutableLiveData<List<Product>>()
    //var searchResults1 = MutableLiveData<List<Product>>()
    // var accountNumberList = MutableLiveData<List<String>>()
    //var allProducts:kotlinx.coroutines.flow.Flow<List<Product>> = productStoreDao.all()
    val allProducts: LiveData<List<Product>> = productStoreDao.all()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    fun insertProduct(newProduct: Product) {
        coroutineScope.launch(Dispatchers.IO) {
            try {
                productStoreDao.insertProduct(newProduct)
            } catch (e: android.database.sqlite.SQLiteConstraintException) {
                Log.e(TAG, "Exception is caught " + e.message)
                Log.e(TAG, "Exception Type is  " + e.toString())
            }
        }
    }

    fun updateProduct(newProduct: ProductUpdate) {
        Log.e(TAG,"updateFinProduct API finished ${newProduct.investorName}")
        coroutineScope.launch(Dispatchers.IO) {
            try {
                productStoreDao.update(newProduct)
            } catch (e: android.database.sqlite.SQLiteConstraintException) {
                Log.e(TAG, "Exception is caught " + e.message)
                Log.e(TAG, "Exception Type is  " + e.toString())
            }
        }
    }

    fun deleteProduct(accountNum: Long) {
        coroutineScope.launch(Dispatchers.IO) {
            productStoreDao.deleteProduct(accountNum)
        }
    }

/*
    fun findProduct(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults1.value = asyncFind(name).await()
        }
    }

    private fun asyncFind(name: String): Deferred<List<Product>?> =
        coroutineScope.async(Dispatchers.IO) {
            Log.e("ProductRepository","fasyncFind enterd ")
            return@async productStoreDao.findProductsHavingAccountNumber(name)
    }

     fun findProductsHavingAccountNumberUsingAsync(accountNum: String) {
         Log.e("ProductRepository","findProductsHavingAccountNumberUsingAsync enterd ")
         coroutineScope.launch(Dispatchers.Main) {
             Log.e("ProductRepository","findProductsHavingAccountNumberUsingAsync enterd in co routine launch")
             val retResult = asyncFind(accountNum)
             Log.e("ProductRepository","findProductsHavingAccountNumberUsingAsync returned before await()")
             searchResults1.value = retResult.await()
             Log.e("ProductRepository","findProductsHavingAccountNumberUsingAsync returned after await()")
         }
     }
 */


    fun findProductsHavingAccountNumber(accountNum: String) {
         coroutineScope.launch(Dispatchers.Main) {
             searchResults.value = coroutineScope.async(Dispatchers.IO) {
                 return@async productStoreDao.findProductsHavingAccountNumber(accountNum)
             }.await()
         }
     }


    fun findProductsHavingAccountNumberNotEqualTo(accountNum: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingAccountNumberNotEqualTo(accountNum)
            }.await()
        }
        Log.e("ProdRepository.kt","findProductsBasedOnAccountNumber API finished ${searchResults.value}")
    }

    fun findProductsHavingFinanceInstituteName(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingFinancialInstitutionName(name)
            }.await()
        }
    }

    fun findProductsHavingFinanceInstituteNameNotEqualTo(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingFinancialInstitutionNameNotEqualTo(name)
            }.await()
        }
    }

    fun findProductsHavingProductType(productType: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingProductType(productType)
            }.await()
        }
    }

    fun findProductsHavingProductTypeNotEqualTo(productType: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingProductTypeNotEqualTo(productType)
            }.await()
        }
    }

    fun findProductsHavingInvestorName(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInvestorName(name)
            }.await()
        }
    }

    fun findProductsHavingInvestorNameNotEqualTo(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInvestorNameNotEqualTo(name)
            }.await()
        }
    }

    fun findProductsHavingNomineeName(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingNomineeName(name)
            }.await()
        }
    }

    fun findProductsHavingNomineeNameNotEqualTo(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingNomineeNameNotEqualTo(name)
            }.await()
        }
    }



      // Investment Amount
    fun findProductsHavingInvestmentAmount(investmentAmountInInt: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInvestmentAmount(investmentAmountInInt)
            }.await()
        }
    }

    fun findProductsHavingInvestmentAmountNotEqualTo(investmentAmountInInt: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInvestmentAmountNotEqualTo(investmentAmountInInt)
            }.await()
        }
    }

    fun findProductsHavingInvestmentAmountGraterThan(investmentAmountInInt: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInvestmentGraterThan(investmentAmountInInt)
            }.await()
        }
    }

    fun findProductsHavingInvestmentAmountGraterThanOrEqualTo(investmentAmountInInt: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInvestmentGraterThanOrEqualTo(investmentAmountInInt)
            }.await()
        }
    }

    fun findProductsHavingInvestmentAmountLessThanOrEqualTo(investmentAmountInInt: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInvestmentLessThanOrEqualTo(investmentAmountInInt)
            }.await()
        }
    }

    fun findProductsHavingInvestmentAmountLessThan(investmentAmountInInt: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInvestmentLessThan(investmentAmountInInt)
            }.await()
        }
    }

    // Maturity Amount
    fun findProductsHavingMaturityAmount(maturityAmountAmountInInt: Double) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingMaturityAmount(maturityAmountAmountInInt)
            }.await()
        }
    }

    fun findProductsHavingMaturityAmountNotEqualTo(maturityAmountInInt: Double) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingMaturityAmountNotEqualTo(maturityAmountInInt)
            }.await()
        }
    }

    fun findProductsHavingMaturityAmountGraterThan(maturityAmountInInt: Double) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingMaturityAmountGraterThan(maturityAmountInInt)
            }.await()
        }
    }

    fun findProductsHavingMaturityAmountGraterThanOrEqualTo(maturityAmountInInt: Double) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingMaturityAmountGraterThanOrEqualTo(maturityAmountInInt)
            }.await()
        }
    }

    fun findProductsHavingMaturityAmountLessThanOrEqualTo(maturityAmountInInt: Double) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingMaturityAmountLessThanOrEqualTo(maturityAmountInInt)
            }.await()
        }
    }

    fun findProductsHavingMaturityAmountLessThan(maturityAmountInInt: Double) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingMaturityAmountLessThan(maturityAmountInInt)
            }.await()
        }
    }

    // Maturity Date
    fun findProductsHavingMaturityDateEqualTo(maturityDate: Calendar) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingMaturityDateEqualTo(maturityDate)
            }.await()
        }
    }

    fun findProductsHavingMaturityDateNotEqualTo(maturityDate: Calendar) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingMaturityDateNotEqualTo(maturityDate)
            }.await()
        }
    }

    fun findProductsHavingMaturityDateGraterThan(maturityDate: Calendar) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingMaturityDateGraterThan(maturityDate)
            }.await()
        }
    }

    fun findProductsHavingMaturityDateGraterThanOrEqualTo(maturityDate: Calendar) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingMaturityDateGraterThanOrEqualTo(maturityDate)
            }.await()
        }
    }

    fun findProductsHavingMaturityDateLessThanOrEqualTo(maturityDate: Calendar) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingMaturityDateLessThanOrEqualTo(maturityDate)
            }.await()
        }
    }

    fun findProductsHavingMaturityDateLessThan(maturityDate: Calendar) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingMaturityDateLessThan(maturityDate)
            }.await()
        }
    }

    // Investment Date
    fun findProductsHavingInvestmentDateEqualTo(investmentDate: Calendar) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInvestmentDateEqualTo(investmentDate)
            }.await()

        }
    }

    fun findProductsHavingInvestmentDateNotEqualTo(investmentDate: Calendar) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {

                return@async productStoreDao.findProductsHavingInvestmentDateNotEqualTo(investmentDate)
            }.await()
        }
    }

    fun findProductsHavingInvestmentDateGraterThan(investmentDate: Calendar) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInvestmentDateGraterThan(investmentDate)
            }.await()
        }
    }

    fun findProductsHavingInvestmentDateGraterThanOrEqualTo(investmentDate: Calendar) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInvestmentDateGraterThanOrEqualTo(investmentDate)
            }.await()

        }
    }

    fun findProductsHavingInvestmentDateLessThanOrEqualTo(investmentDate: Calendar) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInvestmentDateLessThanOrEqualTo(investmentDate)
            }.await()

        }
    }

    fun findProductsHavingInvestmentDateLessThan(investmentDate: Calendar) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInvestmentDateLessThan(investmentDate)
            }.await()

        }
    }


    // Interest rate Amount
    fun findProductsHavingInterestRateEqualTo(InterestRate: Float) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInterestRateEqualTo(InterestRate)
            }.await()
        }
    }

    fun findProductsHavingInterestRateNotEqualTo(InterestRate: Float) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInterestRateNotEqualTo(InterestRate)
            }.await()
        }
    }

    fun findProductsHavingInterestRateGraterThan(InterestRate: Float) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInterestRateGraterThan(InterestRate)
            }.await()
        }
    }

    fun findProductsHavingInterestRateGraterThanOrEqualTo(InterestRate: Float) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInterestRateGraterThanOrEqualTo(InterestRate)
            }.await()
        }
    }

    fun findProductsHavingInterestRateLessThanOrEqualTo(InterestRate: Float) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInterestRateLessThanOrEqualTo(InterestRate)
            }.await()
        }
    }

    fun findProductsHavingInterestRateLessThan(InterestRate: Float) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingInterestRateLessThan(InterestRate)
            }.await()
        }
    }

    // Deposit Period
    fun findProductsHavingDepositPeriodEqualTo(depositPeriod: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingDepositPeriodEqualTo(depositPeriod)
            }.await()
        }
    }

    fun findProductsHavingDepositPeriodNotEqualTo(depositPeriod: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingDepositPeriodNotEqualTo(depositPeriod)
            }.await()
        }
    }

    fun findProductsHavingDepositPeriodGraterThan(depositPeriod: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingDepositPeriodGraterThan(depositPeriod)
            }.await()
        }
    }

    fun findProductsHavingDepositPeriodGraterThanOrEqualTo(depositPeriod: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingDepositPeriodGraterThanOrEqualTo(depositPeriod)
            }.await()
        }
    }

    fun findProductsHavingDepositPeriodLessThanOrEqualTo(depositPeriod: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingDepositPeriodLessThanOrEqualTo(depositPeriod)
            }.await()
        }
    }

    fun findProductsHavingDepositPeriodLessThan(depositPeriod: Int) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = coroutineScope.async(Dispatchers.IO) {
                return@async productStoreDao.findProductsHavingDepositPeriodLessThan(depositPeriod)
            }.await()
        }
    }
}