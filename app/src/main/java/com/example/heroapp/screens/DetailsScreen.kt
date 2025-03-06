package com.example.heroapp.screens.details

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*

import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.heroapp.model.HeroViewModel
import com.example.heroapp.navagation.AppBar
import com.example.heroapp.navagation.AppScreens


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalAnimationApi
@Composable
fun DetailsScreen(
    navController: NavController, heroViewModel: HeroViewModel,
    heroName: String?) {
    val heroResult = heroViewModel.heroResult.observeAsState()
    val heroList = heroResult.value?.body()
  //  val heroListNonNullable = heroList?.filterNotNull() ?: emptyList()
    val heroFiltered = heroList?.filter { hero ->
        hero.name == heroName
    }
    Scaffold(
        topBar = {
            heroFiltered?.get(0)?.let {
                AppBar(
                    currentScreen = AppScreens.DetailScreen.name,
                    navController = navController,
                    navigateUp = { navController.navigateUp() },
                    context = LocalContext.current,
                    textToShare = it.bio,
                    modifier = Modifier
                )
            }
        }
    ) {

        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        fontSize = 24.sp
                    )
                ) {
                    append("Bio: ")
                }
                withStyle(
                    style = SpanStyle(
                        color = Color.Blue,
                        fontSize = 20.sp
                    )
                ) {
                    heroFiltered?.get(0)?.let { append(it.bio) }
                }

            }, modifier = Modifier.padding(6.dp))

            HorizontalDivider(modifier = Modifier.padding(3.dp))
            Text(
                text = "Director: ${heroFiltered?.get(0)?.team}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Actors: ${heroFiltered?.get(0)?.publisher}",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "Rating: ${heroFiltered?.get(0)?.createdby}",
                style = MaterialTheme.typography.titleLarge
            )


        }
    }




}
