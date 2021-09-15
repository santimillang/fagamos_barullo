package dev.santieinaki.fagamosbarullo.app.module.addphoto;

import androidx.annotation.Nullable;
import butterknife.BindView;
import dev.santieinaki.fagamosbarullo.R;
import dev.santieinaki.fagamosbarullo.app.domain.user.User;
import dev.santieinaki.fagamosbarullo.app.module.BaseActivity;
import dev.santieinaki.fagamosbarullo.app.module.addphoto.presenter.AddPhotoPresenter;
import dev.santieinaki.fagamosbarullo.app.module.addphoto.presenter.AddPhotoPresenterImp;
import dev.santieinaki.fagamosbarullo.app.module.addphoto.presenter.AddPhotoView;
import dev.santieinaki.fagamosbarullo.app.util.MaskTransformation;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class AddPhotoActivity extends BaseActivity implements AddPhotoView {

    public static final String USER_DATA = "USER_DATA_INTENT";

    @BindView(R.id.add_photo_bt)
    Button mAddButton;
    @BindView(R.id.photo_description)
    EditText mDescription;
    @BindView(R.id.add_photo_button)
    ImageButton mPhotoButton;
    @BindView(R.id.progressBar2)
    ProgressBar mProgress;

    private Uri mUri;
    private User mUser;
    private AddPhotoPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);

        mUser = (User) getIntent().getExtras().get(USER_DATA);
        mPresenter = new AddPhotoPresenterImp(this);

        initView();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {

            mUri = data.getData();
            setImage();
        }
    }

    @Override
    public void showError() {

        Toast.makeText(this, "Erro!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void blockView() {

        mProgress.setVisibility(View.VISIBLE);
        getWindow()
                .setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void unblockView() {

        mProgress.setVisibility(View.GONE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void closeView(User user) {

        Intent data = new Intent();
        data.putExtra(USER_DATA, user);

        setResult(Activity.RESULT_OK, data);
        finish();
    }

    private void initView() {

        mPhotoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1000);
            }
        });

        mAddButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String description = mDescription.getText().toString();

                if (description.isEmpty() || mPhotoButton.getDrawable() == null) {

                    showError();
                    return;
                }

                mPresenter.uploadPhoto(mUser, mUri, description);
            }
        });
    }

    private void setImage() {

        int width = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, 110, getResources().getDisplayMetrics());
        int height = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, 140, getResources().getDisplayMetrics());
        Transformation transformation = new MaskTransformation(this, R.drawable.my_image);
        Picasso.get().load(mUri).resize(width, height).transform(transformation).into(mPhotoButton);
    }
}