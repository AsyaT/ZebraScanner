package presentation;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.zferma.zebrascanner.R;

public class OperationsListAdapter extends ArrayAdapter {
    private final Activity context;

    private final List<OperationTypesListViewModel> OperationsListModel;


    public OperationsListAdapter(Activity context, List<OperationTypesListViewModel> list) {
        super(context, R.layout.list_view_row, list);
        this.context = context;
        this.OperationsListModel = list;
    }

    static class ViewHolder {
        protected TextView txtOperationName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator = context.getLayoutInflater();
        View view = inflator.inflate(R.layout.list_view_row, null);

        OperationsListAdapter.ViewHolder holder = new OperationsListAdapter.ViewHolder();

        holder.txtOperationName  = (TextView) view.findViewById(R.id.txtOperationName);
        holder.txtOperationName.setText(this.OperationsListModel.get(position).OperationName);

        return view;
    }
}
