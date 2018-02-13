package me.ezeh.safarinet.message

import me.ezeh.safarinet.i18n.LangKey
import org.bukkit.ChatColor
import org.jtwig.JtwigModel
import org.jtwig.JtwigTemplate

abstract class SafariMessage(val langKey: LangKey) {
    val template = JtwigTemplate.inlineTemplate(langKey.string)
    val model = JtwigModel.newModel()

    protected fun set(key: String, value: Any) = model.with(key, value)
    private fun getString() = template.render(model)

    fun error() = ChatColor.RED.toString() + getString() + ChatColor.RESET
    fun info() = ChatColor.GRAY.toString() + getString()
    fun warning() = ChatColor.YELLOW.toString() + getString() + ChatColor.RESET
    fun success() = ChatColor.GREEN.toString() + getString() + ChatColor.RESET
}
