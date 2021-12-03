package com.androidcafe.malaysiansydneyfood.viewmodel

import android.app.Application
import android.content.Context
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

    companion object {
        private const val SHARED_PREF_SAVED_FAV_FOOD_IDS_KEY = "savedFavFoodIds"
    }

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

    private val sharedPrefs by lazy {
        app.getSharedPreferences("SharedFragmentViewModel", Context.MODE_PRIVATE)
    }
    private val savedFavoriteFoodIds by lazy {
        sharedPrefs.getStringSet(SHARED_PREF_SAVED_FAV_FOOD_IDS_KEY, mutableSetOf<String>())
    }

    private val debug = false
    private var _mockAllCardDataList: LiveData<List<CardData>>? = null
    val mockAllCardDataList: LiveData<List<CardData>>? get() = _mockAllCardDataList

    init {
        if (debug) setupMockUpData(false)
        refreshFavoriteData()
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

    private fun refreshFavoriteData() {
        viewModelScope.launch {
            for(id in savedFavoriteFoodIds!!) {
                val foodEntity = repository.getById(id.toInt())
                foodEntity?.run {
                    favorite = true
                    repository.update(this)
                }
            }
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
        if(cardData.favorite == true) {
            savedFavoriteFoodIds!!.add(cardData.id.toString())
        } else {
            savedFavoriteFoodIds!!.remove(cardData.id.toString())
        }

        saveFavoriteFoodSharedPrefs()

        viewModelScope.launch {
            val foodEntity = cardData.asFoodEntity()
            repository.update(foodEntity)
        }
    }

    private fun saveFavoriteFoodSharedPrefs() {
        val editor = sharedPrefs.edit()
        //clear() is required else the second saved string won't work
        editor.clear()
        editor.putStringSet(SHARED_PREF_SAVED_FAV_FOOD_IDS_KEY, savedFavoriteFoodIds)
        editor.apply()
    }
}