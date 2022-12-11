package com.nadigerventures.pfa.room

import android.util.Log
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "productTable")
class Product {
    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "accountNumber")
    var accountNumber: String = ""

    @NonNull
    @ColumnInfo(name = "financialInstitutionName")
    var financialInstitutionName: String = ""

    @NonNull
    @ColumnInfo(name = "productType")
    var productType: String = ""

    @NonNull
    @ColumnInfo(name = "investorName")
    var investorName : String = ""

    @NonNull
    @ColumnInfo(name = "investmentAmount")
    var investmentAmount: Int = 0

    @NonNull
    @ColumnInfo(name = "investmentDate")
    var investmentDate: Calendar = Calendar.getInstance()

    @NonNull
    @ColumnInfo(name = "maturityDate")
    var maturityDate: Calendar = Calendar.getInstance()

    @NonNull
    @ColumnInfo(name = "maturityAmount")
    var maturityAmount: Double = 0.0

    @NonNull
    @ColumnInfo(name = "interestRate")
    var interestRate: Float = 0.0F

    @NonNull
    @ColumnInfo(name = "depositPeriod")
    var depositPeriod: Int = 0

    @NonNull
    @ColumnInfo(name = "nomineeName")
    var nomineeName: String = ""

    constructor() {}

    constructor(accountNumber: String, financialInstitutionName: String,
                productType:String,investorName: String,
                investmentAmount: Int, investmentDate: Calendar, maturityDate: Calendar,
                maturityAmount: Double =0.0, interestRate: Float =0.0f, depositPeriod: Int=1,
                nomineeName: String ="") {
        this.accountNumber = accountNumber

        this.financialInstitutionName = financialInstitutionName

        this.productType = productType

        this.investorName = investorName

        this.investmentAmount = investmentAmount

        this.investmentDate = investmentDate

        this.maturityDate = maturityDate

        this.maturityAmount = maturityAmount

        this.interestRate = interestRate

        this.depositPeriod = depositPeriod

        this.nomineeName = nomineeName
        /*
        Log.i("Product","Product is added accountNumber = ${this.accountNumber}" +
                " \n financialInstitutionName +  ${this.financialInstitutionName}" +
                " \n investorName = ${this.investorName} " +
                " \ninvestmentAmount = ${this.investmentAmount}" +
                " \ninvestmentDate = ${this.investmentDate}  " +
                " \n maturityDate = ${this.maturityDate}  " +
                " \n interestRate = ${this.interestRate}  " +
                " \n depositPeriod = ${this.depositPeriod} " +
                " \n nomineeName = ${this.nomineeName} ")

         */

    }

}