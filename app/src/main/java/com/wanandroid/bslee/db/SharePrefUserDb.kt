package com.wanandroid.bslee.db


class SharePrefUserDb {

    companion object {

        fun isLogin(): Boolean {
            return SharePrefDb.getBoolean(SharePrefDb.KEY_IS_LOGIN)
        }

        fun saveUserInfo(username: String, nickname: String) {
            SharePrefDb.put(SharePrefDb.KEY_IS_LOGIN, true)
            SharePrefDb.put(SharePrefDb.KEY_NICK_NAME, nickname)
            SharePrefDb.put(SharePrefDb.KEY_USER_NAME, username)
        }

    }

}