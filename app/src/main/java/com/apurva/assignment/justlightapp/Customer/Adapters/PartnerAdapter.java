package com.apurva.assignment.justlightapp.Customer.Adapters;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.apurva.assignment.justlightapp.R;
import com.apurva.assignment.justlightapp.Utility.SessionManager;
import com.apurva.assignment.justlightapp.Customer.Data.Partners;
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

import java.util.ArrayList;

/**
 * Created by apurvakumari on 12/4/17.
 */

public class PartnerAdapter extends BaseAdapter
{
    private static ArrayList<Partners> PartnerArray;
    private LayoutInflater mInflater;
    String SolutionID, UserID, partnerID;
    int PartnerID;
    AlertDialogManager alert = new AlertDialogManager();

    public PartnerAdapter(Context context, ArrayList<Partners> results, String solutionID, String userID)
    {
        PartnerArray = results;
        mInflater = LayoutInflater.from(context);
        SolutionID = solutionID;
        UserID = userID;

    }
    @Override
    public int getCount() {
        return PartnerArray.size();
    }

    @Override
    public Object getItem(int position) {
        return PartnerArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.partner_detail_rows, null);
            holder = new ViewHolder();
            holder.Fullname = (TextView) view.findViewById(R.id.Pfullname);
            holder.contact = (TextView) view.findViewById(R.id.Pcontact);
            holder.emailid = (TextView) view.findViewById(R.id.PEmail);
            holder.rating = (TextView) view.findViewById(R.id.Prating);
            holder.requestDemo = (Button)view.findViewById(R.id.RequestAuth);
            holder.order = (Button)view.findViewById(R.id.order);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        //view.setBackground(getContext().getDrawable(R.drawable.border));

        holder.Fullname.setText("Name    : " + PartnerArray.get(position).Fullname);
        holder.contact.setText ("Contact : " +PartnerArray.get(position).contact);
        holder.emailid.setText ("Email   : " +PartnerArray.get(position).emailid);
        holder.rating.setText  ("Rating  : " +PartnerArray.get(position).rating);
        PartnerID = PartnerArray.get(position).ID;
        partnerID = String.valueOf(PartnerID);

        holder.requestDemo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                AsyncTaskRequestDemo asyncTaskRating = new AsyncTaskRequestDemo();
                asyncTaskRating.execute();

            }
        });

        holder.order.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {




            }
        });

        //view.setBackground(getContext().getDrawable(R.drawable.border));
        return view;
    }


    static class ViewHolder
    {

        TextView contact,Fullname,emailid,rating;
        Button requestDemo, order;

    }

    public class AsyncTaskRequestDemo extends AsyncTask<Object, Object, Integer>
    {

        HttpResponse response;
        SessionManager session;
        int AuthID = 0;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Object... params)
        {

            System.out.println("Inside do in background");
            try
            {
                JSONObject requestBody = new JSONObject();
                requestBody.put("customerId", UserID);
                requestBody.put("solutionId",SolutionID);
                requestBody.put("partnerId",String.valueOf(PartnerID));
                requestBody.put("status","Pending");

                String request = requestBody.toString();
                StringEntity request_param = new StringEntity(request);

                System.out.println("Body\n" + request);

                String Url= Constants.BASE_CUSTOMER_URL+Constants.DemoRequest;

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
                        AuthID = j.getInt("AuthorizationRequestId");

                    }
                }
            }
            catch(Exception x)
            {
                throw new RuntimeException("Error from add rating api",x);
            }
            return AuthID;
        }

        @Override
        protected void onPostExecute(Integer R)
        {
            super.onPostExecute(R);

            if(AuthID != 0)
            {
                System.out.println("Auth request sent");
                //alert.showAlertDialog(getContext(), "Success", "Thank you for your feedback!", false);
            }
            else
            {
                System.out.println("Auth request not sent");
                //alert.showAlertDialog(getActivity(), "Unsucessful", "Feedback not Inserted!", false);
            }
        }
    }
}
