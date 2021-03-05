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

import android.os.CountDownTimer
import android.text.format.DateUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val SIXTY = 60L
private const val ONE_THOUSAND = 1_000L

class TimerViewModel : ViewModel() {
    private var _countdownTime: Long = 0

    private val _timeRemaining = MutableLiveData(0L)
    val timeRemaining: LiveData<String> = Transformations.map(_timeRemaining) { time ->
        DateUtils.formatElapsedTime(time)
    }

    private var timer: CountDownTimer? = null

    private var _isInputMode = MutableLiveData(true)
    val isInputMode: LiveData<Boolean> = _isInputMode

    private var _isTimesUp = MutableLiveData(false)
    val isTimesUp: LiveData<Boolean> = _isTimesUp

    fun startTimer(hours: Long, minutes: Long, seconds: Long) {
        _countdownTime = (hours * SIXTY * SIXTY * ONE_THOUSAND) +
            (minutes * SIXTY * ONE_THOUSAND) + (seconds * ONE_THOUSAND)

        timer?.cancel()

        timer = object : CountDownTimer(_countdownTime + ONE_THOUSAND, ONE_THOUSAND) {
            override fun onTick(time: Long) {
                _timeRemaining.value = time / ONE_THOUSAND
            }

            override fun onFinish() {
                cancel()
                _isTimesUp.value = true
                viewModelScope.launch {
                    delay(3_200)
                    _isTimesUp.value = false
                    delay(1_600)
                    _isInputMode.value = true
                }
            }
        }
        timer?.start()
        _isInputMode.value = false
    }

    fun stopTimer() {
        timer?.cancel()
        _timeRemaining.value = 0
        _isInputMode.value = true
    }
}
