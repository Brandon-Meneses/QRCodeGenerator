package com.brandevsolutions.qrgenerator.ui.functions

fun sanitizeFileName(input: String): String {
    // Reemplazar caracteres no válidos en nombres de archivos (/:?*<>|")
    return input.replace(Regex("[^a-zA-Z0-9_\\-]"), "_")
}
