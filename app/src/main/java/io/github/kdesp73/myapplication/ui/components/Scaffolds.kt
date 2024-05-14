package io.github.kdesp73.myapplication.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


enum class ScaffoldAlignment {
    TOP, CENTER, BOTTOM, START, END
}


/**
 * ---------------------
 * Top
 * - - - - - - - - - - -
 * 
 * 
 * Center
 * 
 * 
 * - - - - - - - - - - -
 * Bottom
 * ---------------------
 */
@Composable
fun VerticalScaffold(
    modifier: Modifier = Modifier,
    top: @Composable () -> Unit = {},
    bottom: @Composable () -> Unit = {},
    center: @Composable () -> Unit = {},
    topAlignment: ScaffoldAlignment = ScaffoldAlignment.CENTER,
    centerAlignment: ScaffoldAlignment = ScaffoldAlignment.CENTER,
    bottomAlignment: ScaffoldAlignment = ScaffoldAlignment.CENTER,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        val top_a = when(topAlignment) {
            ScaffoldAlignment.START -> Alignment.TopStart
            ScaffoldAlignment.CENTER -> Alignment.TopCenter
            ScaffoldAlignment.END -> Alignment.TopEnd
            else -> throw IllegalArgumentException("Unsupported alignment: $topAlignment")
        }

        val center_a = when(centerAlignment) {
            ScaffoldAlignment.START -> Alignment.CenterStart
            ScaffoldAlignment.CENTER -> Alignment.Center
            ScaffoldAlignment.END -> Alignment.CenterEnd
            else -> throw IllegalArgumentException("Unsupported alignment: $centerAlignment")
        }

        val bottom_a = when(bottomAlignment) {
            ScaffoldAlignment.START -> Alignment.BottomStart
            ScaffoldAlignment.CENTER -> Alignment.BottomCenter
            ScaffoldAlignment.END -> Alignment.BottomEnd
            else -> throw IllegalArgumentException("Unsupported alignment: $bottomAlignment")
        }

        Box(modifier = Modifier.align(top_a)){
            top()
        }

        Box(modifier = Modifier.align(center_a)){
            center()
        }
        
        Box(modifier = Modifier.align(bottom_a)){
            bottom()
        }
    }
}

/**
 * |      |        |       |
 * |      |        |       |
 * |      |        |       |
 * | Left | Center | Right |
 * |      |        |       |
 * |      |        |       |
 * |      |        |       |
 */
@Composable
fun HorizontalScaffold(
    modifier: Modifier = Modifier,
    left: @Composable () -> Unit,
    right: @Composable () -> Unit,
    center: @Composable () -> Unit,
    leftAlignment: ScaffoldAlignment = ScaffoldAlignment.CENTER,
    centerAlignment: ScaffoldAlignment = ScaffoldAlignment.CENTER,
    rightAlignment: ScaffoldAlignment = ScaffoldAlignment.CENTER,
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        val left_a = when(leftAlignment) {
            ScaffoldAlignment.TOP -> Alignment.TopCenter
            ScaffoldAlignment.CENTER -> Alignment.Center
            ScaffoldAlignment.BOTTOM -> Alignment.BottomCenter
            else -> throw IllegalArgumentException("Unsupported alignment: $leftAlignment")
        }

        val center_a = when(centerAlignment) {
            ScaffoldAlignment.TOP -> Alignment.TopCenter
            ScaffoldAlignment.CENTER -> Alignment.Center
            ScaffoldAlignment.BOTTOM -> Alignment.BottomCenter
            else -> throw IllegalArgumentException("Unsupported alignment: $centerAlignment")
        }

        val right_a = when(rightAlignment) {
            ScaffoldAlignment.TOP -> Alignment.TopCenter
            ScaffoldAlignment.CENTER -> Alignment.Center
            ScaffoldAlignment.BOTTOM -> Alignment.BottomCenter
            else -> throw IllegalArgumentException("Unsupported alignment: $rightAlignment")
        }

        Box(modifier = Modifier.align(left_a)){
            left()
        }

        Box(modifier = Modifier.align(center_a)){
            center()
        }

        Box(modifier = Modifier.align(right_a)){
            right()
        }
    }
}
