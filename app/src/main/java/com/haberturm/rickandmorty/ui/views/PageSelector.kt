package com.haberturm.rickandmorty.ui.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haberturm.rickandmorty.R
import androidx.compose.ui.unit.sp
import com.haberturm.rickandmorty.ui.theme.ClickableColor
import com.haberturm.rickandmorty.ui.theme.SelectedColor


@Composable
fun PageSelector(
    currentPageNum: Int,
    lastPageNum: Int,
    pageSelectorTextValue: String,
    updatePageSelectorTextValue: (String) -> Unit,
    pageNavigationAction: (Int) -> Unit,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth(),

        ) {
        /*
        "magic const" padding because of default padding that 'label' field add to TextField".
        This* padding need for vertical alignment
        */
        Row(Modifier.padding(top = 5.dp)) {//*
            PageSelectorArrow(
                painterRes = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_left_24),
                description = ArrowIconDescription.PreviousPage().description,
                navigationAction = { pageNavigationAction(currentPageNum - 1) },
                active = currentPageNum != 1
            )
            val numOfSelectorItems = 7
            if (currentPageNum in 1..4) {
                (1..numOfSelectorItems).forEach {
                    if (it == numOfSelectorItems - 1) {
                        PageSelectorDotsItem()
                    } else if (it == numOfSelectorItems) {
                        PageSelectorDigitItem(
                            digit = lastPageNum,
                            selected = false,
                            navigationAction = { pageNavigationAction(lastPageNum) }
                        )
                    } else {
                        PageSelectorDigitItem(
                            digit = it,
                            selected = it == currentPageNum,
                            navigationAction = { pageNavigationAction(it) }
                        )
                    }
                }
            } else if (currentPageNum in lastPageNum - 4..lastPageNum) {
                PageSelectorDigitItem(
                    digit = 1,
                    selected = false,
                    navigationAction = { pageNavigationAction(1) }
                )
                PageSelectorDotsItem()
                ((lastPageNum - numOfSelectorItems + 2)..lastPageNum).forEach {
                    PageSelectorDigitItem(
                        digit = it,
                        selected = it == currentPageNum,
                        navigationAction = { pageNavigationAction(it) }
                    )
                }
            } else {
                PageSelectorDigitItem(
                    digit = 1,
                    selected = false,
                    navigationAction = { pageNavigationAction(1) }
                )
                PageSelectorDotsItem()
                (currentPageNum - 1..currentPageNum + 1).forEach {
                    PageSelectorDigitItem(
                        digit = it,
                        selected = it == currentPageNum,
                        navigationAction = { pageNavigationAction(it) }
                    )
                }
                PageSelectorDotsItem()
                PageSelectorDigitItem(
                    digit = lastPageNum,
                    selected = false,
                    navigationAction = { pageNavigationAction(lastPageNum) }
                )
            }
            PageSelectorArrow(
                painterRes = painterResource(id = R.drawable.ic_baseline_keyboard_arrow_right_24),
                description = ArrowIconDescription.NextPage().description,
                navigationAction = { pageNavigationAction(currentPageNum + 1) },
                active = currentPageNum != lastPageNum
            )
        }
        PageSelectorGoToTextField(
            textValue = pageSelectorTextValue,
            updateText = updatePageSelectorTextValue,
            navigationAction = fun(page: Int){
                 pageNavigationAction(page)
            }
        )
    }
}

@Composable
@Preview
fun PageSelectorPrev() {
    PageSelector(
        currentPageNum = 15,
        lastPageNum = 48,
        "",
        {},
        {},
    )
}

@Composable
private fun PageSelectorArrow(
    painterRes: Painter,
    description: String,
    navigationAction: () -> Unit,
    active: Boolean,
) {
    OutlinedButton(
        onClick = {
            if (active) {
                navigationAction()
            }
        },
        modifier = Modifier
            .size(dimensionResource(id = R.dimen.page_selector_item_size).value.dp)
            .padding(end = dimensionResource(id = R.dimen.page_selector_item_padding).value.dp),
        contentPadding = PaddingValues(
            start = 1.dp,
            top = 1.dp,
            end = 1.dp,
            bottom = 1.dp,
        )
    ) {
        Icon(
            painter = painterRes,
            contentDescription = description,
            tint = ClickableColor
        )
    }
}

@Composable
private fun PageSelectorDotsItem() {
    Text(
        text = "...",
        textAlign = TextAlign.Center,
        modifier = Modifier
            .padding(end = dimensionResource(id = R.dimen.page_selector_item_padding).value.dp)
            .size(20.dp),
    )
}

@Composable
private fun PageSelectorDigitItem(
    digit: Int,
    selected: Boolean,
    navigationAction: () -> Unit
) {
    OutlinedButton(
        contentPadding = PaddingValues(
            start = 1.dp,
            end = 1.dp,
        ),
        onClick = {
            navigationAction()
        },
        colors = if (selected) {
            ButtonDefaults.outlinedButtonColors(
                backgroundColor = SelectedColor,
                contentColor = ClickableColor,
            )
        } else {
            ButtonDefaults.outlinedButtonColors(
                backgroundColor = MaterialTheme.colors.background,
                contentColor = ClickableColor,
            )
        },
        modifier = Modifier
            .size(dimensionResource(id = R.dimen.page_selector_item_size).value.dp)
            .padding(end = dimensionResource(id = R.dimen.page_selector_item_padding).value.dp),
    ) {
        Text(text = digit.toString())
    }
}

@Composable
private fun PageSelectorGoToTextField(
    textValue: String,
    updateText: (String) -> Unit,
    navigationAction: (Int) -> Unit
) {
    val focusManager = LocalFocusManager.current
    OutlinedTextField(
        value = textValue,
        onValueChange = { text ->
            var hasNotDigit = false
            text.forEach {
                if(!it.isDigit()) hasNotDigit = true
            }
            if(!hasNotDigit){
                updateText(text)
            }
        },
        label = {
            Text(
                text = "Перейти к...",
                fontSize = 10.sp,
                color = SelectedColor
            )
        },
        modifier = Modifier
            .height(60.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = SelectedColor,
            unfocusedBorderColor = ClickableColor,
            cursorColor = SelectedColor,
        ),
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            focusManager.clearFocus()
            navigationAction(textValue.toInt())
        }),
    )
}

sealed class ArrowIconDescription() {
    data class NextPage(val description: String = "Next Page") : ArrowIconDescription()
    data class PreviousPage(val description: String = "Previous Page") : ArrowIconDescription()
}