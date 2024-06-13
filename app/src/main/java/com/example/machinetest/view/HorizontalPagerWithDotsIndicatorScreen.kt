package com.example.machinetest.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.machinetest.model.HomePageContent


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HorizontalPagerWithDotsIndicatorScreen(bannerSlider: HomePageContent.BannerSlider) {
    val pageCount = bannerSlider.contents.size
    val pagerState = rememberPagerState(pageCount = { pageCount })

    Box(modifier = Modifier) {
        HorizontalPager(state = pagerState) { page ->
            bannerSlider.contents[page].imageUrl?.let { imageUrl ->
                ViewPagerItem(imageUrl = imageUrl)
            }
        }

        ViewPagerDotsIndicator(
            Modifier
                .height(20.dp)
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            pageCount = pageCount,
            currentPageIteration = pagerState.currentPage
        )
    }
}


@Preview
@Composable
private fun HorizontalPagerWithDotsIndicatorScreenPreview() {
//    HorizontalPagerWithDotsIndicatorScreen()
}