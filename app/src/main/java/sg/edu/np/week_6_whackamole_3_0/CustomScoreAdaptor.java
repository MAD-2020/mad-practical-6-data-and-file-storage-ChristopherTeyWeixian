package sg.edu.np.week_6_whackamole_3_0;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class CustomScoreAdaptor extends RecyclerView.Adapter<CustomScoreViewHolder> {
    /* Hint:
        1. This is the custom adaptor for the recyclerView list @ levels selection page

     */
    ArrayList<Integer> levellist;
    ArrayList<Integer> scorelist;
    String Name;


    private static final String FILENAME = "CustomScoreAdaptor.java";
    private static final String TAG = "Whack-A-Mole3.0!";

    public CustomScoreAdaptor(UserData userdata){
        /* Hint:
        This method takes in the data and readies it for processing.
         */
        levellist=userdata.getLevels();
        scorelist=userdata.getScores();
        Name=userdata.getMyUserName();
    }

    public CustomScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        /* Hint:
        This method dictates how the viewholder layuout is to be once the viewholder is created.
         */
        //Load view layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.level_select,parent,false);

        return new CustomScoreViewHolder(view);
    }


    public void onBindViewHolder(CustomScoreViewHolder holder, final int position){

        /* Hint:
        This method passes the data to the viewholder upon bounded to the viewholder.
        It may also be used to do an onclick listener here to activate upon user level selections.

        Log.v(TAG, FILENAME + " Showing level " + level_list.get(position) + " with highest score: " + score_list.get(position));
        Log.v(TAG, FILENAME+ ": Load level " + position +" for: " + list_members.getMyUserName());
         */
        //display the view
        int level = levellist.get(position);
        holder.leveltext.setText("Level " + level);

        int score = scorelist.get(position);
        holder.scoretext.setText("Highest Score: "+score);

        Log.v(TAG, FILENAME + " Showing level " + levellist.get(position) + " with highest score: " + scorelist.get(position));
        //What happened if user click on the level
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Main4Activity.class);
                intent.putExtra("Username",Name);
                intent.putExtra("Level", String.valueOf(levellist.get(position)));
                Log.v(TAG, FILENAME+ ": Load level " + position +" for: " + Name);
                v.getContext().startActivity(intent);
            }
        });
    }

    public int getItemCount(){
        /* Hint:
        This method returns the the size of the overall data.
         */
        return levellist.size();
    }
}