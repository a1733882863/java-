package com.zookeeper;
import jdk.net.SocketFlow;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

/**
 * Zookeeper和doubble的代码
 */
public class ZookeeperTest {
    private String connectString = "192.168.199.128,192.168.199.248,192.168.199.129";
    private int sessionTimeout = 60*1000;
    private ZooKeeper zkClient;

    @Before
    public void init() throws IOException {
        zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
                System.out.println("得到监听反馈,进行业务处理");
            }
        });
    }

    @Test
    public void createNode() throws KeeperException, InterruptedException {
        String nodeCreate = zkClient.create("/lg1","laox".getBytes(),ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        //参数1:要创建的节点路径
        //参数2:节点数据
        //参数3:节点权限
        //参数4:节点的类型
        System.out.println("已创建节点  : "+ nodeCreate);
    }

    //获取节点上的值
    @Test
    public void getNode() throws KeeperException, InterruptedException {
        byte[] getNode = zkClient.getData("/lg1",false,new Stat());
        String str = new String(getNode);
        System.out.println("lg1的节点数据是: " + str);
    }
    //修改节点上的数据
    @Test
    public void updataData() throws KeeperException, InterruptedException {
        zkClient.setData("/lg1","新值".getBytes(),0);
    }

    //删除节点
    @Test
    public void deleteData() throws KeeperException, InterruptedException {
        zkClient.delete("/lg",1);
    }

    //获取子节点
    @Test
    public void getChild() throws KeeperException, InterruptedException {
        List<String> children = zkClient.getChildren("/china", false);
        for (String child : children) {
            System.out.println(child);
        }
    }

    //监听根节点下面的变化
    @Test
    public void watchNode() throws KeeperException, InterruptedException, IOException {
        List<String> children = zkClient.getChildren("/", true);
        for (String child : children) {
            System.out.println(child);
            System.in.read();
        }
    }

    @Test
    public void exists() throws KeeperException, InterruptedException {
        Stat exists = zkClient.exists("/china", false);
        if (exists == null) {
            System.out.println("不存在");
        } else {
            System.out.println("存在");
        }
    }

}
