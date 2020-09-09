package com.example.nattramn.features.user.data

class UserRepository {

    companion object {
        private var myInstance: UserRepository? = null
        fun getInstance(): UserRepository {
            if (myInstance == null) {
                synchronized(this) {
                    myInstance = UserRepository()
                }
            }
            return myInstance!!
        }
    }

    fun loginUser() {
        
    }

}