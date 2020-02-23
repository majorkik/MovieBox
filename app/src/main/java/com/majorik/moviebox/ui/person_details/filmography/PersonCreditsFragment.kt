package com.majorik.moviebox.ui.person_details.filmography

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.ChangeViewTypeListener
import com.majorik.moviebox.adapters.MovieCreditsAdapter
import com.majorik.moviebox.adapters.TVCreditsAdapter
import com.majorik.moviebox.extensions.toDate
import com.majorik.moviebox.extensions.toPx
import com.majorik.moviebox.utils.SpacingDecoration
import com.orhanobut.logger.Logger
import com.soywiz.klock.DateTime
import kotlinx.android.synthetic.main.fragment_person_credits.*
import org.koin.android.viewmodel.ext.android.viewModel


class PersonCreditsFragment : Fragment(), ChangeViewTypeListener {
    enum class CreditsType {
        MOVIE, TV
    }

    private val viewModel: PersonCreditsViewModel by viewModel()

    private var layoutManager: GridLayoutManager? = null
    private val itemDecoration = SpacingDecoration(16.toPx(), 16.toPx(), true)

    private lateinit var creditsType: CreditsType
    private var personId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        creditsType =
            CreditsType.valueOf(
                arguments?.getString(
                    CREDITS_TYPE_ARG
                ) ?: CreditsType.MOVIE.name
            )

        personId = arguments?.getInt(PERSON_ID_ARG) ?: 0

        layoutManager = GridLayoutManager(context, 1)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_person_credits, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        credits_list.layoutManager = layoutManager

        when (creditsType) {
            CreditsType.MOVIE -> {
                viewModel.fetchMovieCredits(personId, "ru-RU")
            }

            CreditsType.TV -> {
                viewModel.fetchTVCredits(personId, "ru-RU")
            }
        }

        setObservers()
    }

    private fun setObservers() {
        viewModel.movieCreditsLiveData.observe(viewLifecycleOwner, Observer {
            credits_list.adapter =
                MovieCreditsAdapter(layoutManager, it.cast.sortedByDescending { item ->
                    if (!item.releaseDate.isNullOrEmpty()) {
                        item.releaseDate!!.toDate().utc.unixMillis
                    } else {
                        0.0
                    }
                })
        })

        viewModel.tvCreditsLiveData.observe(viewLifecycleOwner, Observer {
            credits_list.adapter =
                TVCreditsAdapter(layoutManager, it.cast.sortedByDescending { item ->
                    if (!item.firstAirDate.isNullOrEmpty()) {
                        item.firstAirDate!!.toDate().utc.unixMillis
                    } else {
                        0.0
                    }
                })
        })
    }

    companion object {
        const val CREDITS_TYPE_ARG = "credits_type_arg"
        const val PERSON_ID_ARG = "person_id_arg"

        fun newInstance(personId: Int, creditsType: CreditsType) =
            PersonCreditsFragment().apply {
                arguments = Bundle().apply {
                    putInt(PERSON_ID_ARG, personId)
                    putString(CREDITS_TYPE_ARG, creditsType.name)
                }
            }
    }

    override fun viewTypeChanged(isGrid: Boolean) {
        if (isGrid) {
            layoutManager?.spanCount = 3
            credits_list?.addItemDecoration(itemDecoration)
        } else {
            layoutManager?.spanCount = 1
            credits_list?.removeItemDecoration(itemDecoration)
        }
        credits_list?.adapter?.notifyItemRangeChanged(0, credits_list?.adapter?.itemCount ?: 0)
//        credits_list?.adapter?.notifyDataSetChanged()
    }
}