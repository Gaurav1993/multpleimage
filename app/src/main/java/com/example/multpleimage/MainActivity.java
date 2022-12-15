package com.example.multpleimage;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecycleView;
    TextView mTextView;
    Button mbt_click;
    ArrayList<Uri> mArraylist;
    RecycleViewAdapter mrecycleViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecycleView=(RecyclerView) findViewById(R.id.mrecy_image);
        mTextView=(TextView) findViewById(R.id.texttotalcount);
        mbt_click=(Button) findViewById(R.id.mbt_click);
        mArraylist=new ArrayList<>();
        mrecycleViewAdapter=new RecycleViewAdapter(mArraylist);
        mRecycleView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecycleView.setAdapter(mrecycleViewAdapter);

        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }

    mbt_click.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent();
            intent.setType("image/*");
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN) {
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            }
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent,"Select Picture"),123);

        }
    });


    }

    protected void onActivityResult(int requestCode, int resultCode,@NonNull Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==123 && resultCode==Activity.RESULT_OK) {




                int x=data.getClipData().getItemCount();
                for (int i = 0; i < x; i++) {
                    mArraylist.add(data.getClipData().getItemAt(i).getUri());

                }

                mrecycleViewAdapter.notifyDataSetChanged();
                mTextView.setText("Photo(" + mArraylist.size() + ")");
            } else if (data.getData() != null) {
                String ImageUrl = data.getData().getPath();
                mArraylist.add(Uri.parse(ImageUrl));
            }
        else {
            Toast.makeText(this, "You haven't picked Image", Toast.LENGTH_LONG).show();

        }



    }


}