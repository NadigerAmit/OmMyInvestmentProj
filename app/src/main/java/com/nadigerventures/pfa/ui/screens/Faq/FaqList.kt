package com.nadigerventures.pfa.ui.screens.Faq

import com.nadigerventures.pfa.utility.nod

val faqList = mutableSetOf<Pair<String,String>>()

fun gerFaqList(): List<Pair<String,String>> {

    var questionNumber:Int = 0
    faqList.add(Pair("${++questionNumber}. What is PFA app?",
        "Personal Financial Assistant app is meant for managing the personal investments such as FD(Fixed Deposit)," +
                "Insurance , SB(Saving Bank),RD(Recurring Deposit),NSC(National insurance certificate)," +
                "KVP(Kisan vikas patra),etc  .\n"))

    faqList.add(Pair("${++questionNumber}. Why PFA app ?",
        "Maturity dates of investments such as FD, RD are difficult to track, " +
                "so usually we forget the maturity dates & can't take corrective" +
                " actions such as re-investments.This may leads to financial loss." +
                "This app helps in effective management of the investments by indicating or " +
                "reminding about matured investment and to-be matured investments with different color code .\n"))

    faqList.add(Pair("${++questionNumber}. What is the meaning of color code in home screen ?",
        "Red- Already matured records w.r.t current date.\n" +
                "Magenta - About to be matured in near future (i.e maturityDate <= AdvanceNotification i.e ${nod.value} days]).\n" +
                "Black/White - To matured in far future (i.e maturityDate > AdvanceNotification i.e ${nod.value} days) \n"))

    faqList.add(Pair("${++questionNumber}. What is the meaning of each fields in HomeScreen ?",
        "Please see Setting -> DisplaySetting screen. \n"))

    faqList.add(Pair("${++questionNumber}. Is internet required for this app ?",
        "No , this app can function without internet . \n"))

    faqList.add(Pair("${++questionNumber}. Is Password mandatory for this app login ?",
        "No. It is optional.  This app can works in 2 modes:\n" +
                "1. WithoutPassword: When user opens app , user can directly see the investment screen. \n" +
                "2. WithPassword:Password is needed to access the home screen i.e see the investment \n"))

    faqList.add(Pair("${++questionNumber}. How can I set the password in later stage" +
            " If you didn't set at the signup screen during initial setting ?",
        "Go to Setting -> UserProfileSetting . \n"))

    faqList.add(Pair("${++questionNumber}. What happens if I set the password for this app ?",
        "User always need to input the password to access the app ex: home screen to see the investment. \n"))

    faqList.add(Pair("${++questionNumber}. How can I recover the password if I forget ?",
        "In login screen press ForgotPassword button .\n" +
         "Then you will be asked to input your date of birth which is set while setting the password " +
                "If DOB matched , then app will show the password hint set by the user." +
                "Based on the hint you should remember the password. " +
                "Having said that - There is no way to get the password from the app.\n"))

    faqList.add(Pair("${++questionNumber}. How can I delete the profile info if I choose to delete from the device ?",
        "Go to profile setting page within the app , then Press Delete button at the end of page.\n"))

    faqList.add(Pair("${++questionNumber}. How can I delete the investments records if choose to delete from the device ?",
        "Go to Account details page by pressing on the investment records and can press delete button " +
                " Then deleted Record will be moved to Recycle Bin. " +
                "In Recycle bin page you can Press Red colored button on the Recycle Bin to " +
                "delete all the records in Recycle bin permanently from device." +
                "If you want to delete only individual records , go to detail of that record by " +
                "clicking on the recode and press delete button in detail page.\n "))

    faqList.add(Pair("${++questionNumber}. How can I do factory rest of the app ?",
        "There is no provision for factory rest within the app , how ever you can go to" +
                " Device Settings ->Apps -> PFA -> Storage ->  Clear storage " +
                "Then all your date will be wiped out from your device.\n" +
                "Caution: All data stored in the app will be cleared, App will be same as initial state.\n"))

    faqList.add(Pair("${++questionNumber}. Can I hide some investments ?",
        "Since it is your personal device , we think you don't need this feature since access " +
                "to this app can be protected by password.\n"))

    faqList.add(Pair("${++questionNumber}. Can I change sorting order in home screen ?",
        "Yes , Sorting can be changed based on any field. Remember default sorting order is : based on maturity date\n"))

    faqList.add(Pair("${++questionNumber}. Is advance notification in terms of days is configurable ?",
        "Default advance notification is 30 days .\n" +
                "User can Change it via setting -> NotificationSetting screen. \n"))

    faqList.add(Pair("${++questionNumber}.Where does this app store my personal data ?",
        "All personal data is stored within the Device , i.e will not be sent out side the device.\n"))

    faqList.add(Pair("${++questionNumber}. Is my data safe within the device against rooted device or hackers ?",
        "Personal data such as Name , DOB , Password , passwdHint are stored in encrypted format. \n" +
                "Financial data such as AccNum, DepositAmount, MaturityAmount, Financial institution such as bank name ," +
                "MaturityDate, NomineeName are stored in encrypted database.\n" +
                "so that even if datastore or database is extracted from device ,It is difficult to understand the content.\n" +
                "Having said that please remember no method of electronic storage is 100% secure & reliable.\n"))

    faqList.add(Pair("${++questionNumber}.How and where encryption keys are stored ?",
        "Encryption keys are stored within the standard android keystore.Usually android keystore stores the keys in TEE. \n"))

    faqList.add(Pair("${++questionNumber}. Is encryption keys used are known to developer or anyone ?",
        "No, keys used to encrypt are not known to developer or anyone , all are generated dynamically per device " +
                "e.g: Key used to encrypt database are randomly generated per device.\n"))

    faqList.add(Pair("${++questionNumber}. How can I filter my investment records ?",
        "There are various options to filter or search , please see Search Investment Screen. \n"))

    faqList.add(Pair("${++questionNumber}. How can I filter my investment records ?",
        "There are various options to filter or search , please see SearchInvestment Screen \n"))

    faqList.add(Pair("${++questionNumber}. How can I reach developer of this app ?",
        "e-mail at  nadigerventures@gmail.com  \n"))

    return faqList.toList()
}