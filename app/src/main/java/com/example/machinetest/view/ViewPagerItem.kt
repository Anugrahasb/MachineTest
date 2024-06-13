package com.example.machinetest.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage

@Composable
fun ViewPagerItem(imageUrl : String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(104.dp),
        contentAlignment = Alignment.Center
    ) {
        SubcomposeAsyncImage(
            model = "https://i.postimg.cc/65gDYwtD/Rectangle-26.png",
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp)),
            contentScale = ContentScale.Crop, // Use Crop to fill the Box while maintaining aspect ratio
            contentDescription = "coil_image_content_description",
        )
    }
}

@Preview
@Composable
private fun ViewPagerItemPreview() {
}