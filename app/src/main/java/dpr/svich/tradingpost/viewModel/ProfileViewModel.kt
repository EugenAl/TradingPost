package dpr.svich.tradingpost.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dpr.svich.tradingpost.database.Repository
import dpr.svich.tradingpost.model.StockPortfolio
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository): ViewModel() {

    val stockPortfolioList: LiveData<List<StockPortfolio>> =
        repository.allStockPortfolio.asLiveData()

    fun addStockPortfolio(name: String, description: String): Job {
        return viewModelScope.launch {
            repository.insertStockPortfolio(
                StockPortfolio(0, name, description)
            )
        }
    }

    fun deleteStockPortfolio(sp: StockPortfolio): Job{
        return viewModelScope.launch {
            repository.deleteStockPortfolio(sp)
        }
    }
}