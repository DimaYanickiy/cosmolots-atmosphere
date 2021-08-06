package cos.mol.ott;

import android.content.Context;

import com.facebook.FacebookSdk;
import com.onesignal.OneSignal;

public class Initialization extends Thread{

    private static final String ONE_SIGNAL_ID = "2a755a27-b6e4-4703-aa4a-dfdf9fee43ad";
    Context context;

    public Initialization(Context context){
        this.context = context;
    }

    @Override
    public void run() {
        OneSignal.initWithContext(context);
        OneSignal.setAppId(ONE_SIGNAL_ID);
        FacebookSdk.setAutoInitEnabled(true);
        FacebookSdk.fullyInitialize();
    }
}
