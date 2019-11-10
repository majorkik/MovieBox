package com.majorik.moviebox.ui.movieDetails

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.Point
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import kotlin.math.absoluteValue

abstract class SlidingMovieDetailsActivity : AppCompatActivity() {

    private lateinit var root: View
    private var startX = 0f
    private var startY = 0f
    private var isSliding = false
    private val GESTURE_THRESHOLD = 10
    private lateinit var screenSize: Point

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)
        root = getRootView()
        screenSize = Point().apply { windowManager.defaultDisplay.getSize(this) }
    }

    abstract fun getRootView(): View

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        var handled = false

        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = ev.x
                startY = ev.y
            }

            MotionEvent.ACTION_MOVE -> {
                if ((isSlidingDown(startX, startY, ev) && canSlideDown()) || isSliding) {
                    if (!isSliding) {
                        isSliding = true
                        onSlidingStarted()
                        ev.action = MotionEvent.ACTION_CANCEL
                        super.dispatchTouchEvent(ev)
                    }

                    root.y = (ev.y - startY).coerceAtLeast(0f)

                    handled = true
                }
            }

            MotionEvent.ACTION_UP -> {
                if (isSliding) {
                    isSliding = false
                    onSlidingFinished()
                    handled = true
                    if (shouldClose(ev.y - startY)) {

                    } else {
                        root.y = 0f
                    }
                }
                startX = 0f
                startY = 0f
            }
        }

        return if (handled) true else super.dispatchTouchEvent(ev)
    }

    private fun isSlidingDown(startX: Float, startY: Float, ev: MotionEvent): Boolean {
        val deltaX = (startX - ev.x).absoluteValue
        if (deltaX > GESTURE_THRESHOLD) return false
        val deltaY = ev.y - startY
        return deltaY > GESTURE_THRESHOLD
    }

    abstract fun onSlidingFinished()

    abstract fun onSlidingStarted()

    abstract fun canSlideDown(): Boolean

    private fun shouldClose(delta: Float): Boolean {
        return delta > screenSize.y / 3
    }

    private fun closeDownAndDismiss() {
        val start = root.y
        val finish = screenSize.y.toFloat()
        val positionAnimator = ObjectAnimator.ofFloat(root, "y", start, finish)
        positionAnimator.duration = 100
        positionAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {
            }

            override fun onAnimationEnd(p0: Animator?) {
                finish()
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {
            }
        })
        positionAnimator.start()
    }

}