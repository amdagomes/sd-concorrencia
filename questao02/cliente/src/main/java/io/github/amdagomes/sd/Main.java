package io.github.amdagomes.sd;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author amanda
 */
public class Main {

    public static void main(String[] args) {
        Random random = new Random();

        try {

            Map<String, Integer> numeros = new HashMap<>();

            Runnable thread = new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 0; i < 11; i++) {
                            Socket cliente = new Socket("localhost", 9999);
                            numeros.put("num1", random.nextInt(10));
                            numeros.put("num2", random.nextInt(10));
                            numeros.put("op", random.nextInt(2));

                            System.out.println(
                                    String.format("Equação: %d %s %d", numeros.get("num1"), numeros.get("op") == 0 ? "+" : "-", numeros.get("num2"))
                            );

                            ObjectOutputStream output = new ObjectOutputStream(cliente.getOutputStream());
                            output.writeObject(numeros);

                            ObjectInputStream input = new ObjectInputStream(cliente.getInputStream());

                            int response = input.readInt();

                            System.out.println("Resultado: " + response);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            };

            Thread request = new Thread(thread);
            request.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
