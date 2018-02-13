package me.ezeh.safarinet.message

import me.ezeh.safarinet.SafariNetPermission
import me.ezeh.safarinet.i18n.LangKey

class InvalidPermission(val permission: SafariNetPermission): SafariMessage(LangKey.INVALID_PERMISSION) {
    init {
        set("permission", permission.permission)
    }
}