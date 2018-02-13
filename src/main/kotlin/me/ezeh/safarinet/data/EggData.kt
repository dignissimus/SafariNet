package me.ezeh.safarinet.data

import me.ezeh.safarinet.item.NetType
import org.bukkit.Bukkit
import org.bukkit.entity.LivingEntity
import java.util.*

class EggData(val eggId: UUID, val type: NetType, val entityId: UUID?) {
    val entity: LivingEntity?
        get() = Bukkit.getEntity(entityId) as? LivingEntity
}