package application;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		
		//initialize gui items
		Pane root = new Pane();
		root.setMinHeight(2000);
		root.setMinWidth(2000);
		VBox box = new VBox();
		VBox midBox1 = new VBox();
		VBox midBox2 = new VBox();
		HBox top = new HBox();
		HBox mid = new HBox();
		Label plainT = new Label("Plain Text");
		Label cipherT = new Label("Cipher Text");
		Label keyL = new Label("Key");
		TextArea plainText = new TextArea();
		TextArea cipherText = new TextArea();
		TextArea key = new TextArea();
		key.setPrefSize(100, 10);
		Scene scene = new Scene(root,500,400);
		RadioButton plainTextButton = new RadioButton("Extended by Plain Text");
		RadioButton cipherTextButton = new RadioButton("Extended by Cipher Text");
		Button encrypt = new Button("Encrypt");
		Button decrypt = new Button("Decrypt");
		midBox1.getChildren().addAll(plainT, plainText, cipherT, cipherText);
		midBox2.getChildren().addAll(encrypt, decrypt, keyL, key);
		top.getChildren().addAll(plainTextButton, cipherTextButton);
		mid.getChildren().addAll(midBox1, midBox2);
		box.getChildren().addAll(top, mid);
		root.getChildren().addAll(box);
		plainTextButton.setSelected(true);
		
		
		//initialize event handlers
		plainTextButton.setOnMouseClicked(me ->{
			cipherTextButton.setSelected(false);
			extend = 2;
		});
		cipherTextButton.setOnMouseClicked(me ->{
			plainTextButton.setSelected(false);
			extend = 1;
		});
		encrypt.setOnMouseClicked(me ->{
			String CT = AutoKeyEncipher(key.getText(), plainText.getText(), extend);
			cipherText.setText(CT);
		});
		decrypt.setOnMouseClicked(me ->{
			String PT = AutoKeyDecipher(key.getText(), cipherText.getText(), extend);
			plainText.setText(PT);
		});
		
		
		//display to gui
		primaryStage.setScene(scene);
		primaryStage.setTitle("Cryptography");
		primaryStage.show();
		
		
	}
	private int extend=2;
	

	public String AutoKeyEncipher(String key, String pt, int type) {
		//enciphers the input values using the auto key encryption
		pt = pt.toUpperCase();
		pt = pt.replace(" ", "");//delete all white space in the plain text
		key = key.replace(" ", "");//delete all white space in the key
		ArrayList<Character> ptChars = new ArrayList<Character>();
		for(int i=0; i<pt.length(); i++){
			ptChars.add(pt.charAt(i));//extract the plain text as char variables
		}
		for(int i=0; i<key.length(); i++){//use the key to encipher the plain text using the key
			int charOne = Character.getNumericValue(ptChars.get(i))-10;
			int charTwo = Character.getNumericValue((key.charAt(i)))-10;
			char c = (char) ((((charOne+charTwo)%26)+17)+'0');
			ptChars.set(i, c);
		}
		for(int i=key.length(); i<ptChars.size(); i++){//use the plain text to encipher the rest of the text via autokey encipher method
			int charOne=0;
			if(type==1){
				charOne = Character.getNumericValue(ptChars.get(i-key.length()))-10;
			}
			else if(type==2){
				charOne = Character.getNumericValue(pt.charAt((i-key.length())))-10;
			}
			int charTwo = Character.getNumericValue(ptChars.get(i))-10;
			char c = (char) ((((charOne+charTwo)%26)+17)+'0');
			ptChars.set(i, c);
		}
		//set pt to the new string of chars trimming out any unecessary white space and commas
		pt = ptChars.toString();
		pt = pt.substring(1, pt.length()-1);
		pt = pt.replace(",", "");
		pt = pt.replace(" ", "");
		return pt;
	}
	
	public String AutoKeyDecipher(String key, String ct, int type) {
		//deciphers the input using the auto key encryption
		ct = ct.toUpperCase();
		ct = ct.replace(" ", "");
		key = key.replace(" ", "");
		ArrayList<Character> ctChars = new ArrayList<Character>();
		for(int i=0; i<ct.length(); i++){//get the enciphered chars
			ctChars.add(ct.charAt(i));
		}
		ArrayList<Character> ptChars = ctChars;
		for(int i=0; i<key.length(); i++){//decipher the text using the key
			int charOne = Character.getNumericValue(ctChars.get(i))-10;
			int charTwo = 26-(Character.getNumericValue((key.charAt(i)))-10);
			char c = (char) ((((charOne+charTwo)%26)+17)+'0');
			ptChars.set(i, c);
		}
		for(int i=key.length(); i<ctChars.size(); i++){//decipher the rest of the text using the chars that were deciphered using the key
			int charOne=0;
			if(type==2){
				charOne = 26-(Character.getNumericValue(ctChars.get(i-key.length()))-10);
			}
			else if(type==1){
				charOne = 26-(Character.getNumericValue(ct.charAt((i-key.length())))-10);
			}
			int charTwo = Character.getNumericValue(ctChars.get(i))-10;
			char c = (char) ((((charOne+charTwo)%26)+17)+'0');
			ptChars.set(i, c);
		}
		//set ct to the newly deciphered plain text trimming out unecessary spaces and commas
		ct = ptChars.toString();
		ct = ct.substring(1, ct.length()-1);
		ct = ct.replace(",", "");
		ct = ct.replace(" ", "");
		return ct;
	}
	}