/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.androiddevchallenge.ui.theme.MyTheme
import com.example.androiddevchallenge.ui.theme.typography

@Composable
fun TigcalCountDownTimer(viewModel: TimerViewModel = viewModel()) {
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        TimerInput(viewModel)
        Spacer(modifier = Modifier.padding(top = 16.dp))
        TimerOutput(viewModel)
    }
}

@Composable
fun TimerInput(viewModel: TimerViewModel) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
    ) {
        Text(
            text = stringResource(id = R.string.timer_hour),
            style = typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        TimerSlider(viewModel) { value -> viewModel.setCountdownHours(value) }
        Text(
            text = stringResource(id = R.string.timer_minute),
            style = typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        TimerSlider(viewModel) { value -> viewModel.setCountdownMinutes(value) }
        Text(
            text = stringResource(id = R.string.timer_second),
            style = typography.h6,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )
        TimerSlider(viewModel) { value -> viewModel.setCountdownSeconds(value) }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { startTimer(viewModel) },
            ) {
                Text(stringResource(id = R.string.action_start))
            }
        }
    }
}

@Composable
fun TimerSlider(viewModel: TimerViewModel, onValueChangedFinish: (Long) -> Unit) {
    var value by remember { mutableStateOf(0f) }
    Text(text = value.toLong().toString())
    Slider(
        value = value, valueRange = 0f..59f, steps = 60,
        onValueChange = { value = it },
        onValueChangeFinished = { onValueChangedFinish.invoke(value.toLong()) }
    )
}

fun startTimer(viewModel: TimerViewModel) {
    viewModel.startTimer()
}

@Composable
fun TimerOutput(viewModel: TimerViewModel) {
    val timeRemaining: String by viewModel.timeRemaining.observeAsState("")

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(timeRemaining)
    }
}

@Preview("Light Timer", widthDp = 360, heightDp = 640)
@Composable
fun LightTimer() {
    MyTheme {
        TigcalCountDownTimer(viewModel())
    }
}

@Preview("Dark Timer", widthDp = 360, heightDp = 640)
@Composable
fun DarkTimer() {
    MyTheme(darkTheme = true) {
        TigcalCountDownTimer(viewModel())
    }
}
