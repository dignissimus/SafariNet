package me.ezeh.safarinet

import me.ezeh.safarinet.data.EggDataStore
import me.ezeh.safarinet.data.SafariConfiguration
import org.bukkit.plugin.java.JavaPlugin

class SafariNet : JavaPlugin() {
    companion object {
        val VERSION = "1.0-SNAPSHOT"
    }

    override fun onEnable() {
        if (!dataFolder.exists())
            dataFolder.mkdirs()
        SafariConfiguration.GLOBAL = SafariConfiguration(this)
        EggDataStore.GLOBAL = EggDataStore(this)
        getCommand("safarinet").executor = SafariNetCommand()
        server.pluginManager.registerEvents(SafariListener(), this)
    }
}
