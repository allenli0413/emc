package com.mayigeek.frame.view.state

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 控制界面变化
 * @date 16-8-31 下午4:21
 */
class SimpleViewControl(private val view: View, viewHttpState: ViewHttpState,errorText:String,emptyText:String,id:Int, needError: Boolean) {

    private val loadingView: View
    private val emptyView: View
    private val errorView: View
    private var needError: Boolean = true

    init {
        this.needError = needError
        val context = view.context
        val parentView = FrameLayout(context)
        val layoutParams = view.layoutParams
        val originalParent = view.parent as ViewGroup
        originalParent.removeView(view)
        parentView.addView(view, layoutParams)
        loadingView = viewHttpState.loadingView(parentView)
        errorView = viewHttpState.errorView(parentView,errorText,id)
        emptyView = viewHttpState.emptyView(parentView,emptyText,id)
        parentView.addView(errorView)
        parentView.addView(emptyView)
        parentView.addView(loadingView)
        originalParent.addView(parentView, layoutParams)
    }

    fun build(): ViewControl {
        return object : ViewControl {
            override fun onEmpty() {
                emptyView.visibility = View.VISIBLE
            }

            override fun onStart() {
                view.visibility = View.GONE
                loadingView.visibility = View.VISIBLE
                errorView.visibility = View.GONE
                emptyView.visibility = View.GONE
            }

            override fun onComplete() {
                view.visibility = View.VISIBLE
            }

            override fun onError() {
                if(needError) {
                    errorView.visibility = View.VISIBLE
                } else {
                    view.visibility = View.VISIBLE
                }
            }

            override fun onFinish() {
                loadingView.visibility = View.GONE
            }

        }
    }

}