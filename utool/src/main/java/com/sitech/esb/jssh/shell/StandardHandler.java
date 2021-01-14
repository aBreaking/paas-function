package com.sitech.esb.jssh.shell;

import java.io.IOException;
import java.io.InputStream;

/**
 * 对获取到shell的输出流处理
 * @author liwei_paas
 * @date 2021/1/11
 */
public interface StandardHandler {

    void hand(InputStream out) throws IOException;
}
