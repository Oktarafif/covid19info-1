package id.idn.fahru.covid19info.RetrofitCovid

import id.idn.fahru.covid19info.POJO.ResponseSummary
import retrofit2.Response
import retrofit2.http.GET

interface RetrofitInterfaces {
    @GET("Summary")
    suspend fun getSummary() : Response<ResponseSummary>
}