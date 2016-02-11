/**
 *  Accomplishes 5 tasks
 * 1. Load a book into memory 
 * 2. Save a book into a specified directory
 * 3. Load a concordanence locally to memory 
 * 4. Save a conccordanence into a specified directory
 * 5. View saved books and concordance
 */
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    //String contitle = ;
    
    FileOutputStream fos;
    ObjectOutputStream oss = null;
    
    
    try{
        fos = new FileOutputStream(new File("con.ser"));
        oss = new ObjectOutputStream(fos);
    }catch(IOException oops){
        oops.printStackTrace();
    }
    
}
public Concordance loadConc(String condir){
    
    try {
        FileInputStream fis = new FileInputStream(condir);
        ObjectInputStream ois = new ObjectInputStream(fis);
        
       Concordance c = (Concordance)ois.readObject();
       return c;
       
    }catch(IOException crap) {
        System.out.println("Class not found");
        crap.printStackTrace();
    } catch (ClassNotFoundException ex) {
        Logger.getLogger(FileIOManager.class.getName()).log(Level.SEVERE, null, ex);
    }
    return null;
}

public File loadBook(String bookloc){
    String textcontent = "";
    String line = null;
   try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = 
                new FileReader(bookloc);

            BufferedReader buffReader = 
                new BufferedReader(fileReader);

            while((line = buffReader.readLine()) != null) { 
                textcontent += line;
            }   

            buffReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println( "Unable to open file '" +  bookloc + "'");                
        }
        catch(IOException ex) {
            System.out.println( "Error reading file '" + bookloc + "'");                  
        }
        // write text content to a txt file
        try {
			File file = new File("test2.txt");
			FileWriter fileWriter = new FileWriter(file);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.print(textcontent);
			fileWriter.flush();
			fileWriter.close();
                        return file;
		} catch (IOException e) {
			e.printStackTrace();
		}
   
        // return file not string 
    return null;
}
public String viewBooks(){
    String dirString = "";
 File[] dirlist = currentDirectory.listFiles();
    for (int i = 0; i < dirlist.length; i++) {
        if(dirlist[i].isFile()){
            dirString += dirlist[i].getName();
        }
    }
return dirString;
   
   
}
public String viewBooks(String bookDir){
String dirString = "";
File dir = new File(bookDir);
File[] dirlist = dir.listFiles();
    for (int i = 0; i < dirlist.length; i++) {
        if(dirlist[i].isFile()){
            dirString += dirlist[i].getName();
        }
    }

return dirString;
}

public String viewSavedConc(){
    String dirString = "";
 File[] dirlist = currentDirectory.listFiles();
    for (int i = 0; i < dirlist.length; i++) {
        if(dirlist[i].isFile()){
            dirString += dirlist[i].getName();
        }
    }
return dirString;
}

public String viewSavedConc(String conDir){
 String dirString = "";
File dir = new File(conDir);
File[] dirlist = dir.listFiles();
    for (int i = 0; i < dirlist.length; i++) {
        if(dirlist[i].isFile()){
            dirString += dirlist[i].getName();
        }
    } 
 
 return conDir;
}



}