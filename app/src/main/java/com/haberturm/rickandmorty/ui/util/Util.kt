package com.haberturm.rickandmorty.ui.util

object Util {
    fun endingSelection(number: Int): String{
        return if(number % 10 in (0..1)){
            "эпизоде"
        }else{
            "эпизодах"
        }
    }
}