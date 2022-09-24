package com.example.kotlincountries.Services

//bu veri tabanında sadece 1 obje oluşturmak istiyoruz.
//burada oluşturulacak database singleton mantığıyla oluşturulacak.
//daha önce oluşturulmuş obje yoksa oluştur,varsa eğer onu kullan, ve app'in her yerinden ulaşılabilir.
//ve heryerden ulaşabilmek için companion object oluşturulur.static bir şekilde bu sınıfın scope'u dışından ulaşılabilir.

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlincountries.models.Country

@Database(entities = arrayOf(Country::class), version = 1)
abstract class CountryDatabase :RoomDatabase(){



    abstract fun countryDao():CountryDao

    //singleton mantığıyla oluşturuldu.
    //volatile=tanımladığı değişkeni, diğer threadlere de görünür hale getirir.Static yapıya benzetilebilir.
    //tek oluşturulcak obje = instance. bunu önce kontrol etcez sonra işlem yapıcaz.
    //invoke= genelde instance'ı kontrol etmek için oluşturulan obje(genel kullanım)
    //?: == işlemi instance var mı bak. varsa direkt instacne i döndür.yoksa syncranized çalıştır.
    //syncranized = aynı anda 2 thread bu instance objeye ulaşmaya çalışırsa buna engel oluyor.aynı anda 1 threadde işlem yapılır.

    companion object {

        @Volatile private var instance : CountryDatabase?=null
        private val lock= Any()

        operator fun invoke(context:Context) = instance ?: synchronized(lock){
            instance?: makeDatabase(context).also {
                instance=it

            }

        }

        private fun makeDatabase(context: Context)=Room.databaseBuilder(
            context.applicationContext,CountryDatabase::class.java,"countrydatabase"
        ).build()

    }





}