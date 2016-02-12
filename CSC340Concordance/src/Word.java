
import java.io.Serializable;
import java.util.ArrayList;

public class Word implements Serializable {

        private String string;
        private int occurrenceCount = 0;   
        private ArrayList<Integer> listOfLines;
                
        public Word(String s) {
            this.string = s;
            listOfLines = new ArrayList<Integer>();
        }

        public void setString(String s) {
            this.string = s;
        }

        public String getString() {
            return this.string;
        }
        
        public void incOccurrence(){
            occurrenceCount++;
        }
        
        public Integer getOccurrence(){
            return occurrenceCount;
        }
        
        public void addNewLine(int newLine){
            listOfLines.add(newLine+1);
        }
        
        public ArrayList<Integer> getListOfLines(){
            return listOfLines;
        }
    }