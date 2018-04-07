package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
    //int pricePerCup = 5;
    //int price = 0;
    int whipCreamCost = 1;
    int chocCost = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    //https://gist.github.com/udacityandroid/609dbb5370ba0665955a
    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @return the price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        int pricePerCup = 5;
        if (hasWhippedCream == true) {
            pricePerCup += whipCreamCost;
        }
        if (hasChocolate == true) {
            pricePerCup += chocCost;
        }
        return quantity * pricePerCup;
        //pricePerCup=5;
        //return price;
    }

    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate) {
        //OLD EditText orderName = (EditText) findViewById(R.id.order_name);
        //OLD String orderNameStng = orderName.getText().toString();
        //OLD calculatePrice(hasWhippedCream, hasChocolate);

        String orderName = ((EditText) findViewById(R.id.order_name)).getText().toString();
        String priceMessage = "Name: " + orderName;
        if (hasWhippedCream == true) {
            priceMessage += "\nHas whipped cream?: Yes";
        } else if (hasWhippedCream == false) {
            priceMessage += "\nHas whipped cream?: No";
        }
        if (hasChocolate == true) {
            priceMessage += "\nHas whipped cream?: Yes";
        } else if (hasChocolate == false) {
            priceMessage += "\nHas whipped cream?: No";
        }
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: " + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox checkBoxViewWhip = (CheckBox) findViewById(R.id.whipped_cream);
        boolean hasWhippedCream = checkBoxViewWhip.isChecked();
        CheckBox checkBoxViewChoc = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = checkBoxViewChoc.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        //OLD displayMessage(createOrderSummary(price, hasWhippedCream, hasChocolate));

        String emailMsg = createOrderSummary(price, hasWhippedCream, hasChocolate);
        String orderName = ((EditText) findViewById(R.id.order_name)).getText().toString();
        String emailSubject = "JustJava order for " + orderName;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, emailSubject);
        intent.putExtra(Intent.EXTRA_TEXT, emailMsg);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse("geo:29.475587, -95.514693"));
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method is called when the plus button is is clicked.
     */
    public void increment(View view) {
        if (quantity < 100) {
            quantity += 1;
        } else {
            CharSequence tooMuchCoffee = "You cannot order more than 100 cups of coffee";
            int duration = Toast.LENGTH_SHORT;
            Toast tooMuchToast = Toast.makeText(getApplicationContext(), tooMuchCoffee, duration);
            tooMuchToast.show();
        }
        displayQuantity(quantity);
    }

    /**
     * This method is called when the plus button is is clicked.
     */
    public void decrement(View view) {
        if (quantity > 0) {
            quantity = quantity - 1;
        } else {
            //Context context = getApplicationContext();
            CharSequence tooLittleCoffee = "You cannot have less than 0 cups of coffee";
            int duration = Toast.LENGTH_SHORT;
            Toast tooLittleToast = Toast.makeText(getApplicationContext(), tooLittleCoffee, duration);
            tooLittleToast.show();
        }
        displayQuantity(quantity);
    }

}
