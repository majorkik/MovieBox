package com.majorik.moviebox.feature.navigation.presentation.constants

import com.majorik.moviebox.feature.navigation.R
import com.majorik.moviebox.feature.navigation.domain.models.NetworkModel

object Networks {
    val networks: List<NetworkModel> = listOf(
        NetworkModel(213, "Netflix", R.drawable.ic_027_netflix),
        NetworkModel(174, "AMC", R.drawable.ic_004_amc),
        NetworkModel(49, "HBO", R.drawable.ic_022_hbo),
        NetworkModel(77, "Syfy", R.drawable.ic_036_syfy),
        NetworkModel(56, "Cartoon Network", R.drawable.ic_011_cartoon_network),
        NetworkModel(67, "Showtime", R.drawable.ic_031_showtime),
        NetworkModel(4, "BBC One", R.drawable.ic_007_bbc),
        NetworkModel(59, "CNN", R.drawable.ic_014_cnn),
        NetworkModel(2243, "DC Universe", R.drawable.ic_017_dc),
        NetworkModel(64, "Discovery", R.drawable.ic_018_discovery),
        NetworkModel(318, "Starz", R.drawable.ic_034_starz),
        NetworkModel(19, "FOX", R.drawable.ic_021_fox),
        NetworkModel(2076, "Paramount Network", R.drawable.ic_029_paramount),
        NetworkModel(359, "Cinemax", R.drawable.ic_013_cinemax),
        NetworkModel(6, "NBC", R.drawable.ic_026_nbc),
        NetworkModel(43, "National Geographic", R.drawable.ic_025_national_geographic),
        NetworkModel(91, "Animal Planet", R.drawable.ic_005_animal_planet),
        NetworkModel(2003, "AXN", R.drawable.ic_006_axn),
        NetworkModel(74, "Bravo", R.drawable.ic_010_bravo),
        NetworkModel(16, "CBS", R.drawable.ic_012_cbs),
        NetworkModel(47, "Comedy Central", R.drawable.ic_016_comedy_central),
        NetworkModel(29, "ESPN", R.drawable.ic_020_espn),
        NetworkModel(34, "Lifetime", R.drawable.ic_023_lifetime),
        NetworkModel(33, "MTV", R.drawable.ic_024_mtv),
        NetworkModel(13, "Nickelodeon", R.drawable.ic_028_nickelodeon),
        NetworkModel(14, "PBS", R.drawable.ic_030_pbs),
        NetworkModel(214, "Sky One", R.drawable.ic_032_sky),
        NetworkModel(68, "TBS", R.drawable.ic_037_tbs),
        NetworkModel(71, "The CW", R.drawable.ic_038_cw),
        NetworkModel(41, "TNT", R.drawable.ic_039_tnt),
        NetworkModel(28, "Univision", R.drawable.ic_042_univision)
    )
}
