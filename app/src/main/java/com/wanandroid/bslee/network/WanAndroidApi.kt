package com.wanandroid.bslee.network

import androidx.lifecycle.LiveData
import com.supets.pet.mocklib.mock.TuziMockManager
import com.wanandroid.bslee.AppContext
import com.wanandroid.bslee.BuildConfig
import com.wanandroid.bslee.vo.*
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import java.io.File
import java.util.concurrent.TimeUnit


interface WanAndroidApi {

    companion object {

        private const val WAN_ANDROID_URL = "https://www.wanandroid.com/"
        private const val CACHE_NAME = "/cache/"

        fun get(): WanAndroidApi {
            val clientBuilder = OkHttpClient.Builder()
            clientBuilder.cookieJar(CookiesManager2())
            clientBuilder.connectTimeout(10, TimeUnit.SECONDS);
            clientBuilder.writeTimeout(10, TimeUnit.SECONDS);
            clientBuilder.readTimeout(10, TimeUnit.SECONDS);

            val cacheFile = File(AppContext.filesDir, CACHE_NAME)
            val cache = Cache(cacheFile, 1024 * 1024 * 50)
            clientBuilder.cache(cache)
            clientBuilder.addInterceptor(CacheInterceptor())

            if (BuildConfig.DEBUG) {
                //val loggingInterceptor = HttpLoggingInterceptor()
                //loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                //clientBuilder.addInterceptor(loggingInterceptor)
                clientBuilder.addInterceptor(TuziMockManager.getMockLogInterceptors())
            }

            return Retrofit.Builder()
                .baseUrl(WAN_ANDROID_URL)
                .client(clientBuilder.build())
                .addCallAdapterFactory(LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WanAndroidApi::class.java)
        }

    }

    /**
     * 首页banner
     */
    @GET("banner/json")
    fun bannerList(): LiveData<WanAndroidResponse<List<BannerVO>>>

    /**
     * 文章列表
     */
    @GET("article/list/{page}/json")
    fun articleList(
        @Path("page") page: Int
    ): LiveData<WanAndroidResponse<PageVO<ArticleVO>>>

    /**
     * 知识体系下文章
     */
    @GET("article/list/{page}/json")
    fun articleList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): LiveData<WanAndroidResponse<PageVO<ArticleVO>>>


    /**
     * 项目分类
     */
    @GET("project/tree/json")
    fun projectTree(): LiveData<WanAndroidResponse<List<ProjectCategoryVO>>>

    /**
     * 项目列表
     */
    @GET("project/list/{page}/json")
    fun projectList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): LiveData<WanAndroidResponse<PageVO<ArticleVO>>>

    /**
     * 公众号分类
     */
    @GET("wxarticle/chapters/json")
    fun wxChapters(): LiveData<WanAndroidResponse<List<WXChapterVO>>>

    /**
     * 公众号文章
     */
    @GET("wxarticle/list/{id}/{page}/json")
    fun wxArticlePage(
        @Path("id") id: Int,
        @Path("page") page: Int
    ): LiveData<WanAndroidResponse<PageVO<ArticleVO>>>

    /**
     * 登录
     */
    @POST("user/login")
    fun login(
        @Query("username") username: String?,
        @Query("password") password: String?
    ): LiveData<WanAndroidResponse<LoginUserVO>>

    /**
     * 退出  https://www.wanandroid.com/user/logout/json
     */
    @GET("user/logout/json")
    fun logout(): LiveData<WanAndroidResponse<Unit>>

    /**
     * 用户信息（积分/排名等）
     */
    @GET("lg/coin/userinfo/json")
    fun userInfo(): LiveData<WanAndroidResponse<UserInfoVO>>

    /**
     * 收藏
     */
    @POST("lg/collect/{id}/json")
    fun collect(@Path("id") articleId: Int): LiveData<WanAndroidResponse<String>>

    /**
     * 收藏
     */
    @POST("lg/collect/add/json")
    fun collect(
        @Query("title") title: String,
        @Query("author") author: String,
        @Query("link") link: String
    ): LiveData<WanAndroidResponse<String>>

    /**
     * 取消收藏
     */
    @POST("lg/uncollect_originId/{id}/json")
    fun uncollect(@Path("id") articleId: Int): LiveData<WanAndroidResponse<String>>

    /**
     * 收藏文章分页列表
     */
    @GET("lg/collect/list/{page}/json")
    fun collectPage(@Path("page") page: Int): LiveData<WanAndroidResponse<PageVO<ArticleVO>>>

    /**
     * 搜索
     */
    @POST("article/query/{page}/json")
    fun searchArticlePage(
        @Path("page") page: Int,
        @Query("k") keyword: String
    ): LiveData<WanAndroidResponse<PageVO<ArticleVO>>>
}