package me.ezeh.safarinet.message

import me.ezeh.safarinet.SafariNet
import me.ezeh.safarinet.i18n.LangKey

class UsageMessage(val command: String, val version: String = SafariNet.VERSION) : SafariMessage(LangKey.USAGE) {
    init {
        set("command", command)
        set("version", version)
    }
}