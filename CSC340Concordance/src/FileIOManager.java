/**
 * 
 */
import java.io.*;
import java.util.*;

public class FileIOManager{
private File currentDirectory;
private FileOutputStream outputStream;



public FileIOManager(String s){
     currentDirectory = new File(s);
     
}

public FileIOManager(){
    currentDirectory = new File(System.getProperty("user.dir"));
}

public void saveConc(Concordance con){
    Concordance inputcon = con;
    FileOutputStream fos;
    ObjectOutputStream oss = null;
    
    try{
        fos = new FileOutputStream(new File("con.obj"));
        oss = new ObjectOutputStream(fos);
    }catch(IOException oops){
        oops.printStackTrace();
    }
    
}
public Concordance loadConc(String con){
    throw new UnsupportedOperationException();
}
public String loadBook(String book){
    String bookloc, booktit;
    Scanner kb = new Scanner(System.in);
    System.out.println("Please enter the name & location of your book:");
    bookloc = kb.nextLine();
    kb.nextLine();
    String line = "\n";
   // System.out.println("Please enter the title of your book");
   // booktit = kb.nextLine();
   try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(bookloc);

            BufferedReader buffReader = 
                new BufferedReader(fileReader);

            while((line = buffReader.readLine()) != null) { 
                System.out.println(line);
            }   

            buffReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println( "Unable to open file '" +  bookloc + "'");                
        }
        catch(IOException ex) {
            System.out.println( "Error reading file '" + bookloc + "'");                  
        }
    
    return bookloc;
}
public String viewBooks(){
   throw new UnsupportedOperationException();
}
public String viewBooks(String booklist){
throw new UnsupportedOperationException();
}

public String viewSavedConc(){
throw new UnsupportedOperationException();     
}

public String viewSavedConc(String con){
 throw new UnsupportedOperationException();   
}



}