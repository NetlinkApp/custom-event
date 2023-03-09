

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.mediation.*
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

class RewardCustomEvent
/** Constructor.  */(
    private val mediationRewardedAdConfiguration: MediationRewardedAdConfiguration,
    private val mediationAdLoadCallback: MediationAdLoadCallback<MediationRewardedAd, MediationRewardedAdCallback>
) : AdListener(), MediationRewardedAd {
    private var rewardedAd: RewardedAd? = null
    private val rewardedAdCallback: MediationRewardedAdCallback? = null
    fun loadAd() {
        // All custom events have a server parameter named "parameter" that returns
        // back the parameter entered into the AdMob UI when defining the custom event.
        val serverParameter = mediationRewardedAdConfiguration
            .serverParameters
            .getString(MediationConfiguration.CUSTOM_EVENT_SERVER_PARAMETER_FIELD)
        val adRequest = AdManagerAdRequest.Builder().build()
        RewardedAd.load(
            mediationRewardedAdConfiguration.context, serverParameter!!,
            adRequest, object : RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                    rewardedAd = null
                }

                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                }
            })
    }

    override fun showAd(context: Context) {
        if (rewardedAd != null) {
            rewardedAd!!.show((mediationRewardedAdConfiguration.context as Activity)) { }
        }
    }
}