package controller;

import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXRippler;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import model.Task;
import ui.EditTask;
import ui.ListView;
import ui.PomoTodoApp;
import utility.JsonFileIO;
import utility.Logger;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

// Controller class for Todobar UI
public class TodobarController implements Initializable {
    private static final String todoOptionsPopUpFXML = "resources/fxml/TodoOptionsPopUp.fxml";
    private static final String todoActionsPopUpFXML = "resources/fxml/TodoActionsPopUp.fxml";
    private File todoOptionsPopUp = new File(todoOptionsPopUpFXML);
    private File todoActionsPopUp = new File(todoActionsPopUpFXML);
    
    @FXML
    private Label descriptionLabel;
    @FXML
    private JFXHamburger todoActionsPopUpBurger;
    @FXML
    private StackPane todoActionsPopUpContainer;
    @FXML
    private JFXRippler todoOptionsPopUpRippler;
    @FXML
    private StackPane todoOptionsPopUpBurger;

    private JFXPopup optionsPopUp;
    private JFXPopup actionsPopUp;


    
    private Task task;
    
    // REQUIRES: task != null
    // MODIFIES: this
    // EFFECTS: sets the task in this Todobar
    //          updates the Todobar UI label to task's description
    public void setTask(Task task) {
        this.task = task;
        descriptionLabel.setText(task.getDescription());
    }
    //EFFECTS:

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO: complete this method
        loadActions();
        loadActionsListener();
        loadOptions();
        loadOptionsListener();
    }

    public void loadOptions() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoOptionsPopUp.toURI().toURL());
            fxmlLoader.setController(new TodoBarOptionsController());
            optionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadOptionsListener() {
        todoOptionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                optionsPopUp.show(todoOptionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.RIGHT,
                        -12, 15);
            }
        });

    }

    public void loadActions() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(todoActionsPopUp.toURI().toURL());
            fxmlLoader.setController(new TodoBarActionsController());
            actionsPopUp = new JFXPopup(fxmlLoader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadActionsListener() {
        todoActionsPopUpBurger.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                actionsPopUp.show(todoActionsPopUpBurger,
                        JFXPopup.PopupVPosition.TOP,
                        JFXPopup.PopupHPosition.LEFT,
                        12, 15);
            }
        });
    }


    class TodoBarOptionsController {
        @FXML
        private JFXListView<?> optionPopUpList;

        @FXML
        private void submit() {
            int i = optionPopUpList.getSelectionModel().getSelectedIndex();
            switch (i) {
                case 0:
                    Logger.log("TodobarOptionsController", "Edits this task.");
                    PomoTodoApp.setScene(new EditTask(task));
                    break;
                case 1:
                    Logger.log("TodobarOptionsController", "Task deleted.");
                    PomoTodoApp.getTasks().remove(task);
                    PomoTodoApp.setScene(new ListView(PomoTodoApp.getTasks()));
                    break;
                default:
                    Logger.log("TodobarOptionsController", "No functionality has been implemented for it");
            }
            optionsPopUp.hide();
        }
    }

    class TodoBarActionsController {
        @FXML
        private JFXListView<?> actionsPopUpList;

        @FXML
        private void submit() {
            Logger.log("TodoBarActionsController", "No functionality has been implemented for this.");
        }


    }

}
