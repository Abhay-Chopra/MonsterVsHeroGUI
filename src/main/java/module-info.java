module mvh.app {
    requires javafx.controls;
    requires javafx.fxml;


    opens mvh.app to javafx.fxml;
    exports mvh.app;
}