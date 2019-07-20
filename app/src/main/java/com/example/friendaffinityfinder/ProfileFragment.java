package com.example.friendaffinityfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.facebook.login.widget.ProfilePictureView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private CardView profilecard;
    private TwitterLoginButton twitterLoginButton;
    private SharedPreferences sharedPreferences;
    private ImageView add_account;
    MainActivity mainActivity;
    private TextView name_t,gender_t,birthday_t,email_t,location_t;
//    private ShimmerFrameLayout shimmerFrameLayout;
    private ProfilePictureView profilePictureView;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        //Initialising twitter login
        TwitterConfig config = new TwitterConfig.Builder(getActivity())
                .logger(new DefaultLogger(Log.DEBUG))//enable logging when app is in debug mode
                .twitterAuthConfig(new TwitterAuthConfig(getResources().getString(R.string.consumer_key), getResources().getString(R.string.consumer_secret_key)))//pass the created app Consumer KEY and Secret also called API Key and Secret
                .debug(true)//enable debug mode
                .build();

        //finally initialize twitter with created configs
        Twitter.initialize(config);
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        profilecard = v.findViewById(R.id.profilecard);
        profilecard.setBackgroundResource(R.drawable.heading_tags);
//        add_account = v.findViewById(R.id.add_account);
//        add_account.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                bottomActivity(view);
//            }
//        });
        //ShimmerEffect
//        shimmerFrameLayout = v.findViewById(R.id.shimmer_view_container);
//        shimmerFrameLayout.setVisibility(View.VISIBLE);
//        shimmerFrameLayout.startShimmerAnimation();


        sharedPreferences = getActivity().getSharedPreferences("myPref",MODE_PRIVATE);

        String userid = sharedPreferences.getString("userid","");
        String username = sharedPreferences.getString("username","");
        String gender = sharedPreferences.getString("gender","");
        String birthday = sharedPreferences.getString("birthday","");
        String location = sharedPreferences.getString("city","");
        String email = sharedPreferences.getString("email","");

        profilePictureView =  v.findViewById(R.id.friendProfilePicture);
        profilePictureView.setProfileId(userid);








//        name_t = v.findViewById(R.id.name_txt);
//        gender_t = v.findViewById(R.id.gender_txt);
//        birthday_t = v.findViewById(R.id.birthday_txt);
//        location_t = v.findViewById(R.id.location_txt);
//        email_t = v.findViewById(R.id.email_txt);
//
//        name_t.setText(username);
//        gender_t.setText(gender);
//        birthday_t.setText(birthday);
//        email_t.setText(email);
//        location_t.setText(location);
        Log.d("profile","profile"+profilePictureView.isShown());
            if (profilePictureView.isShown()){
//                shimmerFrameLayout.setVisibility(View.GONE);
                Log.d("profile","profile"+profilePictureView.isShown());
            }

        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("entering","entering");
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
//        if (profilePictureView.isShown()){
//            shimmerFrameLayout.setVisibility(View.GONE);
//        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    //Bottom Activity

    private void bottomActivity(final View view1) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

        twitterLoginButton =  view.findViewById(R.id.default_twitter_login_button);

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // Do something with result, which provides a TwitterSession for making API calls
                Log.d("twitterresponse",result.data.getUserName());
                bottomSheetDialog.dismiss();

            }

            @Override
            public void failure(TwitterException exception) {
                // Do something on failure
                Log.d("twitterfailure",exception.toString());

            }
        });



    }
}
