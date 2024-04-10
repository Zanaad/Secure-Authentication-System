module com.example.ushtrimeknk {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens group_3.Java1 to javafx.graphics;
    opens group_3.Java2 to javafx.graphics;
    opens group_3.Java3 to javafx.graphics;
    opens group_3.Java4 to javafx.graphics;
    opens group_3.Ushtrime to javafx.graphics;
    opens group_3.Java6 to javafx.fxml;
    opens DS.Java7 to javafx.fxml;
    opens LoginSignUp to javafx.fxml;
    opens com.example.ushtrimeknk to javafx.fxml;

    exports com.example.ushtrimeknk;
    exports group_3.Java6;
    exports DS.Java7;
    exports LoginSignUp;
}