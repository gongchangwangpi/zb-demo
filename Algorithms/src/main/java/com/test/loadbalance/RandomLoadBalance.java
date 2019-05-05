package com.test.loadbalance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author zhangbo
 */
public class RandomLoadBalance {
    
    private static int weightSum = 0;
    private static List<Client> clientList = new ArrayList<>();
    private static Random random = new Random();
    
    static {
        Client client1 = new Client(1, 1);
        Client client2 = new Client(2, 2);
        Client client3 = new Client(3, 3);
        Client client4 = new Client(4, 4);

        clientList.add(client1);
        clientList.add(client2);
        clientList.add(client3);
        clientList.add(client4);

        for (Client client : clientList) {
            weightSum += client.weight;
        }
    }
    
    public static void main(String[] args) {

//        randomArray();
        
        randomSubtract();
        
    }

    private static void randomSubtract() {

        for (int i = 0; i < 20; i++) {
            int r = random.nextInt(weightSum);
            System.out.println(r);
            for (Client client : clientList) {
                if ((r -= client.weight) < 0) {
                    System.out.println(client);
                    break;
                }
            }   
        }
    }

    private static void randomArray() {
        // 随机数组
        List<Client> clients = new ArrayList<>();
        
        for (Client client : clientList) {
            int weight = client.getWeight();
            for (int i = 0; i < weight; i++) {
                clients.add(client);
            }
        }

        // 随机20次
        for (int i = 0; i < 20; i++) {
            System.out.println(clients.get(random.nextInt(weightSum)));
        }
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    private static class Client {
        private int id;
        private int weight;
    }
    
}
