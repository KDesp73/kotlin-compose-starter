package io.github.kdesp73.myapplication.ui.routes

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.kdesp73.petadoption.ui.components.Center


@Composable
fun Home(message: String){
    Center(modifier = Modifier.fillMaxSize()){
        Text(message)
    }
}