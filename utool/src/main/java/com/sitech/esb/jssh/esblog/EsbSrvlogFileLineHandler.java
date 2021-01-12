package com.sitech.esb.jssh.esblog;

import com.sitech.esb.jssh.handler.FileLineHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * esb日志文件的数据解析器
 * @author liwei_paas
 * @date 2021/1/12
 */
public class EsbSrvlogFileLineHandler implements FileLineHandler {

    List<String> lineList = new ArrayList<>();

    @Override
    public boolean handLine(String line, int lineNum, boolean isLastLine) {
        return false;
    }
}
