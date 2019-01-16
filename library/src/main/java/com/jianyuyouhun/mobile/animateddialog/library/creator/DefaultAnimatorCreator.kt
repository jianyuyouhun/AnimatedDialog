package com.jianyuyouhun.mobile.animateddialog.library.creator

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.jianyuyouhun.mobile.animateddialog.library.config.EnterAnimationType
import com.jianyuyouhun.mobile.animateddialog.library.config.ExitAnimationType

/**
 * 默认动画生成器
 */
class DefaultAnimatorCreator : AnimatorCreator {
    override fun onCreateEnterAnimator(
        animatorView: View,
        attr: AnimatorAttr,
        enterAnimationType: EnterAnimationType
    ): List<Animator> {
        val animator = when (enterAnimationType) {
            EnterAnimationType.FROM_UP,
            EnterAnimationType.FROM_DOWN -> {
                ObjectAnimator.ofFloat(animatorView, View.TRANSLATION_Y, attr.fromY, attr.toY)
            }
            EnterAnimationType.FROM_LEFT,
            EnterAnimationType.FROM_RIGHT -> {
                ObjectAnimator.ofFloat(animatorView, View.TRANSLATION_X, attr.fromX, attr.toX)
            }
            EnterAnimationType.FADE_IN -> {
                ObjectAnimator.ofFloat(animatorView, View.ALPHA, 0F, 1F)
            }
        }
        return arrayListOf(animator)
    }

    override fun onCreateExitAnimator(
        animatorView: View,
        attr: AnimatorAttr,
        exitAnimationType: ExitAnimationType
    ): List<Animator> {
        val animator = when (exitAnimationType) {
            ExitAnimationType.TO_UP,
            ExitAnimationType.TO_DOWN -> {
                ObjectAnimator.ofFloat(animatorView, View.TRANSLATION_Y, attr.fromY, attr.toY)
            }
            ExitAnimationType.TO_LEFT,
            ExitAnimationType.TO_RIGHT -> {
                ObjectAnimator.ofFloat(animatorView, View.TRANSLATION_X, attr.fromX, attr.toX)
            }
            ExitAnimationType.FADE_OUT -> {
                ObjectAnimator.ofFloat(animatorView, View.ALPHA, 1F, 0F)
            }
        }
        return arrayListOf(animator)
    }
}