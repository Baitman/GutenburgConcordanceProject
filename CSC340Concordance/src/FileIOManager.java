
/**
 * Accomplishes 5 tasks 1. Load a book into memory 2. Save a book into a
 * specified directory 3. Load a concordance locally to memory 4. Save a
 * concordance into a specified directory 5. View saved books and concordance
 *
 * @author Ochaun Marshall & Charles Mayse
 */
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileIOManager {

    private File currentDirectory;
    private FileOutputStream outputStream;
    private String text;

    /**
     * Constructor with a specific directory
     *
     * @param s - Filename
     */
    public FileIOManager(String s) {
        currentDirectory = new File(s);
    }

    public FileIOManager() {
        currentDirectory = new File(System.getProperty("user.dir"));
    }

    /**
     * Returns true if current directory is actually a directory
     *
     * @return
     */
    public boolean verify() {
        return currentDirectory.isDirectory();
    }

    /**
     * Returns the current directory
     *
     * @return
     */
    public String getCurrentDirectory() {
        return currentDirectory.getPath();
    }

    /**
     * Creates a concordance in the current directory with a specific filename
     *
     * @param con
     * @param filename
     */
    public void saveConc(Concordance con, String filename) {
        String inputcon = filename + ".ser";
        //String contitle = ;

        FileOutputStream fos;
        ObjectOutputStream oss = null;

        try {
            fos = new FileOutputStream(new File(inputcon));
            oss = new ObjectOutputStream(fos);
        } catch (IOException oops) {
            oops.printStackTrace();
        }

    }

    /**
     * Creates a concordance in the current directory
     *
     * @param con
     */
    public void saveConc(Concordance con) {
        //String inputcon = filename + ".ser";
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

    /**
     * Loads a concordance into memory
     *
     * @param condir
     * @return
     */
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

    /**
     * Loads a standard gutenburg ebook '.txt' into memory
     *
     * @param bookTitle
     */
    public void loadBook(String bookTitle) throws FileNotFoundException, IOException, GutenFreeException {
        String tempString = "";

        BufferedReader br = null;

        br = new BufferedReader(new FileReader(new File(currentDirectory.getPath() + File.separator + bookTitle)));

        String available;
        while ((available = br.readLine()) != null) {
            tempString += available + " | ";
        }

        if (tempString.contains("*** START OF THIS PROJECT GUTENBERG EBOOK")) {
            // in the future add a .txt file check 
            text = tempString;
        } else {
            throw (new GutenFreeException());
        }

    }

    /**
     * Returns the text
     *
     * @return
     */
    public String getText() {
        return this.text;
    }

    /**
     * View all '.txt' files in the current directory
     *
     * @return
     */
    public String viewBooks() throws FileNotFoundException {

        String dirString = "";
        Scanner reader;
        File[] dirlist = currentDirectory.listFiles();
        for (int i = 0; i < dirlist.length; i++) {
            if (dirlist[i].isFile() && dirlist[i].toString().contains(".txt")) {
                dirString += "\t" + dirlist[i].getName() + "\t";
                reader = new Scanner(new File(dirlist[i].getAbsolutePath()));
                dirString += reader.nextLine() + "\n";
            }
        }
        return dirString;

    }

    /**
     * Displays all the saved concordance files in the current directory
     *
     * @return
     */
    public String viewSavedConc() {
        String dirString = "";
        File[] dirlist = currentDirectory.listFiles();
        for (int i = 0; i < dirlist.length; i++) {
            if (dirlist[i].isFile() && dirlist[i].toString().contains(".ser")) {
                dirString += "\t" + dirlist[i].getName() + "\n";
            }
        }
        return dirString;
    }

    /**
     * Displays all the concordance files in a specify directory
     *
     * @param conDir
     * @return
     */
    public String viewSavedConc(String conDir) {
        String dirString = "";
        File dir = new File(conDir);
        File[] dirlist = dir.listFiles();
        for (int i = 0; i < dirlist.length; i++) {
            if (dirlist[i].isFile() && dirlist[i].toString().contains(".ser")) {
                dirString += "\t" + dirlist[i].getName() + "\n";
            }
        }

        return conDir;
    }

}
