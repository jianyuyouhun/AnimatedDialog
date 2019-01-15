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
    	implementation  'com.github.jianyuyouhun:AnimatedDialog:1.0.1'
	}

### 使用 ###

	class TestDialog(
	    context: Context,
	    style: Int = R.style.AnimatedDialogTheme//可传递其他style来修改主体，默认主题为全屏模式
	) : BaseAnimatedDialog(
	    context,
	    ContentViewGravity.BOTTOM,//contentView的位置
	    EnterAnimationType.FROM_DOWN,//入场动画
	    ExitAnimationType.TO_DOWN,//离场动画
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
        FADE_IN//淡入
    }

ExitAnimationType定义：

    enum class ExitAnimationType {
        TO_DOWN,//下方离场
        TO_UP,//上方离场
        TO_LEFT,//左侧离场
        TO_RIGHT,//右侧离场
        FADE_OUT//淡出
    }

### 一些配置 ###

1、 修改背景颜色

	bgColor = Color.parseColor("#55000000")//kotlin中
	setBgColor(Color.parseColor("#55000000"))//java中

2、 修改动画时长

	animationDuration = 300L //kotlin中
	setAnimationDuration(300) //java中

### 注意 ###

在java中使用时继承需要传递所有配置，kotlin中不传则采用默认配置，分别是
**CENTER, FROM\_DOWN, TO\_DOWN**