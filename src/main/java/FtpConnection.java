import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.UnknownHostException;

public class FtpConnection {

    public static final int COMMAND_FROM_ONE_PART = 1;
    public static final int COMMAND_FROM_TWO_PART = 2;
    public static final int COMMAND_FROM_THREE_PART = 3;

    public static void lsMethod(FTPClient ftp, String[] command) throws IOException {
        if (command.length == COMMAND_FROM_ONE_PART) {
            FTPFile[] files = ftp.listFiles();
            for (FTPFile file : files) {
                if (file.isDirectory()) {
                    System.out.println("Directory = " + file.getName());
                } else {
                    System.out.println("File = " + file.getName());
                }
            }
        } else {
            printAvailableCommands();
        }
    }

    public static void cdMethod(FTPClient ftp, String[] command) throws IOException {
        if (command.length == COMMAND_FROM_TWO_PART) {
            if ("..".equals(command[1])) {
                boolean result2 = ftp.changeToParentDirectory();
                if (result2) {
                    System.out.println("working directory is changed to parent directory.");
                } else {
                    System.out.println("Unable to change the working directory to parent directory.");
                }
            } else {
                boolean result = ftp.changeWorkingDirectory(command[1]);
                if (result) {
                    System.out.println("working directory is changed.");
                } else {
                    System.out.println("Unable to change the working directory.");
                }
            }
        } else {
            printAvailableCommands();
        }
    }

    public static void ftpMethod(FTPClient ftp, String[] command) throws IOException {
        if (command.length == COMMAND_FROM_TWO_PART) {
            if ("close".equals(command[1])) {
                ftp.disconnect();
            } else {
                try {
                    ftp.connect(command[1]);
                    ftp.enterLocalPassiveMode();
                    ftp.login("anonymous", "");
                    System.out.println("Successful connection.");
                } catch (UnknownHostException e) {
                    System.out.println("Unable to connection.");
                }
            }
        } else {
            printAvailableCommands();
        }
    }

    public static void getMethod(FTPClient ftp, String[] command) throws IOException {
        if (command.length == COMMAND_FROM_THREE_PART) {
            try {
                FileOutputStream fos = new FileOutputStream(command[2] + "/" + command[1]);
                ftp.retrieveFile(command[1], fos);
                fos.close();
                System.out.println("File was saved.");
            } catch (Exception e) {
                printAvailableCommands();
            }
        } else {
            printAvailableCommands();
        }
    }

    public static void printAvailableCommands() {
        System.out.println("Unknown command.You can use command:" +
                "\nftp <host>" +
                "\nftp close" +
                "\nls" +
                "\ncd .." +
                "\ncd <dir>" +
                "\nget <pathToFile> <pathToSave>" +
                "\nexit");
    }
}
