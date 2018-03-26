package com.apurva.assignment.justlightapp.Customer.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.apurva.assignment.justlightapp.R;
import com.apurva.assignment.justlightapp.Utility.SessionManager;
import com.apurva.assignment.justlightapp.Customer.Data.CustomerProfile;
import com.apurva.assignment.justlightapp.Utility.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

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
    TextView Address;
    TextView pincode;
    TextView work;
    TextView mobile;
    TextView gender;
    TextView registration_id;
    TextView date_of_birth;
    TextView Email;
    TextView joined_date;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SessionManager sessionManager;


    private HomeFragment.OnFragmentInteractionListener mListener;

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
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        final String TAG = "Customer Fragment";
        View v = inflater.inflate(R.layout.fragment_profile, container, false);
        Address= (TextView)v.findViewById(R.id.blood_group);
        pincode= (TextView)v.findViewById(R.id.education);
        work= (TextView)v.findViewById(R.id.occupation);
        mobile= (TextView)v.findViewById(R.id.mobileNumber);
        gender= (TextView)v.findViewById(R.id.gender);
        registration_id= (TextView)v.findViewById(R.id.marriage);
        date_of_birth= (TextView)v.findViewById(R.id.dob);
        Email= (TextView)v.findViewById(R.id.email);
        joined_date= (TextView)v.findViewById(R.id.approved_by);
        //Address.setText("101E San Fernando Street,San Jose");

        sessionManager =new SessionManager(getActivity());
        HashMap<String,String> user = sessionManager.getUserDetails();
        String UserID =user.get(SessionManager.KEY_ID);
        //String UserID="5";
        AsyncTaskProfile asyncTaskProfile = new AsyncTaskProfile();
        asyncTaskProfile.execute();

        return v;
    }
    public class AsyncTaskProfile extends AsyncTask<Object, Object, ArrayList<CustomerProfile>> {

        private final String LOG_TAG = "Customer Fragment";

        private ArrayList<CustomerProfile> getCustomerDetailsFromJson(String appJsonStr) throws JSONException {

            System.out.println("Inside parse function");
            ArrayList<CustomerProfile> customerProfiles = new ArrayList<CustomerProfile>();

            JSONObject jsonObject = new JSONObject(appJsonStr);
            CustomerProfile temp = new CustomerProfile();
            temp.streetAddress = jsonObject.getString("streetAddress");
            temp.contact = jsonObject.getString("contact");
            temp.pincode = jsonObject.getString("pincode");
            temp.workInfo = jsonObject.getString("workInfo");
            temp.fullname = jsonObject.getString("fullname");
            temp.dob =jsonObject.getString("dob");
            temp.emailId =jsonObject.getString("emailId");
            temp.gender =jsonObject.getString("gender");
            temp.joinedDate  =jsonObject.getString("joinedDate");
            temp.registrationId = jsonObject.getString("registrationId");

            customerProfiles.add(temp);

            return customerProfiles;

        }

        @Override
        protected ArrayList<CustomerProfile> doInBackground(Object... params) {


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String custprofileJson = null;

            try {
                String UserID="5";
                String baseUrl = Constants.BASE_USER_URL+ Constants.customerProfile + UserID;

                // Uri patUri = Uri.parse(baseUrl).buildUpon().appendEncodedPath(PATH_PARAM).appendEncodedPath(String.valueOf(Cus_ID)).build();

                URL url = new URL(baseUrl);

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;

                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                custprofileJson = buffer.toString();
                Log.v(LOG_TAG,"PatientListStr: "+custprofileJson);

            } catch (IOException e) {

                Log.e(LOG_TAG, e.getMessage(), e);
                return null;

            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }

            }
            try{
                return getCustomerDetailsFromJson(custprofileJson);
            }catch (JSONException e){
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }

            return null ;
        }


        @Override
        protected void onPostExecute(ArrayList<CustomerProfile> result) {
            super.onPostExecute(result);
            for(CustomerProfile customer : result)
            {

                Log.d("the result is", "Profile data is  " + customer.streetAddress);
                Address.setText(customer.streetAddress);
                pincode.setText(customer.pincode);
                work.setText(customer.workInfo);
                mobile.setText(customer.contact);
                gender.setText(customer.gender);
                registration_id.setText(customer.registrationId);
                date_of_birth.setText(customer.dob);
                Email.setText(customer.emailId);
                joined_date.setText(customer.joinedDate);
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
            mListener = (HomeFragment.OnFragmentInteractionListener) context;
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

