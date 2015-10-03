package examples.android.com.socialatchangi;

import android.app.Application;
import android.os.Handler;

import com.firebase.client.Firebase;

/**
 * Created by madhur on 3/1/15.
 */
public class App extends Application {

    private static App Instance;
    public static volatile Handler applicationHandler = null;

    @Override
    public void onCreate() {
        super.onCreate();

        Instance=this;

        applicationHandler = new Handler(getInstance().getMainLooper());

        NativeLoader.initNativeLibs(App.getInstance());
        Firebase.setAndroidContext(this);

    }

    public static App getInstance()
    {
        return Instance;
    }
}
