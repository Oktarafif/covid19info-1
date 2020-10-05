package id.idn.fahru.covid19info

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import id.idn.fahru.covid19info.POJO.CountriesItem
import id.idn.fahru.covid19info.POJO.Global
import id.idn.fahru.covid19info.RetrofitCovid.RetrofitInterfaces
import id.idn.fahru.covid19info.RetrofitCovid.RetrofitService
import id.idn.fahru.covid19info.adapter.ListCountryAdapter
import id.idn.fahru.covid19info.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    // rvAdapter dibuat variabel Global
    private lateinit var rvAdapter : ListCountryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // inflater dan inflate binding mesti ada
        val inflater = layoutInflater
        binding = ActivityMainBinding.inflate(inflater)

        // ganti setContentView dengan binding.root
        setContentView(binding.root)

        // definisikan recyclerview adapter
        rvAdapter = ListCountryAdapter()

        // setting recyclerview
        binding.rvCountry.run {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@MainActivity)
            // gunakan recyclerview adapter yang telah didefinisikan sebelumnya
            adapter = rvAdapter
        }

        getCovidData(binding)
        binding.swipeRefresh.setOnRefreshListener {
            getCovidData(binding)

        }

        // Atur searchview
        binding.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    rvAdapter.filter.filter(query)
                    Log.e("TestSearchView", query.toString())
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            }
        )
    }

    // buat fungsi getCovidData
    private fun getCovidData(binding: ActivityMainBinding) {
        // buat lifecyclescope untuk mengakses retrofit
        lifecycleScope.launch {
            // definisikan retrofit service berdasarkan interface yang dituju
            val retrofit = RetrofitService.buildservice(RetrofitInterfaces::class.java)
            // definisikan variabel summary (sesuaikan aja namanya)
            val summary = retrofit.getSummary()
            if (summary.isSuccessful) { // jika berhasil
                // buat variabel dataCountry yang berisi list countries dari API
                val dataCountry = summary.body()?.countries as List<CountriesItem>

                // buat variabel yang memuat nilai global dari API
                val dataGlobal = summary.body()?.global as Global
                // Masukkan data ke dalam activity main
                binding.run {
                    txtConfirmedGlode.text = dataGlobal.totalConfirmed.toString()
                    txtRecoveredGlobe.text = dataGlobal.totalRecovered.toString()
                    txtDeathsGlode.text = dataGlobal.totalDeaths.toString()
                }

                // hilangkan progressbar
                binding.progressBar.visibility = View.GONE

                // hilangkan loading swiperrefresh
                binding.swipeRefresh.isRefreshing = false

                // tambahkan ke dalam rvAdapter
                rvAdapter.addData(dataCountry)
            } else {
                Log.e("RetrofitFailed", summary.errorBody().toString())
            }
        }
    }
}