module com.example.sheetsandpicks {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;

    opens com.example.sheetsandpicks to javafx.fxml;
    opens com.example.sheetsandpicks.Controllers;
    opens com.example.Feira_and_Office.Controller to javafx.fxml;
    exports com.example.sheetsandpicks.Controllers;
    exports com.example.sheetsandpicks;

    exports com.example.sheetsandpicks.Models;
    opens com.example.sheetsandpicks.Models to javafx.fxml, com.fasterxml.jackson.databind;

    exports com.example.sheetsandpicks.Controllers.Stock;
    opens com.example.sheetsandpicks.Controllers.Stock to javafx.fxml;

    exports com.example.sheetsandpicks.Controllers.app_dashboard;
    opens com.example.sheetsandpicks.Controllers.app_dashboard to javafx.fxml;

    exports com.example.sheetsandpicks.Controllers.app_login;
    opens com.example.sheetsandpicks.Controllers.app_login to javafx.fxml;

    exports com.example.sheetsandpicks.Controllers.Users;
    opens  com.example.sheetsandpicks.Controllers.Users to javafx.fxml;

    exports com.example.sheetsandpicks.Controllers.Pagamento;
    opens com.example.sheetsandpicks.Controllers.Pagamento;

    exports com.example.sheetsandpicks.Controllers.Produtos;
    opens com.example.sheetsandpicks.Controllers.Produtos;

    exports com.example.sheetsandpicks.Controllers.Encomenda;
    opens com.example.sheetsandpicks.Controllers.Encomenda to javafx.fxml;
    exports com.example.Feira_and_Office.Controller.Produtos;
    opens com.example.Feira_and_Office.Controller.Produtos;
    exports com.example.Feira_and_Office.Controller.Encomendas;
    opens com.example.Feira_and_Office.Controller.Encomendas;
    opens com.example.util.Models;
    opens com.example.util;
    exports com.example.util.Models;
    exports com.example.util;
    exports com.example.Feira_and_Office.Controller.app_login;
    opens com.example.Feira_and_Office.Controller.app_login to javafx.fxml;

}



