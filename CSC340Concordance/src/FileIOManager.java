
/**
 * Accomplishes 5 tasks. 1. Load a book into memory 2. Save a book into a
 * specified directory 3. Load a concordance locally to memory 4. Save a
 * concordance into a specified directory 5. View saved books and concordance
 *
 * @author Ochaun Marshall & Charles Mayse
 */
import java.io.*;
import java.util.*;

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

    /**
     * Default constructor to set the current directory to the working directory
     * of the program if the user doesn't specify a directory.
     */
    public FileIOManager() {
        currentDirectory = new File(System.getProperty("user.dir"));
    }

    /**
     * Returns true if current directory is actually a directory
     *
     * @return True if the directory is valid. False otherwise.
     */
    public boolean verify() {
        return currentDirectory.isDirectory();
    }

    /**
     * Returns the current directory
     *
     * @return the current directory.
     */
    public String getCurrentDirectory() {
        return currentDirectory.getPath();
    }

    /**
     * Creates a concordance in the current directory with a specific filename
     *
     * @param con the concordance
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
     * @return concordance
     * @throws FileNotFoundException,IOException if the specified concordance doesn't exist.
     */
    public Concordance loadConc(String condir) throws FileNotFoundException, IOException, ClassNotFoundException {

        FileInputStream fis = new FileInputStream(currentDirectory.getPath()+File.pathSeparator+condir);
        ObjectInputStream ois = new ObjectInputStream(fis);

        Concordance c = (Concordance) ois.readObject();
        return c;

    }

    /**
     * Loads a standard gutenburg ebook '.txt' into memory
     *
     * @param bookTitle
     * @throws FileNotFoundException, IOException if there is an issue with loading the text file
     * @throws GutenFreeException is thrown if the file isn't from Project Gutenberg, or contains the Gutenberg preamble, 
     * program execution will continue.
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
     * @return the text from the file
     */
    public String getText() {
        return this.text;
    }

    /**
     * View all '.txt' files in the current directory
     *
     * @return the txt files in the current directory
     * @throws FileNotFoundException if there is an error in displaying the 
     * txt files in the directoy.
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
     * @return the list of Concordances in the directory
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

}
