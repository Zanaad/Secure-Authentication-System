module com.example.datasecurity1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens LoginSignUp to javafx.fxml;
    exports LoginSignUp;
}