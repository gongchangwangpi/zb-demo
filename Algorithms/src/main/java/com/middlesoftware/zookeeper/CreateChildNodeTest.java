package com.middlesoftware.zookeeper;

/**
 * 临时节点和临时顺序节点，不能创建子节点
 * 持久节点和持久顺序节点可以创建子节点
 * 
 * @author zhangbo
 */
public class CreateChildNodeTest extends ConnectionWatcher {

    public static void main(String[] args) throws Exception {
        
        CreateChildNodeTest test = new CreateChildNodeTest();
        test.connect("172.18.8.34");

        byte[] data = test.zk.getData("/test_persistent", true, null);
        System.out.println("-------------- " + new String(data));
        test.zk.setData("/test_persistent", "test".getBytes(), 0);
        
        data = test.zk.getData("/test_persistent", true, null);
        System.out.println("-------------- " + new String(data));
        
    }
    
}
