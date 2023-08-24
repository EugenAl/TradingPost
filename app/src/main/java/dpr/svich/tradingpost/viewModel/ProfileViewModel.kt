package dpr.svich.tradingpost.viewModel

import android.text.Editable.Factory
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl
import dpr.svich.tradingpost.TradingApplication
import dpr.svich.tradingpost.database.Repository
import dpr.svich.tradingpost.model.Stock
import dpr.svich.tradingpost.model.StockPortfolio
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: Repository): ViewModel() {

    val stockPortfolioList: LiveData<List<StockPortfolio>> =
        repository.allStockPortfolio.asLiveData()

    fun getStockPortfolio(id: Int) = repository.getPortfolioById(id).asLiveData()

    fun addStockPortfolio(name: String, description: String): Job {
        return viewModelScope.launch {
            repository.insertStockPortfolio(
                StockPortfolio(0, name, description)
            )
        }
    }

    fun deleteStockPortfolio(sp: StockPortfolio): Job{
        return viewModelScope.launch {
            val stocks = loadStocks(sp.id)
            stocks.value?.let {
                repository.deleteStock(it)
            }
            repository.deleteStockPortfolio(sp)
        }
    }

    fun loadStocks(id: Int) = repository.getStocksFromPortfolio(id).asLiveData()

    fun addStock(index: String, count: Int, price: Double, portfolio: Int): Job{
        return viewModelScope.launch {
            repository.insertStock(Stock(0, index, count, price, portfolio))
        }
    }

    fun deleteStock(s: Stock): Job{
        return viewModelScope.launch {
            repository.deleteStock(s)
        }
    }

    companion object{
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return ProfileViewModel((application as TradingApplication).repository) as T
            }
        }
    }
}