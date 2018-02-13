package me.ezeh.safarinet.encoding

import me.ezeh.safarinet.data.EggData
import me.ezeh.safarinet.data.EggDataStore
import me.ezeh.safarinet.encoding.EncodingBytes.INVALID
import me.ezeh.safarinet.encoding.EncodingBytes.REUSABLE
import me.ezeh.safarinet.encoding.EncodingBytes.SINGLE
import me.ezeh.safarinet.item.NetType
import org.bukkit.entity.LivingEntity
import java.io.ByteArrayOutputStream
import java.io.ObjectOutputStream
import java.nio.ByteBuffer
import java.util.*

class EggEncoder(egg: EggData) { // Used to be used to store data in the lore, now only stores UUIDs
    constructor(type: NetType, entity: LivingEntity? = null, uuid: UUID = UUID.randomUUID()) : this(EggData(uuid, type, entity?.uniqueId))

    private val bytes = ArrayList<Byte>()

    init {
        writeEgg(egg)
    }

    private fun writeEgg(egg: EggData) {
        writeUniqueId(egg.eggId)
        EggDataStore.GLOBAL.add(egg.eggId, egg)
    }

    private fun bytes() = bytes.toByteArray()

    fun loreEncode(): String {
        return Base21.encode(bytes())
    }


    private fun writeType(type: NetType) {
        writeByte(when (type) {
            NetType.SINGLE_USE -> SINGLE
            NetType.REUSABLE -> REUSABLE
            NetType.INVALID -> INVALID
        })
    }

    private fun writeUniqueId(uuid: UUID) { // ~ https://gist.github.com/jeffjohnson9046/c663dd22bbe6bb0b3f5e
        val buffer = ByteBuffer.wrap(ByteArray(16))
        buffer.putLong(uuid.mostSignificantBits)
        buffer.putLong(uuid.leastSignificantBits)
        writeBytes(buffer.array())
    }

    private fun writeBytes(bytes: ByteArray) {
        bytes.map { writeByte(it) }
    }

    private fun writeByte(byte: Byte) {
        bytes.add(byte)
    }

    private fun writeEntity(entity: LivingEntity) {
        val byteStream = ByteArrayOutputStream()
        val oos = ObjectOutputStream(byteStream)
        oos.writeObject(entity)
        writeByteArray(byteStream.toByteArray())
    }

    private fun writeByteArray(bytes: ByteArray) {
        writeSize(bytes.size)
        bytes.forEach { writeByte(it) }
    }

    private fun writeSize(size: Int) {
        var x = size
        do {
            val toAdd = if (size < 255) size else 255
            x -= toAdd
            writeByte(toAdd.toByte())
        } while (x != 0) // 100 --> [100], 255 --> [255], 256 -> [255, 1]
    }

    fun base64Encode() = Base64.getEncoder().encode(bytes())
}