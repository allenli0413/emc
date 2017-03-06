package com.mayigeek.frame.http

import com.mayigeek.frame.http.log.LogLevel
import com.mayigeek.frame.http.log.Logger
import com.mayigeek.frame.http.state.*
import com.mayigeek.frame.view.state.ViewControl
import okhttp3.OkHttpClient
import okio.Buffer
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.fastjson.FastJsonConverterFactory
import rx.Observable
import rx.Subscriber
import rx.Subscription
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import java.io.IOException
import java.io.InputStream
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

/**
 * @author renxiao@zhiwy.com
 * @version V1.0
 * @Description: Api管理，生成api，并调用
 * @date 16-8-24 上午11:32
 */
class ApiManager(private val baseUrl: String//指定的api接口地址
) {

    private val okHttpClient = OkHttpClient.Builder().build()
    var logger = Logger.DEFAULT//自定义log
    var logLevel = LogLevel.NONE//log显示格式
    private val logging: HttpLoggingInterceptor = HttpLoggingInterceptor() //中断，用于打印log和处理http的request和response

    var requestHook: RequestHook? = null//设置HttpRequest的hook
    var responseHook: ResponseHook? = null//设置HttpResponse的hook
    private val cerList = ArrayList<String>() //证书信息

    var hostnameVerify = true//是否忽略证书验证

    /**
     * 添加证书
     * */
    fun addCer(vararg cer: String) {
        cerList.addAll(cer)
    }


    /**
     * 生成指定的api
     */
    fun <T> createApi(service: Class<T>): T {
        logging.setLevel(logLevel)
        logging.setLogger(logger)
        logging.requestHook = requestHook
        logging.responseHook = responseHook

        val httpClientBuilder = okHttpClient.newBuilder()
        httpClientBuilder.addInterceptor(logging)
        httpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        //支持https证书
        if (cerList.isNotEmpty()) configCer(httpClientBuilder)
        //证书验证是否忽略
        if (!hostnameVerify) httpClientBuilder.hostnameVerifier({ s, sslSession -> true })
        val retrofit = Retrofit.Builder().client(httpClientBuilder.build()).baseUrl(baseUrl).addConverterFactory(FastJsonConverterFactory.create()).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build()
        return retrofit.create(service)
    }

    /**
     * 配置htts
     * */
    private fun configCer(httpClientBuilder: OkHttpClient.Builder) {
        configSSLSocketFactory(httpClientBuilder, cerList.map {
            Buffer()
                    .writeUtf8(it)
                    .inputStream()
        })

    }


    /**

     * 证书工厂，https的证书
     */
    private fun configSSLSocketFactory(httpClientBuilder: OkHttpClient.Builder, certificates: List<InputStream>) {
        try {
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null)
            var index = 0
            for (certificate in certificates) {
                val certificateAlias = Integer.toString(index++)
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate))
                try {
                    certificate.close()
                } catch (ignored: IOException) {
                }

            }
            val sslContext = SSLContext.getInstance("TLS")
            val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)
            sslContext.init(
                    null,
                    trustManagerFactory.trustManagers,
                    SecureRandom())
            val trustManager = trustManagerFactory.trustManagers[0] as X509TrustManager
            httpClientBuilder.sslSocketFactory(sslContext.socketFactory, trustManager)

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }


    companion object {


        /**
         * 执行Api
         */
        fun <T> doApi(api: Observable<T>, httpSucess: HttpSucess<T>?, httpError: HttpError?, httpFinish: HttpFinish?): Subscription {
            return api.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Subscriber<T>() {
                override fun onCompleted() {
                    httpFinish?.onFinish()
                }

                override fun onError(e: Throwable) {
                    httpError?.onError(e)
                    httpFinish?.onFinish()
                }

                override fun onNext(t: T) {
                    httpSucess?.onSucess(t)
                }
            })
        }

        /**
         * 执行Api,ViewControl控制
         */
        fun <T> doApi(api: Observable<T>, httpSucess: HttpSucess<T>?, httpError: HttpError?, httpFinish: HttpFinish?, viewControl: ViewControl?): Subscription {
            viewControl?.onStart()
            return api.subscribeOn(Schedulers.io()).unsubscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : Subscriber<T>() {
                override fun onCompleted() {
                    httpFinish?.onFinish()
                    viewControl?.onComplete()
                    viewControl?.onFinish()
                }

                override fun onError(e: Throwable) {
                    httpError?.onError(e)
                    httpFinish?.onFinish()
                    viewControl?.onError()
                    viewControl?.onFinish()
                }

                override fun onNext(t: T) {
                    try {
                        httpSucess?.onSucess(t)
                        if (t == null) {
                            viewControl?.onEmpty()
                        } else {
                            if (t is List<*> && t.isEmpty()) {
                                viewControl?.onEmpty()
                            } else {
                                viewControl?.onComplete()
                            }
                        }
                    } catch (e: Exception) {
                    }

                }
            })
        }
    }

}
