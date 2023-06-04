package com.example.catsanddogs.sdk

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.kotlin.*
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.shadows.ShadowLooper

private const val OBJECT_TYPE = "man"

@RunWith(RobolectricTestRunner::class)
class CsAnalyticsCountTest : TestCase() {

    @Test
    fun `test count objects by position type`() {
        val deb = Debouncer("Background", Debouncer.DEFAULT_DELAY)
        val shadowLooper: ShadowLooper = Shadows.shadowOf(deb.workerHandler.looper)

        val csAnalytics = CsAnalytics(mock(), deb)
        csAnalytics.loggersList = mutableListOf(mock())

        val view : View = mock()
        whenever(view.contentDescription).thenReturn(OBJECT_TYPE)
        val viewHolder : RecyclerView.ViewHolder = CsAnalyticsTest.TestViewHolder(view)

        csAnalytics.track(viewHolder, 0)
        csAnalytics.track(viewHolder, 1)
        csAnalytics.track(viewHolder, 2)

        csAnalytics.trigger(viewHolder, 2)
        shadowLooper.runToEndOfTasks()
        val expectedCountOne = csAnalytics.makeLogEntry(2, OBJECT_TYPE, 3)
        verify(csAnalytics.loggersList[0]).log(expectedCountOne)

        csAnalytics.trigger(viewHolder, 1)
        shadowLooper.runToEndOfTasks()
        val expectedCountTwo = csAnalytics.makeLogEntry(1, OBJECT_TYPE, 2)
        verify(csAnalytics.loggersList[0]).log(expectedCountTwo)
    }

}