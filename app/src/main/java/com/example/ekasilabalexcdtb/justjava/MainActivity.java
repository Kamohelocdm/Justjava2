package com.example.ekasilabalexcdtb.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order from to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // show an erro message as a toast
            Toast.makeText(this, "you cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            // Exit this method early because there`s nithing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // show error message as a toast
            Toast.makeText(this, "you cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            // Exit this method early because there`s nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Find the uder`s name
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        // figure out if the user wants whipped cream topping
        CheckBox WhippedCreamCheckBox = (CheckBox) findViewById(R.id.Whipped_cream_checkbox);
        boolean hasWhippedCream = WhippedCreamCheckBox.isChecked();

        // Figure out if user wants chocolate topping
        CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChoclate = ChocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChoclate);
        String priceMessage = creatOrderSummary(name, price, hasWhippedCream, hasChoclate);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "just java order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }


    /**
     * calculate the price of the order
     *
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        // price of 1 cup of coffee
        int basePrice = 5;

        // Add $1 if the user wants whipped cream
        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }

        // Add $2 if the user wants chocolate
        if (addChocolate) {
            basePrice = basePrice + 2;
        }

        // Calculate the total order price by multiplying by quyantity
        return quantity * basePrice;
    }

    /**
     * creat order summary.
     *
     * @param name            on the order
     * @param price           of the order
     * @param addWhippedCream is whether or not the user wants whipped cream topping.
     * @param addChocolate    is whether or not the user wants chocolate topping.
     * @return text summary
     */

    private String creatOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }

    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }


}
