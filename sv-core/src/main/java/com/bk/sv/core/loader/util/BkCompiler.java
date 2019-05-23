package com.bk.sv.core.loader.util;

import com.google.common.collect.Lists;
import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author BK
 * @version V2.0
 * @description: 编译工具
 *  可以使用：GroovyClassLoader groovyClassLoader = new GroovyClassLoader();
 *          Class<?> clazz = groovyClassLoader.parseClass(codeSource);
 * @team: bk
 * @date 2019/5/5 11:09
 */
public class BkCompiler {
    private JavaCompiler compiler;
    private StandardJavaFileManager fileManager;

    public BkCompiler() {
        compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            throw new RuntimeException("Could not get Java compiler. Please, ensure that JDK is used instead of JRE.");
        }
        fileManager = compiler.getStandardFileManager(null, null, null);
    }

    public static void main(String[] args) throws Exception {
        BkCompiler compiler = new BkCompiler();
        String source = "public class Main {" + "public static void main(String[] args) {" + "System.out.println(\"Hello World!\");" + "} " + "}";
        Class clazz = compiler.compile("Main", source, new PrintWriter(System.err), null, null);
        assert clazz != null;
        Object object = clazz.newInstance();
        MethodUtils.invokeMethod(object, "main", new Object[]{new String[]{}});
    }

    public static void invoke() throws IOException {
        Runtime run = Runtime.getRuntime();
        Process process = run.exec("java -cp  d:/myjava    HelloWorld");
    }

    public static void runJavaClassByReflect(String dir, String classFile) {
        try {
            URL[] urls = new URL[]{new URL("file:/" + dir)};
            Class c;
            try (URLClassLoader loader = new URLClassLoader(urls)) {
                //调用加载类的main方法
                c = loader.loadClass(classFile);
            }
            c.getMethod("main", String[].class).invoke(null, (Object) new String[]{});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 装载字符串成为java可执行文件
     *
     * @param className className
     * @param javaCodes javaCodes
     * @return Class
     */
    public Class<?> compile(String className, String javaCodes, Writer err, String sourcePath, String classPath) {
        StringSourceJavaObject srcObject = new StringSourceJavaObject(className, javaCodes);
        Iterable<? extends JavaFileObject> fileObjects = Collections.singletonList(srcObject);
        String flag = "-d";
        String outDir = "";
        try {
            File classPath1 = new File(Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).toURI());
            outDir = classPath1.getAbsolutePath() + File.separator;
        } catch (URISyntaxException e1) {
            e1.printStackTrace();
        }
        List<String> options = Lists.newArrayList(flag, outDir);
        options.add("-Xlint:all");
        options.add("-g:none");
        options.add("-deprecation");
        if (StringUtils.isNotBlank(sourcePath)) {
            options.add("-sourcepath");
            options.add(sourcePath);
        }
        if (StringUtils.isNotBlank(classPath)) {
            options.add("-classpath");
            options.add(classPath);
        }
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<>();
        JavaCompiler.CompilationTask task = compiler.getTask(err, fileManager, diagnostics, options, null, fileObjects);
        boolean result = task.call();
        if (result) {
            try {
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(err!=null){
            PrintWriter pw = new PrintWriter(err);
            for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
                pw.println(diagnostic);
            }
            pw.flush();
        }
        return null;
    }

    private class StringSourceJavaObject extends SimpleJavaFileObject {
        private String content;

        StringSourceJavaObject(String name, String content) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension), Kind.SOURCE);
            this.content = content;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return content;
        }
    }

}