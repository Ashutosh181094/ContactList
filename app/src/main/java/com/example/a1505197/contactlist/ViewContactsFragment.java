package com.example.a1505197.contactlist;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a1505197.contactlist.Utils.DatabaseHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * Created by 1505197 on 9/13/2017.
 */

public class ViewContactsFragment extends Fragment {
    private static final String TAG = "ViewContactsFragment";
    public interface OnContactsSelectedListener {
        public void OnContactSelected(Contacts contact);

    }
    public interface OnAddContactListener
    {
        public void onAddContact();
    }
    OnAddContactListener mOnAddContact;
    OnContactsSelectedListener mContactListener;
    private static final int STANDARD_APPBAR = 0;
    private static final int SEARCH_APPBAR = 1;
    private int mAppBarState;
    private AppBarLayout viewContactBar, searchBar;
    private String testImageURL="yt3.ggpht.com/-tUnSh4hL1b0/AAAAAAAAAAI/AAAAAAAAAAA/AIy5-05CyFk/s900-c-k-no-mo-rj-c0xffffff/photo.jpg";
    private ContactListAdapter adapter;
    private ListView contactsList;
    private EditText mSearchContacts;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewcontacts, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fabAddContact);
        viewContactBar = (AppBarLayout) view.findViewById(R.id.viewContactToolbar);
        searchBar = (AppBarLayout) view.findViewById(R.id.search_toolbar);
        contactsList=(ListView)view.findViewById(R.id.contactList);
        mSearchContacts=(EditText)view.findViewById(R.id.etSearchContacts);
        setAppBarState(STANDARD_APPBAR);
        setupContactsList();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
       mOnAddContact.onAddContact();
            }
        });
        ImageView ivSearchContact = (ImageView) view.findViewById(R.id.ivSearchIcon);
        ivSearchContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleToolbarState();

            }


        });
        ImageView ivBackArrow = (ImageView) view.findViewById(R.id.ivBackArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleToolbarState();
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try
        {
mContactListener=(OnContactsSelectedListener)getActivity();
            mOnAddContact=(OnAddContactListener)getActivity();
        }catch (ClassCastException e)
        {

        }
    }

    private void setupContactsList()
    {
        final ArrayList<Contacts> contacts=new ArrayList<>();
        //contacts.add(new Contacts("Mitch Tabian","(+91)-8604067421","Mobile","ashutoshrai181094@gmail.com",testImageURL));
       // contacts.add(new Contacts("Ashutosh Rai","(+91)-8604067421","Mobile","ashutoshrai181094@gmail.com",testImageURL));
       // contacts.add(new Contacts("Ashutosh Rai","(+91)-8604067421","Mobile","ashutoshrai181094@gmail.com",testImageURL));
       // contacts.add(new Contacts("Ashutosh Rai","(+91)-8604067421","Mobile","ashutoshrai181094@gmail.com",testImageURL));
       // contacts.add(new Contacts("Ashutosh Rai","(+91)-8604067421","Mobile","ashutoshrai181094@gmail.com",testImageURL));
       // contacts.add(new Contacts("Ashutosh Rai","(+91)-8604067421","Mobile","ashutoshrai181094@gmail.com",testImageURL));
       // contacts.add(new Contacts("Ashutosh Rai","(+91)-8604067421","Mobile","ashutoshrai181094@gmail.com",testImageURL));
       // contacts.add(new Contacts("Ashutosh Rai","(+91)-8604067421","Mobile","ashutoshrai181094@gmail.com",testImageURL));
       // contacts.add(new Contacts("Ashutosh Rai","(+91)-8604067421","Mobile","ashutoshrai181094@gmail.com",testImageURL));
       // contacts.add(new Contacts("Ashutosh Rai","(+91)-8604067421","Mobile","ashutoshrai181094@gmail.com",testImageURL));
       // contacts.add(new Contacts("Ashutosh Rai","(+91)-8604067421","Mobile","ashutoshrai181094@gmail.com",testImageURL));
       // contacts.add(new Contacts("Ashutosh Rai","(+91)-8604067421","Mobile","ashutoshrai181094@gmail.com",testImageURL));
        DatabaseHelper databaseHelper=new DatabaseHelper(getActivity());
        Cursor cursor=databaseHelper.getAllContacts();
        if(!cursor.moveToNext())
        {
            Toast.makeText(getActivity(),"There are no contacts to show",Toast.LENGTH_SHORT).show();
        }
        while(cursor.moveToNext())
        {
            contacts.add(new Contacts(
             cursor.getString(1),//Name
            cursor.getString(2),  //Phone number
            cursor.getString(3),  //device
            cursor.getString(4),  //email
            cursor.getString(5)    // profile image uri
            ));
        }
        Collections.sort(contacts, new Comparator<Contacts>() {
            @Override
            public int compare(Contacts o1, Contacts o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });
       adapter=new ContactListAdapter(getActivity(),R.layout.layout_contactlistitem,contacts,"");

    contactsList.setAdapter(adapter);
        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
             //pass the contact to interface and send it to mainActiviy
                mContactListener.OnContactSelected(contacts.get(position));
            }
        });
        mSearchContacts.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                String text=mSearchContacts.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    private void toggleToolbarState() {
        if (mAppBarState == STANDARD_APPBAR) {
            setAppBarState(SEARCH_APPBAR);
        } else {
            setAppBarState(STANDARD_APPBAR);
        }
    }

    private void setAppBarState(int state) {
        mAppBarState=state;
        if(mAppBarState==STANDARD_APPBAR)
        {
            searchBar.setVisibility(View.GONE);
            viewContactBar.setVisibility(View.VISIBLE);
            View view=getView();
            InputMethodManager imm= (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            try
            {
                imm.hideSoftInputFromWindow(view.getWindowToken(),0);

            }catch (NullPointerException e){
            }

        }
        else if(mAppBarState==SEARCH_APPBAR)
        {
            viewContactBar.setVisibility(View.GONE);
            searchBar.setVisibility(View.VISIBLE);
            InputMethodManager imm=(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(InputMethodManager.SHOW_FORCED,0);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setAppBarState(STANDARD_APPBAR);
    }
}

