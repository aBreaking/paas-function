package com.sitech.paas.javagen.compile;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

/**
 * @{USER}
 * @{DATE}
 */
public class CompileTest {
    public static void main(String[] args) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null,null,null,null);
    }
}
