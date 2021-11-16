package com.example.paketstationgui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Fenster extends Application {

    private TextField myTextInput = new TextField();
    private TextArea myTextOutput = new TextArea();
    private Label myLabelEmpfaenger = new Label("Empfaenger:");
    private Label message = new Label();
    private Button myButtonEinfuegen;
    private Button myButtonEntnehmen;
    private Button myButtonListe;

    public TextArea getMyTextOutput() {
        return myTextOutput;
    }

    public Label getMessage() {
        return message;
    }

    public TextField getMyTextInput() {
        return myTextInput;
    }

    public Stage getStage() {
        return stage;
    }

    private Stage stage;

    public Button getMyButtonEinfuegen() {
        return myButtonEinfuegen;
    }

    public Button getMyButtonEntnehmen() {
        return myButtonEntnehmen;
    }

    public Button getMyButtonListe() {
        return myButtonListe;
    }

    public Button getMyButtonEnde() {
        return myButtonEnde;
    }

    private Button myButtonEnde;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        this.stage = primaryStage;
        primaryStage.setTitle("Paketstation");
        StackPane root = new StackPane();
        Scene myScene = new Scene(root, 600, 400);

        // Big Pane
        BorderPane myWindow = new BorderPane();
        myWindow.setTop(addMyBorderPane());
        myWindow.setCenter(this.myTextOutput);
        this.message.setPadding(new Insets(5));
        myWindow.setBottom(this.message);
        root.getChildren().add(myWindow);
        primaryStage.setScene(myScene);
        primaryStage.show();
        this.myTextInput.setPromptText("Bitte Empfänger oder Nummer eingeben...");

        Presenter.start(this);

    }

    public BorderPane addMyBorderPane() {
       BorderPane pane =  new BorderPane();
       pane.setPadding(new Insets(5));
       pane.setCenter(this.myTextInput);
       BorderPane paneText = new BorderPane();
       paneText.setPadding(new Insets(5, 10, 5, 5));
       paneText.setCenter(this.myLabelEmpfaenger);
       pane.setLeft(paneText);
       pane.setRight(this.getVBox());
       return pane;
    }

    public VBox getVBox() {

        VBox vBox = new VBox(8);
        this.myButtonEinfuegen = new Button("Einfuegen");
        this.myButtonEntnehmen = new Button("Entnehmen");
        this.myButtonListe = new Button("Liste");
        this.myButtonEnde = new Button("Abbrechen");

        myButtonEnde.setPrefWidth(120);
        myButtonEinfuegen.setPrefWidth(120);
        myButtonListe.setPrefWidth(120);
        myButtonEntnehmen.setPrefWidth(120);

        vBox.getChildren().addAll(myButtonEinfuegen, myButtonEnde, myButtonEntnehmen, myButtonListe);
        vBox.setPadding(new Insets(10));
        return vBox;
    }
}
