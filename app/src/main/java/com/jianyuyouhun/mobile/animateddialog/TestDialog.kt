package com.jianyuyouhun.mobile.animateddialog

import android.content.Context
import android.os.Bundle
import android.widget.RelativeLayout
import com.jianyuyouhun.mobile.animateddialog.library.BaseAnimatedDialog
import com.jianyuyouhun.mobile.animateddialog.library.annotation.AnimatorConfig
import com.jianyuyouhun.mobile.animateddialog.library.config.ContentViewGravity
import com.jianyuyouhun.mobile.animateddialog.library.config.EnterAnimationType
import com.jianyuyouhun.mobile.animateddialog.library.config.ExitAnimationType

@AnimatorConfig(
    contentGravity = ContentViewGravity.BOTTOM,
    enterType = EnterAnimationType.FROM_DOWN,
    exitType = ExitAnimationType.TO_DOWN,
    animatorCreator = ScaleAnimatorCreator::class
)
class TestDialog(context: Context) : BaseAnimatedDialog(context) {
    override fun getLayoutId(): Int = R.layout.dialog_test

    override fun createLayoutParams(): RelativeLayout.LayoutParams {
        val horizontal = 80
        val vertical = 120
        val layoutParams = RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.leftMargin = horizontal
        layoutParams.rightMargin = horizontal
        layoutParams.topMargin = vertical
        layoutParams.bottomMargin = vertical
        return layoutParams
    }
}