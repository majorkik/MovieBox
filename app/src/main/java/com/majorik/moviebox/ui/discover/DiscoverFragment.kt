package com.majorik.moviebox.ui.discover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.chip.ChipGroup
import com.jaygoo.widget.OnRangeChangedListener
import com.jaygoo.widget.RangeSeekBar
import com.majorik.domain.GenresConstants
import com.majorik.moviebox.R
import com.majorik.moviebox.adapters.SelectableGenresAdapter
import kotlinx.android.synthetic.main.fragment_discover.*
import java.text.DecimalFormat
import java.util.*

class DiscoverFragment : Fragment() {
    private val selectableGenresAdapter =
        SelectableGenresAdapter(GenresConstants.movieGenres + GenresConstants.tvGenres)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discover, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDateRSB()
        setVoteAverageRSB()
        setDateChipsListener()
        setDefaultChips()
        setGridGenres()
    }

    private fun setGridGenres() {
        grid_genres.layoutManager =
            GridLayoutManager(activity, 3, GridLayoutManager.HORIZONTAL, false)
//        grid_genres.setHasFixedSize(true)
        grid_genres.adapter =
            selectableGenresAdapter
    }

    private fun setDateRSB() {
        val currYear = Calendar.getInstance().get(Calendar.YEAR)
        rsb_date.setRange(1935F, (currYear + 10).toFloat())
        setTextDateRSB(2010f, currYear.toFloat(), false)
        rsb_date.setProgress(2010F, currYear.toFloat())
        rsb_date.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }

            override fun onRangeChanged(
                view: RangeSeekBar?,
                leftValue: Float,
                rightValue: Float,
                isFromUser: Boolean
            ) {
                setTextDateRSB(leftValue, rightValue, chip_date_one_year.isSelected)
            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }
        })
    }

    private fun setVoteAverageRSB() {
        val decimalFormat = DecimalFormat("#.#")
        rsb_vote_average.setRange(0F, 10F)
        rsb_vote_average.setProgress(0F, 10F)
        rsb_vote_average.setOnRangeChangedListener(object : OnRangeChangedListener {
            override fun onStartTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }

            override fun onRangeChanged(
                view: RangeSeekBar?,
                leftValue: Float,
                rightValue: Float,
                isFromUser: Boolean
            ) {
                setTextVoteAverageRSB(
                    decimalFormat.format(leftValue),
                    decimalFormat.format(rightValue)
                )
            }

            override fun onStopTrackingTouch(view: RangeSeekBar?, isLeft: Boolean) {
            }
        })
    }

    private fun setTextDateRSB(leftYear: Float, rightYear: Float, isOnlyLeft: Boolean = true) {
        min_year.text = leftYear.toInt().toString()
        if (!isOnlyLeft) {
            max_year.text = rightYear.toInt().toString()
        }
    }

    private fun setTextVoteAverageRSB(left: String, right: String) {
        min_vote_average.text = left
        max_vote_average.text = right
    }

    private fun setDefaultChips() {
        chip_type_movie.isChecked = true
        chip_date_all_years.isChecked = true
        chip_sort_popular.isChecked = true
    }

    private fun setDateChipsListener() {
        chip_group_date.setOnCheckedChangeListener { group, checkedId ->
            rsbDateListener(checkedId)
        }
    }

    private fun rsbDateListener(id: Int) {
        when (id) {
            R.id.chip_date_all_years -> {
                rsb_date.visibility = RangeSeekBar.GONE
                min_year.visibility = TextView.GONE
                max_year.visibility = TextView.GONE
            }
            R.id.chip_date_one_year -> {
                rsb_date.seekBarMode = RangeSeekBar.SEEKBAR_MODE_SINGLE
                rsb_date.visibility = RangeSeekBar.VISIBLE
                min_year.visibility = TextView.VISIBLE
                max_year.visibility = TextView.VISIBLE
            }
            R.id.chip_date_two_years -> {
                rsb_date.seekBarMode = RangeSeekBar.SEEKBAR_MODE_RANGE
                rsb_date.visibility = RangeSeekBar.VISIBLE
                min_year.visibility = TextView.VISIBLE
                max_year.visibility = TextView.VISIBLE
            }
        }
    }
}