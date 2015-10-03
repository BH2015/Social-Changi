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
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import examples.android.com.socialatchangi.util.FirebaseUtil;

public class ChatViewBroadCastReceiverFragment extends Fragment {
    private static final String TAG = ChatViewBroadCastReceiverFragment.class.getName();
    private TextView mTextView;

    public ChatViewBroadCastReceiverFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FirebaseUtil.writeDataToChild(FirebaseUtil.FirebaseDataTree.GAMES_CATEGORY,
                "HELLO HOW DO U DO");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chat_view_broad_cast_receiver, container,
                false);

        mTextView = (TextView)view.findViewById(R.id.chat_notice);
        FirebaseUtil.addValueEventListener(FirebaseUtil.FirebaseDataTree.GAMES_CATEGORY,
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ChatViewBroadCastReceiverFragment.this.mTextView.setText(
                                (String)dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {
                        Log.e(TAG,firebaseError.getDetails());
                    }
                });
        FirebaseUtil.writeDataToChild(FirebaseUtil.FirebaseDataTree.GAMES_CATEGORY, "WOWO");
        return view;
    }
}
