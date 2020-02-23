package com.majorik.moviebox.ui.person_details.biography

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.majorik.moviebox.R
import com.majorik.moviebox.extensions.toDate
import com.soywiz.klock.KlockLocale
import com.soywiz.klock.locale.russian
import kotlinx.android.synthetic.main.layout_biography.view.*

class BiographyDialog : DialogFragment() {

    private var biography = ""
    private var birthday = ""
    private var placeOfBirth = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        biography = arguments?.getString(BIOGRAPHY_ARG) ?: getString(R.string.is_absent)
        birthday = arguments?.getString(BIRTHDAY_ARG) ?: "-"
        placeOfBirth = arguments?.getString(PLACE_OF_BIRTH_ARG) ?: ""
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_biography, container, false)
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

        if (biography.isNotEmpty()) {
            view.biography.text = biography
        } else {
            view.biography.gravity = Gravity.CENTER
            view.biography.text = getString(R.string.is_absent)
        }

        if (birthday.isNotEmpty()) {
            view.birthday.text = KlockLocale.russian.formatDateLong.format(birthday.toDate())
        } else {
            view.birthday.text = birthday
        }

        view.place_of_birth.text = placeOfBirth

        view.btn_close.setOnClickListener {
            dismiss()
        }
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