package com.example.retrofitforecaster

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var weatherAdapter: WeatherAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherAdapter = WeatherAdapter()
        val recyclerView: RecyclerView = findViewById(R.id.r_view)
        recyclerView.adapter = weatherAdapter

        fetchWeatherData("Shklov")
    }

    private fun fetchWeatherData(city: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val weatherApi = retrofit.create(WeatherApi::class.java)
        val call = weatherApi.getWeatherForecast(city, Constants.API_KEY)

        call.enqueue(object : Callback<ForecastResponse> {
            override fun onResponse(
                call: Call<ForecastResponse>,
                response: Response<ForecastResponse>
            ) {
                if (response.isSuccessful) {
                    val forecastResponse = response.body()
                    if (forecastResponse != null) {
                        val weatherItems = forecastResponse.list.map { forecast ->
                            WeatherItem(
                                date = forecast.dt_txt,
                                description = forecast.weather.firstOrNull()?.description.orEmpty(),
                                temperature = "${forecast.main.temp}°C",
                                iconRes = getWeatherIcon(forecast.weather.firstOrNull()?.icon.orEmpty())
                            )
                        }
                        weatherAdapter.submitList(weatherItems)
                    }
                }
            }

            override fun onFailure(call: Call<ForecastResponse>, t: Throwable) {
                // Handle error (e.g., show a Toast message)
            }
        })
    }

    private fun getWeatherIcon(iconCode: String): Int {
        return when (iconCode) {
            "01d" -> R.drawable.ic_sunny
            "01n" -> R.drawable.ic_clear_night
            "02d", "02n" -> R.drawable.ic_partly_cloudy
            "03d", "03n", "04d", "04n" -> R.drawable.ic_cloudy
            "09d", "09n" -> R.drawable.ic_rain
            "10d", "10n" -> R.drawable.ic_heavy_rain
            "11d", "11n" -> R.drawable.ic_thunderstorm
            "13d", "13n" -> R.drawable.ic_snow
            "50d", "50n" -> R.drawable.ic_mist
            else -> R.drawable.ic_unknown
        }
    }
}