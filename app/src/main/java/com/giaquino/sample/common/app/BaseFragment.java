package com.giaquino.sample.common.app;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;

/**
 * @author Gian Darren Azriel Aquino.
 */
public class BaseFragment extends Fragment {

    private final static Handler MAIN_THREAD_HANDLER = new Handler(Looper.getMainLooper());

    protected void runOnUIThreadIfFragmentAlive(final Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper() && isFragmentAlive()) {
            runnable.run();
        }
        else {
            MAIN_THREAD_HANDLER.post(new Runnable() {
                @Override
                public void run() {
                    if (isFragmentAlive()) {
                        runnable.run();
                    }
                }
            });
        }
    }

    private boolean isFragmentAlive() {
        return getActivity() != null && isAdded() && !isDetached() && getView() != null && !isRemoving();
    }

}
