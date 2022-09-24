package com.example.kotlincountries.ViewModel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.kotlincountries.models.Country
import com.example.kotlincountries.Services.CountryApiService
import com.example.kotlincountries.Services.CountryDatabase
import com.example.kotlincountries.Util.CustomSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FeedViewModel(application: Application):BaseViewModel(application ){
    private val countryApiService=CountryApiService()
    private val disposable=CompositeDisposable()   //kullan at . verileri temizleyip performansı düzenler.
    private val customPreferences=CustomSharedPreferences(getApplication())
    private var refreshTime=10*60*1000*1000*1000L  //nanosaniye cinsinden 10dk

    val countries= MutableLiveData<List<Country>>()     //gelen listeyi gösterecek
    val countryError= MutableLiveData<Boolean>()   //hata var mı yok mu
    val countryLoading= MutableLiveData<Boolean>()      // yükleniyor mu yüklenmiyor mu


    fun refreshData(){
        val updateTime=customPreferences.getTime()
        if (updateTime!=null && updateTime!=0L &&System.nanoTime()-updateTime<refreshTime) {
            getDataFromSQLite()

        }else{
            getDataFromApi()
        }
    }

    fun refreshFromApi(){
        getDataFromApi()
    }

    private fun getDataFromSQLite(){
        countryLoading.value=true
        launch {
            val countries=CountryDatabase(getApplication()).countryDao().getAllCountries()
            showCountries(countries)
            Toast.makeText(getApplication(), "COUNTRIES FROM SQLITE", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getDataFromApi(){
        countryLoading.value=true

        disposable.add(
            countryApiService.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Country>>(){
                    override fun onSuccess(t: List<Country>) {
                        storeInSqlite(t)
                        Toast.makeText(getApplication(), "COUNTRIES FROM Api", Toast.LENGTH_SHORT).show()



                    }

                    override fun onError(e: Throwable) {
                        countryLoading.value=false
                        countryError.value=true
                        e.printStackTrace()

                    }

                })
        )


    }
    private fun showCountries(countryList: List<Country>){

        countries.value=countryList
        countryError.value=false
        countryLoading.value=false
    }
    private fun storeInSqlite(list: List<Country>){
        launch {
            val dao=CountryDatabase(getApplication()).countryDao()
            dao.deleteAllCountries()
            //bu yöntem kotline ait, listeyi tekil elemanla çevirir(individual)
            val listLong=dao.insertAll(*list.toTypedArray())
            var i=0

            while (i<list.size){
                //listlong içindeki verileri uuid olarak tanımla
                list[i].uuid=listLong[i].toInt()
                i=i+1
            }
            showCountries(list)

        }
        customPreferences.saveTime(System.nanoTime())

    }

    override fun onCleared() {
        super.onCleared()
        //fragmentleri silip hafızayı verimli hale getirir.
        disposable.clear()
    }

}