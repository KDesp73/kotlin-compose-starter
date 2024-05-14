package io.github.kdesp73.myapplication.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun CircularIconButton(
    state: MutableStateFlow<ImageVector>,
    description: String,
    bg: Color,
    size: Dp,
    action: () -> Unit
) {
    val icon by state.collectAsState()
    IconButton(
        modifier = Modifier
            .size(size),
        onClick = { action() }
    ) {
        Surface (
            shape = CircleShape,
            modifier = Modifier
                .background(bg, CircleShape)
                .size(size),
        ){
            Icon(
                modifier = Modifier
                    .background(bg)
                    .padding(5.dp),
                imageVector = icon,
                contentDescription = description
            )
        }

    }
}

@Composable
fun CircularIconButton(icon: ImageVector, description: String, bg: Color, size: Dp, action: () -> Unit){
    IconButton(
        modifier = Modifier
            .size(size),
        onClick = { action() }
    ) {
        Surface (
            shape = CircleShape,
            modifier = Modifier
                .background(bg, CircleShape)
                .size(size),
        ){
            Icon(
                modifier = Modifier
                    .background(bg)
                    .padding(5.dp),
                imageVector = icon,
                contentDescription = description
            )
        }

    }
}

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    gradient: Brush? = null,
    icon: ImageVector?,
    text: String?,
    action: () -> Unit = {}
) {
    Button(
        modifier = modifier,
        colors = colors,
        onClick = action,
        contentPadding = PaddingValues(),
    ) {
        Box(
            modifier = if(gradient != null) Modifier
                .background(gradient)
                .then(modifier) else modifier,
            contentAlignment = Alignment.Center,
        ){
            Row (
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ){
                if (icon != null) Icon(imageVector = icon, contentDescription = "icon")
                if (text != null) Text(text = text)
            }
        }
    }
}

@Composable
fun GradientButton(
    text: String,
    gradient : Brush,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    Button(
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
        contentPadding = PaddingValues(),
        onClick = { onClick() },
    ) {
        Box(
            modifier = Modifier
                .background(gradient)
                .then(modifier),
            contentAlignment = Alignment.Center,
        ) {
            Text(text = text)
        }
    }
}

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    text: String?,
    fontSize: TextUnit = 4.em,
    imageVector: ImageVector? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    action: () -> Unit
){
    Button(
        colors = colors,
        modifier = modifier,
        onClick = {
            action()
        }
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ){
            if(imageVector != null){
                Icon(modifier = Modifier.size(20.dp), imageVector = imageVector, contentDescription = text)
            }
            if(text != null){
                Text(text = text, fontSize = fontSize)
            }
        }
    }
}
