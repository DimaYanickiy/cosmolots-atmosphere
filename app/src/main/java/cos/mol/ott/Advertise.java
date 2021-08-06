package cos.mol.ott;

import android.content.Context;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import java.io.IOException;

public class Advertise extends Thread{
    Context context;
    String ad_id = "";
    FastSaver fs;

    public Advertise(Context context){
        this.context = context;
        fs = new FastSaver(context);
    }

    @Override
    public void run() {
        try {
            AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context.getApplicationContext());
            ad_id = adInfo != null ? adInfo.getId() : null;
            fs.setAd(ad_id);
        } catch (IOException | GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException exception) {
        }
    }
}
