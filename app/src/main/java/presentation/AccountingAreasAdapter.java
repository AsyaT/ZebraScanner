package presentation;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.zferma.zebrascanner.R;

public class AccountingAreasAdapter extends ArrayAdapter {

    private final Activity context;

    private final List<AccountingAreasListViewModel> AccountingAreasListModel;

    public AccountingAreasAdapter(Activity context, List<AccountingAreasListViewModel> list) {
        super(context, R.layout.accounting_area_row, list);
        this.context = context;
        this.AccountingAreasListModel = list;
    }

    static class ViewHolder {
        protected TextView txtAccountingAreaName;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator = context.getLayoutInflater();
        View view = inflator.inflate(R.layout.accounting_area_row, null);

        AccountingAreasAdapter.ViewHolder holder = new AccountingAreasAdapter.ViewHolder();

        holder.txtAccountingAreaName  = (TextView) view.findViewById(R.id.txtAccountingAreaName);
        holder.txtAccountingAreaName.setText(this.AccountingAreasListModel.get(position).AccountingAreaName);

        return view;
    }
}
