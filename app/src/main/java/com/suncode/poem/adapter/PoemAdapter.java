package com.suncode.poem.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.suncode.poem.R;
import com.suncode.poem.model.Poem;

import java.util.List;

public class PoemAdapter extends RecyclerView.Adapter<PoemAdapter.PoemHolder> {

    private Context mContext;
    private List<Poem> mData;
    private ClickHandler mClickHandler;

    public PoemAdapter(Context mContext, List<Poem> mData, ClickHandler mClickHandler) {
        this.mContext = mContext;
        this.mData = mData;
        this.mClickHandler = mClickHandler;
    }

    @NonNull
    @Override
    public PoemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_poem, parent, false);
        return new PoemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PoemHolder holder, int position) {
        Poem poem = mData.get(position);

        holder.mTitleTv.setText(poem.getTitle());
        holder.mNameTv.setText(poem.getPoet().getName());

        holder.itemView.setOnClickListener(v -> mClickHandler.onClickListener(position));
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class PoemHolder extends RecyclerView.ViewHolder {

        final TextView mTitleTv;
        final TextView mNameTv;

        public PoemHolder(@NonNull View itemView) {
            super(itemView);

            mTitleTv = itemView.findViewById(R.id.textView_list_item_title);
            mNameTv = itemView.findViewById(R.id.textView_list_item_name);
        }
    }

    public interface ClickHandler {
        void onClickListener(int position);
    }
}
