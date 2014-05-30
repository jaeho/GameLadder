package so.party.ladders.util;

import android.util.Log;

import so.party.ladders.BuildConfig;

/**
 * Created by jaehochoe on 2014. 3. 15..
 */
public class Logger {

    private boolean ON = BuildConfig.DEBUG;

    public static void x(Object o) {
        Log.e("Ladders", o.toString());
    }
}
