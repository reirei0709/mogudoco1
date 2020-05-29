package app.itakura.reirei.database

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import io.realm.Realm
import io.realm.RealmResults
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

     val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val memo:Memo? = read()

        if (memo != null){
            titleEditText.setText(memo.title)
            idoeditText.setText(memo.ido.toString())
            keidoeditText.setText(memo.keido.toString())

        }

        saveButton.setOnClickListener {
            val title = titleEditText.text.toString()
            val ido= idoeditText.text.toString().toDouble()
            val keido = keidoeditText.text.toString().toDouble()
            save(title,ido,keido)




        }
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    fun read(): Memo? {
        return realm.where(Memo::class.java).findFirst()
    }

    fun save(title: String, ido: Double, keido: Double){
        val memo: Memo? = read()

        realm.executeTransaction {
            if(memo != null){
                memo.title = title
                memo.ido= ido.toString().toDouble()
                memo.keido = keido.toString().toDouble()
            }else{
                val newMemo: Memo = it.createObject(Memo::class.java)
                newMemo.title = title
                newMemo.ido = ido.toString().toDouble()
                newMemo.keido = keido.toString().toDouble()
            }

            Snackbar.make(container,"登録出来ました！！", Snackbar.LENGTH_SHORT).show()
        }
    }
}
