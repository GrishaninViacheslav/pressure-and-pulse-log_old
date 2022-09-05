package io.github.grishaninvyacheslav.pressure_and_pulse_log.utils

import kotlinx.coroutines.Job

class CancelableJobs {
    private val jobs: MutableList<Job> = mutableListOf()

    fun add(job: Job){
        jobs.add(job)
    }

    fun cancel(){
        jobs.forEach{
            it.cancel()
        }
        jobs.clear()
    }
}