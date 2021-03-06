package atchat.aegis.com.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import application.DatabaseHelpers.TagDatabaseHelper;
import application.Users.LoggedInUserContainer;
import atchat.aegis.com.myapplication.BottomNavigation.BottomNavigationMenue;

public class loggedIn extends AppCompatActivity {

    private Button logoutButton;
    private Button goToListFragment;

    public static final String FileSettingsName = "LogInFile";
    private SharedPreferences settings;
    private SharedPreferences.Editor settingsEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);
        LoggedInUserContainer userContainer = LoggedInUserContainer.getInstance();
        if(userContainer == null){
            Log.e("LoggedIn", "UserContainer has become null");
        }
        String message = userContainer.getUser().getAlias();
        TextView text = (TextView) findViewById(R.id.textView);
        text.setText("Hello " + message);
        //Declerations
        logoutButton = (Button) findViewById(R.id.logout);
        goToListFragment = (Button) findViewById(R.id.goToListFragment);

        Context context = this;
        settings = context.getSharedPreferences(FileSettingsName, Context.MODE_PRIVATE);
        final SharedPreferences.Editor settingsEditor = settings.edit();
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout();
            }
        });

        goToListFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToListFragment();
            }
        });
    }


    public void logout(){
        final SharedPreferences.Editor tempEditor = settings.edit();
        tempEditor.putString("loginEmail", null);
        tempEditor.putString("password", null);
        tempEditor.commit();
        new DumpDatabase(this).execute();
        Intent logoutIntent = new Intent(this, MainActivity.class);
        startActivity(logoutIntent);
    }

    public void goToListFragment(){
        Intent intent = new Intent(this, BottomNavigationMenue.class);
        startActivity(intent);
    }

    private class DumpDatabase extends AsyncTask<Void, Void, Void>{

        private Context context;

        public DumpDatabase(Context context){
            this.context = context;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            TagDatabaseHelper tdb = new TagDatabaseHelper(context);
            tdb.dumpTable();
            return null;
        }
    }

}
