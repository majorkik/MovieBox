package com.majorik.moviebox.feature.details.presentation.common

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.majorik.library.base.common.resources.*

@Composable
fun RatingView(
    modifier: Modifier = Modifier,
    rating: Double,
    maxRating: Int = 10,
    dotSizeDp: Int = 4,
    textColor: Color = mineShaft
) {
    val indicatorColor = when {
        rating >= 8.0 -> emerald
        rating > 6.0 && rating < 8.0 -> sunglow
        else -> sunsetOrange
    }
    Row(modifier = modifier) {
        Text(
            text = rating.toString(),
            modifier = Modifier.padding(bottom = 4.dp),
            color = textColor,
            fontSize = 18.sp,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.W900
        )
        Text(
            text = "/$maxRating",
            Modifier
                .align(Alignment.Bottom)
                .padding(bottom = 2.dp), color = textColor,
            fontSize = 12.sp,
            fontFamily = montserratFamily,
            fontWeight = FontWeight.W600
        )
        Canvas(
            modifier = Modifier.size(dotSizeDp.dp),
            onDraw = {
                drawCircle(color = indicatorColor)
            }
        )
    }
}

@Preview("RatingView")
@Composable
private fun PreviewRatingView() {
    Box {
        RatingView(rating = 9.0)
    }
}
