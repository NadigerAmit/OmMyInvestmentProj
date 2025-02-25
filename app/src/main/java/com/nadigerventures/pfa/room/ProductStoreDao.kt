package com.nadigerventures.pfa.room

import androidx.lifecycle.LiveData
import androidx.room.*
import java.util.*

/*
“Data access object” (DAO) is a fancy way of saying “the API into the data”. The idea
is that you have a DAO that provides methods for the database operations that you
need: queries, inserts, updates, deletes, whatever.

In Room, the DAO is identified by the @Dao annotation, applied to either an
abstract class or an interface. The actual concrete implementation will be codegenerated
for you by the Room annotation processor.

The primary role of the @Dao-annotated abstract class or interface is to have one
or more methods, with their own Room annotations, identifying what you want to
do with the database and your entities.
* */

@Dao
interface ProductStoreDao {
    //fun all(): Flow<List<Product>>
    @Query("SELECT * FROM productTable ORDER BY maturityDate asc")
    fun all(): LiveData<List<Product>>

    @Query("SELECT * FROM productTable ORDER BY investmentDate asc")
    fun allSortedByInvestmentDate(): LiveData<List<Product>>

    @Query("SELECT * FROM productTable ORDER BY accountNumber asc")
    fun allSortedByAccountNumber(): LiveData<List<Product>>

    @Query("SELECT * FROM productTable ORDER BY investmentAmount asc")
    fun allSortedByInvestmentAmount(): LiveData<List<Product>>

    @Query("SELECT * FROM productTable ORDER BY investorName asc")
    fun allSortedByInvestorName(): LiveData<List<Product>>

    @Query("SELECT * FROM productTable ORDER BY financialInstitutionName asc")
    fun allSortedByFinInstituteName(): LiveData<List<Product>>

    @Query("SELECT * FROM productTable ORDER BY productType asc")
    fun allSortedByProductType(): LiveData<List<Product>>

    @Query("SELECT * FROM productTable ORDER BY maturityAmount asc")
    fun allSortedByMaturityAmount(): LiveData<List<Product>>

    @Query("SELECT * FROM productTable ORDER BY interestRate asc")
    fun allSortedByInterestRate(): LiveData<List<Product>>

    @Query("SELECT * FROM productTable ORDER BY nomineeName asc")
    fun allSortedByNomineeName(): LiveData<List<Product>>

    @Query("SELECT * FROM productTable ORDER BY depositPeriod asc")
    fun allSortedByDepositPeriod(): LiveData<List<Product>>

    @Insert
    fun insertProduct(product: Product)

    @Query("SELECT * FROM productTable WHERE accountNumber = :accountNumber")
    fun findProductsHavingAccountNumber(accountNumber: String): List<Product>

    @Query("SELECT * FROM productTable WHERE accountNumber != :accountNumber")
    fun findProductsHavingAccountNumberNotEqualTo(accountNumber: String): List<Product>

    @Query("SELECT * FROM productTable WHERE financialInstitutionName = :name")
    fun findProductsHavingFinancialInstitutionName(name: String): List<Product>

    @Query("SELECT * FROM productTable WHERE financialInstitutionName != :name")
    fun findProductsHavingFinancialInstitutionNameNotEqualTo(name: String): List<Product>

    @Query("SELECT * FROM productTable WHERE productType = :name")
    fun findProductsHavingProductType(name: String): List<Product>

    @Query("SELECT * FROM productTable WHERE productType != :name")
    fun findProductsHavingProductTypeNotEqualTo(name: String): List<Product>

    @Query("SELECT * FROM productTable WHERE investorName = :name")
    fun findProductsHavingInvestorName(name: String): List<Product>

    @Query("SELECT * FROM productTable WHERE investorName != :name")
    fun findProductsHavingInvestorNameNotEqualTo(name: String): List<Product>


    @Query("SELECT * FROM productTable WHERE nomineeName = :name")
    fun findProductsHavingNomineeName(name: String): List<Product>

    @Query("SELECT * FROM productTable WHERE nomineeName != :name")
    fun findProductsHavingNomineeNameNotEqualTo(name: String): List<Product>

    /*
    @Query("SELECT * FROM productTable WHERE maturityDate== date")
    fun findProductsBasedOnMaturityDate(date: String): List<Product>
     */

    @Query("DELETE FROM productTable WHERE accountNumber = :accountNumber")
    fun deleteProduct(accountNumber: String)

    @Query("DELETE FROM productTable")
    fun deleteAllProduct()

    // InvestmentDateRelated  related queries
    @Query("SELECT * FROM productTable WHERE investmentDate = :investmentDateInCal")
    fun findProductsHavingInvestmentDateEqualTo(investmentDateInCal: Calendar): List<Product>

    @Query("SELECT * FROM productTable WHERE investmentDate != :investmentDateInCal")
    fun findProductsHavingInvestmentDateNotEqualTo(investmentDateInCal: Calendar): List<Product>

    @Query("SELECT * FROM productTable WHERE investmentDate > :investmentDateInCal")
    fun findProductsHavingInvestmentDateGraterThan(investmentDateInCal: Calendar): List<Product>

    @Query("SELECT * FROM productTable WHERE investmentDate >= :investmentDateInCal")
    fun findProductsHavingInvestmentDateGraterThanOrEqualTo(investmentDateInCal: Calendar): List<Product>

    @Query("SELECT * FROM productTable WHERE investmentDate < :investmentDateInCal")
    fun findProductsHavingInvestmentDateLessThan(investmentDateInCal: Calendar): List<Product>

    @Query("SELECT * FROM productTable WHERE investmentDate <= :investmentDateInCal")
    fun findProductsHavingInvestmentDateLessThanOrEqualTo(investmentDateInCal: Calendar): List<Product>

    // MaturityDateRelated  related queries
    @Query("SELECT * FROM productTable WHERE maturityDate = :maturityDateInCal")
    fun findProductsHavingMaturityDateEqualTo(maturityDateInCal: Calendar): List<Product>

    @Query("SELECT * FROM productTable WHERE maturityDate != :maturityDateInCal")
    fun findProductsHavingMaturityDateNotEqualTo(maturityDateInCal: Calendar): List<Product>

    @Query("SELECT * FROM productTable WHERE maturityDate > :maturityDateInCal")
    fun findProductsHavingMaturityDateGraterThan(maturityDateInCal: Calendar): List<Product>

    @Query("SELECT * FROM productTable WHERE maturityDate >= :maturityDateInCal")
    fun findProductsHavingMaturityDateGraterThanOrEqualTo(maturityDateInCal: Calendar): List<Product>

    @Query("SELECT * FROM productTable WHERE maturityDate < :maturityDateInCal")
    fun findProductsHavingMaturityDateLessThan(maturityDateInCal: Calendar): List<Product>

    @Query("SELECT * FROM productTable WHERE maturityDate <= :maturityDateInCal")
    fun findProductsHavingMaturityDateLessThanOrEqualTo(maturityDateInCal: Calendar): List<Product>

 // InvestmentAmount related queries
    @Query("SELECT * FROM productTable WHERE investmentAmount = :investmentAmountInInt")
    fun findProductsHavingInvestmentAmount(investmentAmountInInt: Double): List<Product>

    @Query("SELECT * FROM productTable WHERE investmentAmount != :investmentAmountInInt")
    fun findProductsHavingInvestmentAmountNotEqualTo(investmentAmountInInt: Double): List<Product>

    @Query("SELECT * FROM productTable WHERE investmentAmount > :investmentAmountInInt")
    fun findProductsHavingInvestmentGraterThan(investmentAmountInInt: Double): List<Product>

    @Query("SELECT * FROM productTable WHERE investmentAmount >= :investmentAmountInInt")
    fun findProductsHavingInvestmentGraterThanOrEqualTo(investmentAmountInInt: Double): List<Product>

    @Query("SELECT * FROM productTable WHERE investmentAmount < :investmentAmountInInt")
    fun findProductsHavingInvestmentLessThan(investmentAmountInInt: Double): List<Product>

    @Query("SELECT * FROM productTable WHERE investmentAmount <= :investmentAmountInInt")
    fun findProductsHavingInvestmentLessThanOrEqualTo(investmentAmountInInt: Double): List<Product>


    // MaturityAmount related queries
    @Query("SELECT * FROM productTable WHERE maturityAmount = :maturityAmountInDouble")
    fun findProductsHavingMaturityAmount(maturityAmountInDouble: Double): List<Product>

    @Query("SELECT * FROM productTable WHERE maturityAmount != :maturityAmountInDouble")
    fun findProductsHavingMaturityAmountNotEqualTo(maturityAmountInDouble: Double): List<Product>

    @Query("SELECT * FROM productTable WHERE maturityAmount > :maturityAmountInDouble")
    fun findProductsHavingMaturityAmountGraterThan(maturityAmountInDouble: Double): List<Product>

    @Query("SELECT * FROM productTable WHERE maturityAmount >= :maturityAmountInDouble")
    fun findProductsHavingMaturityAmountGraterThanOrEqualTo(maturityAmountInDouble: Double): List<Product>

    @Query("SELECT * FROM productTable WHERE maturityAmount < :maturityAmountInDouble")
    fun findProductsHavingMaturityAmountLessThan(maturityAmountInDouble: Double): List<Product>

    @Query("SELECT * FROM productTable WHERE maturityAmount <= :maturityAmountInDouble")
    fun findProductsHavingMaturityAmountLessThanOrEqualTo(maturityAmountInDouble: Double): List<Product>

    // MaturityAmount related queries
    @Query("SELECT * FROM productTable WHERE interestRate = :interestRateInInt")
    fun findProductsHavingInterestRateEqualTo(interestRateInInt: Float): List<Product>

    @Query("SELECT * FROM productTable WHERE interestRate != :interestRateInInt")
    fun findProductsHavingInterestRateNotEqualTo(interestRateInInt: Float): List<Product>

    @Query("SELECT * FROM productTable WHERE interestRate > :interestRateInInt")
    fun findProductsHavingInterestRateGraterThan(interestRateInInt: Float): List<Product>

    @Query("SELECT * FROM productTable WHERE interestRate >= :interestRateInInt")
    fun findProductsHavingInterestRateGraterThanOrEqualTo(interestRateInInt: Float): List<Product>

    @Query("SELECT * FROM productTable WHERE interestRate < :interestRateInInt")
    fun findProductsHavingInterestRateLessThan(interestRateInInt: Float): List<Product>

    @Query("SELECT * FROM productTable WHERE interestRate <= :interestRateInInt")
    fun findProductsHavingInterestRateLessThanOrEqualTo(interestRateInInt: Float): List<Product>

    // DepositPeriod related queries
    @Query("SELECT * FROM productTable WHERE depositPeriod = :depositPeriodInInt")
    fun findProductsHavingDepositPeriodEqualTo(depositPeriodInInt: Int): List<Product>

    @Query("SELECT * FROM productTable WHERE depositPeriod != :depositPeriodInInt")
    fun findProductsHavingDepositPeriodNotEqualTo(depositPeriodInInt: Int): List<Product>

    @Query("SELECT * FROM productTable WHERE depositPeriod > :depositPeriodInInt")
    fun findProductsHavingDepositPeriodGraterThan(depositPeriodInInt: Int): List<Product>

    @Query("SELECT * FROM productTable WHERE depositPeriod >= :depositPeriodInInt")
    fun findProductsHavingDepositPeriodGraterThanOrEqualTo(depositPeriodInInt: Int): List<Product>

    @Query("SELECT * FROM productTable WHERE depositPeriod < :depositPeriodInInt")
    fun findProductsHavingDepositPeriodLessThan(depositPeriodInInt: Int): List<Product>

    @Query("SELECT * FROM productTable WHERE depositPeriod <= :depositPeriodInInt")
    fun findProductsHavingDepositPeriodLessThanOrEqualTo(depositPeriodInInt: Int): List<Product>


    @Update(entity = Product::class)
    fun update(obj: ProductUpdate)

    // Need to define the query methods based on the other fields such as Maturity date ;
}