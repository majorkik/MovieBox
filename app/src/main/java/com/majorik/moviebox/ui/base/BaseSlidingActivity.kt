package com.majorik.moviebox.ui.base

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.Point
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.absoluteValue

abstract class BaseSlidingActivity : AppCompatActivity() {
    private lateinit var root: View
    private var startX = 0f
    private var startY = 0f
    private var isSliding = false
    private val threshold = 10
    private lateinit var screenSize: Point

    abstract fun getRootView(): View

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        root = getRootView()
        screenSize = Point().apply { windowManager.defaultDisplay.getSize(this) }
    }

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        var handled = false

        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                // запоминаем точку старта
                startX = ev.x
                startY = ev.y
            }

            MotionEvent.ACTION_MOVE -> {

                // нужно определить, является ли текущий жест "смахиванием вниз"
                if ((isSlidingDown(startX, startY, ev) && canSlideDown()) || isSliding) {
                    if (!isSliding) {
                        // момент, когда мы определили, что польователь "смахивает" экран
                        // начиная с этого жеста все последующие ACTION_MOVE мы будем
                        // воспринимать как "смахивание"
                        isSliding = true
                        onSlidingStarted()

                        // сообщим всем остальным обработчикам, что жест закончился
                        // и им не нужно больше ничего обрабатывать
                        ev.action = MotionEvent.ACTION_CANCEL
                        super.dispatchTouchEvent(ev)
                    }

                    // переместим контейнер на соответсвующую Y координату
                    // но не выше, чем точка старта
                    root.y = (ev.y - startY).coerceAtLeast(0f)

                    handled = true
                }
            }

            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                if (isSliding) {
                    // если пользователь пытался "смахнуть" экран...
                    isSliding = false
                    onSlidingFinished()
                    handled = true
                    if (shouldClose(ev.y - startY)) {
                        // закрыть экран
                        closeDownAndDismiss()
                    } else {
                        // вернуть все как было
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
        if (deltaX > threshold) return false
        val deltaY = ev.y - startY
        return deltaY > threshold
    }

    private fun closeDownAndDismiss() {
        val start = root.y
        val finish = screenSize.y.toFloat()
        val positionAnimator = ObjectAnimator.ofFloat(root, "y", start, finish)
        positionAnimator.duration = 100
        positionAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator) {}

            override fun onAnimationEnd(animation: Animator) {
                finish()
            }

            override fun onAnimationCancel(animation: Animator) {}

            override fun onAnimationStart(animation: Animator) {}

        })
        positionAnimator.start()
    }

    private fun shouldClose(delta: Float): Boolean {
        return delta > screenSize.y / 6
    }

    abstract fun onSlidingStarted()

    abstract fun onSlidingFinished()

    abstract fun canSlideDown(): Boolean
}