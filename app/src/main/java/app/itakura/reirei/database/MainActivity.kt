package app.itakura.reirei.database

import android.os.Bundle
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

        if (memo != null) {
            titleEditText.setText(memo.title)
            titleEditText.setText(memo.detail)


        }

        saveButton.setOnClickListener {

            val your = intent.getStringExtra("yourspot")

            val title = titleEditText.text.toString()
            val detail = detail.text.toString()
            save(title, detail)


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun read(): Memo? {
        return realm.where(Memo::class.java).findFirst()
    }

    fun save(
        title: String,
        detail: String
    ) {
        val memo: Memo? = read()

        realm.executeTransaction {
            if (memo != null) {
                memo.title = title
                memo.detail = detail.toString()
            } else {
                val newMemo: Memo = it.createObject(Memo::class.java)
                newMemo.title = title
                newMemo.detail = detail.toString()
            }

            Snackbar.make(container, "登録出来ました！！", Snackbar.LENGTH_SHORT).show()
        }
    }

}

