package com.mvince.compose.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp

private val customTextFieldStyle = TextStyle(
    color = Color.Black,
    fontSize = 16.sp,
    fontWeight = FontWeight.Normal,
    fontFamily = FontFamily.SansSerif
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    maxLines: Int = 1,
    singleLine: Boolean = true,
    textStyle: TextStyle = customTextFieldStyle,
    keyboardType: KeyboardType = KeyboardType.Text,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isErrorValue: Boolean = false,
    validateInput: (String) -> Boolean = { true }
) {
    OutlinedTextField(
        value = value,
        onValueChange = { newValue ->
            if (validateInput(newValue)) {
                onValueChange(newValue)
            }
        },
        textStyle = textStyle,
        label = label,
        enabled = enabled,
        readOnly = readOnly,
        maxLines = maxLines,
        singleLine = singleLine,
        visualTransformation = visualTransformation,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        isError = isErrorValue,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            errorBorderColor = MaterialTheme.colorScheme.error
        ),
        keyboardOptions = KeyboardOptions(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = keyboardType,
            imeAction = ImeAction.Next
        ),
        modifier = modifier
            .fillMaxWidth()
    )
}

