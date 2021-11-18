package com.example.paketstationgui;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.input.*;
import javafx.scene.paint.Color;

public class Presenter implements EventHandler {
    private Paketstation myStation;
    private Fenster f;


    Presenter(Fenster f) {
        this.myStation = new Paketstation(8);
        this.f = f;
        this.initialisierenEventHandler();

    }

    public static void start(Fenster f) {
        new Presenter(f);
    }

    public void initialisierenEventHandler() {
        f.getMyButtonEinfuegen().addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        f.getMyButtonEnde().addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        f.getMyButtonEntnehmen().addEventHandler(MouseEvent.MOUSE_CLICKED, this);
        f.getMyButtonListe().addEventHandler(MouseEvent.MOUSE_CLICKED, this);
    }
    public void entnehmen() {
        String empfaengerNameoderSendungsNummer = f.getMyTextInput().getText();
        try {
            this.f.getMessage().setTextFill(Color.GREEN);
            this.f.getMessage().setText(this.myStation.paketEntnehmen(empfaengerNameoderSendungsNummer));
        } catch (Exception e) {
            this.f.getMessage().setTextFill(Color.RED);
            this.f.getMessage().setText(e.getMessage());

        }
//        setTimeout(() -> this.f.getMyTextInput().setText(""), 1500);
    }

    public void einfuegen() {
        String empfaengerName = f.getMyTextInput().getText();
        try {
            this.f.getMessage().setTextFill(Color.GREEN);
            this.f.getMessage().setText(this.myStation.paketEinlagern(empfaengerName));
//            setTimeout(()-> this.f.getMyTextInput().setText(""), 1500);
        } catch (Exception e) {
            this.f.getMessage().setTextFill(Color.RED);
            this.f.getMessage().setText(e.getMessage());
        }
//        setTimeout(() -> this.f.getMyTextInput().setText(""), 1500);

    }

    public void auflisten() {
        this.f.getMyTextOutput().setText(this.myStation.listenPakete().toString());
//        setTimeout(()-> this.f.getMyTextOutput().setText(""), 5000);
    }

    public void beenden() {
        f.getStage().close();
    }

    @Override
    public void handle(Event event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_CLICKED)) {
            if (event.getSource().equals(f.getMyButtonEinfuegen())) {
                this.einfuegen();
            }
            ;
            if (event.getSource().equals(f.getMyButtonEnde())) {
                this.beenden();
            }
            if (event.getSource().equals(f.getMyButtonEntnehmen())) {
                this.entnehmen();
            }
            if (event.getSource().equals(f.getMyButtonListe())) {
                this.auflisten();
            }

        }
    }

    public static void setTimeout(Runnable runnable, int delay) {
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            } catch (Exception e) {
                System.err.println(e);
            }
        }).start();
    }
}
