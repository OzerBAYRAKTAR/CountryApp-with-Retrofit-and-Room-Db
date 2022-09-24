package com.example.kotlincountries.Services

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.kotlincountries.models.Country



@Dao
interface CountryDao {

    //Data Access Object


    //sqlite da = INSERT INTO VALUES name:String, vs vs yazardık.
    //Insert -> Insert Into
    //suspend-> coroutine içinde çağrılır. durdurulup devam ettirilmesine olanak sağlayan fonksiyonlardır.
    //vararg->Normalde sqlite'da şu objeyi şuraya koy diye tek tek tanımlardık.Bunu yazınca farklı sayıdaki objeleri
    //tek bir metodla çekebilriz.(Multiple country objects)
    //List<Long> -> Primary Keyi döndürür.



    @Insert
    suspend fun insertAll(vararg countries:Country): List<Long>


    @Query("SELECT * FROM country")
    suspend fun getAllCountries(): List<Country>


    //country Fragmentte 1 tane gösterilecek olan country için.
    //uuid=modelde tanımlanan primary key
    @Query("SELECT * FROM country WHERE uuid= :countryId")
    suspend fun getCountry(countryId:Int):Country

    @Query("DELETE  FROM country")
    suspend fun deleteAllCountries()




}