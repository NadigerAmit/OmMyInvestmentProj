package com.amitnadiger.myinvestment.utility

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController
import com.amitnadiger.myinvestment.ui.NavRoutes


fun truncateString(input:String):String {
    // Log.e("Truncate","inputString - $input")
    val allowedStringLen  = 10
    var outPutString:String = input
    if(input.length>=(allowedStringLen-1)) {
        outPutString = input.subSequence(input.length-(allowedStringLen-1),input.length).toString()
        outPutString="*"+outPutString
    }
    return " $outPutString"
}

@Composable
fun TitleRow(head1: String, head2: String, head3: String) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colors.background)
            .fillMaxWidth()
            .padding(5.dp)
            .border(width = 2.dp, color = Color.Black)


    ) {
        Text(head1, color = Color.White,
            modifier = Modifier
                .weight(0.2f)
                .background(MaterialTheme.colors.primary)
                .border(width = 1.dp, color = Color.Gray))
        Text(head2, color = Color.White,
            modifier = Modifier
                .weight(0.2f)
                .background(MaterialTheme.colors.primary)
                .border(width = 1.dp, color = Color.Gray))
        Text(head3, color = Color.White,
            modifier = Modifier
                .weight(0.2f)
                .background(MaterialTheme.colors.primary)
                .border(width = 1.dp, color = Color.Gray))
    }
}

@Composable
fun ProductRow(accountNumber:String,
               FirstColumn: String,
               SecondColumn: String,
               ThirdColumn: String,
               navController: NavHostController,
               textColor: Color = Color.Black
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clickable {
                Log.e("Home", "Jai shree Ram Row is clicked , AccountNumber = $accountNumber ")
                navController.navigate(NavRoutes.ProductDetail.route + "/$accountNumber")
            }
    ) {
        Text(FirstColumn,color = textColor ,modifier = Modifier
            .weight(0.2f)
            .border(width = 1.dp, color = Color.LightGray))
        Text(SecondColumn ,color = textColor, modifier = Modifier
            .weight(0.2f)
            .border(width = 1.dp, color = Color.LightGray))
        Text(ThirdColumn ,color = textColor, modifier = Modifier
            .weight(0.2f)
            .border(width = 1.dp, color = Color.LightGray))
        //   Text(InvestorDetail, modifier = Modifier.weight(0.2f).border(width = 1.dp, color = Color.Black))
    }
}

@Composable
fun DropDownBox(searchFieldList:List<String>,label:String,width: Dp=0.dp):String{

    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var expanded by remember { mutableStateOf(false) }



    // Create a string value to store the selected city
    var selectedText by rememberSaveable{ mutableStateOf("") }

    var textFieldSize by remember { mutableStateOf(Size.Zero) }

    // Up Icon when expanded and down icon when collapsed
    val icon = if (expanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(Modifier.padding(1.dp)) {

        // Create an Outlined Text Field
        // with icon and not expanded
        var rowSize by remember { mutableStateOf(Size.Zero) }
        Box(modifier = Modifier.width(width)) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                modifier = Modifier
                    .onGloballyPositioned { layoutCoordinates  ->
                        // This value is used to assign to
                        // the DropDown the same width
                        rowSize = layoutCoordinates.size.toSize()
                        textFieldSize = layoutCoordinates.size.toSize()
                    },
                label = {Text(label)},
                trailingIcon = {
                    Icon(icon,"contentDescription",
                        Modifier.clickable { expanded = !expanded })
                }
            )
        }

        // Size calculation starts here
        val itemHeights = remember { mutableStateMapOf<Int, Int>() }
        val baseHeight = 330.dp
        val density = LocalDensity.current
        val maxHeight = remember(itemHeights.toMap()) {
            if (itemHeights.keys.toSet() != searchFieldList.indices.toSet()) {
                // if we don't have all heights calculated yet, return default value
                return@remember baseHeight
            }
            val baseHeightInt = with(density) { baseHeight.toPx().toInt() }

            // top+bottom system padding
            var sum = with(density) { DropdownMenuVerticalPadding.toPx().toInt() } * 2
            for ((i, itemSize) in itemHeights.toSortedMap()) {
                sum += itemSize
                if (sum >= baseHeightInt) {
                    return@remember with(density) { (sum - itemSize / 2).toDp() }
                }
            }
            // all items fit into base height
            baseHeight
        }
        // Size calculation Ends here


        // Create a drop-down menu with list of cities,
        // when clicked, set the Text Field text as the city selected
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
                .requiredSizeIn(maxHeight = maxHeight)

        ) {
            searchFieldList.forEachIndexed {  index,label ->
                DropdownMenuItem(onClick = {
                    selectedText = label
                    expanded = false
                },modifier = Modifier.onSizeChanged {
                    itemHeights[index] = it.height
                }) {
                    Text(text = label)
                }
            }
        }
    }
    return selectedText
}

private val DropdownMenuVerticalPadding = 1.dp
@Composable
fun getHeightOfDropDown(searchFieldList:List<String>) {
    var expanded by remember { mutableStateOf(true) }
    //val items = List(10) { it.toString() }
    val itemHeights = remember { mutableStateMapOf<Int, Int>() }
    val baseHeight = 330.dp
    val density = LocalDensity.current
    val maxHeight = remember(itemHeights.toMap()) {
        if (itemHeights.keys.toSet() != searchFieldList.indices.toSet()) {
            // if we don't have all heights calculated yet, return default value
            return@remember baseHeight
        }
        val baseHeightInt = with(density) { baseHeight.toPx().toInt() }

        // top+bottom system padding
        var sum = with(density) { DropdownMenuVerticalPadding.toPx().toInt() } * 2
        for ((i, itemSize) in itemHeights.toSortedMap()) {
            sum += itemSize
            if (sum >= baseHeightInt) {
                return@remember with(density) { (sum - itemSize / 2).toDp() }
            }
        }
        // all items fit into base height
        baseHeight
    }

}


