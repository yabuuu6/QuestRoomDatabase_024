package com.example.pertemuan10.ui.navigation

import InsertMhsView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = DestinasiInsert.route) {
        composable (
            route = DestinasiInsert.route
        ){
            InsertMhsView(
                onBack = {}, onNavigate = {}
            )
        }
    }
}