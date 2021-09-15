package dev.santieinaki.fagamosbarullo.app.module.main.search.presenter;


import java.util.List;

public interface SearchPresenter {

    public void searchUsers(String text, List<String> mInstrumentos, boolean favorites);

    public void destroy();
}
