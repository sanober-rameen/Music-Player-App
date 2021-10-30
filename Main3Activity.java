package com.example.mohit;


import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.textfield.TextInputLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.DexterBuilder;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main3Activity extends AppCompatActivity {

Button btn;
EditText editText1,editText2,editText3;
TextView textView;
ListView listView;
String item[];
BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


        bottomNavigationView=findViewById(R.id.bottom_Navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                switch (menuItem.getItemId())
                {

                    case R.id.Profile_id:
                        Intent intent=new Intent(getApplicationContext(),Main3Activity.class);
                        startActivity(intent);
                        break;

                    case R.id.Favourite_id:
                        Intent intent1=new Intent(getApplicationContext(),Splash.class);
                        startActivity(intent1);
                        break;

                    case R.id.Search_id:
                        Intent intent2=new Intent(getApplicationContext(),Main3Activity.class);
                        startActivity(intent2);
                        break;
                }


                    return true;
            }
        });



        listView=findViewById(R.id.list_view);
        runtimePermission();

    }



    void runtimePermission()
    {
       Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.CAMERA)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        display();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();

                    }


                }).check();

    }



    public ArrayList<File> findSong(File file)
    {
        ArrayList<File> arrayList=new ArrayList<>();

      File[] fileArray=file.listFiles();

      for(File singleFile:fileArray)
      {
      if(singleFile.isDirectory()&& !singleFile.isHidden() )
      {
          arrayList.addAll(findSong(singleFile));
      }
      else{
          if (singleFile.getName().endsWith(".mp3")||
                  singleFile.getName().endsWith(".wav")||singleFile.getName().endsWith(".mp4") ||singleFile.getName().endsWith(".m4a") ) {
              arrayList.add(singleFile);
          }

      }
      }
      return arrayList;
    }

    void display()
    {
      final ArrayList<File> mysong=findSong(Environment.getExternalStorageDirectory());
        item=new String[mysong.size()];

        for(int i=0;i<mysong.size();i++)
        {
            item[i]=mysong.get(i).getName().toString().replace("mp3"," ").replace("wav","")
                    .replace("mp4","").replace("m4a","");

        }

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,item);
        listView.setAdapter(adapter);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            String songName=listView.getItemAtPosition(i).toString();

            Intent intent=new Intent(Main3Activity.this,MusicPlayer.class);
            intent.putExtra("songs",mysong);
            intent.putExtra("songname",songName);
            intent.putExtra("pos",i);
            startActivity(intent);



        }
    });


    }
}
















