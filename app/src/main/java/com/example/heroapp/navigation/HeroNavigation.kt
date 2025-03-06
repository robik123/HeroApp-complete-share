package com.example.heroapp.navagation

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.heroapp.model.HeroViewModel

import com.example.heroapp.screens.home.HomeScreen
import com.example.heroapp.screens.details.DetailsScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    currentScreen: String,
    navController: NavController,
    navigateUp: () -> Unit,
    textToShare : String,
    context: Context,
    modifier: Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val canNavigateBack = backStackEntry?.destination?.route != AppScreens.HomeScreen.name
    Log.d("canNavigateBack",canNavigateBack.toString());
    TopAppBar(
        title = { Text("Hero App") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.secondary
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        contentDescription = ""
                    )
                }
            }
        },
        actions = {
            if(textToShare.isNotEmpty()){
                IconButton(onClick = {
                    val intent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_SUBJECT,"Here is plot")
                        putExtra(Intent.EXTRA_TEXT,textToShare)

                    }
                    context.startActivity(Intent.createChooser(intent,"Share option"))
                }
                ) {
                    Icon(imageVector = Icons.Default.Share, contentDescription = "Share items")
                }
            }

        }
    )
}
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MovieNavigation() {
    val navController = rememberNavController()
    val heroViewModel: HeroViewModel = viewModel()
    heroViewModel.getData()
        NavHost(navController = navController,
            startDestination = AppScreens.HomeScreen.name,
            modifier = Modifier.fillMaxSize()
                    ){
            composable(AppScreens.HomeScreen.name) {

                HomeScreen(navController = navController,heroViewModel)
            }
            composable(AppScreens.DetailScreen.name +"/{name}",
                arguments = listOf(navArgument(name = "name"){type = NavType.StringType})){
                    backStackEntry ->
                DetailsScreen(
                    navController = navController,heroViewModel,
                    backStackEntry . arguments ?. getString ("name"),
                )

            }


        }


}
