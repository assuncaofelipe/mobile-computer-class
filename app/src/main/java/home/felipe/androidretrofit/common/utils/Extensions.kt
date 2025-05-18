package home.felipe.androidretrofit.common.utils

fun String.isValidCep(): Boolean {
    return this.matches(Regex("^\\d{8}$"))
}