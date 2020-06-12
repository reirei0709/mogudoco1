package app.itakura.reirei.database

import android.os.Bundle
import android.system.Os.read
import androidx.appcompat.app.AppCompatActivity
import app.itakura.reirei.databaserealm.Memo
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    val realm = Realm.getDefaultInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val memo: Memo? = read()

        saveButton.setOnClickListener {

            val Lat = intent.getDoubleExtra("Latitude", 0.0)
            val Long = intent.getDoubleExtra("Longitude", 0.0)

            val title = titleEditText.text.toString()
            val detail = titleEditText.text.toString()
            save(Lat, Long, title, detail)

            Snackbar.make(container, "登録出来ました！！", Snackbar.LENGTH_SHORT).show()



        }

        if (memo != null) {
            titleEditText.setText(memo.title)
            titleEditText.setText(memo.detail)


        }

        fun onDestroy() {
            super.onDestroy()
            realm.close()
        }


    }

    fun read(): Memo? {
        return realm.where(Memo::class.java).findAll()
    }

    fun save(
        Lat: Double,
        Long: Double,
        title: String,
        detail: String
    ) {
        val memo: Memo? = read()

        realm.executeTransaction {
            if (memo != null) {
                memo.Lat = Lat
                memo.Long = Long
                memo.title = title
                memo.detail = detail
            } else {
                val newMemo: Memo = it.createObject(Memo::class.java)
                newMemo.Lat = Lat
                newMemo.Long = Long
                newMemo.title = title
                newMemo.detail = detail
            }
            //Snackbar.make(container, "登録出来ました！！", Snackbar.LENGTH_SHORT).show()


        }

    }
}

