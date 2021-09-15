package dev.santieinaki.fagamosbarullo.app.module.main.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import dev.santieinaki.fagamosbarullo.R;
import dev.santieinaki.fagamosbarullo.app.module.BaseFragment;
import dev.santieinaki.fagamosbarullo.app.module.main.MainActivity;
import dev.santieinaki.fagamosbarullo.app.module.main.search.adapter.FilterAdapter;
import dev.santieinaki.fagamosbarullo.app.module.main.search.adapter.FilterClickListener;
import dev.santieinaki.fagamosbarullo.app.module.main.search.adapter.UserClickListener;
import dev.santieinaki.fagamosbarullo.app.module.main.search.presenter.SearchPresenter;
import dev.santieinaki.fagamosbarullo.app.module.main.search.presenter.SearchPresenterImp;
import dev.santieinaki.fagamosbarullo.app.module.main.search.presenter.SearchView;
import dev.santieinaki.fagamosbarullo.app.module.main.search.viewmodel.UserModel;
import dev.santieinaki.fagamosbarullo.app.module.main.search.adapter.UserItemAdapter;

public class SearchFragment extends BaseFragment implements SearchView, FilterClickListener {

    @BindView(R.id.filter_list)
    RecyclerView mFilterList;
    @BindView(R.id.rv_list)
    RecyclerView mUserList;
    @BindView(R.id.search_et)
    EditText mEditText;
    @BindView(R.id.empty_list_tv)
    TextView mTextView;
    @BindView(R.id.fav_bt)
    ImageView mFav;

    private SearchPresenter mPresenter;
    private FilterAdapter mFilterAdapter;
    private UserItemAdapter mAdapter;
    private List<String> mInstrumentos = new ArrayList<>();
    private boolean mFavorite = false;

    public SearchFragment() {

        mPresenter = new SearchPresenterImp(this);
        mAdapter = new UserItemAdapter(new ArrayList<>(), (UserClickListener) mPresenter);
        mFilterAdapter = new FilterAdapter(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    @Override
    public void fillList(List<UserModel> userModels) {

        // view is deleted
        if (mTextView == null) {
            return;
        }

        // no users matched
        if (userModels.isEmpty()) {
            mTextView.setVisibility(View.VISIBLE);
            mUserList.setVisibility(View.GONE);
            return;
        }

        mTextView.setVisibility(View.GONE);
        mUserList.setVisibility(View.VISIBLE);

        mAdapter.setItems(userModels);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void navigateToProfile() {

        // seria mejor una interfaz :^)
        ((MainActivity) getActivity()).selectProfile();
    }

    private void initView() {

        mEditText.addTextChangedListener(new MyTextWatcher());

        mUserList.setAdapter(mAdapter);
        mUserList.setLayoutManager(new LinearLayoutManager(getActivity()));

        mFilterList.setAdapter(mFilterAdapter);
        mFilterList.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mPresenter.searchUsers("", mInstrumentos, mFavorite);

        mFav.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mFavorite) {

                    mFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_border_24));
                    mFavorite = false;
                } else {

                    mFav.setImageDrawable(getResources().getDrawable(R.drawable.ic_baseline_favorite_24));
                    mFavorite = true;
                }

                mPresenter.searchUsers(mEditText.getText().toString(), mInstrumentos, mFavorite);
            }
        });
    }

    @Override
    public boolean onClick(String filter) {

        boolean ret = true;

        if (mInstrumentos.indexOf(filter) != -1) {

            ret = false;
            mInstrumentos.remove(filter);
        } else {
            mInstrumentos.add(filter);
        }

        mPresenter.searchUsers(mEditText.getText().toString(), mInstrumentos, mFavorite);
        return ret;
    }

    private class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s,
                                      int start,
                                      int count,
                                      int after) {

            return;
        }

        @Override
        public void onTextChanged(CharSequence s,
                                  int start,
                                  int before,
                                  int count) {

            String searchText = mEditText.getText().toString().trim();
            mPresenter.searchUsers(searchText, mInstrumentos, mFavorite);
        }

        @Override
        public void afterTextChanged(Editable s) {

            return;
        }
    }
}
