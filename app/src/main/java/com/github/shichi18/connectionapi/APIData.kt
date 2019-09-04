package com.github.shichi18.connectionapi

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIData {
    //http://zipcloud.ibsnet.co.jp/api/search?zipcode=1000001のドメイン名以下の部分
    //@Queryが?zipcode=ZipCideを生成ZipCodeは関数の引数

    @GET("api/search")
    fun apiDemo(@Query("zipcode") ZipCode: String): Call<ResponseModel>
}
