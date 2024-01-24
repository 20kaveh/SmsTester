package com.example.kaveh.smstester.navigation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.kaveh.smstester.ui.screen.MyApp
import com.example.kaveh.smstester.ui.screen.PermissionRequestScreen

@Composable
fun SetupNavGraph(
    navController: NavHostController,
    context: Context
) {
    NavHost(
        navController = navController,
        startDestination = Screen.PermissionScreen.route
    ) {
        composable(
            route = Screen.PermissionScreen.route
        ) {
            PermissionRequestScreen(navController, requestOnClick = {

            })
        }

        composable(
            route = Screen.MyApp.route
        ){
            MyApp(navHostController = navController , context = context)
        }

    }

}
