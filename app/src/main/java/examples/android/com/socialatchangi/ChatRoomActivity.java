package examples.android.com.socialatchangi;

import android.os.Bundle;
import android.app.Activity;
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

        this.categoryActive = this.categoryMovies;
        this.categoryActive.setSelected(true);
    }

    public void onSwitchCategory(View view) {
        this.categoryActive.setSelected(false);
        this.categoryActive = (Button) view;
        this.categoryActive.setSelected(true);

        int id = view.getId();
        switch (id) {
            case R.id.category_movies:
                break;
            case R.id.category_games:
                break;
            case R.id.category_gardens:
                break;
            case R.id.category_shops:
                break;
        }
    }

}
