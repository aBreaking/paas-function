package com.sitech.paas.elk;

import com.alibaba.fastjson.JSONObject;
import com.sitech.paas.elk.ret.AggregationResult;

import java.util.List;

/**
 *
 * @author liwei_paas
 * @date 2019/9/2
 */
public class Demo {

    static String json= "{\n" +
            "  \"took\": 30,\n" +
            "  \"timed_out\": false,\n" +
            "  \"_shards\": {\n" +
            "    \"total\": 5,\n" +
            "    \"successful\": 5,\n" +
            "    \"skipped\": 0,\n" +
            "    \"failed\": 0\n" +
            "  },\n" +
            "  \"hits\": {\n" +
            "    \"total\": 9940,\n" +
            "    \"max_score\": 0,\n" +
            "    \"hits\": []\n" +
            "  },\n" +
            "  \"aggregations\": {\n" +
            "    \"srvName\": {\n" +
            "      \"doc_count_error_upper_bound\": 80,\n" +
            "      \"sum_other_doc_count\": 5596,\n" +
            "      \"buckets\": [\n" +
            "        {\n" +
            "          \"key\": \"sDynSvc\",\n" +
            "          \"doc_count\": 1510,\n" +
            "          \"retCode\": {\n" +
            "            \"doc_count_error_upper_bound\": 0,\n" +
            "            \"sum_other_doc_count\": 0,\n" +
            "            \"buckets\": [\n" +
            "              {\n" +
            "                \"key\": \"0\",\n" +
            "                \"doc_count\": 1510,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 0,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"ok!\",\n" +
            "                      \"doc_count\": 1510\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"key\": \"sAppQry\",\n" +
            "          \"doc_count\": 514,\n" +
            "          \"retCode\": {\n" +
            "            \"doc_count_error_upper_bound\": 0,\n" +
            "            \"sum_other_doc_count\": 0,\n" +
            "            \"buckets\": [\n" +
            "              {\n" +
            "                \"key\": \"0\",\n" +
            "                \"doc_count\": 514,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 0,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"ok!\",\n" +
            "                      \"doc_count\": 514\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"key\": \"sPFeeQuery\",\n" +
            "          \"doc_count\": 496,\n" +
            "          \"retCode\": {\n" +
            "            \"doc_count_error_upper_bound\": 0,\n" +
            "            \"sum_other_doc_count\": 0,\n" +
            "            \"buckets\": [\n" +
            "              {\n" +
            "                \"key\": \"0\",\n" +
            "                \"doc_count\": 464,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 0,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"ok!\",\n" +
            "                      \"doc_count\": 464\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              },\n" +
            "              {\n" +
            "                \"key\": \"422001001\",\n" +
            "                \"doc_count\": 32,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 22,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"用户不存在 ERRID:10901775\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"用户不存在 ERRID:13251308\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"用户不存在 ERRID:13251646\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"用户不存在 ERRID:17221789\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"用户不存在 ERRID:17221981\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"用户不存在 ERRID:19291136\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"用户不存在 ERRID:19291555\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"用户不存在 ERRID:19712017\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"用户不存在 ERRID:19861746\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"用户不存在 ERRID:20151131\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"key\": \"sIdentChk\",\n" +
            "          \"doc_count\": 374,\n" +
            "          \"retCode\": {\n" +
            "            \"doc_count_error_upper_bound\": 0,\n" +
            "            \"sum_other_doc_count\": 0,\n" +
            "            \"buckets\": [\n" +
            "              {\n" +
            "                \"key\": \"0\",\n" +
            "                \"doc_count\": 374,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 0,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"ok!\",\n" +
            "                      \"doc_count\": 374\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"key\": \"sQryCreditInfo\",\n" +
            "          \"doc_count\": 357,\n" +
            "          \"retCode\": {\n" +
            "            \"doc_count_error_upper_bound\": 0,\n" +
            "            \"sum_other_doc_count\": 0,\n" +
            "            \"buckets\": [\n" +
            "              {\n" +
            "                \"key\": \"0\",\n" +
            "                \"doc_count\": 356,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 0,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"ok!\",\n" +
            "                      \"doc_count\": 356\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              },\n" +
            "              {\n" +
            "                \"key\": \"422015450\",\n" +
            "                \"doc_count\": 1,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 0,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"用户信息不存在 ERRID:86321227\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"key\": \"sUserOrdQry\",\n" +
            "          \"doc_count\": 355,\n" +
            "          \"retCode\": {\n" +
            "            \"doc_count_error_upper_bound\": 0,\n" +
            "            \"sum_other_doc_count\": 0,\n" +
            "            \"buckets\": [\n" +
            "              {\n" +
            "                \"key\": \"40005231\",\n" +
            "                \"doc_count\": 230,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 5,\n" +
            "                  \"sum_other_doc_count\": 220,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:10611319\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:11811276\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:11831910\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:11841951\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:11851215\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:11871289\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:11901058\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:11911452\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:11931161\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:11931722\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              },\n" +
            "              {\n" +
            "                \"key\": \"0\",\n" +
            "                \"doc_count\": 125,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 0,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"ok!\",\n" +
            "                      \"doc_count\": 125\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"key\": \"com_sitech_ordersvc_person_atom_inter_s1018_IDynamicSqlQryAoSvc_qry\",\n" +
            "          \"doc_count\": 213,\n" +
            "          \"retCode\": {\n" +
            "            \"doc_count_error_upper_bound\": 0,\n" +
            "            \"sum_other_doc_count\": 0,\n" +
            "            \"buckets\": [\n" +
            "              {\n" +
            "                \"key\": \"0\",\n" +
            "                \"doc_count\": 213,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 0,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"OK\",\n" +
            "                      \"doc_count\": 213\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"key\": \"sIntegratedQryOfCen\",\n" +
            "          \"doc_count\": 210,\n" +
            "          \"retCode\": {\n" +
            "            \"doc_count_error_upper_bound\": 0,\n" +
            "            \"sum_other_doc_count\": 0,\n" +
            "            \"buckets\": [\n" +
            "              {\n" +
            "                \"key\": \"0\",\n" +
            "                \"doc_count\": 209,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 0,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"ok!\",\n" +
            "                      \"doc_count\": 209\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              },\n" +
            "              {\n" +
            "                \"key\": \"406110002\",\n" +
            "                \"doc_count\": 1,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 0,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"该客户不存在！ ERRID:27881876\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"key\": \"sSmsQryScL\",\n" +
            "          \"doc_count\": 164,\n" +
            "          \"retCode\": {\n" +
            "            \"doc_count_error_upper_bound\": 0,\n" +
            "            \"sum_other_doc_count\": 0,\n" +
            "            \"buckets\": [\n" +
            "              {\n" +
            "                \"key\": \"0\",\n" +
            "                \"doc_count\": 161,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 0,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"ok!\",\n" +
            "                      \"doc_count\": 161\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              },\n" +
            "              {\n" +
            "                \"key\": \"402034812\",\n" +
            "                \"doc_count\": 3,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 0,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"对不起 没有该服务号码对应的用户信息 ERRID:56091960\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"对不起 没有该服务号码对应的用户信息 ERRID:65941896\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"对不起 没有该服务号码对应的用户信息 ERRID:78531050\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        },\n" +
            "        {\n" +
            "          \"key\": \"sMobOnlineSvc\",\n" +
            "          \"doc_count\": 151,\n" +
            "          \"retCode\": {\n" +
            "            \"doc_count_error_upper_bound\": 0,\n" +
            "            \"sum_other_doc_count\": 0,\n" +
            "            \"buckets\": [\n" +
            "              {\n" +
            "                \"key\": \"0\",\n" +
            "                \"doc_count\": 134,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 0,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"ok!\",\n" +
            "                      \"doc_count\": 134\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              },\n" +
            "              {\n" +
            "                \"key\": \"422015900\",\n" +
            "                \"doc_count\": 10,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 0,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:14861613\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:14871532\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:15551384\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:16561878\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:20221636\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:22331657\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:22361295\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:25161231\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:41231824\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"无法获取该错误代码的错误信息 请直接查看明细信息 ERRID:42281844\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              },\n" +
            "              {\n" +
            "                \"key\": \"422002000\",\n" +
            "                \"doc_count\": 3,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 0,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"用户不存在该账期的费用! ERRID:10981683\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"用户不存在该账期的费用! ERRID:10991671\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"用户不存在该账期的费用! ERRID:21821745\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              },\n" +
            "              {\n" +
            "                \"key\": \"422015252\",\n" +
            "                \"doc_count\": 3,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 0,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"取家庭成员信息不存在！ ERRID:10961677\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"取家庭成员信息不存在！ ERRID:22341963\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    },\n" +
            "                    {\n" +
            "                      \"key\": \"取家庭成员信息不存在！ ERRID:98801348\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              },\n" +
            "              {\n" +
            "                \"key\": \"422015013\",\n" +
            "                \"doc_count\": 1,\n" +
            "                \"retMsg\": {\n" +
            "                  \"doc_count_error_upper_bound\": 0,\n" +
            "                  \"sum_other_doc_count\": 0,\n" +
            "                  \"buckets\": [\n" +
            "                    {\n" +
            "                      \"key\": \"打印月份早于过户月份 ERRID:15531855\",\n" +
            "                      \"doc_count\": 1\n" +
            "                    }\n" +
            "                  ]\n" +
            "                }\n" +
            "              }\n" +
            "            ]\n" +
            "          }\n" +
            "        }\n" +
            "      ]\n" +
            "    }\n" +
            "  }\n" +
            "}";


    public static void main(String args[]){
        String[] aggs = {"srvName","retCode","retMsg"};
        AggregationResult result = new AggregationResult(aggs);
        List<AggregationResult.Node> list = result.parse(JSONObject.parseObject(json));
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i));
        }
    }

}
