package com.sitech.paas.elk.input;


import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.elk.util.FastJsonUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class FileEsInput implements EsInput {

    FileInputStream fileInputStream;

    public FileEsInput(String file) throws FileNotFoundException {
        this.fileInputStream = new FileInputStream(file);
    }

    public FileEsInput(FileInputStream fileInputStream){
        this.fileInputStream = fileInputStream;
    }

    public String pin() {
        JSONObject jsonObject = FastJsonUtil.toFastjson(fileInputStream);
        return jsonObject.toJSONString();
    }
}
