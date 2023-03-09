

import android.content.Context
import com.example.netlinkiron.kotlin.admanager.BannerCustomEventLoader
import com.example.netlinkiron.kotlin.admanager.InterstialCustomEvent
import com.example.netlinkiron.kotlin.admanager.NativeCustomEvent
import com.example.netlinkiron.kotlin.admanager.RewardCustomEvent
import com.google.android.gms.ads.mediation.*

class AdNetworkCustomEvent : Adapter() {
    private var bannerLoader: BannerCustomEventLoader? = null
    private var interstitialLoader: InterstialCustomEvent? = null
    private var nativeLoader: NativeCustomEvent? = null
    private var rewardedLoader: RewardCustomEvent? = null
    override fun getSDKVersionInfo(): VersionInfo {
        return VersionInfo(0, 0, 0)
    }

    override fun getVersionInfo(): VersionInfo {
        val versionString = VersionInfo(1, 2, 3).toString()
        val splits =
            versionString.split("\\.".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        if (splits.size >= 4) {
            val major = splits[0].toInt()
            val minor = splits[1].toInt()
            val micro = splits[2].toInt() * 100 + splits[3].toInt()
            return VersionInfo(major, minor, micro)
        }
        return VersionInfo(0, 0, 0)
    }

    override fun initialize(
        context: Context,
        initializationCompleteCallback: InitializationCompleteCallback,
        mediationConfigurations: List<MediationConfiguration>
    ) {
        // This is where you will initialize the SDK that this custom
        // event is built for. Upon finishing the SDK initialization,
        // call the completion handler with success.
        initializationCompleteCallback.onInitializationSucceeded()
    }

    override fun loadBannerAd(
        adConfiguration: MediationBannerAdConfiguration,
        callback: MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback>
    ) {
        bannerLoader = BannerCustomEventLoader(adConfiguration, callback)
        bannerLoader!!.loadAd()
    }

    override fun loadInterstitialAd(
        adConfiguration: MediationInterstitialAdConfiguration,
        callback: MediationAdLoadCallback<MediationInterstitialAd, MediationInterstitialAdCallback>
    ) {
        interstitialLoader = InterstialCustomEvent(adConfiguration, callback)
        interstitialLoader!!.loadAd()
    }

    override fun loadNativeAd(
        adConfiguration: MediationNativeAdConfiguration,
        callback: MediationAdLoadCallback<UnifiedNativeAdMapper, MediationNativeAdCallback>
    ) {
        nativeLoader = NativeCustomEvent(adConfiguration, callback)
        nativeLoader!!.loadAd()
    }

    override fun loadRewardedAd(
        mediationRewardedAdConfiguration: MediationRewardedAdConfiguration,
        mediationAdLoadCallback: MediationAdLoadCallback<MediationRewardedAd, MediationRewardedAdCallback>
    ) {
        rewardedLoader = RewardCustomEvent(
            mediationRewardedAdConfiguration, mediationAdLoadCallback
        )
        rewardedLoader!!.loadAd()
    }
}