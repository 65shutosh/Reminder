package com.social.talk.otp

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Preview
@Composable
private fun preview() {
    SinglePinView()
}


@Composable
fun PinView() {
    var code = "";
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
    ) {

        val a = SinglePinView()
        val b = SinglePinView()
        val c = SinglePinView()
        val d = SinglePinView()
        val e = SinglePinView()
        val f = SinglePinView()
    }
}

@Preview
@Composable
fun PreviewSimplePinEntryView() {
    SimplePinEntryView()
}

@Composable
fun SimplePinEntryView(): String {
    var text by remember { mutableStateOf("") }
    TextField(
        value = text, onValueChange = {
            if (it.length <= 6) {
                text = it
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = White,
            unfocusedIndicatorColor = Gray,
            focusedIndicatorColor = Gray
        ), modifier = Modifier
            .fillMaxWidth()
            .padding(start = 100.dp, end = 100.dp),
        visualTransformation = PasswordVisualTransformation()
    )
    return text
}

@Composable
fun message(code: String) {
    val context = LocalContext.current;
    Toast.makeText(context, code, Toast.LENGTH_SHORT).show()
}

@Composable
fun SinglePinView(): String {
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf("") }
    val maxChar = 1

    Column(
        //  Modifier.background(DarkGray),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = text,
            onValueChange = {
                if (it.length <= maxChar) {
                    text = it
                    focusManager.moveFocus(focusDirection = FocusDirection.Next)
                }
            },
            modifier = Modifier.width(50.dp),
            textStyle = LocalTextStyle.current.copy(
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            ),
            colors = TextFieldDefaults.textFieldColors(
                textColor = Black,
                backgroundColor = White,
                unfocusedIndicatorColor = Gray,
                focusedIndicatorColor = Transparent
            ),
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            )
        )
        Divider(
            Modifier
                .width(28.dp)
                .padding(bottom = 2.dp)
                .offset(y = -10.dp),
            color = Black,
            thickness = 1.dp
        )
    }
    return text;
}

