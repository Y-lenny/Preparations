package com.lvyongwenhouzi.server.java.jvm;

import java.io.*;
import java.lang.reflect.InvocationTargetException;

/**
 * <b>双亲委派是如何实现的，以及如何自定义实现类加载器 ？</b>
 * <p>
 * JVM 内置加载器：
 * 1、BootstrapClassLoader(启动类加载器)：最顶层的加载类，由C++实现，负责加载 %JAVA_HOME%/lib目录下的jar包和类或者或被
 * -Xbootclasspath参数指定的路径中的所有类。
 * 2、ExtensionClassLoader(扩展类加载器)：主要负责加载目录 %JRE_HOME%/lib/ext 目录下的jar包和类，或被 java.ext.dirs 系统变量所指定的路径下的jar包。
 * 3、AppClassLoader(应用程序加载器)：面向我们用户的加载器，负责加载当前应用classpath下的所有jar包和类。
 * <p>
 * Note: AppClassLoader 没有按照双亲委派模型进行定义，主要是考虑到在JVM虚拟机上可能运行多个应用程序；比如：插件系统、tomcat部署war包
 * <p>
 * 双亲委派模型：
 * 每一个类都有一个对应它的类加载器。系统中的 ClassLoader 在协同工作的时候会默认使用 双亲委派模型 。
 * 即在类加载的时候，系统会首先判断当前类是否被加载过。已经被加载的类会直接返回，否则才会尝试加载。
 * 加载的时候，首先会把该请求委派给父类加载器的 loadClass() 处理，因此所有的请求最终都应该传送到顶层的启动类加载器 BootstrapClassLoader 中。
 * 当父类加载器无法处理时，才由自己来处理。当父类加载器为 null 时，会使用启动类加载器 BootstrapClassLoader 作为父类加载器。
 * 遵从：
 * 1、自顶向下尝试着加载类
 * 2、自底向上检查类是否被加载
 * <p>
 * 自定义加载器实现方式：
 * 通过继承 #{@link ClassLoader}类并重写#{@link ClassLoader#findClass(String)}(不破坏委派模型,父类找不到再调用重写方法)/#{@link ClassLoader#loadClass(String)}(破坏双亲委派模型)
 *
 * @author 11114396 lvyongwen
 * @date 2021-08-23 12:29
 * @since 1.0
 */
public class TCustomClassLoader extends ClassLoader {

    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        TCustomClassLoader tCustomClassLoader = new TCustomClassLoader();
        Class<?> loadClass = tCustomClassLoader.loadClass("HelloWorld");
        loadClass.getMethod("hello").invoke(null, null);
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        Class clazz = null;
        // 查找并读取文件
        File classFile = new File("/Users/11114396/IdeaProjects/Preparations/reader/" + name + ".class");
        if (classFile.exists()) {
            try (BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(classFile))) {
                byte[] array = new byte[1024];
                // 读取长度要和下面defineClass对应起来
                int read = bufferedInputStream.read(array);
                clazz = defineClass(name, array, 0, read);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (clazz == null) {
            throw new ClassNotFoundException(name);
        }
        return clazz;
    }
}
