package me.ezeh.safarinet

import org.bukkit.permissions.Permissible


enum class SafariNetPermission(vararg val permissions: String) {
    RELOAD("safarinet.reload"),
    GIVE("safarinet.give"),
    USE_SINGLE("safarinet.use.single", "safarinet.use.single-use", "safarinet.use", "safarinet.use.all"),
    USE_REUSABLE("safarinet.use.reusable", "safarinet.use", "safarinet.us.all");

    val permission = permissions[0]
}

fun Permissible.hasPermission(permission: SafariNetPermission) = permission.permissions.map { this.hasPermission(it) }.any { it }
