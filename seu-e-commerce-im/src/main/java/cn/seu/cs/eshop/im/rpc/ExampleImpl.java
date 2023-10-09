package cn.seu.cs.eshop.im.rpc;

import cn.seu.cs.im.sdk.ExampleInterface;

/**
 * @author Shuxin Wang <shuxinwang662@gmail.com>
 * Created on 2023/10/9
 */
public class ExampleImpl implements ExampleInterface {
    @Override
    public void sayHello() {
        System.out.println("Hello World");
    }
}
