package com.dd2d.voca_block.util

import android.util.Log

//fun List<*>.log(tag: String = ""): List<*>{
//    Log.d("LOG_CHECK", "${this.javaClass.name} :: $tag -> $this \n")
//    return this
//}
//
//fun <T> T.log(tag: String = ""): T{
//    Log.d("LOG_CHECK", " :: $tag -> $this \n")
//    return this
//}

fun log(tag: String){
    Log.d("LOG_CHECK", " :: log() -> $tag")
}

fun ll(t: Any, msg: String = ""){
    Log.d("LOG_CHECK", " :: ll() ->$msg:  $t")
}


