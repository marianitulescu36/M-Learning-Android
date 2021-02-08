package com.example.first;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CursFinalizatAdapter extends ArrayAdapter<CursFinalizat> {
    private Context context;
    private int resource;
    private List<CursFinalizat> cursuriFinalizateList;
    private LayoutInflater layoutInflater;


    public CursFinalizatAdapter(@NonNull Context context, int resource, List<CursFinalizat> cursuriFinalizateList, LayoutInflater layoutInflater) {
        super(context, resource, cursuriFinalizateList);
        this.context = context;
        this.resource = resource;
        this.cursuriFinalizateList = cursuriFinalizateList;
        this.layoutInflater = layoutInflater;
    }

    //adapter pentru a afisa elementul CURS FINALIZAT
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View view = layoutInflater.inflate(resource, parent, false);
        CursFinalizat cursFinalizat = cursuriFinalizateList.get(position);

        if (cursFinalizat!=null)
        {
            TextView tv1 = view.findViewById(R.id.id);
            tv1.setText(String.valueOf(cursFinalizat.getId()));

            TextView tv2 = view.findViewById(R.id.email);
            tv2.setText(String.valueOf(cursFinalizat.getEmail()));

            TextView tv3 = view.findViewById(R.id.nrCursuriFinalizate);
            tv3.setText(String.valueOf(cursFinalizat.getNrCursuriFinalizate()));

            TextView tv4 = view.findViewById(R.id.numePrenume);
            tv4.setText(String.valueOf(cursFinalizat.getNume_prenume()));

            TextView tv5 = view.findViewById(R.id.varsta);
            tv5.setText(String.valueOf(cursFinalizat.getVarsta()));

            TextView tv6 = view.findViewById(R.id.gen);
            tv6.setText(cursFinalizat.getGen());

            TextView tv7 = view.findViewById(R.id.numeCurs);
            tv7.setText(cursFinalizat.getNumeCurs());

            TextView tv8 = view.findViewById(R.id.dificultate);
            tv8.setText(cursFinalizat.getDificultate());

            TextView tv9 = view.findViewById(R.id.notita);
            tv9.setText(cursFinalizat.getNotita());
        }

        return view;
    }

}
