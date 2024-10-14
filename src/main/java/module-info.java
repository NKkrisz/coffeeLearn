module dev.nkkrisz.coffeelearn {
    requires javafx.controls;
    requires javafx.fxml;


    opens dev.nkkrisz.coffeelearn to javafx.fxml;
    exports dev.nkkrisz.coffeelearn;
}