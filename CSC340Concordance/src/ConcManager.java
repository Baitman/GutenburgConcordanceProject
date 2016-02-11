import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/*
*Concordance Manager 
*/
public class ConcManager{
    private HashMap<String, Object> HashTable;
    
    public ConcManager(HashMap<String, Object> HashTable){
        this.HashTable = HashTable;
    }
  
    
    /*
    *
    */
   public ArrayList<Integer> lineListQuery(String word){
       
        //need to get rid of duplicate lines
        return ((Word) HashTable.get(word.toLowerCase())).getListOfLines();
    }
    
    public Integer numLineListQuery(String word){
        //need to get rid of duplicate lines
        ArrayList <Integer> list = ((Word) HashTable.get(word.toLowerCase())).getListOfLines();
        list.trimToSize();
        return list.size();
    }
    
    public Integer appearQuery(String word){             
        //returns an integer value of the number of times the word appears
        return ((Word) HashTable.get(word.toLowerCase())).getOccurrence();
    }
    
    public Integer rankQuery(String word){
        
        //returns an integer value for the rank of the word
        return 0;
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
