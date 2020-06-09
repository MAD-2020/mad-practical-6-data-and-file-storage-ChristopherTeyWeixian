package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {
    EditText Username,Password;
    Button Create,Cancel;
    /* Hint:
        1. This is the create new user page for user to log in
        2. The user can enter - Username and Password
        3. The user create is checked against the database for existence of the user and prompts
           accordingly via Toastbox if user already exists.
        4. For the purpose the practical, successful creation of new account will send the user
           back to the login page and display the "User account created successfully".
           the page remains if the user already exists and "User already exist" toastbox message will appear.
        5. There is an option to cancel. This loads the login user page.
     */


    private static final String FILENAME = "Main2Activity.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Username=findViewById(R.id.Username);
        Password=findViewById(R.id.password);
        Create=findViewById(R.id.Create);
        Cancel=findViewById(R.id.cancel);

        Create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;
                username = Username.getText().toString().trim();
                password = Password.getText().toString().trim();
                if (username.isEmpty()) {
                    Log.v(TAG,"Username Required");
                    Toast.makeText(getApplicationContext(), "Username Required", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (password.isEmpty())//check for empty input
                {
                    Log.v(TAG,"Password Required");
                    Toast.makeText(getApplicationContext(), "Password Required", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Boolean CheckUserNotExisted = Account(username, password);
                    if (CheckUserNotExisted == true){
                        Log.v(TAG, FILENAME + ": New user created successfully!");
                        Toast.makeText(Main2Activity.this, "The account is created successfully.", Toast.LENGTH_SHORT).show();
                    Intent Register = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(Register);
                    finish();
                   }
                    else{
                        Toast.makeText(Main2Activity.this, "User already exist during new user creation!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
        Cancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Log.v(TAG,"Cancelling accounts registration");
                Intent Register=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(Register);
                finish();
            }
        });
        /* Hint:
            This prepares the create and cancel account buttons and interacts with the database to determine
            if the new user created exists already or is new.
            If it exists, information is displayed to notify the user.
            If it does not exist, the user is created in the DB with default data "0" for all levels
            and the login page is loaded.

            Log.v(TAG, FILENAME + ": New user created successfully!");
            Log.v(TAG, FILENAME + ": User already exist during new user creation!");

         */
    }
    public boolean Account(String username, String password){
        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        if (dbHandler.findUser(username) == null){ //check db if user existed or not
            ArrayList<Integer> levels = new ArrayList<>();
            for (int i = 1; i <= 10; i++){
                levels.add(i);
            }
            ArrayList<Integer> scores = new ArrayList<>();
            for (int i = 0; i < 10; i++){
                scores.add(0);
            }
            dbHandler.addUser(new UserData(username, password, levels, scores));
            dbHandler.close();
            return true;
        }
        else{
            dbHandler.close();
            return false;
        }
    }

    protected void onStop() {
        super.onStop();
        finish();
    }
}
