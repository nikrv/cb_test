package dexsys.ru.currencyconverter.ui.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import dexsys.ru.currencyconverter.R;
import dexsys.ru.currencyconverter.data.model.Currency;

/**
 * Created by blood on 01.05.2017.
 */

public class CurrencyAdapter extends ArrayAdapter<Currency> implements Filterable {

    private Context context;
    private List<Currency> originalList;
    private List<Currency> suggestions = new ArrayList<>();
    private Filter filter = new CustomFilter();

    /**
     * @param context      Context
     * @param originalList Original list used to compare in constraints.
     */
    public CurrencyAdapter(Context context, List<Currency> originalList) {
        super(context, android.R.layout.simple_dropdown_item_1line, originalList);
        this.context = context;
        this.originalList = originalList;
    }

    @Override
    public int getCount() {
        return originalList.size();
    }

    @Override
    public Currency getItem(int position) {
        return originalList.get(position);
    }


    /**
     * This is where you inflate the layout and also where you set what you want to display.
     * Here we also implement a View Holder in order to recycle the views.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_dropdown_item_1line,
                    parent,
                    false);
            holder = new ViewHolder();
            holder.autoText = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.autoText.setText(originalList.get(position).getName());

        return convertView;
    }

    /**
     * This is where you inflate the layout and also where you set what you want to display.
     * Here we also implement a View Holder in order to recycle the views.
     */
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);

        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(android.R.layout.simple_dropdown_item_1line,
                    parent,
                    false);
            holder = new ViewHolder();
            holder.autoText = (TextView) convertView.findViewById(android.R.id.text1);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.autoText.setText(originalList.get(position).getName());

        return convertView;
    }


    @Override
    public Filter getFilter() {
        return filter;
    }

    private static class ViewHolder {
        TextView autoText;
    }

    /**
     * Our Custom Filter Class.
     */
    private class CustomFilter extends Filter {

        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((Currency)(resultValue)).getName();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            suggestions.clear();

            if (originalList != null && constraint != null) { // Check if the Original List and Constraint aren't null.
                for (int i = 0; i < originalList.size(); i++) {
                    if (originalList.get(i).getName().toLowerCase().contains(constraint)) { // Compare item in original list if it contains constraints.
                        suggestions.add(originalList.get(i)); // If TRUE add item in Suggestions.
                    }
                }
            }

            FilterResults results = new FilterResults(); // Create new Filter Results and return this to publishResults;
            results.values = suggestions;
            results.count = suggestions.size();

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.count > 0) {
                notifyDataSetChanged();
            } else {
                notifyDataSetInvalidated();
            }
        }
    }
}
