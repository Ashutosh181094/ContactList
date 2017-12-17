package com.example.a1505197.contactlist;


import android.database.Cursor;
import android.graphics.Bitmap;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.a1505197.contactlist.Utils.ChangePhotoDialog;
import com.example.a1505197.contactlist.Utils.DatabaseHelper;
import com.example.a1505197.contactlist.Utils.Init;
import com.example.a1505197.contactlist.Utils.UniversalImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 1505197 on 9/13/2017.
 */

public class EditContactFragment extends Fragment implements ChangePhotoDialog.OnPhotoRecievedListener {
    public EditContactFragment() {
        super();
        setArguments(new Bundle());
    }
    private Contacts mContact;
    private EditText mPhoneNumber,mName,mEmail;
    private CircleImageView mContactImage;
    private Spinner mSelectDevice;
    private Toolbar toolbar;
    private String mSelectedImagePath;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

  View view= inflater.inflate(R.layout.fragment_editcontact,container,false);
        mPhoneNumber=(EditText)view.findViewById(R.id.etContactPhone);
        mName=(EditText)view.findViewById(R.id.etContactName);
        mEmail=(EditText)view.findViewById(R.id.etContactEmail);
        mContactImage=(CircleImageView)view.findViewById(R.id.contactImage);
        mSelectDevice=(Spinner)view.findViewById(R.id.selectDevice);
        toolbar=(Toolbar)view.findViewById(R.id.etcontactToolbar);
        toolbar=getActivity().findViewById(R.id.contactToolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        mSelectedImagePath=null;

        mContact=getContactFromBundle();
        if(mContact!=null)
        {
            init();
        }
        ImageView ivBackArrow = (ImageView) view.findViewById(R.id.backArrow);
        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        ImageView ivCheckbox=(ImageView)view.findViewById(R.id.ivCheckmark);
        ivCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              if(checkStringIfNull(mName.getText().toString()))
              {
                  DatabaseHelper databaseHelper=new DatabaseHelper(getActivity());
                  Cursor cursor=databaseHelper.getContactID(mContact);
                  int contactID=-1;
                  while(cursor.moveToNext())
                  {
                      contactID=cursor.getInt(0);
                  }
                  if(contactID > -1)
                  {
                      if(mSelectedImagePath!=null)
                      {
                          mContact.setProfileImage(mSelectedImagePath);
                      }
                  }
                  mContact.setName(mName.getText().toString());
                  mContact.setPhonenumber(mPhoneNumber.getText().toString());
                  mContact.setDevice(mSelectDevice.getSelectedItem().toString());
                  mContact.setEmail(mEmail.getText().toString());
              }
              else
              {
                  Toast.makeText(getActivity(),"Database Error",Toast.LENGTH_LONG).show();
              }
            }
        });

        ImageView ivCamera = (ImageView) view.findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i=0;i<Init.PERMISSIONS.length;i++)
                {
                    String[] permission={Init.PERMISSIONS[i]};
                    if(((MainActivity)getActivity()).checkPermissions(permission))
                    {
                     if(i==Init.PERMISSIONS.length-1)
                     {
                         ChangePhotoDialog dialog=new ChangePhotoDialog();
                         dialog.show(getFragmentManager(),getString(R.string.change_photo_dialog));
                     dialog.setTargetFragment(EditContactFragment.this,0);
                     }
                    }
                    else
                    {
                        ((MainActivity)getActivity()).verifyPermissions(permission);
                    }
                }


            }
        });
        return view;
    }
    private boolean checkStringIfNull(String string)
    {
        if(string.equals(""))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    private void init()
    {
        mPhoneNumber.setText(mContact.getPhonenumber());
        mName.setText(mContact.getName());
        mEmail.setText(mContact.getEmail());
        UniversalImageLoader.setImage(mContact.getProfileImage(),mContactImage,null,"");
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(getActivity(),R.array.device_options,
                android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        mSelectDevice.setAdapter(adapter);
        int position=adapter.getPosition(mContact.getDevice());
        mSelectDevice.setSelection(position);
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

    @Override
    public void getBitmapImage(Bitmap bitmap) {
        if(bitmap!=null)
        {
            ((MainActivity)getActivity()).compressBitmap(bitmap,70);
         mContactImage.setImageBitmap(bitmap);
        }

    }

    @Override
    public void getImagePath(String imagePath) {
        if(!imagePath.equals(""))
        {
            imagePath=imagePath.replace(":/","://");
            mSelectedImagePath=imagePath;
            UniversalImageLoader.setImage(imagePath,mContactImage,null,"");
        }

    }
}
