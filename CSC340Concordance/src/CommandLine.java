/**
 * CommandLine interface.
 * 
 * @author Charles Mayse
 */


import java.io.FileNotFoundException;
import java.util.ArrayList;

public class CommandLine {

    /**
     * Required object references
     */
    FileIOManager fileIO = null;
    Concordance concordance = null;
    ConcManager concManager = null;

    /**
     * Enumeration of all of the possible commands
     */
    private enum Command {

        START, SETDIR, FINDBK, LISTBK, LOADBK, MAKECS,
        LISTCS, LOADCS, SAVECS, QLINE, QNLINE,
        QAPPR, QRANK, QDIST, QADJ, INV, EXIT
    }

    /**
     * Enumeration of the different states the program will be in SETUP -
     * program is in a state that file directory must be established FETCH -
     * program is in a state that a file must be loaded MANIPULATE - program is
     * in final state in which it can perform any command
     */
    private enum FlowState {

        SETUP, FETCH, MANIPULATE
    }

    /**
     * Initial states for program flow
     */
    private Command command = Command.START;
    private FlowState flowState = FlowState.SETUP;

    /**
     * Changes the current command and checks it against the program flow.
     *
     * @param userCommand is the command in inputArr[0] that the user entered, inputArr[1] and inputArr[2] 
     * are also used for input
     */
    public void setCommand(String[] userCommand) throws FileNotFoundException {
        switch (userCommand[0]) {
            /**
             * Sets the directory
             */
            case ("setdir"):
                command = Command.SETDIR;
                if (flowStateTransition(command)) {
                    flowState = FlowState.FETCH;
                    if (userCommand.length < 2) {
                        fileIO = new FileIOManager();
                        System.out.println("\tCurrent directory is set to: " + fileIO.getCurrentDirectory());
                    } else {
                        fileIO = new FileIOManager(userCommand[1]);
                    }
                }
                System.out.println("\tDirectory valid?" + ((fileIO.verify())?"Yes":"No"));
                break;
            /**
             * Finds a book on Gutenberg.org, however for this implementation, it will
             * simply display the contents of the directory
             */
            case ("findbk"):
                command = Command.FINDBK;
                if(flowStateTransition(command)){
                System.out.println(fileIO.viewBooks());
                }
                break;
            /**
             * Lists the contents of the directory
             */
            case ("listbk"):
                command = Command.LISTBK;
                if (flowStateTransition(command)) {
                    System.out.println(fileIO.viewBooks());
                }
                break;
            /**
             * Loads the book into the program
             */
            case ("loadbk"):
                command = Command.LOADBK;
                if (flowStateTransition(command)) {
                    flowState = FlowState.MANIPULATE;
                    fileIO.loadBook(userCommand[1]);
                    System.out.println("\tLoaded.");
                }
                break;
            /**
             * Initiates and makes the concordance
             */
            case ("makecs"):
                command = Command.MAKECS;
                if (flowStateTransition(command)) {
                    concordance = new Concordance(fileIO.getText());
                    concManager = new ConcManager(concordance.getConcordance());
                    System.out.println("\tDone.");
                }
            /**
             * Saves the concordance
             */
                break;
            case ("savecs"):
                command = Command.SAVECS;
                if (flowStateTransition(command)) {
                    System.out.println("\tSaved.");
                    fileIO.saveConc(concordance);
                }
                break;
            /**
             * Shows which line the target word appears
             */
            case ("qline"):
                command = Command.QLINE;
                if (flowStateTransition(command)) {
                    System.out.println("\tThe lines in which " + userCommand[1].toLowerCase() + " appears");
                    System.out.println("\t"+concManager.lineListQuery(userCommand[1].toLowerCase()).toString());
                    ArrayList<Integer> arrList = concManager.lineListQuery(userCommand[1].toLowerCase());      
                    if(arrList == null){
                        System.out.println("\t" + userCommand[1].toLowerCase() + " does not appear in the concordance.");
                    }else{
                        System.out.print("\tLine numbers where " + userCommand[1].toLowerCase() + " appears: ");                

                        for( int i =0; i < arrList.size(); i++){
                            System.out.print(arrList.get(i) + ", ");
                        }                           
                        System.out.println();
                    }
                }
                break;
           /**
            * Shows the number of lines a target word appears in a concordance
            */
            case ("qnline"):
                command = Command.QNLINE;
                if (flowStateTransition(command)) {
                    System.out.println("\tNumber of lines " + userCommand[1].toLowerCase() + " appears");
                    System.out.println("\t"+concManager.numLineListQuery(userCommand[1]).toString().toLowerCase());
                    int lineCount = concManager.numLineListQuery(userCommand[1].toLowerCase());  
                    if(lineCount ==0){
                        System.out.println("\t" + userCommand[1].toLowerCase() + " does not appear in the concordance.");
                    }else{
                    System.out.println("\t" + userCommand[1].toLowerCase() + " appears on " + lineCount + " line(s)");
                    }
                }
                break;
            /**
             * Shows the number of times a word appears in a concordance
             */
            case ("qappr"):
                command = Command.QAPPR;
                if (flowStateTransition(command)) {
                    int wordCount = concManager.appearQuery(userCommand[1].toLowerCase());
                    System.out.println("\t" + userCommand[1].toLowerCase() + " appears " + wordCount + ((wordCount == 1) ? " time" : " times"));
                }
                break;
            /**
             * Shows target word rank
             */
            case ("qrank"):
                command = Command.QRANK;
                if (flowStateTransition(command)) {
                    System.out.print("\t"+userCommand[1].toLowerCase()+" rank:");
                    System.out.println(" "+concManager.rankQuery(userCommand[1].toLowerCase()));
                    int rank = concManager.rankQuery(userCommand[1].toLowerCase());
                    if(rank ==0){
                        System.out.println("\t" + userCommand[1].toLowerCase() + " does not appear in the concordance.");
                    }else{
                        System.out.println("\tRank: " + rank);
                    }
                }
                break;
            /**
             * Shows the number of words that appear within a line distance of a target word
             */
            case ("qdist"):
                command = Command.QDIST;
                if (flowStateTransition(command)) {

                    System.out.println("\tNumber of words that appear " + userCommand[2] + "line(s) away from " + userCommand[1].toLowerCase());
                    System.out.println("\t" + concManager.distanceQuery(userCommand[1].toLowerCase(),Integer.parseInt(userCommand[2]),Integer.parseInt(userCommand[3])));
                    String[] wordArray = concManager.distanceQuery("produced", 3, 27);
                               
                   for(int i =0; i < wordArray.length; i++){
                       if(wordArray[i] ==null)
                           continue;
                       System.out.print(wordArray[i] + " ");
                   }
                    System.out.println();
                }
                break;
            /**
             * Shows the number of words that appear adjacent to a target word
             */
             case ("qadj"):
                command = Command.QADJ;
                if (flowStateTransition(command)) {
                    ArrayList<Integer> lines = null;
                    
                    try{
                        if(userCommand[3] != null)
                            lines = concManager.adjacentQuery(userCommand[1], userCommand[2], userCommand[3]);                           
                    }
                    catch(ArrayIndexOutOfBoundsException e){
                        lines = concManager.adjacentQuery(userCommand[1], userCommand[2]);
                    }
                        
                 
                    if(lines != null){
                        System.out.print("\tThese words are adjacent on lines: ");
                        for(int i =0; i < lines.size(); i++){                        
                            System.out.print(lines.get(i) + ", ");
                        }
                        System.out.println("");
                    }
                    else
                        System.out.println("\tOne or more selected word does not appear in the concordance.");
                }
                break;
            /**
             * Exits the program.
             */
            case ("exit"):
                command = Command.EXIT;
                System.exit(0);
                break;
            default:
                command = Command.INV;
                System.out.println("Invalid command, check command spelling or format");
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
            System.out.println("Directory isn't set. Please enter a directory path");
            return false;
        } else if (flowState == FlowState.FETCH && !(c != Command.LOADBK || c != Command.LOADCS || c != Command.FINDBK
                || c != Command.LISTBK)) {
            System.out.println("A book or concordance must be loaded at this point.");
            System.out.println("Please load a book or concordance");
            return false;
        } else {
            return true;
        }
    }

}
