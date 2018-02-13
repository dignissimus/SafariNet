package me.ezeh.safarinet

import me.ezeh.safarinet.data.SafariConfiguration
import me.ezeh.safarinet.item.NetType
import me.ezeh.safarinet.item.SafariNetItem
import me.ezeh.safarinet.message.InvalidPermission
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractAtEntityEvent

class SafariListener : Listener { // TODO: exceptions, can be made shorter, code is being reused
    val config = SafariConfiguration.GLOBAL
    @EventHandler
    fun onRightClickEntityEvent(event: PlayerInteractAtEntityEvent) {
        val player = event.player
        val entity = event.rightClicked as? LivingEntity ?: return
        val item = player.inventory.itemInMainHand
        val ident = config.ident(item.itemMeta.lore) ?: throw IllegalStateException()

        when (ident.type) {
            NetType.SINGLE_USE -> {
                if (!(player.hasPermission(SafariNetPermission.USE_SINGLE))) {
                    player.sendMessage(InvalidPermission(SafariNetPermission.USE_SINGLE).error())
                    return
                }

                item.amount -= 1
                entity.suspend()
                val clone = SafariNetItem(NetType.SINGLE_USE, ident.entity).item()
                player.inventory.addItem(clone)

            }
            NetType.REUSABLE -> {
                if (!(player.hasPermission(SafariNetPermission.USE_REUSABLE))) {
                    player.sendMessage(InvalidPermission(SafariNetPermission.USE_REUSABLE).error())
                    return
                }

                item.amount -= 1
                entity.suspend()

                val clone = SafariNetItem(NetType.REUSABLE, ident.entity).item()
                player.inventory.addItem(clone)
            }
            else -> {
                // Do nothing
            }
        }
    }
}

fun LivingEntity.suspend() {
    setAI(false)
    isInvulnerable = true
    canPickupItems = false
    removeWhenFarAway = false
    isCollidable = false
}

fun LivingEntity.reanimate() {
    setAI(false)
    isInvulnerable = false
    canPickupItems = true
    removeWhenFarAway = true
    isCollidable = true
}