package app.itakura.reirei.databaserealm

import android.widget.ImageView
import io.realm.RealmObject

open class Memo(
    open var title: String = "",
    open var detail: String = "",
    open var ido: Double = 0.0,
    open var keido: Double = 0.0
):RealmObject()





