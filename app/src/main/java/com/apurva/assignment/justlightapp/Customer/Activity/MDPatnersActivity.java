package com.apurva.assignment.justlightapp.Customer.Activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.apurva.assignment.justlightapp.Customer.Adapters.PartnerAdapter;

import com.apurva.assignment.justlightapp.R;
import com.apurva.assignment.justlightapp.Utility.SessionManager;
import com.apurva.assignment.justlightapp.Customer.Data.Partners;
import com.apurva.assignment.justlightapp.Utility.Constants;

import org.json.JSONArray;
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
 * Created by priya on 12/3/2017.
 */

public class MDPatnersActivity extends Activity
{

    ListView MDPartnerList;
    final String Solution_ID="1";
    String UserID;
    SessionManager sessionManager;

    SessionManager session;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partner_list_view);

        sessionManager = new SessionManager(getApplicationContext());
        HashMap<String,String> user = sessionManager.getUserDetails();
        UserID =user.get(SessionManager.KEY_ID);

        MDPatnersActivity.AsyncTaskMDpatners asyncTaskMDpatners = new MDPatnersActivity.AsyncTaskMDpatners();
        asyncTaskMDpatners.execute();
    }
    public class AsyncTaskMDpatners extends AsyncTask<Object, Object, ArrayList<Partners>>{

        private final String LOG_TAG = "Smart Lighting Activity";

        private ArrayList<Partners> getMDPatnersDetailsFromJson(String appJsonStr) throws JSONException {

            System.out.println("Inside parse function");
            ArrayList<Partners> MDPartners = new ArrayList<Partners>();
            JSONArray jsonArray = new JSONArray(appJsonStr);

            for(int i=0; i<jsonArray.length();i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Partners temp = new Partners();
                temp.Fullname = jsonObject.getString("fullname");
                temp.contact = jsonObject.getString("contact");
                temp.WorkInfo = jsonObject.getString("workInfo");
                temp.emailid = jsonObject.getString("emailId");
                temp.rating = jsonObject.getInt("rating");
                temp.ID = jsonObject.getInt("userId");
                MDPartners.add(temp);
                System.out.println(temp.Fullname);

            }

            return MDPartners;

        }


        @Override
        protected ArrayList<Partners> doInBackground(Object... params) {


            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String MDPatnerInfoJson= null;


            try {
                String baseUrl = Constants.BASE_PATNERS_URL+ Constants.PatnerInfo + Solution_ID;
                //final String PATH_PARAM = Constants. PatnerInfo;

               // Uri patUri = Uri.parse(baseUrl).buildUpon().appendEncodedPath(PATH_PARAM).appendEncodedPath(String.valueOf(Solution_ID)).build();

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

                MDPatnerInfoJson = buffer.toString();
                //System.out.println(MDPatnerInfoJson);
                //Log.v(LOG_TAG, "PatientListStr: " + MDPatnerInfoJson);

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
                return getMDPatnersDetailsFromJson(MDPatnerInfoJson);
            }catch (JSONException e){
                Log.e(LOG_TAG,e.getMessage(),e);
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(ArrayList<Partners> result)
        {
            super.onPostExecute(result);

            MDPartnerList = (ListView) findViewById(R.id.PartnerListView);
            MDPartnerList.setAdapter(new PartnerAdapter(MDPatnersActivity.this,result,Solution_ID,UserID));


        }


    }
}



