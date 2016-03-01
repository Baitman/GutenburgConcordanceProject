/*
 * This is a custom exception to be thrown when the user tries to make a 
a concordance out of a .txt file that is not a standard project gutenburg .txt 
file.
 */

/**
 *
 * @author Ochaun Marshall
 */
public class GutenFreeException extends Exception{
    public static final long serialVersionUID = 42L;
    // exception code will be placed here
    // 1. Check if it is a .txt file
    // 2. Check if that .txt file contains the *** line
    // 3. if either of the conditions 
    @Override
    public String getMessage(){
     return "ERROR: User tried to load a nonstandard file" + "\n" 
             + "Concordance will not be made from this file";
    }
}

// throw new GutenFreeException ("User tried to concordanate a nonstandard file")