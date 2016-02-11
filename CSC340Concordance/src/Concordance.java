
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;



/**
 * "Title" was changed to "Concordance"
 * 
 */
public class Concordance implements Serializable{
    private HashMap concordance;
    private Map concordanceMap;
    private String[] textArr;
    
   public Concordance(String textFileString){
       textFileString = removePreamble(textFileString);
       textArr = textFileString.split(" ");
       
       for(String item : textArr){
           if(!concordanceMap.containsKey(item)){
               concordanceMap.put(item, 0);
           }
           else{
               int i = (int)concordanceMap.get(item);
               concordanceMap.replace(item, ++i);
           }
       }
       
       concordance = new HashMap(concordanceMap);
   }
    
   /**
    * This method needs to be implemented, simply returns the string passed
    * to it.
    * TODO Implement method.
    * @param s Text to remove preamble.
    * @return 
    */
    private String removePreamble(String s){
        return s;
    }
    

    
    
    private class Word implements Serializable{
        private String string;
        
        public Word(String s){
            this.string = s;
        }
        
        public void setString(String s){
            this.string = s;
        }
        
        public String getString(){
            return this.string;
        }
    }
    
    private class Line implements Serializable{
        private int location;
        
        public void setLine(int i){
            this.location = i;
        }
        
        public int getLine(){
            return this.location;
        }
    }
}
    
//    /**
//     * This main needs to be deleted
//     * @param args 
//     */
//    public static void main(String[] args){
//        TitleElement head = new TitleElement();
//        TitleElement tail = new TitleElement();
//        TitleElement curr = new TitleElement();
//        
//        
//    }
//    
//    public void addTitle(TitleElement curr, String title){
//        curr.setNext(curr);
//        curr = curr.getNext();
//        curr.setTitle(title);
//        curr.setText();//pass param to setter
//        curr.createConcordance();
//        
//    }
//}
//
//class TitleElement{
//    private TitleElement next = new TitleElement();
//    private String text;
//    private String title;
//    private ConcordanceElement[] concordance = new ConcordanceElement[100000];
//    
//    public void setNext(TitleElement nxt){
//        this.next = nxt;
//    }
//    
//    public TitleElement getNext(){
//        return this.next;
//    }
//    
//    public void setTitle(String textTitle){
//        this.title = textTitle;
//    }
//    
//    public String getTitle(){
//        return this.title;
//    }
//    
//    public void setText(){
//        //text reader
//    }
//    
//    public void createConcordance(){
//        //Concordance creator based off text, creates ConcordanceElements for words
//    }
//}
//
//class ConcordanceElement{
//    private int occurances = 0;
//    private LineOccurElement[] arr = new LineOccurElement[100000];
//    
//    public void intOccurances(){
//        this.occurances++;
//    }
//    
//    public int getOccurances(){
//        return this.occurances;
//    }
//    public void setPrev(int i, String prev){
//        arr[i].setPrev(prev);
//    }
//    
//    public String getPrev(int i){
//        return arr[i].getPrev();
//    }
//    
//    public void setNext(int i, String next){
//        arr[i].setNext(next);
//    }
//    
//    public String getNext(int i){
//        return arr[i].getNext();
//    }
//    
//    public void sOL(int i){
//        arr[i].sOL();
//    }
//    
//    public void eOL(int i){
//        arr[i].eOL();
//    }
//    
//    public int sOE(int i){
//        return arr[i].startOrEnd();
//    }
//    
//    
//    
//}
//
//class LineOccurElement{
//    private String prev = "";
//    private String next = "";
//    private int occurances = 0;
//    private boolean startOfLine = false;
//    private boolean endOfLine = false;
//    
//    public void incOccurances(){
//        this.occurances++;
//    }
//    public int getOccurances(){
//        return this.occurances;
//    }
//    
//    public void setPrev(String last){
//        this.prev = last;
//    }
//    
//    public String getPrev(){
//        return this.prev;
//    }
//    
//    public void setNext(String following){
//        this.next = following;
//    }
//    
//    public String getNext(){
//        return this.next;
//    }
//    
//    public void sOL(){
//        this.startOfLine = true;
//    }
//    
//    public void eOL(){
//        this.endOfLine = true;
//    }
//    
//    public int startOrEnd(){
//        if(this.startOfLine == true) return 1;
//        if(this.endOfLine == true) return 2;
//        else return 0;
//    }
//}
//    
