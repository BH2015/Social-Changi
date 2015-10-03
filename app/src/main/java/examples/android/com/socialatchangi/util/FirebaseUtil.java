package examples.android.com.socialatchangi.util;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

/**
 * Created by Shekhar on 4/10/2015.
 */
public final class FirebaseUtil {

    private static final String SHINE_OUR_APP_FIREBASE = "https://shining-fire-1921.firebaseio.com/";
    private static final Firebase FIREBASE_REF = new Firebase(SHINE_OUR_APP_FIREBASE);

    public enum FirebaseDataTree {
        GAMES_CATEGORY("game");

        private String subCategory;

        private FirebaseDataTree(String subCategory) {
            this.subCategory = subCategory;
        }

        public String getSubCategory(){
            return this.subCategory;
        }
    };

    private FirebaseUtil() {
       //No op
    }
    public static void addValueEventListener(FirebaseDataTree treename,
                                             final ValueEventListener valueEventListener) {
        FIREBASE_REF.child(treename.name()).addValueEventListener(valueEventListener);
    }

    public static void writeDataToChild(FirebaseDataTree treename,Object value) {
        FIREBASE_REF.child(treename.name()).child(treename.getSubCategory()).setValue(value);
    }


}
