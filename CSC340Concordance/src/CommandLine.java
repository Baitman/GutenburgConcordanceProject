
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
     * @param userCommand is the command in inputArr[0] that the user entered
     */
    public void setCommand(String[] userCommand) {
        switch (userCommand[0]) {
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
            case ("findbk"):
                command = Command.FINDBK;
                if(flowStateTransition(command)){
                System.out.println(fileIO.viewBooks());
                }
                break;
            case ("listbk"):
                command = Command.LISTBK;
                if (flowStateTransition(command)) {
                    System.out.println(fileIO.viewBooks());
                }
                break;
            case ("loadbk"):
                command = Command.LOADBK;
                if (flowStateTransition(command)) {
                    flowState = FlowState.MANIPULATE;
                    fileIO.loadBook(userCommand[1]);
                    System.out.println("\tLoaded.");
                }
                break;
            case ("makecs"):
                command = Command.MAKECS;
                if (flowStateTransition(command)) {
                    concordance = new Concordance(fileIO.getText());
                    concManager = new ConcManager(concordance.getConcordance());
                    System.out.println("\tDone.");
                }
                break;
            case ("savecs"):
                command = Command.SAVECS;
                if (flowStateTransition(command)) {
                    System.out.println("\tSaved.");
                    fileIO.saveConc(concordance);
                }
                break;
            case ("qline"):
                command = Command.QLINE;
                if (flowStateTransition(command)) {
                    System.out.println("\tThe lines in which " + userCommand[1] + " appears");
                    System.out.println("\t"+concManager.lineListQuery(userCommand[1]).toString());
                    ArrayList<Integer> arrList = concManager.lineListQuery(userCommand[1]);      
                    if(arrList == null){
                        System.out.println("\t" + userCommand[1] + " does not appear in the concordance.");
                    }else{
                        System.out.print("\tLine numbers where " + userCommand[1] + " appears: ");                

                        for( int i =0; i < arrList.size(); i++){
                            System.out.print(arrList.get(i) + ", ");
                        }                           
                        System.out.println();
                    }
                }
                break;
            case ("qnline"):
                command = Command.QNLINE;
                if (flowStateTransition(command)) {
                    System.out.println("\tNumber of lines " + userCommand[1] + " appears");
                    System.out.println("\t"+concManager.numLineListQuery(userCommand[1]).toString());
                    int lineCount = concManager.numLineListQuery(userCommand[1]);  
                    if(lineCount ==0){
                        System.out.println("\t" + userCommand[1] + " does not appear in the concordance.");
                    }else{
                    System.out.println("\t" + userCommand[1] + " appears on " + lineCount + " line(s)");
                    }
                }
                break;
            case ("qappr"):
                command = Command.QAPPR;
                if (flowStateTransition(command)) {
                    int wordCount = concManager.appearQuery(userCommand[1]);
                    System.out.println("\t"+userCommand[1] + " appears " + wordCount + ((wordCount == 1) ? " time" : " times"));
                }
                break;
            case ("qrank"):
                command = Command.QRANK;
                if (flowStateTransition(command)) {

                    System.out.println("\t"+userCommand[1]+" rank:");
                    System.out.println("\t"+concManager.rankQuery(userCommand[1]));
                    int rank = concManager.rankQuery(userCommand[1]);
                    if(rank ==0){
                        System.out.println("\t" + userCommand[1] + " does not appear in the concordance.");
                    }else{
                        System.out.println("\tRank: " + rank);
                    }
                }
                break;
            case ("qdist"):
                command = Command.QDIST;
                if (flowStateTransition(command)) {

                    System.out.println("\tNumber of words that appear " + userCommand[2] + "line(s) away from " + userCommand[1]);
                    System.out.println("\t" + concManager.distanceQuery(userCommand[1],Integer.parseInt(userCommand[2]),Integer.parseInt(userCommand[3])));
                    String[] wordArray = concManager.distanceQuery("produced", 3, 27);
                               
                   for(int i =0; i < wordArray.length; i++){
                       if(wordArray[i] ==null)
                           continue;
                       System.out.print(wordArray[i] + " ");
                   }
                    System.out.println();
                }
                break;
            case ("qadj"):
                command = Command.QADJ;
                if (flowStateTransition(command)) {
                    System.out.println("\tAdjacency: ");
                    System.out.println(concManager.adjacentQuery(userCommand[1], Boolean.TRUE));
                }
                break;
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
