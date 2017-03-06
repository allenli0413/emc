package com.mayigeek.nestrefreshlayout

import cn.appsdream.nestrefresh.base.AbsRefreshLayout
import cn.appsdream.nestrefresh.base.OnPullListener
import com.mayigeek.frame.http.ApiManager
import com.mayigeek.frame.http.state.HttpError
import com.mayigeek.frame.http.state.HttpSucess
import com.mayigeek.frame.view.state.ViewControl
import rx.Observable
import java.util.*

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: 上拉刷新，下拉更多管理
 * @date 16-9-2 下午12:14
 */
class NestRefreshManager<T>(val refreshLayout: AbsRefreshLayout, val viewControl: ViewControl, val createApi: CreateApi<T>) {

    private var page = 0
    private var perPage = 10
    private val list = ArrayList<T>()

    var httpError: HttpError? = null
    var callBack: CallBack<T> ? = null

    /**
     *根据page和perPage创建Api
     * */
    interface CreateApi<T> {
        fun run(page: Int, perPage: Int): Observable<List<T>>
    }

    fun setPerPage(perPage : Int){
        this.perPage = perPage
    }

    /**
     * 数据成功后的返回回调
     * */
    interface CallBack <T> {
        /**
         * 返回所有数据和当前请求回来的数据
         * */
        fun call(allList: List<T>, currentList: List<T>)
    }

    init {
        setPullRefreshEnable(true)
        setPullLoadEnable(false)
        refreshLayout.setOnLoadingListener(object : OnPullListener {
            override fun onLoading(listLoader: AbsRefreshLayout?) {
                getData(listLoader, false)
            }

            override fun onRefresh(listLoader: AbsRefreshLayout?) {
                getData(listLoader, true)
            }

        })
    }


    fun setPullRefreshEnable(enable: Boolean) {
        refreshLayout.setPullRefreshEnable(enable)
    }

    fun setPullLoadEnable(enable: Boolean) {
        refreshLayout.setPullLoadEnable(enable)
    }

    /**
     *执行Api
     * */
    fun doApi() {
        getData(null, true)
    }

    private fun getData(listLoader: AbsRefreshLayout?, isRefresh: Boolean) {
        var vc: ViewControl? = null
        if (listLoader == null) {
            vc = viewControl
        }
        if (isRefresh) {
            refreshLayout.onReset()
            page = 0
        }
        val api: Observable<List<T>> = createApi.run(page, perPage)
        val httpSucess = object : HttpSucess<List<T>> {
            override fun onSucess(data: List<T>) {
                page++
                if (isRefresh) {
                    list.clear()
                }
                list.addAll(data)
                //回调告诉数据成功返回
                callBack?.call(list, data)
                if (listLoader == null) {
                    if (data.size < perPage) {
                        refreshLayout.onLoadAll()
                    }
                }
                if (listLoader != null) {
                    if (data.size < perPage) {
                        listLoader.onLoadAll()
                    } else {
                        listLoader.onLoadFinished()
                    }
                }

            }

        }
        ApiManager.doApi(api = api,
                httpError = httpError,
                httpFinish = null,
                viewControl = vc,
                httpSucess = httpSucess)

    }

}

