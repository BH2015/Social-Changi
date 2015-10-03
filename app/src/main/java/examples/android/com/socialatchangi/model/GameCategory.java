package examples.android.com.socialatchangi.model;

import examples.android.com.socialatchangi.R;

/**
 * Created by Shekhar on 4/10/2015.
 */
public class GameCategory {

    private final String GAMERS_STICKER = "gamers_sticker";
    private String game_id = "1";
    private String game_name;
    private String meetupTime;
    private String arena;

    public String getArena() {
        return arena;
    }

    public void setArena(String arena) {
        this.arena = arena;
    }

    public GameCategory(){
    }

    public String getGame_id() {
        return game_id;
    }

    public void setGame_id(String game_id) {
        this.game_id = game_id;
    }

    public String getGame_name() {
        return game_name;
    }

    public void setGame_name(String game_name) {
        this.game_name = game_name;
    }

    public String getMeetupTime() {
        return meetupTime;
    }

    public void setMeetupTime(String meetupTime) {
        this.meetupTime = meetupTime;
    }

    public String getGameStickerId() {
        return String.valueOf(GAMERS_STICKER + game_id);
    }
}
