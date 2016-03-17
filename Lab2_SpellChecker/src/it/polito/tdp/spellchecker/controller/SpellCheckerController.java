package it.polito.tdp.spellchecker.controller;

import it.polito.tdp.spellchecker.model.EnglishDictionary;
import it.polito.tdp.spellchecker.model.ItalianDictionary;
import it.polito.tdp.spellchecker.model.RichWord;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class SpellCheckerController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lblTime;

    @FXML
    private TextArea txtTesto;

    @FXML
    private Label lblErrors;

    @FXML
    private Button btnSpell;

    @FXML
    private Button btnReset;

    @FXML
    private ComboBox<String> comboLanguage;

    @FXML
    private TextArea txtControllo;

    @FXML
    void doSpell(ActionEvent event) {
    	
    	List<String> inputText = new LinkedList<String>();
    	List<RichWord> outputText = new LinkedList<RichWord>();
    	
    	String testo = txtTesto.getText().toLowerCase();
    	for(String s : testo.split("[ \\p{Punct}]")){
    		inputText.add(s);
    	}
    	
    	long time = System.currentTimeMillis();
    	double seconds = 0;
    	
    	String language = comboLanguage.getValue();
    	if(language.compareTo("Italian") == 0){
    		outputText = italian.spellCheckText(inputText);
    		time = (System.currentTimeMillis()-time);
    		seconds = time * 0.001;
    		
    	}else if(language.compareTo("English") == 0){
    		outputText = english.spellCheckText(inputText);
    		time = (System.currentTimeMillis()-time);
    		seconds = time * 0.001;
    	} 
    	
    	for(RichWord word: outputText){
    		boolean corretta = word.isCorretta();
    		if(!corretta){
    		txtControllo.appendText(word.getParola()+" ");
    		lblErrors.setVisible(true);
    		lblErrors.setTextFill(javafx.scene.paint.Color.RED);
    		lblErrors.setText("Your text contains errors!");
    		}else
    			txtControllo.appendText(word.getParola()+" ");
    	}
    	
    	lblTime.setVisible(true);
    	lblTime.setText(String.format("Spell check completed in %.2f seconds", seconds));
    	

    }

    @FXML
    void doReset(ActionEvent event) {

    	txtTesto.clear();
    	txtControllo.clear();
    	txtControllo.setWrapText(true);
    	lblErrors.setVisible(false);
    	lblTime.setVisible(false);
    	
    }

    @FXML
    void initialize() {
        assert lblTime != null : "fx:id=\"lblTime\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtTesto != null : "fx:id=\"txtTesto\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert lblErrors != null : "fx:id=\"lblErrors\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnSpell != null : "fx:id=\"btnSpell\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert comboLanguage != null : "fx:id=\"comboLanguage\" was not injected: check your FXML file 'SpellChecker.fxml'.";
        assert txtControllo != null : "fx:id=\"txtControllo\" was not injected: check your FXML file 'SpellChecker.fxml'.";

        comboLanguage.getItems().add("Italian");
        comboLanguage.getItems().add("English");
    }

    private ItalianDictionary italian;
    private EnglishDictionary english;
    
	public void setModel(ItalianDictionary model) {
		this.italian = model;	
	}
	
	public void setModel(EnglishDictionary model) {
		this.english = model;	
	}
}

