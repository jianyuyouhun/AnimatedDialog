package com.jianyuyouhun.mobile.animateddialog

import android.content.Context
import com.jianyuyouhun.mobile.animateddialog.library.BaseAnimatedDialog

class TestDialog(
    context: Context,
    style: Int = R.style.AnimatedDialogTheme
) : BaseAnimatedDialog(
    context,
    ContentViewGravity.BOTTOM,
    EnterAnimationType.FROM_DOWN,
    ExitAnimationType.TO_DOWN,
    style
) {
    override fun getLayoutId(): Int = R.layout.dialog_test
}