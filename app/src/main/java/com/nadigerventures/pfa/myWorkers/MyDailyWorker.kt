package com.nadigerventures.pfa.myWorkers

import android.content.Context
import android.util.Log
import androidx.work.*
import com.google.common.util.concurrent.ListenableFuture
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.*


private val TAG = "DailyWorker"

val TAG_NOTIFY_WORK = "PFA_NOTIFICATION4"
val constraints = Constraints.Builder()
    .setRequiresBatteryNotLow(true)
    .build()

val TEST_STARTVAL_HR = 13
val TEST_STARTVAL_MIN = 30

val TEST_REG_HR = TEST_STARTVAL_HR
val TEST_REGL_MIN = TEST_STARTVAL_MIN+1

class MyDailyWorker (val ctx: Context,
                     params: WorkerParameters
) : Worker(ctx, params) {
    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    companion object {
        fun  createWorkRequest(context: Context) {
            if (!isWorkScheduled(
                    TAG_NOTIFY_WORK,
                    context
                )) { // check if your work is not already scheduled
                val currentDate = Calendar.getInstance()
                val dueDate = Calendar.getInstance()
// Set Execution around 05:00:00 AM
                //    dueDate.set(Calendar.HOUR_OF_DAY, 0)
                //    dueDate.set(Calendar.MINUTE, 5)
                //    dueDate.set(Calendar.SECOND, 0)

                dueDate.set(Calendar.HOUR_OF_DAY,  TEST_STARTVAL_HR)
                dueDate.set(Calendar.MINUTE, TEST_STARTVAL_MIN)
                dueDate.set(Calendar.SECOND, 0)
                TEST_STARTVAL_HR
                if (dueDate.before(currentDate)) {
                    dueDate.add(Calendar.HOUR_OF_DAY, 24)
                }
                Log.e(TAG, "createWorkRequest")
                val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
                val dailyWorkRequest = OneTimeWorkRequestBuilder<MyDailyWorker>()
                    .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)//.setConstraints(constraints)
                    .addTag(TAG_NOTIFY_WORK)
                    .build()
                WorkManager.getInstance(context).enqueue(dailyWorkRequest)
            } else {
                Log.e(TAG,"$TAG_NOTIFY_WORK is Already scheduled" )
            }
            pruneTask(context = context)
        }
        private fun isWorkScheduled(tag: String,context: Context): Boolean {
            val instance = WorkManager.getInstance(context)
            val statuses: ListenableFuture<List<WorkInfo>> = instance.getWorkInfosByTag(tag)
            return try {
                var isScheduled  = false
                val workInfoList: List<WorkInfo> = statuses.get()
                for (workInfo in workInfoList) {
                    //  Log.e(TAG,"state = "+workInfo.state +" id = "+workInfo.id +"  tags= "+workInfo.tags
                    //          +"  outputData= "+workInfo.outputData)
                    Log.e(TAG, "workInfo to String = $workInfo")
                    val state = workInfo.state
                    if(state == WorkInfo.State.ENQUEUED ||
                        state == WorkInfo.State.RUNNING) {
                        isScheduled = true
                        break
                    }
                }
                Log.e(TAG, "isScheduled = $isScheduled")
                isScheduled
            } catch (e: ExecutionException) {
                e.printStackTrace()
                false
            } catch (e: InterruptedException) {
                e.printStackTrace()
                false
            }
        }

        private fun printTasks(tag: String,context: Context) {
            val instance = WorkManager.getInstance(context)
            val statuses: ListenableFuture<List<WorkInfo>> = instance.getWorkInfosByTag(tag)
            val workInfoList: List<WorkInfo> = statuses.get()
            for (workInfo in workInfoList) {
                Log.e(TAG, "workInfo to String = $workInfo")
            }
        }
        private fun pruneTask(context: Context) {
            WorkManager.getInstance(context).pruneWork()
        }

    }
    override fun doWork(): Result {
        var result = Result.success()
        Log.e(TAG,"Current thread outside : ${Thread.currentThread()}")
        coroutineScope.launch {
            Log.e(TAG,"Current thread inside withContext : ${Thread.currentThread()}")
            Log.e(TAG,"Current coroutine: $CoroutineName")
            printTasks(TAG_NOTIFY_WORK, context = ctx)
            Log.e(TAG, "create new Work Request in doWork !!")
            val currentDate = Calendar.getInstance()
            val dueDate = Calendar.getInstance()
            // Set Execution around 05:00:00 AM
            dueDate.set(Calendar.HOUR_OF_DAY, TEST_REG_HR)
            dueDate.set(Calendar.MINUTE, TEST_REGL_MIN)
            dueDate.set(Calendar.SECOND, 0)

            //    dueDate.set(Calendar.HOUR_OF_DAY, 0)
            //    dueDate.set(Calendar.MINUTE, 5)
            //    dueDate.set(Calendar.SECOND, 0)

            if (dueDate.before(currentDate) ||
                dueDate.equals(currentDate)) {
                Log.e(TAG,"Due date is before or equal to current date")
                dueDate.add(Calendar.HOUR_OF_DAY, 24)
            }
            val timeDiff = dueDate.timeInMillis - currentDate.timeInMillis
            val dailyWorkRequest = OneTimeWorkRequestBuilder<MyDailyWorker>()
                .setConstraints(constraints)
                .setInitialDelay(timeDiff, TimeUnit.MILLISECONDS)
                .addTag(TAG_NOTIFY_WORK)
                .build()
            WorkManager.getInstance(applicationContext)
                .enqueue(dailyWorkRequest)
            pruneTask(context = ctx)
            Log.e(TAG,"Finished doing work" )
            Result.success()
        }
        Log.e(TAG,"Returning the result" )
        return result
    }
}

