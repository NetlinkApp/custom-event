
import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAd;
import com.google.android.gms.ads.admanager.AdManagerInterstitialAdLoadCallback;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.mediation.MediationAdLoadCallback;
import com.google.android.gms.ads.mediation.MediationConfiguration;
import com.google.android.gms.ads.mediation.MediationInterstitialAd;
import com.google.android.gms.ads.mediation.MediationInterstitialAdCallback;
import com.google.android.gms.ads.mediation.MediationInterstitialAdConfiguration;

public class InterstialCustomEvent extends AdListener implements MediationInterstitialAd {
    private InterstitialAd minterstitialAd;

    private final MediationInterstitialAdConfiguration mediationInterstitialAdConfiguration;

    /** Callback for interstitial ad events. */
    private MediationInterstitialAdCallback interstitialAdCallback;

    /** Callback that fires on loading success or failure. */
    private final MediationAdLoadCallback<MediationInterstitialAd, MediationInterstitialAdCallback>
            mediationAdLoadCallback;

    /** Constructor. */
    public InterstialCustomEvent(
            @NonNull MediationInterstitialAdConfiguration mediationInterstitialAdConfiguration,
            @NonNull MediationAdLoadCallback<MediationInterstitialAd, MediationInterstitialAdCallback>
                    mediationAdLoadCallback) {
        this.mediationInterstitialAdConfiguration = mediationInterstitialAdConfiguration;
        this.mediationAdLoadCallback = mediationAdLoadCallback;
    }
    public void loadAd() {
        // All custom events have a server parameter named "parameter" that returns
        // back the parameter entered into the UI when defining the custom event.

        String serverParameter = mediationInterstitialAdConfiguration.getServerParameters().getString(
                MediationConfiguration.CUSTOM_EVENT_SERVER_PARAMETER_FIELD);


        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(mediationInterstitialAdConfiguration.getContext(),serverParameter, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        minterstitialAd = interstitialAd;
                    }
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        minterstitialAd = null;
                    }
                });
    }
    @Override
    public void showAd(@NonNull Context context) {
        minterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.

                minterstitialAd = null;
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show.

                minterstitialAd = null;
            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when ad is shown.

            }
        });
        minterstitialAd.show((Activity) context);
    }
}
