package dev.nkkrisz.coffeelearn;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    @FXML
    private Label taskLabel;

    @FXML
    private TextField answerField;

    @FXML
    private Label feedbackLabel;

    @FXML
    private Button checkButton;

    @FXML
    private MenuItem additionMenuItem, multiplicationMenuItem;

    @FXML
    private Label scoreLabel;

    private int correctAnswers = 0;
    private int incorrectAnswers = 0;

    private int num1;
    private int num2;
    private String operation;
    private int correctResult;
    private Timer timer;
    private int timeLimit = 5; // seconds

    @FXML
    public void initialize() {
        setTask("+"); // Default művelet
    }

    @FXML
    public void onAdditionMenuItemClicked() {
        setTask("+");
    }

    @FXML
    public void onMultiplicationMenuItemClicked() {
        setTask("*");
    }

    public void setTask(String operation) {
        this.operation = operation;
        Random random = new Random();
        num1 = random.nextInt(10) + 1;
        num2 = random.nextInt(10) + 1;

        switch (operation) {
            case "+":
                correctResult = num1 + num2;
                taskLabel.setText(num1 + " + " + num2 + " = ?");
                break;
            case "*":
                correctResult = num1 * num2;
                taskLabel.setText(num1 + " * " + num2 + " = ?");
                break;
        }

        feedbackLabel.setText("");
        answerField.setText("");
        startTimer();
    }

    @FXML
    public void onCheckButtonClicked() {
        checkAnswer();
    }

    private void checkAnswer() {
        try {
            int userAnswer = Integer.parseInt(answerField.getText());
            if (userAnswer == correctResult) {
                correctAnswers++;
                feedbackLabel.setText("Helyes!");
            } else {
                incorrectAnswers++;
                feedbackLabel.setText("Helytelen! A helyes válasz: " + correctResult);
            }
            updateScore();
            setTask(operation); // Új feladat generálása
        } catch (NumberFormatException e) {
            feedbackLabel.setText("Kérlek, adj meg egy számot!");
        }
    }

    private void updateScore() {
        scoreLabel.setText("Helyes válaszok: " + correctAnswers + " | Helytelen válaszok: " + incorrectAnswers);
    }

    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        TimerTask task = new TimerTask() {
            int timeRemaining = timeLimit;

            @Override
            public void run() {
                Platform.runLater(() -> {
                    if (timeRemaining > 0) {
                        feedbackLabel.setText("Idő: " + timeRemaining + " másodperc");
                        timeRemaining--;
                    } else {
                        timer.cancel();
                        incorrectAnswers++;
                        feedbackLabel.setText("Lejárt az idő! A helyes válasz: " + correctResult);
                        updateScore();
                        setTask(operation);
                    }
                });
            }
        };
        timer.scheduleAtFixedRate(task, 0, 1000);
    }
}
