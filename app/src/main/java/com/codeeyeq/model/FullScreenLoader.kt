package com.codeeyeq.model

import android.animation.Animator
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.RelativeLayout
import com.codeeyeq.R

@SuppressLint("InflateParams")
class FullScreenLoader(var context: Context) : Animator.AnimatorListener {
    private var img: ImageView
    private var animate: ObjectAnimator
    private var dimension: Int = 100
    private var index: Int = 0
    private lateinit var rlp: RelativeLayout.LayoutParams
    private var languages: ArrayList<Int> = ArrayList()
    private var popupWindow: PopupWindow
    private var parent: RelativeLayout

    @SuppressLint("InflateParams")
    private var view: View

    init {
        languages.add(R.drawable.languages_angularjs)
        languages.add(R.drawable.languages_c_sharp_logo)
        languages.add(R.drawable.languages_c)
        languages.add(R.drawable.languages_c_plus_plus)
        languages.add(R.drawable.languages_css3_logo)
        languages.add(R.drawable.languages_django)
        languages.add(R.drawable.languages_drupal)
        languages.add(R.drawable.languages_flutter)
        languages.add(R.drawable.languages_html_5)
        languages.add(R.drawable.languages_java)
        languages.add(R.drawable.languages_javascript_logo)
        languages.add(R.drawable.languages_jewel)
        languages.add(R.drawable.languages_kotlin)
        languages.add(R.drawable.languages_mysql_logo)
        languages.add(R.drawable.languages_nodejs)
        languages.add(R.drawable.languages_php_logo)
        languages.add(R.drawable.languages_python)
        languages.add(R.drawable.languages_r_project)
        languages.add(R.drawable.languages_raspberry_pi)
        languages.add(R.drawable.languages_sass)
        languages.add(R.drawable.languages_swift)
        languages.add(R.drawable.languages_xml)

        view =
            LayoutInflater.from(context).inflate(R.layout.content_full_screen_loader, null, false)
        parent = view.findViewById(R.id.parent)
        img = view.findViewById(R.id.img)
        animate = ObjectAnimator.ofFloat(img, "rotation", 0f, 360f)
        popupWindow = PopupWindow(
            view,
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
    }

    @SuppressLint("InflateParams")
    fun show() {
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        animate.duration = 100
        animate.repeatCount = ObjectAnimator.INFINITE
        animate.addListener(this)
        animate.start()
    }

    override fun onAnimationStart(p0: Animator?) {
        shrink()
    }

    override fun onAnimationEnd(p0: Animator?) {

    }

    override fun onAnimationCancel(p0: Animator?) {

    }

    override fun onAnimationRepeat(p0: Animator?) {

    }

    private fun shrink() {
        if (dimension != 0) {
            dimension -= 10
            val scale = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                dimension.toFloat(),
                context.resources.displayMetrics
            ).toInt()
            rlp = RelativeLayout.LayoutParams(scale, scale)
            rlp.addRule(RelativeLayout.CENTER_IN_PARENT)
            img.layoutParams = rlp
            Handler(Looper.getMainLooper()).postDelayed({ shrink() }, 100)
        } else {
            if (index != 21) {
                index++
            } else {
                index = 0
            }
            img.setImageResource(languages[index])
            Handler(Looper.getMainLooper()).postDelayed({ grow() }, 1000)
        }
    }

    private fun grow() {
        if (dimension != 100) {
            dimension += 10
            val scale = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                dimension.toFloat(),
                context.resources.displayMetrics
            ).toInt()
            rlp = RelativeLayout.LayoutParams(scale, scale)
            rlp.addRule(RelativeLayout.CENTER_IN_PARENT)
            img.layoutParams = rlp
            Handler(Looper.getMainLooper()).postDelayed({ grow() }, 100)
        } else {
            animate.end()
            animate.startDelay = 1000
            animate.duration = 100
            animate.repeatCount = ObjectAnimator.INFINITE
            animate.start()
        }
    }

    fun isShowing(): Boolean {
        return popupWindow.isShowing
    }

    fun hide() {
        animate.removeAllListeners()
        animate.end()
        animate.cancel()
        popupWindow.dismiss()
    }
}