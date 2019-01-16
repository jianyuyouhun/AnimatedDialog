package com.jianyuyouhun.mobile.animateddialog

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.jianyuyouhun.mobile.animateddialog.library.config.EnterAnimationType
import com.jianyuyouhun.mobile.animateddialog.library.config.ExitAnimationType
import com.jianyuyouhun.mobile.animateddialog.library.creator.AnimatorAttr
import com.jianyuyouhun.mobile.animateddialog.library.creator.AnimatorCreator

class ScaleAnimatorCreator : AnimatorCreator {
    override fun onCreateEnterAnimator(
        animatorView: View,
        attr: AnimatorAttr,
        enterAnimationType: EnterAnimationType
    ): List<Animator> {
        var animator1: Animator? = null
        var animator2: Animator? = null
        when (enterAnimationType) {
            EnterAnimationType.FROM_UP,
            EnterAnimationType.FROM_DOWN -> {
//                animator1 = ObjectAnimator.ofFloat(animatorView, View.TRANSLATION_Y, attr.fromY, attr.toY)
                animator2 = ObjectAnimator.ofFloat(animatorView, View.SCALE_Y, 0F, 1F)
            }
            EnterAnimationType.FROM_LEFT,
            EnterAnimationType.FROM_RIGHT -> {
//                animator1 = ObjectAnimator.ofFloat(animatorView, View.TRANSLATION_X, attr.fromX, attr.toX)
                animator2 = ObjectAnimator.ofFloat(animatorView, View.SCALE_X, 0F, 1F)
            }
            EnterAnimationType.FADE_IN -> {
                animator1 = ObjectAnimator.ofFloat(animatorView, View.ALPHA, 0F, 1F)
            }
        }

        return if (animator1 == null && animator2 == null) {
            emptyList()
        } else if (animator1 == null) {
            arrayListOf(animator2!!)
        } else if (animator2 == null) {
            arrayListOf(animator1)
        } else {
            arrayListOf(animator1, animator2)
        }
    }

    override fun onCreateExitAnimator(
        animatorView: View,
        attr: AnimatorAttr,
        exitAnimationType: ExitAnimationType
    ): List<Animator> {
        var animator1: Animator? = null
        var animator2: Animator? = null
        when (exitAnimationType) {
            ExitAnimationType.TO_UP,
            ExitAnimationType.TO_DOWN -> {
//                animator1 = ObjectAnimator.ofFloat(animatorView, View.TRANSLATION_Y, attr.fromY, attr.toY)
                animator2 = ObjectAnimator.ofFloat(animatorView, View.SCALE_Y, 1F, 0F)
            }
            ExitAnimationType.TO_LEFT,
            ExitAnimationType.TO_RIGHT -> {
//                animator1 = ObjectAnimator.ofFloat(animatorView, View.TRANSLATION_X, attr.fromX, attr.toX)
                animator2 = ObjectAnimator.ofFloat(animatorView, View.SCALE_X, 1F, 0F)
            }
            ExitAnimationType.FADE_OUT -> {
                animator1 = ObjectAnimator.ofFloat(animatorView, View.ALPHA, 1F, 0F)
            }
        }
        return if (animator1 == null && animator2 == null) {
            emptyList()
        } else if (animator1 == null) {
            arrayListOf(animator2!!)
        } else if (animator2 == null) {
            arrayListOf(animator1)
        } else {
            arrayListOf(animator1, animator2)
        }
    }
}