package dev.santieinaki.fagamosbarullo.app.module.profile;

import dev.santieinaki.fagamosbarullo.R;
import dev.santieinaki.fagamosbarullo.app.domain.user.User;
import dev.santieinaki.fagamosbarullo.app.module.BaseActivity;
import dev.santieinaki.fagamosbarullo.app.module.main.profile.ProfileFragment;

import android.os.Bundle;

public class Profile extends BaseActivity {

    public static final String USER_INTENT = "USER_PROFILE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // no es muy eficiente :( => mejor Parcelable
        User user = (User) getIntent().getSerializableExtra(USER_INTENT);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ProfileFragment(user)).commit();
    }
}