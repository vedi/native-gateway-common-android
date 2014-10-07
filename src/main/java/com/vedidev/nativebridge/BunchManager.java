package com.vedidev.nativebridge;

import android.content.Context;

import java.util.HashSet;
import java.util.Set;

/**
 * @author vedi
 *         date 05/10/14
 */
public class BunchManager implements Bunch {
    private static BunchManager INSTANCE = null;
    private Context context;

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
}
