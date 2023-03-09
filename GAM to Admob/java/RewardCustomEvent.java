

import android.app.Activity;
import android.content.Context;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.admanager.AdManagerAdRequest;
import com.google.android.gms.ads.mediation.MediationAdLoadCallback;
import com.google.android.gms.ads.mediation.MediationConfiguration;
import com.google.android.gms.ads.mediation.MediationRewardedAd;
import com.google.android.gms.ads.mediation.MediationRewardedAdCallback;
import com.google.android.gms.ads.mediation.MediationRewardedAdConfiguration;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;

public class RewardCustomEvent extends AdListener implements MediationRewardedAd {
    private RewardedAd rewardedAd;

    private final MediationRewardedAdConfiguration mediationRewardedAdConfiguration;


    private final MediationAdLoadCallback<MediationRewardedAd, MediationRewardedAdCallback>
            mediationAdLoadCallback;
    private MediationRewardedAdCallback rewardedAdCallback;

    /** Constructor. */
    public RewardCustomEvent(
            @NonNull MediationRewardedAdConfiguration mediationRewardedAdConfiguration,
            @NonNull MediationAdLoadCallback<MediationRewardedAd, MediationRewardedAdCallback>
                    mediationAdLoadCallback) {
        this.mediationRewardedAdConfiguration = mediationRewardedAdConfiguration;
        this.mediationAdLoadCallback = mediationAdLoadCallback;
    }
    public void loadAd() {
        // All custom events have a server parameter named "parameter" that returns
        // back the parameter entered into the AdMob UI when defining the custom event.

        String serverParameter = mediationRewardedAdConfiguration
                .getServerParameters()
                .getString(MediationConfiguration
                        .CUSTOM_EVENT_SERVER_PARAMETER_FIELD);

        AdManagerAdRequest adRequest = new AdManagerAdRequest.Builder().build();
        RewardedAd.load(mediationRewardedAdConfiguration.getContext(), serverParameter,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        rewardedAd = null;
                    }
                    @Override
                    public void onAdLoaded(@NonNull RewardedAd ad) {
                        rewardedAd = ad;
                    }
                });
    }
    @Override
    public void showAd(@NonNull Context context) {
        if (rewardedAd != null) {
            rewardedAd.show((Activity) mediationRewardedAdConfiguration.getContext(), new OnUserEarnedRewardListener() {
                @Override
                public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                }
            });
        }
    }
}
