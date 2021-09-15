package dev.santieinaki.fagamosbarullo.app.module.profilesettings.presenter;

import android.net.Uri;

import dev.santieinaki.fagamosbarullo.app.domain.user.User;

public interface ProfileSettingsPresenter {

    public void initFlow();

    public boolean instrumentoClicked(String instrumento);

    public void saveInfo(User user);

    public void changeDescripcion(String desciption);

    public void deletePhoto(User user);
}
