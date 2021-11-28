package com.androidcafe.malaysiansydneyfood.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.androidcafe.malaysiansydneyfood.BuildConfig
import com.androidcafe.malaysiansydneyfood.R
import com.androidcafe.malaysiansydneyfood.local.asCardDataList
import com.androidcafe.malaysiansydneyfood.repository.FoodRepository
import kotlinx.coroutines.launch

class SharedFragmentViewModel(
    private val app: Application,
    private val repository: FoodRepository) : AndroidViewModel(app) {

    val allCardDataList: LiveData<List<CardData>> = Transformations.map(repository.foodEntityList) {
        it.asCardDataList()
    }

    private var _searchTitle: String? = null
    val searchTitle: String? get() = _searchTitle

    private val _searchResultCardDataList = MutableLiveData<List<CardData>>()
    val searchResultCardDataList: LiveData<List<CardData>>
        get() = _searchResultCardDataList

    private var _favoriteCardDataList: LiveData<List<CardData>>? = null
    val favoriteCardDataList: LiveData<List<CardData>>?
        get() = _favoriteCardDataList

    val aboutText: String by lazy {
        app.resources.getString(R.string.about_text, BuildConfig.VERSION_NAME)
    }

    private val debug = false
    private var _mockAllCardDataList: LiveData<List<CardData>>? = null
    val mockAllCardDataList: LiveData<List<CardData>>? get() = _mockAllCardDataList

    init {
        if (debug) setupMockUpData(false)
    }

    private fun setupMockUpData(searchResult: Boolean) {

        val _cardDataListLiveData = MutableLiveData<List<CardData>>()

        _cardDataListLiveData.value = mutableListOf(
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
            _searchResultCardDataList.value = _cardDataListLiveData.value
        } else {
            _mockAllCardDataList = _cardDataListLiveData
        }
    }

    fun setSearchTitle(title: String?) {
        _searchTitle = title
    }

    fun refreshSearchResultData() {
        viewModelScope.launch {
            val foodEntityList = repository.getByTitle(searchTitle!!)
            val cardDataList = foodEntityList.asCardDataList()
            _searchResultCardDataList.postValue(cardDataList)
        }
    }

    suspend fun refreshFavoriteData() {
        val foodEntityListLiveData = repository.getByFavorite().asLiveData()
        _favoriteCardDataList = Transformations.map(foodEntityListLiveData) {
            it.asCardDataList()
        }
    }

    suspend fun update(cardData: CardData)  {
        val foodEntity = cardData.asFoodEntity(toggleFavorite = true)
        repository.update(foodEntity)

        for(data in allCardDataList.value!!) {

            if(data.id == cardData.id) {
                data.favorite = foodEntity.favorite
                //break
            }
        }

        cardData.favorite = foodEntity.favorite
    }
}