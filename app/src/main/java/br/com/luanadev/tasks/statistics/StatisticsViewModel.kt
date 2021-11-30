package br.com.luanadev.tasks.statistics

import android.app.Application
import androidx.lifecycle.*
import br.com.luanadev.tasks.data.Result
import br.com.luanadev.tasks.data.Result.Error
import br.com.luanadev.tasks.data.Result.Success
import br.com.luanadev.tasks.data.Task
import br.com.luanadev.tasks.data.source.DefaultTasksRepository
import kotlinx.coroutines.launch

class StatisticsViewModel(application: Application) : AndroidViewModel(application) {

    private val tasksRepository = DefaultTasksRepository.getRepository(application)

    private val tasks: LiveData<Result<List<Task>>> = tasksRepository.observeTasks()
    private val _dataLoading = MutableLiveData(false)
    private val stats: LiveData<StatsResult?> = tasks.map {
        if (it is Success) {
            getActiveAndCompletedStats(it.data)
        } else {
            null
        }
    }

    val activeTasksPercent = stats.map {
        it?.activeTasksPercent ?: 0f
    }
    val completedTasksPercent: LiveData<Float> = stats.map { it?.completedTasksPercent ?: 0f }
    val dataLoading: LiveData<Boolean> = _dataLoading
    val error: LiveData<Boolean> = tasks.map { it is Error }
    val empty: LiveData<Boolean> = tasks.map { (it as? Success)?.data.isNullOrEmpty() }

    fun refresh() {
        _dataLoading.value = true
        viewModelScope.launch {
            tasksRepository.refreshTasks()
            _dataLoading.value = false
        }
    }
}