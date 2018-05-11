package fitness.sportgenertaion.fitnesstrainer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import fitness.sportgenertaion.fitnesstrainer.Classes.Ejercicio;
import fitness.sportgenertaion.fitnesstrainer.Classes.EjercicioAdapter;
import fitness.sportgenertaion.fitnesstrainer.Classes.Rutina;
import fitness.sportgenertaion.fitnesstrainer.Classes.RutinaAdapter;

public class Dias extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private TabLayout tabLayout;
    private ViewPager mViewPager;
     static String idUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dias);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = findViewById(R.id.tabs);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(mViewPager);
            }
        });

        Bundle parametros = this.getIntent().getExtras();
        if(parametros !=null){
            idUsuario=parametros.getString("idUsuario");

        }

    }



/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_dias, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
*/
    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment implements ValueEventListener, ChildEventListener {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        RecyclerView rvEjercicios ;
        List<Rutina> llistaRutina = new ArrayList<Rutina>();
       RutinaAdapter rutinaAdapter;
        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber,String idUsuario)  {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);


            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_dias, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);

            rvEjercicios = rootView.findViewById(R.id.rvRutina);
            //


            ///Cada fragment hara cosas distintas aqui
            String dia="Lunes";
           if(getArguments().getInt(ARG_SECTION_NUMBER)==1){
                dia="Lunes";
            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==2){
               dia="Martes";
            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==3){
               dia="Miercoles";
            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==4){
               dia="Jueves";
            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==5){
               dia="Viernes";
            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==6){
               dia="Sabado";
            }else if(getArguments().getInt(ARG_SECTION_NUMBER)==7){
               dia="Domingo";
            }
            rvEjercicios.setLayoutManager(new LinearLayoutManager(getContext()));
            //rvPrediccions.setLayoutManager(new GridLayoutManager(this, 2));
            rvEjercicios.addItemDecoration(new DividerItemDecoration(getContext(),
                    LinearLayoutManager.VERTICAL));
            rutinaAdapter = new RutinaAdapter(getContext(), llistaRutina, dia,idUsuario);
            DatabaseReference rutina = FirebaseDatabase.getInstance()
                    .getReference()
                    .child("users"+"/"+idUsuario+"/Rutina/"+dia);
            rutina.addValueEventListener(this);
            rutina.addChildEventListener(this);
            return rootView;
        }
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            llistaRutina.removeAll(llistaRutina);
            for (DataSnapshot element : dataSnapshot.getChildren()) {

                    Rutina rutina = new Rutina(Boolean.parseBoolean(element.getValue().toString()),element.getKey().toString());

                    llistaRutina.add(rutina);

            }
            rvEjercicios.setAdapter(rutinaAdapter);
            rvEjercicios.scrollToPosition(llistaRutina.size() - 1);
        }
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }



        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter  {
        private RecyclerView rvEjercicios;
        private List<Ejercicio> llistaEjercicios = new ArrayList<Ejercicio>();
        private EjercicioAdapter ejercicioAdapter;



        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1,idUsuario);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 7;
        }
        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Lu";
                case 1 :
                    return "Ma";
                case 2 :
                    return "Mi";
                case 3 :
                    return "Ju";
                case 4 :
                    return "Vi";
                case 5 :
                    return "Sá";
                case 6 :
                    return "Do";
            }
            return null;
        }




    }
}
