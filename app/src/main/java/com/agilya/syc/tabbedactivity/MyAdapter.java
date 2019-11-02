package com.agilya.syc.tabbedactivity;

import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    //TODO : ici mettre en place la récupération des datas du flux numéro 1 :

    private final List<Pair<String, String>> myData = Arrays.asList(
            Pair.create("mon item 1","mon blalla de mon item 1"),
            Pair.create("mon item 2","mon blalla de mon item 2"),
            Pair.create("mon item 3","mon blalla de mon item 3"),
            Pair.create("mon item 4","mon blalla de mon item 4"),
            Pair.create("mon item 5","mon blalla de mon item 5"),
            Pair.create("mon item 6","mon blalla de mon item 6"),
            Pair.create("mon item 7","mon blalla de mon item 7"),
            Pair.create("mon item 8","mon blalla de mon item 8"),
            Pair.create("mon item 9","mon blalla de mon item 9"),
            Pair.create("mon item 2","mon blalla de mon item 12"),
            Pair.create("mon item 3","mon blalla de mon item 13"),
            Pair.create("mon item 4","mon blalla de mon item 14"),
            Pair.create("mon item 5","mon blalla de mon item 15"),
            Pair.create("mon item 6","mon blalla de mon item 16"),
            Pair.create("mon item 7","mon blalla de mon item 17"),
            Pair.create("mon item 8","mon blalla de mon item 18"),
            Pair.create("mon item 9","mon blalla de mon item 19")
    );

    @Override
    public int getItemCount() {
        return myData.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View viewCell = inflater.inflate(R.layout.rv_item, parent, false);
        return new MyViewHolder(viewCell);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){
        Pair<String, String> currentData = myData.get(position);
        holder.display( currentData );
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ImageView monParam1 ;
        private final TextView monParam2 ;
        private Pair<String,String> curData;

        public MyViewHolder(final View itemView){
            super(itemView);
            monParam1 = ((ImageView) itemView.findViewById(R.id.rvItemImg));
            monParam2 = ((TextView) itemView.findViewById(R.id.rvItemCategory));
        }

        public void display( Pair<String, String> p_pair ){
            curData = p_pair;
            //monParam1.setText(p_pair.first);

            monParam2.setText(p_pair.second);
        }
    }
}

