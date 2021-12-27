package org.but.feec.esport.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.but.feec.esport.api.AdminBasicView;
import org.but.feec.esport.api.AdminDetailView;
import org.but.feec.esport.data.AdminRepository;
import org.but.feec.esport.service.AdminService;
import org.but.feec.esport.App;
import org.but.feec.esport.exceptions.ExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class AdminsController {

    private static final Logger logger = LoggerFactory.getLogger(AdminsController.class);

    @FXML
    public Button addAdminButton;
    @FXML
    public Button refreshButton;
    @FXML
    private TableColumn<AdminBasicView, Long> adminsId;
    @FXML
    private TableColumn<AdminBasicView, String> adminsEmail;
    @FXML
    private TableColumn<AdminBasicView, String> adminsFamilyName;
    @FXML
    private TableColumn<AdminBasicView, String> adminsGivenName;
    @FXML
    private TableColumn<AdminBasicView, String> adminsNickname;
    @FXML
    private TableView<AdminBasicView> systemAdminsTableView;
//    @FXML
//    public MenuItem exitMenuItem;

    private AdminService adminService;
    private AdminRepository adminRepository;

    public AdminsController() {
    }

    @FXML
    private void initialize() {
        adminRepository = new AdminRepository();
        adminService = new AdminService(adminRepository);
//        GlyphsDude.setIcon(exitMenuItem, FontAwesomeIcon.CLOSE, "1em");

        adminsId.setCellValueFactory(new PropertyValueFactory<AdminBasicView, Long>("id"));
        adminsEmail.setCellValueFactory(new PropertyValueFactory<AdminBasicView, String>("email"));
        adminsFamilyName.setCellValueFactory(new PropertyValueFactory<AdminBasicView, String>("familyName"));
        adminsGivenName.setCellValueFactory(new PropertyValueFactory<AdminBasicView, String>("givenName"));
        adminsNickname.setCellValueFactory(new PropertyValueFactory<AdminBasicView, String>("nickname"));


        ObservableList<AdminBasicView> observableAdminsList = initializeAdminsData();
        systemAdminsTableView.setItems(observableAdminsList);

        systemAdminsTableView.getSortOrder().add(adminsId);

        //initializeTableViewSelection();
        loadIcons();

        logger.info("PersonsController initialized");
    }

    private void initializeTableViewSelection() {
        MenuItem edit = new MenuItem("Edit person");
        MenuItem detailedView = new MenuItem("Detailed person view");
        edit.setOnAction((ActionEvent event) -> {
            AdminBasicView personView = systemAdminsTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/PersonEdit.fxml"));
                Stage stage = new Stage();
                stage.setUserData(personView);
                stage.setTitle("BDS JavaFX Edit Person");

                AdminsEditController controller = new AdminsEditController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);

                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });

        detailedView.setOnAction((ActionEvent event) -> {
            AdminBasicView personView = systemAdminsTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/PersonsDetailView.fxml"));
                Stage stage = new Stage();

                Long adminId = personView.getId();
                AdminDetailView adminDetailView = adminService.getAdminDetailView(adminId);

                stage.setUserData(adminDetailView);
                stage.setTitle("BDS JavaFX Persons Detailed View");

                AdminsDetailViewController controller = new AdminsDetailViewController();
                controller.setStage(stage);
                fxmlLoader.setController(controller);

                Scene scene = new Scene(fxmlLoader.load(), 600, 500);

                stage.setScene(scene);

                stage.show();
            } catch (IOException ex) {
                ExceptionHandler.handleException(ex);
            }
        });


        ContextMenu menu = new ContextMenu();
        menu.getItems().add(edit);
        menu.getItems().addAll(detailedView);
        systemAdminsTableView.setContextMenu(menu);
    }

    private ObservableList<AdminBasicView> initializeAdminsData() {
        List<AdminBasicView> persons = adminService.getAdminsBasicView();
        return FXCollections.observableArrayList(persons);
    }

    private void loadIcons() {
        Image vutLogoImage = new Image(App.class.getResourceAsStream("logos/vut-logo-eng.png"));
        ImageView vutLogo = new ImageView(vutLogoImage);
        vutLogo.setFitWidth(150);
        vutLogo.setFitHeight(50);
    }

    public void handleExitMenuItem(ActionEvent event) {
        System.exit(0);
    }

    public void handleAddPersonButton(ActionEvent actionEvent) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("fxml/PersonsCreate.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 500);
            Stage stage = new Stage();
            stage.setTitle("BDS JavaFX Create Person");
            stage.setScene(scene);

//            Stage stageOld = (Stage) signInButton.getScene().getWindow();
//            stageOld.close();
//
//            stage.getIcons().add(new Image(App.class.getResourceAsStream("logos/vut.jpg")));
//            authConfirmDialog();

            stage.show();
        } catch (IOException ex) {
            ExceptionHandler.handleException(ex);
        }
    }

    public void handleRefreshButton(ActionEvent actionEvent) {
        ObservableList<AdminBasicView> observableAdminsList = initializeAdminsData();
        systemAdminsTableView.setItems(observableAdminsList);
        systemAdminsTableView.refresh();
        systemAdminsTableView.sort();
    }
}
