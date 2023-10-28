package com.petsup.ui.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.petsup.api.Rest
import com.petsup.models.petshop.Petshop
import com.petsup.services.PetshopService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val sharedPref by lazy {
        application.getSharedPreferences("SharedPreference", Context.MODE_PRIVATE)
    }

    private val _petshopList = MutableLiveData<List<Petshop>>()
    val petshopList: LiveData<List<Petshop>> = _petshopList

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun getPetshops() = viewModelScope.launch(Dispatchers.IO) {
        val token: String? = sharedPref.getString("token", "")

        Rest.getInstance()
            .create(PetshopService::class.java)
            .listPetshops("Bearer $token")
            .enqueue(object : Callback<List<Petshop>> {
                override fun onResponse(
                    call: Call<List<Petshop>>,
                    response: Response<List<Petshop>>
                ) {
                    if (response.isSuccessful) {
                        _petshopList.postValue(response.body() ?: emptyList())
                    } else {
                        _error.postValue((response.message()))
                    }
                }

                override fun onFailure(call: Call<List<Petshop>>, t: Throwable) {
                    _error.postValue(t.message)
                }
            })
    }
}