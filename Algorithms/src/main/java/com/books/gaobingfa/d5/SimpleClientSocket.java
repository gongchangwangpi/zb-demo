package com.books.gaobingfa.d5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by books on 2017/5/2.
 */
public class SimpleClientSocket {

    public static void main(String[] args) {

        Socket client = null;
        PrintWriter writer = null;
        BufferedReader reader = null;

        try {
            client = new Socket();
            client.connect(new InetSocketAddress("localhost", 18888));

            writer = new PrintWriter(client.getOutputStream(), true);
//            writer.print("hello");
            writer.write("hello");
            writer.flush();

            reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

            System.out.println("from server: " + reader.readLine());

        } catch (Exception e) {

        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

}
