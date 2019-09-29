package sample;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

public class Controller {

    private static final NumberFormat currency = NumberFormat.getCurrencyInstance();
    private static final NumberFormat percent = NumberFormat.getPercentInstance();
    private BigDecimal tipPercentage = new BigDecimal(0.15);


    @FXML
    private TextField numberOfPersons;

    @FXML
    private TextField amountTextField;

    @FXML
    private Label tipPercentageLabel;

    @FXML
    private Slider tipPercentageSlider;

    @FXML
    private TextField tipTextField;

    @FXML
    private TextField totalTextField;

    @FXML
    private TextField dividedBill;


    @FXML
    private void calculateButtonPressed(ActionEvent event) {
        try {
            BigDecimal amount = new BigDecimal(amountTextField.getText());
            BigDecimal tip = amount.multiply(tipPercentage);
            BigDecimal total = amount.add(tip);
            BigDecimal forEachOne = total.divide(new BigDecimal(numberOfPersons.getText()));

            tipTextField.setText(currency.format(tip));
            totalTextField.setText(currency.format(total));
            dividedBill.setText(currency.format(forEachOne));
        }
        catch (NumberFormatException ex) {
            amountTextField.setText("Enter amount");
            amountTextField.selectAll();
            amountTextField.requestFocus();
        }
    }

    public void initialize() {
        currency.setRoundingMode(RoundingMode.HALF_UP);
        tipPercentageSlider.valueProperty().addListener(
                (ov, oldValue, newValue) -> {
                    tipPercentage = BigDecimal.valueOf(newValue.intValue() / 100.0);
                    tipPercentageLabel.setText(percent.format(tipPercentage));
                    BigDecimal amount = new BigDecimal(amountTextField.getText());
                    BigDecimal tip = amount.multiply(tipPercentage);
                    tipTextField.setText(currency.format(tip));
                }
        );
        amountTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,10}([\\.]\\d{0,4})?")) {
                amountTextField.setText(oldValue);
            }
        });
        numberOfPersons.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d{0,10}?")) {
                numberOfPersons.setText(oldValue);
            }
        });
    }
}

