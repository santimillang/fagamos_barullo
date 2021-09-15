package dev.santieinaki.fagamosbarullo.app.module.profilesettings;

import androidx.annotation.Nullable;
import butterknife.BindView;
import dev.santieinaki.fagamosbarullo.R;
import dev.santieinaki.fagamosbarullo.app.domain.user.User;
import dev.santieinaki.fagamosbarullo.app.module.BaseActivity;
import dev.santieinaki.fagamosbarullo.app.module.addmp3.AddMp3Activity;
import dev.santieinaki.fagamosbarullo.app.module.addphoto.AddPhotoActivity;
import dev.santieinaki.fagamosbarullo.app.module.profilesettings.presenter.ProfileSettingsPresenter;
import dev.santieinaki.fagamosbarullo.app.module.profilesettings.presenter.ProfileSettingsPresenterImp;
import dev.santieinaki.fagamosbarullo.app.module.profilesettings.presenter.ProfileSettingsView;
import dev.santieinaki.fagamosbarullo.app.util.CircleTransform;
import dev.santieinaki.fagamosbarullo.app.util.MaskTransformation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileSettings extends BaseActivity implements ProfileSettingsView {

    @BindView(R.id.profile_image)
    ImageView mProfileImage;
    @BindView(R.id.profile_desc_edit)
    EditText mDescription;
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
    @BindView(R.id.foto_1)
    ImageView mFoto1;
    @BindView(R.id.foto_2)
    ImageView mFoto2;
    @BindView(R.id.foto_3)
    ImageView mFoto3;
    @BindView(R.id.foto_4)
    ImageView mFoto4;
    @BindView(R.id.foto_5)
    ImageView mFoto5;
    @BindView(R.id.foto_6)
    ImageView mFoto6;
    @BindView(R.id.bt_foto_1)
    ImageButton mButtonFoto1;
    @BindView(R.id.bt_foto_2)
    ImageButton mButtonFoto2;
    @BindView(R.id.bt_foto_3)
    ImageButton mButtonFoto3;
    @BindView(R.id.bt_foto_4)
    ImageButton mButtonFoto4;
    @BindView(R.id.bt_foto_5)
    ImageButton mButtonFoto5;
    @BindView(R.id.bt_foto_6)
    ImageButton mButtonFoto6;
    @BindView(R.id.bt_maqueta_1)
    ImageButton mButtonMaqueta1;
    @BindView(R.id.bt_maqueta_2)
    ImageButton mButtonMaqueta2;
    @BindView(R.id.maqueta_1)
    ImageView mMaqueta1;
    @BindView(R.id.maqueta_2)
    ImageView mMaqueta2;

    private ProfileSettingsPresenter mPresenter;
    private User mUser;
    private List<ImageView> mImagesMaquetas = new ArrayList<>();
    private List<ImageButton> mMaquetasButtons = new ArrayList<>();
    private List<ImageButton> mButtons = new ArrayList<>();
    private List<ImageView> mImages = new ArrayList<>();
    private Map<String, TextView> mMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);

        mMap.put("Voz", mVoz);
        mMap.put("Batera", mBatera);
        mMap.put("Baixo", mBaixo);
        mMap.put("Guitarra", mGuitarra);
        mMap.put("Teclado", mTeclado);
        mMap.put("Trompeta", mTrompeta);
        mMap.put("Violín", mViolin);
        mMap.put("Saxofón", mSaxofon);
        mMap.put("Outro", mOutro);

        mImages.add(mFoto1);
        mImages.add(mFoto2);
        mImages.add(mFoto3);
        mImages.add(mFoto4);
        mImages.add(mFoto5);
        mImages.add(mFoto6);

        mButtons.add(mButtonFoto1);
        mButtons.add(mButtonFoto2);
        mButtons.add(mButtonFoto3);
        mButtons.add(mButtonFoto4);
        mButtons.add(mButtonFoto5);
        mButtons.add(mButtonFoto6);

        mImagesMaquetas.add(mMaqueta1);
        mImagesMaquetas.add(mMaqueta2);

        mMaquetasButtons.add(mButtonMaqueta1);
        mMaquetasButtons.add(mButtonMaqueta2);

        mPresenter = new ProfileSettingsPresenterImp(this);
        mPresenter.initFlow();

        setListeners();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000) {
            if (resultCode == Activity.RESULT_OK) {
                Uri imageUri = data.getData();
                uploadImageToFirebase(imageUri);
            }
        } else if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {

            setUserInfo((User) data.getExtras().get(AddPhotoActivity.USER_DATA));
        } else if (requestCode == 1002 && resultCode == Activity.RESULT_OK) {

            setUserInfo((User) data.getExtras().get(AddMp3Activity.USER_DATA));
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        mPresenter.saveInfo(mUser);
    }

    @Override
    public void setUserInfo(User user) {

        if (mDescription == null) {
            return;
        }

        mUser = user;
        String description = user.getDescripcion();

        // image
        setImage(user);

        // desc
        mDescription.setText(description);
        if (description.isEmpty()) {
            mDescription.setText(getResources().getString(R.string.sen_descricion));
        }

        // instrumentos
        setInstrumentos(user.getInstrumento());

        // fotos
        for (int i = 0; i < user.getFotos().size(); i++) {

            setPhoto(i, Uri.parse(user.getFotos().get(i)));
            mButtons.get(i).setImageResource(R.drawable.ic_baseline_cancel_24);
        }

        // maquetas
        for (int i = 0; i < user.getMaquetas().size(); i++) {

            setMaqueta(i, Uri.parse(user.getCaratulas().get(i)));
            mMaquetasButtons.get(i).setImageResource(R.drawable.ic_baseline_cancel_24);
        }
    }

    @Override
    public void showError() {

        String err = getResources().getString(R.string.error);
        Toast.makeText(this, err, Toast.LENGTH_SHORT).show();
    }

    private void setListeners() {

        mDescription.addTextChangedListener(new MyTextWatcher());

        for (Map.Entry<String, TextView> entry : mMap.entrySet()) {

            TextView tv = entry.getValue();
            tv.setOnClickListener(v -> {

                if (mPresenter.instrumentoClicked(entry.getKey())) {
                    tv.setBackgroundResource(R.drawable.pill_bg_stroke);
                    return;
                }
                tv.setBackgroundResource(R.drawable.pill_bg);
            });
        }

        mProfileImage.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 1000);
        });

        for (int i = 0; i < mButtons.size(); i++) {

            final int idx = i;
            ImageButton button = mButtons.get(i);
            button.setOnClickListener(v -> {

                addPhotoClicked(idx);
            });
        }

        for (int i = 0; i < mMaquetasButtons.size(); i++) {

            final int idx = i;
            ImageButton button = mMaquetasButtons.get(i);
            button.setOnClickListener(v -> {

                addMaquetaClicked(idx);
            });
        }
    }

    private void addMaquetaClicked(int idx) {

        // add maqueta
        if (idx + 1 > mUser.getMaquetas().size()) {

            Intent intent = new Intent(this, AddMp3Activity.class);
            intent.putExtra(AddMp3Activity.USER_DATA, mUser);
            startActivityForResult(intent, 1002);
            return;
        }

        mUser.getMaquetas().remove(idx);
        mUser.getTitulosMaquetas().remove(idx);
        mUser.getCaratulas().remove(idx);
        mPresenter.deletePhoto(mUser);
        mButtons.get(idx).setImageResource(R.drawable.ic_baseline_add_circle_24);
        resetImages();
        setUserInfo(mUser);
    }

    private void addPhotoClicked(int idx) {

        // add photo
        if (idx + 1 > mUser.getFotos().size()) {

            Intent intent = new Intent(this, AddPhotoActivity.class);
            intent.putExtra(AddPhotoActivity.USER_DATA, mUser);
            startActivityForResult(intent, 1001);
            return;
        }

        // delete photo
        mUser.getFotos().remove(idx);
        mUser.getTitulos().remove(idx);
        mPresenter.deletePhoto(mUser);
        mButtons.get(idx).setImageResource(R.drawable.ic_baseline_add_circle_24);
        resetImages();
        setUserInfo(mUser);
    }

    private void resetImages() {

        for (int i = 0; i < 6; i++) {

            mImages.get(i).setImageResource(R.drawable.my_image);
            mButtons.get(i).setImageResource(R.drawable.ic_baseline_add_circle_24);
        }

        for (int i = 0; i < 2; i++) {

            mImagesMaquetas.get(i).setImageResource(R.drawable.my_image);
            mMaquetasButtons.get(i).setImageResource(R.drawable.ic_baseline_add_circle_24);
        }
    }

    private void setInstrumentos(List<String> instrumentos) {

        for (String instrumento : instrumentos) {

            TextView textView = mMap.get(instrumento);
            textView.setBackgroundResource(R.drawable.pill_bg_stroke);
        }
    }

    private void setImage(User user) {

        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference()
                .child("users/" + user.getEmail() + "/profile.jpg");

        storageReference.getDownloadUrl().addOnSuccessListener(uri -> loadImage(uri));
    }

    private void uploadImageToFirebase(Uri imageUri) {

        if (mUser == null) {
            return;
        }

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        final StorageReference fileRef = storageReference.child("users/" + mUser.getEmail() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(taskSnapshot -> fileRef.getDownloadUrl()
                .addOnSuccessListener(uri -> loadImage(uri)));
    }

    private void loadImage(Uri uri) {

        if (mProfileImage == null) {
            return;
        }
        Picasso.get().load(uri).transform(new CircleTransform()).into(mProfileImage);
    }

    private void setMaqueta(int idx,
                          Uri uri) {

        ImageView image = mImagesMaquetas.get(idx);

        if (image == null) {
            return;
        }
        int width = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, 170, getResources().getDisplayMetrics());
        int height = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, 170, getResources().getDisplayMetrics());
        Transformation transformation = new MaskTransformation(this, R.drawable.my_image);
        Picasso.get().load(uri).resize(width, height).transform(transformation).into(image);
    }

    private void setPhoto(int idx,
                          Uri uri) {

        ImageView image = mImages.get(idx);

        if (image == null) {
            return;
        }
        int width = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, 110, getResources().getDisplayMetrics());
        int height = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, 140, getResources().getDisplayMetrics());
        Transformation transformation = new MaskTransformation(this, R.drawable.my_image);
        Picasso.get().load(uri).resize(width, height).transform(transformation).into(image);
    }

    private class MyTextWatcher implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s,
                                      int start,
                                      int count,
                                      int after) {

        }

        @Override
        public void onTextChanged(CharSequence s,
                                  int start,
                                  int before,
                                  int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            mPresenter.changeDescripcion(s.toString());
        }
    }
}