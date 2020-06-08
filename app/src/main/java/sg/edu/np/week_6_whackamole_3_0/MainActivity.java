package sg.edu.np.week_6_whackamole_3_0;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText Username,Password;
    TextView register;
    Button Login;

    /*
        1. This is the main page for user to log in
        2. The user can enter - Username and Password
        3. The user login is checked against the database for existence of the user and prompts
           accordingly via Toastbox if user does not exist. This loads the level selection page.
        4. There is an option to create a new user account. This loads the create user page.
     */
    private static final String FILENAME = "MainActivity.java";
    private static final String TAG = "Whack-A-Mole3.0!";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    Username=findViewById(R.id.Username);
    Password=findViewById(R.id.password);
    Login=findViewById(R.id.login);
    register=findViewById(R.id.Register);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username, password;
                username = Username.getText().toString().trim();
                password = Password.getText().toString().trim();
                Log.v(TAG, FILENAME + ": Logging in with: " + username+ ": " + password);
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
                if(isValidUser(username, password)==false)
                {
                    Toast.makeText(getApplicationContext(),"Login failed",Toast.LENGTH_LONG).show();
                    Log.v(TAG, FILENAME + ": Invalid user!");
                }
                else {
                    Log.v(TAG, FILENAME + ": Valid User! Logging in");
                    Toast.makeText(getApplicationContext(),"Login successful",Toast.LENGTH_LONG).show();
                    Intent in = new Intent(getApplicationContext(), Main3Activity.class);
                    in.putExtra("Username", username);
                    startActivity(in);
                }

            }

        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v(TAG, FILENAME + ": Create new user!");
                Intent in = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(in);
            }
        });

        /* Hint:
            This method creates the necessary login inputs and the new user creation ontouch.
            It also does the checks on button selected.
            Log.v(TAG, FILENAME + ": Create new user!");
            Log.v(TAG, FILENAME + ": Logging in with: " + etUsername.getText().toString() + ": " + etPassword.getText().toString());
            Log.v(TAG, FILENAME + ": Valid User! Logging in");
            Log.v(TAG, FILENAME + ": Invalid user!");

        */


    }

    protected void onStop(){
        super.onStop();
        finish();
    }

    public boolean isValidUser(String Name, String password){

        MyDBHandler dbHandler = new MyDBHandler(this, null, null, 1);
        UserData userData = dbHandler.findUser(Name);
        if (userData !=  null)
        {
            Log.v(TAG, FILENAME + ": Running Checks..." + userData.getMyUserName() + ": " + userData.getMyPassword() +" <--> "+ Name + " " + password);
            if (userData.getMyUserName().equals(Name) && userData.getMyPassword().equals(password))
            {
                Log.v(TAG, FILENAME + "User has pass validation" );
                return true;
            }
            else{
                Log.v(TAG, FILENAME + "User has failed validation");
            }
        }
        return false;
    }
}
