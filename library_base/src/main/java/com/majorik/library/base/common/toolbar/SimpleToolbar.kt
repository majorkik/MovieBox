package com.majorik.library.base.common.toolbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.majorik.base.R
import com.majorik.library.base.common.resources.mineShaft
import com.majorik.library.base.common.resources.rubikFontFamily
import com.majorik.library.base.common.resources.transparent
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import java.util.*

@Composable
fun SimpleToolbar(
    heightDp: Int,
    title: String,
    modifier: Modifier,
    backPressedClick: () -> Unit,
    backgroundColor: Color = transparent,
    buttonIconColor: Color = mineShaft,
    titleColor: Color = mineShaft
) {
    val widthButtonDp = 56

    Surface(color = backgroundColor, modifier = modifier) {
        Row(modifier = Modifier.height(height = heightDp.dp)) {
            // Кнопка назад
            IconButton(
                onClick = backPressedClick,
                modifier = Modifier.width(widthButtonDp.dp).height(heightDp.dp),
            ) {
                Icon(
                    imageVector = vectorResource(id = R.drawable.ic_arrow_down_24),
                    contentDescription = null,
                    tint = buttonIconColor
                )
            }

            // Название экрана
            Text(
                text = title.toUpperCase(Locale.getDefault()),
                color = titleColor,
                fontFamily = rubikFontFamily,
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                modifier = Modifier.align(Alignment.CenterVertically).padding(end = widthButtonDp.dp).fillMaxWidth()
            )
        }
    }
}

@Preview(
    name = "Simple toolbar",
    widthDp = 350,
    heightDp = 350
)
@Composable
private fun SimpleToolbarPreview() {
    Column {
        SimpleToolbar(
            56,
            "Toolbar title",
            Modifier.statusBarsPadding(),
            backgroundColor = Color.White,
            backPressedClick = {
            }
        )
    }
}
