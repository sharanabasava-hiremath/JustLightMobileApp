package com.apurva.assignment.justlightapp.Customer.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.apurva.assignment.justlightapp.R;
import com.apurva.assignment.justlightapp.Customer.Data.AuthRequestCustomer;

import java.util.ArrayList;

/**
 * Created by apurvakumari on 12/4/17.
 */

public class AuthRequestAdapter extends BaseAdapter
{
    private static ArrayList<AuthRequestCustomer> AuthRequestArray;
    private LayoutInflater mInflater;

    public AuthRequestAdapter(Context context, ArrayList<AuthRequestCustomer> results) {
        AuthRequestArray = results;
        mInflater = LayoutInflater.from(context);

    }
    @Override
    public int getCount() {
        return AuthRequestArray.size();
    }

    @Override
    public Object getItem(int position) {
        return AuthRequestArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        ViewHolder holder;
        if (view == null) {
            view = mInflater.inflate(R.layout.auth_request_list_customer, null);
            holder = new ViewHolder();
            holder.status = (TextView) view.findViewById(R.id.ReqSolutionTitle);
            holder.partner = (TextView) view.findViewById(R.id.ReqPartnerTitle);
            holder.solution = (TextView) view.findViewById(R.id.ReqTitle);

            view.setTag(holder);
        } else
            {
            holder = (ViewHolder) view.getTag();
        }

        holder.solution.setText("Solution : " + AuthRequestArray.get(position).solution);
        holder.partner.setText ("Partner  : " +AuthRequestArray.get(position).partner);
        holder.status.setText  ("Status    : " +AuthRequestArray.get(position).status);

        return view;
    }

    static class ViewHolder
    {
        TextView solution;
        TextView partner;
        TextView status;
    }

    }

