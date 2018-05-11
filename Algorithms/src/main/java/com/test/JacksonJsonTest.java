package com.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.test.chexian.api.dto.CarDto;
import com.util.JacksonJsonMapper;

/**
 * Jackson 测试
 * 
 * @author books
 */
public class JacksonJsonTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);

        final String jsonStr1 = "{\"isTransferVehicle\":false,\"vehicleClass\":\"越野车类\",\"vehicleCurbWeight\":\"2.13\",\"vehicleDisplacement\":\"3.956\",\"vehicleEngineNo\":\"K066375\",\"vehicleEnginePower\":\"179\",\"vehicleId\":\"FTAAVD0010\",\"vehicleModel\":\"丰田SCT6491\",\"vehicleName\":\"丰田SCT6491E3轻型客车\",\"vehicleOriginType\":\"合资\",\"vehiclePlate\":\"川AVG332\",\"vehiclePrice\":610000,\"vehicleRegDate\":1485100800000,\"vehicleSeat\":\"8\",\"vehicleUseCharacter\":1,\"vehicleVin\":\"LFMAY86C8G0519009\"}";
        final String jsonStr2 = "{\"vehiclePlate\":\"川AVG332\",\"vehicleUseCharacter\":1,\"isTransferVehicle\":false,\"vehicleModel\":\"丰田SCT6491\",\"vehicleVin\":\"LFMAY86C8G0519009\",\"vehicleEngineNo\":\"K066375\",\"vehicleRegDate\":1485100800000,\"vehicleSeat\":\"8\",\"vehicleId\":\"FTAAVD0010\",\"vehicleName\":\"丰田SCT6491E3轻型客车\",\"vehicleClass\":\"越野车类\",\"vehiclePrice\":610000,\"vehicleCurbWeight\":\"2.13\",\"vehicleEnginePower\":\"179\",\"vehicleDisplacement\":\"3.956\",\"vehicleOriginType\":\"合资\"}";

        for (int i = 0; i < 20; i++) {
            
            if (i % 2 == 0) {
                executorService.submit(() -> {
                    CarDto carDto = JacksonJsonMapper.INSTANCE.fromJson(jsonStr1, CarDto.class);
                    System.out.println(JacksonJsonMapper.INSTANCE.toJson(carDto));
                });

            } else {
                executorService.submit(() -> {
                    CarDto carDto = JacksonJsonMapper.INSTANCE.fromJson(jsonStr2, CarDto.class);
                    System.out.println(JacksonJsonMapper.INSTANCE.toJson(carDto));

                });
            }
            
        }
        
        executorService.shutdown();


    }
    
    
    
}
