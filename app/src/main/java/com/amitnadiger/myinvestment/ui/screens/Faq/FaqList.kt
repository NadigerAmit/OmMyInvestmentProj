package com.amitnadiger.myinvestment.ui.screens.Faq

val faqList = mutableSetOf<Pair<String,String>>()

fun gerFaqList(): List<Pair<String,String>> {

    var questionNumber:Int = 0
    faqList.add(Pair("${++questionNumber}. What is PFA app?",
        "Personal Financial Assistant app is meant for managing the personal investments such as FD(Fixed Deposit)," +
                "Insurance , SB(Saving Bank),RD(Recurring Deposit),NSC(National insurance certificate)," +
                "KVP(Kisan vikas patra),etc  .\n"))

    faqList.add(Pair("${++questionNumber}. Why we need PFA app ?",
        "Maturity dates of investments are such as FD, RD are difficult to track & take corrective" +
                " actions such as re-investments . This app helps in effective management of the investments.\n"))

    faqList.add(Pair("${++questionNumber}. What is the meaning of each fields in HomeScreen ?",
        "Please see Setting -> DisplaySetting screen. \n"))

    faqList.add(Pair("${++questionNumber}. Is internet required for this app  ?",
        "No , this app can function without internet . \n"))

    faqList.add(Pair("${++questionNumber}. Is Password is mandatory for this app login ?",
        "No. It is optional.  This app can works in 2 modes:\n" +
                "1. WithoutPassword: When user opens app , user can directly see the investment screen. \n" +
                "2. WithPassword:Password is needed to access the home screen i.e see the investment \n"))

    faqList.add(Pair("${++questionNumber}. How can set the password in later stage" +
            " if dont set at the signup screen during initial setting or vice versa ?",
        "Go to Setting -> UserProfileSetting . \n"))

    faqList.add(Pair("${++questionNumber}. What happens if I set the password for this app ?",
        "User always need to input the password to access the home screen i.e see the investment. \n"))

    faqList.add(Pair("${++questionNumber}. How can I recover the password if I forget ?",
        "In login screen press ForgotPassword button .\n" +
         "Then you will be asked to input your date of birth which is set while setting the password " +
                "If DOB matched , then app will show the password hint set by the user.\n"))

    faqList.add(Pair("${++questionNumber}. Can I hide some investments  ?",
        "As of now now , but we have plan to implement this feature. \n"))

    faqList.add(Pair("${++questionNumber}. Can I change sorting order in home screen ?",
        "No, it is fixed i.e hoem screen always sorted based on maturity dates as of now.\n"))

    faqList.add(Pair("${++questionNumber}.What is color code meaning in  home screen ?",
        "Red- Already matured records w.r.t current date.\n" +
        "Magenta - About to be matured in near future (i.e maturityDate <= AdvanceNotification[Default 30 days]).\n" +
                "Black/White - To matured in far future (i.e maturityDate > AdvanceNotification) \n"))

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
                "Having said that please remember no method\" +\n" +
                " of electronic storage is 100% secure and reliable.\n"))

    faqList.add(Pair("${++questionNumber}.How and where encryption keys are stored ?",
        "Encryption keys are stored within the standard android keystore.Usually android keystore stored the keys in TEE. \n"))
    faqList.add(Pair("${++questionNumber}. Is encryption keys used are known to developer or anyone ?",
        "No keys used to encrypt are known to developer or anyone , all are generated dynamically per device " +
                "e.g: Key used to encrypt database are randomly generated per device.\n"))

    faqList.add(Pair("${++questionNumber}. How can filter my investment records ?",
        "There are various options to filter or search , please see SearchInvestment Screen \n"))

    faqList.add(Pair("${++questionNumber}. How can filter my investment records ?",
        "There are various options to filter or search , please see SearchInvestment Screen \n"))

    return faqList.toList()
}