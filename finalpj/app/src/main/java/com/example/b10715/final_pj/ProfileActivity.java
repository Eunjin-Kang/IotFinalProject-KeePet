package com.example.b10715.final_pj;

import android.app.Activity;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
        import android.graphics.Paint;
        import android.graphics.PorterDuff;
        import android.graphics.PorterDuffXfermode;
        import android.graphics.Rect;
        import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
        import android.support.design.widget.TabLayout;
        import android.support.v4.content.ContextCompat;
        import android.support.v4.view.GravityCompat;
        import android.support.v4.view.ViewPager;
        import android.support.v4.widget.DrawerLayout;
        import android.support.v4.app.Fragment;
        import android.support.v7.app.ActionBar;
        import android.support.v7.app.ActionBarActivity;
        import android.support.v7.app.ActionBarDrawerToggle;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.widget.Toolbar;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
        import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.TabHost;
        import android.widget.TextView;
        import android.view.View.OnClickListener;
        import android.widget.Toast;
        import android.graphics.Bitmap.Config;

import java.io.File;
import java.io.IOException;

import static android.R.attr.data;
import static com.example.b10715.final_pj.Config.EMAIL_SHARED_PREF;
        import static com.example.b10715.final_pj.Config.LOGGEDIN_SHARED_PREF;
        import static com.example.b10715.final_pj.Config.SHARED_PREF_NAME;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    DrawerLayout mDrawerLayout;
//    TabHost tabHost;

    TextView name;
    TextView age;
    TextView species;
    TextView sex;

    private static final int REQUEST_TAKE_PHOTO = 3; //0,1 을 request num으로 받아 올때 겹치는 부분이 있어서 3,4,5 로 바꿨음.
    private static final int REQUEST_TAKE_ALBUM = 4;
    private static final int REQUEST_IMAGE_CROP = 5;

    private Uri photoURI, albumURI = null;
    private ImageView iv_capture;
    private String mCurrentPhotoPath;
    Boolean album = false; // 앨범에서 가져온 사진인지, 직접 바로 찍은 사진인지를 구분

/*    String dbName = "st_file.db";
    private MySQLiteOpenHelper helper;
    public SQLiteDatabase db;
    String tag = "SQLite"; // Log의 tag 로 사용
    public String tableName = "petinfo"; // DB의 table 명
    MySQLiteOpenHelper db1;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        iv_capture = (ImageView) this.findViewById(R.id.iv_capture);
        Button btn_choose = (Button) this.findViewById(R.id.btn_choose_image);

        btn_choose.setOnClickListener(this);

        TabHost tabHost=(TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec spec1=tabHost.newTabSpec("Profile_Tab1");
        Drawable home = ContextCompat.getDrawable(this, R.drawable.ic_action_change_home);
        spec1.setIndicator("", home);
        spec1.setContent(R.id.tab1);
        tabHost.addTab(spec1);

        TabHost.TabSpec spec2=tabHost.newTabSpec("Profile_Tab2");
        Drawable cam = ContextCompat.getDrawable(this, R.drawable.ic_action_cam);
        spec2.setIndicator("", cam);
        spec2.setContent(R.id.tab2);
        tabHost.addTab(spec2);

        TabHost.TabSpec spec3=tabHost.newTabSpec("Profile_Tab3");
        Drawable gps = ContextCompat.getDrawable(this, R.drawable.ic_action_gps);
        spec3.setIndicator("", gps);
        spec3.setContent(R.id.tab3);
        tabHost.addTab(spec3);

        TabHost.TabSpec spec4=tabHost.newTabSpec("Profile_Tab4");
        Drawable user = ContextCompat.getDrawable(this, R.drawable.ic_action_user);
        spec4.setIndicator("", user);
        spec4.setContent(R.id.tab4);
        tabHost.addTab(spec4);

        tabHost.setCurrentTab(0);

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if(tabId.equals("Profile_Tab1")){
                    Intent intenta = new Intent(ProfileActivity.this, ProfileActivity.class);
                    startActivity(intenta);
                }else if(tabId.equals("Profile_Tab2")){
                    Intent intents = new Intent(ProfileActivity.this, CamActivity.class);
                    startActivity(intents);
                }else if(tabId.equals("Profile_Tab3")){
                    Intent intentd = new Intent(ProfileActivity.this, GpsActivity.class);
                    startActivity(intentd);
                }else if(tabId.equals("Profile_Tab4")){
                    Intent intentf = new Intent(ProfileActivity.this, UserActivity.class);
                    startActivity(intentf);
                }
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.profile_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                mDrawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                switch (id) {
                    case R.id.navigation_item_user:
                        Intent intent1 = new Intent(ProfileActivity.this, UserActivity.class);
                        startActivity(intent1);
                        break;
                    case R.id.navigation_item_cam:
                        Intent intent = new Intent(ProfileActivity.this, CamActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.navigation_item_gps:
                        Intent intent2 = new Intent(ProfileActivity.this, GpsActivity.class);
                        startActivity(intent2);
                        break;
                }
                return true;
            }
        });

        LoginActivity loginActivity = new LoginActivity();

        View header = navigationView.getHeaderView(0);
        TextView header_email = (TextView)header.findViewById(R.id.header_email);
        header_email.setText(loginActivity.call_email);

        /*    Button petadd = (Button) findViewById(R.id.pet_add);

        petadd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                final View dialogView = (View) View.inflate(ProfileActivity.this, R.layout.pet_dialog1, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(ProfileActivity.this);
                dlg.setTitle("애완견 정보 입력");
                dlg.setView(dialogView);
                dlg.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText dlgEdtName = (EditText) dialogView.findViewById(R.id.dlgedt1);
                        EditText dlgEdtAge = (EditText) dialogView.findViewById(R.id.dlgedt2);
                        EditText dlgEdtSpecies = (EditText) dialogView.findViewById(R.id.dlgedt3);
                        EditText dlgEdtSex = (EditText) dialogView.findViewById(R.id.dlgedt4);

                        dlgEdtName.setText(dlgEdtName.getText().toString());
                        dlgEdtAge.setText(dlgEdtAge.getText().toString());
                        dlgEdtSpecies.setText(dlgEdtSpecies.getText().toString());
                        dlgEdtSex.setText(dlgEdtSex.getText().toString());
                    }
                });
            }
        });
    */

        Button pet_add_btn = (Button) findViewById(R.id.pet_add);

        name = (TextView) findViewById(R.id.edit_petname);
        age = (TextView) findViewById(R.id.edit_petage);
        species = (TextView) findViewById(R.id.edit_petspecies);
        sex = (TextView) findViewById(R.id.edit_petsex);

        pet_add_btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfileActivity.this, Profile_subActivity.class);
                startActivityForResult(intent, 1);
            }
        });

    }

    /**
     * 카메라에서 사진 촬영
     */
    public void doTakePhotoAction() // 카메라 촬영 후 이미지 가져오기
    {
        Log.i("action","camera");
        Intent takePictureIntent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.i("action","Intent takePictureIntent");

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile(); // 사진찍은 후 저장할 임시 파일
            } catch (IOException ex) {
                Toast.makeText(getApplicationContext(), "createImageFile Failed", Toast.LENGTH_LONG).show();
            }

            if (photoFile != null) {
                Log.i("action","after if");
                photoURI = Uri.fromFile(photoFile); // 임시 파일의 위치,경로 가져옴
                Log.i("action"," photoURI = Uri.fromFile");
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI); // 임시 파일 위치에 저장
                Log.i("action"," takePictureIntent.putExtra");
                startActivityForResult(takePictureIntent,REQUEST_TAKE_PHOTO);
                Log.i("action","startActivityForResult");
            }
        }
    }

    // 저장할 폴더 생성
    private File createImageFile() throws IOException {
        /* Create an image file name, 폴더명 지정 방법 (문제 : DIRECTORY_DCIM , DIRECTORY_PICTURE 경로가 없는 폰 존재)
        String imageFileName = "tmp_" + String.valueOf(System.currentTimeMillis());
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "MYAPP/");
        File file = File.createTempFile(imageFileName, ".jpg", storageDir);
        mCurrentPhotoPath = file.getAbsolutePath();
        return file;
        */

        // 특정 경로와 폴더를 지정하지 않고, 메모리 최상 위치에 저장 방법
        String imageFileName = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        File storageDir = new File(Environment.getExternalStorageDirectory(), imageFileName);
        mCurrentPhotoPath = storageDir.getAbsolutePath();
        return storageDir;
    }

    /**
     * 앨범에서 이미지 가져오기
     */
    public void doTakeAlbumAction() // 앨범에서 이미지 가져오기
    {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, REQUEST_TAKE_ALBUM);
    }

    private void cropImage() {
        Intent cropIntent = new Intent("com.android.camera.action.CROP");

        cropIntent.setDataAndType(photoURI, "image/*");
        //cropIntent.putExtra("outputX", 200); // crop한 이미지의 x축 크기
        //cropIntent.putExtra("outputY", 200); // crop한 이미지의 y축 크기
        //cropIntent.putExtra("aspectX", 1); // crop 박스의 x축 비율
        //cropIntent.putExtra("aspectY", 1); // crop 박스의 y축 비율
        cropIntent.putExtra("scale", true);

        if(album == false) {
            cropIntent.putExtra("output", photoURI); // 크랍된 이미지를 해당 경로에 저장
        } else if(album == true){
            cropIntent.putExtra("output", albumURI); // 크랍된 이미지를 해당 경로에 저장
        }

        startActivityForResult(cropIntent, REQUEST_IMAGE_CROP);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);

        //if(requestCode == 1) {
            if(resultCode == RESULT_OK) {

                String str1 = data.getStringExtra("INPUT_NAME");
                name.setText(str1);

                String str2 = data.getStringExtra("INPUT_AGE");
                age.setText(str2);

                String str3 = data.getStringExtra("INPUT_SPECIES");
                species.setText(str3);

                String str4 = data.getStringExtra("INPUT_SEX");
                sex.setText(str4);
            }else if(resultCode == RESULT_CANCELED) {
                // 취소 버튼 클릭
            }
        //}


        switch(requestCode) {
            case REQUEST_TAKE_ALBUM: {
                album = true;
                File albumFile = null;
                try {
                    albumFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(albumFile != null){
                    albumURI = Uri.fromFile(albumFile); // 앨범 이미지 Crop한 결과는 새로운 위치 저장
                }

                photoURI = data.getData(); // 앨범 이미지의 경로

                //iv_capture에 띄우기
                Bitmap image_bitmap = null;
                try {
                    image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), photoURI);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                iv_capture.setImageBitmap(image_bitmap);

            } case REQUEST_TAKE_PHOTO: {
                Log.i("action","REQUEST_TAKE_PHOTO");
                cropImage();
                Log.i("action","cropImage");
                break;
            } case REQUEST_IMAGE_CROP: {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                if(resultCode != RESULT_OK) {
                    return; }

                Bitmap photo = BitmapFactory.decodeFile(photoURI.getPath());
                iv_capture.setImageBitmap(photo);

                Intent mediaScanIntent = new Intent( Intent.ACTION_MEDIA_SCANNER_SCAN_FILE ); // 동기화
                if(album == false) {
                    mediaScanIntent.setData(photoURI); // 동기화
                } else if(album == true){
                    album = false;
                    mediaScanIntent.setData(albumURI); // 동기화
                }
                this.sendBroadcast(mediaScanIntent); // 동기화

                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_choose_image) {
            DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Log.i("button","Takecamera");
                    doTakePhotoAction();
                    Log.i("button","Takecamera2");
                }
            };
            DialogInterface.OnClickListener albumListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    doTakeAlbumAction();
                }
            };

            DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            };

            new AlertDialog.Builder(this)
                    .setTitle("업로드할 이미지 선택")
                    .setPositiveButton("취소", cancelListener)
                    .setNeutralButton("사진촬영", cameraListener)
                    .setNegativeButton("앨범선택", albumListener)
                    .show();
        }

    }

    //Logout function
    private void logout(){
        //Creating an alert dialog to confirm logout
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Are you sure you want to logout?");
        alertDialogBuilder.setPositiveButton("Yes       ",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                        //Getting out sharedpreferences
                        SharedPreferences preferences = getSharedPreferences(SHARED_PREF_NAME,Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();

                        //Puting the value false for loggedin
                        editor.putBoolean(LOGGEDIN_SHARED_PREF, false);

                        //Putting blank value to email
                        editor.putString(EMAIL_SHARED_PREF, "");

                        //Saving the sharedpreferences
                        editor.commit();

                        //Starting login activity
                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);

                    }
                });

        alertDialogBuilder.setNegativeButton("No        ",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {

                    }
                });

        //Showing the alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Adding our menu to toolbar
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
/*            case R.id.action_settings:
                return true;*/

            case R.id.menuLogout :{
                logout();
            }
        }
/*
        if (id == R.id.menuLogout) {
            //calling logout method when the logout button is clicked
            logout();
        }*/
        return super.onOptionsItemSelected(item);
    }


}
