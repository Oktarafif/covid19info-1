package id.idn.fahru.covid19info.adapter

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import id.idn.fahru.covid19info.POJO.CountriesItem
import id.idn.fahru.covid19info.databinding.ListCountryBinding

class ListCountryVH(val binding : ListCountryBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: CountriesItem) {

        binding.run {
            txtCountryName.text = data.country
            txtTotalCase.text = data.totalConfirmed.toString()
            txtTotalRecovered.text = data.totalRecovered.toString()
            txtTotalDeaths.text = data.totalDeaths.toString()
        }

        // Glide menambahkan gambar online
        Glide.with(binding.root.context) // with diisi oleh context dari viewHolder
            .load("https://www.countryflags.io/${data.countryCode}/flat/16.png")
            .into(binding.imgFlagCountry)
    }
}

//    fun bind (data : CountriesItem) {
//        binding.txtCountryName.text = data.country
//        binding.txtTotalCase.text = data.totalConfirmed.toString()
//        binding.txtTotalRecovered.text = data.totalRecovered.toString()
//        binding.txtTotalDeaths.text = data.totalDeaths.toString()