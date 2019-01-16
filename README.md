# AnimatedDialog

附带基础入场离场动画的dialog，减少重复代码

## 效果 ##

<img src="GIF.gif"/>

## 使用 ##

### 引入到工程 ###

#### Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

#### Step 2. Add the dependency ####

	dependencies {
    	implementation  'com.github.jianyuyouhun:AnimatedDialog:1.0.2'
	}

### 使用 ###

1、默认配置注解 

	@AnimatorConfig(
	    contentGravity = ContentViewGravity.BOTTOM,//默认为CETNER
	    enterType = EnterAnimationType.FROM_DOWN,//默认为FADE_IN
	    exitType = ExitAnimationType.TO_DOWN,//默认为FADE_OUT
		animatorCreator = DefaultAnimatorCreator::class//默认为DefaultAnimatorCreator
	)
	class TestDialog(
	    context: Context,
	    style: Int = R.style.AnimatedDialogTheme//可传递其他style来修改主体，默认主题为全屏模式
	) : BaseAnimatedDialog(
	    context,
	    style
	) {
	    override fun getLayoutId(): Int = R.layout.dialog_test
	}

ContentViewGravity定义：
	
	
    enum class ContentViewGravity {
        TOP,
        CENTER,
        BOTTOM,
        LEFT,
        RIGHT
    }

EnterAnimationType定义：

    enum class EnterAnimationType {
        FROM_DOWN,//从下方进入
        FROM_UP,//从上方进入
        FROM_LEFT,//从左侧进入
        FROM_RIGHT,//从右侧进入
        FADE_IN,//淡入
		CUSTOMER//自定义，采用此配置时需要自定义AnimatorCreator实现类
    }

ExitAnimationType定义：

    enum class ExitAnimationType {
        TO_DOWN,//下方离场
        TO_UP,//上方离场
        TO_LEFT,//左侧离场
        TO_RIGHT,//右侧离场
        FADE_OUT,//淡出
		CUSTOMER//自定义，采用此配置时需要自定义AnimatorCreator实现类
	}

2、自定义动画配置

实现AnimatorCreator接口，注意：当enterType或exitType设置为CUSTOMER时，对应的Attr配置值均为0。实际上enterType、exitType一般不采用CUSTOMER，可以直接重新实现一个AnimatorCreator来替换掉默认的实现。

### 一些配置 ###

1、 修改背景颜色

	bgColor = Color.parseColor("#55000000")//kotlin中
	setBgColor(Color.parseColor("#55000000"))//java中

2、 修改动画时长

	animationDuration = 300L //kotlin中
	setAnimationDuration(300) //java中
