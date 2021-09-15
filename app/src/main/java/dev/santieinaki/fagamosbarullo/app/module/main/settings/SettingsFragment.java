package dev.santieinaki.fagamosbarullo.app.module.main.settings;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import butterknife.BindView;
import dev.santieinaki.fagamosbarullo.R;
import dev.santieinaki.fagamosbarullo.app.module.accountsettings.AccountSettings;
import dev.santieinaki.fagamosbarullo.app.module.BaseFragment;
import dev.santieinaki.fagamosbarullo.app.module.licenses.Licenses;
import dev.santieinaki.fagamosbarullo.app.module.main.settings.presenter.SettingsPresenter;
import dev.santieinaki.fagamosbarullo.app.module.main.settings.presenter.SettingsPresenterImp;
import dev.santieinaki.fagamosbarullo.app.module.main.settings.presenter.SettingsView;
import dev.santieinaki.fagamosbarullo.app.module.profilesettings.ProfileSettings;
import dev.santieinaki.fagamosbarullo.app.module.register.Register;

public class SettingsFragment extends BaseFragment implements SettingsView {

    @BindView(R.id.bt_logout)
    Button mLogout;
    @BindView(R.id.tv_perfil)
    TextView mProfile;
    @BindView(R.id.tv_cuenta)
    TextView mCuenta;
    @BindView(R.id.licenses)
    TextView mLicenzas;

    private SettingsPresenter mPresenter;

    public SettingsFragment() {

        mPresenter = new SettingsPresenterImp(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        mPresenter.initFlow();
        mLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mPresenter.logoutClicked();
            }
        });
        mProfile.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mPresenter.profileClicked();
            }
        });
        mCuenta.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mPresenter.cuentaClicked();
            }
        });
        mLicenzas.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                mPresenter.licenzasClicked();
            }
        });
    }

    @Override
    public void logout() {

        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getActivity(), Register.class));
        getActivity().finish();
    }

    @Override
    public void navigateToProfileSettings() {

        Intent intent = new Intent(getContext(), ProfileSettings.class);
        startActivity(intent);
    }

    @Override
    public void navigateToAccountSettings() {

        Intent intent = new Intent(getContext(), AccountSettings.class);
        startActivity(intent);
    }

    @Override
    public void navigateToLicenses() {

        Intent intent = new Intent(getContext(), Licenses.class);
        startActivity(intent);
    }
}
