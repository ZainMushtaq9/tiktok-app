package com.example.config

data class AdsConfig(
    val websiteAdsEnabled: Boolean = true,
    val mobileAdsEnabled: Boolean = true,
    val bannerAdUnit: String = "ca-app-pub-3940256099942544/6300978111", // Test IDs
    val interstitialAdUnit: String = "ca-app-pub-3940256099942544/1033173712",
    val interstitialEveryNDownloads: Int = 3
)

class RemoteConfigManager {
    // Stub for Firebase Remote Config
    // In production: Firebase.remoteConfig.fetchAndActivate()
    var currentConfig: AdsConfig = AdsConfig()
        private set
        
    fun fetchAdConfig() {
        // Here we would sync with:
        // ads_config: { website_ads_enabled: true, ... }
        currentConfig = AdsConfig(
            mobileAdsEnabled = true,
            interstitialEveryNDownloads = 3
        )
    }
}
