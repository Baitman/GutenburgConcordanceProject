import java.io.Serializable;
import java.util.ArrayList;

/**
 * Word Class
 * 
 * @author Matthew Yengle
 */

public class Word implements Serializable {
    
        /**
         * Fields
         * 
         * String string - holds the word for the Word Object
         * int occurrenceCount - Keeps track of the number of occurrences of the word
         * ArrayList<Integer> listOfLines - Keeps track of each line number that word appears on
         * ArrayList<Integer> listWordNumber - Keeps track of each word number for the word
         */

        private String string;
        private int occurrenceCount = 0;   
        private ArrayList<Integer> listOfLines;
        private ArrayList<Integer> listWordNumber;
                
        public Word(String s) {
            /**
             * Constructor for Word class
             * sets string equal to the parameter s
             * Initializes listOfLines and listWordNumber
             * 
             * @param s the string value of the word for which the object is being mapped to 
             */
            this.string = s;
            listOfLines = new ArrayList<Integer>();
            listWordNumber = new ArrayList<Integer>();
        }
        
        /**
         * Getters and setters for fields 
         */

        public void setString(String s) {
            this.string = s;
        }

        public String getString() {
            return this.string;
        }
        
        public void incOccurrence(){
            occurrenceCount++;
        }
        
        public Integer getOccurrence(){
            return occurrenceCount;
        }
        
        public void addNewLine(int newLine){
                listOfLines.add(newLine+1);        
        }
        
        public ArrayList<Integer> getListOfLines(){
            return listOfLines;
        }
        
        public void addWordNumber(int wordNumber){
            listWordNumber.add(wordNumber);
        }
        
        public ArrayList<Integer> getWordNumber(){
            return listWordNumber;
        }      
    }
