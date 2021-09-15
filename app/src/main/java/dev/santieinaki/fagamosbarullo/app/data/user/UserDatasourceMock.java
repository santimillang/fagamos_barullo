package dev.santieinaki.fagamosbarullo.app.data.user;

import android.net.Uri;

import java.util.List;

import dev.santieinaki.fagamosbarullo.app.domain.user.User;
import dev.santieinaki.fagamosbarullo.app.domain.user.datasource.UserDatasource;

public class UserDatasourceMock implements UserDatasource {

    @Override
    public boolean register(User user) {

        return false;
    }

    @Override
    public User getUserInfo(String id) {

        return null;
    }

    @Override
    public void saveUserInfo(User user) {

        return;
    }

    @Override
    public User getUserInfoWithEmail(String email) {

        return null;
    }

    @Override
    public List<User> searchUsers(String name, List<String> instrumentos, boolean favs) {

        return null;
    }

    @Override
    public User follow(User user,
                          String id) {

        return null;
    }

    @Override
    public User uploadPhoto(User user,
                            Uri uri,
                            String titulo) {

        return null;
    }

    @Override
    public User uploadMaqueta(User user,
                              Uri uri,
                              Uri image,
                              String titulo) {

        return null;
    }

}
