package dev.santieinaki.fagamosbarullo.app.module.main.profile.presenter;

import dev.santieinaki.fagamosbarullo.app.domain.user.User;

public interface ProfileView {

    public void setUserInfo(User user);

    public void showError();

    public void updateUser(User user);
}
