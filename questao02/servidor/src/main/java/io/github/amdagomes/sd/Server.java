package io.github.amdagomes.sd;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author amanda
 */
public class Server {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        int num1 = 0;
        int num2 = 0;
        int op = 0;

        ServerSocket server = new ServerSocket(9999);

        Socket socket = null;

        Map<String, Integer> numeros = new HashMap<>();

        ObjectInputStream input = null;
        ObjectOutputStream output = null;

        while (true) {

            socket = server.accept();

            input = new ObjectInputStream(socket.getInputStream());
            output = new ObjectOutputStream(socket.getOutputStream());

            numeros = (Map<String, Integer>) input.readObject();

            num1 = numeros.get("num1");
            num2 = numeros.get("num2");
            op = numeros.get("op");

            System.out.println(String.format("Response: %d", op == 0 ? num1 + num2 : num1 - num2));

            output.writeInt(
                    op == 0 ? num1 + num2 : num1 - num2
            );

            output.flush();
        }
    }
}
