fun identity(num: Int): Int {
    return num
}

fun half(num: Int): Int {
    return num / 2
}

fun zero(num: Int): Int {
    return 0
}

fun generate(functionName: String): (Int) -> Int {
    return when (functionName) {
        "identity" -> ::identity
        "half" -> ::half
        else -> ::zero
    }
}
