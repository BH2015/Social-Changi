package examples.android.com.socialatchangi;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import examples.android.com.socialatchangi.model.GameCategory;
import examples.android.com.socialatchangi.util.FirebaseUtil;

public class ChatViewBroadCastReceiverFragment extends Fragment {
    private static final String TAG = ChatViewBroadCastReceiverFragment.class.getName();
    private TextView mGameName;
    private TextView mArena;
    private TextView mStartTimr;
    private TextView mLoc;
    private ImageView mImg;

    public ChatViewBroadCastReceiverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_view_broad_cast_receiver, container,
                false);

        mGameName = (TextView)view.findViewById(R.id.game_name);
        mArena = (TextView)view.findViewById(R.id.arena);
        mStartTimr = (TextView)view.findViewById(R.id.startTime);
        mLoc = (TextView)view.findViewById(R.id.arena_location);

        mImg = (ImageView)view.findViewById(R.id.sticker_id);
        FirebaseUtil.addValueEventListener(FirebaseUtil.FirebaseDataTree.GAMES_CATEGORY,
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Map<String,Map<String,String>> categoryMap
                                = new HashMap<String, Map<String, String>>();
                        categoryMap = (HashMap<String,Map<String,String>>)dataSnapshot.getValue();
                        String meetupTime = categoryMap.get(FirebaseUtil.FirebaseDataTree
                                .GAMES_CATEGORY.getSubCategory()).get("meetupTime");
                        String game_name = categoryMap.get(FirebaseUtil.FirebaseDataTree
                                .GAMES_CATEGORY.getSubCategory()).get("game_name");
                        String game_id = categoryMap.get(FirebaseUtil.FirebaseDataTree
                                .GAMES_CATEGORY.getSubCategory()).get("game_id");
                        String arena = categoryMap.get(FirebaseUtil.FirebaseDataTree
                                .GAMES_CATEGORY.getSubCategory()).get("arena");
                        String gameStickerId = categoryMap.get(FirebaseUtil.FirebaseDataTree
                                .GAMES_CATEGORY.getSubCategory()).get("gameStickerId");
                        String loc = categoryMap.get(FirebaseUtil.FirebaseDataTree
                                .GAMES_CATEGORY.getSubCategory()).get("location");

                        int resId = ChatViewBroadCastReceiverFragment.this.getResources()
                                .getIdentifier(gameStickerId, "drawable",
                                        ChatViewBroadCastReceiverFragment.this.getActivity()
                                                .getApplicationContext().getPackageName());
                        mGameName.setText(game_name);
                        mArena.setText(arena);
                        mStartTimr.setText(meetupTime);
                        mLoc.setText(loc);
                        mImg.setImageResource(resId);
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Log.e(TAG, firebaseError.getDetails());
                    }
                });
        FirebaseUtil.writeDataToChild(FirebaseUtil.FirebaseDataTree.GAMES_CATEGORY, getRandomGameMessage());
        return view;
    }

    public GameCategory getRandomGameMessage() {
        GameCategory game = new GameCategory();
        game.setGame_id(String.valueOf((int) Math.round(Math.random() * 5)));
        game.setGame_name("GOD OF WAR" + game.getGame_id());
        game.setArena("MULTIUSER");
        game.setLocation("T2, GamingZone, L2");
        Date date = new Date();
        game.setMeetupTime(date.toString());
        return game;
    }
}
