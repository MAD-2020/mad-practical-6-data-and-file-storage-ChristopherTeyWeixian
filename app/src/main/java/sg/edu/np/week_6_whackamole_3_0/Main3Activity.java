package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Main3Activity extends AppCompatActivity {
    Button gobacklogin;
    RecyclerView recyclerView;
    ArrayList<String> levelList;
    ArrayList<String> highestScoreList;
    String userName;
    UserData userData;
    CustomScoreAdaptor customScoreAdaptor;
    /* Hint:
        1. This displays the available levels from 1 to 10 to the user.
        2. The different levels makes use of the recyclerView and displays the highest score
           that corresponds to the different levels.
        3. Selection of the levels will load relevant Whack-A-Mole game.
        4. The levels are with the following difficulties.
            a. Level 1 will have a new mole at each 10000ms.
            b. Each level up will shorten the time to next mole by 100ms with level 10 as 1000 second per mole.
            c. For level 1 ~ 5, there is only 1 mole.
            d. For level 6 ~ 10, there are 2 moles.
            e. Each location of the mole is randomised.
        5. There is an option return to the login page.
     */
    private static final String FILENAME = "Main3Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        /* Hint:
        This method receives the username account data and looks up the database for find the
        corresponding information to display in the recyclerView for the level selections page.

        Log.v(TAG, FILENAME + ": Show level for User: "+ userName);
         */
        //get user
        Intent recevingEnd = getIntent();
        userName = recevingEnd.getStringExtra("Username");
        Log.v(TAG, FILENAME + ": Show level for User: " + userName);

        recyclerView = findViewById(R.id.recyclerView);
        gobacklogin=findViewById(R.id.gobackloginpage);
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);

        UserData user = dbHandler.findUser(userName);
        userData = dbHandler.findUser(userName);
        dbHandler.close();

        customScoreAdaptor = new CustomScoreAdaptor(user);
        recyclerView.setAdapter(customScoreAdaptor);
        //Layout
        LinearLayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //if user click on the go back login button
        gobacklogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(in);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
