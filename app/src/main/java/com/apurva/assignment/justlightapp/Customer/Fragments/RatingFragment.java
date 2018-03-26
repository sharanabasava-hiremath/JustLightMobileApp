package com.apurva.assignment.justlightapp.Customer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import com.apurva.assignment.justlightapp.R;
import com.apurva.assignment.justlightapp.Utility.SessionManager;
import com.apurva.assignment.justlightapp.Customer.Data.RatingData;
import com.apurva.assignment.justlightapp.Utility.AlertDialogManager;
import com.apurva.assignment.justlightapp.Utility.Constants;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RatingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RatingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RatingFragment extends Fragment
{
    String ratingStr;
    String commentStr;
    AlertDialogManager alert = new AlertDialogManager();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public RatingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RatingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RatingFragment newInstance(String param1, String param2) {
        RatingFragment fragment = new RatingFragment();
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
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_rating, container, false);
        final EditText comment = (EditText)v.findViewById(R.id.input_rating);


        RatingBar ratingBar = (RatingBar)v.findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                ratingStr = String.valueOf(((int)rating));

            }
        });

        Button submit = (Button)v.findViewById(R.id.btn_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                commentStr = comment.getText().toString();
                System.out.println(commentStr);
                System.out.println(ratingStr);
                if(commentStr.equals(null))
                {
                    commentStr = "Good Application";
                }
                RatingData R = new RatingData();
                R.comment = commentStr;
                R.rate = ratingStr;
                AsyncTaskSubmitRating asyncTaskRating = new AsyncTaskSubmitRating();
                asyncTaskRating.execute(R);

            }
        });
        return v;
    }


    public class AsyncTaskSubmitRating extends AsyncTask<RatingData, Object, RatingData>
    {
        HttpResponse response;
        SessionManager session;
        int feedbackID;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected RatingData doInBackground(RatingData... params)
        {

            System.out.println("Inside do in background");
            session = new SessionManager(getActivity());
            HashMap<String,String> user = session.getUserDetails();
            String UserID =user.get(SessionManager.KEY_ID);
            try
            {
                JSONObject requestBody = new JSONObject();
                requestBody.put("userId", UserID);
                requestBody.put("comment",params[0].comment);
                requestBody.put("rating",params[0].rate);

                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);

                System.out.println("Body\n" + request);

                String Url= Constants.BASE_USER_URL+Constants.Rating;

                System.out.println(Url);
                HttpPost post = new HttpPost(Url);
                post.setHeader("Content-Type","application/json");
                post.setEntity(request_param);
                HttpClient httpClient = new DefaultHttpClient();
                response = httpClient.execute(post);

                System.out.println("Reached after coming back from Backend API");



                if (response.getStatusLine().getStatusCode() != 200)
                {
                    if(response.getStatusLine().getStatusCode() == 401)
                    {
                        System.out.println("Verification failed 401");
                    }
                    else if(response.getStatusLine().getStatusCode() == 400)
                    {
                        System.out.println("Verification failed 400");
                    }
                    else
                        throw new RuntimeException("Failed: HTTP error code :" + response.getStatusLine().getStatusCode());
                }
                else
                {
                    HttpEntity e = response.getEntity();
                    String i = EntityUtils.toString(e);
                    JSONObject j = new JSONObject(i);
                    if(!i.equals("Verification Failed"))
                    {
                        //int userID = Integer.parseInt(i);
                        System.out.println(i ); //+ j.getInt("Username and password entered is correct for userId:"));
                        feedbackID = j.getInt("User Feedback inserted successfully: ");
                        params[0].feedbackId = feedbackID;
                    }
                }
            }
            catch(Exception x)
            {
                throw new RuntimeException("Error from add rating api",x);
            }
            return params[0];
        }

        @Override
        protected void onPostExecute(RatingData R)
        {
            super.onPostExecute(R);

            if(R.feedbackId != 0)
            {
                alert.showAlertDialog(getActivity(), "Success", "Thank you for your feedback!", false);
            }
            else
            {
                alert.showAlertDialog(getActivity(), "Unsucessful", "Feedback not Inserted!", false);
            }
        }
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
