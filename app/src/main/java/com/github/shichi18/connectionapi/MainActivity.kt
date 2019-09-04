package com.github.shichi18.connectionapi

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    //innerクラスだと親クラスのメンバ変数にもアクセスできる
    // MainActivity内にクラスを作ると便利
    inner class ZipApiDataCallback : retrofit2.Callback<ResponseModel> {//Callback<T>を継承

        //データ受信時に発生すると呼ばれるメソッド
        override fun onResponse(call: Call<ResponseModel>?, response: Response<ResponseModel>?) {
            //response.body()がZipApiData本体
            if (response?.body()?.results != null) {
                if (response.body()!!.results.count() > 0) {
                    numTextView.text = response.body()!!.results[0].zipcode
                    addressTextKana1.text = response.body()!!.results[0].kana1
                    addressTextKana2.text = response.body()!!.results[0].kana2
                    addressTextKana3.text = response.body()!!.results[0].kana3
                    addressText1.text = response.body()!!.results[0].address1
                    addressText2.text = response.body()!!.results[0].address2
                    addressText3.text = response.body()!!.results[0].address3
                }
            } else {
                Toast.makeText(applicationContext, "存在しない郵便番号です", Toast.LENGTH_LONG).show()

            }
        }

        //失敗したときに呼ばれるメソッド
        override fun onFailure(call: Call<ResponseModel>?, t: Throwable?) {
            Toast.makeText(applicationContext, "失敗しました", Toast.LENGTH_LONG).show()

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //enqueueは非同期処理（別スレッド）で通信を行うメソッド
        //引数としてコールバックさせるクラスを指定
        val retrofit = Retrofit.Builder().let {
            it.baseUrl("http://zipcloud.ibsnet.co.jp/")
            it.addConverterFactory(GsonConverterFactory.create())
            it.build()
        }

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val numText = findViewById<EditText>(R.id.numText).text.toString()
            println(numText)
            if (numText.length == 7) {
                val service = retrofit.create(APIData::class.java)
                service.apiDemo(numText).enqueue(ZipApiDataCallback())
            } else {
                Toast.makeText(applicationContext, "郵便番号は7桁です", Toast.LENGTH_LONG).show()
            }
        }
    }
}
