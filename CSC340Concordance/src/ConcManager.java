public class ConcManager<T>{
    private T HashTable;
    private String keyword;
    
    public ConcManager(T HashTable){
        this.HashTable = HashTable;
    }
    
    public void setKeyword(String word){
        keyword = word;
    }
    
    public String[] lineListQuery(String word){
        setKeyword(word);
                
        return null;
    }
    
    public Integer numLineListQuery(String word){
        setKeyword(word);
        
        return 0;
    }
    
    public Integer appearQuery(String word){
        setKeyword(word);
        
        return 0;
    }
    
    public Integer rankQuery(String word){
        setKeyword(word);
        
        return 0;
    }
    
    public String[] distanceQuery(String word, Integer distance){
        setKeyword(word);
        
        return null;
    }
    
    public String[] adjacentQuery(String word, Boolean bool){
        setKeyword(word);
        
        return null;
    }
}
