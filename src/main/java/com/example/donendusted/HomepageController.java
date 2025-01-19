package com.example.donendusted;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Insets;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class HomepageController implements Features
{

    //For time
    @FXML
    private Label dateTimeLabel;

    // For Theme & User Info
    @FXML
    private Label username;
    @FXML
    private BorderPane borderpane;

    @FXML
    private Label progressText;
    @FXML
    private HBox date;

    @FXML
    private HBox header;


    @FXML
    private Button addButton;
    private boolean mode;
    private String currentUser;  // Stores the logged-in user

    // For Task Management
    @FXML
    private ProgressBar progressBar;
    @FXML
    private Label progressLabel;
    @FXML
    private ListView<Task> taskListView;

    @FXML
    private Button deleteButton;

    @FXML
    private Button addTaskButton;
    @FXML
    private Button editTaskButton;
    @FXML
    private Button deleteTaskButton;

    @FXML
    private Label taskLabel;

    //Quotes
    @FXML
    private VBox quote1;
    @FXML
    private Label quote1Text;

    @FXML
    private VBox quote2;
    @FXML
    private Label quote2Text;

    @FXML
    private VBox quote3;
    @FXML
    private Label quote3Text;


    private ObservableList<Task> tasks;
    public HomepageController() {}

    @Override
    public void initialize() {
        startClock(); // Start the clock display
        tasks = FXCollections.observableArrayList(); // Initialize the task list
        mode = true; // Set initial theme (false for dark mode)

        // Load user data and tasks from file
        loadUserData();

        //Loading saved Theme
        updateTheme(mode);
        taskListView.setItems(tasks);

        addButton.setOnAction(e -> updateTheme(!mode));


        // Set custom cell factory for task list view
        taskListView.setCellFactory(param -> new ListCell<Task>() {
            private CheckBox checkBox = new CheckBox();
            private Label taskLabel = new Label();
            private HBox cellBox = new HBox(10, checkBox, taskLabel);

            @Override
            protected void updateItem(Task task, boolean empty) {
                super.updateItem(task, empty);
                if (empty || task == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("-fx-background-color: transparent;");
                } else {
                    // Set checkbox status and task label text
                    checkBox.setSelected(task.isCompleted());
                    checkBox.setOnAction(e -> handleTaskCompletion(task));

                    taskLabel.setText(task.toString());
                    updateTaskStyle(task);

                    // Set alternating background colors for rows
                    setAlternatingRowStyle(getIndex(), false); // Pass 'false' initially for no selection

                    // Set the cell's graphic (checkBox + taskLabel)
                    setGraphic(cellBox);

                    // Add listener to handle selection change
                    setOnMouseClicked(event -> {
                        if (isSelected()) {
                            setAlternatingRowStyle(getIndex(), true); // Highlight the selected row
                        } else {
                            setAlternatingRowStyle(getIndex(), false); // Revert style when deselected
                        }
                    });
                }
            }

            private void handleTaskCompletion(Task task) {
                // Update task's completed status
                task.setCompleted(checkBox.isSelected());
                updateTaskStyle(task);
                updateProgress();

                // Save the updated task list to the file
                saveTasksToFile(currentUser);
            }

            private void updateTaskStyle(Task task) {
                if (task.isCompleted()) {
                    taskLabel.setStyle(mode? "-fx-text-fill: grey; -fx-strikethrough: true;" : "-fx-text-fill: #FFFFFF50; -fx-strikethrough: true;");
                } else {
                    taskLabel.setStyle(mode
                            ? "-fx-text-fill: black; -fx-strikethrough: false;"
                            : "-fx-text-fill: white; -fx-strikethrough: false;");
                }
            }

            private void setAlternatingRowStyle(int index, boolean isSelected) {
                if (isSelected) {
                    // Style for selected row
                    setStyle(mode
                            ? "-fx-background-color: #106b64; -fx-text-fill: #FFFFFF; -fx-font-family: 'Roboto Slab'; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 6px; -fx-background-radius: 7px; -fx-border-color: ##386e69; -fx-border-width: 1px; -fx-border-radius: 7px;"
                            : "-fx-background-color: #ab8f02; -fx-text-fill: #FFFFFF; -fx-font-family: 'Roboto Slab'; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 6px; -fx-background-radius: 7px; -fx-border-color: ##386e69; -fx-border-width: 1px; -fx-border-radius: 7px;");
                } else {
                    // Alternating row styles
                    if (index % 2 == 0)
                    {
                        setStyle(mode
                                ? "-fx-background-color: #E1E1E1; -fx-text-fill: #333333; -fx-font-family: 'Roboto Slab'; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 6px; -fx-background-radius: 7px; -fx-border-radius: 7px; -fx-border-width: 1px;-fx-border-color: #106b6440;"
                                : "-fx-background-color: #ab8f0240; -fx-text-fill: #333333; -fx-font-family: 'Roboto Slab'; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 6px; -fx-background-radius: 7px; -fx-border-width: 1px; -fx-border-radius: 7px;");
                    }
                    else
                    {
                        setStyle(mode
                                ? "-fx-background-color: #106b6440; -fx-text-fill: #FFFFFF; -fx-font-family: 'Roboto Slab'; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 6px; -fx-background-radius: 7px; -fx-border-width: 1px; -fx-border-radius: 7px;"
                                : "-fx-background-color: #333333; -fx-text-fill: #FFFFFF; -fx-font-family: 'Roboto Slab'; -fx-font-size: 13px; -fx-font-weight: bold; -fx-padding: 6px; -fx-background-radius: 7px; -fx-border-radius: 7px; -fx-border-width: 1px;-fx-border-color: #ab8f0240;");
                    }
                }
            }
        });
    }

    // Method to update the theme
    @Override
    public void updateTheme(boolean isLightMode) {
        mode = isLightMode; // Update the mode (true for light, false for dark)

        if (mode)
        {
            // Light Mode

            //Header
            borderpane.setStyle("-fx-background-color: #E1E1E1; -fx-padding: 30;");
            header.setStyle("-fx-background-color: #E1E1E1; -fx-effect: dropshadow(gaussian, #106b6470, 10, 0.5, 5, 5); -fx-border-color: #106b6480; -fx-border-width: 2; -fx-border-radius: 15 15 15 15; -fx-background-radius: 15 15 15 15;");
            username.setStyle("-fx-text-fill: #106b64; -fx-font-family: 'Roboto Slab'; -fx-font-weight: bold; -fx-font-size: 16px;-fx-padding: 10px; -fx-background-radius: 5px;");
            addButton.setStyle("-fx-background-color: #E1E1E1; -fx-text-fill: #106b64; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px; -fx-border-color: #106b64; -fx-border-radius: 15px; -fx-background-radius: 15px;");

            //Date
            date.setStyle("-fx-background-color: #E1E1E1; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
            dateTimeLabel.setStyle("-fx-background-color: #E1E1E1;-fx-font-size: 16px; -fx-text-fill: #106b64; -fx-font-family: 'Roboto Slab'; -fx-font-weight: bold; -fx-padding: 9px;");

            //Progress Bar
            progressText.setStyle("-fx-font-size: 16; -fx-font-family: 'Roboto Slab'; -fx-font-weight: bold;-fx-text-fill: #106b64;");
            progressLabel.setStyle("-fx-font-size: 16; -fx-font-family: 'Roboto Slab'; -fx-font-weight: bold; -fx-text-fill: #106b64;");
            progressBar.setStyle("-fx-control-inner-background: linear-gradient(to right, #e6e6e6, #ffffff); -fx-accent: #106b64; -fx-border-color: #106b64; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");

            //Task Buttons and List
            addTaskButton.setStyle("-fx-background-color: #E1E1E1; -fx-text-fill: #106b64; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 7px; -fx-border-color: #106b64; -fx-border-radius: 15px; -fx-background-radius: 15px;");
            editTaskButton.setStyle("-fx-background-color: #E1E1E1; -fx-text-fill: #106b64; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 7px; -fx-border-color: #106b64; -fx-border-radius: 15px; -fx-background-radius: 15px;");
            deleteTaskButton.setStyle("-fx-background-color: #E1E1E1; -fx-text-fill: #106b64; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 7px; -fx-border-color: #106b64; -fx-border-radius: 15px; -fx-background-radius: 15px;");
            taskLabel.setStyle("-fx-background-color: #E1E1E1; -fx-text-fill: #106b64; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 7px; -fx-border-color: #106b64; -fx-border-radius: 15px; -fx-background-radius: 15px;");
            taskListView.setStyle("-fx-background-color: #E1E1E1; ");

            //Quotes
            quote1.setStyle("-fx-background-color: #E1E1E1; -fx-effect: dropshadow(gaussian, #106b6470, 10, 0.5, 5, 5);-fx-border-color : #106b64; -fx-border-width : 1;-fx-border-radius : 7;-fx-padding: 10; -fx-background-radius: 7;");
            quote2.setStyle("-fx-background-color: #E1E1E1; -fx-effect: dropshadow(gaussian, #106b6470, 10, 0.5, 5, 5);-fx-border-color : #106b64; -fx-border-width : 1;-fx-border-radius : 7;-fx-padding: 10; -fx-background-radius: 7;");
            quote3.setStyle("-fx-background-color: #E1E1E1; -fx-effect: dropshadow(gaussian, #106b6470, 10, 0.5, 5, 5);-fx-border-color : #106b64; -fx-border-width : 1;-fx-border-radius : 7;-fx-padding: 10; -fx-background-radius: 7;");

            quote1Text.setStyle("-fx-font-size: 14; -fx-text-fill: #333333; -fx-font-weight: bold; -fx-font-family: Roboto Slab;");
            quote2Text.setStyle("-fx-font-size: 14; -fx-text-fill: #333333; -fx-font-weight: bold; -fx-font-family: Roboto Slab;");
            quote3Text.setStyle("-fx-font-size: 14; -fx-text-fill: #333333; -fx-font-weight: bold; -fx-font-family: Roboto Slab;");
        }
        else
        {
            // Dark Mode

            //Header
            borderpane.setStyle("-fx-background-color: #333333; -fx-padding: 30;");
            header.setStyle("-fx-background-color: #333333; -fx-effect: dropshadow(gaussian, #ab8f0270, 10, 0.5, 5, 5); -fx-border-color: #ab8f02; -fx-border-width: 2; -fx-border-radius: 15 15 15 15; -fx-background-radius: 15 15 15 15;");
            username.setStyle("-fx-text-fill: #E1E1E1; -fx-font-family: 'Roboto Slab'; -fx-font-weight: bold; -fx-font-size: 16px;-fx-padding: 10px;-fx-background-radius: 5px;");
            addButton.setStyle("-fx-background-color: #333333; -fx-text-fill: #E1E1E1; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px; -fx-border-color: #E1E1E1; -fx-border-radius: 15px; -fx-background-radius: 15px;");

            //Date
            date.setStyle("-fx-background-color: #333333; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
            dateTimeLabel.setStyle("-fx-background-color: #333333;-fx-font-size: 16px; -fx-text-fill: #E1E1E1; -fx-font-family: 'Roboto Slab'; -fx-font-weight: bold; -fx-padding: 9px;");

            //ProgressBar
            progressText.setStyle("-fx-font-size: 16; -fx-font-family: 'Roboto Slab'; -fx-font-weight: bold; -fx-text-fill: #ab8f02;");
            progressLabel.setStyle("-fx-font-size: 16; -fx-font-family: 'Roboto Slab';-fx-font-weight: bold; -fx-text-fill: #ab8f02;");
            progressBar.setStyle("-fx-control-inner-background: linear-gradient(to right, #333333, #ab8f02); -fx-accent: #ab8f02; -fx-border-color: #ab8f02; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");

            //Task Buttons and List
            addTaskButton.setStyle("-fx-background-color: #333333; -fx-text-fill: #E1E1E1; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 7px; -fx-border-color: #ab8f02; -fx-border-radius: 15px; -fx-background-radius: 15px;");
            editTaskButton.setStyle("-fx-background-color: #333333; -fx-text-fill: #E1E1E1; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 7px; -fx-border-color: #ab8f02; -fx-border-radius: 15px; -fx-background-radius: 15px;");
            deleteTaskButton.setStyle("-fx-background-color: #333333; -fx-text-fill: #E1E1E1; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 7px; -fx-border-color: #ab8f02; -fx-border-radius: 15px; -fx-background-radius: 15px;");
            taskLabel.setStyle("-fx-background-color: #333333; -fx-text-fill: #E1E1E1; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 7px; -fx-border-color: #ab8f02; -fx-border-radius: 15px; -fx-background-radius: 15px;");
            taskListView.setStyle("-fx-background-color: #333333;");

            //Quotes
            quote1.setStyle("-fx-background-color: #333333; -fx-effect: dropshadow(gaussian, #ab8f0270, 10, 0.5, 5, 5);-fx-border-color : #ab8f02; -fx-border-width : 1;-fx-border-radius : 7;-fx-padding: 10; -fx-background-radius: 7;");
            quote2.setStyle("-fx-background-color: #333333; -fx-effect: dropshadow(gaussian, #ab8f0270, 10, 0.5, 5, 5);-fx-border-color : #ab8f02; -fx-border-width : 1;-fx-border-radius : 7;-fx-padding: 10; -fx-background-radius: 7;");
            quote3.setStyle("-fx-background-color: #333333; -fx-effect: dropshadow(gaussian, #ab8f0270, 10, 0.5, 5, 5);-fx-border-color : #ab8f02; -fx-border-width : 1;-fx-border-radius : 7;-fx-padding: 10; -fx-background-radius: 7;");

            quote1Text.setStyle("-fx-font-size: 14; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-family: Roboto Slab; -fx-border-color : #33335033");
            quote2Text.setStyle("-fx-font-size: 14; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-family: Roboto Slab; -fx-border-color : #33335033");
            quote3Text.setStyle("-fx-font-size: 14; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-font-family: Roboto Slab; -fx-border-color : #33335033");
        }
        saveTasksToFile(currentUser);
        taskListView.refresh(); // Refresh ListView if necessary
    }

    @Override
    public void loadUserData() {
        try {
            java.io.File file = new java.io.File("data/user.txt");
            if (file.exists()) {
                try (java.util.Scanner scanner = new java.util.Scanner(file)) {
                    if (scanner.hasNext()) {
                        currentUser = scanner.nextLine();  // Save current user
                        username.setText(username.getText() + ", " + currentUser);
                        loadTasksFromFile(currentUser); // Load tasks for this user
                        sortTasks(); // Ensure tasks remain sorted
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace(); // Debugging
            showError("Failed to load user data.");
        }
    }

    private void showError(String message) {
        // A helper method to display error messages
        System.err.println(message);
        // You can also use an alert box or label to show error messages to the user
    }

    @Override
    public void startClock()
    {
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateDateTime()));
        clock.setCycleCount(Timeline.INDEFINITE);//To run indefinitely
        clock.play();
    }
    @FXML
    private void updateDateTime()
    {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM, yyyy\t\thh : mm a", Locale.ENGLISH);
        dateTimeLabel.setText(now.format(formatter));
    }


    // Switch between light and dark themes

    @FXML
    protected void handleButtonAction()
    {

        if (mode)
        {
            // Light mode
            borderpane.setStyle("-fx-background-color: #E1E1E1; -fx-padding: 30;");
            header.setStyle("-fx-background-color: #E1E1E1; -fx-effect: dropshadow(gaussian, #106b6470, 10, 0.5, 5, 5); -fx-border-color: #106b6480; -fx-border-width: 2; -fx-border-radius: 15 15 15 15; -fx-background-radius: 15 15 15 15;");
            username.setStyle("-fx-text-fill: #106b64; -fx-font-family: 'Roboto Slab'; -fx-font-weight: bold; -fx-font-size: 16px;-fx-padding: 10px; -fx-background-radius: 5px;");
            addButton.setStyle("-fx-background-color: #E1E1E1; -fx-text-fill: #106b64; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px; -fx-border-color: #106b64; -fx-border-radius: 15px; -fx-background-radius: 15px;");
            date.setStyle("-fx-background-color: #E1E1E1; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
            dateTimeLabel.setStyle("-fx-background-color: #E1E1E1;-fx-font-size: 16px; -fx-text-fill: #106b64; -fx-font-family: 'Roboto Slab'; -fx-font-weight: bold; -fx-padding: 9px;");

            //Progress Bar
            progressText.setStyle("-fx-font-size: 16; -fx-font-family: 'Roboto Slab'; -fx-font-weight : bold;-fx-text-fill: #106b64;");
            progressLabel.setStyle("-fx-font-size: 16; -fx-font-family: 'Roboto Slab'; -fx-font-weight : bold; -fx-text-fill: #106b64;");
            progressBar.setStyle("-fx-control-inner-background: linear-gradient(to right, #e6e6e6, #ffffff); -fx-accent: #106b64; -fx-border-color: #106b64; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;" );

            //Task Buttons
            addTaskButton.setStyle("-fx-background-color: #E1E1E1; -fx-text-fill: #106b64; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 7px; -fx-border-color: #106b64; -fx-border-radius: 15px; -fx-background-radius: 15px;");
            editTaskButton.setStyle("-fx-background-color: #E1E1E1; -fx-text-fill: #106b64; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 7px; -fx-border-color: #106b64; -fx-border-radius: 15px; -fx-background-radius: 15px;");
            deleteTaskButton.setStyle("-fx-background-color: #E1E1E1; -fx-text-fill: #106b64; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 7px; -fx-border-color: #106b64; -fx-border-radius: 15px; -fx-background-radius: 15px;");

            //Left Side Bar
            taskLabel.setStyle("-fx-background-color: #E1E1E1; -fx-text-fill: #106b64; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 7px; -fx-border-color: #106b64; -fx-border-radius: 15px; -fx-background-radius: 15px;");

            //Task List
            taskListView.setStyle("-fx-background-color: #E1E1E1; -fx-text-fill: #106b64; -fx-font-family: 'Roboto Slab'; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 6px; -fx-border-color: #106b64; -fx-border-radius: 15px; -fx-background-radius: 15px;");

        }
        else
        {
            // Dark mode
            borderpane.setStyle("-fx-background-color: #333333; -fx-padding: 30;");
            header.setStyle("-fx-background-color: #333333; -fx-effect: dropshadow(gaussian, #00000070, 10, 0.5, 5, 5); -fx-border-color: #55555580; -fx-border-width: 2; -fx-border-radius: 15 15 15 15; -fx-background-radius: 15 15 15 15;");
            username.setStyle("-fx-text-fill: #E1E1E1; -fx-font-family: 'Roboto Slab'; -fx-font-weight: bold; -fx-font-size: 16px;-fx-padding: 10px;-fx-background-radius: 5px;");
            addButton.setStyle("-fx-background-color: #333333; -fx-text-fill: #E1E1E1; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 10px; -fx-border-color: #E1E1E1; -fx-border-radius: 15px; -fx-background-radius: 15px;");
            date.setStyle("-fx-background-color: #333333; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
            dateTimeLabel.setStyle("-fx-background-color: #333333;-fx-font-size: 16px; -fx-text-fill: #E1E1E1; -fx-font-family: 'Roboto Slab'; -fx-font-weight: bold; -fx-padding: 9px;");

            //Progress Bar
            progressText.setStyle("-fx-font-size: 16; -fx-font-family: 'Roboto Slab'; -fx-font-weight: bold; -fx-text-fill: #E1E1E1;");
            progressLabel.setStyle("-fx-font-size: 16; -fx-font-family: 'Roboto Slab';-fx-font-weight: bold;  -fx-text-fill: #E1E1E1;");
            progressBar.setStyle("-fx-control-inner-background: linear-gradient(to right, #333333, #ffffff);; -fx-accent: #ffffff; -fx-border-color: #E1E1E1; -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;" );

            //Task Buttons
            addTaskButton.setStyle("-fx-background-color: #333333; -fx-text-fill: #E1E1E1; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 7px; -fx-border-color: #E1E1E1; -fx-border-radius: 15px; -fx-background-radius: 15px;");
            editTaskButton.setStyle("-fx-background-color: #333333; -fx-text-fill: #E1E1E1; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 7px; -fx-border-color: #E1E1E1; -fx-border-radius: 15px; -fx-background-radius: 15px;");
            deleteTaskButton.setStyle("-fx-background-color: #333333; -fx-text-fill: #E1E1E1; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 7px; -fx-border-color: #E1E1E1; -fx-border-radius: 15px; -fx-background-radius: 15px;;");

            //left SideBar
            taskLabel.setStyle("-fx-background-color: #333333; -fx-text-fill: #E1E1E1; -fx-font-family: 'Roboto Slab';-fx-font-size: 16px; -fx-font-weight: bold; -fx-padding: 7px; -fx-border-color: #E1E1E1; -fx-border-radius: 15px; -fx-background-radius: 15px;");

            //TaskList
            taskListView.setStyle("-fx-background-color: #333333; -fx-text-fill: #E1E1E1; -fx-font-family: 'Roboto Slab'; -fx-font-size: 12px; -fx-font-weight: bold; -fx-padding: 6px; -fx-border-color: #106b64; -fx-border-radius: 15px; -fx-background-radius: 15px;");

        }
        mode = !mode; // Toggle the mode
    }




    // Task management: Add a new task
    @FXML
    private void handleAddTask() {
        Dialog<Task> dialog = createAddTaskDialog();
        dialog.showAndWait().ifPresent(task -> {
            tasks.add(task);
            sortTasks(); // Ensure tasks remain sorted
            updateProgress();
            saveTasksToFile(currentUser); // Save tasks for the logged-in user
        });
    }

    // Task management: Delete selected task
    @FXML
    private void handleDeleteTask() {
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();
        if (selectedTask != null) {
            tasks.remove(selectedTask);
            sortTasks(); // Ensure tasks remain sorted
            updateProgress();
            saveTasksToFile(currentUser); // Save changes for the user
        } else {
            showAlert("No Task Selected", "Please select a task to delete.", Alert.AlertType.WARNING);
        }
    }

    // Update progress bar based on completed tasks
    private void updateProgress() {
        if (tasks.isEmpty()) {
            progressBar.setProgress(0);
            progressLabel.setText("0%");
            return;
        }

        long completedTasks = tasks.stream().filter(Task::isCompleted).count();
        double progress = (double) completedTasks / tasks.size();
        progressBar.setProgress(progress);
        progressLabel.setText(String.format("%.0f%%", progress * 100));
    }

    // Create a dialog for adding a new task
    private Dialog<Task> createAddTaskDialog() {
        Dialog<Task> dialog = new Dialog<>();
        dialog.setTitle("Add Task");

        TextField descriptionField = new TextField();
        descriptionField.setPromptText("Task Description");

        ComboBox<String> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll("High", "Medium", "Low");
        priorityBox.setPromptText("Select Priority");

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Select Date");

        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty)
            {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now()))
                {
                    setDisable(true);
                    setStyle("-fx-background-color: #d3d3d3;");
                }
            }
        });

        TextArea notesArea = new TextArea();
        notesArea.setPromptText("Additional Notes");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Description:"), 0, 0);
        grid.add(descriptionField, 1, 0);
        grid.add(new Label("Priority:"), 0, 1);
        grid.add(priorityBox, 1, 1);
        grid.add(new Label("Date:"), 0, 2);
        grid.add(datePicker, 1, 2);
        grid.add(new Label("Notes:"), 0, 3);
        grid.add(notesArea, 1, 3);

        dialog.getDialogPane().setContent(grid);

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                String description = descriptionField.getText();
                String priority = priorityBox.getValue();
                String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : null;
                String notes = notesArea.getText();

                if (description.isEmpty() || priority == null) {
                    showAlert("Incomplete Data", "Please fill all mandatory fields.", Alert.AlertType.ERROR);
                    return null;
                }

                return new Task(description, priority, date, notes);
            }
            return null;
        });

        return dialog;
    }

    // Display an alert
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @Override
    public void saveTasksToFile(String username) {
        try {
            java.io.File directory = new java.io.File("data");
            if (!directory.exists()) {
                directory.mkdir(); // Create the directory if it doesn't exist
            }

            java.io.PrintWriter writer = new java.io.PrintWriter("data/tasks_" + username + ".txt");
            writer.println(mode);
            for (Task task : tasks) {
                writer.println(task.getDescription() + ";" +
                        task.getPriority() + ";" +
                        task.getDate() + ";" +
                        task.getNotes() + ";" +
                        task.isCompleted());
            }
            writer.close(); // Always close the writer after use
        } catch (IOException e) {
            e.printStackTrace(); // Log the error for debugging
            showAlert("File Save Error", "There was an error saving the tasks to the file.", Alert.AlertType.ERROR);
        }
    }

    @Override
    public void loadTasksFromFile(String username) {
        tasks.clear(); // Clear the current tasks

        java.io.File file = new java.io.File("data/tasks_" + username + ".txt");
        if (!file.exists()) {
            return; // If no file exists for this user, return early
        }

        try (java.util.Scanner scanner = new java.util.Scanner(file))
        {
            if(scanner.hasNextLine())
            {
                mode = Boolean.parseBoolean(scanner.nextLine().trim());//Saving Mode Value for theme(true or False)
            }
            while (scanner.hasNextLine())
            {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue; // Skip empty lines
                }

                String[] taskData = line.split(";");
                if (taskData.length == 5) {
                    String description = taskData[0];
                    String priority = taskData[1];
                    String date = taskData[2].equals("null") ? null : taskData[2];
                    String notes = taskData[3];
                    boolean completed = Boolean.parseBoolean(taskData[4]);

                    tasks.add(new Task(description, priority, date, notes, completed));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            showAlert("File Load Error", "Error occurred while loading tasks.", Alert.AlertType.ERROR);
        }

        // Update progress bar and label after loading tasks
        updateProgress();
    }

    @FXML
    public void handleEditTask(ActionEvent actionEvent) {
        // Get the selected task from the ListView
        Task selectedTask = taskListView.getSelectionModel().getSelectedItem();

        if (selectedTask == null) {
            showAlert("No Task Selected", "Please select a task to edit.", Alert.AlertType.WARNING);
            return;  // Exit if no task is selected
        }

        // Create the edit task dialog
        Dialog<Task> dialog = createEditTaskDialog(selectedTask);
        dialog.showAndWait().ifPresent(updatedTask -> {
            // Update the task in the list
            int selectedIndex = taskListView.getSelectionModel().getSelectedIndex();
            tasks.set(selectedIndex, updatedTask);
            updateProgress();  // Recalculate progress after editing
            saveTasksToFile(currentUser);  // Save tasks after editing
        });
    }

    private Dialog<Task> createEditTaskDialog(Task task) {
        Dialog<Task> dialog = new Dialog<>();
        dialog.setTitle("Edit Task");

        // Create the fields for editing the task
        TextField descriptionField = new TextField(task.getDescription());
        descriptionField.setPromptText("Task Description");

        ComboBox<String> priorityBox = new ComboBox<>();
        priorityBox.getItems().addAll("High", "Medium", "Low");
        priorityBox.setValue(task.getPriority());  // Set the current priority
        priorityBox.setPromptText("Select Priority");

        DatePicker datePicker = new DatePicker();
        if (task.getDate() != null) {
            datePicker.setValue(LocalDate.parse(task.getDate()));  // Set the current date
        }
        datePicker.setPromptText("Select Date");

        // Prevent selecting past dates
        datePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isBefore(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #d3d3d3;");
                }
            }
        });

        TextArea notesArea = new TextArea(task.getNotes());
        notesArea.setPromptText("Additional Notes");

        // Create the layout for the dialog
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        grid.add(new Label("Description:"), 0, 0);
        grid.add(descriptionField, 1, 0);
        grid.add(new Label("Priority:"), 0, 1);
        grid.add(priorityBox, 1, 1);
        grid.add(new Label("Date:"), 0, 2);
        grid.add(datePicker, 1, 2);
        grid.add(new Label("Notes:"), 0, 3);
        grid.add(notesArea, 1, 3);

        dialog.getDialogPane().setContent(grid);

        // Add buttons to the dialog
        ButtonType updateButtonType = new ButtonType("Update", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(updateButtonType, ButtonType.CANCEL);

        // Handle the dialog result
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == updateButtonType) {
                String description = descriptionField.getText();
                String priority = priorityBox.getValue();
                String date = (datePicker.getValue() != null) ? datePicker.getValue().toString() : null;
                String notes = notesArea.getText();

                if (description.isEmpty() || priority == null) {
                    showAlert("Incomplete Data", "Please fill all mandatory fields.", Alert.AlertType.ERROR);
                    return null;
                }

                return new Task(description, priority, date, notes, task.isCompleted()); // Return the updated task
            }
            return null;
        });

        return dialog;
    }

    private void sortTasks() {
        FXCollections.sort(tasks, (task1, task2) -> {
            // Define priority order
            int priority1 = getPriorityValue(task1.getPriority());
            int priority2 = getPriorityValue(task2.getPriority());

            // Sort by priority, and optionally by date if priorities are equal
            int priorityComparison = Integer.compare(priority1, priority2);
            if (priorityComparison == 0 && task1.getDate() != null && task2.getDate() != null) {
                return task1.getDate().compareTo(task2.getDate());
            }
            return priorityComparison;
        });
    }

    private int getPriorityValue(String priority) {
        switch (priority) {
            case "High":
                return 1;
            case "Medium":
                return 2;
            case "Low":
                return 3;
            default:
                return Integer.MAX_VALUE; // Unknown priority sorts last
        }
    }

}
