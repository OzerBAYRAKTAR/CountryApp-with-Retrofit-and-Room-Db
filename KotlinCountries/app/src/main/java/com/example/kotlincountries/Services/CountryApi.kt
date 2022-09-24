package com.example.kotlincountries.Services

import com.example.kotlincountries.models.Country
import io.reactivex.Single
import retrofit2.http.GET

interface CountryApi {

    //apilerden gelen post get işlemleri yapılır. get=almak, post=değiştirme işlemleri

    //  https://raw.githubusercontent.com/atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json
    //Base url=https://raw.githubusercontent.com/
    //extension= atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json

    // : dan sonra call=retrofit, observable=rxjava yöntemi endlessly.(veriyi sürekli alıp güncellemek)
    // single=yine bir observable, veriyi 1 kere alır 1 kere kullanır.

    @GET("atilsamancioglu/IA19-DataSetCountries/master/countrydataset.json")
    fun getCountries(): Single<List<Country>>


}