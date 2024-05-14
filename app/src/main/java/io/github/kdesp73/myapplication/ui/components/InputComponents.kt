package io.github.kdesp73.myapplication.ui.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import io.github.kdesp73.myapplication.ui.utils.DecimalFormatter
import io.github.kdesp73.myapplication.ui.utils.DecimalInputVisualTransformation
import kotlinx.coroutines.flow.MutableStateFlow
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun SelectImage(
    trigger: @Composable (action: () -> Unit) -> Unit,
    imageContainer: @Composable (image: Uri?) -> Unit
){
    var imageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    imageContainer(imageUri)
    trigger { launcher.launch("image/*") }
}


@Composable
fun SelectImage(
    defaultUri: Uri?,
    containerButton: @Composable (action: () -> Unit, image: Uri?) -> Unit,
){
    var imageUri by remember {
        mutableStateOf<Uri?>(defaultUri)
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        imageUri = uri
    }
    containerButton({ launcher.launch("image/*") }, imageUri)
}

enum class Orientation {
    HORIZONTAL, VERTICAL
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalLayoutApi::class)
@Composable
fun OptionPicker(
    modifier: Modifier = Modifier,
    value: String,
    options: List<String>,
    orientation: Orientation,
    width: Dp?,
    onChange: (String) -> Unit = {}
) {
    @Composable
    fun Options(width: Dp?){
        val optionModifier = if(width != null) Modifier.width(width) else Modifier
        options.forEach { option ->
            Button(
                modifier = optionModifier,
                shape = RectangleShape,
                colors =
                if(value == option)
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                else
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.Black
                    ),
                onClick = {
                    onChange(option)
                }
            ) {
                Text(text = option)
            }
        }
    }

    when(orientation){
        Orientation.HORIZONTAL -> {
            FlowRow (modifier = modifier){
                Options(null)
            }
        }
        Orientation.VERTICAL -> {
            FlowColumn (
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ){
                Options(width)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OptionPicker(
    modifier: Modifier = Modifier,
    state: MutableStateFlow<String>,
    options: List<String>,
    orientation: Orientation,
    width: Dp?,
) {
    val opt by state.collectAsState()
    @Composable
    fun Options(width: Dp?){
        val optionModifier = if(width != null) Modifier.width(width) else Modifier
        options.forEach { option ->
            Button(
                modifier = optionModifier,
                shape = RectangleShape,
                colors =
                if(opt == option)
                    ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                else
                    ButtonDefaults.buttonColors(
                        containerColor = Color.Gray,
                        contentColor = Color.Black
                    ),
                onClick = {
                    state.value = option
                }
            ) {
                Text(text = option)
            }
        }
    }

    when(orientation){
        Orientation.HORIZONTAL -> {
            FlowRow (modifier = modifier){
                Options(null)
            }
        }
        Orientation.VERTICAL -> {
            FlowColumn (
                modifier = modifier,
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ){
                Options(width)
            }
        }
    }

}

@Composable
fun CheckboxComponent(
    state: MutableStateFlow<Boolean> = MutableStateFlow(false),
    onCheckedChange: (Boolean) -> Unit = {},
    text: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(56.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        val checked by state.collectAsState()
        Checkbox(checked = checked,
            onCheckedChange = {
                state.value = !state.value
                onCheckedChange.invoke(it)
            })

        text()
    }
}

enum class TextFieldType {
    NORMAL, OUTLINED
}

@Composable
fun DecimalFieldComponent(
    state : MutableStateFlow<String> = MutableStateFlow(""),
    labelValue: String,
    icon: ImageVector,
    iconDescriptor: String = "Text field icon",
    type: TextFieldType = TextFieldType.NORMAL,
    isError: Boolean = false,
){
    val decimalFormatter = DecimalFormatter(symbols = DecimalFormatSymbols(Locale.US))
    val text by state.collectAsState()
    val iconModifier = Modifier.size(20.dp)

    when (type) {
        TextFieldType.NORMAL -> {
            TextField(
                isError = isError,
                value = text.toString(),
                onValueChange = { state.value = decimalFormatter.cleanup(it) },
                label = { Text(labelValue) },
                leadingIcon = { Icon(modifier = iconModifier, imageVector = icon, contentDescription = iconDescriptor) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                visualTransformation = DecimalInputVisualTransformation(decimalFormatter)
            )
        }
        TextFieldType.OUTLINED -> {
            OutlinedTextField(
                isError = isError,
                value = text.toString(),
                onValueChange = { state.value = decimalFormatter.cleanup(it) },
                label = { Text(labelValue) },
                leadingIcon = { Icon(modifier = iconModifier, imageVector = icon, contentDescription = iconDescriptor) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                visualTransformation = DecimalInputVisualTransformation(decimalFormatter)
            )
        }
    }
}

@Composable
fun Dropdown(
    modifier: Modifier = Modifier,
    state: MutableStateFlow<String> = MutableStateFlow(""),
    defaultExpanded: Boolean = false,
    title: String,
    showTitle: Boolean = true,
    fontSize: TextUnit = 4.em,
    items: List<String>
){
    val opt by state.collectAsState()
    var expanded by remember { mutableStateOf(defaultExpanded) }

    Column(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        if(showTitle)
            Text(text = title)

        IconButton (
            modifier = modifier,
            text = opt,
            fontSize = fontSize,
            imageVector = if(!expanded) Icons.Filled.KeyboardArrowDown else Icons.Filled.KeyboardArrowUp
        ){
            expanded = !expanded
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            offset = DpOffset(x = 25.dp, y = 5.dp)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item) },
                    onClick = {
                        expanded = false
                        state.value = item
                    }
                )
            }

        }
    }
}

@Composable
fun EmailFieldComponent(
    state: MutableStateFlow<String> = MutableStateFlow(""),
    labelValue: String,
    icon: ImageVector,
    iconDescriptor: String = "Text field icon",
    type: TextFieldType = TextFieldType.NORMAL,
    isError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    onValueChange: () -> Unit = {}
) {
    val text by state.collectAsState()
    val error by isError.collectAsState()
    val iconModifier = Modifier.size(20.dp)

    when (type) {
        TextFieldType.NORMAL -> {
            TextField(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                isError = error,
                placeholder = { Text("john@doe.xyz") },
                value = text,
                onValueChange = {
                    state.value = it
                    onValueChange()
                },
                label = { Text(labelValue) },
                leadingIcon = { Icon(modifier = iconModifier, imageVector = icon, contentDescription = iconDescriptor) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }
        TextFieldType.OUTLINED -> {
            OutlinedTextField(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                isError = error,
                placeholder = { Text("john@doe.xyz") },
                value = text,
                onValueChange = {
                    state.value = it
                    onValueChange()
                },
                label = { Text(labelValue) },
                leadingIcon = { Icon(modifier = iconModifier, imageVector = icon, contentDescription = iconDescriptor) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
            )
        }
    }
}

@Composable
fun NumberFieldComponent(
    state : MutableStateFlow<String> = MutableStateFlow(""),
    labelValue: String,
    icon: ImageVector,
    iconDescriptor: String = "Text field icon",
    type: TextFieldType = TextFieldType.NORMAL,
    isError: Boolean = false,
){
    val text by state.collectAsState()
    val iconModifier = Modifier.size(20.dp)

    when (type) {
        TextFieldType.NORMAL -> {
            TextField(
                isError = isError,
                value = text,
                onValueChange = { state.value = it },
                label = { Text(labelValue) },
                leadingIcon = { Icon(modifier = iconModifier, imageVector = icon, contentDescription = iconDescriptor) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        TextFieldType.OUTLINED -> {
            OutlinedTextField(
                isError = isError,
                value = text,
                onValueChange = { state.value = it },
                label = { Text(labelValue) },
                leadingIcon = { Icon(modifier = iconModifier, imageVector = icon, contentDescription = iconDescriptor) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
    }
}

@Composable
fun PasswordTextFieldComponent(
    state: MutableStateFlow<String> = MutableStateFlow(""),
    labelValue: String,
    icon: ImageVector,
    iconDescriptor: String = "Text field icon",
    type: TextFieldType = TextFieldType.NORMAL,
    isError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    onValueChanged: () -> Unit = {}
) {
    val password by state.collectAsState()
    val error by isError.collectAsState()
    val iconModifier = Modifier.size(20.dp)

    when (type) {
        TextFieldType.NORMAL -> {
            TextField(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                isError = error,
                value = password,
                onValueChange = {
                    state.value = it
                    onValueChanged()
                },
                label = { Text(labelValue) },
                leadingIcon = { Icon(modifier = iconModifier, imageVector = icon, contentDescription = iconDescriptor) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }

        TextFieldType.OUTLINED -> {
            OutlinedTextField(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                isError = error,
                value = password,
                onValueChange = {
                    state.value = it
                    onValueChanged()
                },
                label = { Text(labelValue) },
                leadingIcon = { Icon(modifier = iconModifier, imageVector = icon, contentDescription = iconDescriptor) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
    }
}

@Composable
fun StarRatingBar(
    maxStars: Int = 5,
    rating: Float,
    onRatingChanged: (Float) -> Unit
) {
    val density = LocalDensity.current.density
    val starSize = (12f * density).dp
    val starSpacing = (0.5f * density).dp

    Row(
        modifier = Modifier.selectableGroup(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 1..maxStars) {
            val isSelected = i <= rating
            val icon = if (isSelected) Icons.Filled.Star else Icons.Default.Star
            val iconTintColor = if (isSelected) Color(0xFFFFC700) else Color(0x20FFFFFF)
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconTintColor,
                modifier = Modifier
                    .selectable(
                        selected = isSelected,
                        onClick = {
                            onRatingChanged(i.toFloat())
                        }
                    )
                    .width(starSize).height(starSize)
            )

            if (i < maxStars) {
                Spacer(modifier = Modifier.width(starSpacing))
            }
        }
    }
}

@Composable
fun TextFieldComponent(
    state : MutableStateFlow<String> = MutableStateFlow(""),
    labelValue: String,
    icon: ImageVector,
    iconDescriptor: String = "Text field icon",
    type: TextFieldType = TextFieldType.NORMAL,
    isError: MutableStateFlow<Boolean> = MutableStateFlow(false),
    onValueChanged: () -> Unit = {}
) {
    val text by state.collectAsState()
    val error by isError.collectAsState()
    val iconModifier = Modifier.size(20.dp)

    when (type) {
        TextFieldType.NORMAL -> {
            TextField(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                isError = error,
                value = text,
                onValueChange = {
                    state.value = it
                    onValueChanged()
                },
                label = { Text(labelValue) },
                leadingIcon = { Icon(modifier = iconModifier, imageVector = icon, contentDescription = iconDescriptor) }
            )
        }
        TextFieldType.OUTLINED -> {
            OutlinedTextField(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                isError = error,
                value = text,
                onValueChange = {
                    state.value = it
                    onValueChanged()
                },
                label = { Text(labelValue) },
                leadingIcon = { Icon(modifier = iconModifier, imageVector = icon, contentDescription = iconDescriptor) }
            )
        }
    }
}
