package dev.santieinaki.fagamosbarullo.app.module.main.profile.adapter;

import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dev.santieinaki.fagamosbarullo.R;
import dev.santieinaki.fagamosbarullo.app.util.MaskTransformation;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {

    private List<String> mList;
    private List<String> mTitulos;

    public PhotosAdapter() {

        mList = new ArrayList<>();
        mTitulos = new ArrayList<>();
    }

    public void setItems(List<String> list) {

        mList = list;
    }

    public void setTitulos(List<String> list) {

        mTitulos = list;
    }

    @NonNull
    @Override
    public PhotoViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                              int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.foto_layout, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoViewHolder holder,
                                 int position) {

        holder.bind(mList.get(position), mTitulos.get(position));
    }

    @Override
    public int getItemCount() {

        return mList.size();
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {

        private TextView mText;
        private ImageView mImage;

        public PhotoViewHolder(@NonNull View itemView) {

            super(itemView);

            mImage = itemView.findViewById(R.id.foto);
            mText = itemView.findViewById(R.id.titulo);
        }

        public void bind(String url, String titulo) {

            mText.setText(titulo);

            int width = (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_SP, 110, itemView.getResources().getDisplayMetrics());
            int height = (int) TypedValue
                    .applyDimension(TypedValue.COMPLEX_UNIT_SP, 140, itemView.getResources().getDisplayMetrics());
            Transformation transformation = new MaskTransformation(itemView.getContext(), R.drawable.my_image);
            Picasso.get().load(url).resize(width, height).transform(transformation).into(mImage);
        }
    }
}
