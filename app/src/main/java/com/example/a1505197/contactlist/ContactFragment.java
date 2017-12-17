package com.example.a1505197.contactlist;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.a1505197.contactlist.Utils.ContactPropertyListAdapter;
import com.example.a1505197.contactlist.Utils.UniversalImageLoader;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 1505197 on 9/13/2017.
 */

public class ContactFragment extends Fragment {
    //This will remove the nullpointerexception when adding to a new bundle from mainActivity
    public interface OnEditContactListener
    {
      public void onEditContactSelected(Contacts contact);
    }
    OnEditContactListener mOnEditContactListener;
    public ContactFragment()
    {
        super();
        setArguments(new Bundle());
    }
    Toolbar toolbar;
    private Contacts mContact;
    private TextView mContactName;
    private CircleImageView mContactImage;
    private ListView listView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_contact,container,false);
        toolbar=getActivity().findViewById(R.id.contactToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        mContactName=(TextView) view.findViewById(R.id.contactName);
        mContactImage=(CircleImageView) view.findViewById(R.id.contactImage);
        listView=(ListView) view.findViewById(R.id.lvContactProperties);
         mContact=getContactFromBundle();
        init();
        ImageView ivBackArrow = (ImageView) view.findViewById(R.id.backArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
           getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        ImageView ivEdit=(ImageView)view.findViewById(R.id.ivEdit);
        ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mOnEditContactListener.onEditContactSelected(mContact);
            }
        });
        return view;
    }
    private void init()
    {
        mContactName.setText(mContact.getName());
        UniversalImageLoader.setImage(mContact.getProfileImage(),mContactImage,null,"");
        ArrayList<String> properties=new ArrayList<>();
        properties.add(mContact.getPhonenumber());
        properties.add(mContact.getEmail());
        ContactPropertyListAdapter adapter=new ContactPropertyListAdapter(getActivity(),R.layout.layout_cardview,properties);
        listView.setAdapter(adapter);
        listView.setDivider(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        inflater.inflate(R.menu.menu,menu);
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.menuitem_delete:

        }
        return super.onOptionsItemSelected(item);
    }
    private Contacts getContactFromBundle()
    {
        Bundle bundle=this.getArguments();
        if(bundle!=null)
        {
            return bundle.getParcelable("Contacts");
        }
        else
        {
            return null;
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try
        {
           mOnEditContactListener=(OnEditContactListener)getActivity();
        }
        catch (ClassCastException e)
        {

        }
    }
}
