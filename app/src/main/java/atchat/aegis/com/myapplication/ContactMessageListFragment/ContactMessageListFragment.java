package atchat.aegis.com.myapplication.ContactMessageListFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import atchat.aegis.com.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactMessageListFragment extends Fragment {


    public ContactMessageListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_message_list, container, false);
    }

}
