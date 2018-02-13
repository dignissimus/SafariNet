package me.ezeh.safarinet

import me.ezeh.safarinet.SafariNetPermission.GIVE
import me.ezeh.safarinet.data.SafariConfiguration
import me.ezeh.safarinet.item.NetType
import me.ezeh.safarinet.item.SafariNetItem
import me.ezeh.safarinet.message.*
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class SafariNetCommand : CommandExecutor { // returns true to output its own error message // TODO: So. Many. Returns! Remove unnecessary ones, make the code look nicer
    override fun onCommand(sender: CommandSender, command: Command, alias: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage(UsageMessage(alias).error())
            return true
        }
        val subCommand = args[0] // TODO: when statement on the subCommand variable seems in order
        if (subCommand == "give") {
            if (!sender.hasPermission(GIVE)) {
                sender.sendMessage(InvalidPermission(GIVE).error())
                return true
            }

            if (args.size < 2) {
                sender.sendMessage(UsageMessage(alias).error())
                return true
            }
            val player = Bukkit.getPlayer(args[1])
            if (player == null || !player.isOnline) {
                sender.sendMessage(PlayerNotOnline(args[1]).error())
                return true
            }
            var type = NetType.DEFAULT
            var count = 1
            if (args.size > 3) {
                val parsedType = NetType.parse(args[2])
                if (parsedType == NetType.INVALID) {
                    sender.sendMessage(UsageMessage(alias).error())
                    return true
                }
                type = parsedType

            }
            if (args.size > 4) {
                try {
                    count = Integer.parseInt(args[3])
                }
                catch (exception: NumberFormatException) {
                    sender.sendMessage(UsageMessage(alias).error())
                    return true
                }
            }
            val item = SafariNetItem(type, null, count).item()
            player.inventory.addItem(item)
            return true
        }
        if (subCommand == "info" || subCommand == "version") {
            sender.sendMessage(VersionMessage().info()) // Send info message
            return true
        }
        if (subCommand == "reload") {
            SafariConfiguration.GLOBAL.reload() // Reload config
            sender.sendMessage(ReloadSuccessful().success())
            return true
        }

        return true
    }
}

