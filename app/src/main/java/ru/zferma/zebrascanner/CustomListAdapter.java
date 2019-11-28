package ru.zferma.zebrascanner;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter {

    //to reference the Activity
    private final Activity context;

    private final List<ProductListViewModel> ListOrderModel;


    public CustomListAdapter(Activity context, List<ProductListViewModel> list) {
        super(context, R.layout.list_view_row, list);
        this.context = context;
        this.ListOrderModel = list;
    }

    static class ViewHolder {
        protected TextView textStringNumber;
        protected TextView textNomenclature;
        protected TextView textWeight;
        protected TextView textCoefficient;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator = context.getLayoutInflater();
        View view = inflator.inflate(R.layout.list_view_row, null);

        ViewHolder holder = new ViewHolder();

        holder.textStringNumber  = (TextView) view.findViewById(R.id.textViewStringNumber);
        holder.textStringNumber.setText(this.ListOrderModel.get(position).getStringNumber());

        holder.textNomenclature = (TextView) view.findViewById(R.id.textViewNomenclature);
        holder.textNomenclature.setText(this.ListOrderModel.get(position).getNomenclature());

        holder.textWeight = (TextView) view.findViewById(R.id.textViewWeight);
        holder.textWeight.setText(this.ListOrderModel.get(position).getWeight());

        holder.textCoefficient = (TextView) view.findViewById(R.id.textViewCoefficient);
        holder.textCoefficient.setText(this.ListOrderModel.get(position).getCoefficient());

        return view;
    }
}
