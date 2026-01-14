package ru.ae.watchme.di

import androidx.room.Room
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.ae.watchme.BuildConfig
import ru.ae.watchme.data.local.database.MovieDatabase
import ru.ae.watchme.data.remote.interceptor.ApiKeyInterceptor
import ru.ae.watchme.data.remote.service.MovieService
import ru.ae.watchme.data.repository.MovieRepositoryImpl
import ru.ae.watchme.domain.repository.MovieRepository
import ru.ae.watchme.ui.viewmodels.MovieDetailsViewModel
import ru.ae.watchme.ui.viewmodels.MovieListViewModel
import ru.ae.watchme.ui.viewmodels.RandomMovieViewModel

val networkModule = module {
    single { BuildConfig.API_KEY }

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(ApiKeyInterceptor(get()))
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://api.poiskkino.dev/v1.4/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(MovieService::class.java) }
}

val repositoryModule = module {
    single<MovieRepository> {
        MovieRepositoryImpl(
            get(),
            get()
        )
    }
}

val viewModelModule = module {
    viewModel { MovieListViewModel(get()) }
    viewModel { RandomMovieViewModel(get()) }
    viewModel { (id: Int) ->
        MovieDetailsViewModel(id, get())
    } // Синтаксис котла поражает воображение
}

val databaseModule = module {
    single {
        Room.databaseBuilder(
            get(),
            MovieDatabase::class.java,
            "watchme.db"
        ).build()
    }

    single {
        get<MovieDatabase>().movieDao()
    }
}

val appModule = module {
    //TODO() Передать остальные модули, если чето еще есть
    includes(
        networkModule,
        repositoryModule,
        databaseModule,
        viewModelModule
    )
}