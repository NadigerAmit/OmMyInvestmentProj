package com.nadigerventures.pfa.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.util.*

@Entity
class ProductUpdate {

    @ColumnInfo(name = "accountNumber")
    var accountNumber: String = ""

    @ColumnInfo(name = "financialInstitutionName")
    var financialInstitutionName: String = ""

    @ColumnInfo(name = "productType")
    var productType: String = ""

    @ColumnInfo(name = "investorName")
    var investorName : String = ""

    @ColumnInfo(name = "investmentAmount")
    var investmentAmount: Int = 0

    @ColumnInfo(name = "investmentDate")
    var investmentDate: Calendar = Calendar.getInstance()

    @ColumnInfo(name = "maturityDate")
    var maturityDate: Calendar = Calendar.getInstance()

    @ColumnInfo(name = "maturityAmount")
    var maturityAmount: Double = 0.0

    @ColumnInfo(name = "interestRate")
    var interestRate: Float = 0.0F

    @ColumnInfo(name = "depositPeriod")
    var depositPeriod: Int = 0

    @ColumnInfo(name = "nomineeName")
    var nomineeName: String = ""


    constructor(accountNumber: String, financialInstitutionName: String,
                productType:String,investorName: String,
                investmentAmount: Int, investmentDate: Calendar, maturityDate: Calendar,
                maturityAmount: Double =1.1, interestRate: Float =1.1f, depositPeriod: Int=1,
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

    }
}