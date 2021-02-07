package com.majorik.library.base.common.resources

import androidx.compose.ui.text.font.*
import com.majorik.base.R

val rubikFontFamily = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.rubik_one_regular,
            weight = FontWeight.W400,
            style = FontStyle.Normal
        )
    )
)

val montserratFamily = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.montserrat_regular,
            weight = FontWeight.W400,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.montserrat_medium,
            weight = FontWeight.W500,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.montserrat_semibold,
            weight = FontWeight.W600,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.montserrat_bold,
            weight = FontWeight.W700,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.montserrat_extrabold,
            weight = FontWeight.W800,
            style = FontStyle.Normal
        ),
        Font(
            resId = R.font.montserrat_black,
            weight = FontWeight.W900,
            style = FontStyle.Normal
        )
    )
)
