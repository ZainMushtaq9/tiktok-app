import { env } from '../config/env';

export const adsConfig = {
  adsenseClientId: env.ADSENSE_CLIENT_ID,

  // Remote config fallbacks
  websiteAdsEnabled: true,
  mobileAdsEnabled: true,
  bannerAdUnit: '',
  interstitialAdUnit: '',
  rewardedAdUnit: '',
  nativeAdUnit: '',
  appOpenAdUnit: '',
  adFrequencySeconds: 120,
  interstitialEveryNDownloads: 3,
};
