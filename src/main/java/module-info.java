module org.tnt.task2 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires vosk;
    requires org.json;


    opens org.tnt.task2 to javafx.fxml;
    exports org.tnt.task2;
}