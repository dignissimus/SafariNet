package me.ezeh.safarinet.encoding

import me.ezeh.safarinet.data.EggDataStore
import java.nio.ByteBuffer
import java.util.*

class EggDecoder(bytes: ByteArray) {
    constructor(string: String): this(Base21.decode(string))

    val bytes = ByteBuffer.wrap(bytes)
    fun readEgg() = EggDataStore.GLOBAL.get(readUniqueId())

    private fun readUniqueId(): UUID { // ~ https://gist.github.com/jeffjohnson9046/c663dd22bbe6bb0b3f5e
        val buffer = ByteBuffer.wrap(readBytes(16))
        return UUID(buffer.long, buffer.long)
    }

    private fun readBytes(size: Int) = (1..size).map { readByte() }.toByteArray()


    private fun readByte() = bytes.get()

}