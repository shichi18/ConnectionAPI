package com.github.shichi18.connectionapi

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    //作成するinner class以外の記述は省略

    //Callback<T>を継承したクラスを作る
    //innerクラスだと親クラスのメンバ変数にもアクセスできるので、MainActibity内にクラスを作ると便利
    inner class ZipApiDataCallback : retrofit2.Callback<ResponseModel> {

        //データ受信時に発生すると呼ばれるメソッド
        override fun onResponse(call: Call<ResponseModel>?, response: Response<ResponseModel>?) {

            //response.body()がZipApiDataの本体になる
            //nullじゃなければログを残す
            if (response?.body()?.results != null) {
                if (response.body()!!.results!!.count() > 0) {
                    Log.v("nullpo", response.body()!!.results!![0].address1)
                    Log.v("nullpo", response.body()!!.results!![0].address2)
                    Log.v("nullpo", response.body()!!.results!![0].address3)
                    button.setOnClickListener {
                        addressText1.text = response.body()!!.results!![0].address1
                        addressText2.text = response.body()!!.results!![0].address2
                        addressText3.text = response.body()!!.results!![0].address3


                    }

                }
            }
        }

        //失敗したときに呼ばれるメソッド
        override fun onFailure(call: Call<ResponseModel>?, t: Throwable?) {
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val numtext = findViewById<EditText>(R.id.numText)
        val address1 = findViewById<TextView>(R.id.addressText1)
        val address2 = findViewById<TextView>(R.id.addressText2)
        val address3 = findViewById<TextView>(R.id.addressText3)

        //retrofitのインスタンスを生成して、様々な設定をする
        var retrofit = Retrofit.Builder().let {

            //WebAPIのアクセス先のドメインを指定する
            it.baseUrl("http://zipcloud.ibsnet.co.jp/")

            //受信したjson形式のデータを、kotlinのデータクラスに格納するコンバータを指定する
            //今回はgoogleのgsonというコンバータのインスタンスを渡す
            it.addConverterFactory(GsonConverterFactory.create())
            it.build()
        }

        //サービスのインスタンスを生成する
        //アクセス先のURLとデータクラスの型を記述したインターフェースを指定する
        var service = retrofit.create(APIsipcode::class.java)

        //これが実行させるメソッド
        //引数として郵便番号を指定する
        //enqueueは非同期処理（別スレッド）で通信を行うメソッド
        //引数としてコールバックさせるクラスを指定する
        service.apiDemo("0790177").enqueue(ZipApiDataCallback())
    }


}
