package com.majorik.library.base.constants

import com.majorik.library.base.utils.PACKAGE_NAME

object ScreenLinks {
    /**
     * Activities
     */

    //    Details module
    const val movieDetails = "$PACKAGE_NAME.feature.details.presentation.movieDetails.MovieDetailsActivity"
    const val tvDetails = "$PACKAGE_NAME.feature.details.presentation.tvDetails.TVDetailsActivity"
    const val peopleDetails = "$PACKAGE_NAME.feature.details.presentation.person_details.PersonDetailsActivity"

    //   Authorization module
    const val authorization = "$PACKAGE_NAME.feature.auth.presentation.ui.authorization.AuthorizationActivity"

    //Navigation module
    const val movieCollection = "$PACKAGE_NAME.feature.collections.presentation.movieTabCollections.MovieCollectionsActivity"
    const val tvCollection = "$PACKAGE_NAME.feature.collections.presentation.tvTabCollections.TVCollectionsActivity"
    const val genresActivity = "$PACKAGE_NAME.feature.collections.presentation.genres.GenresActivity"
    const val searchableActivity = "$PACKAGE_NAME.feature.search.presentation.ui.SearchableActivity"
    /**
     * Fragments
     */

    //Search discover filters
    const val discoverFiltersDialog = "$PACKAGE_NAME.feature.search.presentation.ui.filters.DiscoverFiltersBottomSheetFragment"
}
