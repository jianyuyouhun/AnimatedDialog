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
import com.jianyuyouhun.mobile.animateddialog.library.annotation.AnimatorConfig
import com.jianyuyouhun.mobile.animateddialog.library.config.ContentViewGravity
import com.jianyuyouhun.mobile.animateddialog.library.config.EnterAnimationType
import com.jianyuyouhun.mobile.animateddialog.library.config.ExitAnimationType
import com.jianyuyouhun.mobile.animateddialog.library.creator.AnimatorAttr
import com.jianyuyouhun.mobile.animateddialog.library.creator.AnimatorCreator
import com.jianyuyouhun.mobile.animateddialog.library.creator.DefaultAnimatorCreator

abstract class BaseAnimatedDialog(
    context: Context,
    style: Int = R.style.AnimatedDialogTheme
) : Dialog(context, style) {

    private val handler by lazy { Handler(Looper.getMainLooper()) }
    private var animatorSet: AnimatorSet? = null
    var bgColor: Int = Color.parseColor("#55000000")
    var animationDuration = 300L
    private var canCancelByTouchOutside = true

    private lateinit var animatorCreator: AnimatorCreator
    private lateinit var contentViewGravity: ContentViewGravity
    private lateinit var enterAnimation: EnterAnimationType
    private lateinit var exitAnimation: ExitAnimationType

    init {
        var cls: Class<*>? = this.javaClass
        var hasFoundAnnotation = false
        while (cls != null && !hasFoundAnnotation) {
            val animatedConfig = cls.getAnnotation(AnimatorConfig::class.java)
            if (animatedConfig == null) {
                cls = cls.superclass
                hasFoundAnnotation = false
            } else {
                hasFoundAnnotation = true
                try {
                    animatorCreator = animatedConfig.animatorCreator.java.newInstance()
                    contentViewGravity = animatedConfig.contentGravity
                    enterAnimation = animatedConfig.enterType
                    exitAnimation = animatedConfig.exitType
                } catch (e: InstantiationException) {
                    throw RuntimeException(e)
                } catch (e: IllegalAccessException) {
                    throw RuntimeException(e)
                }
            }
        }
        if (!hasFoundAnnotation) {
            animatorCreator = DefaultAnimatorCreator()
            contentViewGravity = ContentViewGravity.CENTER
            enterAnimation = EnterAnimationType.FADE_IN
            exitAnimation = ExitAnimationType.FADE_OUT
        }
    }

    private lateinit var baseAllContainer: RelativeLayout
    private lateinit var baseBgView: View
    private lateinit var itemContainer: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        super.setContentView(R.layout.dialog_base_animated_layout)
        baseAllContainer = findViewById(R.id.base_all_container)
        baseBgView = findViewById(R.id.base_bg_view)
        baseBgView.setBackgroundColor(bgColor)
        itemContainer = findViewById(R.id.base_item_container)
        itemContainer.removeAllViews()
        itemContainer.layoutParams = createLayoutParams()
        itemContainer.addView(itemContainer.inflate(getLayoutId()))
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
                val translationAnimators = getEnterAnimation(itemContainer)
                duration = animationDuration
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        animatorSet = null
                    }
                })
                val animatorList = ArrayList(translationAnimators)
                animatorList.add(alphaAnimator)
                playTogether(animatorList)
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
            val translationAnimators = getExitAnimation(itemContainer)
            duration = animationDuration
            addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    animatorSet = null
                    itemContainer.visibility = View.INVISIBLE
                    superDismiss()
                }
            })
            val animatorList = ArrayList(translationAnimators)
            animatorList.add(alphaAnimator)
            playTogether(animatorList)
            start()
        }
    }

    private fun superDismiss() {
        if (ownerActivity?.isFinishing == true) {
            return
        }
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
        return RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
    }

    private fun getEnterAnimation(view: View): List<Animator> {
        var fromX = 0F
        var fromY = 0F
        val toX = 0F
        val toY = 0F
        when (enterAnimation) {
            EnterAnimationType.FROM_UP -> {
                fromY = -view.height.toFloat() + (-view.top.toFloat())
            }
            EnterAnimationType.FROM_DOWN -> {
                fromY = view.height.toFloat() + (baseAllContainer.height.toFloat() - view.bottom.toFloat())
            }
            EnterAnimationType.FROM_LEFT -> {
                fromX = -view.width.toFloat() + (-view.left.toFloat())
            }
            EnterAnimationType.FROM_RIGHT -> {
                fromX = view.width.toFloat() + (baseAllContainer.width.toFloat() - view.right.toFloat())
            }
            else -> {
            }
        }
        return animatorCreator.onCreateEnterAnimator(view, AnimatorAttr(fromX, fromY, toX, toY), enterAnimation)
    }

    private fun getExitAnimation(view: View): List<Animator> {
        val fromX = 0F
        val fromY = 0F
        var toX = 0F
        var toY = 0F
        when (exitAnimation) {
            ExitAnimationType.TO_UP -> {
                toY = -view.height.toFloat() + (-view.top.toFloat())
            }
            ExitAnimationType.TO_DOWN -> {
                toY = view.height.toFloat() + (baseAllContainer.height.toFloat() - view.bottom.toFloat())
            }
            ExitAnimationType.TO_LEFT -> {
                toX = -view.width.toFloat() + (-view.left.toFloat())
            }
            ExitAnimationType.TO_RIGHT -> {
                toX = view.width.toFloat() + (baseAllContainer.width.toFloat() - view.right.toFloat())
            }
            else -> {
            }
        }
        return animatorCreator.onCreateExitAnimator(view, AnimatorAttr(fromX, fromY, toX, toY), exitAnimation)
    }

    //根据id生成填充view并返回
    private fun ViewGroup.inflate(layoutRes: Int): View {
        return LayoutInflater.from(context).inflate(layoutRes, this, false)
    }

}