package com.jianyuyouhun.mobile.animateddialog

import android.content.Context
import android.os.Bundle
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}