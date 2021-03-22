package com.example.foody.di

import com.example.foody.util.Constants.Companion.BASE_URL
import com.example.foody.data.network.FoodRecipeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 *  참고 : https://developer.android.com/training/dependency-injection/hilt-android#hilt-modules
 *
 * Retrofit과 같은 external library는 Hilt에서 종속성을 제공하기위한 인스턴스를 만들 수 없다.
 * 보통 생성자를 통해 종속성을 삽입하는데 이게 불가능하다.
 * 이때는 Hilt에 결합정보를 알려주기 위하여 @Module과 @InstallIn annotation을 사용한다.
 * 이렇게 하면 외부라이브러리를 Hilt에 알리게 된다.
 * */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton // Singleton을 사용하면 동일한 인스턴스를 반환하겠다는 뜻이다.
    @Provides // OkHttpClient은 내가 직접 인스턴스를 만들어주는게 아닌 OkHttpClient library에서 제공되므로 @Provides 주석을 통해 인스턴스를 제공해준다.
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides // Retrofit은 내가 직접 인스턴스를 만들어주는게 아닌 Retorift library에서 제공되므로 @Provides 주석을 통해 인스턴스를 제공해준다.
    fun provideApiService(retrofit: Retrofit): FoodRecipeApi {
        return retrofit.create(FoodRecipeApi::class.java)
    }

}
