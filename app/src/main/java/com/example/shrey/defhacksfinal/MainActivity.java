package com.example.shrey.defhacksfinal;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
        RelativeLayout parentLayout;
        private Button tapButton;
        private ArrayList<String> values = new ArrayList<String>();
        ArrayList<String> favoriteList = new ArrayList<String>();
        int currentFavorite = 0;

        String txtMessage = "HI";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            tapButton = (Button) findViewById(R.id.tapButton);
            parentLayout = (RelativeLayout)findViewById(R.id.layout);
            favoriteList.add("7325994131");
            favoriteList.add("7328513752");
            favoriteList.add("7325265443");

            parentLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    values.add(".");
                    Toast.makeText(getApplicationContext(), "dot", Toast.LENGTH_SHORT).show();
                    System.out.println(values);
                }
            });

            parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(getApplicationContext(), "dash", Toast.LENGTH_SHORT).show();
                    values.add("-");
                    return true;
                }
            });

            tapButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), "space", Toast.LENGTH_SHORT).show();
                    values.add("/");
                    System.out.println(values);

                }
            });


        }




        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if ((keyCode == KeyEvent.KEYCODE_VOLUME_DOWN)) {     //Send message
                sendSMSMessage();
                values.clear();
            }
            if ((keyCode == KeyEvent.KEYCODE_VOLUME_UP)) {       //Go to next favorite number
                currentFavorite++;
                if (currentFavorite >= favoriteList.size()) {
                    currentFavorite = 0;
                }
                Toast.makeText(getApplicationContext(), "Current Favorite = " + currentFavorite, Toast.LENGTH_SHORT).show();
            }
            return true;
        }


        protected void sendSMSMessage() {
            Log.i("Send SMS", "");
            //String phoneNo = txtphoneNo.getText().toString();
            String phoneNo = favoriteList.get(currentFavorite);
            String message = toEnglish(values);
            System.out.println(message);


            try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(phoneNo, null, message, null, null);
                Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        }

        public static String[] morse = {".-", "-...",  "-.-.", "-..",  ".", "..-.",
                "--.",   "....",  "..",   ".---", "-.-",  ".-..",
                "--",    "-.",    "---",  ".--.", "--.-", ".-.",
                "...",   "-",    "..-",  "...-", ".--",  "-..-",
                "-.--",  "--..", "/", ".-.-.-", "--..--", "---...",
                "..--..", ".----.", ".-..-.", "-----", ".----", "..---",
                "...--", "....-", ".....", "-....", "--...", "---..", "----."};
        public static String[] text = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
                "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y",
                "Z", " ", ".", ",", ":", "?", "'", "\"", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};

        public static String toEnglish (List<String> morseText){

            List<String> newMorseText = new ArrayList<String>();

            String word ="";
            for (int i = 0; i < morseText.size(); i++){

                if (morseText.get(i) != "/"){
                    word += morseText.get(i);
                    System.out.println(word);

                }
                if (morseText.get(i).equals("/") || i == morseText.size()-1){
                    newMorseText.add(word);
                    word = "";
                }
            }

            morseText = newMorseText;

            StringBuilder sb = new StringBuilder();
            String finalMessage = "";

            for (int i = 0; i < morseText.size(); i++){       //go through user inputted morse code
                for (int j = 0; j < morse.length; j++){
                    if (morseText.get(i).equals(morse[j])){
                        sb.append(text[j]);
                    }
                }
            }

            finalMessage = sb.toString();

            return finalMessage;
        }


    }


//clear button
//swipe for space
//receive messages through app
//unicode faces with special character and then cycling through
