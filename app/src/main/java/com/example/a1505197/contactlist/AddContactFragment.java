package com.example.a1505197.contactlist;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a1505197.contactlist.Utils.ChangePhotoDialog;
import com.example.a1505197.contactlist.Utils.DatabaseHelper;
import com.example.a1505197.contactlist.Utils.Init;
import com.example.a1505197.contactlist.Utils.UniversalImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 1505197 on 10/2/2017.
 */

public class AddContactFragment extends Fragment implements ChangePhotoDialog.OnPhotoRecievedListener {

    private Contacts mContact;
    private EditText mPhoneNumber,mName,mEmail;
    private CircleImageView mContactImage;
    private Spinner mSelectDevice;
    private Toolbar toolbar;
    private String mSelectedImagePath;
    private int mPreviousKeyStroke;
    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_addcontact,container,false);
        mPhoneNumber=(EditText)view.findViewById(R.id.etContactPhone);
        mName=(EditText)view.findViewById(R.id.etContactName);
        mEmail=(EditText)view.findViewById(R.id.etContactEmail);
        mContactImage=(CircleImageView)view.findViewById(R.id.contactImage);
        mSelectDevice=(Spinner)view.findViewById(R.id.selectDevice);
        toolbar=(Toolbar)view.findViewById(R.id.etcontactToolbar);
        toolbar=getActivity().findViewById(R.id.contactToolbar);

        mSelectedImagePath=null;
        UniversalImageLoader.setImage(null,mContactImage,null,"http://");

        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        TextView heading=(TextView)view.findViewById(R.id.textContactToolbar);
        heading.setText(getString(R.string.add_contact));
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
                 Contacts contact=new Contacts( mName.getText().toString(), mPhoneNumber.getText().toString(),
                 mSelectDevice.getSelectedItem().toString(),
                 mEmail.getText().toString(),
                         mSelectedImagePath);
                 if(databaseHelper.addContact(contact))
                 {
                     Toast.makeText(getActivity(), "Contact Saved", Toast.LENGTH_SHORT).show();
                 getActivity().getSupportFragmentManager().popBackStack();
                 }
                 else
                 {
                     Toast.makeText(getActivity(), "Error Saving", Toast.LENGTH_SHORT).show();
                 }

             }
            }
        });
        ImageView ivCamera = (ImageView) view.findViewById(R.id.ivCamera);
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i< Init.PERMISSIONS.length; i++)
                {
                    String[] permission={Init.PERMISSIONS[i]};
                    if(((MainActivity)getActivity()).checkPermissions(permission))
                    {
                        if(i==Init.PERMISSIONS.length-1)
                        {
                            ChangePhotoDialog dialog=new ChangePhotoDialog();
                            dialog.show(getFragmentManager(),getString(R.string.change_photo_dialog));
                            dialog.setTargetFragment(AddContactFragment.this,0);
                        }
                    }
                    else
                    {
                        ((MainActivity)getActivity()).verifyPermissions(permission);
                    }
                }


            }
        });



        initOnTextChangedListener();
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

    private void initOnTextChangedListener()
    {
        mPhoneNumber.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                mPreviousKeyStroke=keyCode;
                return false;
            }
        });
        mPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               String number=s.toString();
                if(number.length()==3 && mPreviousKeyStroke!=KeyEvent.KEYCODE_DEL &&!number.contains("("))
                {
                    number=String.format("(%s",s.toString().substring(0,3));
                    mPhoneNumber.setText(number);
                    mPhoneNumber.setSelection(number.length());
                }
                else
                    if(number.length()==5 && mPreviousKeyStroke!=KeyEvent.KEYCODE_DEL &&!number.contains(")"))
                    {
                       number=String.format("(%s)%s",s.toString().substring(1,4),s.toString().substring(4,5));
                        mPhoneNumber.setText(number);
                        mPhoneNumber.setSelection(number.length());
                    }
                    else
                        if (number.length()==10&&mPreviousKeyStroke!=KeyEvent.KEYCODE_DEL&&!number.contains("-"))
                        {
                            number=String.format("(%s) %s-%s",s.toString().substring(1,4),s.toString().substring(6,9),s.toString().substring(9,10));
                            mPhoneNumber.setText(number);
                            mPhoneNumber.setSelection(number.length());
                        }
            }
        });
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
