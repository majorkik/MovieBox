package com.majorik.moviebox.feature.details.presentation.compose_movie_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationEndReason
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.scrollorientationlocking.Orientation
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.majorik.library.base.common.pager.Pager
import com.majorik.library.base.common.pager.PagerState
import com.majorik.library.base.common.resources.gallery
import com.majorik.library.base.common.resources.montserratFamily
import com.majorik.library.base.constants.AppConfig
import com.majorik.library.base.constants.UrlConstants
import com.majorik.library.base.extensions.toDate
import com.majorik.moviebox.feature.details.R
import com.majorik.moviebox.feature.details.domain.tmdbModels.cast.Cast
import com.majorik.moviebox.feature.details.domain.tmdbModels.image.ImageDetails
import com.majorik.moviebox.feature.details.domain.tmdbModels.movie.MovieDetails
import com.majorik.moviebox.feature.details.presentation.common.RatingView
import com.majorik.moviebox.feature.details.presentation.common.details.FactText
import com.majorik.moviebox.feature.details.presentation.common.details.PersonItem
import com.majorik.moviebox.feature.details.presentation.common.details.SectionText
import com.majorik.moviebox.feature.details.presentation.movieDetails.MovieDetailsDialogFragmentArgs
import com.majorik.moviebox.feature.details.presentation.movieDetails.MovieDetailsViewModel
import com.soywiz.klock.KlockLocale
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.LocalWindowInsets
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.floor

class MovieDetailsComposeFragment : Fragment() {

    private val viewModel: MovieDetailsViewModel by viewModel()

    private val args: MovieDetailsDialogFragmentArgs by navArgs()

    companion object {
        private const val IMAGE_PAGER_HEIGHT = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setStyle(STYLE_NORMAL, com.majorik.moviebox.R.style.AppTheme_DialogFragmentTransparentStyle)

        viewModel.fetchMovieDetails(
            args.id,
            AppConfig.REGION,
            "images,credits,videos",
            "ru,en,null"
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        ComposeView(requireContext()).apply {
//            layoutParams = ViewGroup.LayoutParams(
//                ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.MATCH_PARENT
//            )

            setContent {
                ProvideWindowInsets {
                    val state = viewModel.stateLiveData.collectAsState()
                    val pagerState = remember { PagerState() }

                    BoxWithConstraints {
                        val sideMaxPx = constraints.maxHeight.toFloat()

                        val position = remember { Animatable(0f) }
                        position.updateBounds(0f, sideMaxPx)

                        val xOffset = with(LocalDensity.current) { position.value.toDp() }

                        Box(modifier = Modifier
                            .offset(y = xOffset)
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .background(color = gallery)
                            .draggable(
                                startDragImmediately = position.isRunning,
                                orientation = Orientation.Vertical,
                                onDragStopped = {
                                    lifecycleScope.launch {
                                        if (xOffset <= 200.dp) {
                                            position.animateTo(
                                                targetValue = 0f,
                                                initialVelocity = it
                                            )
                                        } else {
                                            val result = position.animateTo(
                                                targetValue = sideMaxPx
                                            )

                                            if (result.endReason == AnimationEndReason.Finished) {
                                                findNavController().popBackStack()
                                            }
                                        }
                                    }
                                }
                            ) { delta ->
                                lifecycleScope.launch {
                                    val value = position.value + delta
                                    position.snapTo(if (value < 0f) 0f else value)
                                }
                            }
                        ) {
                            PagerContent(
                                images = state.value.details?.images?.backdrops ?: emptyList(),
                                pagerState = pagerState,
                                pagerHeight = IMAGE_PAGER_HEIGHT
                            )

                            val statusBarHeight = with(LocalDensity.current) {
                                LocalWindowInsets.current.statusBars.top.toDp()
                            }

                            if (state.value.details != null) {
                                MainContent(
                                    details = state.value.details!!,
                                    statusBarHeight = statusBarHeight,
                                    topOffset = IMAGE_PAGER_HEIGHT
                                )
                            }
                        }
                    }
                }
            }
        }

    /**
     * Пайджер картинок
     */

    @Composable
    fun PagerContent(images: List<ImageDetails>, pagerState: PagerState, pagerHeight: Int) {
        pagerState.maxPage = (images.count() - 1).coerceAtLeast(0)

        Pager(
            state = pagerState,
            modifier = Modifier.fillMaxWidth().preferredHeight(pagerHeight.dp)
        ) {
            val imagePath = images.getOrNull(page)?.filePath

            CoilImage(
                data = UrlConstants.TMDB_BACKDROP_SIZE_1280 + imagePath,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                fadeIn = true
            )
        }
    }

    @Composable
    fun MainContent(
        details: MovieDetails,
        statusBarHeight: Dp,
        topOffset: Int
    ) {
        val (sideMinPx, sideMaxPx) = with(LocalDensity.current) {
            statusBarHeight.toPx() to topOffset.dp.toPx()
        }

        val scrollEnabled = mutableStateOf(false)

        val position = remember { Animatable(sideMaxPx) }
        position.updateBounds(sideMinPx, sideMaxPx)

        val xOffset = with(LocalDensity.current) { position.value.toDp() }

        Box(
            modifier = Modifier
                .offset(x = 0.dp, y = xOffset)
                .fillMaxHeight()
                .fillMaxWidth()
                .draggable(
                    enabled = true,
                    startDragImmediately = position.isRunning,
                    orientation = Orientation.Vertical,
                    onDragStopped = {
                        lifecycleScope.launch {
                            //todo доделать подтягивание к верху и низу
//                            position.animateDecay(initialVelocity = it, flingConfig.decayAnimation.generateDecayAnimationSpec())
                        }
                    }
                ) { delta ->
                    lifecycleScope.launch {
                        val value = position.value + delta
                        val fixValue = when {
                            value < statusBarHeight.toPx() -> statusBarHeight.toPx()
                            value > sideMaxPx -> sideMaxPx
                            else -> value
                        }
                        position.snapTo(fixValue)

                        scrollEnabled.value = value <= statusBarHeight.toPx()
                    }
                }
                .background(color = Color.White)
        ) {
            ContentDetails(details, scrollEnabled)
        }
    }

    @Composable
    fun ContentDetails(details: MovieDetails, scrollEnabled: MutableState<Boolean>) {
        val scrollState = rememberScrollState(0f)

        Column(modifier = Modifier.verticalScroll(scrollState, scrollEnabled.value))
        {
            Row {
                Text(
                    text = details.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(16.dp),
                    fontSize = 18.sp,
                    fontFamily = montserratFamily,
                    fontWeight = FontWeight.Black
                )

                RatingView(
                    rating = details.voteAverage,
                    modifier = Modifier.padding(16.dp)
                )
            }

            SectionText(R.string.details_overview)

            Text(
                text = details.overview ?: "",
                fontSize = 12.sp,
                fontFamily = montserratFamily,
                fontWeight = FontWeight.Light,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            SectionText(R.string.details_facts)

            FactText(name = R.string.details_original_title, value = details.originalTitle)
            FactText(name = R.string.details_status, value = details.status)
            FactText(name = R.string.details_original_language, value = details.originalLanguage)
            FactText(name = R.string.details_budget, value = "$ ${details.budget}")
            FactText(name = R.string.details_revenue, value = "$ ${details.revenue}")
            FactText(
                name = R.string.details_release_date,
                value = KlockLocale.default.formatDateLong.format(details.releaseDate.toDate(getString(R.string.details_yyyy_mm_dd)))
            )
            FactText(
                name = R.string.details_runtime,
                value = formatRuntime(details.runtime)
            )
            FactText(
                name = R.string.details_company_production,
                value = details.productionCompanies.joinToString(", ") { it.name }
            )


            SectionText(R.string.details_persons)

            LazyRow {
                itemsIndexed(details.credits.casts) { _: Int, item: Cast ->
                    PersonItem(item) {

                    }
                }
            }

            SectionText(R.string.details_images)
        }
    }

    private fun formatRuntime(runtime: Int?): String {
        return if (runtime != null) {
            val hours = floor(runtime / 60.0).toInt()
            val stringHours =
                resources.getQuantityString(R.plurals.hours, hours, hours)

            val minutes = runtime % 60
            val stringMinutes =
                resources.getQuantityString(R.plurals.minutes, minutes, minutes)

            getString(R.string.details_runtime_mask, stringHours, stringMinutes)
        } else {
            getString(R.string.details_unknown)
        }
    }
}
