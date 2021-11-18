package com.example.paketstationgui;

import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Paketstation {
    private ArrayList<Fach> faecher = new ArrayList<>();
    private ArrayList<Integer> sendungsnummern = new ArrayList<>();
    private ArrayList<Paket> pakete = new ArrayList<>();

    public Paketstation(int faecher) {
        for (int i = 0; i < faecher; i++) {
            this.faecher.add(new Fach(Integer.toString(i + 1)));
        }
    }


    public String paketEinlagern(String empfaengerName) throws Exception{
        if (empfaengerName.trim().isEmpty()) {
            throw new Exception("Bitte geben Sie einen gültigen Namen oder eine Nummer ein");
        }
        if (pakete.size() == faecher.size()) {
            throw new Exception("Tut uns Leid, aber es ist kein Fächer frei!");
        }
        Kunde kunde = new Kunde(empfaengerName);
        Fach fach = getFirstEmptyFach();
        Paket paket = new Paket(generateSendugnsnummer(this.faecher.size()), kunde, fach.getId());
        this.pakete.add(paket);
        fach.setPaket(paket);
        return "Paket wurde eingelagert.";
    }

    public String paketEntnehmen(String empfaengerNameoderSendungsnummer) throws Exception {
        String output = "";
        if (empfaengerNameoderSendungsnummer.trim().isEmpty()) {
            throw new Exception("Bitte geben Sie einen gültigen Namen oder eine Nummer ein");
        }
        ArrayList<Paket> paketezuentnehmen = this.findPaket(empfaengerNameoderSendungsnummer);
        if (paketezuentnehmen.isEmpty()) {
            throw new Exception("Es wurde kein Paket gefunden");
        }

        ArrayList<String> sendungsNummern = new ArrayList<String>();
        String empfaenger = paketezuentnehmen.get(0).getEmpfaenger().getName();

        for (Paket paket : paketezuentnehmen) {
            Fach fachZuEntleeren = this.findFach(paket);
            sendungsNummern.add(paket.getSendungsnummer());
            fachZuEntleeren.setEmpty();
            int index = sendungsnummern.indexOf(Integer.parseInt(paket.getSendungsnummer()));
            sendungsnummern.remove(index);
            pakete.remove(paket);
        }
        output = this.entnehmenOutput(sendungsNummern, empfaenger);
        return output;
    }
    public StringBuilder listenPakete() {

        StringBuilder paketList = new StringBuilder();

        String header = String.format(Locale.GERMANY, "%-15s\n\r\n%-15s%-15s%-15s\r\n", "Pakete der Paketstation",
                "Fach", "Empfänger", "Sendungsnr");
        paketList.append(header);

        for (Fach fach : this.faecher) {
            String message = String.format(Locale.GERMANY, "\n%-18s%-25s%-18s", fach.getId(),
                    (fach.getPaket() != null) ? fach.getPaket().getEmpfaenger().getName() : "leer",
                    (fach.getPaket() != null) ? fach.getPaket().getSendungsnummer() : "-");
            paketList.append(message);
        }
        return paketList;
    }

    public String entnehmenOutput(ArrayList<String> sendungsNummern, String empfaenger) {
        sendungsNummern.add(sendungsNummern.size()- 1, "und");


//        if (sendungsNummern.size() == 2) {
//            sendungsNummern.add(sendungsNummern.size()- 1, "und");
//        } else {
//            sendungsNummern.add(sendungsNummern.size() - 1, "und");
//        }
        String sendungsNummernformatted = sendungsNummern.toString().replace("[", "").replace("]", "");
        StringBuilder string = new StringBuilder();
        System.out.toString();
        String message = String.format(Locale.GERMANY, "Paket mit der Nr. %s  von %s entnommen",
                sendungsNummernformatted, empfaenger);
        string.append(message);
        return string.toString();

    }

    private String generateSendugnsnummer(int maximum) {
        int min = 1;
        int max = maximum;
        int random_sendungsnummer = (int) Math.floor(Math.random() * (max - min + 1) + min);

        while (sendungsnummerExists(random_sendungsnummer)) {
            random_sendungsnummer = (int) Math.floor(Math.random() * (max - min + 1) + min);
        }

        this.sendungsnummern.add(random_sendungsnummer);
        return Integer.toString(random_sendungsnummer);
    }

    private boolean sendungsnummerExists(int sendungsnummer) {
        boolean exists = this.sendungsnummern.contains(sendungsnummer);
        return exists;
    }

    private Fach getFirstEmptyFach() {
        Fach empty = faecher.stream().filter(fach -> fach.getEmpty() == true).findAny().orElse(null);
        return empty;
    }

    private ArrayList<Paket> findPaket(String empfaengerNameoderSendungsnummer) {
        Stream<Paket> paket = pakete.stream()
                .filter(findPaket -> findPaket.getEmpfaenger().getName().equals(empfaengerNameoderSendungsnummer)
                        || findPaket.getSendungsnummer().equals(empfaengerNameoderSendungsnummer));
        ArrayList<Paket> paketeArray = paket.collect(Collectors.toCollection(ArrayList::new));
        return paketeArray;
    }

    private Fach findFach(Paket paket) {
        Fach fach = faecher.stream().filter(findFach -> findFach.getId().equals(paket.getFachId())).findAny()
                .orElse(null);
        return fach;
    }
}