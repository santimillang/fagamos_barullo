package dev.santieinaki.fagamosbarullo.app.module.main.profile.presenter;

import dev.santieinaki.fagamosbarullo.app.domain.user.User;

public interface ProfilePresenter {

    public void initFlow(User user);

    public void follow(User user);

    public void destroy();
}
