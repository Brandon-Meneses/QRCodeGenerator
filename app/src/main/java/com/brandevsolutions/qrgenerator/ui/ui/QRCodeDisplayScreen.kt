package com.brandevsolutions.qrgenerator.ui.ui

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun QRCodeDisplayScreen(encodedQrText: String, navController: NavController) {
    val qrText = Uri.decode(encodedQrText)  // Descodifica la URL
    val qrCodeBitmap = generateQRCode(qrText)

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        qrCodeBitmap?.let { bitmap ->
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = "QR Code",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.size(400.dp)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        //texto de salida con margin
        Text(
            text = "QR Code for: $qrText",
            modifier = Modifier.padding(20.dp)
        )

        // Agregar un botón para volver a la pantalla de entrada
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Generate Another QR Code")
        }
        //Boton para imprimir el qr en un pdf
        Button(onClick = {

        }){
            Text("Print QR Code")
        }
    }
}