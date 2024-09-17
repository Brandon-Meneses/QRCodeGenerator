package com.brandevsolutions.qrgenerator.ui.ui

import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun QRCodeInputScreen(navController: NavHostController) {
    var textToGenerate by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = textToGenerate,
            onValueChange = { textToGenerate = it },
            label = { Text("Enter text to generate QR") },
            modifier = Modifier.padding(16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            if (textToGenerate.isNotEmpty()) {
                val encodedUrl = Uri.encode(textToGenerate)  // Codifica la URL
                navController.navigate("displayScreen/$encodedUrl")
            }
        }) {
            Text("Generate QR Code")
        }
    }
}
