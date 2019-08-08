package com.sitech.esb.jsoneye;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sitech.esb.jsoneye.resv.MyExcelResolver;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @{USER}
 * @{DATE}
 */
public class MyComparator {

    /**
     * 输入的excel文档
     */
    Workbook workbook;

    public static String FIRST_JSON_NODE_NAME = "UNI_BSS_BODY";

    static String jsonstr = "{    \"UNI_BSS_HEAD\": {        \"RESP_DESC\": \"success\",        \"TRANS_ID\": \"20190806101320097172372\",        \"TIMESTAMP\": \"2019-08-06 10:13:20 097\",        \"RESP_CODE\": \"00000\",        \"APP_ID\": \"51-COMP\"    },    \"UNI_BSS_ATTACHED\": {        \"MEDIA_INFO\": \"\"    },    \"UNI_BSS_BODY\": {        \"CHECK_USER_INFO_RSP\": {            \"USER_INFO\": {                \"X_SVCSTATE_EXPLAIN\": \"开通\",                \"USER_TYPE_CODE\": \"0\",                \"X_EPARCHY_NAME\": \"广州\",                \"PRODUCT_ID\": \"99999830\",                \"REMARK\": \"\",                \"DEVELOP_EPARCHY_CODE\": \"\",                \"IN_DEPART_ID\": \"5103029\",                \"PRODUCT_TYPE_CODE\": \"4G000001\",                \"USECUST_ID\": \"5114111112546880\",                \"DEVELOP_DEPART_ID\": \"51b0nut\",                \"VPN_NAME\": \"\",                \"USER_STATE_CODESET\": \"0\",                \"IN_STAFF_ID\": \"NULL\",                \"ACTIVITY_INFO\": {                    \"ACTIVITY_ACTIVE_TIME\": \"20150201000000\",                    \"ACTIVITY_INACTIVE_TIME\": \"20501230000000\",                    \"ACTIVITY_ID\": \"4G000001\",                    \"ACTIVITY_NAME\": \"4G基本产品\"                },                \"CHANGEUSER_DATE\": \"\",                \"ARREARAGE_FEE_INFO\": {                    \"FEE\": \"10295\",                    \"LAST_BALANCE\": \"25904\",                    \"DEPOSIT_MONEY\": \"-14414\",                    \"BALANCE_BEFORE_LAST\": \"\"                },                \"SERVICE_CLASS_CODE\": \"0050\",                \"EPARCHY_CODE\": \"0020\",                \"CREDIT_VALUE\": \"29000\",                \"ASSURE_CUST_ID\": \"\",                \"REMOVE_REASON_CODE\": \"\",                \"DEVELOP_CITY_CODE\": \"\",                \"CUST_ID\": \"5114111112546880\",                \"USECUST_NAME\": \"张三\",                \"USER_STATE\": \"00\",                \"USER_PASSWD\": \"38Z87a\",                \"OPEN_MODE\": \"0\",                \"CREDIT_CLASS\": \"66\",                \"OPEN_DEPART_ID\": \"5103029\",                \"X_REMOVE_TAG_NAME\": \"正常\",                \"CITY_CODE\": \"51a0nn\",                \"LAST_STOP_TIME\": \"20150426053618\",                \"CITY_NAME\": \"移动部\",                \"SUBSCRB_TYPE\": \"1\",                \"USECUST_PSPT_ID\": \"5114111112546880\",                \"X_PREPAY_TAG_NAME\": \"后付费\",                \"PROVINCE_CODE\": \"51\",                \"OPEN_DATE\": \"20101216160231\",                \"PRE_DESTROY_TIME\": \"\",                \"IN_NET_MODE\": \"\",                \"PRODUCT_NAME\": \"4G全国套餐-76元套餐\",                \"DISCNT_INFO\": [                    {                        \"SPEC_TAG\": \"0\",                        \"RELATION_TYPE_CODE\": \"\",                        \"END_DATE\": \"20500101235959\",                        \"DISCNT_CODE\": \"5601000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846887\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999792\",                        \"START_DATE\": \"20150201000000\"                    },                    {                        \"SPEC_TAG\": \"0\",                        \"RELATION_TYPE_CODE\": \"\",                        \"END_DATE\": \"20500101235959\",                        \"DISCNT_CODE\": \"5631000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846888\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999792\",                        \"START_DATE\": \"20150201000000\"                    },                    {                        \"SPEC_TAG\": \"0\",                        \"RELATION_TYPE_CODE\": \"\",                        \"END_DATE\": \"20501230235959\",                        \"DISCNT_CODE\": \"5990170\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846892\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999768\",                        \"START_DATE\": \"20150201000000\"                    },                    {                        \"SPEC_TAG\": \"0\",                        \"RELATION_TYPE_CODE\": \"\",                        \"END_DATE\": \"20501230235959\",                        \"DISCNT_CODE\": \"5690000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846890\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999767\",                        \"START_DATE\": \"20150201000000\"                    },                    {                        \"SPEC_TAG\": \"0\",                        \"RELATION_TYPE_CODE\": \"\",                        \"END_DATE\": \"20501230235959\",                        \"DISCNT_CODE\": \"5702000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846891\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999764\",                        \"START_DATE\": \"20150201000000\",                        \"ATTR_INFO\": {                            \"END_DATE\": \"20500101000000\",                            \"ATTR_CODE\": \"netDiscntStartTime\",                            \"ATTR_VALUE\": \"20141201000000\",                            \"START_DATE\": \"20150201000001\"                        }                    },                    {                        \"SPEC_TAG\": \"0\",                        \"RELATION_TYPE_CODE\": \"\",                        \"END_DATE\": \"20500101235959\",                        \"DISCNT_CODE\": \"5661000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846889\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999792\",                        \"START_DATE\": \"20150201000000\"                    }                ],                \"DEVELOP_STAFF_ID\": \"NULL\",                \"MPUTE_MONTH_FEE\": \"1\",                \"ACCT_TAG\": \"0\",                \"ROAM_STAT\": \"\",                \"DESTROY_TIME\": \"\",                \"USER_DIFF_CODE\": \"00\",                \"BASIC_CREDIT_VALUE\": \"19000\",                \"USECUST_PSPT_TYPE_CODE\": \"2\",                \"CREDIT_RULE_ID\": \"-1\",                \"X_ACCT_TAG_NAME\": \"正常处理\",                \"USER_ID_liwei\": \"5114111167421191\",                \"IN_DATE\": \"20101216160231\",                \"FIRST_CALL_TIME\": \"\",                \"USER_SCORE_AMOUNT\": \"1996\",                \"ATTR_INFO\": [                    {                        \"END_DATE\": \"20501231000000\",                        \"ATTR_CODE\": \"DEVINVOICE_FEE\",                        \"ATTR_VALUE\": \"0\",                        \"START_DATE\": \"20150106175216\"                    },                    {                        \"END_DATE\": \"20501231235959\",                        \"ATTR_CODE\": \"IS_TRANSMIT\",                        \"ATTR_VALUE\": \"1\",                        \"START_DATE\": \"20141201000001\"                    },                    {                        \"END_DATE\": \"20501231235959\",                        \"ATTR_CODE\": \"LINK_NAME\",                        \"ATTR_VALUE\": \"韩枝华\",                        \"START_DATE\": \"20150201000001\"                    },                    {                        \"END_DATE\": \"20501231235959\",                        \"ATTR_CODE\": \"LINK_PHONE\",                        \"ATTR_VALUE\": \"18665067225\",                        \"START_DATE\": \"20150201000001\"                    },                    {                        \"END_DATE\": \"20501231235959\",                        \"ATTR_CODE\": \"OLD_NET_TYPE_CODE_SF\",                        \"ATTR_VALUE\": \"0010\",                        \"START_DATE\": \"20141201000001\"                    },                    {                        \"END_DATE\": \"20501230000000\",                        \"ATTR_CODE\": \"X_OPTION\",                        \"ATTR_VALUE\": \"116671\",                        \"START_DATE\": \"20141209230115\"                    },                    {                        \"END_DATE\": \"20501231235959\",                        \"ATTR_CODE\": \"OLD_CUST_ID_SF\",                        \"ATTR_VALUE\": \"2009051905079630\",                        \"START_DATE\": \"20141201000001\"                    },                    {                        \"END_DATE\": \"20501230000000\",                        \"ATTR_CODE\": \"INIT_PASSWD\",                        \"ATTR_VALUE\": \"1\",                        \"START_DATE\": \"20141209230116\"                    },                    {                        \"END_DATE\": \"20501231235959\",                        \"ATTR_CODE\": \"OLD_COMBO_NAME\",                        \"ATTR_VALUE\": \"WCDMA(3G)-66元基本套餐B\",                        \"START_DATE\": \"20141201000001\"                    },                    {                        \"END_DATE\": \"20501231235959\",                        \"ATTR_CODE\": \"OLD_USER_ID_SF\",                        \"ATTR_VALUE\": \"2010121621132293\",                        \"START_DATE\": \"20141201000001\"                    },                    {                        \"END_DATE\": \"20501230000000\",                        \"ATTR_CODE\": \"USER_PASSWD\",                        \"ATTR_VALUE\": \"123457\",                        \"START_DATE\": \"20141209230115\"                    },                    {                        \"END_DATE\": \"20501231235959\",                        \"ATTR_CODE\": \"OLD_COMBO_FEE\",                        \"ATTR_VALUE\": \"6600\",                        \"START_DATE\": \"20141201000001\"                    },                    {                        \"END_DATE\": \"20501231235959\",                        \"ATTR_CODE\": \"OLD_ACCT_ID_SF\",                        \"ATTR_VALUE\": \"2010121616850428\",                        \"START_DATE\": \"20141201000001\"                    }                ],                \"REMOVE_TAG\": \"0\",                \"MPUTE_DATE\": \"20150426071551\",                \"NEXT_PRODUCT_NAME\": \"4G全国套餐-76元套餐\",                \"OPEN_STAFF_ID\": \"ITF0DZQD\",                \"CONTRACT_ID\": \"\",                \"DEVELOP_DATE\": \"\",                \"REMOVE_EPARCHY_CODE\": \"\",                \"BRAND_CODE\": \"4G00\",                \"SERIAL_NUMBER\": \"18665067225\",                \"LAND_LEVLE\": \"\",                \"X_OPEN_MODE_NAME\": \"正常\",                \"DEVELOP_NO\": \"\",                \"BRAND\": \"沃4G\",                \"ASSURE_TYPE_CODE\": \"\",                \"REMOVE_CITY_CODE\": \"\",                \"NEXT_PRODUCT_ID\": \"99999830\",                \"REMOVE_DEPART_ID\": \"\",                \"PRODUCT_INFO\": {                    \"PRODUCT_MODE\": \"00\",                    \"PRODUCT_ID\": \"99999830\",                    \"PRODUCT_INACTIVE_TIME\": \"20501230000000\",                    \"PRODUCT_NAME\": \"4G全国套餐-76元套餐\",                    \"PRODUCT_ACTIVE_TIME\": \"20150201000000\"                },                \"SCORE_VALUE\": \"1996\",                \"SVC_INFO\": [                    {                        \"SERVICE_ID\": \"50000\",                        \"END_DATE\": \"20501230000000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846893\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999793\",                        \"START_DATE\": \"20150201000000\",                        \"MAIN_TAG\": \"1\"                    },                    {                        \"SERVICE_ID\": \"50003\",                        \"END_DATE\": \"20501230000000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846895\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999793\",                        \"START_DATE\": \"20150201000000\",                        \"MAIN_TAG\": \"0\"                    },                    {                        \"SERVICE_ID\": \"50004\",                        \"END_DATE\": \"20501230000000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846896\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999768\",                        \"START_DATE\": \"20150201000000\",                        \"MAIN_TAG\": \"0\"                    },                    {                        \"SERVICE_ID\": \"50006\",                        \"END_DATE\": \"20501230000000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846897\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999793\",                        \"START_DATE\": \"20150201000000\",                        \"MAIN_TAG\": \"0\"                    },                    {                        \"SERVICE_ID\": \"50007\",                        \"END_DATE\": \"20501230000000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846898\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999793\",                        \"START_DATE\": \"20150201000000\",                        \"MAIN_TAG\": \"0\"                    },                    {                        \"SERVICE_ID\": \"50010\",                        \"END_DATE\": \"20501230000000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846899\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999793\",                        \"START_DATE\": \"20150201000000\",                        \"MAIN_TAG\": \"0\"                    },                    {                        \"SERVICE_ID\": \"50014\",                        \"END_DATE\": \"20501230000000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846900\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999793\",                        \"START_DATE\": \"20150201000000\",                        \"MAIN_TAG\": \"0\"                    },                    {                        \"SERVICE_ID\": \"50019\",                        \"END_DATE\": \"20501230000000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846901\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999793\",                        \"START_DATE\": \"20150201000000\",                        \"MAIN_TAG\": \"0\"                    },                    {                        \"SERVICE_ID\": \"50020\",                        \"END_DATE\": \"20501230000000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846902\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999793\",                        \"START_DATE\": \"20150201000000\",                        \"MAIN_TAG\": \"0\"                    },                    {                        \"SERVICE_ID\": \"50021\",                        \"END_DATE\": \"20501230000000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846903\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999793\",                        \"START_DATE\": \"20150201000000\",                        \"MAIN_TAG\": \"0\"                    },                    {                        \"SERVICE_ID\": \"50022\",                        \"END_DATE\": \"20501230000000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846904\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999793\",                        \"START_DATE\": \"20150201000000\",                        \"MAIN_TAG\": \"0\"                    },                    {                        \"SERVICE_ID\": \"50100\",                        \"END_DATE\": \"20501230000000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846905\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999793\",                        \"START_DATE\": \"20150201000000\",                        \"MAIN_TAG\": \"0\"                    },                    {                        \"SERVICE_ID\": \"50103\",                        \"END_DATE\": \"20501230000000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846906\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999793\",                        \"START_DATE\": \"20150201000000\",                        \"MAIN_TAG\": \"0\"                    },                    {                        \"SERVICE_ID\": \"50300\",                        \"END_DATE\": \"20501230000000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846907\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999793\",                        \"START_DATE\": \"20150201000000\",                        \"MAIN_TAG\": \"0\",                        \"ATTR_INFO\": {                            \"END_DATE\": \"20501230000000\",                            \"ATTR_CODE\": \"remindMethod\",                            \"ATTR_VALUE\": \"1\",                            \"START_DATE\": \"20150201000001\"                        }                    },                    {                        \"SERVICE_ID\": \"50001\",                        \"END_DATE\": \"20501230000000\",                        \"PRODUCT_ID\": \"99999830\",                        \"ITEM_ID\": \"5115010690846894\",                        \"USER_ID_A\": \"-1\",                        \"PACKAGE_ID\": \"59999793\",                        \"START_DATE\": \"20150201000000\",                        \"MAIN_TAG\": \"0\"                    }                ],                \"PREPAY_TAG\": \"1\"            },            \"CUST_INFO\": {                \"CERT_END_DATE\": \"20170123000000\",                \"BIRTHDAY\": \"19821010000000\",                \"PHONE\": \"13012355687\",                \"SEX\": \"M\",                \"CERT_ADDR\": \"联通CBSS测试地址测试专用\",                \"REMARK\": \"\",                \"PROVINCE_CODE\": \"97\",                \"FOLK_CODE\": \"01\",                \"CUST_CLASS_TYPE\": \"\",                \"NATIONALITY_CODE\": \"\",                \"DEVELOP_STAFF_ID\": \"\",                \"POST_PERSON\": \"\",                \"BIRTHDAY_FLAG\": \"\",                \"LOCAL_NATIVE_CODE\": \"\",                \"CUSTOMER_LOC\": \"\",                \"CONTACT\": \"韩枝华\",                \"DEVELOP_DEPART_ID\": \"51b0nut\",                \"HOME_ADDRESS\": \"\",                \"CUST_NAME\": \"张三\",                \"EDUCATE_DEGREE_CODE\": \"\",                \"CERT_CODE\": \"5114111112546880\",                \"IN_STAFF_ID\": \"\",                \"POST_ADDRESS\": \"联通CBSS测试地址测试专用\",                \"CUSTOMER_LEVEL\": \"\",                \"IN_DATE\": \"20141111171331\",                \"CUST_STATE\": \"0\",                \"FAX_NBR\": \"\",                \"EPARCHY_CODE\": \"0020\",                \"AUDIT_NUM\": \"-1\",                \"CERT_TYPE\": \"护照\",                \"CONTACT_PHONE\": \"13012355687\",                \"JOB\": \"\",                \"MARRIAGE\": \"\",                \"CUST_ID\": \"5114111112546880\",                \"REVENUE_LEVEL_CODE\": \"\",                \"CUST_TYPE\": \"0\",                \"WORK_ADDRESS\": \"\",                \"CREDIT_CLASS\": \"66\",                \"CERT_TYPE_CODE\": \"2\",                \"EMAIL\": \"\",                \"WORK_PHONE\": \"\",                \"JOB_TYPE_CODE\": \"\",                \"HOME_PHONE\": \"\",                \"CONTACT_TYPE\": \"\",                \"WORK_NAME\": \"\",                \"CHARACTER_TYPE_CODE\": \"\",                \"BIRTHDAY_LUNAR\": \"\"            },            \"RESP_DESC\": \"TradeOk\",            \"RESP_CODE\": \"0000\",            \"PARA\": [                {                    \"PARA_ID\": \"GRADUATE_SCHOOL\",                    \"PARA_VALUE\": \"\"                },                {                    \"PARA_ID\": \"SPECIALITY\",                    \"PARA_VALUE\": \"\"                },                {                    \"PARA_ID\": \"HEALTH_STATE_CODE\",                    \"PARA_VALUE\": \"\"                },                {                    \"PARA_ID\": \"RELIGION_CODE\",                    \"PARA_VALUE\": \"\"                },                {                    \"PARA_ID\": \"QQ\",                    \"PARA_VALUE\": \"\"                },                {                    \"PARA_ID\": \"WEI_XIN\",                    \"PARA_VALUE\": \"\"                },                {                    \"PARA_ID\": \"WEI_BO\",                    \"PARA_VALUE\": \"\"                },                {                    \"PARA_ID\": \"X_ZXXKSTATE_ITEM\",                    \"PARA_VALUE\": \"非在线信控用户\"                },                {                    \"PARA_ID\": \"WHETHER_GROUP_USER\",                    \"PARA_VALUE\": \"1\"                },                {                    \"PARA_ID\": \"CUST_TAG\",                    \"PARA_VALUE\": \"4\"                },                {                    \"PARA_ID\": \"CERT_TAG\",                    \"PARA_VALUE\": \"4\"                },                {                    \"PARA_ID\": \"CHECK_INFO0\",                    \"PARA_VALUE\": \"TradeCheck_CheckOweFee:用户已经欠费，欠费金额为：-144.14,建议终止业务的办理！\\n是否要继续业务的办理？选择【确定】继续办理业务，选择【取消】终止办理业务。\"                },                {                    \"PARA_ID\": \"BLACK_USER_TAG\",                    \"PARA_VALUE\": \"0\"                },                {                    \"PARA_ID\": \"NOT_OWE_TAG\",                    \"PARA_VALUE\": \"1\"                }            ],            \"ACCT_INFO\": {                \"LIMIT\": \"0\",                \"ACCT_PASSWD\": \"\",                \"ACT_TAG\": \"1\",                \"START_ACYC_ID\": \"201411\",                \"CONTRACT_NO\": \"\",                \"OPEN_DATE\": \"20141201024725\",                \"DEBUTY_USER_ID\": \"\",                \"CUST_ID\": \"5114111112546880\",                \"BIND_TYPE\": \"1\",                \"END_ACYC_ID\": \"203712\",                \"PAY_MODE_CODE\": \"0\",                \"ACCOUNT_CYCLE\": \"\",                \"USER_PRIORITY\": \"0\",                \"PAYITEM_CODE\": \"-1\",                \"IS_GROUP_ACCT\": \"0\",                \"DEFAULT_TAG\": \"1\",                \"LIMIT_TYPE\": \"0\",                \"ACCT_ID\": \"5114111162187095\",                \"ACCT_PRIORITY\": \"0\",                \"DEBUTY_CODE\": \"\",                \"SCORE_VALUE\": \"0\",                \"ACCT_ONLY\": \"1\",                \"PAY_NAME\": \"张三\"            }        }    }}";

    public MyComparator(Workbook workbook) {
        this.workbook = workbook;
    }

    public MyComparator(String excelFile) throws IOException, InvalidFormatException {
        this.workbook = new XSSFWorkbook(new File(excelFile));
    }

    public static void main(String args[]) throws Exception{
        JSONObject jsonObject = JSONObject.parseObject(jsonstr);
        JSONObject firstJson = new JSONObject();
        firstJson.put(FIRST_JSON_NODE_NAME,jsonObject.getJSONObject(FIRST_JSON_NODE_NAME));

        String file = "C:\\Users\\MI\\Desktop\\demo.xlsx";
        MyComparator comparator = new MyComparator(file);
        comparator.compare(firstJson);
    }


    /**
     * 具体的比较操作
     * @return
     */
    private void compare(JSONObject jsonObject){
        Sheet sheet = workbook.getSheetAt(0);
        int lastRowNum = sheet.getLastRowNum();
        while (isBlank(sheet.getRow(lastRowNum))){
            lastRowNum--;
        }
        //找出需要被比较的json串
        MyExcelResolver myExcelResolver = new MyExcelResolver(sheet,lastRowNum);
        Node node = myExcelResolver.parse();
        Set<Node> set = new HashSet<>();
        set.add(node);
        compare(set,jsonObject,FIRST_JSON_NODE_NAME);
    }

    /**
     *
     * @param benchNode excel表格里解析后的节点对象
     * @param compareJson 要比较的json对象
     * @parem parentNodeNama 父节点名，比较过程中发现有缺少/多余的节点，能够通过parentNodeName定位到是哪个节点
     */
    public void compare(Set<Node> benchNode,Object compareJson,String parentNodeName){
        if (benchNode.isEmpty()){
            return;
        }
        if (compareJson instanceof JSONObject){
            doCompareJsonObject(benchNode, (JSONObject) compareJson,parentNodeName);
        }
        if (compareJson instanceof JSONArray){
            doCompareJsonArray(benchNode, (JSONArray) compareJson,parentNodeName);
        }
    }

    private void doCompareJsonObject(Set<Node> benchNode,JSONObject compareJson,String parentNodeName){

        Iterator<Node> iterator = benchNode.iterator();
        while (iterator.hasNext()){
            Node next = iterator.next();

            iterator.remove();

            if (!compareJson.containsKey(next.getName()) && next.getConstraint()!=0){
                //说明这个节点没得，就不用再继续比较它的子节点了
                System.out.println(parentNodeName+"少了这些节点："+next.getName());
                continue;
            }

            //继续比较它的节点
            Set<Node> childNodes = next.getChildNodes();
            Object o = compareJson.get(next.getName());
            compare(childNodes,o,next.toString());
            compareJson.remove(next.getName());
        }
        //继续看compareJson是不是还有多的
        if (compareJson.keySet().isEmpty()){
            return;
        }
        System.out.println(parentNodeName+"多了这些节点："+compareJson.keySet());
    }

    /**
     * json数组的比较
     * @param benchNode
     * @param compareArray
     */
    private void doCompareJsonArray(Set<Node> benchNode,JSONArray compareArray,String parentNodeName){
        if(parentNodeName.equals("USER_INFO.ATTR_INFO")){
            System.out.println();
        }
        for (int i = 0; i < compareArray.size(); i++) {
            Object o = compareArray.get(i);
            String pNodeName = parentNodeName+"数组下面第"+(i+1)+"个";
            Set<Node> _bNode = new HashSet<>();
            _bNode.addAll(benchNode);
            compare(_bNode,o,pNodeName);
        }
    }


    /**
     * 判断该excel行是否是空的
     * @param row
     * @return
     */
    private boolean isBlank(Row row){
        if (row==null){
            return true;
        }
        String s = row.getCell(0).toString();
        return s==null||s.trim().length()==0;
    }


}
