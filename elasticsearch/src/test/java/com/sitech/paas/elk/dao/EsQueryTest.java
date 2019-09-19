package com.sitech.paas.elk.dao;


import com.sitech.paas.elk.dao.impl.BaseEsQuery;
import com.sitech.paas.elk.dao.impl.EsbwsEsQuery;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.junit.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EsQueryTest {

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date begin = dateFormat.parse("2019-09-01 00:00:47");
    Date end = dateFormat.parse("2019-09-01 00:30:47");
    String index = EsQueryFactory.INDEX_ESB_SRVLOG;
    EsQueryFactory esQueryFactory = new EsQueryFactory();


    public EsQueryTest() throws ParseException {
    }

    @Test
    public void testParseInfo(){
        String srvName = "sPubUserWordBrandInfo";
        //解析
        String infoFirst = "ERROR --> 处理RESTful服务请求出错: sPubUserWordBrandInfo,CRM_TEMPLATE_31A2_TAEC128CRM2，服务调用出错：TPENOENT(6):0:0:TPED_MINVAL(0):QMNONE(0):0esbRetCode:-4, ERROR:null\tat com.sitech.esb.rs.service.TxdoJsonRestService.invoke(TxdoJsonRestService.java:1012)\tat com.sitech.esb.rs.service.TxdoJsonRestService.internalPost(TxdoJsonRestService.java:97)\tat com.sitech.esb.rs.service.RestService.doPost(RestService.java:430)\tat com.sitech.esb.rs.engine.RESTfulEngine.receive(RESTfulEngine.java:62)\tat com.sitech.esb.rs.resource.EsbResource.post(EsbResource.java:460)\tat sun.reflect.GeneratedMethodAccessor236.invoke(Unknown Source)\tat sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\tat java.lang.reflect.Method.invoke(Method.java:606)\tat org.jboss.resteasy.core.MethodInjectorImpl.invoke(MethodInjectorImpl.java:137)\tat org.jboss.resteasy.core.ResourceMethodInvoker.invokeOnTarget(ResourceMethodInvoker.java:280)\tat org.jboss.resteasy.core.ResourceMethodInvoker.invoke(ResourceMethodInvoker.java:234)\tat org.jboss.resteasy.core.ResourceMethodInvoker.invoke(ResourceMethodInvoker.java:221)\tat org.jboss.resteasy.core.SynchronousDispatcher.invoke(SynchronousDispatcher.java:356)\tat org.jboss.resteasy.core.SynchronousDispatcher.invoke(SynchronousDispatcher.java:179)\tat org.jboss.resteasy.plugins.server.servlet.ServletContainerDispatcher.service(ServletContainerDispatcher.java:220)\tat org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher.service(HttpServletDispatcher.java:56)\tat org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher.service(HttpServletDispatcher.java:51)\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:844)\tat weblogic.servlet.internal.StubSecurityHelper$ServletServiceAction.run(StubSecurityHelper.java:280)\tat weblogic.servlet.internal.StubSecurityHelper$ServletServiceAction.run(StubSecurityHelper.java:254)\tat weblogic.servlet.internal.StubSecurityHelper.invokeServlet(StubSecurityHelper.java:136)\tat weblogic.servlet.internal.ServletStubImpl.execute(ServletStubImpl.java:346)\tat weblogic.servlet.internal.TailFilter.doFilter(TailFilter.java:25)\tat weblogic.servlet.internal.FilterChainImpl.doFilter(FilterChainImpl.java:79)\tat com.emobile.esb.ESBmanager.EsbRouteHelper.doFilter(EsbRouteHelper.java:67)\tat weblogic.servlet.internal.FilterChainImpl.doFilter(FilterChainImpl.java:79)\tat weblogic.servlet.internal.WebAppServletContext$ServletInvocationAction.wrapRun(WebAppServletContext.java:3456)\tat weblogic.servlet.internal.WebAppServletContext$ServletInvocationAction.run(WebAppServletContext.java:3422)\tat weblogic.security.acl.internal.AuthenticatedSubject.doAs(AuthenticatedSubject.java:323)\tat weblogic.security.service.SecurityManager.runAs(SecurityManager.java:120)\tat weblogic.servlet.provider.WlsSubjectHandle.run(WlsSubjectHandle.java:57)\tat weblogic.servlet.internal.WebAppServletContext.doSecuredExecute(WebAppServletContext.java:2280)\tat weblogic.servlet.internal.WebAppServletContext.securedExecute(WebAppServletContext.java:2196)\tat weblogic.servlet.internal.WebAppServletContext.execute(WebAppServletContext.java:2174)\tat weblogic.servlet.internal.ServletRequestImpl.run(ServletRequestImpl.java:1632)\tat weblogic.servlet.provider.ContainerSupportProviderImpl$WlsRequestExecutor.run(ContainerSupportProviderImpl.java:256)\tat weblogic.work.ExecuteThread.execute(ExecuteThread.java:311)\tat weblogic.work.ExecuteThread.run(ExecuteThread.java:263)Caused by: TPENOENT(6):0:0:TPED_MINVAL(0):QMNONE(0):0\tat weblogic.wtc.jatmi.dsession.tpgetrply(dsession.java:3806)\tat weblogic.wtc.jatmi.dsession.tpcall(dsession.java:3972)\tat weblogic.wtc.gwt.STTuxedoConnection.tpcall(STTuxedoConnection.java:58)\tat com.emobile.esb.wtc.WtcUTypeService.getTxdResult(WtcUTypeService.java:21)\tat com.sitech.esb.rs.service.TxdoJsonRestService.invoke(TxdoJsonRestService.java:966)\t... 37 more";
        String infoStack = "ERROR --> 请求MessageContext属性值：REST_END_TIME=1567268908605TRACE_ID=01*20190901002828*00000*000000*102769HTTP_CLIENT_IP=10.113.158.85CHANNEL_ID=05HTTP_METHOD=POSTENDUSRLOGINID_IN=SSRV_ENDPOINT=sPubUserWordBrandInfo,CRM_TEMPLATE_32C2_TCEC138CRM2SSRV_EXECUTE_TIME=2ClientChnUsrInfo@emobile=com.emobile.esb.ESBmanager.WSClientChnUsrInfo@1bf8da43IN_POOL_ID=31serviceName=sPubUserWordBrandInfoFUNCAT_NAME=协同类ROUTE_KEY=10SSRV_BEGIN_TIME=1567268908602ORI_INPARAM_STR={\"ROOT\":{\"HEADER\":{\"ROUTING\":{\"ROUTE_KEY\":\"10\",\"ROUTE_VALUE\":\"18282062416\"},\"DB_ID\":\"\",\"ENDUSRLOGINID\":\"\",\"CHANNEL_ID\":\"05\",\"USERNAME\":\"mmmmmm5\",\"PASSWORD\":\"LWwySLxHcD8=\",\"ENDUSRLOGINKEY\":\"18282062416\",\"ENDUSRIP\":\"10.113.158.71\",\"CONTACT_ID\":\"022014091516075847800758348\",\"POOL_ID\":\"31\"},\"BODY\":{\"PHONE_NO\":\"18282062416\"}}}CLIENT_NAME=自助终端OUTFLOWRST_CHECEKED=trueCHARACTER_SET_ENCODING=GBKClientAddrInfo@emobile=com.emobile.esb.ESBmanager.WSClientAddrInfo@1c139a38HTTP_REQ_BYTES=[B@193033f7PASSWORD=LWwySLxHcD8=Accept=text/htmlSSRV_END_TIME=1567268908604DB_ID=C2CLIENT_ID=105FLOW_CODE=022014091516075847800758348EsbSrvException=esbRetCode:-4, ERROR:nullAppFuncatInfo@emobile=com.emobile.esb.ESBmanager.WSAppFunInfo@44c6a66bClientInfo@emobile=com.emobile.esb.ESBmanager.WSClientInfo@4dd7de13ISNEED_SAVE_INPARAMS=trueENDUSRIP=10.113.158.71endUsrId@inParams=18282062416ContentTypeMT=application/json;charset=GBKREST_EXECUTE_TIME=4http=HTTP/1.1http.servletResponseHeaders=org.jboss.resteasy.specimpl.ResteasyHttpHeaders@2ba3aa0chttp.servletRequest=com.emobile.esb.ESBmanager.ModifyServletWrapper@5f11036http.servletResponse=weblogic.servlet.internal.ServletResponseImpl@c6a54cb[HTTP/1.1 200 OK]ROUTE_VALUE=18282062416USERNAME=mmmmmm5ENDUSRLOGINKEY=18282062416POOL_ID=32C2ContentType=application/json;charset=\"GBK\"REST_START_TIME=1567268908601appKey=null请求REST报文：{\"ROOT\":{\"HEADER\":{\"ROUTING\":{\"ROUTE_KEY\":\"10\",\"ROUTE_VALUE\":\"18282062416\"},\"DB_ID\":\"\",\"ENDUSRLOGINID\":\"\",\"CHANNEL_ID\":\"05\",\"USERNAME\":\"mmmmmm5\",\"PASSWORD\":\"LWwySLxHcD8=\",\"ENDUSRLOGINKEY\":\"18282062416\",\"ENDUSRIP\":\"10.113.158.71\",\"CONTACT_ID\":\"022014091516075847800758348\",\"POOL_ID\":\"31\"},\"BODY\":{\"PHONE_NO\":\"18282062416\"}}}返回REST报文：REST响应时间为: 2019-09-01 00:28:28:605, REST响应报文为：{\"ROOT\": {\"ESBRETCODE\":\"-4\",\"RETURN_CODE\":\"-040107\",\"RETURN_MSG\":\"sPubUserWordBrandInfo,CRM_TEMPLATE_32C2_TCEC138CRM2，服务调用出错：TPENOENT(6):0:0:TPED_MINVAL(0):QMNONE(0):0\",\"DETAIL_MSG\":\"sPubUserWordBrandInfo,CRM_TEMPLATE_32C2_TCEC138CRM2，服务调用出错：TPENOENT(6):0:0:TPED_MINVAL(0):QMNONE(0):0\"}}服务对象参数值：\tsrvId=170135\tsrvType=13\talertTime=100\taverageTime=60\ttimeDuration=10\trecDuration=3\tsrvName=sPubUserWordBrandInfo\tsreqXmlEncoding=null\tretXmlEncoding=null源服务访问点为：sPubUserWordBrandInfo,CRM_TEMPLATE_32C2_TCEC138CRM2";

        infoFirst = infoFirst.substring(0, infoFirst.indexOf("\t"));
        infoFirst = infoFirst.substring(infoFirst.indexOf(srvName));

        System.out.println(infoFirst);


        infoStack = infoStack.substring(infoStack.indexOf("DETAIL_MSG"));
        infoStack = infoStack.substring(infoStack.indexOf(srvName));
        infoStack = infoStack.substring(0,infoStack.indexOf("\"}}"));
        System.out.println(infoStack);
    }

    @Test
    public void test02(){
        EsbwsEsQuery esbwsEsQuery = esQueryFactory.buildEsbwsQuery(11, begin, end);
        ArrayList<String> list = new ArrayList<>();
        String srvName = "sPubUserWordBrandInfo";
        list.add(srvName);
        list.add("040107");
        esbwsEsQuery.addFromAndSize(5);
        SearchHits hits = esbwsEsQuery.queryInfoByRegexp(list);


        for (SearchHit hit: hits){
            Map<String, Object> source = hit.getSourceAsMap();
            String info = (String) source.get("info");
            System.out.println(EsbwsEsQuery.parseInfo2Summary(info,srvName));
        }
        System.out.println(hits.getHits().length);
        System.out.println(hits.getTotalHits());
    }

    @Test
    public void test01() throws ParseException {
        BaseEsQuery query = esQueryFactory.build(11, index,begin,end);
        Map<String, String> map = new HashMap<>();
        map.put("srvName", "sPFeeQuery");
        map.put("dbId","A2");
        SearchHits searchHits = query.queryTerms(map);
        System.out.println(searchHits.getTotalHits());
    }
}
