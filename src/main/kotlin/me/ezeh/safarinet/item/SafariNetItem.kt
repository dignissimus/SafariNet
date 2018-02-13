package me.ezeh.safarinet.item

import me.ezeh.safarinet.data.SafariConfiguration
import org.bukkit.entity.LivingEntity
import org.bukkit.inventory.ItemStack

class SafariNetItem(var type: NetType = NetType.SINGLE_USE, val entity: LivingEntity? = null, var count: Int = 1) { // TODO: fill out exceptions, i18n exceptions?
    val config = SafariConfiguration.GLOBAL

    fun item(): ItemStack {
        val item = ItemStack(config.type(type), count)
        val meta = item.itemMeta

        meta.displayName = config.displayName(type)
        meta.lore = config.lore(type, entity)

        item.itemMeta = meta

        return item
    }
}

