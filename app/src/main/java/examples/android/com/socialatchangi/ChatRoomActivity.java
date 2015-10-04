package examples.android.com.socialatchangi;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class ChatRoomActivity extends ActionBarActivity {

    private Toolbar categoriesBar;
    private Button categoryMovies;
    private Button categoryGames;
    private Button categoryGardens;
    private Button categoryShops;
    private Button categoryActive;
    private ChatFragment chatFragmentActive;
    private ChatViewBroadCastReceiverFragment broadcastFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        this.categoryActive = this.categoryGames;
        this.categoryActive.setSelected(true);
        startCategory("games");
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

            Bundle b = new Bundle();
            b.putString("category", category);

            ChatFragment chatFragment = new ChatFragment();
            chatFragment.setArguments(b);
            this.chatFragmentActive = chatFragment;

            getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, this.chatFragmentActive).commit();

            ChatViewBroadCastReceiverFragment broadcastFragment = new ChatViewBroadCastReceiverFragment();
            broadcastFragment.setArguments(b);
            this.broadcastFragment = broadcastFragment;

            getSupportFragmentManager().beginTransaction().add(R.id.broadcast_fragment_container, this.broadcastFragment).commit();
        }
    }

}
