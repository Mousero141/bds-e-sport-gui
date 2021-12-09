/**package esport.but.feec.esport.controller;

import esport.but.feec.esport.api.AdminBasicView;
import esport.but.feec.esport.data.PersonRepository;
import esport.but.feec.esport.exceptions.ExceptionHandler;
import esport.but.feec.esport.service.PersonService;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class PersonsController {
    private static final Logger logger = LoggerFactory.getLogger(PersonsController.class);

    @FXML
    public Button addPersonButton;
    @FXML
    public Button refreshButton;
    @FXML
    private TableColumn<AdminBasicView, Long> adminId;
    @FXML
    private TableColumn<AdminBasicView, String> adminNickname;
    @FXML
    private TableColumn<AdminBasicView, String> adminEmail;
    @FXML
    private TableColumn<AdminBasicView, String> adminFamilyName;
    @FXML
    private TableColumn<AdminBasicView, String> adminGivenName;
    @FXML
    private TableView<AdminBasicView> systemPersonsTableView;
//    @FXML
//    public MenuItem exitMenuItem;

    private PersonService personService;
    private PersonRepository personRepository;

    public PersonsController() {
    }

    @FXML
    private void initialize() {
        personRepository = new PersonRepository();
        personService = new PersonService(personRepository);
//        GlyphsDude.setIcon(exitMenuItem, FontAwesomeIcon.CLOSE, "1em");

        adminId.setCellValueFactory(new PropertyValueFactory<AdminBasicView, Long>("id"));
        adminNickname.setCellValueFactory(new PropertyValueFactory<AdminBasicView, String>("nickname"));
        adminEmail.setCellValueFactory(new PropertyValueFactory<AdminBasicView, String>("email"));
        adminFamilyName.setCellValueFactory(new PropertyValueFactory<AdminBasicView, String>("familyName"));
        adminGivenName.setCellValueFactory(new PropertyValueFactory<AdminBasicView, String>("givenName"));



        ObservableList<AdminBasicView> observablePersonsList = initializePersonsData();
        systemPersonsTableView.setItems(observablePersonsList);

        systemPersonsTableView.getSortOrder().add(adminId);

        initializeTableViewSelection();
        loadIcons();

        logger.info("PersonsController initialized");
    }

    private void initializeTableViewSelection() {
        MenuItem edit = new MenuItem("Edit person");
        MenuItem detailedView = new MenuItem("Detailed person view");
        edit.setOnAction((ActionEvent event) -> {
            AdminBasicView personView = systemPersonsTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/PersonEdit.fxml"));
                Stage stage = new Stage();
                stage.setUserData(personView);
                stage.setTitle("BDS JavaFX Edit Person");

                PersonsEditController controller = new PersonsEditController();
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
            AdminBasicView personView = systemPersonsTableView.getSelectionModel().getSelectedItem();
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(App.class.getResource("fxml/PersonsDetailView.fxml"));
                Stage stage = new Stage();

                Long personId = personView.getId();
                PersonDetailView personDetailView = personService.getPersonDetailView(personId);

                stage.setUserData(personDetailView);
                stage.setTitle("BDS JavaFX Persons Detailed View");

                PersonsDetailViewController controller = new PersonsDetailViewController();
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
        systemPersonsTableView.setContextMenu(menu);
    }

    private ObservableList<AdminBasicView> initializePersonsData() {
        List<AdminBasicView> persons = personService.getPersonsBasicView();
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
        ObservableList<AdminBasicView> observablePersonsList = initializePersonsData();
        systemPersonsTableView.setItems(observablePersonsList);
        systemPersonsTableView.refresh();
        systemPersonsTableView.sort();
    }
}*/
