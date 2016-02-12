import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/*
*Concordance Manager 
*/
public class ConcManager{
    private HashMap<String, Object> HashTable;
    
    public ConcManager(HashMap<String, Object> HashTable){
        this.HashTable = HashTable;
    }
  
    
    
    //returns an array of lines where the word appears
   public ArrayList<Integer> lineListQuery(String word){
       
       
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
    
    //returns the number of lines that the word appears on
    public Integer numLineListQuery(String word){
        return lineListQuery(word).size();
    }
    
    //returns an integer value of the number of times the word appears
    public Integer appearQuery(String word){             
        return ((Word) HashTable.get(word.toLowerCase())).getOccurrence();
    }
    
    public Integer rankQuery(String word){     
        Set keySet = HashTable.keySet();               
        int wordRank = 1;        
        for(int i =0; i < keySet.size(); i++){
            if( appearQuery(keySet.toArray()[i].toString()) > appearQuery(word)){   
                if(keySet.toArray()[i].toString() == word){
                    continue;
                }
                wordRank++;
            }               
        }
        
        //returns an integer value for the rank of the word
        return wordRank;
    }
    
    public String[][] distanceQuery(String word, Integer distance){
        
        
        //returns a 2D array, where each occurence of the word is a separate row
        //the first colomn in each row is the line number where the word appears
        //words appear in order as they would in the text
        /*example
        line#, word-3, word-2, word-1, word, word+1,word+2,word+3
        line#, word-3, word-2, word-1, word, word+1, word+2, word+3*/
        return null;
    }
    
    public String[] adjacentQuery(String word, Boolean bool){
        
        //not sure how this is going to work yet
        return null;    
    }
}
