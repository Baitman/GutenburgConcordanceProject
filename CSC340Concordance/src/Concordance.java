

/**
 * "Title" was changed to "Concordance"
 * 
 */
public class Concordance{
    
    /**
     * This main needs to be deleted
     * @param args 
     */
    public static void main(String[] args){
        TitleElement head = new TitleElement();
        TitleElement tail = new TitleElement();
        TitleElement curr = new TitleElement();
        
        
    }
    
    public void addTitle(TitleElement curr, String title){
        curr.setNext(curr);
        curr = curr.getNext();
        curr.setTitle(title);
        curr.setText();//pass param to setter
        curr.createConcordance();
        
    }
}

class TitleElement{
    private TitleElement next = new TitleElement();
    private String text;
    private String title;
    private ConcordanceElement[] concordance = new ConcordanceElement[100000];
    
    public void setNext(TitleElement nxt){
        this.next = nxt;
    }
    
    public TitleElement getNext(){
        return this.next;
    }
    
    public void setTitle(String textTitle){
        this.title = textTitle;
    }
    
    public String getTitle(){
        return this.title;
    }
    
    public void setText(){
        //text reader
    }
    
    public void createConcordance(){
        //Concordance creator based off text, creates ConcordanceElements for words
    }
}

class ConcordanceElement{
    private int occurances = 0;
    private LineOccurElement[] arr = new LineOccurElement[100000];
    
    public void intOccurances(){
        this.occurances++;
    }
    
    public int getOccurances(){
        return this.occurances;
    }
    public void setPrev(int i, String prev){
        arr[i].setPrev(prev);
    }
    
    public String getPrev(int i){
        return arr[i].getPrev();
    }
    
    public void setNext(int i, String next){
        arr[i].setNext(next);
    }
    
    public String getNext(int i){
        return arr[i].getNext();
    }
    
    public void sOL(int i){
        arr[i].sOL();
    }
    
    public void eOL(int i){
        arr[i].eOL();
    }
    
    public int sOE(int i){
        return arr[i].startOrEnd();
    }
    
    
    
}

class LineOccurElement{
    private String prev = "";
    private String next = "";
    private int occurances = 0;
    private boolean startOfLine = false;
    private boolean endOfLine = false;
    
    public void incOccurances(){
        this.occurances++;
    }
    public int getOccurances(){
        return this.occurances;
    }
    
    public void setPrev(String last){
        this.prev = last;
    }
    
    public String getPrev(){
        return this.prev;
    }
    
    public void setNext(String following){
        this.next = following;
    }
    
    public String getNext(){
        return this.next;
    }
    
    public void sOL(){
        this.startOfLine = true;
    }
    
    public void eOL(){
        this.endOfLine = true;
    }
    
    public int startOrEnd(){
        if(this.startOfLine == true) return 1;
        if(this.endOfLine == true) return 2;
        else return 0;
    }
}
    
