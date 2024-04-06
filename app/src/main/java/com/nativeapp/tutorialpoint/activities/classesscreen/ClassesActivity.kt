package com.nativeapp.tutorialpoint.activities.classesscreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nativeapp.tutorialpoint.ClassEntity
import com.nativeapp.tutorialpoint.activities.playerscreen.PlayerActivity
import com.nativeapp.tutorialpoint.presentation.activities.ui.theme.TutorialPointTheme
import com.nativeapp.tutorialpoint.repositories.ClassModel
import com.nativeapp.tutorialpoint.viewmodels.ClassesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ClassesActivity : ComponentActivity() {

    private val classesVM: ClassesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TutorialPointTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val state = classesVM.uiState.collectAsState()
                    showUI(state.value, classesVM)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        classesVM.fetchClass()
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun showUI(state: ClassesUiState, viewModel: ClassesViewModel) {
        val scope = rememberCoroutineScope()
        val snackbarHostState = remember { SnackbarHostState() }
        Scaffold(
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            content = { contentPadding ->
                Column(
                    modifier = Modifier.padding(contentPadding)
                ){
                    when(state){
                        is FetchingClassesState -> Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            CircularProgressIndicator()
                        }
                        is ClassState -> showClassDetails(state.classData)
                        else -> {
                            LaunchedEffect("Key_Snackbar"){
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = (state as FetchFailed).errorMessage,
                                        actionLabel = "Retry",
                                        duration = SnackbarDuration.Indefinite
                                    )
                                    when(result){
                                        SnackbarResult.Dismissed -> {}
                                        SnackbarResult.ActionPerformed -> {
                                        viewModel.fetchClass()
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        )
    }

    @Composable
    fun showClassDetails(classDetails : ClassModel){
        Column (
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ){
            Text(
                text = classDetails.sectionTitle,
                color = Color.Black,
                textAlign = TextAlign.Center,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally)
            )
            classDetails.lecture.forEach {
                Card(
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White,
                    ),
                    border = BorderStroke(2.dp,MaterialTheme.colorScheme.surfaceVariant),
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .width(84.dp)
                ) {
                    Row {
                        Text(
                            text = it.title,
                            modifier = Modifier
                                .padding(16.dp),
                            textAlign = TextAlign.Center,
                        )
                        Spacer(
                            Modifier
                                .weight(1f)
                                .fillMaxHeight())
                        Icon(
                            imageVector = Icons.Default.PlayArrow,
                            contentDescription = "Play Icon",
                            tint = Color.Green,
                            modifier = Modifier
                                .clickable {
                                    val intent =
                                        Intent(this@ClassesActivity, PlayerActivity::class.java)
                                    intent.putExtra("url", it.lectureurl)
                                    startActivity(intent)
                                }
                                .align(Alignment.CenterVertically)
                                .padding(end = 12.dp)
                        )
                    }

                }
            }
        }

    }

}