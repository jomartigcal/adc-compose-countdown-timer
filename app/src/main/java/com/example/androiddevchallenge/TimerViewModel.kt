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

class TimerViewModel : ViewModel() {
    private var _countdownTime: Long = 0

    private var _countdownHours: Long = 0
    private var _countdownMinutes: Long = 0
    private var _countdownSeconds: Long = 0

    private val _timeRemaining = MutableLiveData(0L)
    val timeRemaining: LiveData<String> = Transformations.map(_timeRemaining) { time ->
        DateUtils.formatElapsedTime(time)
    }

    fun setCountdownHours(hours: Long) {
        _countdownHours = hours * 60 * 60 * 1000
        updateCountdownTime()
    }

    fun setCountdownMinutes(minutes: Long) {
        _countdownMinutes = minutes * 60 * 1000
        updateCountdownTime()
    }

    fun setCountdownSeconds(seconds: Long) {
        _countdownSeconds = seconds * 1000
        updateCountdownTime()
    }

    private fun updateCountdownTime() {
        _countdownTime = _countdownHours + _countdownMinutes + _countdownSeconds
    }

    fun startTimer() {
        val timer = object : CountDownTimer(_countdownTime, 1000) {
            override fun onTick(time: Long) {
                _timeRemaining.value = time / 1000
            }

            override fun onFinish() {
                cancel()
            }
        }
        timer.start()
    }
}
