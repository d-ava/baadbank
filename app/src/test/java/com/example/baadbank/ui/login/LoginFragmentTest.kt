package com.example.baadbank.ui.login

import com.example.baadbank.LoginUtil
import com.google.common.truth.Truth.assertThat

import org.junit.Test

class LoginFragmentTest(){

    @Test
    fun empty_email_returns_false(){
        val result = LoginUtil.validateLogInInput(
            "", "1234"
        )

        assertThat(result).isFalse()
    }

    @Test
    fun not_registered_user_returns_false(){


        val result = LoginUtil.validateLogInInput(
           "user@gmail.com",
           "1234"
        )
    assertThat(result).isFalse()
    }


}