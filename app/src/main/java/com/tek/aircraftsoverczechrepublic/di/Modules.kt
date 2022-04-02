package com.tek.aircraftsoverczechrepublic.di

import com.tek.aircraftsoverczechrepublic.BuildConfig
import com.tek.aircraftsoverczechrepublic.data.remote.OpenSkyApi
import com.tek.aircraftsoverczechrepublic.data.repository.AircraftRepositoryImpl
import com.tek.aircraftsoverczechrepublic.domain.repository.AircraftRepository
import com.tek.aircraftsoverczechrepublic.domain.use_case.get_aircrafts.GetAircraftUseCase
import com.tek.aircraftsoverczechrepublic.presentation.map.MapViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val viewModelModule = module {
    viewModel { MapViewModel(get()) }

    single<AircraftRepository> { AircraftRepositoryImpl(get()) }

    single { GetAircraftUseCase((get())) }
}

val networkModule = module {
    single {
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(
            OkHttpClient().newBuilder().addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }).build()
        )
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    single { get<Retrofit>().create(OpenSkyApi::class.java) }
}
