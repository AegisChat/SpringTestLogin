package atchat.aegis.com.myapplication.BottomNavigation.ContactListFragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import application.Users.UserTemplate;
import atchat.aegis.com.myapplication.BottomNavigation.BottomNavigationMenue;
import atchat.aegis.com.myapplication.R;

/**
 * Created by Avi on 2018-02-16.
 */

public class ContactListAdapter extends RecyclerView.Adapter{

    private Context mContext;
    private List<UserTemplate> mContactList;

    public ContactListAdapter(Context context, List<UserTemplate> contactList){
        mContext = context;
        mContactList = contactList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_layout, parent, false);
        return new ContactViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ContactViewHolder)holder).bind(mContactList.get(position));
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mContactList.size();
    }

    private class ContactViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView contactTextView;
        private UserTemplate mItem;


        public ContactViewHolder(View view) {
            super(view);
            mView = view;
            contactTextView = (TextView) view.findViewById(R.id.content);
            mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Toast.makeText(view.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    Fragment fragment = ContactListProfileFragment.newInstance(mItem);
                    FragmentTransaction fragmentTransaction = ((BottomNavigationMenue) mContext).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);
                    fragmentTransaction.replace(R.id.contentLayout, fragment).commit();
                }
            });
        }

        public void bind(UserTemplate userTemplate){
                contactTextView.setText(userTemplate.getName());
                mItem = userTemplate;
        }



    }
}
