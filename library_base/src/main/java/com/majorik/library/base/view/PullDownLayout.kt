package com.majorik.library.base.view

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewConfiguration
import android.widget.FrameLayout
import androidx.annotation.NonNull
import androidx.core.view.ViewCompat
import androidx.customview.widget.ViewDragHelper
import kotlin.math.max

class PullDownLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val draggerCallback = ViewDragCallback()
    private val dragger = ViewDragHelper.create(this, 1f, draggerCallback)
    private val minimumFlingVelocity = ViewConfiguration.get(context).scaledMinimumFlingVelocity
    private var callback: Callback? = null

    fun dismissLayoutProgrammatically() {
        draggerCallback.closeDownAndDismiss(this)
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean =
        if (ev.pointerCount > 1 || !isEnabled || callback?.pullDownReady() != true) {
            false
        } else {
            dragger.shouldInterceptTouchEvent(ev)
        }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(@NonNull event: MotionEvent): Boolean =
        if (event.pointerCount > 1 || !isEnabled) false
        else {
            try {
                dragger.processTouchEvent(event)
            } catch (e: Exception) {
            }

            true
        }

    override fun computeScroll() {
        if (dragger.continueSettling(true)) ViewCompat.postInvalidateOnAnimation(this)
    }

    interface Callback {
        fun onPullStart()
        fun onPull(progress: Float)
        fun onPullCancel()
        fun onPullComplete()

        fun pullDownReady(): Boolean
    }

    private inner class ViewDragCallback : ViewDragHelper.Callback() {

        override fun tryCaptureView(child: View, pointerId: Int): Boolean = true

        override fun clampViewPositionHorizontal(child: View, left: Int, dx: Int): Int = 0

        override fun clampViewPositionVertical(child: View, top: Int, dy: Int): Int = max(0, top)

        override fun getViewHorizontalDragRange(child: View): Int = 0

        override fun getViewVerticalDragRange(child: View): Int = height

        override fun onViewCaptured(capturedChild: View, activePointerId: Int) {
            callback?.onPullStart()
        }

        override fun onViewPositionChanged(
            changedView: View,
            left: Int,
            top: Int,
            dx: Int,
            dy: Int
        ) {
            callback?.onPull(top.toFloat() / height.toFloat())
        }

        override fun onViewReleased(releasedChild: View, xvel: Float, yvel: Float) {
            val threshold = if (yvel > minimumFlingVelocity) height / 6 else height / 3
            if (releasedChild.top > threshold) {
                closeDownAndDismiss(releasedChild)
            } else {
                callback?.onPullCancel()
                dragger.settleCapturedViewAt(0, 0)
                invalidate()
            }
        }

        fun closeDownAndDismiss(root: View) {
            val start = root.y
            val finish = root.measuredHeight.toFloat()
            val positionAnimator = ObjectAnimator.ofFloat(root, "y", start, finish)
            positionAnimator.duration = 100
            positionAnimator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator) {}

                override fun onAnimationEnd(animation: Animator) {
                    callback?.onPullComplete()
                }

                override fun onAnimationCancel(animation: Animator) {}

                override fun onAnimationStart(animation: Animator) {}
            })
            positionAnimator.start()
        }
    }
}
