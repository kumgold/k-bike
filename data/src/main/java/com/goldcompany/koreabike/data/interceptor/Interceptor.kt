package com.goldcompany.koreabike.data.interceptor

import com.goldcompany.koreabike.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class KakaoInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder().addHeader("Authorization", BuildConfig.KAKAO_API_KEY)

        return chain.proceed(request.build())
    }
}

class NaverInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("X-NCP-APIGW-API-KEY-ID", BuildConfig.NAVER_CLIENT_ID)
            .addHeader("X-NCP-APIGW-API-KEY", BuildConfig.NAVER_CLIENT_SECRET)

        return chain.proceed(request.build())
    }
}