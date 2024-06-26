package com.karna.weatherappcompose.ui.weather

import android.util.Log
import com.karna.weatherappcompose.data.ApiState
import com.karna.weatherappcompose.data.WeatherApiService
import com.karna.weatherappcompose.data.currentTemperatureModels.WeatherData
import com.karna.weatherappcompose.data.forecastTemperatureModels.WeatherForecastResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class WeatherInfoRepoImpl(private val weatherApiService: WeatherApiService) : WeatherInfoRepo {

    override suspend fun getCurrentWeather(): ApiState =
        suspendCoroutine { continuation ->
            weatherApiService.getCurrentWeather().enqueue(object : Callback<WeatherData> {
                override fun onResponse(call: Call<WeatherData>, response: Response<WeatherData>) {
                    if (response.isSuccessful) {
                        Log.d("Repo", "Checking Current day --------- Success")
                        response.body()?.let {
                            continuation.resume(ApiState.Success(it))
                        }
                            ?: continuation.resume(ApiState.Failure(Throwable(message = "Failed to fetch current weather")))
                    } else {
                        Log.d("Repo", "Checking Current day --------- Fail")
                        continuation.resume(ApiState.Failure(Throwable(message = "Failed to fetch current weather")))
                    }
                }

                override fun onFailure(call: Call<WeatherData>, t: Throwable) {
                    Log.d("Repo", "Checking Current day --------- Failure -- ${t.localizedMessage}")
                    continuation.resume(ApiState.Failure(t))
                }

            })
        }

    override suspend fun getForeCastWeather(): ApiState =
        suspendCoroutine { continuation ->
            weatherApiService.getForeCastWeather().enqueue(object : Callback<WeatherForecastResponse> {
                override fun onResponse(call: Call<WeatherForecastResponse>, response: Response<WeatherForecastResponse>) {
                    if (response.isSuccessful) {
                        Log.d("Repo", "Checking ForeCast --------- Success")
                        response.body()?.let {
                            continuation.resume(ApiState.Success(it))
                        }
                            ?: continuation.resume(ApiState.Failure(Throwable(message = "Failed to fetch current weather")))
                    } else {
                        Log.d("Repo", "Checking ForeCast --------- Fail")
                        continuation.resume(ApiState.Failure(Throwable(message = "Failed to fetch current weather")))
                    }
                }

                override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
                    Log.d("Repo", "Checking ForeCast --------- Failure -- ${t.localizedMessage}")
                    continuation.resume(ApiState.Failure(t))
                }

            })
        }


}