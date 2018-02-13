package me.ezeh.safarinet.message

import me.ezeh.safarinet.i18n.LangKey

class PlayerNotOnline(name: String) : SafariMessage(LangKey.PLAYER_NOT_ONLINE) {
    init {
        set("player", name)
    }
}