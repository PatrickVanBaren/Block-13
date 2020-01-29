package com.example.contactsprovider;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.view_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.READ_CONTACTS}, 0);
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor contacts = getContentResolver().query(uri, null, null, null, null);
        mRecyclerView.setAdapter(new ContactsAdapter(this, contacts));
    }

    class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {

        LayoutInflater mInflater;
        Cursor mCursor;

        public ContactsAdapter(final Context context, Cursor cursor) {
            mInflater = LayoutInflater.from(context);
            mCursor = cursor;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(mInflater.inflate(android.R.layout.simple_list_item_1, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            if (mCursor != null) {
                mCursor.moveToPosition(position);
                String string = String.format("%s, %s",
                        mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)),
                        mCursor.getString(mCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                holder.textView.setText(string);
            }
        }

        @Override
        public int getItemCount() {
            if(mCursor != null){
                return mCursor.getCount();
            } else {
                return 0;
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView textView;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = findViewById(android.R.id.text1);
            }
        }
    }
}
