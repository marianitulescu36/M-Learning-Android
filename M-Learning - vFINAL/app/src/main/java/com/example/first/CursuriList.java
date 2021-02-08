package com.example.first;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class CursuriList extends AppCompatActivity {

    ListView listView;
    List<Curs> cursuri = new ArrayList<Curs>();

    List<String> dbtitles;
    List<String> dbcateg;
    int image = R.drawable.imagine_curs;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cursuri);

        CursuriDB db = CursuriDB.getInstance(getApplicationContext());
        cursuri = db.getCursuriDao().getAll();


        dbtitles = cursuri.stream()
                .map(p -> p.getTitlu())
                .collect(Collectors.toList());
        dbcateg = cursuri.stream()
                .map(p -> p.getTipCurs())
                .collect(Collectors.toList());

        listView = findViewById(R.id.listview);

        Adaptor adaptor = new Adaptor(this, dbtitles, dbcateg, image);
        listView.setAdapter(adaptor);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                AddCourseFragment fragment = new AddCourseFragment();
                Bundle args = new Bundle();

                args.putString("titlu", cursuri.get(position).getTitlu());
                args.putString("continut", cursuri.get(position).getContinut());
                args.putString("tip", cursuri.get(position).getTipCurs());
                args.putString("id", String.valueOf(cursuri.get(position).getId()));
                fragment.setArguments(args);

                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                TextView tv = findViewById(R.id.cursuriMesaj);
                tv.setVisibility(View.GONE);
                ListView lv = findViewById(R.id.listview);
                lv.setVisibility(View.GONE);
                ft.replace(R.id.cursuri_container, fragment);
                ft.commit();
            }

        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long id) {

                final Curs curs = cursuri.get(position);

                final Adaptor adapter = (Adaptor) listView.getAdapter();


                final AlertDialog dialog = new AlertDialog.Builder(CursuriList.this).
                        setTitle("Confirmare stergere").
                        setMessage("Sigur doriti sa stergeti?")
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(getApplicationContext(), "Nu am sters nimic!", Toast.LENGTH_LONG).show();
                                dialogInterface.cancel();
                            }
                        }).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                CursuriDB db = CursuriDB.getInstance(view.getContext().getApplicationContext());
                                db.getCursuriDao().delete(curs);

                                adapter.notifyDataSetChanged();
                                Toast.makeText(getApplicationContext(), curs.getTitlu() + " a fost sters!", Toast.LENGTH_LONG).show();

                                finish();
                                startActivity(getIntent());
                            }
                        }).setNeutralButton("Vizualizează", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(CursuriList.this, ViewCourseActivity.class);
                                intent.putExtra("curs", curs);
                                startActivity(intent);
                            }
                        }).create();

                dialog.show();
                return true;
            }
        });
    }


    class Adaptor extends ArrayAdapter<String> {
        Context context;
        List<String> rTitle;
        List<String> rcateg;
        int rImgages;

        Adaptor (Context c, List<String> title, List<String> categ, int image) {
            super(c, R.layout.row, R.id.textview1, title);
            this.context = c;
            this.rTitle = title;
            this.rcateg = categ;
            this.rImgages = image;
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView images = row.findViewById(R.id.image);
            TextView title = row.findViewById(R.id.textview1);
            TextView categ = row.findViewById(R.id.textview2);

            images.setImageResource(rImgages);
            title.setText(rTitle.get(position));
            categ.setText(rcateg.get(position));

            return row;
        }
    }
}