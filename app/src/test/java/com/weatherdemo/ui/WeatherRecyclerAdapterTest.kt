package com.weatherdemo.ui

import androidx.recyclerview.widget.RecyclerView
import com.nhaarman.mockitokotlin2.whenever
import com.weatherdemo.data.response.ForeCastDay
import com.weatherdemo.recyclerComponents.WeatherRecyclerAdapter
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class WeatherRecyclerAdapterTest {

    @Mock
    private var recyclerView: RecyclerView? = null
    private var recyclerItems = listOf(
        ForeCastDay(""),
        ForeCastDay(""),
        ForeCastDay("")
    )
    private var adapter: WeatherRecyclerAdapter? = null

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        adapter = Mockito.spy(WeatherRecyclerAdapter(recyclerItems)
        )
        whenever(recyclerView?.adapter).thenReturn(adapter)
    }

    @Test
    fun checkRecyclerAdapter() {
        recyclerView?.adapter = adapter
        TestCase.assertSame(adapter, recyclerView?.adapter)
    }

    @Test
    fun checkRecyclerAdapterItemCount() {
        TestCase.assertEquals(adapter?.itemCount, recyclerItems.size)
    }
}
