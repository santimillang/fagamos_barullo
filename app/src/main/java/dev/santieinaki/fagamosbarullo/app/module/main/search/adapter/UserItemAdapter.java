package dev.santieinaki.fagamosbarullo.app.module.main.search.adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dev.santieinaki.fagamosbarullo.R;
import dev.santieinaki.fagamosbarullo.app.module.main.search.viewmodel.UserModel;
import dev.santieinaki.fagamosbarullo.app.util.CircleTransform;

public class UserItemAdapter extends RecyclerView.Adapter<UserItemAdapter.UsersViewHolder> {

    private List<UserModel> mList;
    private UserClickListener mListener;

    public UserItemAdapter(List<UserModel> list, UserClickListener listener) {

        mList = list;
        mListener = listener;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                              int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_display, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final UsersViewHolder holder,
                                 int position) {

        holder.bind(mList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mListener.onClicked(mList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {

        return mList.size();
    }

    public void setItems(List<UserModel> users) {

        mList = users;
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {

        private TextView fName;
        private TextView email;
        private ImageView profileImage;

        public UsersViewHolder(@NonNull View itemView) {

            super(itemView);

            fName = itemView.findViewById(R.id.userName);
            email = itemView.findViewById(R.id.userEmail);
            profileImage = itemView.findViewById(R.id.userProfile);
        }

        public void bind(UserModel userModel) {

            fName.setText(userModel.getfName());
            email.setText(userModel.getEmail());

            StorageReference profileRef = FirebaseStorage.getInstance().getReference()
                    .child("users/" + userModel.getEmail() + "/profile.jpg");
            profileRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {

                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    if (profileImage == null) {
                        return;
                    }

                    if (task.isSuccessful()) {
                        Picasso.get().load(task.getResult()).transform(new CircleTransform()).into(profileImage);
                        return;
                    }
                    profileImage.setImageDrawable(null);
                }
            });
        }
    }
}
