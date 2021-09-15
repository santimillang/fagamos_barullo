package dev.santieinaki.fagamosbarullo.app.module.addphoto.presenter;

import dev.santieinaki.fagamosbarullo.app.domain.user.User;

public interface AddPhotoView {

    public void showError();

    public void blockView();

    public void unblockView();

    public void closeView(User user);
}
