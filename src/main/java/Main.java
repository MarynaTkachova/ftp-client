import org.apache.commons.net.ftp.FTPClient;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static final String GET = "get";
    public static final String FTP = "ftp";
    public static final String LS = "ls";
    public static final String CD = "cd";

    public static void main(String[] args) throws IOException {
        FTPClient ftp = new FTPClient();
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the Command:");
            String fullCommand = scan.nextLine();
            String[] command = fullCommand.split(" ");
            String method = command[0];
            switch (method) {
                case FTP:
                    FtpConnection.ftpMethod(ftp, command);
                    break;
                case LS:
                    FtpConnection.lsMethod(ftp, command);
                    break;
                case CD:
                    FtpConnection.cdMethod(ftp, command);
                    break;
                case GET:
                    FtpConnection.getMethod(ftp, command);
                    break;
                case "exit":
                    if (command.length == 1) {
                        ftp.disconnect();
                        System.exit(0);
                    } else {
                        FtpConnection.printAvailableCommands();
                    }
                    break;
                default:
                    FtpConnection.printAvailableCommands();
            }
        }
    }
}

