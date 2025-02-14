package com.seiko.imageloader

import androidx.compose.runtime.staticCompositionLocalOf
import com.seiko.imageloader.component.setupDefaultComponents
import okio.Path.Companion.toPath
import platform.Foundation.NSCachesDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

actual fun createImageLoaderProvidableCompositionLocal() = ImageLoaderProvidableCompositionLocal(
    delegate = staticCompositionLocalOf {
        ImageLoader.DefaultIOS
    },
)

val ImageLoader.Companion.DefaultIOS: ImageLoader
    get() = ImageLoader {
        components {
            setupDefaultComponents()
        }
        interceptor {
            defaultImageResultMemoryCache()
            memoryCacheConfig {
                maxSizeBytes(32 * 1024 * 1024) // 32MB
            }
            diskCacheConfig {
                directory(getCacheDir().toPath().resolve("image_cache"))
                maxSizeBytes(512L * 1024 * 1024) // 512MB
            }
        }
    }

private fun getCacheDir(): String {
    return NSSearchPathForDirectoriesInDomains(
        NSCachesDirectory,
        NSUserDomainMask,
        true,
    ).first() as String
}
