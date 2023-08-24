package dpr.svich.tradingpost.viewModel

import android.text.Editable.Factory
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import dpr.svich.tradingpost.TradingApplication
import dpr.svich.tradingpost.database.Repository
import dpr.svich.tradingpost.model.Stock
import dpr.svich.tradingpost.model.StockPortfolio
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainViewViewModel(private val repository: Repository): ViewModel() {

    val stockPortfolioList: LiveData<List<StockPortfolio>> =
        repository.allStockPortfolio.asLiveData()

    fun loadStocks(id: Int) = repository.getStocksFromPortfolio(id).asLiveData()

    companion object{
        val Factory: ViewModelProvider.Factory = object: ViewModelProvider.Factory{
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val application = checkNotNull(extras[APPLICATION_KEY])
                return MainViewViewModel((application as TradingApplication).repository) as T
            }
        }
    }
}