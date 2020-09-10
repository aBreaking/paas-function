package com.sitech.esb.sap.autoservice;

import jxl.Workbook;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author liwei_paas
 * @date 2020/4/2
 */
@WebServlet(urlPatterns = "/sap")
public class SapServlet extends HttpServlet {
    /**
     * get 请求用于进行同步结果的查询
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws  IOException {
        PrintWriter writer = resp.getWriter();
        String invokeKey = req.getParameter("invokeKey");
        if (invokeKey==null){
            invokeKey = req.getParameter("q");
        }
        if (invokeKey==null){
            writer.write("no request key specified,which named invokeKey");
        }

        String value = SapInvoker.INVOKE_STATE.get(invokeKey);
        writer.write(value);
    }

    /**
     * post请求用于服务的同步
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        //上传的文件流
        PrintWriter writer = resp.getWriter();
        try {

            ServletInputStream inputStream = req.getInputStream();
            EsbPoolConfig esbPoolConfig = new EsbPoolConfig();
            esbPoolConfig.setPoolKey( req.getParameter("poolKey"));
            esbPoolConfig.setHbXml( req.getParameter("hbXml"));
            esbPoolConfig.setPoolAddress( req.getParameter("poolAddress"));

            SapInvoker invoker = new SapInvoker();
            invoker.setWorkbook(Workbook.getWorkbook(inputStream));
            invoker.setConfig(esbPoolConfig);

            String s = invoker.asyncInvoke();

            writer.write(s);
        } catch (Exception e) {
        	e.printStackTrace();
            writer.write(e.getMessage());
        }finally {
            writer.close();
        }

    }
    

}
