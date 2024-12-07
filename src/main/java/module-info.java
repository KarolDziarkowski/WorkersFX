module com.example.workersfxx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.workersfxx to javafx.fxml;
    exports com.example.workersfxx;
    exports com.example.workersfxx.models;
    opens com.example.workersfxx.models to javafx.fxml;
}