package app.itakura.reirei.databaserealm

import io.realm.RealmObject

open class Memo (
    open var title: String = "",
    open var ido:  Double  = 0.0,
    open var keido:Double = 0.0
):RealmObject()
