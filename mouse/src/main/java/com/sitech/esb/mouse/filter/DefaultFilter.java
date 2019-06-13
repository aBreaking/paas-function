package com.sitech.esb.mouse.filter;

/**
 * 可能某些并不需要检查，
 * 定义一个默认的Filter，就无需检查
 */
public class DefaultFilter implements Filter {
    public static final String FILTER_NAME="default";
    @Override
    public boolean passCheck(String statement) {
        return true;
    }
}
