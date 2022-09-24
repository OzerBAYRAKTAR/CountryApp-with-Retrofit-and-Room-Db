package com.example.kotlincountries.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.kotlincountries.R
import com.example.kotlincountries.Util.downloadFromUrl
import com.example.kotlincountries.Util.placeHolderProgressBar
import com.example.kotlincountries.ViewModel.CountryViewModel
import kotlinx.android.synthetic.main.fragment_country.*


class CountryFragment : Fragment() {

    private lateinit var countryViewModel:CountryViewModel
    private var countryUuid=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_country, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            countryUuid=CountryFragmentArgs.fromBundle(it).countryUuid

        }
        countryViewModel=ViewModelProviders.of(this).get(CountryViewModel::class.java)
        countryViewModel.getDataFromRoom(countryUuid)



        //feedFragmentten countryUuid yi yollamak isterssek bu yöntemle alabilirz.

        observeLiveData()
    }

    //livedatadan gelen veri eğer boş değilse; aşağıdaki verilere ata.
    private fun observeLiveData(){
        countryViewModel.countryLiveData.observe(viewLifecycleOwner, Observer {country->
            country?.let {
                countryName.text=country.countryName
                countryCapital.text=country.countryCapital
                countryRegion.text=country.countryRegion
                countryCurrency.text=country.countryCurrency
                countryLanguage.text=country.countryLanguage
                countryName.text=country.countryName
                context?.let {
                    countryImage.downloadFromUrl(country.imageUrl, placeHolderProgressBar(it))

                }

            }

        })

    }

}