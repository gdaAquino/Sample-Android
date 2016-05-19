package com.giaquino.sample.common.app;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Gian Darren Azriel Aquino.
 */
public abstract class BaseFragment extends Fragment {

    private final static Handler MAIN_THREAD_HANDLER = new Handler(Looper.getMainLooper());

    private Unbinder unbinder;

    public abstract void initialize();

    protected void runOnUIThreadIfFragmentAlive(final Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper() && isFragmentAlive()) {
            runnable.run();
        } else {
            MAIN_THREAD_HANDLER.post(new Runnable() {
                @Override public void run() {
                    if (isFragmentAlive()) {
                        runnable.run();
                    }
                }
            });
        }
    }

    private boolean isFragmentAlive() {
        return getActivity() != null
            && isAdded()
            && !isDetached()
            && getView() != null
            && !isRemoving();
    }

    @Override public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        initialize();
    }

    @Override public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
