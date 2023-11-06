package com.example.todue.modifier

import androidx.compose.foundation.shape.GenericShape
import androidx.compose.ui.graphics.Shape

//fun to create bottom line, used in the top bar
fun getBottomLineShape() : Shape {
    return GenericShape { size, _ ->
        // 1) Bottom-left corner
        moveTo(0f, size.height)
        // 2) Bottom-right corner
        lineTo(size.width, size.height)
        // 3) Top-right corner
        lineTo(size.width, size.height - 3)
        // 4) Top-left corner
        lineTo(0f, size.height - 3)
    }
}