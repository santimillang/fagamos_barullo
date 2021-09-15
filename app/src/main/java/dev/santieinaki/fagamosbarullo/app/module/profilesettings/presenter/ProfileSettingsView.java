package dev.santieinaki.fagamosbarullo.app.module.profilesettings.presenter;

import dev.santieinaki.fagamosbarullo.app.domain.user.User;

public interface ProfileSettingsView {

    public void setUserInfo(User user);

    public void showError();
}
