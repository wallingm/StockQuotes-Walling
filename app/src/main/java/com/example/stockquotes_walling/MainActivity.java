package com.example.stockquotes_walling;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    String stock;
    EditText input;
    TextView symbol, name, tradePrice, tradeTime, change, weekRange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        symbol     = findViewById(R.id.symbolResult);
        name       = findViewById(R.id.nameResult);
        tradePrice = findViewById(R.id.tradePriceResult);
        tradeTime  = findViewById(R.id.tradeTimeResult);
        change     = findViewById(R.id.changeResult);
        weekRange  = findViewById(R.id.weekRangeResult);
        input      = findViewById(R.id.input);

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //
            }

            @Override
            public void afterTextChanged(Editable s) {
                stock = input.getText().toString();
                StockAsyncTask asyncTask = new StockAsyncTask(stock, findViewById(R.id.activity), MainActivity.this);
                asyncTask.execute();
                //
            }
        });
    }

    static class StockAsyncTask extends AsyncTask<Void, Void, Stock>{
        private String inputStock;
        private View view;
        private Context cxt;

        StockAsyncTask(String s, View v, Context c) {
            this.inputStock = s;
            this.view = v;
            this.cxt = c;
        }

        @Override
        protected Stock doInBackground(Void... voids) {
            Stock stock = new Stock(this.inputStock);
            try {
                stock.load();
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
                return null;
            }
            return stock;
        }

        @Override
        protected void onPostExecute(Stock stock) {
            super.onPostExecute(stock);

            TextView symbol     = view.findViewById(R.id.symbolResult);
            TextView name       = view.findViewById(R.id.nameResult);
            TextView tradePrice = view.findViewById(R.id.tradePriceResult);
            TextView tradeTime  = view.findViewById(R.id.tradeTimeResult);
            TextView change     = view.findViewById(R.id.changeResult);
            TextView weekRange  = view.findViewById(R.id.weekRangeResult);

            try {
                symbol.setText(stock.getSymbol());
                name.setText(stock.getName());
                tradePrice.setText(stock.getLastTradePrice());
                tradeTime.setText(stock.getLastTradeTime());
                change.setText(stock.getChange());
                weekRange.setText(stock.getRange());
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
