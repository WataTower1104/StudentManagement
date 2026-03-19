module site.watatower.studentmanagement {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires transitive java.xml;
    requires java.logging;

    opens site.watatower.student to javafx.fxml;
    opens site.watatower.student.Entity to javafx.base;
    opens site.watatower.student.Util.javafx to javafx.fxml;
    exports site.watatower.student;
    exports site.watatower.student.Entity;
    exports site.watatower.student.Util.javafx;

}