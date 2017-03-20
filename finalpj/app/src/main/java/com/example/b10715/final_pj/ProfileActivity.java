package com.example.b10715.final_pj;

import android.app.Activity;
        import android.content.Context;
        import android.content.DialogInterface;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Bitmap;
        import android.graphics.Canvas;
        import android.graphics.Paint;
        import android.graphics.PorterDuff;
        import android.graphics.PorterDuffXfermode;
        import android.graphics.Rect;
        import android.graphics.drawable.Drawable;
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
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.EditText;
        import android.widget.FrameLayout;
        import android.widget.ListView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.TabHost;
        import android.widget.TextView;
        import android.view.View.OnClickListener;
        import android.widget.Toast;
        import android.graphics.Bitmap.Config;

        import static com.example.b10715.final_pj.Config.EMAIL_SHARED_PREF;
        import static com.example.b10715.final_pj.Config.LOGGEDIN_SHARED_PREF;
        import static com.example.b10715.final_pj.Config.SHARED_PREF_NAME;

public class ProfileActivity extends AppCompatActivity {
    DrawerLayout mDrawerLayout;
//    TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

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
                    case R.id.navigation_item_feed:
                        Intent intent3 = new Intent(ProfileActivity.this, FeedActivity.class);
                        startActivity(intent3);
                        break;
                }
                return true;
            }
        });

        LoginActivity loginActivity = new LoginActivity();

        View header = navigationView.getHeaderView(0);
        TextView header_email = (TextView)header.findViewById(R.id.header_email);
        header_email.setText(loginActivity.call_email);

    }

/*
    public Bitmap getCircleBitmap(Bitmap bitmap) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        int size = (bitmap.getWidth()/2);
        canvas.drawCircle(size, size, size, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);
        return output;
    }
*/




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
