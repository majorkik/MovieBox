package com.majorik.moviebox.feature.details.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.majorik.library.base.common.resources.gallery
import com.majorik.library.base.common.resources.montserratFamily
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun HorizontalMediaItem(
    image: String,
    year: Int,
    title: String,
    genres: String,
    rating: Double,
    cardPaddingDp: Int,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = Modifier
            .padding(horizontal = cardPaddingDp.dp)
            .fillMaxWidth()
            .height(160.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Box(modifier = modifier) {
            CoilImage(data = image, contentScale = ContentScale.Crop, fadeIn = true, contentDescription = null)

            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            ) {
                Text(
                    text = year.toString(),
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W800,
                    color = Color.White,
                    fontSize = 14.sp
                )
                Text(
                    text = title,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W800,
                    color = Color.White,
                    fontSize = 16.sp
                )
                Text(
                    text = genres,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.W400,
                    color = gallery,
                    fontSize = 12.sp
                )
            }

            RatingView(
                rating = rating,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp),
                textColor = Color.White
            )
        }
    }
}

@Preview(name = "HorizontalMediaItem")
@Composable
fun PreviewHorizontalMediaItem() {
    HorizontalMediaItem(
        "https://images.unsplash.com/photo-1606788073386-c0248906641f?ixid=MXwxMjA3fDF8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=1352&q=80",
        2020,
        "John Wick 3",
        "Action, Fantasy",
        9.0,
        16,
        Modifier
            .fillMaxWidth()
            .height(160.dp)
    )
}
