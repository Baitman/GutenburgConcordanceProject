
/**
 * Accomplishes 5 tasks 1. Load a book into memory 2. Save a book into a
 * specified directory 3. Load a concordance locally to memory 4. Save a
 * concordance into a specified directory ======= 4. Save a conccordance into a
 * specified directory >>>>>>> origin/master 5. View saved books and concordance
 */
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileIOManager {

    private File currentDirectory;
    private FileOutputStream outputStream;

    public FileIOManager(String s) {
        currentDirectory = new File(s);

    }

    public FileIOManager() {
        currentDirectory = new File(System.getProperty("user.dir"));
    }

    public File getCurrentDirectory() {
        return this.currentDirectory;
    }

    public FileOutputStream getOutputStream() {
        return this.outputStream;
    }

    public void setOutputStream(FileOutputStream fos) {
        this.outputStream = fos;
    }

    public void setCurrentDirectory(File dir) {
        this.currentDirectory = dir;
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

    public String loadBook(String bookFile) {
        String line = null;
        String textcontent = "";

        String text = "";
        try {
            File file = new File(currentDirectory.toString() + bookFile);
            FileReader fileReader
                    = new FileReader(bookFile);

            BufferedReader buffReader
                    = new BufferedReader(fileReader);
            Scanner reader = new Scanner(file);
            while ((line = buffReader.readLine()) != null) {
                textcontent += line;
                //The "|" is used as a line delimiter
                while (reader.hasNext()) {
                    text += (reader.nextLine() + " ");
                }

                reader.close();
            }
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + currentDirectory + "'");
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(FileIOManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return text;

    }

    public String viewBooks() {
        String dirString = "";
        File[] dirlist = currentDirectory.listFiles();
        for (int i = 0; i < dirlist.length; i++) {
            if (dirlist[i].isFile()) {
                dirString += dirlist[i].getName();
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

    /**
     * Future methods that may need to be implemented if books and concordances
     * are to be saved in the same directory. These methods would be better than
     * just the standard .isFile() check. These methods cannot be implemented
     * without getters or setters
     *
     * @param c
     * @return
     */

    public boolean isConc(Concordance c) {
        throw new UnsupportedOperationException();
   //return c.isFile() && c.getHashmap != null;
    }

    public boolean isBook(File b) {
        throw new UnsupportedOperationException();
    //What are the filds for a book object?
        //return c.isFile() && b.title != null;
    }

}
