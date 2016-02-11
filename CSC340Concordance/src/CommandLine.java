
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
                    } else {
                        fileIO = new FileIOManager(userCommand[1]);
                    }
                }
                System.out.println(fileIO.verify());
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
                    fileIO.viewBooks();
                }
                break;
            case ("loadbk"):
                command = Command.LOADBK;
                if (flowStateTransition(command)) {
                    flowState = FlowState.MANIPULATE;
                    fileIO.loadBook(userCommand[1]);
                }
                break;
            case ("makecs"):
                command = Command.MAKECS;
                if (flowStateTransition(command)) {
                    concordance = new Concordance(fileIO.getText());
                    concManager = new ConcManager(concordance.getConcordance());
                }
            case ("savecs"):
                command = Command.SAVECS;
                if (flowStateTransition(command)) {
                    fileIO.saveConc(concordance);
                }
                break;
            case ("qline"):
                command = Command.QLINE;
                if (flowStateTransition(command)) {
                    //perform command here
                }
                break;
            case ("qnline"):
                command = Command.QNLINE;
                if (flowStateTransition(command)) {
                    //perform command here
                }
                break;
            case ("qappr"):
                command = Command.QAPPR;
                if (flowStateTransition(command)) {
                    int wordCount = concManager.numLineListQuery(userCommand[1]);
                    System.out.println(userCommand[1] + " appears " + wordCount + ((wordCount == 1) ? " time" : " times"));
                }
                break;
            case ("qrank"):
                command = Command.QRANK;
                if (flowStateTransition(command)) {
                    //perform command here
                }
                break;
            case ("qdist"):
                command = Command.QDIST;
                if (flowStateTransition(command)) {
                    //perform command here
                }
                break;
            case ("qadj"):
                command = Command.QADJ;
                if (flowStateTransition(command)) {
                    //perform command here
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
