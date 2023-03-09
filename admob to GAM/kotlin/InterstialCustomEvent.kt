

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.mediation.*

class InterstialCustomEvent
/** Constructor.  */(
    private val mediationInterstitialAdConfiguration: MediationInterstitialAdConfiguration,
    /** Callback that fires on loading success or failure.  */
    private val mediationAdLoadCallback: MediationAdLoadCallback<MediationInterstitialAd, MediationInterstitialAdCallback>
) : AdListener(), MediationInterstitialAd {
    private var minterstitialAd: InterstitialAd? = null

    /** Callback for interstitial ad events.  */
    private val interstitialAdCallback: MediationInterstitialAdCallback? = null
    fun loadAd() {
        // All custom events have a server parameter named "parameter" that returns
        // back the parameter entered into the UI when defining the custom event.
        val serverParameter = mediationInterstitialAdConfiguration.serverParameters.getString(
            MediationConfiguration.CUSTOM_EVENT_SERVER_PARAMETER_FIELD
        )
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(
            mediationInterstitialAdConfiguration.context, serverParameter!!, adRequest,
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    minterstitialAd = interstitialAd
                }

                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    minterstitialAd = null
                }
            })
    }

    override fun showAd(context: Context) {
        minterstitialAd!!.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                minterstitialAd = null
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError) {
                // Called when ad fails to show.
                minterstitialAd = null
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
            }
        }
        minterstitialAd!!.show((context as Activity))
    }
}