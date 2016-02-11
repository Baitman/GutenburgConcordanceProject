
/**
 * Accomplishes 5 tasks 1. Load a book into memory 2. Save a book into a
 * specified directory 3. Load a concordance locally to memory 4. Save a
 * concordance into a specified directory 5. View saved books and concordance
 */
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileIOManager {

    private File currentDirectory;
    private FileOutputStream outputStream;
    private String text;

    public FileIOManager(String s) {
        currentDirectory = new File(s);
    }

    public FileIOManager() {
        currentDirectory = new File(System.getProperty("user.dir"));
    }
    
    public boolean verify(){
        return currentDirectory.isDirectory();
    }

    public void saveConc(Concordance con) {
        Concordance inputcon = con;
        //String contitle = ;

        FileOutputStream fos;
        ObjectOutputStream oss = null;

        try {
            fos = new FileOutputStream(new File("con.ser"));
            oss = new ObjectOutputStream(fos);
        } catch (IOException oops) {
            oops.printStackTrace();
        }

    }

    public Concordance loadConc(String condir) {

        try {
            FileInputStream fis = new FileInputStream(condir);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Concordance c = (Concordance) ois.readObject();
            return c;

        } catch (IOException crap) {
            System.out.println("Class not found");
            crap.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(FileIOManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void loadBook(String bookTitle) {
        String tempString = "";
        
        try {

            File file = new File(currentDirectory.toString() + bookTitle);

            Scanner reader = new Scanner(file);

            while (reader.hasNext()) {
                tempString += (reader.nextLine() + " ");
            }
            
            text = tempString;

            reader.close();
        }
        catch(IOException e){
            System.out.println("Error with loading book");
            e.printStackTrace();
        }
    }

    public String getText(){
        return this.text;
    }
    
    public String viewBooks() {
        String dirString = "";
        File[] dirlist = currentDirectory.listFiles();
        for (int i = 0; i < dirlist.length; i++) {
            if (dirlist[i].isFile()) {
                dirString += dirlist[i].getName() + "\n";
            }
        }
        return dirString;

    }

    public String viewBooks(String bookDir) {
        String dirString = "";
        File dir = new File(bookDir);
        File[] dirlist = dir.listFiles();
        for (int i = 0; i < dirlist.length; i++) {
            if (dirlist[i].isFile()) {
                dirString += dirlist[i].getName();
            }
        }

        return dirString;
    }

    public String viewSavedConc() {
        String dirString = "";
        File[] dirlist = currentDirectory.listFiles();
        for (int i = 0; i < dirlist.length; i++) {
            if (dirlist[i].isFile()) {
                dirString += dirlist[i].getName();
            }
        }
        return dirString;
    }

    public String viewSavedConc(String conDir) {
        String dirString = "";
        File dir = new File(conDir);
        File[] dirlist = dir.listFiles();
        for (int i = 0; i < dirlist.length; i++) {
            if (dirlist[i].isFile()) {
                dirString += dirlist[i].getName();
            }
        }

        return conDir;
    }

}
