import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Concordance class.
 *
 * @author Charles Mayse, Matthew Yengle
 */
public class Concordance implements Serializable {

    /**
     * Fields
     *
     * concordance - HashMap, holds the hashmap concordanceMap - Map, creates
     * the initial mapping of key-value pairs textArr - String[] holds the words
     * to turn into keys.
     */
    private HashMap concordance;
    private String[] textArr;

    /**
     * Constructor for Concordance class - creates HashMap after receiving a
     * string, the hash represents (String word, Object Word).
     *
     *
     * @param textFileString the string representation of a Gutenberg book
     */
    public Concordance(String textFileString) {
        /**
         * Each word will be placed into an element of the string array, textArr
         * Using the fact that each word is separated by a space, the space
         * character will be used as a delimiter.
         *
         */
        
        textArr = textFileString.split(" ");
        concordance = new HashMap<String, Word>();
        
        
        
        /**
         * For each item in the textArr, the duplicateWord array will be checked to
         * make sure that a Word object for that string has not been created yet.
         * If the duplicateWord array does not have an entry for the string then:
         * 1. an Object of the string will be made and the object will be added to the HashMap
         * 2. the string will be added to duplicateWord array to check for duplicates of the same string
         * Regardless:
         * For each validated string, the occurrence will be incremented, the line number, and word count number
         * will be updated within the Word object to keep track of that particular string         
         */
        
         ArrayList<String> duplicateWord = new ArrayList<String>(); 
         
        /**
         * Find the end of the preamble while keeping track of the line number
         */
        int preambleLine = 1;
        for(String preambleString : textArr){
            if(preambleString.equals("***")){
                preambleLine++;
                break;
            }
            else if(preambleString.equals("|")){
                preambleLine++;
            }
        }
        
        /**
         * The string '|' is being used as a line delimiter and
         * lineCount is being incremented with every occurrence of the delimiter
         * 
         * wordCount is incremented for each valid string
         * 
         * The for loop starts at the ending line for the preamble and
         * the loop breaks when it reaches the start of the ending preamble
         * which is denoted with the string "***"
         * 
         * String from the text is cleaned and stored in cleanString so 
         * the same word will consistently map to the same place in duplicateArray
         */
        
        int lineCount = 0;
        int wordCount = 0;
        String cleanString;
        for(String item : textArr){         
            
            if(item.equals("|")){
                lineCount++;                
            }            
            
            if(lineCount < preambleLine){
                continue;
            }
                        
            if(item.equals("***")){
                break;
            }
                        
            cleanString = item.replaceAll("[^a-zA-Z]", "").toLowerCase();   
            
            if(cleanString.equals("")){
                continue;
            }
            
            if(duplicateWord.contains(cleanString)){
                ((Word)concordance.get(cleanString)).incOccurrence();
                ((Word)concordance.get(cleanString)).addNewLine(lineCount);
                ((Word)concordance.get(cleanString)).addWordNumber(wordCount);
                wordCount++;
            }
            else if(cleanString == null){
                break;
            }
            else{  
                Word newWord = new Word(cleanString);
                concordance.put(cleanString, newWord);
                duplicateWord.add(cleanString);
                ((Word)concordance.get(cleanString)).incOccurrence();
                ((Word)concordance.get(cleanString)).addNewLine(lineCount);
                ((Word)concordance.get(cleanString)).addWordNumber(wordCount);
                wordCount++;
            }
        }       
    }

    /**
     * Returns the concordance hashmap
     *
     * @return the hashmap (word,objectWord).
     */
    public HashMap getConcordance() {
        return this.concordance;
    }
}
  
