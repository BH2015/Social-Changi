package examples.android.com.socialatchangi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.HashMap;
import java.util.Map;

public class ChatRoomActivity extends ActionBarActivity {

    private Toolbar categoriesBar;
    private Button categoryMovies;
    private Button categoryGames;
    private Button categoryGardens;
    private Button categoryShops;
    private Button categoryActive;
    private ChatFragment chatFragmentActive;
    private Map<String, ChatFragment> chatFragmentMap = new HashMap<>();
    private ChatViewBroadCastReceiverFragment broadcastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String categorySelected = intent.getStringExtra("catgeory");
        setContentView(R.layout.activity_chat_room);

        this.categoriesBar = (Toolbar) this.findViewById(R.id.categories_bar);
        this.categoriesBar.setContentInsetsAbsolute(0, 0);
        this.setSupportActionBar(this.categoriesBar);
        this.getSupportActionBar().setDisplayShowHomeEnabled(false);
        this.getSupportActionBar().setDisplayShowCustomEnabled(false);
        this.getSupportActionBar().setDisplayShowTitleEnabled(false);

        this.categoryMovies = (Button) this.categoriesBar.findViewById(R.id.category_movies);
        this.categoryGames = (Button) this.categoriesBar.findViewById(R.id.category_games);
        this.categoryGardens = (Button) this.categoriesBar.findViewById(R.id.category_gardens);
        this.categoryShops = (Button) this.categoriesBar.findViewById(R.id.category_shops);
        if (categorySelected != null) {
            if ("Games".equalsIgnoreCase(categorySelected)) {
                this.categoryActive = this.categoryGames;
            } else if ("Movies".equalsIgnoreCase(categorySelected)) {
                this.categoryActive = this.categoryMovies;
            } else if ("Garden".equalsIgnoreCase(categorySelected)) {
                this.categoryActive = this.categoryGardens;
            } else if ("Shops".equalsIgnoreCase(categorySelected)) {
                this.categoryActive = this.categoryShops;
            }
        }

        this.categoryActive.setSelected(true);

        Log.d("CATEGORY SELECTED ",categorySelected);

        startCategory(categorySelected.toLowerCase());

        Bundle b = new Bundle();
        b.putString("category", "broadcast");
        if (this.broadcastFragment == null) {
            this.broadcastFragment = new ChatViewBroadCastReceiverFragment();
            this.broadcastFragment.setArguments(b);
            getSupportFragmentManager().beginTransaction().add(R.id.broadcast_fragment_container, this.broadcastFragment).commit();
        }
    }

    public void onSwitchCategory(View view) {
        this.categoryActive.setSelected(false);
        this.categoryActive = (Button) view;
        this.categoryActive.setSelected(true);

        int id = view.getId();
        switch (id) {
            case R.id.category_movies:
                startCategory("movies");
                break;
            case R.id.category_games:
                startCategory("games");
                break;
            case R.id.category_gardens:
                startCategory("gardens");
                break;
            case R.id.category_shops:
                startCategory("shops");
                break;
        }
    }

    private void startCategory(String category) {
        if (findViewById(R.id.fragment_container) != null) {


            if (this.chatFragmentActive != null) {
                getSupportFragmentManager().beginTransaction().remove(this.chatFragmentActive).commit();
            }

            this.chatFragmentActive = getChatFragment(category);

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, this.chatFragmentActive).commit();

        }
    }

    private ChatFragment getChatFragment(String category) {
        Bundle b = new Bundle();
        b.putString("category", category);

        if (this.chatFragmentMap.containsKey(category)) {
            return chatFragmentMap.get(category);
        } else {
            ChatFragment frag = new ChatFragment();
            frag.setArguments(b);
            return frag;
        }
    }

}
