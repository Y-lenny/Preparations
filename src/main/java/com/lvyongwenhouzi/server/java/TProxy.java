package com.lvyongwenhouzi.server.java;

import com.lvyongwenhouzi.util.ProxyUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * JDK动态代理是怎么实现 ？
 * 1. 定义一个接口
 * 2. 实现这个接口方法
 * 3. 定义一个动态代理类（实现InvocationHandler.invoke方法）
 * 4. 创建代理对象（Proxy.newProxyInstance）
 * 5. 调用代理对象方法
 *
 * ------- 原理 ---------
 * 通过InvocationHandler这个切入点（自定上下文内容）把代理类关联到目标对象（TProxy -> Handler -> Obj）;
 *
 * 参考链接：https://zhangjun075.github.io/learning/javassit/
 *
 */
public class TProxy {

    public static void main(String[] args) {

        Obj obj = new ObjImpl();
        Obj proxyInstance = (Obj) java.lang.reflect.Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces()
                , new InvokeObj(obj));

        proxyInstance.print();

        // reader/proxyObjClazz.class
        // ProxyUtils.generateClassFile(obj.getClass(),"proxyObjClazz");
    }


    public static class InvokeObj implements InvocationHandler {

        private Object obj;

        public InvokeObj(Object obj) {
            this.obj = obj;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

            System.out.println("动态代理开始 starting.........");
            method.invoke(obj, args);
            System.out.println("动态代理结束 ended........");

            return null;
        }
    }

    public interface Obj {

        void print();
    }

    public static class ObjImpl implements Obj {

        @Override
        public void print() {
            System.out.println("Obj print ...........");
        }
    }


}
