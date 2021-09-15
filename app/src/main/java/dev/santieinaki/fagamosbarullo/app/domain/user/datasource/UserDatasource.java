package dev.santieinaki.fagamosbarullo.app.domain.user.datasource;

import android.net.Uri;

import java.util.List;

import dev.santieinaki.fagamosbarullo.app.domain.user.User;

public interface UserDatasource {

    public boolean register(User user);

    public User getUserInfo(String id);

    public void saveUserInfo(User user);

    public User getUserInfoWithEmail(String email);

    public List<User> searchUsers(String name, List<String> instrumentos, boolean favs);

    public User follow(User user, String id);

    public User uploadPhoto(User user, Uri uri, String titulo);

    public User uploadMaqueta(User user, Uri uri, Uri image, String titulo);
}
