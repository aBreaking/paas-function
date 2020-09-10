package com.sitech.esb.hb;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.sitech.esb.util.AppSrvConstant;
import com.sitech.esb.util.StringUtil;

/**
 * Data access object (DAO) for domain model class SrvInfo.
 * @see com.sitech.esb.hb.SrvInfo
 * @author MyEclipse - Hibernate Tools
 */
public class SrvInfoDAO extends BaseHibernateDAO {

    private static final Logger log = Logger.getLogger(SrvInfoDAO.class);

	//property constants
	public static final String SRV_NAME = "srvName";
	public static final String SRV_CH_NAME = "srvChName";
	public static final String SRV_DESCRIBE = "srvDescribe";
	public static final String INPARAMS_DEMO = "inparamsDemo";
	public static final String SRV_VER = "srvVer";
	public static final String SRV_WSDL_ADDR = "srvWsdlAddr";
	public static final String SRV_IN = "srvIn";
	public static final String SRV_OUT = "srvOut";
	public static final String BUSINESS_UUID = "businessUuid";
	public static final String SRV_UUID = "srvUuid";
	public static String AllServiceNames="";
	
	//update by liwei ,���ر���������
    public Serializable save(SrvInfo transientInstance, Session session) {
        log.debug("saving SrvInfo instance");
        try {
            Serializable id = session.save(transientInstance);            
            log.debug("save successful");
            return id;
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    public void update(SrvInfo transientInstance, Session session) {
    	log.debug("saving SrvInfo instance");
        try {
            session.update(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    
    public void saveOrUpdate(SrvInfo transientInstance, Session session) {
    	log.debug("saving SrvInfo instance");
        try {
            session.saveOrUpdate(transientInstance);
            log.debug("save successful");
        } catch (RuntimeException re) {
            log.error("save failed", re);
            throw re;
        }
    }
    public void save(SrvWsdlFile transientInstance, Session session) {
        
        try {
            session.saveOrUpdate(transientInstance);

        } catch (RuntimeException re) {
      
            throw re;
        }
    }
  public void save(SrvSchemaFile transientInstance, Session session) {
        
        try {
            session.saveOrUpdate(transientInstance);

        } catch (RuntimeException re) {
      
            throw re;
        }
    }
	public static int copyStream(InputStream in, OutputStream out)
	throws IOException {
		byte[]	buf = new byte[1024 * 64]; // 64k bytes
		int		len = 0;
		int		empCount = 0;
		int		totalLen = 0;
		
		while (len >= 0 && empCount < 5) {
			len = in.read(buf);
			
			if (len < 0) {
				break;
			}
			else if (len == 0) {
				++ empCount;
			}
			else {
				out.write(buf, 0, len);
				totalLen += len;
			}
		}
		return totalLen;
	}
	public void delete(SrvInfo persistentInstance, Session session) {
        log.debug("deleting SrvInfo instance");
        try {
            session.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed");
            throw re;
        }
    }
	
	/**
	 * add by liuc
	 * ��appname����
	 * @param id
	 * @param session
	 * @return
	 */
	public SrvInfo findInstanceBySrvName( String srvName, Session session) {
		log.debug("getting SrvInfo instance with name: " + srvName);
		String hql = "from SrvInfo ss where ss.srvName = ?";
		try {
			Query query = session.createQuery(hql).setParameter(0, srvName);
			SrvInfo instance = (SrvInfo) query.uniqueResult();
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	public void deleteCommFromSrv(String srvId, String srvCommId, Session session) {
        log.debug("deleting CommProtl instance");
        try {
        	SrvCommProtl scp = new SrvCommProtlDAO().findById(Long.valueOf(srvCommId), session);
        	SrvInfo service = findById(Long.valueOf(srvId), session);
        	service.getSrvCommProtls().remove(scp);
        	session.update(service);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
	
	public void addCommFromSrv(String srvId, String srvCommId, Session session) {
		log.debug("add CommProtl instance to app");
        try {
        	SrvCommProtl scp = new SrvCommProtlDAO().findById(Long.valueOf(srvCommId), session);
        	SrvInfo service = findById(Long.valueOf(srvId), session);
        	service.getSrvCommProtls().add(scp);
        	session.update(service);
            log.debug("add successful");
        } catch (RuntimeException re) {
            log.error("add failed", re);
            throw re;
        }
	}
	
	public SrvInfo findById( java.lang.Long id, Session session) {
        log.debug("getting SrvInfo instance with id: " + id);
        try {
            SrvInfo instance = (SrvInfo) session.get("com.sitech.esb.hb.SrvInfo", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
	public SrvWsdlFile findWsdlById( java.lang.Long id, Session session) {
        log.debug("getting SrvInfo instance with id: " + id);
        try {
        	SrvWsdlFile instance = (SrvWsdlFile) session.get("com.sitech.esb.hb.SrvWsdlFile", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
	public SrvSchemaFile findSchemaById( java.lang.Long id, Session session) {
        log.debug("getting SrvInfo instance with id: " + id);
        try {
        	SrvSchemaFile instance = (SrvSchemaFile) session.get("com.sitech.esb.hb.SrvSchemaFile", id);
            return instance;
        } catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
	public void deleteSrvSchemaFile(SrvSchemaFile persistentInstance, Session session) {
        log.debug("deleting SrvInfo instance");
        try {
            session.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed");
            throw re;
        }
    }
	public void deleteSrvWsdlFile(SrvWsdlFile persistentInstance, Session session) {
        log.debug("deleting SrvInfo instance");
        try {
            session.delete(persistentInstance);
            log.debug("delete successful");
        } catch (RuntimeException re) {
            log.error("delete failed");
            throw re;
        }
    }
    public List findByExample(SrvInfo instance, Session session) {
        log.debug("finding SrvInfo instance by example");
        try {
            List results = session
                    .createCriteria("com.sitech.esb.hb.SrvInfo")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        } catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    } 
    
    public int findCountByProperty(String propertyName, Object value, Session session) {
        log.debug("finding SrvInfo instance count with property: " + propertyName
              + ", value: " + value);
        try {
           String queryString = "select count(model.srvId) from SrvInfo as model where model." 
           						+ propertyName + "= ?";
           Query queryObject = session.createQuery(queryString);
  		   queryObject.setParameter(0, value);
  		 return ((Long)queryObject.uniqueResult()).intValue();
        } catch (RuntimeException re) {
           log.error("find by property name failed", re);
           throw re;
        }
  	}
    public List findWsdlByParas(String [] paras, String srvIds, Session session) {
    	log.debug("finding SrvInfo instance with property:1 ");
    	StringBuffer hsb = new StringBuffer("select model from SrvWsdlFile as model ");
    	if(!paras[7].trim().equals("")) {
    		int srvType = Integer.parseInt(paras[7].trim()); 
    		if(srvType==AppSrvConstant.SRVTYPE_SITECH_TXDO) {
    			hsb = new StringBuffer("select model from SiTxdSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SITXD_COM) {
    			hsb = new StringBuffer("select model from SiTxdComSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SOAP) {
    			hsb = new StringBuffer("select model from AgentSoapSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SOAP_COM) {
    			hsb = new StringBuffer("select model from SelfSoapSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SIUTYPEWS) {
    			hsb = new StringBuffer("select model from SiUtypeWsSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_XMLSTR_COM) {
    			hsb = new StringBuffer("select model from XmlComInfo as model ");
    		}
    	} 
		StringBuffer sb = new StringBuffer();
			if(!paras[0].trim().equals("")) {
				sb.append("upper(model.srvinfo.srvName) like :srvName");
				sb.append(" and ");
			}	
			if(!paras[1].trim().equals("")) {
				sb.append("upper(model.srvinfo.srvChName) like :srvChName");
				sb.append(" and ");
			}	
			if(!paras[2].trim().equals("-1")) {
				sb.append("upper(model.srvinfo.validFlag) like :validFlag");
				sb.append(" and ");
			}
			if(Long.valueOf(paras[3].trim()).longValue() >= 0) {
				sb.append("model.srvinfo.srvStatus.srvStatusId = :srvStatusId");
				sb.append(" and ");
			}
			if(!paras[4].trim().equals("")) {
				sb.append("upper(input.inputName) like :srvIn");
				sb.append(" and ");
				hsb.append(" join model.srvinfo.srvInputs as input");
			}
			if(!paras[5].trim().equals("")) {
				sb.append("upper(output.outputName) like :srvOut");
				sb.append(" and ");
				hsb.append(" join model.srvinfo.srvOutputs as output");
			}
			if(!paras[6].trim().equals("")) {
				sb.append("upper(appInfo.appName) like :appName");
				sb.append(" and ");
				hsb.append(" join model.srvinfo.appInfo as appInfo");
			}
			if(!paras[7].trim().equals("")) {
				sb.append("srvType.srvType = :srvType");
				sb.append(" and ");
				hsb.append(" join model.srvinfo.srvType as srvType");
			}
		
			if(!paras[8].trim().equals("-1")) {
				if(Integer.parseInt(paras[7].trim())==AppSrvConstant.SRVTYPE_SOAP) {
					sb.append(" soaphttpSystem.soaphttpSystemId = :system");
					sb.append(" and ");
					hsb.append(" join model.srvinfo.soaphttpSystem as soaphttpSystem");
				}
				if((Integer.parseInt(paras[7].trim())==AppSrvConstant.SRVTYPE_SIUTYPEWS) || 
				   (Integer.parseInt(paras[7].trim())==AppSrvConstant.SRVTYPE_SITECH_TXDO) ||
				    (Integer.parseInt(paras[7].trim()) == AppSrvConstant.SRVTYPE_UTYPE0)) {
					sb.append(" txdoSystem.txdoSystemId = :system");
					sb.append(" and ");
					hsb.append(" join model.srvinfo.txdoSystem as txdoSystem");
				}
			}
			if(!paras[9].trim().equals("")) {
				sb.append(" upper(model.srvWsdlFile) like = :");
				sb.append(" and ");

			}

			if(!srvIds.equals("")) {
				sb.append(" model.srvWsdlFileid in ("+srvIds+")");
				sb.append(" and ");
			
			}
			
			hsb.append(" where ");
			hsb.append(" 1=1 and ");
		String strTemp = sb.toString();	
	    if (strTemp.endsWith(" and ")) {
	    	strTemp = strTemp.substring(0,strTemp.length()-5);
	    }
	     
		try {	
			String queryString = null; 
			Query queryObject  = null;
				if(sb.length()>0) {
			    	queryString = hsb.toString() + strTemp + " order by model.srvId ";
			    
			    	queryObject = session.createQuery(queryString);
			    	if(!paras[0].trim().equals("")) {
			    		queryObject.setParameter("srvName", "%" + paras[0].toUpperCase() + "%");
					}
					if(!paras[1].trim().equals("")) {
						queryObject.setParameter("srvChName", "%" + paras[1].toUpperCase() + "%");
					}	
					if(!paras[2].trim().equals("-1")) {
						queryObject.setParameter("validFlag", Long.valueOf(paras[2]));
					}
					if(Long.valueOf(paras[3].trim()).longValue() >= 0) {
						queryObject.setParameter("srvStatusId", Long.valueOf(paras[3].trim()));
					}
					if(!paras[4].trim().equals("")) {
						queryObject.setParameter("srvIn", "%" + paras[4].toUpperCase() + "%");
					}
					if(!paras[5].trim().equals("")) {
						queryObject.setParameter("srvOut", "%" + paras[5].toUpperCase() + "%");
					}
					if(!paras[6].trim().equals("")) {
						queryObject.setParameter("appName", "%" + paras[6].toUpperCase() + "%");
					}
					if(!paras[7].trim().equals("")) {
						queryObject.setParameter("srvType", Long.valueOf(paras[7].trim()));
					}
					if(paras[8] != null && !paras[8].trim().equals("") && !paras[8].trim().equals("-1")) {
						queryObject.setParameter("system", Long.valueOf(paras[8].trim()));
					}
					if(!paras[9].trim().equals("")) {
						queryObject.setParameter("srvWsdlFile", "%" + paras[9].toUpperCase() + "%");
					}
				
			    } else {
			    	queryString =  "select model from SrvWsdlFile as model " +
					               " order by model.srvId";
			    	/*
			    	queryString =  "model.srvId, model.srvName, model.srvChName, model.validFlag, " +
	 	               " model.srvType.srvTypName, model.srvStatus.srvStatusName, model.appInfo.appName, " +
	 	               " model.srvCommProtls.srvAddr, model.srvInputs.inputName, model.srvOutputs.outputName " +
	 	               " where model.srvStatus.srvStatusId<>" + com.sitech.esb.util.AppSrvConstant.SRV_REMOVING + " order by model.srvName";
			    	*/
	
			    	queryObject = session.createQuery(queryString);
			    }
				
				List list = queryObject.list();
				return list;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
    }
    public List findSchemaByParas(String [] paras, String srvSchemaFileId, Session session) {
    	log.debug("finding SrvInfo instance with property:1 ");
    	StringBuffer hsb = new StringBuffer("select model from SrvSchemaFile as model ");
    	if(!paras[7].trim().equals("")) {
    		int srvType = Integer.parseInt(paras[7].trim()); 
    		if(srvType==AppSrvConstant.SRVTYPE_SITECH_TXDO) {
    			hsb = new StringBuffer("select model from SiTxdSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SITXD_COM) {
    			hsb = new StringBuffer("select model from SiTxdComSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SOAP) {
    			hsb = new StringBuffer("select model from AgentSoapSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SOAP_COM) {
    			hsb = new StringBuffer("select model from SelfSoapSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SIUTYPEWS) {
    			hsb = new StringBuffer("select model from SiUtypeWsSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_XMLSTR_COM) {
    			hsb = new StringBuffer("select model from XmlComInfo as model ");
    		}
    	} 
		StringBuffer sb = new StringBuffer();
			if(!paras[0].trim().equals("")) {
				sb.append("upper(model.srvWsdlFile.srvinfo.srvName) like :srvName");
				sb.append(" and ");
			}	
			if(!paras[1].trim().equals("")) {
				sb.append("upper(model.srvWsdlFile.srvinfo.srvChName) like :srvChName");
				sb.append(" and ");
			}	
			if(!paras[2].trim().equals("-1")) {
				sb.append("upper(model.srvWsdlFile.srvinfo.validFlag) like :validFlag");
				sb.append(" and ");
			}
			if(Long.valueOf(paras[3].trim()).longValue() >= 0) {
				sb.append("model.srvWsdlFile.srvinfo.srvStatus.srvStatusId = :srvStatusId");
				sb.append(" and ");
			}
			if(!paras[4].trim().equals("")) {
				sb.append("upper(input.inputName) like :srvIn");
				sb.append(" and ");
				hsb.append(" join model.srvWsdlFile.srvinfo.srvInputs as input");
			}
			if(!paras[5].trim().equals("")) {
				sb.append("upper(output.outputName) like :srvOut");
				sb.append(" and ");
				hsb.append(" join model.srvWsdlFile.srvinfo.srvOutputs as output");
			}
			if(!paras[6].trim().equals("")) {
				sb.append("upper(appInfo.appName) like :appName");
				sb.append(" and ");
				hsb.append(" join model.srvWsdlFile.srvinfo.appInfo as appInfo");
			}
			if(!paras[7].trim().equals("")) {
				sb.append("srvType.srvType = :srvType");
				sb.append(" and ");
				hsb.append(" join model.srvWsdlFile.srvinfo.srvType as srvType");
			}
		
			if(!paras[8].trim().equals("-1")) {
				if(Integer.parseInt(paras[7].trim())==AppSrvConstant.SRVTYPE_SOAP) {
					sb.append(" soaphttpSystem.soaphttpSystemId = :system");
					sb.append(" and ");
					hsb.append(" join model.srvWsdlFile.srvinfo.soaphttpSystem as soaphttpSystem");
				}
				if((Integer.parseInt(paras[7].trim())==AppSrvConstant.SRVTYPE_SIUTYPEWS) || 
				   (Integer.parseInt(paras[7].trim())==AppSrvConstant.SRVTYPE_SITECH_TXDO) ||
				    (Integer.parseInt(paras[7].trim()) == AppSrvConstant.SRVTYPE_UTYPE0)) {
					sb.append(" txdoSystem.txdoSystemId = :system");
					sb.append(" and ");
					hsb.append(" join model.srvWsdlFile.srvinfo.txdoSystem as txdoSystem");
				}
			}
			if(!paras[9].trim().equals("")) {
				sb.append(" upper(model.srvWsdlFile.srvWsdlFile) like = :");
				sb.append(" and ");

			}
			if(!paras[10].trim().equals("")) {
				sb.append(" model.srvWsdlFileid =:srvWsdlFileid");
				sb.append(" and ");

			}
			if(!srvSchemaFileId.equals("")) {
			
				sb.append(" model.srvSchemaFileId in ("+srvSchemaFileId+") ");
				sb.append(" and ");
			}
			hsb.append(" where ");
			hsb.append(" 1=1 and ");
		String strTemp = sb.toString();	
	    if (strTemp.endsWith(" and ")) {
	    	strTemp = strTemp.substring(0,strTemp.length()-5);
	    }
		try {	
			String queryString = null; 
			Query queryObject  = null;
				if(sb.length()>0) {
			    	queryString = hsb.toString() + strTemp + " order by model.srvId ";
//			    System.out.println(queryString);
			    	queryObject = session.createQuery(queryString);
			    	if(!paras[0].trim().equals("")) {
			    		queryObject.setParameter("srvName", "%" + paras[0].toUpperCase() + "%");
					}
					if(!paras[1].trim().equals("")) {
						queryObject.setParameter("srvChName", "%" + paras[1].toUpperCase() + "%");
					}	
					if(!paras[2].trim().equals("-1")) {
						queryObject.setParameter("validFlag", Long.valueOf(paras[2]));
					}
					if(Long.valueOf(paras[3].trim()).longValue() >= 0) {
						queryObject.setParameter("srvStatusId", Long.valueOf(paras[3].trim()));
					}
					if(!paras[4].trim().equals("")) {
						queryObject.setParameter("srvIn", "%" + paras[4].toUpperCase() + "%");
					}
					if(!paras[5].trim().equals("")) {
						queryObject.setParameter("srvOut", "%" + paras[5].toUpperCase() + "%");
					}
					if(!paras[6].trim().equals("")) {
						queryObject.setParameter("appName", "%" + paras[6].toUpperCase() + "%");
					}
					if(!paras[7].trim().equals("")) {
						queryObject.setParameter("srvType", Long.valueOf(paras[7].trim()));
					}
					if(paras[8] != null && !paras[8].trim().equals("") && !paras[8].trim().equals("-1")) {
						queryObject.setParameter("system", Long.valueOf(paras[8].trim()));
					}
					if(!paras[9].trim().equals("")) {
						queryObject.setParameter("srvWsdlFile", "%" + paras[9].toUpperCase() + "%");
					}
					if(!paras[10].trim().equals("")) {
						queryObject.setParameter("srvWsdlFileid",  Long.valueOf(paras[10].trim()));
					}
			    } else {
			    	queryString =  "select model from SrvSchemaFile as model " +
					               " order by model.srvId";
			    	/*
			    	queryString =  "model.srvId, model.srvName, model.srvChName, model.validFlag, " +
	 	               " model.srvType.srvTypName, model.srvStatus.srvStatusName, model.appInfo.appName, " +
	 	               " model.srvCommProtls.srvAddr, model.srvInputs.inputName, model.srvOutputs.outputName " +
	 	               " where model.srvStatus.srvStatusId<>" + com.sitech.esb.util.AppSrvConstant.SRV_REMOVING + " order by model.srvName";
			    	*/
	
			    	queryObject = session.createQuery(queryString);
			    }
				
				List list = queryObject.list();
				return list;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
    }
    
    /**add by liwei
     * ���ݷ�������ѯ�����񣬾�ȷ��ѯ
     * @param srvNames ����������
     * @return
     */
    public ArrayList findBysrvNames(List<String> srvNames,Session session) {
    	ArrayList list = new ArrayList();
    	for(String s: srvNames) {
    		if(StringUtil.isNotBlank(s)) {
	    		/*String[] paras = {s,"","-1","-1","","","","","-1"};*/
	    		
	    		String queryString = "select model from SrvInfo as model  where  model.srvStatus.srvStatusId<>9 and upper(model.srvName) = :srvName order by model.srvName";
	    		
	    		Query query = session.createQuery(queryString);
	    		query.setParameter("srvName",s.trim().toUpperCase());	    		
	    		List sonList = query.list();
	    		
	    		//ֱ��ѭ������ˣ�ȥ���ظ���Ԫ��
	    		/*for(Object o:sonList) {
	    			if(!list.contains(o)) {
	    				list.add(o);
	    			}
	    		}*/
	    		
	    		//ÿ�ζ��Ǿ�ȷ��ѯ����û���ظ��������
	    		if(!sonList.isEmpty()) {
	    			list.add(sonList.get(0));
	    		}
    		}
    	}
    	
    	return list;
    }
    
    public List findByParas(String [] paras, String clientId, Session session) {
    	log.debug("finding SrvInfo instance with property: ");
    	
    	StringBuffer hsb = new StringBuffer("select model from SrvInfo as model ");
    	if(!paras[7].trim().equals("")) {
    		int srvType = Integer.parseInt(paras[7].trim()); 
    		if(srvType==AppSrvConstant.SRVTYPE_SITECH_TXDO) {
    			hsb = new StringBuffer("select model from SiTxdSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SITXD_COM) {
    			hsb = new StringBuffer("select model from SiTxdComSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SOAP) {
    			hsb = new StringBuffer("select model from AgentSoapSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SOAP_COM) {
    			hsb = new StringBuffer("select model from SelfSoapSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SIUTYPEWS) {
    			hsb = new StringBuffer("select model from SiUtypeWsSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_XMLSTR_COM) {
    			hsb = new StringBuffer("select model from XmlComInfo as model ");
    		}
    	} 
		StringBuffer sb = new StringBuffer();
			if(!paras[0].trim().equals("")) {
				sb.append("upper(model.srvName) like :srvName");
				sb.append(" and ");
			}	
			if(!paras[1].trim().equals("")) {
				sb.append("upper(model.srvChName) like :srvChName");
				sb.append(" and ");
			}	
			if(!paras[2].trim().equals("-1")) {
				sb.append("upper(model.validFlag) like :validFlag");
				sb.append(" and ");
			}
			if(Long.valueOf(paras[3].trim()).longValue() >= 0) {
				sb.append("model.srvStatus.srvStatusId = :srvStatusId");
				sb.append(" and ");
			}
			if(!paras[4].trim().equals("")) {
				sb.append("upper(input.inputName) like :srvIn");
				sb.append(" and ");
				hsb.append(" join model.srvInputs as input");
			}
			if(!paras[5].trim().equals("")) {
				sb.append("upper(output.outputName) like :srvOut");
				sb.append(" and ");
				hsb.append(" join model.srvOutputs as output");
			}
			if(!paras[6].trim().equals("")) {
				sb.append("upper(appInfo.appName) like :appName");
				sb.append(" and ");
				hsb.append(" join model.appInfo as appInfo");
			}
			if(!paras[7].trim().equals("")) {
				sb.append("srvType.srvType = :srvType");
				sb.append(" and ");
				hsb.append(" join model.srvType as srvType");
			}
			if(!paras[8].trim().equals("-1")) {
				if(Integer.parseInt(paras[7].trim())==AppSrvConstant.SRVTYPE_SOAP) {
					sb.append(" soaphttpSystem.soaphttpSystemId = :system");
					sb.append(" and ");
					hsb.append(" join model.soaphttpSystem as soaphttpSystem");
				}
				if((Integer.parseInt(paras[7].trim())==AppSrvConstant.SRVTYPE_SIUTYPEWS) || 
				   (Integer.parseInt(paras[7].trim())==AppSrvConstant.SRVTYPE_SITECH_TXDO) ||
				    (Integer.parseInt(paras[7].trim()) == AppSrvConstant.SRVTYPE_UTYPE0)) {
					sb.append(" txdoSystem.txdoSystemId = :system");
					sb.append(" and ");
					hsb.append(" join model.txdoSystem as txdoSystem");
				}
			}
			if(!clientId.equals("")) {
				sb.append("ca.clientInfo.clientId = :clientId");
				sb.append(" and ");
				hsb.append(" join model.appInfo.clientApps as ca");
			}
			hsb.append(" where ");
			hsb.append(" model.srvStatus.srvStatusId<>" + com.sitech.esb.util.AppSrvConstant.SRV_REMOVING + " and ");
		String strTemp = sb.toString();	
	    if (strTemp.endsWith(" and ")) {
	    	strTemp = strTemp.substring(0,strTemp.length()-5);
	    }
		try {	
			String queryString = null; 
			Query queryObject  = null;
				if(sb.length()>0) {
			    	queryString = hsb.toString() + strTemp + " order by model.srvName";
			    	queryObject = session.createQuery(queryString);
			    	if(!paras[0].trim().equals("")) {
			    		queryObject.setParameter("srvName", "%" + paras[0].toUpperCase() + "%");
					}
					if(!paras[1].trim().equals("")) {
						queryObject.setParameter("srvChName", "%" + paras[1].toUpperCase() + "%");
					}	
					if(!paras[2].trim().equals("-1")) {
						queryObject.setParameter("validFlag", Long.valueOf(paras[2]));
					}
					if(Long.valueOf(paras[3].trim()).longValue() >= 0) {
						queryObject.setParameter("srvStatusId", Long.valueOf(paras[3].trim()));
					}
					if(!paras[4].trim().equals("")) {
						queryObject.setParameter("srvIn", "%" + paras[4].toUpperCase() + "%");
					}
					if(!paras[5].trim().equals("")) {
						queryObject.setParameter("srvOut", "%" + paras[5].toUpperCase() + "%");
					}
					if(!paras[6].trim().equals("")) {
						queryObject.setParameter("appName", "%" + paras[6].toUpperCase() + "%");
					}
					if(!paras[7].trim().equals("")) {
						queryObject.setParameter("srvType", Long.valueOf(paras[7].trim()));
					}
					if(paras[8] != null && !paras[8].trim().equals("") && !paras[8].trim().equals("-1")) {
						queryObject.setParameter("system", Long.valueOf(paras[8].trim()));
					}
					if(!clientId.equals("")) {
						queryObject.setParameter("clientId", new Long(clientId));
					}
			    } else {
			    	queryString =  "select model from SrvInfo as model " +
					               "where model.srvStatus.srvStatusId<>" + com.sitech.esb.util.AppSrvConstant.SRV_REMOVING + " order by model.srvName";
			    	/*
			    	queryString =  "model.srvId, model.srvName, model.srvChName, model.validFlag, " +
	 	               " model.srvType.srvTypName, model.srvStatus.srvStatusName, model.appInfo.appName, " +
	 	               " model.srvCommProtls.srvAddr, model.srvInputs.inputName, model.srvOutputs.outputName " +
	 	               " where model.srvStatus.srvStatusId<>" + com.sitech.esb.util.AppSrvConstant.SRV_REMOVING + " order by model.srvName";
			    	*/
			    	
			    	queryObject = session.createQuery(queryString);
			    }
				List list = queryObject.list();
				return list;
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by property name failed", re);
			throw re;
		}
    }
    public List findByNowsdlParas(String [] paras, String clientId, Session session) {
    	log.debug("finding SrvInfo instance with property: ");
    	
    	StringBuffer hsb = new StringBuffer("select model from SrvInfo as model ");
    	if(!paras[7].trim().equals("")) {
    		int srvType = Integer.parseInt(paras[7].trim()); 
    		if(srvType==AppSrvConstant.SRVTYPE_SITECH_TXDO) {
    			hsb = new StringBuffer("select model from SiTxdSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SITXD_COM) {
    			hsb = new StringBuffer("select model from SiTxdComSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SOAP) {
    			hsb = new StringBuffer("select model from AgentSoapSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SOAP_COM) {
    			hsb = new StringBuffer("select model from SelfSoapSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SIUTYPEWS) {
    			hsb = new StringBuffer("select model from SiUtypeWsSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_XMLSTR_COM) {
    			hsb = new StringBuffer("select model from XmlComInfo as model ");
    		}
    	} 
		StringBuffer sb = new StringBuffer();
			if(!paras[0].trim().equals("")) {
				sb.append("upper(model.srvName) like :srvName");
				sb.append(" and ");
			}	
			if(!paras[1].trim().equals("")) {
				sb.append("upper(model.srvChName) like :srvChName");
				sb.append(" and ");
			}	
			if(!paras[2].trim().equals("-1")) {
				sb.append("upper(model.validFlag) like :validFlag");
				sb.append(" and ");
			}
			if(Long.valueOf(paras[3].trim()).longValue() >= 0) {
				sb.append("model.srvStatus.srvStatusId = :srvStatusId");
				sb.append(" and ");
			}
			if(!paras[4].trim().equals("")) {
				sb.append("upper(input.inputName) like :srvIn");
				sb.append(" and ");
				hsb.append(" join model.srvInputs as input");
			}
			if(!paras[5].trim().equals("")) {
				sb.append("upper(output.outputName) like :srvOut");
				sb.append(" and ");
				hsb.append(" join model.srvOutputs as output");
			}
			if(!paras[6].trim().equals("")) {
				sb.append("upper(appInfo.appName) like :appName");
				sb.append(" and ");
				hsb.append(" join model.appInfo as appInfo");
			}
			if(!paras[7].trim().equals("")) {
				sb.append("srvType.srvType = :srvType");
				sb.append(" and ");
				hsb.append(" join model.srvType as srvType");
			}
			if(!paras[8].trim().equals("-1")) {
				if(Integer.parseInt(paras[7].trim())==AppSrvConstant.SRVTYPE_SOAP) {
					sb.append(" soaphttpSystem.soaphttpSystemId = :system");
					sb.append(" and ");
					hsb.append(" join model.soaphttpSystem as soaphttpSystem");
				}
				if((Integer.parseInt(paras[7].trim())==AppSrvConstant.SRVTYPE_SIUTYPEWS) || 
				   (Integer.parseInt(paras[7].trim())==AppSrvConstant.SRVTYPE_SITECH_TXDO) ||
				    (Integer.parseInt(paras[7].trim()) == AppSrvConstant.SRVTYPE_UTYPE0)) {
					sb.append(" txdoSystem.txdoSystemId = :system");
					sb.append(" and ");
					hsb.append(" join model.txdoSystem as txdoSystem");
				}
			}
			if(!clientId.equals("")) {
				sb.append("ca.clientInfo.clientId = :clientId");
				sb.append(" and ");
				hsb.append(" join model.appInfo.clientApps as ca");
			}
			hsb.append(" where ");
			hsb.append(" where model.srvId not in (select pp.srvId from SrvWsdlFile pp  where pp.srvId is not null )  and ");
		String strTemp = sb.toString();	
	    if (strTemp.endsWith(" and ")) {
	    	strTemp = strTemp.substring(0,strTemp.length()-5);
	    }
		try {	
			String queryString = null; 
			Query queryObject  = null;
				if(sb.length()>0) {
			    	queryString = hsb.toString() + strTemp + " order by model.srvName";
			    	queryObject = session.createQuery(queryString);
			    	if(!paras[0].trim().equals("")) {
			    		queryObject.setParameter("srvName", "%" + paras[0].toUpperCase() + "%");
					}
					if(!paras[1].trim().equals("")) {
						queryObject.setParameter("srvChName", "%" + paras[1].toUpperCase() + "%");
					}	
					if(!paras[2].trim().equals("-1")) {
						queryObject.setParameter("validFlag", Long.valueOf(paras[2]));
					}
					if(Long.valueOf(paras[3].trim()).longValue() >= 0) {
						queryObject.setParameter("srvStatusId", Long.valueOf(paras[3].trim()));
					}
					if(!paras[4].trim().equals("")) {
						queryObject.setParameter("srvIn", "%" + paras[4].toUpperCase() + "%");
					}
					if(!paras[5].trim().equals("")) {
						queryObject.setParameter("srvOut", "%" + paras[5].toUpperCase() + "%");
					}
					if(!paras[6].trim().equals("")) {
						queryObject.setParameter("appName", "%" + paras[6].toUpperCase() + "%");
					}
					if(!paras[7].trim().equals("")) {
						queryObject.setParameter("srvType", Long.valueOf(paras[7].trim()));
					}
					if(paras[8] != null && !paras[8].trim().equals("") && !paras[8].trim().equals("-1")) {
						queryObject.setParameter("system", Long.valueOf(paras[8].trim()));
					}
					if(!clientId.equals("")) {
						queryObject.setParameter("clientId", new Long(clientId));
					}
			    } else {
			    	queryString =  "select model from SrvInfo as model " +
					               "where model.srvId not in (select pp.srvId from SrvWsdlFile pp where pp.srvId is not null )  order by model.srvName";
		
			    	queryObject = session.createQuery(queryString);
			    }
				List list = queryObject.list();
				return list;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
    }
    public List selectWsdlByParas(String [] paras, String srvWsdlFileid, Session session) {
    	log.debug("finding SrvInfo instance with property:1 ");
    	StringBuffer hsb = new StringBuffer("select model from SrvWsdlFile as model ");
    	if(!paras[7].trim().equals("")) {
    		int srvType = Integer.parseInt(paras[7].trim()); 
    		if(srvType==AppSrvConstant.SRVTYPE_SITECH_TXDO) {
    			hsb = new StringBuffer("select model from SiTxdSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SITXD_COM) {
    			hsb = new StringBuffer("select model from SiTxdComSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SOAP) {
    			hsb = new StringBuffer("select model from AgentSoapSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SOAP_COM) {
    			hsb = new StringBuffer("select model from SelfSoapSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_SIUTYPEWS) {
    			hsb = new StringBuffer("select model from SiUtypeWsSrvInfo as model ");
    		}
    		if(srvType==AppSrvConstant.SRVTYPE_XMLSTR_COM) {
    			hsb = new StringBuffer("select model from XmlComInfo as model ");
    		}
    	} 
		StringBuffer sb = new StringBuffer();
			if(!paras[0].trim().equals("")) {
				sb.append("upper(model.srvinfo.srvName) like :srvName");
				sb.append(" and ");
			}	
			if(!paras[1].trim().equals("")) {
				sb.append("upper(model.srvinfo.srvChName) like :srvChName");
				sb.append(" and ");
			}	
			if(!paras[2].trim().equals("-1")) {
				sb.append("upper(model.srvinfo.validFlag) like :validFlag");
				sb.append(" and ");
			}
			if(Long.valueOf(paras[3].trim()).longValue() >= 0) {
				sb.append("model.srvinfo.srvStatus.srvStatusId = :srvStatusId");
				sb.append(" and ");
			}
			if(!paras[4].trim().equals("")) {
				sb.append("upper(input.inputName) like :srvIn");
				sb.append(" and ");
				hsb.append(" join model.srvinfo.srvInputs as input");
			}
			if(!paras[5].trim().equals("")) {
				sb.append("upper(output.outputName) like :srvOut");
				sb.append(" and ");
				hsb.append(" join model.srvinfo.srvOutputs as output");
			}
			if(!paras[6].trim().equals("")) {
				sb.append("upper(appInfo.appName) like :appName");
				sb.append(" and ");
				hsb.append(" join model.srvinfo.appInfo as appInfo");
			}
			if(!paras[7].trim().equals("")) {
				sb.append("srvType.srvType = :srvType");
				sb.append(" and ");
				hsb.append(" join model.srvinfo.srvType as srvType");
			}
		
			if(!paras[8].trim().equals("-1")) {
				if(Integer.parseInt(paras[7].trim())==AppSrvConstant.SRVTYPE_SOAP) {
					sb.append(" soaphttpSystem.soaphttpSystemId = :system");
					sb.append(" and ");
					hsb.append(" join model.srvinfo.soaphttpSystem as soaphttpSystem");
				}
				if((Integer.parseInt(paras[7].trim())==AppSrvConstant.SRVTYPE_SIUTYPEWS) || 
				   (Integer.parseInt(paras[7].trim())==AppSrvConstant.SRVTYPE_SITECH_TXDO) ||
				    (Integer.parseInt(paras[7].trim()) == AppSrvConstant.SRVTYPE_UTYPE0)) {
					sb.append(" txdoSystem.txdoSystemId = :system");
					sb.append(" and ");
					hsb.append(" join model.srvinfo.txdoSystem as txdoSystem");
				}
			}
			if(!paras[9].trim().equals("")) {
				sb.append(" upper(model.srvWsdlFile) like = :");
				sb.append(" and ");

			}
			if(!srvWsdlFileid.equals("")) {
				sb.append(" model.srvWsdlFileid = :srvWsdlFileid");
				sb.append(" and ");
			
			}
			hsb.append(" where ");
			hsb.append(" model.srvId is not null and ");
		String strTemp = sb.toString();	
	    if (strTemp.endsWith(" and ")) {
	    	strTemp = strTemp.substring(0,strTemp.length()-5);
	    }
		try {	
			String queryString = null; 
			Query queryObject  = null;
				if(sb.length()>0) {
			    	queryString = hsb.toString() + strTemp + " order by model.srvId ";
			    
			    	queryObject = session.createQuery(queryString);
			    	if(!paras[0].trim().equals("")) {
			    		queryObject.setParameter("srvName", "%" + paras[0].toUpperCase() + "%");
					}
					if(!paras[1].trim().equals("")) {
						queryObject.setParameter("srvChName", "%" + paras[1].toUpperCase() + "%");
					}	
					if(!paras[2].trim().equals("-1")) {
						queryObject.setParameter("validFlag", Long.valueOf(paras[2]));
					}
					if(Long.valueOf(paras[3].trim()).longValue() >= 0) {
						queryObject.setParameter("srvStatusId", Long.valueOf(paras[3].trim()));
					}
					if(!paras[4].trim().equals("")) {
						queryObject.setParameter("srvIn", "%" + paras[4].toUpperCase() + "%");
					}
					if(!paras[5].trim().equals("")) {
						queryObject.setParameter("srvOut", "%" + paras[5].toUpperCase() + "%");
					}
					if(!paras[6].trim().equals("")) {
						queryObject.setParameter("appName", "%" + paras[6].toUpperCase() + "%");
					}
					if(!paras[7].trim().equals("")) {
						queryObject.setParameter("srvType", Long.valueOf(paras[7].trim()));
					}
					if(paras[8] != null && !paras[8].trim().equals("") && !paras[8].trim().equals("-1")) {
						queryObject.setParameter("system", Long.valueOf(paras[8].trim()));
					}
					if(!paras[9].trim().equals("")) {
						queryObject.setParameter("srvWsdlFile", "%" + paras[9].toUpperCase() + "%");
					}
					if(!srvWsdlFileid.equals("")) {
						queryObject.setParameter("srvWsdlFileid", new Long(srvWsdlFileid));
					}
			    } else {
			    	queryString =  "select model from SrvWsdlFile as model where  model.srvId is not null " +
					               " order by model.srvId";
			    	/*
			    	queryString =  "model.srvId, model.srvName, model.srvChName, model.validFlag, " +
	 	               " model.srvType.srvTypName, model.srvStatus.srvStatusName, model.appInfo.appName, " +
	 	               " model.srvCommProtls.srvAddr, model.srvInputs.inputName, model.srvOutputs.outputName " +
	 	               " where model.srvStatus.srvStatusId<>" + com.sitech.esb.util.AppSrvConstant.SRV_REMOVING + " order by model.srvName";
			    	*/
	
			    	queryObject = session.createQuery(queryString);
			    }
				
				List list = queryObject.list();
				return list;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
    }
    public List findByProperty(String propertyName, Object value, Session session) {
      log.debug("finding SrvInfo instance with property: " + propertyName
            + ", value: " + value);
      try {
         String queryString = "from SrvInfo as model where model." 
         						+ propertyName + "= ? order by model.srvName";
         Query queryObject = session.createQuery(queryString);
		 queryObject.setParameter(0, value);
		 return queryObject.list();
      } catch (RuntimeException re) {
    	  re.printStackTrace();
         log.error("find by property name failed", re);
         throw re;
      }
	}
    
    
    public String[] getBySrvName(Object value, Session session) {
        log.debug("finding SrvInfo instance with property: srvName value: " + value);
        try {
           String queryString = "select model.srvId, model.srvChName from SrvInfo as model where model.srvName = ?";
           Query queryObject = session.createQuery(queryString);
  		   queryObject.setParameter(0, value);
  		   List list = queryObject.list();
  		   String [] strs = new String[2];
  		   Object [] objs = null;
  		   if(!list.isEmpty()) {
  			objs = (Object[])list.get(0);
	  		 if(objs!=null) {
	  		 for(int i=0; i<strs.length; i++) {
	  			 if(objs[i]!=null && !objs[i].toString().trim().equals("")) {
	  				strs[i]= objs[i].toString();
	  			 } else {
	  				strs[i]="";
	  			 }
	  		 }
	  		 }
  		  }
  		  return strs;
          } catch (RuntimeException re) {
            log.error("find by property name failed", re);
            throw re;
          }
  	}
    
    public List getDefault(Session session) {
    	log.debug("finding All SrvInfo instance");
          try {
             String queryString = "from SrvInfo as model where model.srvStatus.srvStatusId<>" + com.sitech.esb.util.AppSrvConstant.SRV_REMOVING + " order by model.srvName";
             Query queryObject = session.createQuery(queryString);
    		 return queryObject.list();
          } catch (RuntimeException re) {
             log.error("find by property name failed", re);
             throw re;
          }
    }
    
    public List getUsed(Session session) {
    	log.debug("finding All SrvInfo instance");
          try {
             String queryString = "from SrvInfo as model where model.appInfo IS NOT NULL or model.srvStatus.srvStatusId='" + AppSrvConstant.SRV_CONFIG_ERROR + "' order by model.srvName";
             Query queryObject = session.createQuery(queryString);
    		 return queryObject.list();
          } catch (RuntimeException re) {
             log.error("find by property name failed", re);
             throw re;
          }
    }
    
    public List getNotDeleted(Session session) {
    	log.debug("finding All SrvInfo instance");
          try {
             String queryString = "from SrvInfo as model where model.srvStatus.srvStatusId!='9' order by model.srvName";
             Query queryObject = session.createQuery(queryString);
    		 return queryObject.list();
          } catch (RuntimeException re) {
             log.error("find by property name failed", re);
             throw re;
          }
    }
    
    public List getNotDeletedOfTree(Session session) {
    	log.debug("finding All SrvInfo instance");
          try {
             String queryString = "select new SrvInfo(model.srvId, model.srvName, model.srvChName, model.srvType) from SrvInfo as model where model.srvStatus.srvStatusId!='9' order by model.srvName";
             Query queryObject = session.createQuery(queryString);
    		 return queryObject.list();
          } catch (RuntimeException re) {
             log.error("find by property name failed", re);
             throw re;
          }
    }
    
    /*
     * �÷������ã���APP_SRV��
     * 
    public List getApps(SrvInfo srvInfo) {
    	log.debug("finding All SrvInfo instance");
          try {
        	 Set set  = srvInfo.getAppSrvs();
         	 Iterator it = set.iterator();
         	 List list = new ArrayList();
         	 AppSrv appSrv = null;
         	 while (it.hasNext()) {
         		 appSrv = (AppSrv)it.next();
         		 list.add(appSrv.getAppInfo());
         	 }
     		 return list;
          } catch (RuntimeException re) {
             log.error("find by property name failed", re);
             throw re;
          }
    }
    */
    
    public List findByNamesNotUsed(String srvName, String srvChName, Session session) {
		log.debug("finding SrvInfo instance with property: " + srvName + " and  " + srvChName);
		String queryString = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select new SrvInfo(model.srvId, model.srvName, model.srvChName, model.srvType, model.srvStatus) from SrvInfo as model " +
				" where model.srvStatus.srvStatusId<>" + com.sitech.esb.util.AppSrvConstant.SRV_REMOVING + 
				" and model.appInfo is null " );
		String tailStr = " order by model.srvName";
		Query queryObject = null;
		try {
			if (srvName.equals("") && srvChName.equals("")) {
				queryString = sb.toString() + tailStr ;
				queryObject = session.createQuery(queryString);
			}
			if (srvName.equals("") && !srvChName.equals("")) {
				queryString = sb.toString() + " and upper(model.srvChName) like :srvChName " + tailStr;
				queryObject = session.createQuery(queryString);
				queryObject.setParameter("srvChName", "%" + srvChName.toUpperCase() + "%");
			}
			if (!srvName.equals("") && srvChName.equals("")) {
				queryString = sb.toString() + " and upper(model.srvName) like :name " + tailStr;
				queryObject = session.createQuery(queryString);
				queryObject.setParameter("name", "%" + srvName.toUpperCase() + "%");
			}
			if (!srvName.equals("") && !srvChName.equals("")) {
				queryString = sb.toString() + " and upper(model.srvName) like :name and upper(model.srvChName) like :srvChName " + tailStr;
				queryObject = session.createQuery(queryString);
				queryObject.setParameter("name", "%" + srvName.toUpperCase() + "%");
				queryObject.setParameter("srvChName", "%" + srvChName.toUpperCase() + "%");
			}
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
    public List findByAppSrvNotUsed(String srvName, String srvChName, Session session) {
		log.debug("finding SrvInfo instance with property: " + srvName + " and  " + srvChName);
		String queryString = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select new SrvInfo(model.srvId, model.srvName, model.srvChName, model.srvType, model.srvStatus) from SrvInfo as model   " +
				" where model.srvId not in (select appSrv.id.srvId from AppSrv appSrv) and model.srvStatus.srvStatusId<>" + com.sitech.esb.util.AppSrvConstant.SRV_REMOVING + 
				" " );
		String tailStr = " order by model.srvName";

		Query queryObject = null;
		try {
			if (srvName.equals("") && srvChName.equals("")) {
				queryString = sb.toString() + tailStr ;
				queryObject = session.createQuery(queryString);
			}
			if (srvName.equals("") && !srvChName.equals("")) {
				queryString = sb.toString() + " and upper(model.srvChName) like :srvChName " + tailStr;
				queryObject = session.createQuery(queryString);
				queryObject.setParameter("srvChName", "%" + srvChName.toUpperCase() + "%");
			}
			if (!srvName.equals("") && srvChName.equals("")) {
				queryString = sb.toString() + " and upper(model.srvName) like :name " + tailStr;
				queryObject = session.createQuery(queryString);
				queryObject.setParameter("name", "%" + srvName.toUpperCase() + "%");
			}
			if (!srvName.equals("") && !srvChName.equals("")) {
				queryString = sb.toString() + " and upper(model.srvName) like :name and upper(model.srvChName) like :srvChName " + tailStr;
				queryObject = session.createQuery(queryString);
				queryObject.setParameter("name", "%" + srvName.toUpperCase() + "%");
				queryObject.setParameter("srvChName", "%" + srvChName.toUpperCase() + "%");
			}
			return queryObject.list();
			
		
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	public List findBySrvName(Object srvName, Session session) {
		return findByProperty(SRV_NAME, srvName, session);
	}
	
	public List findBySrvChName(Object srvChName, Session session) {
		return findByProperty(SRV_CH_NAME, srvChName, session);
	}
	
	public List findBySrvDescribe(Object srvDescribe, Session session) {
		return findByProperty(SRV_DESCRIBE, srvDescribe, session);
	}
	
	public List findBySrvVer(Object srvVer, Session session) {
		return findByProperty(SRV_VER, srvVer, session);
	}
	
	public List findBySrvWsdlAddr(Object srvWsdlAddr, Session session) {
		return findByProperty(SRV_WSDL_ADDR, srvWsdlAddr, session);
	}
	
	public List findBySrvIn(Object srvIn, Session session) {
		return findByProperty(SRV_IN, srvIn, session);
	}
	
	public List findBySrvOut(Object srvOut, Session session) {
		return findByProperty(SRV_OUT, srvOut, session);
	}
	
	public List findByBusinessUuid(Object businessUuid, Session session) {
		return findByProperty(BUSINESS_UUID, businessUuid, session);
	}
	
	public List findBySrvUuid(Object srvUuid, Session session) {
		return findByProperty(SRV_UUID, srvUuid, session);
	}
	
    public SrvInfo merge(SrvInfo detachedInstance, Session session) {
        log.debug("merging SrvInfo instance");
        try {
            SrvInfo result = (SrvInfo) session
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        } catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }

    public void attachDirty(SrvInfo instance, Session session) {
        log.debug("attaching dirty SrvInfo instance");
        try {
            session.saveOrUpdate(instance);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(SrvInfo instance, Session session) {
        log.debug("attaching clean SrvInfo instance");
        try {
            session.lock(instance, LockMode.NONE);
            log.debug("attach successful");
        } catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public List findBySystem(SoaphttpSystem soaphttpSystem, Session session, int srvLanguageType) {
		log.debug("finding SrvInfo instance ");
		//�������ֶ�����
        String orderStr = " order by model.srvName"; 
        if(srvLanguageType==1) {
     	   orderStr = " order by model.srvChName";
        }
        
		String queryString = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select new AgentSoapSrvInfo(model.srvId, model.srvName, model.srvChName) from AgentSoapSrvInfo as model " +
				" where model.srvStatus.srvStatusId<>" + com.sitech.esb.util.AppSrvConstant.SRV_REMOVING + 
				" and model.validFlag = 1 " + 
				" and model.soaphttpSystem = :soaphttpSystem" );
		String tailStr = " order by model.srvName";
		Query queryObject = null;
		try {
			queryString = sb.toString() + orderStr;
			queryObject = session.createQuery(queryString);
			queryObject.setParameter("soaphttpSystem",  soaphttpSystem);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
    
    public List findBySystem(TxdoSystem txdoSystem, Session session, int srvType, int srvLanguageType) {
		log.debug("finding SrvInfo instance ");
		
		//�������ֶ�����
        String orderStr = " order by model.srvName"; 
        if(srvLanguageType==1) {
     	   orderStr = " order by model.srvChName";
        }
        
		String queryString = null;
		StringBuffer sb = new StringBuffer();
		if(srvType == AppSrvConstant.SRVTYPE_SIUTYPEWS) {
			sb.append("select new SiUtypeWsSrvInfo(model.srvId, model.srvName, model.srvChName) from SiUtypeWsSrvInfo as model " +
					" where model.srvStatus.srvStatusId<>" + com.sitech.esb.util.AppSrvConstant.SRV_REMOVING + 
					" and model.validFlag = 1 " + 
					" and model.txdoSystem = :txdoSystem" );
		}
		if(srvType == AppSrvConstant.SRVTYPE_SITECH_TXDO) {
			sb.append("select new SiTxdSrvInfo(model.srvId, model.srvName, model.srvChName) from SiTxdSrvInfo as model " +
					" where model.srvStatus.srvStatusId<>" + com.sitech.esb.util.AppSrvConstant.SRV_REMOVING + 
					" and model.validFlag = 1 " + 
					" and model.txdoSystem = :txdoSystem" );
		}
		
		Query queryObject = null;
		try {
			queryString = sb.toString() + orderStr;
			queryObject = session.createQuery(queryString);
			queryObject.setParameter("txdoSystem",  txdoSystem);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
    
    public List findBySrvType(Session session, int srvType, int srvLanguageType) {
		log.debug("finding SrvInfo instance ");
		
		//�������ֶ�����
        String orderStr = " order by model.srvName"; 
        if(srvLanguageType==1) {
     	   orderStr = " order by model.srvChName";
        }
        
		String queryString = null;
		StringBuffer sb = new StringBuffer();
		sb.append("select new SrvInfo(model.srvId, model.srvName, model.srvChName) from SrvInfo as model " +
				" where model.srvStatus.srvStatusId<>" + com.sitech.esb.util.AppSrvConstant.SRV_REMOVING + 
				" and model.validFlag = 1 " + 
				" and model.srvType.srvType = :srvType" );
		
		Query queryObject = null;
		try {
			queryString = sb.toString() + orderStr;
			queryObject = session.createQuery(queryString);
			queryObject.setParameter("srvType",  new Long(srvType));
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			
			throw re;
		}
	}

	/**
	 * add by liuc end
	 * @param srvnames
	 * @param session
	 * @return
	 */
	public List<SrvInfo> getSrvList(String srvnames, Session session) {
		log.debug("finding SrvInfo List  with property: srvNames value: " + srvnames);
		StringBuffer srvbuffuer = new StringBuffer();
		if(srvnames!=null && !"".equals(srvnames.trim())){
			String[] srvStrs = srvnames.split(",");
			for(int i = 0;i<srvStrs.length;i++){
				srvbuffuer.append("'");
				srvbuffuer.append(srvStrs[i]);
				if(i == srvStrs.length-1){
					srvbuffuer.append("'");
				}else {
					srvbuffuer.append("',");
				}
			}
		}
		try {
			String queryString = "select new SrvInfo(model.srvId, model.srvName, model.srvChName, model.srvType, model.srvStatus) from SrvInfo as model where model.srvName in ("+srvbuffuer.toString()+")";
			Query queryObject = session.createQuery(queryString);
			List<SrvInfo> list = queryObject.list();
			return list;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}