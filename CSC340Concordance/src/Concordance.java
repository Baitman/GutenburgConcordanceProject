import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Concordance class.
 *
 * @author Charles Mayse, Matthew Yengle 
 * March 03, 2016
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
       
        
        //Sorts the Word objects based on their number of occurrences from high to low 
         ArrayList<Word> rankSystemArray = new ArrayList<Word>();
        
        
        /**
         * For each item in the textArr, the concordance will be checked to
         * make sure that a Word object for that string has not been created yet.
         * If the concordance does not have an entry for the string then:
         * an Object of the string will be made and the object will be added to the HashMap
         * Regardless:
         * For each validated string, the occurrence will be incremented, the line number, and word count number
         * will be updated within the Word object to keep track of that particular string         
         */
        
         
         
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
                        
            cleanString = item.replaceAll("[^a-zA-Z]", "");
            cleanString = cleanString.toLowerCase();
            
            if(cleanString.equals("")){
                continue;
            }
            
            if(concordance.containsKey(cleanString)){
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
                //Exclude stop words from the ranking system
                if(!isStopWord(cleanString){
                    rankSystemArray.add(newWord); 
                }
                ((Word)concordance.get(cleanString)).incOccurrence();
                ((Word)concordance.get(cleanString)).addNewLine(lineCount);
                ((Word)concordance.get(cleanString)).addWordNumber(wordCount);
                wordCount++;
            }
        }
        /**
         * Set the rank of a Word based on its index in the rankSystemArray
         */
        mergeSort(rankSystemArray);
        for(int i =0; i < rankSystemArray.size(); i ++){
            rankSystemArray.get(i).setRank(i+1);
        }
    }
    
    /**
     * Merge Sort for to order words based on their number of occurrences (high to low)
     * @param list
     * @return 
     */
    
    private  ArrayList<Word> mergeSort(ArrayList<Word> list){
        ArrayList<Word> left = new ArrayList<Word>();
        ArrayList<Word> right = new ArrayList<Word>();
        int center;
        
        if(list.size() <= 1)
            return list;
        else{
            center = list.size()/2;
            for(int i =0; i < center; i ++){
                left.add(list.get(i));
            }
            for(int i=center; i <list.size(); i++){
                right.add(list.get(i));
            }
            left = mergeSort(left);
            right = mergeSort(right);
            
            merge(left, right, list);        
        }
        return list;        
    }
    
    private void merge(ArrayList<Word> left, ArrayList<Word> right, ArrayList<Word> list){
        int leftIndex = 0;
        int rightIndex = 0;
        int listIndex = 0;
        
        while(leftIndex < left.size() && rightIndex < right.size()){
            if(left.get(leftIndex).getOccurrence() > right.get(rightIndex).getOccurrence()){
                list.set(listIndex, left.get(leftIndex));
                leftIndex++;
            }
            else{
                list.set(listIndex, right.get(rightIndex));
                rightIndex++;
            }
            listIndex++;
        }
        if(leftIndex >= left.size()){
            for(int i = rightIndex; i < right.size(); i++){
                list.set(listIndex, right.get(i));
                listIndex++;
            }
        }
        else{
            for(int i = leftIndex; i < left.size(); i++){
                list.set(listIndex, left.get(i));
                listIndex++;
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
    
    /**
     * Method to check if a word is a stop word
     * Stop words are common words to be excluded from ranking system
     * 
     * @return true if the parameter is a stop word, false otherwise
     */
     
    private boolean isStopWord(String word){
         /**
         * An array of stop words to exclude from rank query
         * These words will have a rank of 0
         */
        String[] stopWords = {"a",  "also", "am", "an", "and",  "are",  "as", "be", "been",  "being","but", "by", "can", "cannot", "cant", "do",             
        "for", "get", "give", "had", "has", "hasnt", "have", "he", "her", "here", "hers", "herself","him", "himself", "his", "in", "into", "is", "it", "its", "itself", 
         "ltd","may", "me","my", "myself", "no",  "nor", "not", "now",  "of", "or", "other",  "our", "ours", "she","so",  "that", "the", "their", "them",  "there", 
         "these", "they", "this",  "to",   "us",  "was", "we",  "you"};
        
        for(int i =0; i < stopWords.length; i++){
            if(stopWords[i].equals(word)){
                System.out.println("Stop Word: " + stopWords[i] + "\tWord: " + word);
                return true;
            }
        }
        return false;        
    }
}
  
