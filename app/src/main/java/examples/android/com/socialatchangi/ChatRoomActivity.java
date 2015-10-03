package examples.android.com.socialatchangi;

import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

public class ChatRoomActivity extends ActionBarActivity {

    private Toolbar categoriesBar;

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
    }

}
