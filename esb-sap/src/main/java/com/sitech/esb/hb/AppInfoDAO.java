package com.sitech.esb.hb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;

import com.sitech.esb.util.AppSrvConstant;

/**
 * Data access object (DAO) for domain model class AppInfo.
 * @see com.sitech.esb.hb.AppInfo
 * @author MyEclipse - Hibernate Tools
 */
public class AppInfoDAO extends BaseHibernateDAO {

	private static final Logger log = Logger.getLogger(AppInfoDAO.class);

	//property constants
	public static final String APP_NAME = "appName";
	public static final String APP_DESCRIBE = "appDescribe";

	public Long save(AppInfo transientInstance, Session session) {
		log.debug("saving AppInfo instance");
		try {
			Long save = (Long) session.save(transientInstance);
			log.debug("save successful");
			return save;
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void update(AppInfo transientInstance, Session session) {
		log.debug("update AppInfo instance");
		try {
			session.update(transientInstance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	public void saveOrUpdate(AppInfo transientInstance, Session session) {
		log.debug("saveOrUpdate AppInfo instance");
		try {
			session.saveOrUpdate(transientInstance);
			log.debug("saveOrUpdate successful");
		} catch (RuntimeException re) {
			log.error("saveOrUpdate failed", re);
			throw re;
		}
	}

	public void addSrv(AppSrv appSrv, Session session) {
		log.debug("saveOrUpdate AppInfo instance");
		try {
			// System.out.println(111);
			session.save(appSrv);
			log.debug("saveOrUpdate successful");
		} catch (RuntimeException re) {
			log.error("saveOrUpdate failed", re);
			throw re;
		}
	}

	public void delete(AppInfo persistentInstance, Session session) {
		log.debug("deleting AppInfo instance");
		try {
			session.delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public AppInfo findById(java.lang.Long id, Session session) {
		log.debug("getting AppInfo instance with id: " + id);
		try {
			AppInfo instance = (AppInfo) session.get(
					"com.sitech.esb.hb.AppInfo", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * add by liuc
	 *
	 * @param id
	 * @param session
	 * @return
	 */
	public AppInfo findByInstanceAppName(String appName, Session session) {
		log.debug("getting AppInfo instance with id: " + appName);
		String hql = "from AppInfo aa where aa.appName = ? ";
		try {
			Query query = session.createQuery(hql).setParameter(0, appName);
			AppInfo instance = (AppInfo) query.uniqueResult();
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	// ������ˣ�sqlע������
	public AppSrv findById(java.lang.Long appId, java.lang.Long srvId,
			Session session) {
		log.debug("finding All SrvInfo instance");
		try {
			String queryString = "from AppSrv as model where model.id.srvId=? and model.id.appId=? ";
			Query queryObject = session.createQuery(queryString)
					.setParameter(0, srvId).setParameter(1, appId);
			if (queryObject.list().size() > 0) {
				return (AppSrv) queryObject.list().get(0);
			} else {
				return null;
			}

		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByExample(AppInfo instance, Session session) {
		log.debug("finding AppInfo instance by example");
		try {
			List results = session.createCriteria("com.sitech.esb.hb.AppInfo")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value,
			Session session) {
		log.debug("finding AppInfo instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from AppInfo as model where model."
					+ propertyName + "= ? order by model.appName";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public int findCountByProperty(String propertyName, Object value,
			Session session) {
		log.debug("finding AppInfo instance count with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "select count(model.appId) from AppInfo as model where model."
					+ propertyName + "= ?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, value);
			return ((Long) queryObject.uniqueResult()).intValue();
		} catch (RuntimeException re) {
			log.error("find by property AppInfo failed", re);
			throw re;
		}
	}

	public List findByAppName(Object appName, Session session) {
		return findByProperty(APP_NAME, appName, session);
	}

	public List getDefault(Session session) {
		log.debug("finding All AppInfo instance");
		try {
			String queryString = "from AppInfo as model order by model.appName";
			Query queryObject = session.createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}


	public List getServices(AppInfo app, Session session) {
		log.debug("finding All AppInfo instance");
		try {
			// String queryString =
			// "select appSrvs.srvInfo from AppInfo as model join model.appSrvs as appSrvs where appSrvs.appInfo=:app and appSrvs.srvInfo.srvStatus<>:srvStatus order by appSrvs.srvInfo.srvName";
			String queryString = "select new SrvInfo(srvInfos.srvId, srvInfos.srvName, srvInfos.srvChName) from AppInfo as model join model.srvInfos as srvInfos where model=:app order by srvInfos.srvName";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter("app", app);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List getServicesOfTree(AppInfo app, Session session,
			int srvLanguageType) {
		log.debug("finding All AppInfo instance");
		try {
			// �������ֶ�����
			String orderStr = " order by srvInfos.srvName";
			if (srvLanguageType == 1) {
				orderStr = " order by srvInfos.srvChName";
			}
			// String queryString =
			// "select appSrvs.srvInfo from AppInfo as model join model.appSrvs as appSrvs where appSrvs.appInfo=:app and appSrvs.srvInfo.srvStatus<>:srvStatus order by appSrvs.srvInfo.srvName";
			String queryString = "select new SrvInfo(srvInfos.srvId, srvInfos.srvName, srvInfos.srvChName, srvInfos.srvType) from AppInfo as model join model.srvInfos as srvInfos where model=:app "
					+ orderStr;
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter("app", app);
			List list = queryObject.list();
			return list;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List getAppInfosOfTree(ClientInfo clientInfo,
			AppFunCateg appFunCateg, Session session) {
		try {
			String queryString = "select new AppInfo(model.appId, model.appName) "
					+ " from AppInfo as model join model.clientApps ca "
					+ " where ca.clientInfo=:clientInfo and model.appFunctionCateg=:appFunCateg "
					+ "and model.appStatus.appStatusId<>:appStatusId order by model.appName";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter("clientInfo", clientInfo);
			queryObject.setParameter("appFunCateg", appFunCateg);
			queryObject.setParameter("appStatusId", new Long(
					AppSrvConstant.APP_REMOVING));
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// �������Ӧ��
	public List findByParams(String appName, String appFuncCategId,
			String clientId, Session session) {
		try {
			StringBuffer sqlBuffer = new StringBuffer();
			Map<String, Object> params = new HashMap<String, Object>();

			String sqlHead = "";
			String sqlAppName = "";
			String sqlClientInfo = "";
			String sqlFunction = "";
			String sqlStatus = " model.appStatus.appStatusId<> :app_removing";
			params.put("app_removing", new Long(AppSrvConstant.APP_REMOVING));

			if (!appName.equals("")) {
				sqlAppName = " upper(model.appName) like :appName";
				params.put("appName", "%" + appName.toUpperCase() + "%");
			}

			if (appFuncCategId.equals("") && clientId.equals("")) {
				sqlHead = "select model from AppInfo as model ";
				if (sqlAppName.equals("")) {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlStatus);
				} else {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlAppName).append(" and ")
							.append(sqlStatus);
				}
			}
			if (!appFuncCategId.equals("") && clientId.equals("")) {
				sqlHead = "select model from AppInfo as model ";
				sqlFunction = " model.appFunctionCateg.appFuncatId=:appFuncCategId";
				params.put("appFuncCategId", appFuncCategId);
				if (sqlAppName.equals("")) {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlFunction).append(" and ")
							.append(sqlStatus);
				} else {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlAppName).append(" and ")
							.append(sqlFunction).append(" and ")
							.append(sqlStatus);
				}
			}
			if (appFuncCategId.equals("") && !clientId.equals("")) {
				sqlHead = "select model from AppInfo as model join model.clientApps as clientApps ";
				sqlClientInfo = " clientApps.clientInfo.clientId =:clientId";
				params.put("clientId", Long.parseLong(clientId));
				if (sqlAppName.equals("")) {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlClientInfo).append(" and ")
							.append(sqlStatus);
				} else {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlAppName).append(" and ")
							.append(sqlClientInfo).append(" and ")
							.append(sqlStatus);
				}
			}
			if (!appFuncCategId.equals("") && !clientId.equals("")) {
				sqlHead = "select model from AppInfo as model join model.clientApps as clientApps ";
				sqlFunction = " model.appFunctionCateg.appFuncatId=:appFuncCategId";
				params.put("appFuncCategId", Long.parseLong(appFuncCategId));
				sqlClientInfo = " clientApps.clientInfo.clientId=:clientId";
				params.put("clientId", Long.parseLong(clientId));
				if (sqlAppName.equals("")) {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlFunction).append(" and ")
							.append(sqlClientInfo).append(" and ")
							.append(sqlStatus);
				} else {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlAppName).append(" and ")
							.append(sqlFunction).append(" and ")
							.append(sqlClientInfo).append(" and ")
							.append(sqlStatus);
				}
			}
			log.debug("Ӧ�����sql��" + sqlBuffer.toString());
			Query queryObject = session.createQuery(sqlBuffer.toString());
			System.out.println("---------" + sqlBuffer.toString());
			if (params != null && !params.isEmpty()) {
				for (String key : params.keySet()) {
					queryObject.setParameter(key, params.get(key));
				}
			}
			return queryObject.list();
		} catch (RuntimeException re) {
			re.printStackTrace();
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// ���ڽ�������Ӧ�÷���Ȩ��
	public List findByParams(String appName, String appFuncCategId,
			String clientId, String srvName, Session session) {
		try {
			StringBuffer sqlBuffer = new StringBuffer();
			Map<String, Object> params = new HashMap<String, Object>();

			sqlBuffer.append("select model from AppInfo as model ");
			if (!srvName.equals("")) {
				sqlBuffer.append(" join model.srvInfos as srvInfos ");
			}

			if (!clientId.equals("")) {
				sqlBuffer.append(" join model.clientApps as clientApps ");
			}

			sqlBuffer.append(" where ");
			if (!appName.equals("") || !appFuncCategId.equals("")
					|| !srvName.equals("") || !clientId.equals("")) {
				if (!appName.equals("")) {
					sqlBuffer
							.append(" upper(model.appName) like :appName  and ");
					params.put("appName", "%" + appName.toUpperCase() + "%");
				}
				if (!appFuncCategId.equals("")) {
					sqlBuffer
							.append(" model.appFunctionCateg.appFuncatId=:appFuncCategId and ");
					params.put("appFuncCategId", appFuncCategId);
				}
				if (!clientId.equals("")) {
					sqlBuffer
							.append(" clientApps.clientInfo.clientId =:clientId and ");
					params.put("clientId", clientId);
				}
				if (!srvName.equals("")) {
					sqlBuffer
							.append(" upper(srvInfos.srvName) like :srvName  and srvInfos.validFlag = 1 and ");
					params.put("srvName", "%" + srvName.toUpperCase() + "%");
				}
			}
			sqlBuffer.append(" model.appStatus.appStatusId <> :app_removeing");
			params.put("app_removeing", new Long(AppSrvConstant.APP_REMOVING));
			Query queryObject = session.createQuery(sqlBuffer.toString());
			if (params != null && !params.isEmpty()) {
				for (String key : params.keySet()) {
					queryObject.setParameter(key, params.get(key));
				}
			}
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/*
	 * //���ڽ�������Ӧ�÷���Ȩ�� public List findBaseInfoByParams(String appName, String
	 * appFuncCategId, String clientId, String currentClientId, Session session)
	 * { try { StringBuffer sqlBuffer = new StringBuffer(); String sqlHead = "";
	 * String sqlAppName = ""; String sqlClientInfo = ""; String sqlFunction =
	 * ""; String sqlStatus = " model.appStatus.appStatusId<>" +
	 * com.sitech.esb.util.AppSrvConstant.APP_REMOVING; if(!appName.equals(""))
	 * { sqlAppName = " upper(model.appName) like '%" + appName.toUpperCase() +
	 * "%' "; } sqlHead =
	 * "select new AppInfo(model.appId, model.appName, model.appStatus, model.appFunctionCateg) from AppInfo as model join model.clientApps as clientApps "
	 * + " where clientApps.clientInfo.clientId != " + currentClientId;
	 * if(appFuncCategId.equals("") && clientId.equals("")) {
	 * if(sqlAppName.equals("")) {
	 * sqlBuffer.append(sqlHead).append(" and ").append(sqlStatus); } else {
	 * sqlBuffer
	 * .append(sqlHead).append(" and ").append(sqlAppName).append(" and "
	 * ).append(sqlStatus); } } if(!appFuncCategId.equals("") &&
	 * clientId.equals("")) { sqlFunction =
	 * " model.appFunctionCateg.appFuncatId=" + appFuncCategId ;
	 * if(sqlAppName.equals("")) {
	 * sqlBuffer.append(sqlHead).append(" and ").append
	 * (sqlFunction).append(" and ").append(sqlStatus); } else {
	 * sqlBuffer.append
	 * (sqlHead).append(" and ").append(sqlAppName).append(" and "
	 * ).append(sqlFunction).append(" and ").append(sqlStatus); } }
	 * if(appFuncCategId.equals("") && !clientId.equals("")) { sqlClientInfo =
	 * " clientApps.clientInfo.clientId =" + clientId ;
	 * if(sqlAppName.equals("")) {
	 * sqlBuffer.append(sqlHead).append(" and ").append
	 * (sqlClientInfo).append(" and ").append(sqlStatus); } else {
	 * sqlBuffer.append
	 * (sqlHead).append(" and ").append(sqlAppName).append(" and "
	 * ).append(sqlClientInfo).append(" and ").append(sqlStatus); } }
	 * if(!appFuncCategId.equals("") && !clientId.equals("")) { sqlFunction =
	 * " model.appFunctionCateg.appFuncatId=" + appFuncCategId ; sqlClientInfo =
	 * " clientApps.clientInfo.clientId =" + clientId ;
	 * if(sqlAppName.equals("")) {
	 * sqlBuffer.append(sqlHead).append(" and ").append
	 * (sqlFunction).append(" and "
	 * ).append(sqlClientInfo).append(" and ").append(sqlStatus); } else {
	 * sqlBuffer
	 * .append(sqlHead).append(" and ").append(sqlAppName).append(" and "
	 * ).append
	 * (sqlFunction).append(" and ").append(sqlClientInfo).append(" and "
	 * ).append(sqlStatus); } } Query queryObject =
	 * session.createQuery(sqlBuffer.toString()); return queryObject.list(); }
	 * catch (RuntimeException re) { log.error("find by property name failed",
	 * re); throw re; } }
	 */
	// ���ڽ�������Ӧ�÷���Ȩ��
	public List findBaseInfoByParams(String appName, String appFuncCategId,
			String clientId, Session session) {
		try {
			StringBuffer sqlBuffer = new StringBuffer();
			Map<String, Object> params = new HashMap<String, Object>();

			String sqlHead = "";
			String sqlAppName = "";
			String sqlClientInfo = "";
			String sqlFunction = "";
			String sqlStatus = " model.appStatus.appStatusId<> :app_removing";
			params.put("app_removing", new Long(AppSrvConstant.APP_REMOVING));

			if (!appName.equals("")) {
				sqlAppName = " upper(model.appName) like :appName";
				params.put("appName", "%" + appName.toUpperCase() + "%");
			}

			if (appFuncCategId.equals("") && clientId.equals("")) {
				sqlHead = "select new AppInfo(model.appId, model.appName, model.appStatus, model.appFunctionCateg) from AppInfo as model ";
				if (sqlAppName.equals("")) {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlStatus);
				} else {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlAppName).append(" and ")
							.append(sqlStatus);
				}
			}
			if (!appFuncCategId.equals("") && clientId.equals("")) {
				sqlHead = "select new AppInfo(model.appId, model.appName, model.appStatus, model.appFunctionCateg) from AppInfo as model ";
				sqlFunction = " model.appFunctionCateg.appFuncatId=:appFuncCategId";
				params.put("appFuncCategId", appFuncCategId);
				if (sqlAppName.equals("")) {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlFunction).append(" and ")
							.append(sqlStatus);
				} else {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlAppName).append(" and ")
							.append(sqlFunction).append(" and ")
							.append(sqlStatus);
				}
			}
			if (appFuncCategId.equals("") && !clientId.equals("")) {
				sqlHead = "select new AppInfo(model.appId, model.appName, model.appStatus, model.appFunctionCateg) from AppInfo as model join model.clientApps as clientApps ";
				sqlClientInfo = " clientApps.clientInfo.clientId =:clientId";
				params.put("clientId", clientId);
				if (sqlAppName.equals("")) {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlClientInfo).append(" and ")
							.append(sqlStatus);
				} else {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlAppName).append(" and ")
							.append(sqlClientInfo).append(" and ")
							.append(sqlStatus);
				}
			}
			if (!appFuncCategId.equals("") && !clientId.equals("")) {
				sqlHead = "select new AppInfo(model.appId, model.appName, model.appStatus, model.appFunctionCateg) from AppInfo as model join model.clientApps as clientApps ";
				sqlFunction = " model.appFunctionCateg.appFuncatId=:appFuncCategId";
				params.put("appFuncCategId", appFuncCategId);
				sqlClientInfo = " clientApps.clientInfo.clientId =:clientId";
				params.put("clientId", clientId);
				if (sqlAppName.equals("")) {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlFunction).append(" and ")
							.append(sqlClientInfo).append(" and ")
							.append(sqlStatus);
				} else {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlAppName).append(" and ")
							.append(sqlFunction).append(" and ")
							.append(sqlClientInfo).append(" and ")
							.append(sqlStatus);
				}
			}
			Query queryObject = session.createQuery(sqlBuffer.toString());
			if (params != null && !params.isEmpty()) {
				for (String key : params.keySet()) {
					queryObject.setParameter(key, params.get(key));
				}
			}
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	// Ӧ�ã�Ӧ�ù����򣬽�����û�������֤��������������������·���
	public List findSrvByParams(String appId, String appFuncCategId,
			String clientId, Session session) {
		try {
			StringBuffer sqlBuffer = new StringBuffer();
			Map<String, Object> params = new HashMap<String, Object>();
			String sqlHead = "";
			String sqlAppId = "";
			String sqlClientInfo = "";
			String sqlFunction = "";
			String sqlStatus = "srvInfos.srvStatus.srvStatusId<> :srv_removing";
			params.put("srv_removing",
					(long) com.sitech.esb.util.AppSrvConstant.SRV_REMOVING);
			if (!appId.equals("")) {
				sqlAppId = " model.appId=:appId";
				long appid = Long.parseLong(appId);
				params.put("appId", appid);
			}

			if (appFuncCategId.equals("") && clientId.equals("")) {
				sqlHead = "select srvInfos from AppInfo as model join model.srvInfos as srvInfos ";
				if (sqlAppId.equals("")) {
					sqlBuffer.append(sqlHead);
				} else {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlAppId).append(" and ").append(sqlStatus);
				}
			}
			if (!appFuncCategId.equals("") && clientId.equals("")) {
				sqlHead = "select srvInfos from AppInfo as model join model.srvInfos as srvInfos ";
				sqlFunction = " model.appFunctionCateg.appFuncatId=:appFuncCategId";
				long appFuncCategid = Long.parseLong(appFuncCategId);
				params.put("appFuncCategId", appFuncCategid);
				if (sqlAppId.equals("")) {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlFunction).append(" and ")
							.append(sqlStatus);
				} else {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlAppId).append(" and ")
							.append(sqlFunction).append(" and ")
							.append(sqlStatus);
				}
			}
			if (appFuncCategId.equals("") && !clientId.equals("")) {
				sqlHead = "select srvInfos from AppInfo as model join model.srvInfos as srvInfos join model.clientApps as clientApps ";
				sqlClientInfo = " clientApps.clientInfo.clientId =:clientId";
				long clientid = Long.parseLong(clientId);
				params.put("clientId", clientid);
				if (sqlAppId.equals("")) {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlClientInfo).append(" and ")
							.append(sqlStatus);
				} else {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlAppId).append(" and ")
							.append(sqlClientInfo).append(" and ")
							.append(sqlStatus);
				}
			}
			if (!appFuncCategId.equals("") && !clientId.equals("")) {
				sqlHead = "select srvInfos from AppInfo as model join model.srvInfos as srvInfos join model.clientApps as clientApps ";
				sqlFunction = " model.appFunctionCateg.appFuncatId="
						+ appFuncCategId;
				sqlClientInfo = " clientApps.clientInfo.clientId =:clientId";
				long clientid = Long.parseLong(clientId);
				params.put("clientId", clientid);
				if (sqlAppId.equals("")) {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlFunction).append(" and ")
							.append(sqlClientInfo).append(" and ")
							.append(sqlStatus);
				} else {
					sqlBuffer.append(sqlHead).append(" where ")
							.append(sqlAppId).append(" and ")
							.append(sqlFunction).append(" and ")
							.append(sqlClientInfo).append(" and ")
							.append(sqlStatus);
				}
			}
			Query queryObject = session.createQuery(sqlBuffer.toString());
			if (params != null && !params.isEmpty()) {
				for (String key : params.keySet()) {
					queryObject.setParameter(key, params.get(key));
				}
			}
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAppDescribe(Object appDescribe, Session session) {
		return findByProperty(APP_DESCRIBE, appDescribe, session);
	}

	public AppInfo merge(AppInfo detachedInstance, Session session) {
		log.debug("merging AppInfo instance");
		try {
			AppInfo result = (AppInfo) session.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(AppInfo instance, Session session) {
		log.debug("attaching dirty AppInfo instance");
		try {
			session.saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(AppInfo instance, Session session) {
		log.debug("attaching clean AppInfo instance");
		try {
			session.lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List getAppCategs(AppInfo appInfo, Session session) {
		try {
			String queryString = "select acs from AppInfo as model join model.appCategs as acs where model=? order by acs.appDimension.appDimenId";
			// String queryString =
			// "select apps from AppCateg as model join model.apps as apps where model.appCategId=? order by apps.appName";
			Query queryObject = session.createQuery(queryString);
			// queryObject.setParameter(0, appCateg.getAppCategId());
			queryObject.setParameter(0, appInfo);
			List list = queryObject.list();
			return list;
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List getClients(AppInfo appInfo, Session session) {
		try {
			String queryString = "select clientApps.clientInfo from AppInfo as model join model.clientApps clientApps where clientApps.appInfo=:appInfo order by clientApps.clientInfo.clientId";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter("appInfo", appInfo);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}
