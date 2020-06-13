package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class myApplication extends Application {

    //the start method is abstract and MUST be overided when making a new javafx app
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("HISTOGAM OF THE YEAR build.2137");
        stage.setWidth(800);
        stage.setHeight(600);
        TextArea inputField = new TextArea();
        TextArea histogramArea = new TextArea();
        inputField.setPromptText("Tutaj wpisz tekst do dokonania histogramu");
        inputField.setFocusTraversable(false); //focus
        histogramArea.setPromptText("Tutaj pojawi się twój histogram");
        //Buttons
        Button button1 = new Button();
        button1.setText("DOKONAJ HISTOGRAMU");
        button1.setWrapText(true);

        Button button2 = new Button();
        button2.setText("ZAPISZ HISTOGRAM DO PLIKU");
        button2.setWrapText(true);

        Button button3 = new Button();
        button3.setText("HISTOGRAM BEZ PRZYIMKÓW");
        button3.setWrapText(true);

        button1.setOnAction(e -> {
            histogramArea.setText(show(inputField.getText()));
        });

        button2.setOnAction(e -> {
            try {
                fileWriter(show(inputField.getText()));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        button3.setOnAction(e -> {
            histogramArea.setText(showNoPrzyimki(inputField.getText()));
        });


        VBox root = new VBox(inputField, histogramArea, button1, button2, button3);

        root.getChildren();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    String show(String word) {
        word = word.toLowerCase();
        word = word.replaceAll("\\.", "");
        String[] stops = {",", ":", "-","\n", "   ", "  "};
        for (int i = 0; i < stops.length; i++) {
            word = word.replaceAll(stops[i], " ");
        }
        ArrayList<String> list = new ArrayList<String>();
        for (String wordd : word.split(" ")) {
            list.add(wordd);
        }
        Map<String, Integer> hm = new TreeMap<String, Integer>();

        for (String i : list) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }

        // displaying the occurrence of elements in the arraylist
        return hm.toString();
    }

    void fileWriter(String word) throws java.io.IOException {
        word = word.toLowerCase();
        FileWriter writer = new FileWriter("histogram.txt", true);
        PrintWriter output = new PrintWriter(writer);
        output.print(word);
        output.close();
    }

    String showNoPrzyimki(String word) {
        word = word.toLowerCase();
        word = word.replaceAll("\\.", "");
        String[] stops = {",", ":", "-","\n", "   ", "  "};
        for (int i = 0; i < stops.length; i++) {
            word = word.replaceAll(stops[i], " ");
        }
        ArrayList<String> list = new ArrayList<String>();
        ArrayList<String> list2 = list;
        for (String wordd : word.split(" ")) {
            list.add(wordd);
        }
        String[] przymki = {"z", "do", "na", "bez", "za", "pod", "u", "w", "nad", "o", "od", "po"};
        for (String temp : przymki) {
            list.removeIf(next -> next.equals(temp));           //bez remove if wywalalo java.util.ConcurrentModificationException
        }                                                       //bo jednocześnie iterując po liście usuwałem z niej elementy
        Map<String, Integer> hm = new TreeMap<String, Integer>();

        for (String i : list2) {
            Integer j = hm.get(i);
            hm.put(i, (j == null) ? 1 : j + 1);
        }

        return hm.toString();
    }

}