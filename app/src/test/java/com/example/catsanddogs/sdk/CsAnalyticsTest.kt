package com.example.catsanddogs.sdk

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*

private const val MAN_TYPE = "man"
private const val WOLF_TYPE = "wolf"

@RunWith(MockitoJUnitRunner::class)
class CsAnalyticsTest : TestCase() {

    @Mock
    private lateinit var mockContext: Context
    @Mock
    private lateinit var debouncer: Debouncer

    @Test
    fun `test trigger of debouncer`() {
        val csAnalytics = CsAnalytics(mockContext, debouncer)
        val view : View = mock()
        whenever(view.contentDescription).thenReturn(MAN_TYPE)
        val viewHolder : RecyclerView.ViewHolder = TestViewHolder(view)
        csAnalytics.track(viewHolder, 0)
        csAnalytics.track(viewHolder, 1)

        csAnalytics.trigger(viewHolder, 0)
        csAnalytics.trigger(viewHolder, 1)
        csAnalytics.trigger(viewHolder, 0)
        verify(debouncer, times(3)).postDebounceAction(any())
    }

    @Test
    fun `test tracking of types`() {
        val csAnalytics = CsAnalytics(mockContext, debouncer)
        val viewOne : View = mock()
        whenever(viewOne.contentDescription).thenReturn(MAN_TYPE)
        val viewHolderOne : RecyclerView.ViewHolder = TestViewHolder(viewOne)
        csAnalytics.track(viewHolderOne, 0)
        csAnalytics.track(viewHolderOne, 2)

        val viewTwo : View = mock()
        whenever(viewTwo.contentDescription).thenReturn(WOLF_TYPE)
        val viewHolderTwo : RecyclerView.ViewHolder = TestViewHolder(viewTwo)
        csAnalytics.track(viewHolderTwo, 5)
        csAnalytics.track(viewHolderTwo, 8)
        csAnalytics.track(viewHolderTwo, 9)

        assert(csAnalytics.animalMap[WOLF_TYPE]!!.size == 3)
        assert(csAnalytics.animalMap[MAN_TYPE]!!.size == 2)
    }

    class TestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}