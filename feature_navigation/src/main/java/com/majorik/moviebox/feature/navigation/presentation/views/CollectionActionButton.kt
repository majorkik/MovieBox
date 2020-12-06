package com.majorik.moviebox.feature.navigation.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.majorik.moviebox.feature.navigation.R
import com.majorik.moviebox.feature.navigation.databinding.LayoutCollectionActionAdvancedButtonBinding
import com.majorik.moviebox.feature.navigation.databinding.LayoutCollectionActionButtonBinding

class CollectionActionButton : LinearLayout {

    private var viewBinding: LayoutCollectionActionButtonBinding? = null

    var buttonTitle: String = ""
        set(value) {
            field = value
            viewBinding?.buttonTitle?.text = value
        }

    var buttonOptionTitle: String? = ""
        set(value) {
            field = value

            viewBinding?.buttonOptionTitle?.text = value
        }

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet? = null) {
        val layoutInflater = LayoutInflater.from(context)
        viewBinding = LayoutCollectionActionButtonBinding.inflate(layoutInflater, this)

        val ta = context.obtainStyledAttributes(attrs, R.styleable.CollectionActionButton)

        val buttonTitle = ta.getString(R.styleable.CollectionActionButton_cb_title)
        val buttonOptionTitle = ta.getString(R.styleable.CollectionActionButton_cb_option_title)

        buttonTitle?.let { this.buttonTitle = it }
        buttonOptionTitle?.let { this.buttonOptionTitle = it }

        if (isInEditMode) {
            this.buttonTitle = "test"
            this.buttonOptionTitle = "test"
        }

        orientation = HORIZONTAL

        ta.recycle()
    }
}
