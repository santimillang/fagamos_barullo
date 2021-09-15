package dev.santieinaki.fagamosbarullo.app.module.addmp3.presenter;

import dev.santieinaki.fagamosbarullo.app.domain.user.User;

public interface AddMp3View {

    public void showError();

    public void blockView();

    public void unblockView();

    public void closeView(User user);
}
