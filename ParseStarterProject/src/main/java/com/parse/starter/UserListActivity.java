package com.parse.starter;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.ToolbarWidgetWrapper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class UserListActivity extends AppCompatActivity {
    ArrayList<String>users;
    ArrayAdapter arrayAdapter;
    Button logOutBtn;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        users = new ArrayList<String>();
        setTitle("User List");
        ListView userListView = (ListView) findViewById(R.id.userListView);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), ChatActivity.class);
                intent.putExtra("username", users.get(i));
                startActivity(intent);
            }
        });
        users.clear();
        arrayAdapter  = new ArrayAdapter(this, android.R.layout.simple_list_item_1, users);
        /*logOutBtn = (Button)findViewById(R.id.Logout_button);
        logOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToMain = new Intent(getApplicationContext(),MainActivity.class);
                ParseUser.logOut();
                startActivity(goToMain);
            }
        });*/
        userListView.setAdapter(arrayAdapter);
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if(e==null){
                    if(objects.size() > 0){
                        for(ParseUser user:objects){
                            users.add(user.getUsername());
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                }
            }
        });


        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarUL);
        //setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.popup_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()){
            case R.id.settings:
                Toast.makeText(this, "Item 1 clicked", Toast.LENGTH_SHORT);
                return true;

            case R.id.logoutPopup:
                logOutNow();
                return true;
            default: return false;
        }
    }


    public void logOutNow (){
        Intent goToMain = new Intent(getApplicationContext(),MainActivity.class);
        ParseUser.logOut();
        startActivity(goToMain);
    }
}
