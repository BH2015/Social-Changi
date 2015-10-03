package examples.android.com.socialatchangi.helper;

import android.content.Context;
import android.content.SharedPreferences;

import examples.android.com.socialatchangi.model.Avatar;
import examples.android.com.socialatchangi.model.Person;

public class PreferenceHelper {

    private static final String PERSON_PREFERENCES = "userPreferences";
    private static final String PREFERENCE_PERSON_DISPLAY_NAME = PERSON_PREFERENCES + ".displayname";
    private static final String PREFERENCE_AVATAR = PERSON_PREFERENCES + ".avatar";

    private PreferenceHelper() {
        //no instance
    }


    public static void writeToPreferences(Context context, Person player) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(PREFERENCE_PERSON_DISPLAY_NAME, player.getDisplayName());
        editor.putString(PREFERENCE_AVATAR, player.getAvatar().name());
        editor.apply();
    }

    public static Person getPerson(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        final String firstName = preferences.getString(PREFERENCE_PERSON_DISPLAY_NAME, null);
        final String avatarPreference = preferences.getString(PREFERENCE_AVATAR, null);
        final Avatar avatar;
        if (null != avatarPreference) {
            avatar = Avatar.valueOf(avatarPreference);
        } else {
            avatar = null;
        }

        if (null == firstName && null == avatar) {
            return null;
        }
        return new Person(firstName, avatar);
    }

    public static void signOut(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.remove(PREFERENCE_PERSON_DISPLAY_NAME);
        editor.remove(PREFERENCE_AVATAR);
        editor.apply();
    }

    public static boolean isSignedIn(Context context) {
        final SharedPreferences preferences = getSharedPreferences(context);
        return preferences.contains(PREFERENCE_PERSON_DISPLAY_NAME) &&
                preferences.contains(PREFERENCE_AVATAR);
    }

    private static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences preferences = getSharedPreferences(context);
        return preferences.edit();
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PERSON_PREFERENCES, Context.MODE_PRIVATE);
    }
}
