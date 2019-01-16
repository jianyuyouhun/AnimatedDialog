package com.jianyuyouhun.mobile.animateddialog.library.creator

import android.animation.Animator
import android.view.View
import com.jianyuyouhun.mobile.animateddialog.library.config.EnterAnimationType
import com.jianyuyouhun.mobile.animateddialog.library.config.ExitAnimationType

/**
 * 动画生成器接口
 */
interface AnimatorCreator {
    fun onCreateEnterAnimator(animatorView: View, attr: AnimatorAttr, enterAnimationType: EnterAnimationType): List<Animator>
    fun onCreateExitAnimator(animatorView: View, attr: AnimatorAttr, exitAnimationType: ExitAnimationType): List<Animator>
}