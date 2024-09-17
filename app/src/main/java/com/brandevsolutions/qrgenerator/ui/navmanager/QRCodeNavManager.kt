package com.brandevsolutions.qrgenerator.ui.navmanager

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.brandevsolutions.qrgenerator.ui.ui.QRCodeDisplayScreen
import com.brandevsolutions.qrgenerator.ui.ui.QRCodeInputScreen

@Composable
fun QRCodeNavManager(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "inputScreen",
        modifier = modifier
    ) {
        composable("inputScreen") {
            QRCodeInputScreen(navController)
        }
        composable("displayScreen/{qrText}") { backStackEntry ->
            val qrText = backStackEntry.arguments?.getString("qrText") ?: ""
            QRCodeDisplayScreen(qrText, navController)
        }
    }
}
