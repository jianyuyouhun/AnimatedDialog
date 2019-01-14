package com.jianyuyouhun.mobile.animateddialog.library

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout

abstract class BaseAnimatedDialog(context: Context,
                         private val contentViewGravity: ContentViewGravity = ContentViewGravity.CENTER,
                         private val enterAnimation: EnterAnimationType = EnterAnimationType.FROM_DOWN,
                         private val exitAnimation: ExitAnimationType = ExitAnimationType.TO_DOWN,
                         style: Int = R.style.AnimatedDialogTheme) : Dialog(context, style) {
    private lateinit var baseAllContainer: RelativeLayout
    private lateinit var baseBgView : View
    private lateinit var itemContainer : RelativeLayout

    private val handler by lazy { Handler(Looper.getMainLooper()) }
    private var animatorSet: AnimatorSet? = null
    private var bgColor:Int = Color.parseColor("#55000000")
    private var animationDuration = 300L

    private var canCancelByTouchOutside = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.dialog_base_animated_layout)
        baseAllContainer = findViewById(R.id.base_all_container)
        baseBgView = findViewById(R.id.base_bg_view)
        baseBgView.setBackgroundColor(bgColor)
        itemContainer = findViewById(R.id.base_item_container)
        itemContainer.removeAllViews()
        itemContainer.layoutParams = createLayoutParams()
        itemContainer.addView(itemContainer.inflate(getLayoutId()), createLayoutParams())
        (itemContainer.layoutParams as RelativeLayout.LayoutParams).apply {
            when (contentViewGravity) {
                ContentViewGravity.CENTER -> {
                    addRule(RelativeLayout.CENTER_IN_PARENT)
                }
                ContentViewGravity.BOTTOM -> {
                    addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                }
                ContentViewGravity.TOP -> {
                    addRule(RelativeLayout.ALIGN_PARENT_TOP)
                }
                ContentViewGravity.LEFT -> {
                    addRule(RelativeLayout.ALIGN_PARENT_LEFT)
                }
                ContentViewGravity.RIGHT -> {
                    addRule(RelativeLayout.ALIGN_PARENT_RIGHT)
                }
            }
        }
        baseBgView.setOnClickListener {
            if (canCancelByTouchOutside) {
                dismiss()
            }
        }
        itemContainer.setOnClickListener { }
    }

    override fun setCanceledOnTouchOutside(cancel: Boolean) {
        super.setCanceledOnTouchOutside(cancel)
        canCancelByTouchOutside = cancel
    }

    override fun show() {
        if (animatorSet != null) return
        super.show()
        itemContainer.post {
            itemContainer.visibility = View.VISIBLE
            animatorSet = AnimatorSet()
            animatorSet!!.apply {
                val alphaAnimator = ObjectAnimator.ofFloat(baseBgView, View.ALPHA, 0F, 1F)
                val translationAnimator = getEnterAnimation(itemContainer)
                duration = animationDuration
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        animatorSet = null
                    }
                })
                playTogether(alphaAnimator, translationAnimator)
                handler.post {
                    start()
                }
            }
        }
    }

    override fun dismiss() {
        if (animatorSet != null) return
        animatorSet = AnimatorSet()
        animatorSet!!.apply {
            val alphaAnimator = ObjectAnimator.ofFloat(baseBgView, View.ALPHA, 1F, 0F)
            val translationAnimator = getExitAnimation(itemContainer)
            duration = animationDuration
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    animatorSet = null
                    itemContainer.visibility = View.INVISIBLE
                    superDismiss()
                }
            })
            playTogether(alphaAnimator, translationAnimator)
            start()
        }
    }

    private fun superDismiss() {
        super.dismiss()
    }

    @Deprecated("使用getLayoutId()", ReplaceWith("super.setContentView(layoutResID)"))
    override fun setContentView(layoutResID: Int) {
        throw RuntimeException("使用getLayoutId()")
    }

    @Deprecated("使用getLayoutId()", ReplaceWith("super.setContentView(layoutResID)"))
    override fun setContentView(view: View) {
        throw RuntimeException("使用getLayoutId()")
    }

    @Deprecated("使用getLayoutId()", ReplaceWith("super.setContentView(layoutResID)"))
    override fun setContentView(view: View, params: ViewGroup.LayoutParams?) {
        throw RuntimeException("使用getLayoutId()")
    }

    abstract fun getLayoutId(): Int

    open fun createLayoutParams(): RelativeLayout.LayoutParams {
        return RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
    }

    enum class EnterAnimationType {
        FROM_DOWN,
        FROM_UP,
        FROM_LEFT,
        FROM_RIGHT,
        FADE_IN,
    }

    enum class ExitAnimationType {
        TO_DOWN,
        TO_UP,
        TO_LEFT,
        TO_RIGHT,
        FADE_OUT
    }

    enum class ContentViewGravity {
        TOP,
        CENTER,
        BOTTOM,
        LEFT,
        RIGHT
    }

    private fun getEnterAnimation(view: View): Animator = when (enterAnimation) {
        EnterAnimationType.FROM_UP -> {
            ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, -view.height.toFloat() + (-view.top.toFloat()), 0F)
        }
        EnterAnimationType.FROM_DOWN -> {
            ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, view.height.toFloat() + (baseAllContainer.height.toFloat() - view.bottom.toFloat()), 0F)
        }
        EnterAnimationType.FROM_LEFT -> {
            ObjectAnimator.ofFloat(view, View.TRANSLATION_X, -view.width.toFloat() + (-view.left.toFloat()), 0F)
        }
        EnterAnimationType.FROM_RIGHT -> {
            ObjectAnimator.ofFloat(view, View.TRANSLATION_X, view.width.toFloat() + (baseAllContainer.width.toFloat() - view.right.toFloat()), 0F)
        }
        EnterAnimationType.FADE_IN -> {
            ObjectAnimator.ofFloat(view, View.ALPHA, 0F, 1F)
        }
    }

    private fun getExitAnimation(view: View): Animator = when (exitAnimation) {
        ExitAnimationType.TO_UP -> {
            ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0F, -view.height.toFloat() + (-view.top.toFloat()))
        }
        ExitAnimationType.TO_DOWN -> {
            ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0F, view.height.toFloat() + (baseAllContainer.height.toFloat() - view.bottom.toFloat()))
        }
        ExitAnimationType.TO_LEFT -> {
            ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0F, -view.width.toFloat() + (-view.left.toFloat()))
        }
        ExitAnimationType.TO_RIGHT -> {
            ObjectAnimator.ofFloat(view, View.TRANSLATION_X, 0F, view.width.toFloat() + (baseAllContainer.width.toFloat() - view.right.toFloat()))
        }
        ExitAnimationType.FADE_OUT -> {
            ObjectAnimator.ofFloat(view, View.ALPHA, 1F, 0F)
        }
    }

    //根据id生成填充view并返回
    private fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }

}