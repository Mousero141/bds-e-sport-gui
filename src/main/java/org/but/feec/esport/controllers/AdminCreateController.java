package org.but.feec.esport.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import org.but.feec.esport.api.AdminCreateView;
import org.but.feec.esport.data.AdminRepository;
import org.but.feec.esport.service.AdminService;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class AdminCreateController {

    private static final Logger logger = LoggerFactory.getLogger(AdminCreateController.class);

    @FXML
    public Button newAdminCreateAdmin;
    @FXML
    private TextField newAdminEmail;

    @FXML
    private TextField newAdminGivenName;

    @FXML
    private TextField newAdminFamilyName;

    @FXML
    private TextField newAdminNickname;

    @FXML
    private TextField newAdminPwd;

    @FXML
    private TextField newAdminSalary;

    private AdminService adminService;
    private AdminRepository adminRepository;
    private ValidationSupport validation;

    @FXML
    public void initialize() {
        adminRepository = new AdminRepository();
        adminService = new AdminService(adminRepository);

        validation = new ValidationSupport();
        validation.registerValidator(newAdminEmail, Validator.createEmptyValidator("The email must not be empty."));
        validation.registerValidator(newAdminGivenName, Validator.createEmptyValidator("The first name must not be empty."));
        validation.registerValidator(newAdminFamilyName, Validator.createEmptyValidator("The last name must not be empty."));
        validation.registerValidator(newAdminNickname, Validator.createEmptyValidator("The nickname must not be empty."));
        validation.registerValidator(newAdminPwd, Validator.createEmptyValidator("The password must not be empty."));
        validation.registerValidator(newAdminSalary, Validator.createEmptyValidator("The salary must not be empty."));

        newAdminCreateAdmin.disableProperty().bind(validation.invalidProperty());

        logger.info("AdminCreateController initialized");
    }

    @FXML
    void handleCreateNewPerson(ActionEvent event) {
        // can be written easier, its just for better explanation here on so many lines
        String email = newAdminEmail.getText();
        String firstName = newAdminGivenName.getText();
        String lastName = newAdminFamilyName.getText();
        String nickname = newAdminNickname.getText();
        String password = newAdminPwd.getText();
        Long salary = Long.valueOf(newAdminSalary.getText());

        AdminCreateView adminCreateView = new AdminCreateView();
        adminCreateView.setPwd(password.toCharArray());
        adminCreateView.setEmail(email);
        adminCreateView.setGiven_name(firstName);
        adminCreateView.setFamily_name(lastName);
        adminCreateView.setNickname(nickname);
        adminCreateView.setSalary(salary);

        adminService.createAdmin(adminCreateView);

        personCreatedConfirmationDialog();
    }

    private void personCreatedConfirmationDialog() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Admin Created Confirmation");
        alert.setHeaderText("Your admin was successfully created.");

        Timeline idlestage = new Timeline(new KeyFrame(Duration.seconds(3), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                alert.setResult(ButtonType.CANCEL);
                alert.hide();
            }
        }));
        idlestage.setCycleCount(1);
        idlestage.play();
        Optional<ButtonType> result = alert.showAndWait();
    }

}
