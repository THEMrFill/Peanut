package io.peanutapp.newsfeed.di

import android.app.Application
import androidx.room.Room
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import io.peanutapp.newsfeed.BuildConfig
import io.peanutapp.newsfeed.api.ApiNetwork
import io.peanutapp.newsfeed.repo.NewsRepository
import io.peanutapp.newsfeed.repo.NewsRepositoryImpl
import io.peanutapp.newsfeed.storage.NewsDao
import io.peanutapp.newsfeed.storage.NewsDatabase
import io.peanutapp.newsfeed.ui.detail.DetailViewModel
import io.peanutapp.newsfeed.ui.main.MainViewModel
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModules = module {
  // The Retrofit service using our custom HTTP client instance as a singleton
  single {
    createWebService(
      okHttpClient = createHttpClient(),
      baseUrl = BuildConfig.API_URL,
    )
  }
  // factories for Koin
  single<NewsRepository> { NewsRepositoryImpl(get(), get(), get()) }
  // view models
  viewModel { MainViewModel(get()) }
  viewModel { DetailViewModel(get()) }
  // Room database
  fun provideDatabase(application: Application): NewsDatabase{
    return Room.databaseBuilder(application, NewsDatabase::class.java, "news")
      .fallbackToDestructiveMigration()
      .build()
  }
  fun provideNewsDao(database: NewsDatabase): NewsDao {
    return  database.newsDao()
  }
  single { provideDatabase(get()) }
  single { provideNewsDao(get()) }
}

/* Returns a custom OkHttpClient instance with interceptor. Used for building Retrofit service */
fun createHttpClient(): OkHttpClient {
  val client = OkHttpClient.Builder()
  client.readTimeout(30, TimeUnit.SECONDS)
  return client.addInterceptor {
    val original = it.request()
    val requestBuilder = original.newBuilder()
    requestBuilder.header("Content-Type", "application/json")
    val request = requestBuilder.method(original.method, original.body).build()
    return@addInterceptor it.proceed(request)
  }.build()
}

/* function to build our Retrofit service */
fun createWebService(
  okHttpClient: OkHttpClient,
  baseUrl: String
): ApiNetwork {
  val retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .client(okHttpClient)
    .build()
  return retrofit.create(ApiNetwork::class.java)
}
