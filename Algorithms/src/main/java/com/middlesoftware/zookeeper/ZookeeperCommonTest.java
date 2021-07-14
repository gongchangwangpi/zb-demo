package com.middlesoftware.zookeeper;

import com.alibaba.fastjson.JSON;
import com.util.io.ScannerUtil;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;

/**
 * @author zhangbo
 */
@Slf4j
public class ZookeeperCommonTest extends ConnectionWatcher {

    private static final String op_create = "create";
    private static final String op_delete = "delete";
    private static final String op_set_data = "setdata";
    private static final String op_get_data = "getdata";
    private static final String op_sync = "sync";
    private static final String op_get_children = "getchildren";
    private static final String op_exists = "exists";

    public void process(String op, String path, Object data) throws KeeperException, InterruptedException {
        path = "/" + path;

        switch (op) {
            case op_create:
                String createdPath = zk.create(path, data == null ? null : JSON.toJSONBytes(data), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                log.info("Created path: {}", createdPath);
                break;
            case op_delete:
                zk.delete(path, data == null ? -1 : (int) data);
                break;
            case op_set_data:
                zk.setData(path, data == null ? null : JSON.toJSONBytes(data), -1);
                break;
            case op_get_data:
                byte[] resp = zk.getData(path, null, null);
                log.info("get data: {}", new String(resp));
                break;
            case op_sync:
                zk.sync(path, null, null);
                break;
            case op_get_children:
                List<String> children = zk.getChildren(path, null);
                log.info("get children: {}", children);
                break;
            case op_exists:
                Stat exists = zk.exists(path, null);
                log.info("exists: {}", exists);
                break;
            default:
                log.info("command error: {}", op);
        }

    }
    
    public static void main(String[] args) throws Exception {
        ZookeeperCommonTest createGroup = new ZookeeperCommonTest();
        createGroup.connect("127.0.0.1:2183");

        ScannerUtil.scanHandle(4, (s) -> {
            System.out.println("read from console: " + s);
            String[] arr = s.split(" ");
            try {
                createGroup.process(arr[0], args[1], arr[2]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
//        createGroup.close();

        System.in.read();
    }
}
