package com.example.auzan.kamusku.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.auzan.kamusku.Kamus;
import com.example.auzan.kamusku.R;

import java.util.ArrayList;

/**
 * Created by auzan on 9/27/2018.
 * Github: @auzanassdq
 */
public class ListKamusAdapter extends RecyclerView.Adapter<ListKamusAdapter.KamusHolder> {

    private ArrayList<Kamus> mData = new ArrayList<>();
    private Context context;
    private LayoutInflater mInflater;

    public ListKamusAdapter(Context context){
        this.context = context;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public KamusHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_word_item, parent, false);
        return new KamusHolder(view);
    }

    public void addItem(ArrayList<Kamus> mData){
        this.mData = mData;
        notifyDataSetChanged();
    }

    public void replaceAll(ArrayList<Kamus> items) {
        mData = items;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(KamusHolder holder, int position) {
        holder.tvWord.setText(mData.get(position).getWords());
        holder.tvMean.setText(mData.get(position).getMeans());
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class KamusHolder extends RecyclerView.ViewHolder {
        private TextView tvWord;
        private TextView tvMean;

        public KamusHolder(View itemView) {
            super(itemView);
            tvWord = (TextView)itemView.findViewById(R.id.tv_word);
            tvMean = (TextView)itemView.findViewById(R.id.tv_mean);
        }
    }
}
