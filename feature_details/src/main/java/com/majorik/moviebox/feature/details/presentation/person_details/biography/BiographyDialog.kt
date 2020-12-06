package com.majorik.moviebox.feature.details.presentation.person_details.biography

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.majorik.library.base.extensions.toDate
import com.soywiz.klock.KlockLocale
import com.soywiz.klock.locale.russian
import com.majorik.moviebox.feature.details.R
import com.majorik.moviebox.feature.details.databinding.DialogBiographyDetailsBinding

class BiographyDialog : DialogFragment() {

    private val viewBinding: DialogBiographyDetailsBinding by viewBinding()

    private var biography = ""
    private var birthday = ""
    private var placeOfBirth = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        biography = arguments?.getString(BIOGRAPHY_ARG) ?: getString(R.string.details_is_absent)
        birthday = arguments?.getString(BIRTHDAY_ARG) ?: "-"
        placeOfBirth = arguments?.getString(PLACE_OF_BIRTH_ARG) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.dialog_biography_details,
            container,
            false
        )
    }

    override fun onStart() {
        super.onStart()

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setData()
        setClickListeners()
    }

    private fun setClickListeners() {
        viewBinding.btnClose.setOnClickListener {
            dismiss()
        }
    }

    private fun setData() {
        if (biography.isNotEmpty()) {
            viewBinding.biography.text = biography
        } else {
            viewBinding.biography.gravity = Gravity.CENTER
            viewBinding.biography.text = getString(R.string.details_is_absent)
        }

        if (birthday.isNotEmpty() && birthday != "-") {
            viewBinding.birthday.text = KlockLocale.russian.formatDateLong.format(birthday.toDate())
        } else {
            viewBinding.birthday.text = birthday
        }

        viewBinding.placeOfBirth.text = placeOfBirth
    }

    companion object {
        const val BIOGRAPHY_ARG = "biography_arg"
        const val BIRTHDAY_ARG = "birthday_arg"
        const val PLACE_OF_BIRTH_ARG = "place_of_birth_arg"

        fun newInstance(biography: String?, birthday: String?, placeOfBirth: String?) =
            BiographyDialog().apply {
                arguments = Bundle().apply {
                    putString(BIOGRAPHY_ARG, biography)
                    putString(BIRTHDAY_ARG, birthday)
                    putString(PLACE_OF_BIRTH_ARG, placeOfBirth)
                }
            }
    }
}
