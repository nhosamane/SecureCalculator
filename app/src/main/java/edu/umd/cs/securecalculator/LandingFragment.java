package edu.umd.cs.securecalculator;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



public class LandingFragment extends Fragment {
    LinearLayout okL, helpL, outofAppL;
    private LoginActivity loginActivity;
    // Firebase Database
    private FirebaseDatabase fireDB;
    private DatabaseReference database;

    @Override
    public void onCreate(Bundle save){
        super.onCreate(save);

        // Init Firebase DB
        fireDB = FirebaseDatabase.getInstance();
        database = fireDB.getReference();
    }

    @Override
    public View onCreateView(LayoutInflater inflator,ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflator, container, savedInstanceState);
        View view = inflator.inflate(R.layout.fragment_landing, container,false);
        okL = (LinearLayout) view.findViewById(R.id.todo_column);
        helpL = (LinearLayout) view.findViewById(R.id.inprogress_column);
        outofAppL = (LinearLayout) view.findViewById(R.id.done_column);

        //get all information
        //change "CMSC436-0101" ; get actual firebase
        database.child("CMSC436-0101")
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            //User student = new User(dataSnapshot);
                            final User student = snapshot.getValue(User.class);
                            //student = new User(snapshot);

                            final TextView currentUsername = new TextView(getActivity());
                            currentUsername.setText(student.getUsername());
                            if(student.getStatus() == 0){
                                okL.addView(currentUsername);
                            }
                            else if(student.getStatus() == 1){
                                helpL.addView(currentUsername);
                            }
                            if(student.getStatus() == 2){
                                outofAppL.addView(currentUsername);
                            }
                            currentUsername.setOnClickListener(new View.OnClickListener(){
                                @Override
                                public void onClick(View view){//this is an anonymous inner class
                                    Toast.makeText(getActivity(), student.getLog().toString() ,Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
        return view;
    }

    public static LandingFragment newInstance() {

        Bundle args = new Bundle();

        LandingFragment fragment = new LandingFragment();
        fragment.setArguments(args);
        return fragment;
    }

}