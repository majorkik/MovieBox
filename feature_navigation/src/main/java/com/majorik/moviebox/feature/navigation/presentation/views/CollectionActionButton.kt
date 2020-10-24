package com.majorik.moviebox.feature.navigation.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.majorik.moviebox.feature.navigation.R
import kotlinx.android.synthetic.main.layout_collection_action_button.view.*

class CollectionActionButton : LinearLayout {

    var buttonTitle: String = ""
        set(value) {
            field = value
            button_title.text = value
        }

    var buttonOptionTitle: String? = ""
        set(value) {
            field = value

            button_option_title.text = value
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
        LayoutInflater.from(context).inflate(R.layout.layout_collection_action_button, this, true)

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
