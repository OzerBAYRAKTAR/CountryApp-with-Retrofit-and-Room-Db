package com.example.kotlincountries.Views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlincountries.Adapter.CountryAdapter
import com.example.kotlincountries.R
import com.example.kotlincountries.ViewModel.FeedViewModel
import kotlinx.android.synthetic.main.fragment_feed.*


class FeedFragment : Fragment() {

    private lateinit var viewModel:FeedViewModel
    private val countryadapter=CountryAdapter(arrayListOf())



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_feed, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //hangi fragmentteyiz ve hangi viewmodelin sınıfıyla çalışmak istediğimizi yazabiliyoruz.(provider ile)
        viewModel=ViewModelProviders.of(this).get(FeedViewModel::class.java)
        viewModel.refreshData()

        recycler_feed.layoutManager=LinearLayoutManager(context)
        recycler_feed.adapter=countryadapter

        /*
        feed_button.setOnClickListener {
            val action=FeedFragmentDirections.actionFeedFragmentToCountryFragment()
            Navigation.findNavController(it).navigate(action)
        }

         */

        swipeRefreshLayout.setOnRefreshListener {
            recycler_feed.visibility=View.GONE
            countryError.visibility=View.GONE
            progressBarCountryLoading.visibility=View.VISIBLE
            viewModel.refreshFromApi() // veriler güncellenecek. api aracılığıyla
            swipeRefreshLayout.isRefreshing=false   //yukardan aşağı çekince dönen progres bar çıkmayacak.
        }
        observeLiveData()


    }
   private  fun observeLiveData(){
        //viewmodeldeki tanımladığımız mutable list içindeki country list geliyor.
        viewModel.countries.observe(viewLifecycleOwner, Observer {countries->


            countries?.let {
                recycler_feed.visibility=View.VISIBLE
                countryadapter.updateCountryList(countries)

            }


        })

        viewModel.countryError.observe(viewLifecycleOwner, Observer {error->
            error?.let {
                if (it){
                    countryError.visibility=View.VISIBLE
                }else{
                    countryError.visibility=View.GONE
                }
            }

        })
        viewModel.countryLoading.observe(viewLifecycleOwner, Observer {loading->
            loading?.let {
                if (it){
                    progressBarCountryLoading.visibility=View.VISIBLE
                    countryError.visibility=View.GONE
                    recycler_feed.visibility=View.GONE
                }else{
                    progressBarCountryLoading.visibility=View.GONE
                }
            }

        })
    }


    }
