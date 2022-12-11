package com.nadigerventures.pfa.ui.screens.setting.termandpolicy

import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.navigation.NavController

import com.nadigerventures.pfa.base.asHTML

import com.nadigerventures.pfa.ui.NavRoutes
import com.nadigerventures.pfa.ui.screens.ScreenConfig

val privacyPolicy = "\nAbout personal data:\n" +
        "\n" +
        "All the data provided to this app will be stored within the device " +
        "in the encrypted format and will be accessed only by PFA app.\n" +
        "None of the data is sent outside the device by any means.\n" +
        "\n" +
        "Below is the list of personal data which are stored in" +
        " encrypted format within the device.\n" +
        "\n" +
        "1. Full Name -> Used for displaying name in the profile page.\n\n" +
        "2. Date of birth -> Used for authentication of user to display,Password hint when user clicks forgot password in login screen.\n\n" +
        "3. Password -> Used to login in PFA app if user wants to protect his data with password to access the app.\n\n" +
        "4. Password Hint1/2 - To help user if he forgets the password in login page when user clicks forgot passwd.\n\n" +
        "   the hint will be shown after user provide the correct date of birth registered during signup.\n\n" +

        "How to delete profile data ?\n" +
        "Go to profile setting page within the app , then Press Delete button at the end of page."+
        "Below is the list of personal investment data that will be stored in the encrypted local database within the device.\n" +
        " None of the data is sent outside the device by any means.\n" +
        "\n" +
        "1. Account Number - used as Unique ID for any investment, usually provided by banks in India. \n" +
        "2. Investor name \n" +
        "3. Investment amount\n" +
        "4. Bank name - Financial institution name such as SBI , IDBI,LIC,etc\n" +
        "5. Product type - Investment Type such as FD ,NSC , Insurance,Mutual funds ,etc\n" +
        "6. Investment date \n" +
        "7. Maturity date \n" +
        "8. Nominee name \n" +
        "\n" +
        "None of the above data is sent outside of the device.\n"+
        "\n" +
        "How to delete investment records ?\n" +
        "Go to Account details page by pressing on the investment records and can press delete" +
        "Records will be moved to Recycle Bin. " +
        "In Recycle bin page you can Press Red colored button on the Recycle Bin to delete all the records permanently from device" +
        "If you want to delete only individual records in the Recycle bin , go to detail of that record by " +
        "clicking on the recode and press delete button in detail page.\n\n"+

        "It’s user/your responsibility to keep mobile phone access to keep the app secure. \n" +
        "We therefore recommend that you do not jailbreak or root your phone, " +
        "which is the process of removing software restrictions and limitations imposed by the " +
        "official operating system of your device. It could make your phone vulnerable to malware/viruses/malicious programs." +
        "Even though the data in the device is encrypted  remember that no method" +
        "  of electronic storage is 100% secure and reliable, and I cannot" +
        " guarantee its absolute security. " +
        "\n\n Below is how to delete all the data(Profile data and investment data) from the device:\n"+
        "Settings ->Apps -> PFA -> Storage ->  Clear storage . " +
        "Then all your date will be wiped out from your device. " +
        "\n Caution: All data stored in the app will be cleared, App will be same as initial state." +
        " \n\n" +
        " Contact us:"+
        "\n If you have any questions or suggestions about my Privacy Policy, " +
        "do not hesitate to contact me at nadigerventures@gmail.com."+
        "\n\nLink to this Privacy policy:" +
        "\n https://pages.flycricket.io/personal-financial-a/privacy.html"+
        "\n\nLink to Google Policy & Terms:\n" +
        "" +
        "https://policies.google.com/terms\n\n"

@Composable
private fun linkTextColor() = Color(
    TextView(LocalContext.current).linkTextColors.defaultColor
)

private const val URL_TAG = "url_tag"
@Composable
fun TermsAndPrivacyScreen(
    navigator: NavController
    ,padding: PaddingValues,

) {

    val content = "https://policies.google.com/terms".asHTML(
        14.sp,
        HtmlCompat.FROM_HTML_MODE_COMPACT,
        SpanStyle(
            color = linkTextColor(),
            textDecoration = TextDecoration.Underline
        )
    )

    Column(modifier = Modifier.background(MaterialTheme.colors.background)) {
        var linkClicked: ((String) -> Unit) = {}

        Text(
            text = privacyPolicy,
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
            //fontSize = 22.sp,
            // fontWeight = FontWeight.Bold
            //color = Color.Black
        )

        ClickableText(
            text = AnnotatedString(content.text),
            onClick = {
                content
                    .getStringAnnotations(URL_TAG, it, it)
                    .firstOrNull()
                    ?.let { stringAnnotation -> linkClicked(stringAnnotation.item) }

            })
    }
}

fun getScreenConfig4TCAndPrivacy(): ScreenConfig = //Log.e("HomeScvreen","getScreenConfig4Home");
    ScreenConfig(
        enableTopAppBar = true,
        enableBottomAppBar = true,
        enableDrawer = true,
        screenOnBackPress = NavRoutes.Home.route,
        enableFab = false,
        topAppBarStartPadding = 20.dp,
        topAppBarTitle = "Privacy Policy", bottomAppBarTitle = "",
        fabString = "",
    )