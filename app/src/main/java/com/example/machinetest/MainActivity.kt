package com.example.machinetest

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountBox
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.machinetest.api.RetrofitInstance
import com.example.machinetest.dao.HomePageDatabase
import com.example.machinetest.model.BottomNavigationItem
import com.example.machinetest.model.HomePageContent
import com.example.machinetest.network.NetworkUtilReceiver
import com.example.machinetest.repository.HomePageRepository
import com.example.machinetest.ui.theme.MachineTestTheme
import com.example.machinetest.view.CategoryItem
import com.example.machinetest.view.CircularProgressIndicators
import com.example.machinetest.view.HorizontalPagerWithDotsIndicatorScreen
import com.example.machinetest.view.Product
import com.example.machinetest.view.SingleBanner
import com.example.machinetest.view.TopAppBarSection
import com.example.machinetest.viewmodel.HomePageViewModel
import com.example.machinetest.viewmodel.HomePageViewModelFactory
import com.example.machinetest.viewmodel.UiState

class MainActivity : ComponentActivity() {

    private lateinit var networkUtilReceiver: NetworkUtilReceiver

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Manual ViewModel Creation
        val repository = HomePageRepository(applicationContext, RetrofitInstance.api)
        val viewModelFactory = HomePageViewModelFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory).get(HomePageViewModel::class.java)

        setContent {
            MachineTestTheme {
                when (val state = viewModel.uiState.collectAsState().value) {
                    is UiState.Empty -> {
                    }
                    is UiState.Loading -> {
                        CircularProgressIndicators()
                    }

                    is UiState.Loaded -> {
                        state.value?.let { MainScreen(it) }
                    }

                    is UiState.Error -> {

                    }
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    private fun MainScreen(value: List<HomePageContent>) {
        val items = listOf(
            BottomNavigationItem(
                title = "Home",
                selectedIcon = Icons.Filled.Home,
                unSelectedIcon = Icons.Outlined.Home,
            ),
            BottomNavigationItem(
                title = "Category",
                selectedIcon = Icons.Filled.AccountBox,
                unSelectedIcon = Icons.Outlined.AccountBox,
            ),
            BottomNavigationItem(
                title = "Cart",
                selectedIcon = Icons.Filled.ShoppingCart,
                unSelectedIcon = Icons.Outlined.ShoppingCart,
            ),
            BottomNavigationItem(
                title = "Offers",
                selectedIcon = Icons.Filled.FavoriteBorder,
                unSelectedIcon = Icons.Outlined.FavoriteBorder,
            ),
            BottomNavigationItem(
                title = "Account",
                selectedIcon = Icons.Filled.Person,
                unSelectedIcon = Icons.Outlined.Person,
            )
        )
        var selectedItemIndex by rememberSaveable {
            mutableStateOf(0)
        }
        Scaffold(
            topBar = {
                TopAppBarSection()
            },
            bottomBar = {
                NavigationBar {
                    items.forEachIndexed { index, bottomNavigationItem ->
                        NavigationBarItem(
                            selected = selectedItemIndex == index,
                            onClick = {
                                selectedItemIndex = index
                            },
                            label = {
                                Text(text = bottomNavigationItem.title)
                            },
                            icon = {
                                Box() {
                                    Icon(
                                        imageVector = if (index == selectedItemIndex) {
                                            bottomNavigationItem.selectedIcon
                                        } else bottomNavigationItem.unSelectedIcon,
                                        contentDescription = bottomNavigationItem.title,
                                        tint = if (index == selectedItemIndex) {
                                            Color.White
                                        } else Color.Gray
                                    )
                                }
                            }
                        )
                    }
                }
            }
        ) {
            when (selectedItemIndex) {
                0 -> HomePage(value) // Home tab content
                else -> BlankContent() // Other tabs content
            }
        }
    }
}
@Composable
fun HomePage(value: List<HomePageContent>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(top = 56.dp)
    ) {
        val bannerSliders = value.filterIsInstance<HomePageContent.BannerSlider>()
        val product = value.filterIsInstance<HomePageContent.Product>()
        val singleBanner = value.filterIsInstance<HomePageContent.BannerSingle>()
        val category = value.filterIsInstance<HomePageContent.Category>()

        //1.  bannerSliders
        val list = listOf(
            "https://i.postimg.cc/65gDYwtD/Rectangle-26.png",
            "https://i.postimg.cc/65gDYwtD/Rectangle-26.png",
            "https://i.postimg.cc/65gDYwtD/Rectangle-26.png",
            "https://i.postimg.cc/65gDYwtD/Rectangle-26.png",
            "https://i.postimg.cc/65gDYwtD/Rectangle-26.png"
        )
        bannerSliders.firstOrNull()?.let { bannerSlider ->
            HorizontalPagerWithDotsIndicatorScreen(bannerSlider)
        }

        Column(modifier = Modifier.padding(start = 16.dp, top = 10.dp)) {
            //2.  ProductSection

            product.firstOrNull { it.title == "Most Popular" }?.let { mostPopularProduct ->
                ProductSection(title = "Most Popular", product = mostPopularProduct)
            }
            Spacer(modifier = Modifier.height(16.dp))

            //3.  SingleBanner

            singleBanner.firstOrNull()?.let { singleBanners ->
                SingleBanner(
                    source = singleBanners.imageUrl,
                    modifier = Modifier.padding(end = 16.dp)
                )
            }
            Spacer(modifier = Modifier.height(11.dp))

            //4.  SingleBanner

            category.firstOrNull()?.let { categorys ->
                CategorySection(categorys)
            }
            Spacer(modifier = Modifier.height(7.dp))

            //4.  Featured Products

            product.firstOrNull { it.title == "Best Sellers" }?.let { mostPopularProduct ->
                ProductSection(title = "Featured Products", product = mostPopularProduct)
            }
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}


@Composable
fun BlankContent() {
    // This composable displays nothing, used for other tabs.
}

@Composable
private fun CategorySection(categories: HomePageContent.Category) {
    Row {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Categories",
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 12.sp
                )
                Text(
                    text = "View all",
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 8.sp
                )
            }
            Spacer(modifier = Modifier.height(11.dp))
            val categories22 = listOf(
                Pair(
                    "https://i.postimg.cc/wvjr5K6r/shopping-basket-full-of-variety-of-grocery-products-food-and-drink-isolated-on-white-background-3d-i.png",
                    "Grocery & Foods"
                ),
                Pair(
                    "https://i.postimg.cc/wvjr5K6r/shopping-basket-full-of-variety-of-grocery-products-food-and-drink-isolated-on-white-background-3d-i.png",
                    "Mobile & Devices"
                ),
                Pair(
                    "https://i.postimg.cc/wvjr5K6r/shopping-basket-full-of-variety-of-grocery-products-food-and-drink-isolated-on-white-background-3d-i.png",
                    "Consumer Electronics"
                ),
                Pair(
                    "https://i.postimg.cc/wvjr5K6r/shopping-basket-full-of-variety-of-grocery-products-food-and-drink-isolated-on-white-background-3d-i.png",
                    "Kids & Mom"
                )
            )
            CategoryList(categories = categories)
        }
    }
}

@Composable
private fun ProductSection(title: String,product: HomePageContent.Product) {
    Row {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 12.sp
                )
                Text(
                    text = "View all",
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .align(Alignment.CenterVertically),
                    fontSize = 8.sp
                )
            }
            Spacer(modifier = Modifier.height(11.dp))
            val productValue = listOf(
                Pair(
                    "https://i.postimg.cc/wvjr5K6r/shopping-basket-full-of-variety-of-grocery-products-food-and-drink-isolated-on-white-background-3d-i.png",
                    "Motul 5100 10W40 4-Stroke Motor"
                ),
                Pair(
                    "https://i.postimg.cc/wvjr5K6r/shopping-basket-full-of-variety-of-grocery-products-food-and-drink-isolated-on-white-background-3d-i.png",
                    "Mobile & Devices"
                ),
                Pair(
                    "https://i.postimg.cc/wvjr5K6r/shopping-basket-full-of-variety-of-grocery-products-food-and-drink-isolated-on-white-background-3d-i.png",
                    "Consumer Electronics"
                ),
                Pair(
                    "https://i.postimg.cc/wvjr5K6r/shopping-basket-full-of-variety-of-grocery-products-food-and-drink-isolated-on-white-background-3d-i.png",
                    "Kids & Mom"
                )
            )

            ProductList(categories = product )
        }
    }
}

@Composable
fun CategoryList(categories:HomePageContent.Category ) {
    LazyRow(
        modifier = Modifier,
    ) {
        items(categories.contents.size) { index ->
            val category = categories.contents[index]
            CategoryItem(value = category)
        }
    }
}

@Composable
fun ProductList(categories: HomePageContent.Product) {
    LazyRow(
        modifier = Modifier,
    ) {
        items(categories.contents.size) { index ->
            val category = categories.contents[index]
            Product(value = category)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MachineTestTheme {
    }
}
