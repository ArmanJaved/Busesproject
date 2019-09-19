package com.sgbuses;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends Activity {
    private LinearLayout llContainer;
    private EditText etSearch;
    private ListView lvProducts;

    private ArrayList<Product> mProductArrayList = new ArrayList<Product>();
    private MyAdapter adapter1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        getUserData();




        etSearch = (EditText) findViewById(R.id.etSearch);
        lvProducts = (ListView)findViewById(R.id.lvProducts);

        // Add Text Change Listener to EditText
        etSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Call back the Adapter with current character to Filter
                adapter1.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            }


        });

//        http://datamall2.mytransport.sg/ltaodataservice/BusStops



    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();

        mProductArrayList.clear();
        mProductArrayList.add(new Product("a", "100"));
        mProductArrayList.add(new Product("b", "100"));
        mProductArrayList.add(new Product("c", "100"));
        mProductArrayList.add(new Product("d", "100"));
        mProductArrayList.add(new Product("e", "100"));
        mProductArrayList.add(new Product("f", "200"));
        mProductArrayList.add(new Product("g", "222"));
        mProductArrayList.add(new Product("h", "100"));
        mProductArrayList.add(new Product("i", "100"));
        mProductArrayList.add(new Product("j", "100"));
        mProductArrayList.add(new Product("k", "300"));
        mProductArrayList.add(new Product("l", "100"));
        mProductArrayList.add(new Product("m", "100"));
        mProductArrayList.add(new Product("n", "100"));
        mProductArrayList.add(new Product("o", "100"));
        mProductArrayList.add(new Product("p", "100"));


        adapter1 = new MyAdapter(SearchActivity.this, mProductArrayList);
        lvProducts.setAdapter(adapter1);
    }


    // Adapter Class
    public class MyAdapter extends BaseAdapter implements Filterable {

        private ArrayList<Product> mOriginalValues; // Original Values
        private ArrayList<Product> mDisplayedValues;    // Values to be displayed
        LayoutInflater inflater;

        public MyAdapter(Context context, ArrayList<Product> mProductArrayList) {
            this.mOriginalValues = mProductArrayList;
            this.mDisplayedValues = mProductArrayList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return mDisplayedValues.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        private class ViewHolder {
            LinearLayout llContainer;
            TextView tvName,tvPrice;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;

            if (convertView == null) {

                holder = new ViewHolder();
                convertView = inflater.inflate(R.layout.row, null);
                holder.llContainer = (LinearLayout)convertView.findViewById(R.id.llContainer);
                holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.tvPrice);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvName.setText(mDisplayedValues.get(position).name);
            holder.tvPrice.setText(mDisplayedValues.get(position).price+"");

            holder.llContainer.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    Toast.makeText(SearchActivity.this, mDisplayedValues.get(position).name, Toast.LENGTH_SHORT).show();
                }
            });

            return convertView;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,FilterResults results) {


                    mDisplayedValues = (ArrayList<Product>) results.values; // has the filtered values
                    notifyDataSetChanged();  // notifies the data with new filtered values
                }

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                    ArrayList<Product> FilteredArrList = new ArrayList<Product>();

                    if (mOriginalValues == null) {
                        mOriginalValues = new ArrayList<Product>(mDisplayedValues); // saves the original data in mOriginalValues
                    }

                    /********
                     *
                     *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
                     *  else does the Filtering and returns FilteredArrList(Filtered)
                     *
                     ********/
                    if (constraint == null || constraint.length() == 0) {

                        // set the Original result to return
                        results.count = mOriginalValues.size();
                        results.values = mOriginalValues;
                    } else {
                        constraint = constraint.toString().toLowerCase();
                        for (int i = 0; i < 16; i++) {
                            String data = mOriginalValues.get(i).name;
                            String data1 = mOriginalValues.get(i).price;


                            if (data.toLowerCase().startsWith(constraint.toString())  || data1.toLowerCase().startsWith(constraint.toString()) ) {
                                FilteredArrList.add(new Product(mOriginalValues.get(i).name,mOriginalValues.get(i).price));
                            }
                        }
                        // set the Filtered result to return
                        results.count = FilteredArrList.size();
                        results.values = FilteredArrList;
                    }
                    return results;
                }
            };
            return filter;
        }
    }


}