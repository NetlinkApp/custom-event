

import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.mediation.MediationAdLoadCallback;
import com.google.android.gms.ads.mediation.MediationConfiguration;
import com.google.android.gms.ads.mediation.MediationNativeAdCallback;
import com.google.android.gms.ads.mediation.MediationNativeAdConfiguration;
import com.google.android.gms.ads.mediation.UnifiedNativeAdMapper;
import com.google.android.gms.ads.nativead.NativeAd;

import java.lang.annotation.Native;

public class NativeCustomEvent extends AdListener implements NativeAd.OnNativeAdLoadedListener {

    private final MediationNativeAdConfiguration mediationNativeAdConfiguration;

    NativeAd nativeAd;
    private final MediationAdLoadCallback<UnifiedNativeAdMapper, MediationNativeAdCallback>
            mediationAdLoadCallback;

    /** Callback for native ad events. */
    private MediationNativeAdCallback nativeAdCallback;

    /** Constructor */
    public NativeCustomEvent(
            @NonNull MediationNativeAdConfiguration mediationNativeAdConfiguration,
            @NonNull MediationAdLoadCallback<UnifiedNativeAdMapper, MediationNativeAdCallback>
                    mediationAdLoadCallback) {
        this.mediationNativeAdConfiguration = mediationNativeAdConfiguration;
        this.mediationAdLoadCallback = mediationAdLoadCallback;
    }
    public void loadAd() {
        // Create one of the Sample SDK's ad loaders to request ads.



        // All custom events have a server parameter named "parameter" that returns
        // back the parameter entered into the UI when defining the custom event.
        String serverParameter = mediationNativeAdConfiguration
                .getServerParameters()
                .getString(MediationConfiguration
                        .CUSTOM_EVENT_SERVER_PARAMETER_FIELD);

        AdLoader.Builder loader =
                new AdLoader.Builder(mediationNativeAdConfiguration.getContext(), serverParameter);
        loader.forNativeAd(this);
        loader.withAdListener(this);
        loader.build().loadAd(new AdManagerAdRequest.Builder().build());
    }

    @Override
    public void onNativeAdLoaded(@NonNull NativeAd na) {
        nativeAd = na;
    }

    @Override
    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
        super.onAdFailedToLoad(loadAdError);
        nativeAd = null;
    }
}
