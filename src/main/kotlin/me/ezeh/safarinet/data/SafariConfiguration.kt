package me.ezeh.safarinet.data

import me.ezeh.safarinet.SafariNet
import me.ezeh.safarinet.encoding.EggDecoder
import me.ezeh.safarinet.encoding.EggEncoder
import me.ezeh.safarinet.item.NetType
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.LivingEntity
import java.io.File

class SafariConfiguration(plugin: SafariNet) : YamlConfiguration() {
    // TODO: When a config value is invalid, tell the user that the config is invalid before returning the default value
    // TODO: i18n on exceptions, fill out exceptions
    private val configFile = File(plugin.dataFolder, "config.yml")

    init {
        if (!configFile.exists())
            configFile.createNewFile()
    }

    companion object {
        lateinit var GLOBAL: SafariConfiguration

        private val DEFAULT_MATERIAL = Material.MONSTER_EGG
        private val DEFAULT_LORE = listOf("Right click on an entity to catch it!")

        private val DEFAULT_REUSABLE_DISPLAY_NAME = ChatColor.YELLOW.toString() + "SafariNet (Reusable)"
        private val DEFAULT_SINGLE_DISPLAY_NAME = ChatColor.YELLOW.toString() + "SafariNet (Single)"

        private val REUSABLE_DISPLAY_NAME_KEYS = listOf("reusable.display-name", "item.display-name")
        private val REUSABLE_LORE_KEYS = listOf("reusable.lore", "item.lore")
        private val REUSABLE_MATERIAL_KEYS = listOf("reusable.material", "item.material")
        private val SINGLE_DISPLAY_NAME_KEYS = listOf("single-use.display-name", "single.display-name", "item.display-name")
        private val REUSABLE_LORE_IDENT = ChatColor.RED.toString() + ChatColor.RESET

        private val SINGLE_LORE_KEYS = listOf("single-use.lore", "single.lore", "item.lore")
        private val SINGLE_MATERIAL_KEYS = listOf("single-use.material", "single.material", "item.material")
        private val SINGLE_LORE_IDENT = ChatColor.BLUE.toString() + ChatColor.RESET
    }

    fun reload() {
        load(configFile)
    }

    init {
        load(configFile)
    }

    fun type(type: NetType) =
            when (type) {
                NetType.SINGLE_USE -> singleMaterial
                NetType.REUSABLE -> reusableMaterial
                else -> throw IllegalStateException()
            }

    fun displayName(type: NetType) =
            when (type) {
                NetType.SINGLE_USE -> singleDisplayName
                NetType.REUSABLE -> reusableDisplayName
                else -> throw IllegalStateException()
            }

    fun lore(type: NetType, entity: LivingEntity? = null) =
            when (type) {
                NetType.SINGLE_USE -> singleLore
                NetType.REUSABLE -> reusableLore
                else -> throw IllegalStateException()
            } + EggEncoder(type, entity).loreEncode()


    private fun ident(last: String) = EggDecoder(last).readEgg()

    fun ident(lore: List<String>) = ident(lore.last())

    private val singleDisplayName: String
        get() = SINGLE_DISPLAY_NAME_KEYS
                .firstOrNull { isString(it) && getString(it) != null }
                ?.let { getString(it) }
                ?: DEFAULT_SINGLE_DISPLAY_NAME

    private val singleLore: List<String>
        get() {
            val stringLore = SINGLE_LORE_KEYS.firstOrNull { isString(it) && getString(it) != null }
                    ?.let { getString(it) }
            if (stringLore != null)
                return listOf(stringLore)
            return SINGLE_LORE_KEYS.firstOrNull { isList(it) && getList(it) != null }?.let { getStringList(it) }
                    ?: DEFAULT_LORE
        }

    private val reusableDisplayName: String
        get() = REUSABLE_DISPLAY_NAME_KEYS
                .firstOrNull { isString(it) && getString(it) != null }
                ?.let { getString(it) }
                ?: DEFAULT_REUSABLE_DISPLAY_NAME

    private val reusableLore: List<String>
        get() {
            val stringLore = SINGLE_LORE_KEYS.firstOrNull { isString(it) && getString(it) != null }
                    ?.let { getString(it) }
            if (stringLore != null)
                return listOf(stringLore)
            return REUSABLE_LORE_KEYS.firstOrNull { isList(it) && getList(it) != null }.let { getStringList(it) }
                    ?: DEFAULT_LORE
        }

    private val reusableMaterial: Material
        get() = REUSABLE_MATERIAL_KEYS
                .firstOrNull { isString(it) && getString(it) != null }
                ?.let { Material.getMaterial(it) }
                ?: DEFAULT_MATERIAL

    private val singleMaterial: Material
        get() = SINGLE_MATERIAL_KEYS
                .firstOrNull { isString(it) && getString(it) != null }
                ?.let { Material.getMaterial(it) }
                ?: DEFAULT_MATERIAL
}
