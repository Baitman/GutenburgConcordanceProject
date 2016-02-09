
import java.util.Scanner;

public class Program {

    public static void main(String[] args) {

        String input;
        String[] inputArr;

        System.out.println("Welcome to Concordance");

        while (true) {

            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            inputArr = input.trim().split(" ");

            CommandLine.setCommand(inputArr[0]);

        }
    }

    private enum Command {

        START, SETDIR, FINDBK, LISTBK, LOADBK, MAKECS,
        LISTCS, LOADCS, SAVECS, QLINE, QNLINE,
        QAPPR, QRANK, QDIST, QADJ, INV, EXIT
    }

    private enum FlowState {

        SETUP, FETCH, MANIPULATE
    }

    private static class CommandLine {

        private static Command command = Command.START;
        private static FlowState flowState = FlowState.SETUP;

        public static void setCommand(String userCommand) {
            switch (userCommand) {
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
