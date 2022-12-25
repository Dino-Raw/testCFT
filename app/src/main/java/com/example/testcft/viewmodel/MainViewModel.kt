package com.example.testcft.viewmodel

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testcft.model.Card
import com.example.testcft.model.History
import com.example.testcft.objects.Client
import com.example.testcft.objects.Const.KEY_HISTORY_LIST
import com.example.testcft.objects.SharedPreferencesExtended.get
import com.example.testcft.objects.SharedPreferencesExtended.isSharedPreferencesInitialized
import com.example.testcft.objects.SharedPreferencesExtended.put
import com.example.testcft.objects.SharedPreferencesExtended.sharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainViewModel: ViewModel() {
    val bin: MutableLiveData<String> = MutableLiveData(null)

    private val _historyList: MutableLiveData<MutableList<History>> = MutableLiveData(null)
    val historyList: LiveData<MutableList<History>> = _historyList

    private val _card: MutableLiveData<Card?> = MutableLiveData(null)
    val card: LiveData<Card?> = _card

    private val _coordinates: MutableLiveData<String> = MutableLiveData()
    val coordinates: LiveData<String> = _coordinates

    private val _message: MutableLiveData<String> = MutableLiveData("")
    val message: LiveData<String> = _message

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCardData() {
        if (bin.value?.let { it.length > 3 } == true) {
            viewModelScope.launch(Dispatchers.IO) {
                _isLoading.postValue(true)

                bin.value?.let { bin ->
                    _card.postValue(
                        try {
                            Client.retrofit.getCardData(bin).also { card ->
                                if (card != null) {
                                    addLookupToHistory()
                                    saveHistory()
                                }
                            }
                        } catch (e: Exception) {
                            _message.postValue("Failed to get data")
                            null
                        }
                    )
                }
                _isLoading.postValue(false)
            }
        } else {
            _message.value = "Enter BIN"
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun addLookupToHistory() {
        if (historyList.value?.isEmpty() == true)
            bin.value?.let { _historyList.value?.add(History(it, getCurrentTimeData())) }
        else
            bin.value?.let { _historyList.value?.add(0, History(it, getCurrentTimeData())) }
    }

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentTimeData(): String {
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yy, HH:mm")
        return LocalDateTime.now().format(formatter)
    }


    fun openMap() {
        _coordinates.value =
            "geo:${card.value?.country?.latitude}.0000,${card.value?.country?.longitude}.0000"
    }

    fun loadHistory() {
        if (isSharedPreferencesInitialized)
            _historyList.value = sharedPreferences.get(KEY_HISTORY_LIST)
    }

    @SuppressLint("CommitPrefEdits")
    fun saveHistory() {
        if (!_historyList.value.isNullOrEmpty())
            sharedPreferences.edit().put(_historyList.value!!, KEY_HISTORY_LIST)
    }
}