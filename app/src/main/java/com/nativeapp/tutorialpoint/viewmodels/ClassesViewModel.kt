package com.nativeapp.tutorialpoint.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nativeapp.tutorialpoint.ClassEntity
import com.nativeapp.tutorialpoint.activities.classesscreen.ClassState
import com.nativeapp.tutorialpoint.activities.classesscreen.ClassesUiState
import com.nativeapp.tutorialpoint.activities.classesscreen.FetchFailed
import com.nativeapp.tutorialpoint.activities.classesscreen.FetchingClassesState
import com.nativeapp.tutorialpoint.repositories.ApiResponse
import com.nativeapp.tutorialpoint.repositories.ClassModel
import com.nativeapp.tutorialpoint.repositories.ClassesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassesViewModel @Inject constructor(val repository: ClassesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow<ClassesUiState>(FetchingClassesState)
    val uiState : StateFlow<ClassesUiState> = _uiState.asStateFlow()

    fun fetchClass(){
        viewModelScope.launch(Dispatchers.IO) {
            val apiResponse: ApiResponse<Any> = repository.getClasses()
            Log.d("Response-response", (apiResponse as ApiResponse.Success<ClassModel>).data.sectionTitle)
            _uiState.value = when(apiResponse){
                is ApiResponse.Success<*> -> ClassState((apiResponse as ApiResponse.Success<ClassModel>).data)
                is ApiResponse.Failure -> FetchFailed((apiResponse as ApiResponse.Failure).exception.localizedMessage)
                is ApiResponse.ApiError -> FetchFailed((apiResponse as ApiResponse.ApiError).message)
                else -> FetchFailed("Something went wrong!")
            }
        }
    }

}