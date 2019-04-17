package com.dubbo.test.serialize.hessian;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.serialize.ObjectInput;
import com.alibaba.dubbo.common.serialize.ObjectOutput;
import com.alibaba.dubbo.common.serialize.support.hessian.Hessian2Serialization;
import com.alibaba.fastjson.JSON;
import com.dubbo.test.serialize.Parent;
import com.dubbo.test.serialize.QueryResult;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author zhangbo
 */
public class Hessian2SerializeTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        URL url = new URL("protocol", "1.1.1.1", 1234);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
        Hessian2Serialization hessian2Serialization = new Hessian2Serialization();
        
        // 准备数据
        QueryResult<Parent> queryResult = new QueryResult<>();
        Parent[] parents = new Parent[1];
        Parent parent = new Parent(1L, "parent1", "1");
        parents[0] = parent;
        queryResult.setData(parents);
        queryResult.setBody(parent);
        queryResult.setList(Arrays.asList(parent));

        System.out.println(queryResult.getClass());
        System.out.println(queryResult.getBody().getClass());
        System.out.println(queryResult.getData().getClass());
        System.out.println(queryResult.getData()[0].getClass());
        System.out.println(queryResult.getList().getClass());

        System.out.println("----------------------------------------");

        // 序列化
        ObjectOutput objectOutput = hessian2Serialization.serialize(url, byteArrayOutputStream);
        objectOutput.writeObject(queryResult);
        objectOutput.flushBuffer();

        // 反序列化
        byte[] bytes = byteArrayOutputStream.toByteArray();
        System.out.println(new String(bytes));
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInput deserialize = hessian2Serialization.deserialize(url, byteArrayInputStream);
        
        QueryResult result = deserialize.readObject(queryResult.getClass());

        System.out.println(result.getClass());
        System.out.println(result.getBody().getClass());
        System.out.println(result.getData().getClass());
        System.out.println(result.getData()[0].getClass());
        System.out.println(result.getList().getClass());
        System.out.println(result.getList());

        System.out.println("-------------------------------------");
        
        String jsonString = JSON.toJSONString(queryResult);
        System.out.println(jsonString);
        
//        QueryResult<Parent> result1 = JSON.parseObject(jsonString, new TypeReference<QueryResult<Parent>>() {});
        QueryResult<Parent> result1 = JSON.parseObject(jsonString, QueryResult.class);

        Parent[] data = result1.getData();
        System.out.println(data);
    }
    
}
