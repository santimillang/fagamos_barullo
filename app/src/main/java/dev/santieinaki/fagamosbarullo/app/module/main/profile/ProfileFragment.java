package dev.santieinaki.fagamosbarullo.app.module.main.profile;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import dev.santieinaki.fagamosbarullo.R;
import dev.santieinaki.fagamosbarullo.app.domain.user.User;
import dev.santieinaki.fagamosbarullo.app.module.BaseFragment;
import dev.santieinaki.fagamosbarullo.app.module.main.profile.adapter.PhotosAdapter;
import dev.santieinaki.fagamosbarullo.app.module.main.profile.presenter.ProfilePresenter;
import dev.santieinaki.fagamosbarullo.app.module.main.profile.presenter.ProfilePresenterImp;
import dev.santieinaki.fagamosbarullo.app.module.main.profile.presenter.ProfileView;
import dev.santieinaki.fagamosbarullo.app.util.CircleTransform;
import dev.santieinaki.fagamosbarullo.app.util.MaskTransformation;

public class ProfileFragment extends BaseFragment implements ProfileView {

    @BindView(R.id.yourName)
    TextView fullName;
    @BindView(R.id.profile_image)
    ImageView profileImage;
    @BindView(R.id.profile_description)
    TextView profileDescription;
    @BindView(R.id.textView2)
    TextView mTitle;
    @BindView(R.id.bt_follow)
    TextView mFollow;
    @BindView(R.id.followers)
    TextView mFollowers;
    @BindView(R.id.following)
    TextView mFollowing;
    @BindView(R.id.voz)
    TextView mVoz;
    @BindView(R.id.batera)
    TextView mBatera;
    @BindView(R.id.baixo)
    TextView mBaixo;
    @BindView(R.id.guitarra)
    TextView mGuitarra;
    @BindView(R.id.teclado)
    TextView mTeclado;
    @BindView(R.id.trompeta)
    TextView mTrompeta;
    @BindView(R.id.violin)
    TextView mViolin;
    @BindView(R.id.saxofon)
    TextView mSaxofon;
    @BindView(R.id.outro)
    TextView mOutro;
    @BindView(R.id.ningun)
    TextView mNingun;
    @BindView(R.id.fotos_rv)
    RecyclerView mFotos;
    @BindView(R.id.sen_fotos)
    TextView mSenFotos;
    @BindView(R.id.sen_maquetas)
    TextView mSenMaquetas;
    @BindView(R.id.l_maqueta1)
    LinearLayout mMaqueta1;
    @BindView(R.id.l_maqueta2)
    LinearLayout mMaqueta2;
    @BindView(R.id.maqueta_1)
    ImageView mImageMaqueta1;
    @BindView(R.id.maqueta_2)
    ImageView mImageMaqueta2;
    @BindView(R.id.titulo_1)
    TextView mTitulo1;
    @BindView(R.id.titulo_2)
    TextView mTitulo2;
    @BindView(R.id.play_bt_1)
    ImageButton mPlay1;
    @BindView(R.id.play_bt_2)
    ImageButton mPlay2;

    private int mPlaying;
    private User mUser;
    private MediaPlayer mPlayer;
    private PhotosAdapter mAdapter = new PhotosAdapter();
    private Map<String, TextView> mMap;
    private ProfilePresenter mPresenter;

    public ProfileFragment() {

        mPresenter = new ProfilePresenterImp(this);
    }

    public ProfileFragment(User user) {

        this();
        this.mUser = user;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onDestroy() {

        mPresenter.destroy();
        mPlayer.stop();
        mPlayer.release();

        super.onDestroy();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        mPlayer = new MediaPlayer();

        mFotos.setAdapter(mAdapter);
        mFotos.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        mMap = new HashMap<>();
        mMap.put("Voz", mVoz);
        mMap.put("Batera", mBatera);
        mMap.put("Baixo", mBaixo);
        mMap.put("Guitarra", mGuitarra);
        mMap.put("Teclado", mTeclado);
        mMap.put("Trompeta", mTrompeta);
        mMap.put("Violín", mViolin);
        mMap.put("Saxofón", mSaxofon);
        mMap.put("Outro", mOutro);
        mMap.put("Ningún", mNingun);

        mPresenter.initFlow(mUser);

        // set the view so that we are not the owner
        if (mUser != null) {
            mTitle.setText("Estas a ver o perfil de");
        } else {
            mFollow.setVisibility(View.GONE);
        }

        mVoz.setVisibility(View.GONE);
        mBatera.setVisibility(View.GONE);
        mBaixo.setVisibility(View.GONE);
        mGuitarra.setVisibility(View.GONE);
        mTeclado.setVisibility(View.GONE);
        mTrompeta.setVisibility(View.GONE);
        mViolin.setVisibility(View.GONE);
        mSaxofon.setVisibility(View.GONE);
        mOutro.setVisibility(View.GONE);

        mFollow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mUser.getFollowers() == null) {
                    return;
                }
                mPresenter.follow(mUser);
            }
        });
    }

    @Override
    public void updateUser(User user) {

        // view is deleted
        if (fullName == null) {
            return;
        }

        mUser = user;
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (user.getFollowers().indexOf(firebaseUser.getUid()) != -1) {

            mFollow.setText("XA NON");
            mFollow.setBackgroundResource(R.drawable.textview_bg);
        } else {

            mFollow.setText("GÚSTAME");
            mFollow.setBackgroundResource(R.drawable.edittext_bg);
        }

        mFollowers.setText(String.valueOf(user.getFollowers().size()));
        mFollowing.setText(String.valueOf(user.getFollowing().size()));
    }

    @Override
    public void setUserInfo(User user) {

        // view is deleted
        if (fullName == null) {
            return;
        }
        updateUser(user);

        List<String> instrumentos = user.getInstrumento();
        if (!instrumentos.isEmpty()) {

            mNingun.setVisibility(View.GONE);
            for (String instrumento : instrumentos) {

                mMap.get(instrumento).setVisibility(View.VISIBLE);
            }
        }

        String description = user.getDescripcion();
        fullName.setText(user.getFullName());
        profileDescription.setText(user.getDescripcion());
        if (description.isEmpty()) {
            profileDescription.setText(getResources().getString(R.string.sen_descricion));
        }

        if (mUser.getFotos().size() > 0) {

            mSenFotos.setVisibility(View.GONE);
            mFotos.setVisibility(View.VISIBLE);
            mAdapter.setItems(mUser.getFotos());
            mAdapter.setTitulos(mUser.getTitulos());
            mAdapter.notifyDataSetChanged();
        }

        int size = mUser.getMaquetas().size();
        if (size > 0) {

            mSenMaquetas.setVisibility(View.GONE);

            if (size > 1) {

                setImageMaqueta(mMaqueta2,
                        mImageMaqueta2,
                        mTitulo2,
                        Uri.parse(mUser.getMaquetas().get(1)),
                        Uri.parse(mUser.getCaratulas().get(1)),
                        mUser.getTitulosMaquetas().get(1),
                        mPlay2,
                        1);
            }
            setImageMaqueta(mMaqueta1,
                    mImageMaqueta1,
                    mTitulo1,
                    Uri.parse(mUser.getMaquetas().get(0)),
                    Uri.parse(mUser.getCaratulas().get(0)),
                    mUser.getTitulosMaquetas().get(0),
                    mPlay1,
                    0);
        }

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference()
                .child("users/" + user.getEmail() + "/profile.jpg");

        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

            @Override
            public void onSuccess(Uri uri) {

                if (profileImage == null) {
                    return;
                }
                Picasso.get().load(uri).transform(new CircleTransform()).into(profileImage);
            }
        });
    }

    @Override
    public void showError() {

        String err = getResources().getString(R.string.error);
        Toast.makeText(getActivity(), err, Toast.LENGTH_SHORT).show();
    }

    private void setImageMaqueta(LinearLayout linear,
                                 ImageView image,
                                 TextView titulo,
                                 Uri media,
                                 Uri caratula,
                                 String valor,
                                 ImageButton playBt,
                                 int idx) {

        linear.setVisibility(View.VISIBLE);
        titulo.setText(valor);
        int width = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, 170, getResources().getDisplayMetrics());
        int height = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, 170, getResources().getDisplayMetrics());
        Transformation transformation = new MaskTransformation(getContext(), R.drawable.my_image);
        Picasso.get().load(caratula).resize(width, height).transform(transformation).into(image);

        playBt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mPlayer.isPlaying()) {
                    mPlayer.stop();
                    mPlayer.reset();
                    mPlay1.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                    mPlay2.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
                    if (idx == mPlaying) {
                        return;
                    }
                }

                try {
                    mPlaying = idx;
                    playBt.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                    mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mPlayer.setDataSource(v.getContext(), media);
                    mPlayer.prepare();
                    mPlayer.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    showError();
                }
            }
        });
    }
}
