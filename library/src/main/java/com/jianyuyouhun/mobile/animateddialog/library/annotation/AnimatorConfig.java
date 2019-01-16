package com.jianyuyouhun.mobile.animateddialog.library.annotation;

import com.jianyuyouhun.mobile.animateddialog.library.config.ContentViewGravity;
import com.jianyuyouhun.mobile.animateddialog.library.config.EnterAnimationType;
import com.jianyuyouhun.mobile.animateddialog.library.config.ExitAnimationType;
import com.jianyuyouhun.mobile.animateddialog.library.creator.AnimatorCreator;
import com.jianyuyouhun.mobile.animateddialog.library.creator.DefaultAnimatorCreator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AnimatorConfig {
    ContentViewGravity contentGravity() default ContentViewGravity.CENTER;

    EnterAnimationType enterType() default EnterAnimationType.FADE_IN;

    ExitAnimationType exitType() default ExitAnimationType.FADE_OUT;

    Class<? extends AnimatorCreator> animatorCreator() default DefaultAnimatorCreator.class;

}
