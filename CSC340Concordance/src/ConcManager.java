import java.util.ArrayList;
import static java.util.Collections.binarySearch;
import java.util.HashMap;
import java.util.Set;

/**
 * Concordance Manager class
 * 
 * @author Matt
 */
public class ConcManager{
    /**
     * Fields
     * 
     * HashMap<String, Object> HashTable - Holds the mappings for the current concordance
     */
    private HashMap<String, Object> HashTable;
    
    /**
     * Constructor for ConcManager
     * 
     * Sets HashTable equal to the parameter
     * @param HashTable 
     */    
    public ConcManager(HashMap<String, Object> HashTable){        
        this.HashTable = HashTable;
    }
      
    /**
     * Method to return a list of line numbers on which the given word appears on
     * If the given word appears on the same line twice, then only one occurrence appears
     * in the list
     * 
     * @param word The word being queried
     * @return  an ArrayList<Integer> of exact size with line numbers appearing in ascending order
     */
   public ArrayList<Integer> lineListQuery(String word){      
      if(isNotValid(word))
          return null;
      
       ArrayList<Integer> oldLineList = ((Word) HashTable.get(word.toLowerCase())).getListOfLines();
       ArrayList<Integer> lineList = new ArrayList<Integer>();
       
       oldLineList.trimToSize();
       
       for (int i =0; i < oldLineList.size(); i ++){
           if(!lineList.contains(oldLineList.get(i))){
               lineList.add(oldLineList.get(i));
           }
       }
        return lineList;
    }
    
    /**
     * Method to return the number of lines on which the given word appears on
     * If the given word appears on the same line twice, then the line is only counted a single time
     * 
     * @param word The word being queried
     * @return an integer value for the number of lines
     */
    public Integer numLineListQuery(String word){
       if(isNotValid(word))
           return 0;
        return lineListQuery(word).size();
    }
    
    /**
     * Method to return the number of occurrences of the given word
     * 
     * @param word The word being queried 
     * @return an integer value for the number of occurrences of the word
     */
    public Integer appearQuery(String word){     
        if (isNotValid(word)){
            return 0;
        }
        return ((Word) HashTable.get(word.toLowerCase())).getOccurrence();
    }
    
    /**
     * Method to return the rank (based on occurrences) of the given word with respect
     * other word in the concordance
     * Uses getRank() from Word Object
     * 
     * Ranking starts at 1, with the word that has the highest number of occurrences
     * 
     * @param word The word being queried
     * @return 
     */
    public Integer rankQuery(String word){     
        if(isNotValid(word))
            return -1;              
        return ((Word)HashTable.get(word)).getRank();
    }
    
    /**
     * Method to return a list of words that are within a given distance of the target word
     * The target word is specified by a string value and a specific line number that the word appears on
     * 
     * 
     * @param word The word being queried
     * @param distance The distance from the target word (exclusive) in both directions
     * @param lineNumber The line number that the target word appears on, if the target word appears
     *                    multiple times on the line, then only the first occurrence is used as the target word
     * 
     * @return Returns a String[] of the words found within the distance of the target word
     *          The words appears as they would in the text, with the target word always in the middle of the array
     */
    public String[] distanceQuery(String word, Integer distance, Integer lineNumber){
        if(isNotValid(word))
            return null;
        /**
         * Find the word's word number based on the given line number
         * Because of how they are stored in the word Object, the indexes of the list of lines 
         * correspond directly to the index of that word's word number
         * 
         * idexOfTargetWordLineNumber finds the target line for the target word
         * targetWordNumber finds the corresponding word number for that target line
         * 
         * String[] wordArray is created to store the words found, it will always be the size of (2*distance)+1
         * 
         * A keySet of the HashTable is created to find all of the string values in the concordance
         */               
        int indexOfTargetWordLineNumber =   (((Word)HashTable.get(word.toLowerCase())).getListOfLines()).indexOf(lineNumber);
        int targetWordNumber = (((Word)HashTable.get(word.toLowerCase())).getWordNumber()).get(indexOfTargetWordLineNumber);        
        
        String[] wordArray = new String[((2*distance)+1)];        
        
        Set keySet = HashTable.keySet();    
        
        /**
         * The outer for loop iterates over the keySet (over the string values in the concordance)
         * 
         * For each string value in the keySet, the inner for loop checks each of the word's word numbers
         * They are added to the wordArray if they are within the given distance from the target word
         */
        
        for(int i = 0; i < keySet.size(); i++){
            for(int j=0; j < (((Word)HashTable.get(keySet.toArray()[i].toString())).getWordNumber().size()); j++){                
                int newWordPos = (((Integer)((Word)HashTable.get(keySet.toArray()[i].toString())).getWordNumber().toArray()[j]) - (targetWordNumber-distance));                
                if(newWordPos >= 0 && newWordPos < wordArray.length){
                        wordArray[newWordPos] = keySet.toArray()[i].toString();        
                }
            }            
        }        
        return wordArray;
    }
    
    /**
     * Overloaded method for two word adjacency query
     * Method to return a list of line numbers on which the two words are adjacent on
     * The words can appear in any order as long as they are adjacent
     * 
     * @param firstWord 
     * @param secondWord
     * @return ArrayList<Integer> an array of line numbers
     */    
    public ArrayList<Integer> adjacentQuery(String firstWord, String secondWord){              
        /**
         * Check if words appear in concordance
         */
        if(isNotValid(firstWord) || isNotValid(secondWord)){
            return null;
        }    
        
        /** 
         * Get array of word counts for first word and second word
         */
        ArrayList<Integer> firstArray = ((Word)HashTable.get(firstWord.toLowerCase())).getWordNumber();
        ArrayList<Integer> secondArray = ((Word)HashTable.get(secondWord.toLowerCase())).getWordNumber();
        ArrayList<Integer> lineCount = new ArrayList<Integer>();
                      
        /**
         * The word with more occurrences needs to used in the loop 
         * Decision statement to find the word with more occurrences
         */
        if(firstArray.size() > secondArray.size()){        
            /**
             * Search the first array's values and compare to the second array
             * check if any value in the first array is within 1 of any value in the second array
             * Store the line numbers that this occurs on
             */
            ArrayList<Integer> firstLineArray = ((Word)HashTable.get(firstWord.toLowerCase())).getListOfLines();
            for(int i =0; i < firstArray.size(); i++){
                if(secondArray.contains(firstArray.get(i)-1)){
                    lineCount.add(firstLineArray.get(i));
                }
                else if (secondArray.contains(firstArray.get(i)+1)){
                    lineCount.add(firstLineArray.get(i));
                }
            }        
        }
        else{
            /**
             * Search the second array's values and compare to the first array
             * check if any value in the second array is within 1 of any value in the first array
             * Store the line numbers that this occurs on
             */
            ArrayList<Integer> secondLineArray = ((Word)HashTable.get(secondWord.toLowerCase())).getListOfLines();
             for(int i =0; i < secondArray.size(); i++){
                if(firstArray.contains(secondArray.get(i)-1)){
                    lineCount.add(secondLineArray.get(i));

                }
                else if (firstArray.contains(secondArray.get(i)+1)){
                    lineCount.add(secondLineArray.get(i));
                }
            }      
        }
        return lineCount;    
    }
    
    /**
     * Overloaded method for three word adjacency query
     * Method to return a list of line numbers on which the three words are adjacent on
     * The three words can appear in any order as long as they are adjacent to one another
     * 
     * @param firstWord 
     * @param secondWord
     * @return ArrayList<Integer> an array of line numbers
     */  
    public ArrayList<Integer> adjacentQuery(String firstWord, String secondWord, String thirdWord){              
        /**
         * Check if words appear in concordance
         */
        if(isNotValid(firstWord) || isNotValid(secondWord) || isNotValid(thirdWord)){
            return null;
        }    
        
        /** 
         * Get array of word counts for first word and second word and third word
         */
        ArrayList<Integer> firstArray = ((Word)HashTable.get(firstWord.toLowerCase())).getWordNumber();
        ArrayList<Integer> secondArray = ((Word)HashTable.get(secondWord.toLowerCase())).getWordNumber();
        ArrayList<Integer> thirdArray = ((Word)HashTable.get(thirdWord.toLowerCase())).getWordNumber();
        ArrayList<Integer> lineCount = new ArrayList<Integer>();
                      
        /**
         * The word with more occurrences needs to used in the loop 
         * Outer if... else if ... else if statement is used to find the word with more occurrences
         */
        if(firstArray.size() > secondArray.size() && firstArray.size() > thirdArray.size()){        
            /**
             * Search the first array's values and compare to the second array and third array
             * check if any value in the first array is within distance of words in the second or third array
             * Store the line numbers that this occurs on
             */
            ArrayList<Integer> firstLineArray = ((Word)HashTable.get(firstWord.toLowerCase())).getListOfLines();
            for(int i =0; i < firstArray.size(); i++){
                if(secondArray.contains(firstArray.get(i) -1)){
                  if(thirdArray.contains(firstArray.get(i)+1) || thirdArray.contains(firstArray.get(i)-2)){
                      lineCount.add(firstLineArray.get(i));
                  }      
                }
                else if (secondArray.contains(firstArray.get(i)+1)){
                    if(thirdArray.contains(firstArray.get(i)-1) || thirdArray.contains(firstArray.get(i)+2)){
                        lineCount.add(firstLineArray.get(i));
                    }
                }    
                else if (secondArray.contains(firstArray.get(i)-2) && thirdArray.contains(firstArray.get(i)-1)){
                    lineCount.add(firstLineArray.get(i));                   
                }
                 else if (secondArray.contains(firstArray.get(i)+2) && thirdArray.contains(firstArray.get(i)+1)){
                    lineCount.add(firstLineArray.get(i));                   
                }
            }        
        }
        else if (secondArray.size() > thirdArray.size()) {
            /**
             * Search the second array's values and compare to the first array and third array
             * check if any value in the second array is within distance of words in the first or third array
             * Store the line numbers that this occurs on
             */
            ArrayList<Integer> secondLineArray = ((Word)HashTable.get(secondWord.toLowerCase())).getListOfLines();
             for(int i =0; i < secondArray.size(); i++){
                if(firstArray.contains(secondArray.get(i) -1)){
                  if(thirdArray.contains(secondArray.get(i)+1) || thirdArray.contains(secondArray.get(i)-2)){
                      lineCount.add(secondLineArray.get(i));
                  }      
                }
                else if (firstArray.contains(secondArray.get(i)+1)){
                    if(thirdArray.contains(secondArray.get(i)-1) || thirdArray.contains(secondArray.get(i)+2)){
                        lineCount.add(secondLineArray.get(i));
                    }
                }    
                else if (firstArray.contains(secondArray.get(i)-2) && thirdArray.contains(secondArray.get(i)-1)){
                    lineCount.add(secondLineArray.get(i));                   
                }
                else if (firstArray.contains(secondArray.get(i)+2) && thirdArray.contains(secondArray.get(i)+1)){
                    lineCount.add(secondLineArray.get(i));                   
                }
             }
        }
        else if (thirdArray.size() > secondArray.size()){  
            /**
             * Search the third array's values and compare to the first array and second array
             * check if any value in the third array is within distance of words in the first or second array
             * Store the line numbers that this occurs on
             */
            ArrayList<Integer> thirdLineArray = ((Word)HashTable.get(thirdWord.toLowerCase())).getListOfLines();
             for(int i =0; i < thirdArray.size(); i++){
                if(secondArray.contains(thirdArray.get(i) -1)){
                  if(firstArray.contains(thirdArray.get(i)+1) || firstArray.contains(thirdArray.get(i)-2)){
                      lineCount.add(thirdLineArray.get(i));
                  }      
                }
                else if (secondArray.contains(thirdArray.get(i)+1)){
                    if(firstArray.contains(thirdArray.get(i)-1) || firstArray.contains(thirdArray.get(i)+2)){
                        lineCount.add(thirdLineArray.get(i));
                    }
                }    
                else if (secondArray.contains(thirdArray.get(i)-2) && firstArray.contains(thirdArray.get(i)-1)){
                    lineCount.add(thirdLineArray.get(i));                   
                }
                else if (secondArray.contains(thirdArray.get(i)+2) && firstArray.contains(thirdArray.get(i)+1)){
                    lineCount.add(thirdLineArray.get(i));                   
                }
            }  
        }        
        return lineCount;    
    }
    
    /**
     * Method to check if word is not in the concordance
     * @param word
      * @return 
     */
    private Boolean isNotValid(String word){
        Set keySet = HashTable.keySet();
        return !keySet.contains(word);
    }
}
