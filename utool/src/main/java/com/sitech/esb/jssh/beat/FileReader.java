package com.sitech.esb.jssh.beat;

import com.sitech.esb.jssh.handler.FileLineHandler;
import com.sitech.esb.jssh.shell.RemoteShellExecutor;
import com.sitech.esb.jssh.shell.ShellExecutorFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * linux主机文件读取
 * @author liwei_paas
 * @date 2021/1/11
 */
public class FileReader {

    /**
     * 对文件进行分页读取每行数据并处理,默认通过sed+分页的方式读取
     * 如果之前已经读过了，接着上次继续读取；
     * 如果之前没有读过，那么根据文件大小以及最大行 计算出应该读取行数
     *
     * @param fileLineHandler 每行数据的解析工具
     * @param fileRecord 文件记录
     * @return 根据lineNum判断文件是否还有数据并已经处理了
     * @throws IOException
     */
    public void readAndHandleLine(FileRecord fileRecord,FileLineHandler fileLineHandler) throws IOException{
        int startLineNum = fileRecord.getLastLineNum(); // 开始读取的位置，上次的位置+1
        int offset = fileRecord.getOffset(); // 每次读多少行数据
        int nlct = fileRecord.getNullLineConsecutiveTimes(); // 连续多少次没有内容
        String command = command2readFileRow(fileRecord.getFilePath(),(startLineNum+1),offset); // 默认使用sed命令来获取文件数据
        RemoteShellExecutor remoteShellExecutor = ShellExecutorFactory.getLoggedShellExecutor();
        remoteShellExecutor.doExecute(command, out -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(out));
            //先判断下有没有内容
            String currentLine = reader.readLine();
            if (currentLine == null){
                // 如果没有读取到数据
                fileRecord.setNullLineConsecutiveTimes(nlct+1);
                return;
            }
            fileRecord.setNullLineConsecutiveTimes(0); //说明有内容了

            int start = startLineNum+1;
            //使用nextLine来判断currentLine是不是最后一行了
            for (String nextLine = reader.readLine() ; currentLine!=null ; currentLine = nextLine){
                if (fileLineHandler.handLine(currentLine, start,nextLine==null)){
                    fileRecord.setLastLineNum(start); // 记录已经处理过的行号
                }
                start++;
            }
        });
    }

    /**
     * 默认使用sed命令获取文件数据
     * @param filePath
     * @param start 开始行号
     * @param offset 每次读多少行数据
     * @return
     */
    protected String command2readFileRow(String filePath,int start,int offset){
        return "sed -n "+start+","+offset+"p "+filePath;
    }

}
