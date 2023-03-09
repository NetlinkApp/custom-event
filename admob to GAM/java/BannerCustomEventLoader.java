

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.Keep;
import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerAdView;
import com.google.android.gms.ads.mediation.MediationAdLoadCallback;
import com.google.android.gms.ads.mediation.MediationBannerAd;
import com.google.android.gms.ads.mediation.MediationBannerAdCallback;
import com.google.android.gms.ads.mediation.MediationBannerAdConfiguration;
import com.google.android.gms.ads.mediation.MediationConfiguration;


@Keep

public class BannerCustomEventLoader extends AdListener implements MediationBannerAd {

    private AdView adView;
    private final MediationBannerAdConfiguration mediationBannerAdConfiguration;
    private final MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback>
            mediationAdLoadCallback;
    /** Constructor. */
    public BannerCustomEventLoader(
            @NonNull MediationBannerAdConfiguration mediationBannerAdConfiguration,
            @NonNull MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback>
                    mediationAdLoadCallback) {
        this.mediationBannerAdConfiguration = mediationBannerAdConfiguration;
        this.mediationAdLoadCallback = mediationAdLoadCallback;
    }
    public void loadAd() {
        String serverParameter =
                mediationBannerAdConfiguration.getServerParameters().getString(
                        MediationConfiguration.CUSTOM_EVENT_SERVER_PARAMETER_FIELD);


        Context context = mediationBannerAdConfiguration.getContext();
        adView = new AdView(context);
        adView.setAdUnitId(serverParameter);
        AdSize size = mediationBannerAdConfiguration.getAdSize();
        int widthInPixels = size.getWidthInPixels(context);
        int heightInPixels = size.getHeightInPixels(context);
        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        int widthInDp = Math.round(widthInPixels / displayMetrics.density);
        int heightInDp = Math.round(heightInPixels / displayMetrics.density);
        adView.setAdSize(new AdSize(widthInDp, heightInDp));
        adView.setAdListener(this);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
    @NonNull
    @Override
    public View getView() {
        return adView;
    }
}
