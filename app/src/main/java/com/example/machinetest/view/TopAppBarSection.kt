package com.example.machinetest.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.machinetest.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarSection() {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF92C848)),
            modifier = Modifier.background(color = Color(0xFF92C848)),
            title = {
                Row(
                    modifier = Modifier.padding(vertical = 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Icon on the start
                    IconButton(onClick = { /* Handle icon click */ }) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = null)
                    }

                    Spacer(modifier = Modifier.weight(1f))
                    SearchBar()
                    Spacer(modifier = Modifier.weight(1f))

                    // Notification button on the end
                    IconButton(onClick = { /* Handle icon click */ }) {
                        Icon(
                            modifier = Modifier
                                .height(12.dp)
                                .width(12.dp),
                            painter = painterResource(id = R.drawable.noification),
                            contentDescription = null,
                            tint = Color(0xFFFFFFFF)
                        )
                    }
                }
            },
        )
    }
}

@Composable
fun SearchBar() {
    Box(
        modifier = Modifier
            .width(232.dp)
            .height(24.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(color = Color.White),
        contentAlignment = Alignment.CenterEnd
    ) {
        IconButton(onClick = { /* Handle search icon click */ }) {
            Icon(
                modifier = Modifier
                    .height(9.dp)
                    .width(9.dp),
                painter = painterResource(id = R.drawable.search_icon),
                contentDescription = null,
                tint = Color(0xFFD9D9D9)
            )
        }
    }
}