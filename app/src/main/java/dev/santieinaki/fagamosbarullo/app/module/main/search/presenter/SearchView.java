package dev.santieinaki.fagamosbarullo.app.module.main.search.presenter;

import java.util.List;

import dev.santieinaki.fagamosbarullo.app.module.main.search.viewmodel.UserModel;

public interface SearchView {

    public void fillList(List<UserModel> userModels);

    public void navigateToProfile();
}
