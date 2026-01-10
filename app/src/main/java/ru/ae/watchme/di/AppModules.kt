package ru.ae.watchme.di

import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    single {
        Retrofit.Builder()
            .baseUrl("TODO") // TODO: Вставить URL от API
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .build()
    }

    // TODO: Передать класс сервиса
    // single { get<Retrofit>().create(TODO()) }
}

val databaseModule = module {
    // TODO: Передать класс для базы данных
    // single { Room.databaseBuilder(get(), TODO(), "WatchMeLocal.db").build() }
    // single { get<TODO()>() }
}

val appModule = module {
    // TODO: Передать репозитории и view модели
}