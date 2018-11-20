package com.ldf.dl.common;

import java.io.*;

/**
 * Created by ldf on 2018/11/19.
 */
public class MyClassLoad extends ClassLoader {
    private String rootPath;

    public MyClassLoad(String rootPath) {
        this.rootPath = rootPath;
    }
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class<?> c = findLoadedClass(name);
        // 内存堆中还没加载该类
        if (c == null) {
            // 自己实现加载类
            c = findMyClass(name);
        }
        return c;
    }

    private Class<?> findMyClass(String name) {
        try {
            byte[] bytes = getData(name);
            // 调用父类方法，生成具体类
            return this.defineClass(null, bytes, 0, bytes.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private byte[] getData(String className) {
        String path = rootPath + File.separatorChar + className.replace('.', File.separatorChar) + ".class";
        InputStream is = null;
        try {
            is = new FileInputStream(path);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[2048];
            int num = 0;
            while ((num = is.read(buffer)) != -1) {
                stream.write(buffer, 0, num);
            }
            return stream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }


}
