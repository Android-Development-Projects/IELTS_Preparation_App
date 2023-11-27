package com.wolfbytetechnologies.ielts.ui.dashboard.repo

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.wolfbytetechnologies.ielts.R
import com.wolfbytetechnologies.ielts.ui.dashboard.data.DashboardItems

class MainDashboardItemsRepo(
    val context: Context
) {
    @RequiresApi(Build.VERSION_CODES.M)
    fun getDashboardItems(): List<DashboardItems> {
        return listOf(
            DashboardItems(
                getDrawable(context, R.drawable.ic_reading_lesson_card),
                context.getString(R.string.reading),
                context.getString(R.string.lesson),
                context.getColor(R.color.blue_500)
            ),
            DashboardItems(
                getDrawable(context, R.drawable.ic_test_card),
                context.getString(R.string.reading),
                context.getString(R.string.test),
                context.getColor(R.color.orange_800),

            ),
            DashboardItems(
                getDrawable(context, R.drawable.ic_test_card),
                context.getString(R.string.writing),
                context.getString(R.string.lesson),
                context.getColor(R.color.purple_400)
            ),
            DashboardItems(
                getDrawable(context, R.drawable.ic_test_card),
                context.getString(R.string.writing),
                context.getString(R.string.writing_task_1),
                context.getColor(R.color.orange_800)
            ),
            DashboardItems(
                getDrawable(context, R.drawable.ic_test_card),
                context.getString(R.string.writing),
                context.getString(R.string.writing_task_2),
                context.getColor(R.color.orange_800)
            ),
            DashboardItems(
                getDrawable(context, R.drawable.ic_speaking_image_transparent_background),
                context.getString(R.string.speaking),
                context.getString(R.string.lesson),
                context.getColor(R.color.green_400)
            ),
            DashboardItems(
                getDrawable(context, R.drawable.ic_listening_image_transparent_background),
                context.getString(R.string.speaking),
                context.getString(R.string.test),
                context.getColor(R.color.blue_500)
            ),
            DashboardItems(
                getDrawable(context, R.drawable.ic_listening_test),
                context.getString(R.string.listening),
                context.getString(R.string.lesson),
                context.getColor(R.color.red_500)
            ),
            DashboardItems(
                getDrawable(context, R.drawable.ic_listening_test),
                context.getString(R.string.listening),
                context.getString(R.string.test),
                context.getColor(R.color.pink_400)
            )
        )
    }
}