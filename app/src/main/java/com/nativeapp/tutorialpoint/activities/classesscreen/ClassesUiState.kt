package com.nativeapp.tutorialpoint.activities.classesscreen

import com.nativeapp.tutorialpoint.ClassEntity
import com.nativeapp.tutorialpoint.repositories.ClassModel

sealed class ClassesUiState

object FetchingClassesState: ClassesUiState()

class ClassState(
    val classData: ClassModel
): ClassesUiState()

class FetchFailed(
    val errorMessage: String
): ClassesUiState()