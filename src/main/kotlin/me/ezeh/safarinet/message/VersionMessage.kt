package me.ezeh.safarinet.message

import me.ezeh.safarinet.SafariNet
import me.ezeh.safarinet.i18n.LangKey

class VersionMessage :  SafariMessage(LangKey.VERSION){
    init {
        set("version", SafariNet.VERSION)
    }

}