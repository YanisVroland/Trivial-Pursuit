package com.mvince.compose.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
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
    visualTransformation: VisualTransformation = VisualTransformation.None,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    //isErrorValue: Boolean = false,
    validateInput: (String) -> Boolean = { true }
) {
    Column(
        modifier = modifier
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
            //isErrorValue = isErrorValue,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        )
        if (!validateInput(value)) {
            Text(
                text = "Invalid input",
                color = MaterialTheme.colorScheme.error,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

