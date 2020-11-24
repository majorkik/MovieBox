package com.majorik.library.base.constants

import com.majorik.library.base.utils.PACKAGE_NAME

object ScreenLinks {
    /**
     * Activities
     */

    //    Details module
    const val movieDetails = "$PACKAGE_NAME.feature.details.presentation.movieDetails.MovieDetailsActivity"
    const val tvDetails = "$PACKAGE_NAME.feature.details.presentation.tvDetails.TVDetailsActivity"

    //   Authorization module
    const val authorization = "$PACKAGE_NAME.feature.auth.presentation.ui.authorization.AuthorizationActivity"

    //  Navigation module
    const val genresActivity = "$PACKAGE_NAME.feature.collections.presentation.genres.GenresActivity"
    const val searchableActivity = "$PACKAGE_NAME.feature.search.presentation.ui.SearchableActivity"

    //  Search module

    const val discoverActivity = "$PACKAGE_NAME.feature.search.presentation.ui.discover.DiscoverActivity"
}
