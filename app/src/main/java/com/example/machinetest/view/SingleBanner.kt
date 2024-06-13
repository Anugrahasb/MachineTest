package com.example.machinetest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter

@Composable
fun SingleBanner(modifier: Modifier = Modifier, source: String) {
    Box(
        modifier = Modifier
            .height(96.dp)
            .fillMaxWidth()
    ) {
        CoilImageLoader(modifier = modifier, source = source)
    }
}

@Composable
internal fun CoilImageLoader(modifier: Modifier = Modifier, source: String) {
    SubcomposeAsyncImage(
        model = source,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(10.dp)),
        contentScale = ContentScale.Crop, // Use Crop to fill the Box while maintaining aspect ratio
        loading = {
            Box(
                modifier = Modifier.fillMaxWidth().height(96.dp), // Match the parent Box size
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        },
        contentDescription = "coil_image_content_description",
    )
}
