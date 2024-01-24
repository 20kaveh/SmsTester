package com.example.kaveh.smstester.navigation

sealed class Screen(val route:String){
    object MyApp: Screen(route = "myapp_screen")
    object PermissionScreen: Screen(route = "permission_screen")
}