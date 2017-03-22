package com.example.b10715.final_pj;


import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Profile_subActivity extends AppCompatActivity {

    String dbName = "st_file.db";
    int dbVersion = 3;
    private MySQLiteOpenHelper helper;
    private SQLiteDatabase db;
    String tag = "SQLite"; // Log의 tag 로 사용
    public String tableName = "petinfo"; // DB의 table 명

    EditText edit_name;
    EditText edit_age;
    EditText edit_species;
    EditText edit_sex;

    TextView tv;

    Button button_ok, button_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_sub);

        edit_name = (EditText) findViewById(R.id.edit_sub_name);
        edit_age = (EditText) findViewById(R.id.edit_sub_age);
        edit_species = (EditText) findViewById(R.id.edit_sub_species);
        edit_sex = (EditText) findViewById(R.id.edit_sub_sex);

        button_ok = (Button) findViewById(R.id.btn_ok);
        button_cancel = (Button) findViewById(R.id.btn_cancel);
        tv      = (TextView)findViewById(R.id.textView4);

        helper = new MySQLiteOpenHelper(
                Profile_subActivity.this,  // 현재 화면의 제어권자
                dbName,  // 데이터베이스 이름
                null, // 커서팩토리 - null 이면 표준 커서가 사용됨
                dbVersion);  // 데이터베이스 버전

        try {
            db = helper.getWritableDatabase();
        } catch (SQLiteException e) {
            e.printStackTrace();
            Log.e(tag,"데이터 베이스를 열수 없음");
            finish();
        }

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edit_name.getText().toString();
                String age = edit_age.getText().toString();
                String species = edit_species.getText().toString();
                String sex = edit_sex.getText().toString();

                if ("".equals(name)|| "".equals(age)||"".equals(species)||"".equals(sex)){
                    tv.setText("모든 항목을 입력하세요");
                    return;// 그냥 빠져나감
                }
                int a = 0;
                try {
                    a = Integer.parseInt(age);
                } catch (NumberFormatException e) {
                    tv.setText("age는 숫자로 입력하세요");
                    return;
                }
                insert (name, a, species, sex);

                String s = String.valueOf(a); // 강제 형변환, a는 숫자만 들어가게 했는데 profileActivity에서 받을 때 string형으로 받게 해놔서 쓸 수 없으므로 강제 형 변환

                Intent intent = new Intent();
                intent.putExtra("INPUT_NAME", name);
                intent.putExtra("INPUT_AGE", s);
                intent.putExtra("INPUT_SPECIES", species);
                intent.putExtra("INPUT_SEX", sex);

                setResult(Activity.RESULT_OK, intent);

                //finish();
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    void delete(String name) {
        int result = db.delete(tableName, "name=?", new String[] {name});
        Log.d(tag, result + "개 row delete 성공");
        tv.setText(result + "개 row delete 성공");
        select(); // delete 후에 select 하도록
    }

    void select () {
        Cursor c = db.query(tableName, null, null, null, null, null, null);
        while(c.moveToNext()) {
            int _id = c.getInt(0);
            String name = c.getString(1);
            int age = c.getInt(2);
            String species = c.getString(3);
            String sex = c.getString(4);

            Log.d(tag,"_id:"+_id+",name:"+name
                    +",age:"+age+",species:"+species+",sex:"+sex);
            tv.append("\n"+"_id:"+_id+",name:"+name
                    +",age:"+age+",species:"+species+",sex:"+sex);

            // 키보드 내리기
            InputMethodManager imm =
                    (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow
                    (getCurrentFocus().getWindowToken(), 0);
        }

    }

    void insert (String name, int age, String species, String sex) {
        ContentValues values = new ContentValues();
        // 키,값의 쌍으로 데이터 입력
        values.put("name", name);
        values.put("age", age);
        values.put("species", species);
        values.put("sex", sex);

        long result = db.insert(tableName, null, values);
        Log.d(tag, result + "번째 row insert 성공했음");
        tv.setText(result + "번째 row insert 성공했음");
        select(); // insert 후에 select 하도록
    }

}


//http://bitsoul.tistory.com/119 참고 했음.
