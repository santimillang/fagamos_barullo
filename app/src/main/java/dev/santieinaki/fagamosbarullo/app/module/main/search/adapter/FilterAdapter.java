package dev.santieinaki.fagamosbarullo.app.module.main.search.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import dev.santieinaki.fagamosbarullo.R;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {

    private List<String> mList;
    private FilterClickListener mListener;

    public FilterAdapter(FilterClickListener listener) {

        mListener = listener;
        mList = new ArrayList<>();

        mList.add("Voz");
        mList.add("Batera");
        mList.add("Baixo");
        mList.add("Guitarra");
        mList.add("Teclado");
        mList.add("Trompeta");
        mList.add("Violín");
        mList.add("Saxofón");
        mList.add("Outro");
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                               int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.filter_layout, parent, false);
        return new FilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder holder,
                                 int position) {

        holder.bind(mList.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (mListener.onClick(mList.get(position))) {

                    holder.setBackground(R.drawable.pill_bg_stroke);
                    return;
                }
                holder.setBackground(R.drawable.pill_bg);
            }
        });
    }

    @Override
    public int getItemCount() {

        return mList.size();
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {

        private TextView mFiltro;

        public FilterViewHolder(@NonNull View itemView) {

            super(itemView);

            mFiltro = itemView.findViewById(R.id.filter_tv);
        }

        public void bind(String filter) {

            mFiltro.setText(filter);
        }

        public void setBackground(int resId) {

            mFiltro.setBackgroundResource(resId);
        }
    }
}
