

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.netlinkiron.admob.BannerCustomEventLoader;
import com.example.netlinkiron.admob.InterstialCustomEvent;
import com.example.netlinkiron.admob.NativeCustomEvent;
import com.example.netlinkiron.admob.RewardCustomEvent;
import com.google.android.gms.ads.mediation.Adapter;
import com.google.android.gms.ads.mediation.InitializationCompleteCallback;
import com.google.android.gms.ads.mediation.MediationAdLoadCallback;
import com.google.android.gms.ads.mediation.MediationBannerAd;
import com.google.android.gms.ads.mediation.MediationBannerAdCallback;
import com.google.android.gms.ads.mediation.MediationBannerAdConfiguration;
import com.google.android.gms.ads.mediation.MediationConfiguration;
import com.google.android.gms.ads.mediation.MediationInterstitialAd;
import com.google.android.gms.ads.mediation.MediationInterstitialAdCallback;
import com.google.android.gms.ads.mediation.MediationInterstitialAdConfiguration;
import com.google.android.gms.ads.mediation.MediationNativeAdCallback;
import com.google.android.gms.ads.mediation.MediationNativeAdConfiguration;
import com.google.android.gms.ads.mediation.MediationRewardedAd;
import com.google.android.gms.ads.mediation.MediationRewardedAdCallback;
import com.google.android.gms.ads.mediation.MediationRewardedAdConfiguration;
import com.google.android.gms.ads.mediation.UnifiedNativeAdMapper;
import com.google.android.gms.ads.mediation.VersionInfo;

import java.util.List;

public class AdNetworkCustomEvent extends Adapter {
    private BannerCustomEventLoader bannerLoader;
    private InterstialCustomEvent interstitialLoader;
    private NativeCustomEvent nativeLoader;
    private RewardCustomEvent rewardedLoader;
    


    @NonNull
    @Override
    public VersionInfo getSDKVersionInfo() {
        return new VersionInfo(0, 0, 0);
    }


    @NonNull
    @Override
    public VersionInfo getVersionInfo() {
        String versionString = String.valueOf(new VersionInfo(1, 2, 3));
        String[] splits = versionString.split("\\.");

        if (splits.length >= 4) {
            int major = Integer.parseInt(splits[0]);
            int minor = Integer.parseInt(splits[1]);
            int micro = Integer.parseInt(splits[2]) * 100 + Integer.parseInt(splits[3]);
            return new VersionInfo(major, minor, micro);
        }

        return new VersionInfo(0, 0, 0);
    }

    @Override
    public void initialize(Context context,
                           InitializationCompleteCallback initializationCompleteCallback,
                           List<MediationConfiguration> mediationConfigurations) {
        // This is where you will initialize the SDK that this custom
        // event is built for. Upon finishing the SDK initialization,
        // call the completion handler with success.
        initializationCompleteCallback.onInitializationSucceeded();
    }

    @Override
    public void loadBannerAd(
            @NonNull MediationBannerAdConfiguration adConfiguration,
            @NonNull MediationAdLoadCallback<MediationBannerAd, MediationBannerAdCallback> callback) {
        bannerLoader = new BannerCustomEventLoader(adConfiguration, callback);
        bannerLoader.loadAd();
    }
    @Override
    public void loadInterstitialAd(
            @NonNull MediationInterstitialAdConfiguration adConfiguration,
            @NonNull
            MediationAdLoadCallback<MediationInterstitialAd, MediationInterstitialAdCallback>
                    callback) {
        interstitialLoader = new InterstialCustomEvent(adConfiguration, callback);
        interstitialLoader.loadAd();
    }
    @Override
    public void loadNativeAd(
            @NonNull MediationNativeAdConfiguration adConfiguration,
            @NonNull MediationAdLoadCallback<UnifiedNativeAdMapper, MediationNativeAdCallback> callback) {
        nativeLoader = new NativeCustomEvent(adConfiguration, callback);
        nativeLoader.loadAd();
    }

    @Override
    public void loadRewardedAd(
            @NonNull MediationRewardedAdConfiguration mediationRewardedAdConfiguration,
            @NonNull
            MediationAdLoadCallback<MediationRewardedAd, MediationRewardedAdCallback>
                    mediationAdLoadCallback) {
        rewardedLoader =
                new RewardCustomEvent(
                        mediationRewardedAdConfiguration, mediationAdLoadCallback);
        rewardedLoader.loadAd();
    }
}
