package com.example.machinetest.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.machinetest.R
import com.example.machinetest.model.Content

@Composable
fun Product(
  value: Content
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val isLandscape = remember { screenWidth > screenHeight }

    val cardWidth = if (isLandscape) screenWidth / 6 else screenWidth / 4
    val cardHeight = if (isLandscape) screenHeight / 4 else screenWidth / 5

    Card(
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(4.dp)
            .width(cardWidth)
            .height(156.dp)
            .background(color = Color.White)
            .border(1.dp, color = Color.Gray, shape = RoundedCornerShape(10.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            value.imageUrl.let {
                CoilImage(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp)),
                    source = "https://i.postimg.cc/c1B00NGz/Lenovo-K3-Mini-Outdoor-Wireless-Speaker-1.png"
                )
            }
            Column(
                modifier = Modifier.padding(start = 8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                ColorCard(value.discount.toString())
                Spacer(modifier = Modifier.height(7.dp))
                Text(
                    text = value.productName.toString(),
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontSize = 6.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Black
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    lineHeight = 8.sp // Adjust the lineHeight value as needed

                )
                Spacer(modifier = Modifier.height(7.dp))
                value.productRating?.let { RatingStar2(it) }
                Spacer(modifier = Modifier.height(7.dp))
                value.offerPrice?.let { value.actualPrice?.let { it1 -> PriceRow(offerPrice = it, actualPrice = it1) } }
                Spacer(modifier = Modifier.height(7.dp))
            }
        }
    }
}
@Composable
fun PriceRow(offerPrice: String, actualPrice: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = offerPrice,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 5.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Black
            ),
            lineHeight = 6.sp,
            modifier = Modifier.padding(end = 2.dp)
        )

        Text(
            text = actualPrice,
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 5.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray,
                textDecoration = TextDecoration.LineThrough
            ),
            lineHeight = 6.sp
        )
    }
}


@Composable
fun ColorCard(text: String) {
    Box(
        modifier = Modifier
            .height(16.dp)
            .width(44.dp)
            .clip(RoundedCornerShape(5.dp))
            .background(Color(0xFFFB7B4E)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "sale $text",
            textAlign = TextAlign.Center,
            fontSize = 5.sp,
            modifier = Modifier

        )
    }
}

@Composable
fun RatingStar2(rate: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Start
    ) {
        repeat(5) { index ->
            val isSelected = index < rate
            Image(
                modifier = Modifier
                    .height(8.dp)
                    .width(8.dp)
                    .padding(top = 3.dp),
                painter = if (isSelected) {
                    painterResource(id = R.drawable.vector__6_)
                } else {
                    painterResource(id = R.drawable.vector__4_)
                },
                contentDescription = "Star $index"
            )
        }
    }
}