package com.example.a1505197.contactlist.Utils;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.a1505197.contactlist.MainActivity;
import com.example.a1505197.contactlist.R;

import java.util.List;

/**
 * Created by 1505197 on 9/22/2017.
 */

public class ContactPropertyListAdapter extends ArrayAdapter<String> {
    private LayoutInflater mInflter;
    private List<String> mProperties = null;
    private int layoutResource;
    private Context mContext;
    private String mAppend;


    public ContactPropertyListAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> properties) {
        super(context, resource, properties);
        mInflter = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutResource = resource;
        this.mContext = context;

        this.mProperties = properties;


    }

    private static class ViewHolder {
        TextView property;
        ImageView rightIcon;
        ImageView leftIcon;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = mInflter.inflate(layoutResource, parent, false);
            holder = new ViewHolder();
            holder.property = (TextView) convertView.findViewById(R.id.tvMiddleCardView);
            holder.rightIcon = (ImageView) convertView.findViewById(R.id.iconRightCardView);
            holder.leftIcon = (ImageView) convertView.findViewById(R.id.iconLeftCardView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final String property = getItem(position);
        holder.property.setText(property);

        if (property.contains("@")) {
            holder.leftIcon.setImageResource(R.drawable.ic_email);
            holder.leftIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   Intent emailintent=new Intent(Intent.ACTION_SEND);
                    emailintent.setType("plain/text");
                    emailintent.putExtra(Intent.EXTRA_EMAIL,new String[]{property});
                    mContext.startActivity(emailintent);
                }
            });
        }
        else if ((property.length() != 0)) {
            holder.leftIcon.setImageResource(R.drawable.ic_phone);
            holder.leftIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (((MainActivity) mContext).checkPermissions(Init.PHONE_PERMISSION)) {
                        Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", property, null));
                        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        mContext.startActivity(callIntent);
                         }
                         else
                             {

                                 ((MainActivity)mContext).verifyPermissions(Init.PHONE_PERMISSION);
                         }
                     }
                 });
                 holder.rightIcon.setImageResource(R.drawable.ic_message);
                 holder.rightIcon.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Intent smsIntent=new Intent(Intent.ACTION_VIEW,Uri.fromParts("sms",property,null));
                         mContext.startActivity(smsIntent);
                     }
                 });
             }
        return convertView;
    }
}
