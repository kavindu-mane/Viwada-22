module com.hexa2zero.viwada_22 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;

    opens com.hexa2zero.viwada_22 to javafx.fxml;
    exports com.hexa2zero.viwada_22;
}