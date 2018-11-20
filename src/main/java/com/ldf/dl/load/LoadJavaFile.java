package com.ldf.dl.load;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

/**
 * 动态加载java 文件
 * Created by ldf on 2018/11/19.
 */
public class LoadJavaFile {

    static final String ROOT_PATH = System.getProperty("user.dir");
    static final String JAVA_PATH = ROOT_PATH + "\\external";
    static final String CLASS_PATH = ROOT_PATH + "\\external\\class" ;
    static final String JAR_PATH = ROOT_PATH + "\\external\\jar";

    public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException, MalformedURLException, ClassNotFoundException {
        System.out.println(CLASS_PATH);
        System.out.println(File.pathSeparatorChar);
        compile();
        load();
    }

    private static void load() throws MalformedURLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        File classesDir = new File(CLASS_PATH);
        URLClassLoader loader = new URLClassLoader(new URL[]{classesDir.toURI().toURL()});
        Class<?> test1 = loader.loadClass("Test1");
        Method noResultMethod = test1.getMethod("noResultMethod", String.class);
        noResultMethod.invoke(test1.newInstance(), "test");
        Method withResultMethod = test1.getMethod("withResultMethod", int.class, int.class);
        Object o = withResultMethod.invoke(test1.newInstance(), 1, 2);
        System.out.println("test1--------------------end");
        Class<?> test2 = loader.loadClass("Test2");
        Method m1 = test2.getMethod("m1", String.class);
        m1.invoke(test2.newInstance(), "m1 test");
        Method m2 = test2.getMethod("m2", int.class, int.class);
        Object m21 = m2.invoke(test2.newInstance(), 1, 3);
        System.out.println("test2----------------------end");
    }


    /**
     * 编译文件
     * 注意：编译前把package名删掉
     * 编译选项，在编译java文件时，编译程序会自动的去寻找java文件引用的其他的java源文件或者class。
     * -sourcepath选项就是定义java源文件的查找目录，
     * -classpath选项就是定义class文件的查找目录，-d就是编译文件的输出目录
     */
    private static void compile(){
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        //获取java文件管理类
        StandardJavaFileManager manager =
                compiler.getStandardFileManager(null, null, null);
        //获取java文件对象迭代器
        test2Compile(compiler, manager);
        test3Compile(compiler, manager);
        System.out.println("添加编译Test2-------------------------------------------");
    }


    private static void test2Compile(JavaCompiler compiler, StandardJavaFileManager manager){
        System.out.println("添加编译Test1-------------------------------------------");
        Iterable<? extends JavaFileObject> it = manager.getJavaFileObjects(JAVA_PATH + "//Test2.java");
        //设置编译参数
        ArrayList<String> ops = new ArrayList<String>();
        ops.add("-Xlint:unchecked");
        //设置class文件存放地址
        ops.add("-d");
        ops.add(CLASS_PATH);
        //获取编译任务
        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, ops, null, it);
        //执行编译任务
        task.call();
    }

    private static void test3Compile(JavaCompiler compiler, StandardJavaFileManager manager){
        System.out.println("添加编译Test3-------------------------------------------");
        Iterable<? extends JavaFileObject> it = manager.getJavaFileObjects(JAVA_PATH + "//Test3.java");
        //设置编译参数
        ArrayList<String> ops = new ArrayList<String>();
        ops.add("-Xlint:unchecked");
        //设置class文件存放地址
        ops.add("-d");
        ops.add(CLASS_PATH);
        //设置class的文件的查找目录 即引用对象的地址
        //JAR_PATH + "\\lombok.jar" "D:/growup/dynamic-load/external/jar/lombok.jar"
        String lombokPath = JAR_PATH + "\\lombok.jar";
        String fastjsonPath = JAR_PATH + "\\fastjson.jar";
        ops.add("-classpath");
        ops.add(lombokPath + File.pathSeparatorChar + fastjsonPath);
        ops.add("-sourcepath");
        ops.add(lombokPath + File.pathSeparatorChar + fastjsonPath);
        //获取编译任务
        JavaCompiler.CompilationTask task = compiler.getTask(null, manager, null, ops, null, it);
        //执行编译任务
        task.call();

    }


}
