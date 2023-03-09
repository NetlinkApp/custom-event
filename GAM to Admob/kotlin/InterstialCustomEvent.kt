

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback
import com.google.android.gms.ads.mediation.*

class InterstialCustomEvent
/** Constructor.  */(
    private val mediationInterstitialAdConfiguration: MediationInterstitialAdConfiguration,
    /** Callback that fires on loading success or failure.  */
    private val mediationAdLoadCallback: MediationAdLoadCallback<MediationInterstitialAd, MediationInterstitialAdCallback>
) : AdListener(), MediationInterstitialAd {
    private var adManagerInterstitialAd: AdManagerInterstitialAd? = null

    /** Callback for interstitial ad events.  */
    private val interstitialAdCallback: MediationInterstitialAdCallback? = null
    fun loadAd() {
        // All custom events have a server parameter named "parameter" that returns
        // back the parameter entered into the UI when defining the custom event.
        val serverParameter = mediationInterstitialAdConfiguration.serverParameters.getString(
            MediationConfiguration.CUSTOM_EVENT_SERVER_PARAMETER_FIELD
        )
        val adRequest = AdManagerAdRequest.Builder().build()
        AdManagerInterstitialAd.load(
            mediationInterstitialAdConfiguration.context, serverParameter!!, adRequest,
            object : AdManagerInterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: AdManagerInterstitialAd) {
                    adManagerInterstitialAd = interstitialAd
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    adManagerInterstitialAd = null
                }
            })
    }

    override fun showAd(context: Context) {
        adManagerInterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                adManagerInterstitialAd = null
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when ad fails to show.
                adManagerInterstitialAd = null
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
            }
        }
        adManagerInterstitialAd!!.show((context as Activity))
    }
}