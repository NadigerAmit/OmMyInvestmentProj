package com.nadigerventures.pfa.utility

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import androidx.navigation.NavHostController

import com.nadigerventures.pfa.ui.NavRoutes


private val TAG = "ProductDetail"
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
                .border(width = 1.dp, color = Color.Black))
        Text(head2, color = Color.White,
            modifier = Modifier
                .weight(0.2f)
                .background(MaterialTheme.colors.primary)
                .border(width = 1.dp, color = Color.Black))
        Text(head3, color = Color.White,
            modifier = Modifier
                .weight(0.2f)
                .background(MaterialTheme.colors.primary)
                .border(width = 1.dp, color = Color.Black))
    }
}

@Composable
fun SummaryRow(summaryField:String,
               summaryValue: String,
               textColor: Color = Color.Black) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)

    ) {
        //Text(summaryField,color = textColor ,modifier = Modifier
        Text(summaryField,modifier = Modifier
            .weight(0.2f)
            .border(width = 1.dp, color = MaterialTheme.colors.secondaryVariant))
        //Text(summaryField,color = textColor ,modifier = Modifier
        Text(summaryValue , modifier = Modifier
            .weight(0.2f)
            .border(width = 1.dp, color = MaterialTheme.colors.secondaryVariant))

    }
}

@Composable
fun ProductRow(accountNumber:String,
               FirstColumn: String,
               SecondColumn: String,
               ThirdColumn: String,
               navController: NavHostController,
               textColor: Color = Color.Unspecified,
               parentScreen:String,
) {

    var cellBorderWidth:Dp = .4.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 3.dp,end = 3.dp, top = 0.dp, bottom = 0.dp)
            .clickable {
                when(parentScreen) {
                    "HistoryProductDetail" -> {
                        navController.navigate(NavRoutes.HistoryProductDetail.route + "/$accountNumber") {
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            launchSingleTop = true
                        }
                    }
                    "ProductDetail" ->{
                        navController.navigate(NavRoutes.ProductDetail.route + "/$accountNumber") {
                            // Pop up to the start destination of the graph to
                            // avoid building up a large stack of destinations
                            // on the back stack as users select items
                            navController.graph.startDestinationRoute?.let { route ->
                                popUpTo(route) {
                                    saveState = true
                                }
                            }
                            // Avoid multiple copies of the same destination when
                            // reselecting the same item
                            launchSingleTop = true
                            // Restore state when reselecting a previously selected item
                            //restoreState = true
                        }
                    }
                }

            }
    ) {
        Text(FirstColumn,color = textColor ,modifier = Modifier
            .weight(0.2f)
           // .background(backgroundColor)
            .border(width = cellBorderWidth, color = MaterialTheme.colors.primary)
        )
        Text(SecondColumn ,color = textColor, modifier = Modifier
            .weight(0.2f)
            //.background(backgroundColor)
            //.border(width = cellBorderWidth)
            .border(width = cellBorderWidth, color = MaterialTheme.colors.primary)
        )
        Text(ThirdColumn ,color = textColor, modifier = Modifier
            .weight(0.2f)
            //.background(backgroundColor)
            .border(width = cellBorderWidth, color = MaterialTheme.colors.primary)
        )
    }
}

private val DropdownMenuVerticalPadding = 1.dp
@Composable
fun DropDownBox(searchFieldList:List<String>,label:String,width: Dp=0.dp,preSelectedText:String = ""):String{

    // Declaring a boolean value to store
    // the expanded state of the Text Field
    var expanded by remember { mutableStateOf(false) }



    // Create a string value to store the selected city
    var selectedText by rememberSaveable{ mutableStateOf(preSelectedText) }

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

@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    onChange: (String) -> Unit = {},
    imeAction: ImeAction = ImeAction.Next,
    keyboardType: KeyboardType = KeyboardType.Text,
    keyBoardActions: KeyboardActions = KeyboardActions(),
    isEnabled: Boolean = true,
    visualTransformationParam: VisualTransformation = VisualTransformation.None
) {
    OutlinedTextField(
        modifier = modifier,
        value = text,
        label = { Text(placeholder) },
        onValueChange = onChange,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        textStyle = TextStyle(fontSize = 18.sp),
        keyboardOptions = KeyboardOptions(imeAction = imeAction, keyboardType = keyboardType),
        keyboardActions = keyBoardActions,
        enabled = isEnabled,
        visualTransformation = visualTransformationParam,

        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colors.primary,
            unfocusedBorderColor = Color.Gray,
            disabledBorderColor = Color.Gray,
            disabledTextColor = MaterialTheme.colors.primary
        ),
    )
}




