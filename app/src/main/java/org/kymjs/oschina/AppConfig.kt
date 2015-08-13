package org.kymjs.oschina

import org.kymjs.kjframe.ui.KJActivityStack
import org.kymjs.oschina.bean.User
import org.kymjs.oschina.utils.CacheUtil
import java.io.File
import java.io.IOException

/**
 * 应用配置器

 * @author kymjs (http://www.kymjs.com/) on 8/11/15.
 */
public object AppConfig {
    private var user: User? = null

    public fun isLogin(): Boolean {
        if (user == null) {
            user = CacheUtil.read(getLoginCacheFile()) as User?
        }
        return user?.isLogin() ?: false
    }

    public fun getUser(): User {
        if (user == null) {
            user = CacheUtil.read(getLoginCacheFile()) as User?
        }
        return user as User
    }

    public fun save(user: User) {
        CacheUtil.save(getLoginCacheFile(), user)
    }

    public fun getLoginCacheFile(): File {
        val cacheDir = KJActivityStack.create().topActivity().getCacheDir()
        val cacheFile = File("$cacheDir/cookie")
        if (!cacheFile.exists()) {
            try {
                cacheFile.createNewFile()
            } catch (e: IOException) {
            }
        }
        return cacheFile
    }
}
