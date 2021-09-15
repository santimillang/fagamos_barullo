package dev.santieinaki.fagamosbarullo.app.module.addmp3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import dev.santieinaki.fagamosbarullo.R;
import dev.santieinaki.fagamosbarullo.app.domain.user.User;
import dev.santieinaki.fagamosbarullo.app.module.BaseActivity;
import dev.santieinaki.fagamosbarullo.app.module.addmp3.presenter.AddMp3Presenter;
import dev.santieinaki.fagamosbarullo.app.module.addmp3.presenter.AddMp3PresenterImp;
import dev.santieinaki.fagamosbarullo.app.module.addmp3.presenter.AddMp3View;
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
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class AddMp3Activity extends BaseActivity implements AddMp3View {

    public static final String USER_DATA = "USER_DATA_MP3";

    @BindView(R.id.progressBar2)
    ProgressBar mProgress;
    @BindView(R.id.add_photo_button)
    ImageButton mImageButton;
    @BindView(R.id.mp3_bt)
    TextView mMp3Button;
    @BindView(R.id.maqueta_desc)
    EditText mDescripcion;
    @BindView(R.id.add_maqueta_bt)
    Button mButton;

    private Uri mImage;
    private Uri mMp3;
    private User mUser;
    private AddMp3Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_mp3);

        mUser = (User) getIntent().getExtras().get(USER_DATA);
        mPresenter = new AddMp3PresenterImp(this);

        initView();
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == Activity.RESULT_OK) {

            mImage = data.getData();
            setImage();
        } else if (requestCode == 1001 && resultCode == Activity.RESULT_OK) {

            mMp3 = data.getData();
            setMp3();
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

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putParcelable("MP3", mMp3);
        outState.putParcelable("IMAGE", mImage);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        mMp3 = (Uri) savedInstanceState.get("MP3");
        mImage = (Uri) savedInstanceState.get("IMAGE");

        if (mMp3 != null) {
            setMp3();
        }
        if (mImage != null) {
            setImage();
        }
    }

    private void initView() {

        mImageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1000);
            }
        });

        mMp3Button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("audio/mp3");
                startActivityForResult(intent, 1001);
            }
        });

        mButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String description = mDescripcion.getText().toString();

                if (description.isEmpty() || mImage == null || mMp3 == null) {

                    showError();
                    return;
                }

                mPresenter.uploadMaqueta(mUser, mMp3, mImage, description);
            }
        });
    }

    private void setImage() {

        int width = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, 170, getResources().getDisplayMetrics());
        int height = (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_SP, 170, getResources().getDisplayMetrics());
        Transformation transformation = new MaskTransformation(this, R.drawable.my_image);
        Picasso.get().load(mImage).resize(width, height).transform(transformation).into(mImageButton);
    }

    private void setMp3() {

        String[] arr = mMp3.toString().split("/");
        mMp3Button.setText(arr[arr.length - 1]);
    }
}