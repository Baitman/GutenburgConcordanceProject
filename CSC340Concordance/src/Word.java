import java.io.Serializable;
import java.util.ArrayList;

/**
 * Word Class
 * 
 * @author Matthew Yengle
 * 
 * Complete 3/4/16
 */

public class Word implements Serializable {
    
        /**
         * Fields
         * 
         * String string - holds the word for the Word Object
         * int occurrenceCount - Keeps track of the number of occurrences of the word
         * int rank - Keeps track of the rank of the word based on number of occurrences with respect to every word in concordance
         * ArrayList<Integer> listOfLines - Keeps track of each line number that word appears on
         * ArrayList<Integer> listWordNumber - Keeps track of each word number for the word
         */

        private String string;
        private int occurrenceCount = 0;   
        private int rank = 0;
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
         * Sets string field.
         * @param s string
         */
        public void setString(String s) {
            this.string = s;
        }

        /**
         * Gets string field.
         * @return string field
         */
        public String getString() {
            return this.string;
        }
        
        /**
         * Increases the occurrence of a word.
         */
        public void incOccurrence(){
            occurrenceCount++;
        }
        
        /**
         * Gets the number of occurrences of a word.
         * @return int of the number of occurrences
         */
        public Integer getOccurrence(){
            return occurrenceCount;
        }
        
        /**
         * Adds a new line.
         * @param newLine 
         */
        public void addNewLine(int newLine){
                listOfLines.add(newLine+1);        
        }
        
        /**
         * Returns the list of lines
         * @return 
         */
        public ArrayList<Integer> getListOfLines(){
            return listOfLines;
        }
        
        /**
         * Adds a word number
         * @param wordNumber 
         */
        public void addWordNumber(int wordNumber){
            listWordNumber.add(wordNumber);
        }
        
        /**
         * Returns the word number
         * @return 
         */
        public ArrayList<Integer> getWordNumber(){
            return listWordNumber;
        }      
        
        /**
         * Sets the rank
         * @param rank 
         */
        public void setRank(int rank){
            this.rank = rank;
        }
        
        /**
         * Returns a rank of a word
         * @return 
         */
        public int getRank(){
            return this.rank;
        }
    }
