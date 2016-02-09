/**
 * This is main java file for the Concordance program. Contains Main().
 * @author Charles Mayse
 * 2/9/16
 */

/**
 * Import required for reader
 */
import java.util.Scanner;

public class Program {

    /**
     * main Entry point of the program
     * @param args Not used.
     */
    public static void main(String[] args) {
        
        /**
         * Required object references
         */
        FileIOManager fileIO = null;
        ConcManager concManager = null;

        /**
         * String and String array used to hold user input
         */
        String input;
        String[] inputArr;

        /**
         * Welcome prompt.
         */
        System.out.println("Welcome to Concordance");

        /**
         * This while loop will continue to run until the user types in 'exit'
         */
        while (true) {

            /**
             * Scanner object to read from console
             * after the user enters a command, parse the command using
             * the space character as a delimiter
             */
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            inputArr = input.trim().split(" ");

            /**
             * Commands must follow certain order to maintain program
             * integrity
             */
            CommandLine.setCommand(inputArr, fileIO, concManager);

        }
    }

    /**
     * Enumeration of all of the possible commands
     */
    private enum Command {

        START, SETDIR, FINDBK, LISTBK, LOADBK, MAKECS,
        LISTCS, LOADCS, SAVECS, QLINE, QNLINE,
        QAPPR, QRANK, QDIST, QADJ, INV, EXIT
    }

    /**
     * Enumeration of the different states the program will be in
     * SETUP - program is in a state that file directory must be established
     * FETCH - program is in a state that a file must be loaded
     * MANIPULATE - program is in final state in which it can perform any command
     */
    private enum FlowState {

        SETUP, FETCH, MANIPULATE
    }
    
    /**
     * Static class that manages the command line interface and flow
     */
    private static class CommandLine {

        /**
         * Initial states for program flow
         */
        private static Command command = Command.START;
        private static FlowState flowState = FlowState.SETUP;

        /**
         * Changes the current command and checks it against the program flow.
         * 
         * @param userCommand is the command in inputArr[0] that the user
         * entered
         */
        public static void setCommand(String[] userCommand, FileIOManager fIOMgr, ConcManager cncMgr) {
            switch (userCommand[0]) {
                case ("setdir"):
                    command = Command.SETDIR;
                    if(flowStateTransition(command)){
                        flowState = FlowState.FETCH;
                        //perform command here
                    }
                    break;
                case ("findbk"):
                    command = Command.FINDBK;
                    break;
                case ("listbk"):
                    command = Command.LISTBK;
                    break;
                case ("loadbk"):
                    command = Command.LOADBK;
                    if(flowStateTransition(command)){
                        flowState = FlowState.MANIPULATE;
                        //perform command here
                    }
                    break;
                case ("savecs"):
                    command = Command.SAVECS;
                    if(flowStateTransition(command)){
                        //perform command here
                    }
                    break;
                case ("qline"):
                    command = Command.QLINE;
                    if(flowStateTransition(command)){
                        //perform command here
                    }
                    break;
                case ("qnline"):
                    command = Command.QNLINE;
                    if(flowStateTransition(command)){
                        //perform command here
                    }
                    break;
                case ("qappr"):
                    command = Command.QAPPR;
                    if(flowStateTransition(command)){
                        //perform command here
                    }
                    break;
                case ("qrank"):
                    command = Command.QRANK;
                    if(flowStateTransition(command)){
                        //perform command here
                    }
                    break;
                case ("qdist"):
                    command = Command.QDIST;
                    if(flowStateTransition(command)){
                        //perform command here
                    }
                    break;
                case ("qadj"):
                    command = Command.QADJ;
                    if(flowStateTransition(command)){
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
        private static boolean flowStateTransition(Command c) {
            if (flowState == FlowState.SETUP && c != Command.SETDIR) {
                System.out.println("Directory isn't set. Please enter a directory path");
                return false;
            } else if (flowState == FlowState.FETCH && !(c != Command.LOADBK || c != Command.LOADCS)) {
                System.out.println("A book or concordance must be loaded at this point.");
                System.out.println("Please load a book or concordance");
                return false;
            } else {
                return true;
            }
        }

    }
}
