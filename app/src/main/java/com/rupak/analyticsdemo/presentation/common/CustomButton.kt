package com.rupak.analyticsdemo.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerIcon.Companion.Text
import androidx.compose.ui.unit.dp
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color

@Composable
fun CustomButton(onClick:()->Unit ,text :String,textColor: Color){
    Button(onClick={
        onClick.invoke()
    }, modifier = Modifier.padding(10.dp)) {
        Text( text = text,color = textColor)
    }
}
