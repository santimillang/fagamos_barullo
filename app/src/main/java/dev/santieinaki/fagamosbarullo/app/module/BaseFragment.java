package dev.santieinaki.fagamosbarullo.app.module;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BaseFragment extends Fragment {

    private Unbinder mUnbinder;

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void onDestroy() {

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }

        super.onDestroy();
    }
}
