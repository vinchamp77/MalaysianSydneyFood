package com.androidcafe.malaysiansydneyfood.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.androidcafe.malaysiansydneyfood.BuildConfig
import com.androidcafe.malaysiansydneyfood.R
import com.androidcafe.malaysiansydneyfood.local.FoodEntity
import com.androidcafe.malaysiansydneyfood.local.asCardDataList
import com.androidcafe.malaysiansydneyfood.repository.FoodRepository
import kotlinx.coroutines.launch

class SharedFragmentViewModel(
    private val app: Application,
    private val repository: FoodRepository) : AndroidViewModel(app) {

    val allCardDataList: LiveData<List<CardData>> =
        Transformations.map(repository.allFoodEntityList) {
        it.asCardDataList()
    }

    val favCardDataList: LiveData<List<CardData>> =
        Transformations.map(repository.favFoodEntityList) {
        it.asCardDataList()
    }

    private var _searchTitle: String? = null
    private var _searchFavorite = false

    private val _searchResultCardDataList = MutableLiveData<List<CardData>>()
    val searchResultCardDataList: LiveData<List<CardData>>
        get() = _searchResultCardDataList

    val versionText: String by lazy {
        app.resources.getString(R.string.version_text, BuildConfig.VERSION_NAME)
    }

    private val debug = false
    private var _mockAllCardDataList: LiveData<List<CardData>>? = null
    val mockAllCardDataList: LiveData<List<CardData>>? get() = _mockAllCardDataList

    init {
        if (debug) setupMockUpData(false)
    }

    private fun setupMockUpData(searchResult: Boolean) {

        val cardDataListLiveData = MutableLiveData<List<CardData>>()

        cardDataListLiveData.value = mutableListOf(
            CardData(
                0,
                "Mamak",
                4f,
                "Buzzing Malaysian restaurant serving satay, noodles and desserts like sweet roti at shared tables.",
                "https://mamak.com.au/images/content/_max/haymarket-shop.jpg",
                "https://goo.gl/maps/RJDrEzw4agb9bGkE7",
                false,
            ),
        )
        if(searchResult) {
            _searchResultCardDataList.value = cardDataListLiveData.value
        } else {
            _mockAllCardDataList = cardDataListLiveData
        }
    }

    fun setSearchInfo(title: String?, favorite:Boolean) {
        _searchTitle = title
        _searchFavorite = favorite
    }

    fun refreshSearchResultData() {
        viewModelScope.launch {
            lateinit var foodEntityList : List<FoodEntity>

            if (_searchFavorite) {
                foodEntityList = repository.getByTitleFavoriteFood(_searchTitle!!)
            } else {
                foodEntityList = repository.getByTitle(_searchTitle!!)
            }
            val cardDataList = foodEntityList.asCardDataList()
            _searchResultCardDataList.postValue(cardDataList)
        }
    }

    fun update(cardData: CardData) {
        viewModelScope.launch {
            val foodEntity = cardData.asFoodEntity()
            repository.update(foodEntity)
        }
    }
}