package me.ezeh.safarinet.data

import me.ezeh.safarinet.SafariNet
import me.ezeh.safarinet.item.NetType
import org.bukkit.Bukkit
import java.io.File
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*
import kotlin.collections.HashMap

class EggDataStore(plugin: SafariNet) {
    companion object {
        lateinit var GLOBAL: EggDataStore
    }

    private val dataFile = File(plugin.dataFolder, "data.bin")
    private var data = HashMap<UUID, EggData>()

    init {
        if (!dataFile.exists()) {
            dataFile.createNewFile()
            saveData()
        }
        loadData()
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, this::saveData, 0, 20 * 60) // save data to file every minute // TODO: only save when data has changed
    }

    fun add(uuid: UUID, egg: EggData) {
        data[uuid] = egg
    }

    fun get(uuid: UUID) = data[uuid]

    private fun loadData() {
        val ois = ObjectInputStream(dataFile.inputStream())
        val read = ois.readObject() as? HashMap<UUID, Pair<NetType, UUID?>>
                ?: throw IllegalStateException() // TODO: i18n
        data = HashMap(read.mapValues { EggData(it.key, it.value.first, it.value.second) })
    }

    private fun saveData() {
        val toSave = data.mapValues { Pair(it.value.type, it.value.entityId) }
        val oos = ObjectOutputStream(dataFile.outputStream())
        oos.writeObject(toSave)
    }
}