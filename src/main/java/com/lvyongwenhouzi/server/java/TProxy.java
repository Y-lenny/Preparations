package com.lvyongwenhouzi.server.java;

import com.lvyongwenhouzi.util.ProxyUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * JDK动态代理是怎么实现 ？
 * 1. 定义一个接口
 * 2. 实现这个接口方法
 * 3. 定义一个动态代理类（实现InvocationHandler.invoke方法）
 * 4. 创建代理对象（Proxy.newProxyInstance）
 * 5. 调用代理对象方法
 * <p>
 * ------- 原理 ---------
 * 通过InvocationHandler这个切入点（自定上下文内容）把代理类关联到目标对象（TProxy -> Handler -> Obj）;
 * <p>
 * 参考链接：https://zhangjun075.github.io/learning/javassit/
 */
public class TProxy {

    public static void main(String[] args) {

        Obj obj = new ObjImpl();
        Obj proxyInstance = (Obj) java.lang.reflect.Proxy.newProxyInstance(
                obj.getClass().getClassLoader(),
                obj.getClass().getInterfaces()
                , new InvokeObj(obj));

        proxyInstance.print();

        // ./proxyObjClazz.class
        // ProxyUtils.generateClassFile(obj.getClass(),"proxyObjClazz");
    }


    public static class InvokeObj implements InvocationHandler {

        private Object obj;

        public InvokeObj(Object obj) {
            this.obj = obj;
        }

        /**
         * 反射调用
         *
         * 通过反射的方式把方法和对象进行分离，把方法、对象作为参数进行调用处理；提供了前、后、返回、异常等等钩子入口。
         * 拓展至：Spring AOP {@link com.lvyongwenhouzi.architecture.spring.aop.LogAOP}
         *
         * @param proxy  代理对象
         * @param method 代理方法
         * @param args   代理参数
         * @return
         * @throws Throwable
         */
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) {

            System.out.println("动态代理开始 starting........."); // AOP 前置通知{@link org.aspectj.lang.annotation.Before}
            Object invoke = null;
            try {
                invoke = method.invoke(obj, args);
            } catch (Exception e) {
                System.out.println("动态代理异常 exception........"); // AOP 异常通知{@link org.aspectj.lang.annotation.AfterThrowing}
            }
            System.out.println("动态代理结束 ended........"); // AOP 后置通知{@link org.aspectj.lang.annotation.After}

            return invoke; // System.out.println("动态代理响应 return........"); // AOP 响应通知{@link org.aspectj.lang.annotation.AfterReturning}
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
