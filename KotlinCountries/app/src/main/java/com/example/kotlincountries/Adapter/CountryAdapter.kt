package com.example.kotlincountries.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation

import androidx.recyclerview.widget.RecyclerView
import com.example.kotlincountries.models.Country
import com.example.kotlincountries.R
import com.example.kotlincountries.Util.downloadFromUrl
import com.example.kotlincountries.Util.placeHolderProgressBar
import com.example.kotlincountries.Views.FeedFragmentDirections

import kotlinx.android.synthetic.main.recycler_row.view.*

class CountryAdapter(val countryList:ArrayList<Country>): RecyclerView.Adapter<CountryAdapter.CountryHolder>() {


    class CountryHolder(var view:View):RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryHolder {
      val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_row,parent,false)
        return CountryHolder(view)



    }

    override fun onBindViewHolder(holder: CountryHolder, position: Int) {

        holder.view.name.text=countryList[position].countryName
        holder.view.region.text=countryList[position].countryRegion

        holder.view.setOnClickListener {

            val action=FeedFragmentDirections.actionFeedFragmentToCountryFragment(countryList[position].uuid)
            //countryList[position].uuid position'a göre uuidyi al ve country fragmentine gönder
            Navigation.findNavController(it).navigate(action)
        }

        holder.view.imageView.downloadFromUrl(countryList[position].imageUrl, placeHolderProgressBar(holder.view.context))

    }




    override fun getItemCount(): Int {
        return countryList.size
    }


        //swipe layout un fonksiyon
    fun updateCountryList(newCountryList:List<Country>){
        countryList.clear()  // sayfa yenilenince eski listeyi silecek
            countryList.addAll(newCountryList)  // sonra yeni yüklenene countrylisti ekleyecek
            notifyDataSetChanged()   //adaptorü yenilemek için

    }


}