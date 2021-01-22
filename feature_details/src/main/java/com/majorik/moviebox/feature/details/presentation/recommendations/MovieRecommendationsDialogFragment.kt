package com.majorik.moviebox.feature.details.presentation.recommendations

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.Space
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.onActive
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.navArgs
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.common.resources.montserratFamily
import com.majorik.library.base.common.lists.LazyGridFor
import com.majorik.library.base.common.toolbar.SimpleToolbar
import com.majorik.moviebox.feature.details.R
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.Movie
import com.orhanobut.logger.Logger
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

//                            val data = viewModel.stateLiveData.collectAsState()

//                            WithPageState(pageState = data) {
//                                LazyGridFor(data.value.items) { movie, index ->
//                                    onActive { //this block will make sure this is triggered only once when renderring item on screen
//                                        if (index == data.value.items.lastIndex) viewModel.nextPage()
//                                    }
//                                    GridItem(movie = movie) {
//                                        //handle click
//                                    }
//                                }
//                            }


                            val items = viewModel.moviesFlow.collectAsLazyPagingItems()

                            LazyColumn {
                                fakeGridItems(
                                    lazyPagingItems = items,
                                    columns = 3,
                                    contentPadding = PaddingValues(4.dp),
                                    verticalItemPadding = 2.dp,
                                    horizontalItemPadding = 2.dp
                                ) { entry ->
                                    if (entry != null) {
                                        GridItem(movie = entry, onClick = {})
                                    }
                                }

                                spacerItem(16.dp)

                                items(items) { movie ->
                                }
                            }


//                            LazyColumn {
//                                items(items) { movie ->
//                                    GridItem(movie = movie!!) {}
//                                }
//                            }
                        }
                    }
                }
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        viewModel.fetchMovies()
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
            modifier = Modifier.fillMaxWidth()
                .aspectRatio(0.666f)
                .clip(RoundedCornerShape(8.dp))
                .clickable { onClick() },
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

@Preview(name = "Empty text", widthDp = 350, heightDp = 850, backgroundColor = 0xFFFFFF)
@Composable
fun PreviewEmptyText() {
    Surface(color = Color.White, modifier = Modifier.fillMaxSize()) {
        Box(contentAlignment = Center) {
//            EmptyText(text = "example Empty text")
        }
    }
}

fun LazyListScope.spacerItem(height: Dp) {
    item {
        Spacer(Modifier.preferredHeight(height).fillParentMaxWidth())
    }
}

/**
 * Displays a 'fake' grid using [LazyColumn]'s DSL. It's fake in that we just we add individual
 * column items, with a inner fake row.
 */
fun <T : Any> LazyListScope.fakeGridItems(
    lazyPagingItems: LazyPagingItems<T>,
    columns: Int,
    contentPadding: PaddingValues = PaddingValues(),
    horizontalItemPadding: Dp = 0.dp,
    verticalItemPadding: Dp = 0.dp,
    itemContent: @Composable (T?) -> Unit
) {
    val rows = when {
        lazyPagingItems.itemCount % columns == 0 -> lazyPagingItems.itemCount / columns
        else -> (lazyPagingItems.itemCount / columns) + 1
    }

    Logger.d("rows: $rows, items: ${lazyPagingItems.itemCount}")

    for (row in 0 until rows) {
        if (row == 0) spacerItem(contentPadding.top)

        item {
            Row(
                Modifier.fillMaxWidth()
                    .padding(start = contentPadding.start, end = contentPadding.end)
            ) {
                for (column in 0 until columns) {
                    Box(modifier = Modifier.weight(1f)) {
                        val index = (row * columns) + column
                        if (index < lazyPagingItems.itemCount) {
                            itemContent(lazyPagingItems[index])
                        }
                    }
                    if (column < columns - 1) {
                        Spacer(modifier = Modifier.preferredWidth(horizontalItemPadding))
                    }
                }
            }
        }

        if (row < rows - 1) {
            spacerItem(verticalItemPadding)
        } else {
            spacerItem(contentPadding.bottom)
        }
    }
}
