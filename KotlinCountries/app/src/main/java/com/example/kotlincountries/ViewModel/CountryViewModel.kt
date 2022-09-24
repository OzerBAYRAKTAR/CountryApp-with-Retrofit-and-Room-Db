package com.example.kotlincountries.ViewModel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.kotlincountries.models.Country
import com.example.kotlincountries.Services.CountryDatabase
import kotlinx.coroutines.launch

class CountryViewModel(application: Application):BaseViewModel(application) {

val countryLiveData=MutableLiveData<Country>()

    //roomdan gelecek verileri locale kaydedip textlerde göstericez
    fun getDataFromRoom(uuid:Int){
        launch {
            val dao=CountryDatabase(getApplication()).countryDao()
            val country=dao.getCountry(uuid)
            countryLiveData.value=country
        }




    }

}