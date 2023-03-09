

import android.content.res.Resources
import android.view.View
import androidx.annotation.Keep
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.mediation.*

@Keep
class BannerCustomEventLoader
/** Constructor.  */(
    private val mediationBannerAdConfiguration: MediationBannerAdConfiguration,
    private val mediationAdLoadCallback: MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback>
) : AdListener(), MediationBannerAd {
    private var adView: AdView? = null
    fun loadAd() {
        val serverParameter = mediationBannerAdConfiguration.serverParameters.getString(
            MediationConfiguration.CUSTOM_EVENT_SERVER_PARAMETER_FIELD
        )
        val context = mediationBannerAdConfiguration.context
        adView = AdView(context)
        adView!!.adUnitId = serverParameter!!
        val size = mediationBannerAdConfiguration.adSize
        val widthInPixels = size.getWidthInPixels(context)
        val heightInPixels = size.getHeightInPixels(context)
        val displayMetrics = Resources.getSystem().displayMetrics
        val widthInDp = Math.round(widthInPixels / displayMetrics.density)
        val heightInDp = Math.round(heightInPixels / displayMetrics.density)
        adView!!.setAdSize(AdSize(widthInDp, heightInDp))
        adView!!.adListener = this
        val adRequest = AdRequest.Builder().build()
        adView!!.loadAd(adRequest)
    }

    override fun getView(): View {
        return adView!!
    }
}