

import android.content.res.Resources
import android.view.View
import androidx.annotation.Keep
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.admanager.AdManagerAdRequest
import com.google.android.gms.ads.admanager.AdManagerAdView
import com.google.android.gms.ads.mediation.*

@Keep
class BannerCustomEventLoader
/** Constructor.  */(
    private val mediationBannerAdConfiguration: MediationBannerAdConfiguration,
    private val mediationAdLoadCallback: MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback>
) : AdListener(), MediationBannerAd {
    private var adManagerAdView: AdManagerAdView? = null
    fun loadAd() {
        val serverParameter = mediationBannerAdConfiguration.serverParameters.getString(
            MediationConfiguration.CUSTOM_EVENT_SERVER_PARAMETER_FIELD
        )
        val context = mediationBannerAdConfiguration.context
        adManagerAdView = AdManagerAdView(context)
        adManagerAdView!!.adUnitId = serverParameter!!
        val size = mediationBannerAdConfiguration.adSize
        val widthInPixels = size.getWidthInPixels(context)
        val heightInPixels = size.getHeightInPixels(context)
        val displayMetrics = Resources.getSystem().displayMetrics
        val widthInDp = Math.round(widthInPixels / displayMetrics.density)
        val heightInDp = Math.round(heightInPixels / displayMetrics.density)
        adManagerAdView!!.setAdSizes(AdSize(widthInDp, heightInDp))
        adManagerAdView!!.adListener = this
        val adRequest = AdManagerAdRequest.Builder().build()
        adManagerAdView!!.loadAd(adRequest)
    }

    override fun getView(): View {
        return adManagerAdView!!
    }
}