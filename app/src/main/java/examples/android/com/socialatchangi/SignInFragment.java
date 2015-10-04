package examples.android.com.socialatchangi;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;

import examples.android.com.socialatchangi.adapter.AvatarAdapter;
import examples.android.com.socialatchangi.helper.PreferenceHelper;
import examples.android.com.socialatchangi.model.Avatar;
import examples.android.com.socialatchangi.model.Person;
import examples.android.com.socialatchangi.widget.DoneFab;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class SignInFragment extends Fragment {


    private static final String EXTRA_PERSON = "person";
    private OnFragmentInteractionListener mListener;
    private GridView mAvatarGrid;
    private View mSelectedAvatarView;
    private DoneFab mDoneFab;

    private static final String KEY_SELECTED_AVATAR_INDEX = "selectedAvatarIndex";
    private Person mPerson;
    private EditText mFirstName;
    private Avatar mSelectedAvatar = Avatar.ONE;
    private boolean edit;
    private TextWatcher textWatcher;

    public static SignInFragment newInstance() {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SignInFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        view.addOnLayoutChangeListener(new View.
                OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom,
                                       int oldLeft, int oldTop, int oldRight, int oldBottom) {
                v.removeOnLayoutChangeListener(this);
                setUpGridView(getView());
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        ImageView img = (ImageView)getActivity().findViewById(R.id.splashscreen);
        if (img != null) {
            img.setVisibility(View.GONE);
        }

        assurePlayerInit();

        if (null == mPerson) {
            view.findViewById(R.id.empty).setVisibility(View.GONE);
            view.findViewById(R.id.content).setVisibility(View.VISIBLE);
            initContentViews(view);
            initContents();
        } else {
            final Activity activity = getActivity();
            //CategorySelectionActivity.start(activity, mPlayer);
            activity.finish();
        }
        if (mPerson != null && mFirstName != null && mFirstName.getText() != null
                && !mFirstName.getText().toString().isEmpty()) {
            mDoneFab.setVisibility(View.VISIBLE);
        }

        super.onViewCreated(view, savedInstanceState);
    }


    private void setUpGridView(View container) {
        mAvatarGrid = (GridView) container.findViewById(R.id.avatars);
        mAvatarGrid.setAdapter(new AvatarAdapter(getActivity()));
        mAvatarGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedAvatarView = view;
                mSelectedAvatar = Avatar.values()[position];
            }
        });
        mAvatarGrid.setNumColumns(calculateSpanCount());
        mAvatarGrid.setItemChecked(mSelectedAvatar.ordinal(), true);
    }

    private void initContents() {
        assurePlayerInit();
        if (null != mPerson) {
            mFirstName.setText(mPerson.getDisplayName());
            mSelectedAvatar = mPerson.getAvatar();
        }
    }

    private int calculateSpanCount() {
        int avatarSize = getResources().getDimensionPixelSize(R.dimen.size_fab);
        int avatarPadding = getResources().getDimensionPixelSize(R.dimen.spacing_double);
        return mAvatarGrid.getWidth() / (avatarSize + avatarPadding);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mFirstName.removeTextChangedListener(textWatcher);
    }

    @Override
    public void onResume() {
        super.onResume();
        mFirstName.setText("");
        mFirstName.addTextChangedListener(textWatcher);
        mDoneFab.setVisibility(View.GONE);
    }

    private void initContentViews(View view) {
        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                /* no-op */
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mDoneFab.setVisibility(View.GONE);
                } else {
                    mDoneFab.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                /* no-op */
            }
        };

        mFirstName = (EditText) view.findViewById(R.id.first_name);
        mFirstName.addTextChangedListener(textWatcher);

        mDoneFab = (DoneFab) view.findViewById(R.id.done);
        mDoneFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.done:
                        savePerson(getActivity());
                        removeDoneFab(new Runnable() {
                            @Override
                            public void run() {
                                if (null == mSelectedAvatarView) {
                                    performSignInWithTransition(mAvatarGrid.getChildAt(
                                            mSelectedAvatar.ordinal()));
                                } else {
                                    performSignInWithTransition(mSelectedAvatarView);
                                }
                            }
                        });
                        break;
                    default:
                        throw new UnsupportedOperationException(
                                "The onClick method has not been implemented for " + getResources()
                                        .getResourceEntryName(v.getId()));
                }
            }
        });
    }

    private void savePerson(Activity activity) {
        mPerson = new Person(mFirstName.getText().toString(), mSelectedAvatar);
        PreferenceHelper.writeToPreferences(activity, mPerson);
    }

    private void performSignInWithTransition(View v) {
        final Activity activity = getActivity();
//
//        final Pair[] pairs = new Pair[]{
//                new Pair<>(v, activity.getString(R.string.transition_avatar))};
//        ActivityOptions activityOptions = ActivityOptions
//                .makeSceneTransitionAnimation(activity, pairs);
        Intent starter = new Intent(activity, ChatRoomActivity.class);
        starter.putExtra(EXTRA_PERSON, mPerson);
//        activity.startActivity(starter, activityOptions.toBundle());
        activity.startActivity(starter);
        //CategorySelectionActivity.start(activity, mPerson, activityOptions);
    }

    private void removeDoneFab(@Nullable Runnable endAction) {
        mDoneFab.animate()
                .scaleX(0)
                .scaleY(0)
                .setInterpolator(new FastOutSlowInInterpolator())
                .withEndAction(endAction)
                .start();
    }

    private void assurePlayerInit() {
        PreferenceHelper.clearPerson(getActivity());
        if (null == mPerson) {
            mPerson = PreferenceHelper.getPerson(getActivity());
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onFragmentInteraction(String id);
    }

}
