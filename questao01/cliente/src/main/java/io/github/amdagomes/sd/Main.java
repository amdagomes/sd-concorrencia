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
        int response;

        try {
            Socket cliente = null;
            
            Map<String, Integer> numeros = new HashMap<>();

            numeros.put("num1", random.nextInt(10));
            numeros.put("num2", random.nextInt(10));
            numeros.put("op", random.nextInt(2));
            
            while (true) {
                cliente = new Socket("localhost", 9999);
                
                System.out.println(
                    String.format("Equação: %d %s %d", numeros.get("num1"), numeros.get("op") == 0 ? "+" : "-", numeros.get("num2"))
                );
                
                ObjectOutputStream output = new ObjectOutputStream(cliente.getOutputStream());
                output.writeObject(numeros);
                
                ObjectInputStream input = new ObjectInputStream(cliente.getInputStream());
                
                response = input.readInt();
                
                System.out.println("Resultado: " + response);
                
                numeros.replace("num1", response);
                numeros.replace("num2", random.nextInt(10));
                numeros.replace("op", random.nextInt(2));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
