package com.sitech.paas.elk.ret;

import java.util.List;

/**
 * 结果数据封装
 * @author liwei_paas
 * @date 2019/9/3
 */
public interface Results {

    /**
     * 结果。目前是以数组形式得封装的
     * @return
     */
    List<String[]> getResult();

    /**
     * 结果宽度，就是上面String[]的大小
     * @return
     */
    int width();
}
