import java.io.OutputStream;
import java.net.Socket;

public class SocketTest {
    public static void main(String[] args) {
        try{

            Socket socket = new Socket("127.0.0.1",8888);
            OutputStream os = socket.getOutputStream();
            os.write("你好服务器".getBytes());
        }
        catch (Exception ex){

        }
    }
}
