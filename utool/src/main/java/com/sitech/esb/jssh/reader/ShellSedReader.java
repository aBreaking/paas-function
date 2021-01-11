package com.sitech.esb.jssh.reader;


import com.sitech.esb.jssh.shell.RemoteShellExecutor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 默认使用sed的方式读取
 * @author liwei_paas
 * @date 2021/1/11
 */
public class ShellSedReader extends RemoteFileReader{
    private static final String sed = "sed -n ";

    public ShellSedReader(RemoteShellExecutor remoteShellExecutor, FileRowParser fileRowParser) {
        super(remoteShellExecutor, fileRowParser);
    }

    @Override
    public void readRow(String filepath, int start, int offset) throws IOException {
        String command = sed+start+","+offset+"p "+filepath;
        remoteShellExecutor.doExecute(command, out -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(out));
            String s;
            while ((s = reader.readLine())!=null){
                fileRowParser.parse(s);
            }

        });
    }
}
