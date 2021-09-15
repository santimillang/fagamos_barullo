package dev.santieinaki.fagamosbarullo.app.module.addphoto.presenter;

import android.net.Uri;

import dev.santieinaki.fagamosbarullo.app.domain.user.User;

public interface AddPhotoPresenter {

    public void uploadPhoto(User user, Uri imageUri, String titulo);
}
