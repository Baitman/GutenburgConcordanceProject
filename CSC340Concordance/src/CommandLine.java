

/**
 * CommandLine interface.
 *
 * @author Charles Mayse 
 * 
 * Complete: 3/4/16
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class CommandLine {

    /**
     * Required object references.
     */
    FileIOManager fileIO = null;
    Concordance concordance = null;
    ConcManager concManager = null;

    /**
     * Enumeration of all of the possible commands.
     */
    private enum Command {

        START, SETDIR, LISTCS, LISTBK, LOADBK, MAKECS,
        LOADCS, SAVECS, QLINE, QNLINE,
        QAPPR, QRANK, QDIST, QADJ, INV, EXIT
    }

    /**
     * Enumeration of the different states the program will be in SETUP -
     * program is in a state that file directory must be established FETCH -
     * program is in a state that a file must be loaded MANIPULATE - program is
     * in final state in which it can perform any command.
     */
    private enum FlowState {

        SETUP, FETCH, MANIPULATE
    }

    /**
     * Initial states for program flow.
     */
    private Command command = Command.START;
    private FlowState flowState = FlowState.SETUP;

    /**
     * Changes the current command and checks it against the program flow.
     *
     * @param userCommand is the command in inputArr[0] that the user entered,
     * inputArr[1] and inputArr[2] are also used for input.
     */
    public void setCommand(String[] userCommand) throws FileNotFoundException {
        switch (userCommand[0]) {
            /**
             * Sets the directory.
             */
            case ("setdir"):
                command = Command.SETDIR;
                if (flowStateTransition(command)) {
                    if (userCommand.length < 2) {
                        fileIO = new FileIOManager();
                        System.out.println("\tCurrent directory is set to: " + fileIO.getCurrentDirectory());
                    } else {
                        fileIO = new FileIOManager(userCommand[1]);
                    }
                    if (fileIO.verify()) {
                        flowState = FlowState.FETCH;
                        System.out.println("\tDirectory is set.");
                    } else {
                        System.out.println("\tDirectory is not valid. Please try again.");
                    }
                }
                break;
            /**
             * Finds a book on Gutenberg.org, however for this implementation,
             * it will simply display the contents of the directory.
             */
            case ("listcs"):
                command = Command.LISTCS;
                if (flowStateTransition(command)) {
                    try {
                        System.out.println(fileIO.viewSavedConc());
                    } catch (FileNotFoundException fnfe) {
                        System.out.println("\tError with displaying concordances in the directory");
                        break;
                    }
                }
                break;
            /**
             * Lists the contents of the directory.
             */
            case ("listbk"):
                command = Command.LISTBK;
                if (flowStateTransition(command)) {
                    try {
                        System.out.print(fileIO.viewBooks());
                    } catch (FileNotFoundException fnfe) {
                        System.out.println("\tError with displaying text files in the directory");
                        break;
                    }
                }
                break;
            /**
             * Loads the book into the program.
             */
            case ("loadbk"):
                command = Command.LOADBK;
                try {
                    System.out.print("\tLoading text file...");
                    if (flowStateTransition(command)) {
                        fileIO.loadBook(userCommand[1]);
                    }
                    flowState = FlowState.MANIPULATE;
                    System.out.println("loaded.");
                } catch (FileNotFoundException fnfe) {
                    System.out.println("\tError loading file, be sure to check filenames and extensions");
                    break;
                } catch (GutenFreeException gfe) {
                    System.out.println("\tWarning: File is not from Gutenberg.org, concordance will not be made.");
                    break;
                } catch (IOException ex) {
                    System.out.println("\tError with loading file");
                    break;
                } catch (ArrayIndexOutOfBoundsException aioobe) {
                    System.out.println("\tInvalid command syntax: <command> <filename>");
                    break;
                }
                break;
            /**
             * Loads a concordance.
             */
            case ("loadcs"):
                command = Command.LOADCS;
                try {
                    if (flowStateTransition(command)) {
                        System.out.println("\tLoading: " + fileIO.getCurrentDirectory()+File.separator+userCommand[1]);
                        this.concordance = fileIO.loadConc(userCommand[1]);
                        concManager = new ConcManager(concordance.getConcordance());
                    }
                    flowState = FlowState.MANIPULATE;
                    System.out.println("\tConcordance loaded.");
                } catch (FileNotFoundException fnfe) {
                    System.out.println("\tConcordance not found. Check file name and extension");
                    break;
                } catch (IOException ioe) {
                    System.out.println("\tError loading Concordance. Try again.");
                    System.out.println(ioe.getMessage());
                    ioe.printStackTrace();
                    break;
                } catch (ClassNotFoundException cnfe) {
                    System.out.println("\tConcordance not found. Check file name and extension");
                    break;
                }
                break;

            /**
             * Initiates and makes the concordance.
             */
            case ("makecs"):
                command = Command.MAKECS;
                if (flowStateTransition(command)) {
                    try {
                        concordance = new Concordance(fileIO.getText());
                        concManager = new ConcManager(concordance.getConcordance());
                        System.out.println("\tDone.");
                    } catch (NullPointerException npe) {
                        System.out.println("\tError in making concordance. A book/text file must be loaded prior to making a concordance");
                        break;
                    }
                }
                break;
            /**
             * Saves the concordance.
             */
            case ("savecs"):
                command = Command.SAVECS;
                if (flowStateTransition(command)) {
                    try {
                        fileIO.saveConc(concordance, userCommand[1]);
                        System.out.println("\tSaved.");
                    } catch (FileNotFoundException fnfe) {
                        System.out.println("Error in saving concordance. Check for similar filenames inside directory");
                    } catch (IOException ioe) {
                        System.out.println("Error in saving concordance. Try again.");
                    } catch (ArrayIndexOutOfBoundsException aioobe) {
                        System.out.println("\tInvalid command syntax: <command> <filename>");
                    }
                }
                break;
            /**
             * Shows which line the target word appears.
             */
            case ("qline"):
                command = Command.QLINE;
                if (flowStateTransition(command)) {
                    try {
                        ArrayList<Integer> arrList = concManager.lineListQuery(userCommand[1].toLowerCase());
                        if (arrList == null) {
                            System.out.println("\t" + userCommand[1].toLowerCase() + " does not appear in the concordance.");
                        } else {
                            System.out.print("\tLine numbers where " + userCommand[1].toLowerCase() + " appears: \n");

                            for (int i = 0; i < arrList.size(); i++) {
                                System.out.println("\t" + arrList.get(i));
                            }
                        }
                    } catch (ArrayIndexOutOfBoundsException aioobe) {
                        System.out.println("\tInvalid command syntax: <command> <string>");
                    } catch (NullPointerException npe) {
                        System.out.println("\tPlease make a concordance before performing a query");
                    }
                }
                break;
            /**
             * Shows the number of lines a target word appears in a concordance.
             */
            case ("qnline"):
                command = Command.QNLINE;
                if (flowStateTransition(command)) {
                    try {
                        int lineCount = concManager.numLineListQuery(userCommand[1].toLowerCase());
                        if (lineCount == 0) {
                            System.out.println("\t" + userCommand[1].toLowerCase() + " does not appear in the concordance.");
                        } else {
                            System.out.println("\t" + userCommand[1].toLowerCase() + " appears on " + lineCount + " line(s)");
                        }
                    } catch (ArrayIndexOutOfBoundsException aioobe) {
                        System.out.println("\tInvalid command syntax: <command> <string>");
                    } catch (NullPointerException npe) {
                        System.out.println("\tPlease make a concordance before performing a query");
                    }
                }
                break;
            /**
             * Shows the number of times a word appears in a concordance.
             */
            case ("qappr"):
                command = Command.QAPPR;
                if (flowStateTransition(command)) {
                    try {
                        int wordCount = concManager.appearQuery(userCommand[1].toLowerCase());
                        System.out.println("\t" + userCommand[1].toLowerCase() + " appears " + wordCount + ((wordCount == 1) ? " time" : " times"));
                    } catch (ArrayIndexOutOfBoundsException aioobe) {
                        System.out.println("\tInvalid command syntax: <command> <string>");
                    } catch (NullPointerException npe) {
                        System.out.println("\tPlease make a concordance before performing a query");
                    }
                }
                break;
            /**
             * Shows target word rank
             */
            case ("qrank"):
                command = Command.QRANK;
                if (flowStateTransition(command)) {
                    try {
                        int rank = concManager.rankQuery(userCommand[1].toLowerCase());
                        if (rank == 0) {
                            System.out.println("\t" + userCommand[1].toLowerCase() + " is a common word and excluded from rank");
                        } else if (rank == -1) {
                            System.out.println("\t" + userCommand[1].toLowerCase() + " does not appear in the concordance.");
                        } else {
                            System.out.println("\tRank: " + rank);
                        }
                    } catch (ArrayIndexOutOfBoundsException aioobe) {
                        System.out.println("\tInvalid command syntax: <command> <string>");
                    } catch (NullPointerException npe) {
                        System.out.println("\tPlease make a concordance before performing a query");
                    }
                }
                break;
            /**
             * Shows the number of words that appear within a line distance of a
             * target word.
             */
            case ("qdist"):
                command = Command.QDIST;
                if (flowStateTransition(command)) {
                    try {
                        String[] wordArray = concManager.distanceQuery(userCommand[1].toLowerCase(), Integer.parseInt(userCommand[2]), Integer.parseInt(userCommand[3]));
                        System.out.println("\tLocations of words that appear " + userCommand[2] + "line(s) away from " + userCommand[1].toLowerCase());
                        for (String word : wordArray) {
                            System.out.print(word + " ");
                        }
                        System.out.println();
                    } catch (ArrayIndexOutOfBoundsException aioobe) {
                        System.out.println("Invalid command syntax: <command> <string> <integer> <integer>");
                    } catch (NullPointerException npe) {
                        System.out.println("\tPlease make a concordance before performing a query");
                    }
                }
                break;
            /**
             * Shows the number of words that appear adjacent to a target word.
             */
            case ("qadj"):
                command = Command.QADJ;
                if (flowStateTransition(command)) {
                    ArrayList<Integer> lines = null;

                    try {
                        if (userCommand[3] != null) {
                            lines = concManager.adjacentQuery(userCommand[1], userCommand[2], userCommand[3]);
                        }
                    } catch (ArrayIndexOutOfBoundsException e) {
                        lines = concManager.adjacentQuery(userCommand[1], userCommand[2]);
                    } catch (NullPointerException npe) {
                        System.out.println("\tPlease make a concordance before performing a query");
                        break;
                    }

                    if (lines != null && lines.size()>0) {
                        System.out.println("\tThese words are adjacent on lines: ");
                        for (Integer line : lines) {
                            System.out.println("\t" + line);
                        }
                    } else {
                        System.out.println("\tOne or more selected word does not appear adjacent in the concordance.");
                    }
                }
                break;
            /**
             * Exits the program.
             */
            case ("exit"):
                command = Command.EXIT;
                System.out.println("\tThank you for using Concordance. Now exited.");
                System.exit(0);
                break;
            default:
                command = Command.INV;
                System.out.println("\tInvalid command, check command spelling or format");
                break;
        }
    }

    /**
     * Checks the current command and verifies that its execution is appropriate
     * for the current state of the program. Displays an error message.
     *
     * @param c the command being performed
     * @return true if the command is acceptable, false otherwise
     */
    private boolean flowStateTransition(Command c) {
        if (flowState == FlowState.SETUP && c != Command.SETDIR) {
            System.out.println("\tA valid directory isn't set. Please set a valid directory path.");
            return false;
        } else if (flowState == FlowState.FETCH && !(c != Command.LOADBK || c != Command.LOADCS || c != Command.LISTCS
                || c != Command.LISTBK)) {
            System.out.println("\tA book or concordance must be loaded at this point.");
            System.out.println("\tPlease load a book or concordance");
            return false;
        } else {
            return true;
        }
    }

}
