package com.majorik.moviebox.feature.details.presentation.common.details

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import coil.transform.CircleCropTransformation
import com.majorik.library.base.common.resources.mineShaft
import com.majorik.library.base.common.resources.mineShaft50
import com.majorik.library.base.common.resources.montserratFamily
import com.majorik.library.base.constants.UrlConstants
import com.majorik.moviebox.feature.details.domain.tmdbModels.cast.Cast
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun Fragment.SectionText(@StringRes textRes: Int) {
    Text(
        text = getString(textRes), color = mineShaft,
        fontSize = 16.sp,
        fontFamily = montserratFamily,
        fontWeight = FontWeight.Black,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun Fragment.FactText(@StringRes name: Int, value: String) {
    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = mineShaft50, fontWeight = FontWeight.Normal)) {
                append(getString(name))
            }

            append(": ")

            withStyle(style = SpanStyle(color = mineShaft, fontWeight = FontWeight.SemiBold)) {
                append(value)
            }
        },
        fontSize = 12.sp,
        fontFamily = montserratFamily,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
    )
}

@Composable
fun PersonItem(credit: Cast, click: (id: Int) -> Unit) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .clickable { click(credit.id) }
            .background(color = Color.Blue),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            Modifier
                .padding(horizontal = 4.dp, vertical = 2.dp)
        ) {
            CoilImage(
                data = UrlConstants.TMDB_PROFILE_SIZE_185 + credit.profilePath,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                requestBuilder = {
                    transformations(CircleCropTransformation())
                },
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = credit.name,
                textAlign = TextAlign.Center,
                fontFamily = montserratFamily,
                color = mineShaft,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 4.dp)
                    .widthIn(max = 100.dp),
                maxLines = 1
            )
            Text(
                text = credit.character ?: "",
                color = mineShaft50,
                textAlign = TextAlign.Center,
                fontFamily = montserratFamily,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(vertical = 2.dp)
                    .widthIn(max = 100.dp),
                maxLines = 1
            )
        }
    }
}
