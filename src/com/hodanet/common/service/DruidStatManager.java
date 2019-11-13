package com.hodanet.common.service;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.alibaba.druid.stat.JdbcStatManager;

/**
 * @author lance.lengcs
 * @version 2012-7-23 2:56:52
 * 
 * <pre>
 *    druid SQLִм(ͨjconsole鿴sqlִ).
 *    üҳurl {$rootpath}/druid/index.html
 * </pre>
 */
// TODO @Component עЧļ
@Component
public class DruidStatManager {

    private static final Logger logger = Logger.getLogger(DruidStatManager.class);

    static {
        try {
            ManagementFactory.getPlatformMBeanServer().registerMBean(JdbcStatManager.getInstance(),
                                                                     new ObjectName(
                                                                                    "com.alibaba.druid:type=JdbcStatManager"));
        } catch (InstanceAlreadyExistsException e) {
            logger.error("DruidStatManager register error! ", e);
        } catch (MBeanRegistrationException e) {
            logger.error("DruidStatManager register error! ", e);
        } catch (NotCompliantMBeanException e) {
            logger.error("DruidStatManager register error! ", e);
        } catch (MalformedObjectNameException e) {
            logger.error("DruidStatManager register error! ", e);
        } catch (NullPointerException e) {
            logger.error("DruidStatManager register error! ", e);
        }
    }
}
