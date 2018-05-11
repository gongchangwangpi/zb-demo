package com.books.gaobingfa.d5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by books on 2017/5/2.
 */
public class SimpleMultiThreadEchoServer {

//    private static ExecutorService service = Executors.newFixedThreadPool(5);

    public static void main(String[] args) throws IOException {
        ExecutorService service = Executors.newFixedThreadPool(5);
        ServerSocket server = new ServerSocket(18888);

        while (true) {
            Socket client = server.accept();
            System.out.println(client.getRemoteSocketAddress() + " connect");

            service.execute(new HandleMsg(client));

        }

    }

    static class HandleMsg implements Runnable {

        private Socket client;

        public HandleMsg(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            BufferedReader reader = null;
            PrintWriter writer = null;
            try {
                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));

                writer = new PrintWriter(client.getOutputStream(), true);

                String line = null;
                /*while ((line = reader.readLine()) != null) {
                    writer.print(line);
                }*/
                line = reader.readLine();
                System.out.println("from client: " + line);
                writer.print(line);
            } catch (Exception e) {
                e.printStackTrace();
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

}
