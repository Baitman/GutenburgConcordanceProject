
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Title {

    public static void main(String[] args) {
        TitleElement head = new TitleElement();
        TitleElement tail = new TitleElement();
        TitleElement curr = new TitleElement();

    }

    public void addTitle(TitleElement curr, String title) throws FileNotFoundException {
        curr.setNext(curr);
        curr = curr.getNext();
        curr.setTitle(title);
        curr.setText();//pass param to setter
        curr.createConcordance();

    }
}

class TitleElement {

    private TitleElement next = new TitleElement();
    private String text;
    private String title;
    private ConcordanceElement[] concordance = new ConcordanceElement[100000];

    public void setNext(TitleElement nxt) {
        this.next = nxt;
    }

    public TitleElement getNext() {
        return this.next;
    }

    public void setTitle(String textTitle) {
        this.title = textTitle;
    }

    public String getTitle() {
        return this.title;
    }

    public void setText() {
        //text reader
    }

    public void storeWordS(String nextWord, String word, int line) {
        concordance[word.hashCode()].occupy();
        concordance[word.hashCode()].incOccurances();
        concordance[word.hashCode()].incLineOccurances(line);
        concordance[word.hashCode()].setNext(line, nextWord);
        concordance[word.hashCode()].sOL(line);
    }

    public void storeWord(String nextWord, String word, String prevWord, int line) {
        concordance[word.hashCode()].occupy();
        concordance[word.hashCode()].incOccurances();
        concordance[word.hashCode()].incLineOccurances(line);
        concordance[word.hashCode()].setNext(line, nextWord);
        concordance[word.hashCode()].setPrev(line, prevWord);
    }

    public void storeWordE(String word, String prevWord, int line) {
        concordance[word.hashCode()].occupy();
        concordance[word.hashCode()].incOccurances();
        concordance[word.hashCode()].incLineOccurances(line);
        concordance[word.hashCode()].setPrev(line, prevWord);
        concordance[word.hashCode()].eOL(line);
    }

    public void readLine(String line, int lineNum) {
        String nextWord = "";
        String word = "";
        String prevWord = "";
        boolean firstSpace = true;
        boolean firstWordStore = false;
        boolean endOfLine = false;
        int i = 0;
        while (i < line.length()) {
            nextWord += line.charAt(i);
            i++;
            if (i == (line.length() - 1)) {
               storeWordE(nextWord, word, lineNum);
               i++;
               endOfLine = true;
            }
            
            if (line.charAt(i) == ' ' && firstWordStore == false && firstSpace == false && endOfLine == false) {
                storeWord(nextWord, word, prevWord, lineNum);
                prevWord = word;
                word = nextWord;
                nextWord = "";
                i++;
            }
            
            if (line.charAt(i) == ' ' && firstWordStore == true && endOfLine == false && firstSpace == false) {
                storeWordS(nextWord, word, lineNum);
                prevWord = word;
                word = nextWord;
                nextWord = "";
                i++;
            }
            
            if (line.charAt(i) == ' ' && firstSpace == true && endOfLine == false) {
                word = nextWord;
                nextWord = "";
                i++;
                firstSpace = false;
            }

            
        }
    }

    public void createConcordance() throws FileNotFoundException {
        boolean startConc = false;
        boolean endConc = false;
        int lineNum = 0;
        Scanner file = new Scanner(new File(this.title));
        while (file.hasNext() && startConc == false) {
            if (file.nextLine().substring(0, 16).equals("*** START OF THIS")) {
                startConc = true;
            }
            lineNum++;
        }
        while (file.hasNext() && endConc == false) {
            readLine(file.nextLine(), lineNum);
            lineNum++;
        }

    }
}

class ConcordanceElement {

    private int occurances = 0;
    private boolean occupied = false;
    private LineOccurElement[] arr = new LineOccurElement[100000];

    public void incOccurances() {
        this.occurances++;
    }

    public void incLineOccurances(int line) {
        this.arr[line].incOccurances();
    }

    public int getOccurances() {
        return this.occurances;
    }

    public void occupy() {
        this.occupied = true;
    }

    public boolean getOccupied() {
        return this.occupied;
    }

    public void setPrev(int i, String prev) {
        arr[i].setPrev(prev);
    }

    public String getPrev(int i) {
        return arr[i].getPrev();
    }

    public void setNext(int i, String next) {
        arr[i].setNext(next);
    }

    public String getNext(int i) {
        return arr[i].getNext();
    }

    public void sOL(int i) {
        arr[i].sOL();
    }

    public void eOL(int i) {
        arr[i].eOL();
    }

    public int sOE(int i) {
        return arr[i].startOrEnd();
    }

}

class LineOccurElement {

    private String prev = "";
    private String next = "";
    private int occurances = 0;
    private boolean startOfLine = false;
    private boolean endOfLine = false;

    public void incOccurances() {
        this.occurances++;
    }

    public int getOccurances() {
        return this.occurances;
    }

    public void setPrev(String last) {
        this.prev = last;
    }

    public String getPrev() {
        return this.prev;
    }

    public void setNext(String following) {
        this.next = following;
    }

    public String getNext() {
        return this.next;
    }

    public void sOL() {
        this.startOfLine = true;
    }

    public void eOL() {
        this.endOfLine = true;
    }

    public int startOrEnd() {
        if (this.startOfLine == true) {
            return 1;
        }
        if (this.endOfLine == true) {
            return 2;
        } else {
            return 0;
        }
    }
}
