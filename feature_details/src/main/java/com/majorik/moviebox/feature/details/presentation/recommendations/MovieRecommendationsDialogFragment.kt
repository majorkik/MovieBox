package com.majorik.moviebox.feature.details.presentation.recommendations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.onActive
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.common.resources.montserratFamily
import com.majorik.library.base.common.lists.LazyGridFor
import com.majorik.library.base.common.toolbar.SimpleToolbar
import com.majorik.moviebox.feature.details.R
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.Movie
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.statusBarsPadding
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.majorik.moviebox.R as AppRes

/**
 * Jetpack compose экран
 * TODO - сомнительная производительность для списка из 3 колонок
 */

class MovieRecommendationsDialogFragment : DialogFragment() {

    private val viewModel: MovieRecommendationsViewModel by viewModel()

    private val args: MovieRecommendationsDialogFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, AppRes.style.AppTheme_DialogFragmentTransparentStyle)
        viewModel.movieId = args.movieId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        ComposeView(requireContext()).apply {
            layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT)

            setContent {
                ProvideWindowInsets {
                    Surface(color = colorResource(id = com.majorik.base.R.color.colorWhite)) {
                        Column {
                            SimpleToolbar(
                                heightDp = 56,
                                title = context.getString(R.string.recommendations_screen_title),
                                modifier = Modifier.statusBarsPadding(),
                                backPressedClick = { dismiss() })

                            val data = viewModel.stateLiveData.collectAsState()

                            WithPageState(pageState = data) {
                                LazyGridFor(data.value.items) { movie, index ->
                                    onActive { //this block will make sure this is triggered only once when renderring item on screen
                                        if (index == data.value.items.lastIndex) viewModel.nextPage()
                                    }
                                    GridItem(movie = movie) {
                                        //handle click
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.fetchMovies()
    }
}

@Composable
fun LoadingView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        CircularProgressIndicator(modifier = Modifier.wrapContentWidth(CenterHorizontally).size(32.dp))
    }
}

@Composable
fun ErrorView(message: String = "Oops! Something went wrong, Please refresh after some time") {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        ErrorText(message)
    }
}

@Composable
fun ErrorText(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text, modifier = modifier.fillMaxWidth().padding(horizontal = 48.dp),
        style = MaterialTheme.typography.body2,
        textAlign = TextAlign.Center, color = MaterialTheme.colors.error.copy(alpha = 0.9F)
    )
}

@Composable
fun WithPageState(
    pageState: State<MovieRecommendationsViewModel.ViewState>,
    content: @Composable() () -> Unit
) {
    val state = pageState.value

    when {
        state.isLoading -> {
            LoadingView()
        }

        state.isGenericError && state.items.isNotEmpty() -> {
            ErrorView()
        }

        state.isNetworkError && state.items.isNotEmpty() -> {
            ErrorView()
        }

        state.items.isNullOrEmpty() -> {

        }

        state.items.isNotEmpty() -> {
            content()
        }
    }
}

@Composable
fun GridItem(movie: Movie, onClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(
            elevation = 0.dp,
            modifier = Modifier.fillMaxWidth().aspectRatio(0.666f).clickable { onClick() },
            shape = RoundedCornerShape(8.dp)
        ) {
            CoilImage(
                data = UrlConstants.TMDB_POSTER_SIZE_185 + movie.posterPath,
                contentScale = ContentScale.Crop,
                fadeIn = true
            )
        }

        Text(
            text = movie.title,
            maxLines = 1,
            fontFamily = montserratFamily,
            fontWeight = W500,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 2.dp, vertical = 8.dp)
        )
    }
}

//@Preview(name = "Empty text", widthDp = 350, heightDp = 850, backgroundColor = 0xFFFFFF)
//@Composable
//fun PreviewEmptyText() {
//    Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
//        Box(contentAlignment = Center) {
//            EmptyText(text = "example Empty text")
//        }
//    }
//}
