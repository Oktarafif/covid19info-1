package id.idn.fahru.covid19info.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import id.idn.fahru.covid19info.POJO.CountriesItem
import id.idn.fahru.covid19info.databinding.ListCountryBinding
import java.util.*

class ListCountryAdapter : RecyclerView.Adapter<ListCountryVH>(), Filterable {
    // Buat vaiabel list/arraylist untuk menyimpan data di dalam adapter
    private val dataCountry = mutableListOf<CountriesItem>()
    private var dataFiltered = mutableListOf<CountriesItem>()

    // Buat fungsi addData agar kelas lain bisa mengisi data kedalam recyclerview adpater
    fun addData(listCountry: List<CountriesItem>) {
        // bersihkan data lama jika ada menggunakan clear()
        dataCountry.clear()
        // tambahkan data set baru menggukan addAll
        dataCountry.addAll(listCountry)
        // Beritahu recylerView Adapter karena ada perubahan data set
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListCountryVH {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListCountryBinding.inflate(inflater, parent, false)
        return ListCountryVH(binding)
    }

    override fun getItemCount(): Int {
        // Ukuran dari data country size
        return dataFiltered.size
    }

    override fun onBindViewHolder(holder: ListCountryVH, position: Int) {
        // memilih data sesuai posisi item recyclerView
        val data = dataFiltered[position]
        // data tersebut ditempelkan ke dalam view menggunakan holder / viewHolder
        holder.bind(data)
    }

    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults? {
            val filterResult = FilterResults()
            //Mengisi Data Filtered dengan semua data yg ada di data countries jika tidak ada keyword
            if (constraint.isNullOrEmpty()) {
                filterResult.values = dataCountry
            } else {
                val resultFilter = mutableListOf<CountriesItem>()
                // Lakukan pengulangan data pada dataCountry untuk menyaring data berdasarkan keyword
                dataCountry.forEach { data ->
                    val countryName = data.country
                    countryName?.let { country ->
                        if (country.contains(constraint, true)) {
                            resultFilter.add(data)
                        }
                    }
                }
                filterResult.values = resultFilter
            }
            return filterResult
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            results?.let { data -> // Jika variabel tersebut tidak null, maka buat variabel baru "data" berisi results
                if (data is List<*>) { //Jika data tersebut berbentuk list
                    dataFiltered.clear() // Hapus data filtered yang ada saat ini
                    data.forEach { country -> // Lakukan per ulangan, tiap item list data jadi variabel country
                        country?.let {// Jika country tidak null
                            //maka tambahan data tersebut ke dalam data Filtered
                            if (country is CountriesItem) dataFiltered.add(it as CountriesItem)
                        }
                    }
                    // beritahu adapter recyclerview jika dataset telah berubah
                    notifyDataSetChanged()
                }
            }
        }
    }
}

