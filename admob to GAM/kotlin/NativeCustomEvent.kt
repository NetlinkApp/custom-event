

import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.mediation.*
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAd.OnNativeAdLoadedListener

class NativeCustomEvent
/** Constructor  */(
    private val mediationNativeAdConfiguration: MediationNativeAdConfiguration,
    private val mediationAdLoadCallback: MediationAdLoadCallback<UnifiedNativeAdMapper, MediationNativeAdCallback>
) : AdListener(), OnNativeAdLoadedListener {
    var nativeAd: NativeAd? = null
    fun loadAd() {
        // Create one of the Sample SDK's ad loaders to request ads.


        // All custom events have a server parameter named "parameter" that returns
        // back the parameter entered into the UI when defining the custom event.
        val serverParameter = mediationNativeAdConfiguration
            .serverParameters
            .getString(MediationConfiguration.CUSTOM_EVENT_SERVER_PARAMETER_FIELD)
        val loader = AdLoader.Builder(mediationNativeAdConfiguration.context, serverParameter!!)
        loader.forNativeAd(this)
        loader.withAdListener(this)
        loader.build().loadAd(AdManagerAdRequest.Builder().build())
    }

    override fun onNativeAdLoaded(na: NativeAd) {
        nativeAd = na
    }

    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
        super.onAdFailedToLoad(loadAdError)
        nativeAd = null
    }
}