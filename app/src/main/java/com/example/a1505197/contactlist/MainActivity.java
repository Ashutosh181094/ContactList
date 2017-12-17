package com.example.a1505197.contactlist;

import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.example.a1505197.contactlist.Utils.UniversalImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity implements ViewContactsFragment.OnContactsSelectedListener,ContactFragment.OnEditContactListener,ViewContactsFragment.OnAddContactListener {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_CODE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initimageLoader();
        init();
    }
    private void init()
    {
        ViewContactsFragment fragment=new ViewContactsFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }
    private  void initimageLoader()
    {
        UniversalImageLoader universalImageLoader=new UniversalImageLoader(MainActivity.this);
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }
    public Bitmap compressBitmap(Bitmap bitmap,int quality)
    {
        ByteArrayOutputStream stream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,quality,stream);
    return bitmap;
    }

    @Override
    public void OnContactSelected(Contacts contact) {
ContactFragment fragment=new ContactFragment();
        Bundle args=new Bundle();
        args.putParcelable("Contacts",contact);
        fragment.setArguments(args);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.addToBackStack(getString(R.string.contact_fragment));
        transaction.commit();

    }

    public void verifyPermissions(String[] permissions)
    {
        ActivityCompat.requestPermissions(MainActivity.this,permissions,REQUEST_CODE);
    }
    public boolean checkPermissions(String[] permission)
    {
       int permissionRequest=ActivityCompat.checkSelfPermission(MainActivity.this,permission[0]);
        if(permissionRequest!= PackageManager.PERMISSION_GRANTED)
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onEditContactSelected(Contacts contact)
    {
        EditContactFragment fragment=new EditContactFragment();
        Bundle args=new Bundle();
        args.putParcelable("Contacts",contact);
        fragment.setArguments(args);
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.addToBackStack(getString(R.string.edit_contact_fragment));
        transaction.commit();

    }

    @Override
    public void onAddContact() {
        AddContactFragment fragment=new AddContactFragment();
        FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.addToBackStack(getString(R.string.add_contact_fragment));
        transaction.commit();
    }
}
