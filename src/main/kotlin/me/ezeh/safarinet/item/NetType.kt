package me.ezeh.safarinet.item

enum class NetType {
    REUSABLE, SINGLE_USE, INVALID;

    companion object {
        val DEFAULT = SINGLE_USE
        fun parse(string: String): NetType {
            return when (string.toLowerCase()) {
                "reusable" -> REUSABLE
                "single", "single-use", "single use" -> SINGLE_USE
                else -> INVALID
            }
        }
    }
}