package com.vedidev.nativebridge;

import android.app.Activity;
import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

/**
 * @author vedi
 *         date 05/10/14
 */
public class BunchManager implements Bunch {
    private static BunchManager INSTANCE = null;
    private Context context;
    private WeakReference<Activity> activityRef = new WeakReference<>(null);

    public static BunchManager getInstance() {
        if (INSTANCE == null) {
            synchronized (BunchManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new BunchManager();
                }
            }
        }
        return INSTANCE;
    }

    private Set<Bunch> services = new HashSet<Bunch>();

    public void registerBunch(Bunch bunch) {
        bunch.setContext(getContext());
        services.add(bunch);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setActivity(Activity activity) {
        this.activityRef = new WeakReference<>(activity);
    }

    public Activity getActivity() {
        return activityRef.get();
    }
}
