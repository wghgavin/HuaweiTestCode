import java.io.*;
import java.net.*;

public class ServerSocketTest {
    public static void main(String[] args) {
        try {
            ServerSocket server = new ServerSocket(8888);
            Socket socket = server.accept();
            InputStream is = socket.getInputStream();
            byte[] bytes = new byte[1024];
            int len = is.read(bytes);
            OutputStream os = socket.getOutputStream();
            os.write("收到谢谢".getBytes());
            socket.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
