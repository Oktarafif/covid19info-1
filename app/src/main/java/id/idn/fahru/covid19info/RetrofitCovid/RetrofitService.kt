package id.idn.fahru.covid19info.RetrofitCovid

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitService {

    //  buat client dari OkHttpClient
    private val client = OkHttpClient.Builder().build()

    // Buat RetrofitClient
    private val retrofitClient = Retrofit.Builder()
        .client(client).baseUrl("https://api.covid19api.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Buat Fungsi yang bisa diakses oleh kelas lain untuk memanggil retrofitClient
    fun <T> buildservice(service: Class<T>): T = retrofitClient.create(service)

}