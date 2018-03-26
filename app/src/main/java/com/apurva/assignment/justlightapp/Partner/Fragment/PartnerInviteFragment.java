package com.apurva.assignment.justlightapp.Partner.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.apurva.assignment.justlightapp.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PartnerInviteFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PartnerInviteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PartnerInviteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PartnerInviteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PartnerInviteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PartnerInviteFragment newInstance(String param1, String param2) {
        PartnerInviteFragment fragment = new PartnerInviteFragment();
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
        View v = inflater.inflate(R.layout.fragment_partner_invite,container, false);

        Button btn_email = (Button) v.findViewById(R.id.btn_partnr_gmail);
        btn_email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_EMAIL, "contactus@justlight.com");
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, "Promotion of Just Light Technologies");
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Empower your business with Green Building Energy Solution.");
                sendIntent.setType("*/*");
                //getActivity().startActivity(sendIntent);
                getActivity().startActivity(sendIntent);

            }
        });

        Button btn_message = (Button)v.findViewById(R.id.btn_partnr_message);

        btn_message.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Empower your business with Green Building Energy Solution.");
                sendIntent.setType("text/plain");
                getActivity().startActivity(sendIntent);
            }
        });

        Button btn_tweet = (Button)v.findViewById(R.id.btn_partnr_twitter);

        btn_tweet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Empower your business with Green Building Energy Solution.");
                sendIntent.setType("text/plain");
                getActivity().startActivity(sendIntent);

            }
        });

        Button btn_facebook = (Button)v.findViewById(R.id.btn_partnr_facebook);

        btn_facebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey! Empower your business with Green Building Energy Solution.");
                sendIntent.setType("text/plain");
                getActivity().startActivity(sendIntent);
            }
        });


        return inflater.inflate(R.layout.fragment_partner_invite, container, false);
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
}
