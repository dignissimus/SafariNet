package me.ezeh.safarinet.encoding

import org.bukkit.ChatColor
import java.math.BigInteger
import kotlin.experimental.and
import kotlin.math.pow

object Base21 { // TODO: fix
    private val alphabet = ChatColor.values().map { it.char } // Reset char is not used

    fun encode(bytes: ByteArray): String {
        var num = BigInteger(
                bytes.joinToString("") {
                    Integer.toBinaryString((it and 0xff.toByte()).toInt())
                            .replace("\u0030", "0")
                            .replace("\u0031", "1")
                }
                , 2)
        var result = ""
        var place = 0
        while (num > 0) {
            val mod = (num % 21).toInt()
            result += ChatColor.getByChar(alphabet[mod])
            num -= mod * (21 pow place++) // num -= mod * (21 ** place++)
        }
        return result.reversed() + ChatColor.RESET
    }

    fun decode(string: String): ByteArray {
        val num = ChatColor.stripColor(string).map { alphabet.indexOf(it) }.mapIndexed({ index, value -> index pow value }).sum()
        var binary = Integer.toBinaryString(num)
                .replace("\u0030", "0")
                .replace("\u0031", "1")
        while (binary.length % 8 != 0)
            binary = "0" + binary
        return binary.chunked(8).map { Integer.parseInt(binary, 2).toByte() }.toByteArray()
    }

    private infix fun Int.pow(other: Int): Int = this.toDouble().pow(other).toInt()

    private operator fun BigInteger.minus(int: Int) = minus(BigInteger.valueOf(int.toLong()))

    private operator fun BigInteger.rem(int: Int) = rem(java.math.BigInteger.valueOf(int.toLong()))

    private operator fun BigInteger.compareTo(int: Int) = compareTo(BigInteger.valueOf(int.toLong()))
}
