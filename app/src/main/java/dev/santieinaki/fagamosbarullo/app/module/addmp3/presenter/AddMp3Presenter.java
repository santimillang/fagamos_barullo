package dev.santieinaki.fagamosbarullo.app.module.addmp3.presenter;

import android.net.Uri;

import dev.santieinaki.fagamosbarullo.app.domain.user.User;

public interface AddMp3Presenter {

    public void uploadMaqueta(User user, Uri maqueta, Uri imagen, String titulo);
}
