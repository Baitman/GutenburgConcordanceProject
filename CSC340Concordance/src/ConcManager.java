import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/*
*Concordance Manager 
*/
public class ConcManager{
    private HashMap<String, Object> HashTable;
    private Object word;
    
    public ConcManager(HashMap<String, Object> HashTable){
        this.HashTable = HashTable;
    }
        
    private void findWordObject(String word){          
        this.word = HashTable.get(word);
    }
    
    /*
    *
    */
    public Integer[] lineListQuery(String word){
        
        findWordObject(word);
        //returns an array of lines where the word appears
        //ex. [1, 5, 10, 55, 105]
        return word.getLineList();
    }
    
    public Integer numLineListQuery(String word){
        findWordObject(word);
        
        //returns an integer value for number of lines the word appears
        return word.getNumLineList();
    }
    
    public Integer appearQuery(String word){
        
        //returns an integer value of the number of times the word appears
        return 0;
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
