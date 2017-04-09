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

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot have more than 100 coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot have less than 1 coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText orderName=(EditText)findViewById(R.id.name);
        String nameString=orderName.getText().toString();

        CheckBox wippedCreamCheckBox = (CheckBox) findViewById(R.id.wipped_cream_checkbox);
        boolean hasWhippedCream = wippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolateCheckBox = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolateCheckBox);
        String priceMessage =createOrderSummary(price, hasWhippedCream, hasChocolateCheckBox, nameString);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: david.katic@gmail.com")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + nameString);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
//          startActivity(Intent.createChooser(intent, "Izaberite klijenta:")); ->  može ići i ovo ako želimo svaki puta birati klijenta za tu akciju
        }
//        displayMessage(priceMessage);
    }

    /**
     * Calculates the price of the order.
     *
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        if ((addWhippedCream) && (!addChocolate)) {
            return quantity * 6;
        } else if ((!addWhippedCream) && (addChocolate)) {
            return quantity * 7;
        } else if ((addWhippedCream) && (addChocolate)) {
            return quantity * 8;
        } else {
            return quantity * 5;
        }
    }

    private String createOrderSummary(int price, boolean addWhippedCream, boolean addChocolate, String nameString) {
        String priceMessage = "Name: " + nameString;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
//    private void displayMessage(String message) {
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        orderSummaryTextView.setText(message);
//    }

}