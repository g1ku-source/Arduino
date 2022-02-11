import com.fazecast.jSerialComm.SerialPort;
import java.io.*;

public class Main {

    public static void main(String[] args) {

        SerialPort serialPort = SerialPort.getCommPort("/dev/ttyACM0");
        serialPort.setComPortParameters(9600, 8, 1, 0);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

        if(!serialPort.openPort()) {

            System.out.println("Port not opened");
            return ;
        }

        try {

            Thread.sleep(2000);
            File file = new File("Robot.gcode");
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line = bufferedReader.readLine();

            while(line != null)  {

                serialPort.getOutputStream().write(line.getBytes());
                serialPort.getOutputStream().write('\n');
                serialPort.getOutputStream().flush();

                System.out.println(line);

                Thread.sleep(100);

                line = bufferedReader.readLine();
            }
            fileReader.close();
            serialPort.closePort();
        }
        catch(IOException | InterruptedException e) {

            System.out.println("Error");
        }
    }
}
