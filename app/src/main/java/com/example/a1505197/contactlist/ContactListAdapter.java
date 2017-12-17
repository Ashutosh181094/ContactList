package com.example.a1505197.contactlist;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 1505197 on 9/13/2017.
 */

public class ContactListAdapter extends ArrayAdapter<Contacts> {
    private LayoutInflater mInflter;
    private List<Contacts> mContacts=null;
    private ArrayList<Contacts> arrayList;
    private int layoutResource;
    private Context mContext;
    private String mAppend;
    public ContactListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Contacts> contacts, String append) {
        super(context, resource, contacts);
        mInflter=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource=resource;
        this.mContext=context;
        mAppend=append;
        this.mContacts=contacts;
        arrayList=new ArrayList<>();
        this.arrayList.addAll(mContacts);
    }
private static class ViewHolder
{
    TextView name;
    CircleImageView contactImage;
    ProgressBar mProgressBar;
}

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if(convertView==null)
        {
            convertView=mInflter.inflate(layoutResource,parent,false);
            holder=new ViewHolder();
            holder.name=(TextView)convertView.findViewById(R.id.contactName);
            holder.contactImage=(CircleImageView)convertView.findViewById(R.id.contactImage);
            holder.mProgressBar=(ProgressBar)convertView.findViewById(R.id.contactProgressBar);
            convertView.setTag(holder);
        }
        else
        {
            holder=(ViewHolder)convertView.getTag();
        }
        String name=getItem(position).getName();
        String imagePath=getItem(position).getProfileImage();
        holder.name.setText(name);
        ImageLoader imageLoader=ImageLoader.getInstance();
        imageLoader.displayImage(mAppend + imagePath, holder.contactImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                holder.mProgressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.mProgressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                holder.mProgressBar.setVisibility(View.GONE);
            }
        });
        return convertView;
    }
    public void filter(String characterText)
    {
        characterText=characterText.toLowerCase(Locale.getDefault());
        mContacts.clear();
        if(characterText.length()==0) {
            mContacts.addAll(arrayList);
        }else
        {
            mContacts.clear();
            for(Contacts contact:arrayList)
            {
                if(contact.getName().toLowerCase(Locale.getDefault()).contains(characterText))
                    mContacts.add(contact);
            }
        }
        notifyDataSetChanged();
    }

}
