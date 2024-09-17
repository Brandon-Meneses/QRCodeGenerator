package com.brandevsolutions.qrgenerator.ui.ui

import android.net.Uri
import android.os.Build
import androidx.activity.ComponentActivity
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.brandevsolutions.qrgenerator.ui.functions.createPdfWithQRCode
import com.brandevsolutions.qrgenerator.ui.functions.generateQRCode
import com.brandevsolutions.qrgenerator.ui.functions.requestStoragePermissions
import com.brandevsolutions.qrgenerator.ui.functions.sanitizeFileName

@Composable
fun QRCodeDisplayScreen(encodedQrText: String, navController: NavController) {
    val qrText = Uri.decode(encodedQrText)  // Descodifica la URL
    val qrCodeBitmap = generateQRCode(qrText)
    val context = LocalContext.current  // Para obtener el contexto
    val activity = LocalContext.current as ComponentActivity

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
        // Texto de salida con margin
        Text(
            text = "QR Code for: $qrText",
            modifier = Modifier.padding(20.dp)
        )

        // Botón para volver a la pantalla de entrada
        Button(onClick = {
            navController.popBackStack()
        }) {
            Text("Generate Another QR Code")
        }

        // Botón para imprimir el QR en un PDF
        Button(onClick = {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                requestStoragePermissions(activity)
            }
            qrCodeBitmap?.let {
                val sanitizedFileName = sanitizeFileName("QRCode_$qrText")
                createPdfWithQRCode(context, it, sanitizedFileName)  // Llamar a la función para generar el PDF
            }
        }) {
            Text("Print QR Code")
        }

    }
}
