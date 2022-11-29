package com.amitnadiger.myinvestment.ui.screens.Faq

val faqList = mutableSetOf<Pair<String,String>>()

fun gerFaqList(): List<Pair<String,String>> {

    var questionNumber:Int = 0
    faqList.add(Pair("${++questionNumber}. How and where my personal data is stored within this app ?",
        "All the personal data such as Name , DOB , Password , passwdHint are stored locally within the device" +
                "in the encrypted format , so that no one can understand it.\n" +
                "Financial data such as AccNum,DepositAmount,MaturityAmount,Financial institution such as back name , " +
                "MaturityDate,NomineeName are stored in encrypted database within the device, " +
                "so that even if database is extracted from device , no one can read database.\n"))
    faqList.add(Pair("${++questionNumber}. How and where my personal data is stored within this app ?",
        "All the personal data such as Name , DOB , Password , passwdHint are stored locally within the device" +
                "in the encrypted format , so that no one can understand it.\n" +
                "Financial data such as AccNum,DepositAmount,MaturityAmount,Financial institution such as back name , " +
                "MaturityDate,NomineeName are stored in encrypted database within the device, " +
                "so that even if database is extracted from device , no one can read database.\n"))
    faqList.add(Pair("${++questionNumber}. How and where my personal data is stored within this app ?",
        "All the personal data such as Name , DOB , Password , passwdHint are stored locally within the device" +
                "in the encrypted format , so that no one can understand it.\n" +
                "Financial data such as AccNum,DepositAmount,MaturityAmount,Financial institution such as back name , " +
                "MaturityDate,NomineeName are stored in encrypted database within the device, " +
                "so that even if database is extracted from device , no one can read database.\n"))
    faqList.add(Pair("${++questionNumber}. How and where my personal data is stored within this app ?",
        "All the personal data such as Name , DOB , Password , passwdHint are stored locally within the device" +
                "in the encrypted format , so that no one can understand it.\n" +
                "Financial data such as AccNum,DepositAmount,MaturityAmount,Financial institution such as back name , " +
                "MaturityDate,NomineeName are stored in encrypted database within the device, " +
                "so that even if database is extracted from device , no one can read database.\n"))
    faqList.add(Pair("${++questionNumber}. How and where my personal data is stored within this app ?",
        "All the personal data such as Name , DOB , Password , passwdHint are stored locally within the device" +
                "in the encrypted format , so that no one can understand it.\n" +
                "Financial data such as AccNum,DepositAmount,MaturityAmount,Financial institution such as back name , " +
                "MaturityDate,NomineeName are stored in encrypted database within the device, " +
                "so that even if database is extracted from device , no one can read database.\n"))
    faqList.add(Pair("${++questionNumber}. How and where my personal data is stored within this app ?",
        "All the personal data such as Name , DOB , Password , passwdHint are stored locally within the device" +
                "in the encrypted format , so that no one can understand it.\n" +
                "Financial data such as AccNum,DepositAmount,MaturityAmount,Financial institution such as back name , " +
                "MaturityDate,NomineeName are stored in encrypted database within the device, " +
                "so that even if database is extracted from device , no one can read database.\n"))
    return faqList.toList()
}