package com.hodanet.common.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hodanet.common.spring.SpringContextUtil;
import com.hodanet.yuma.service.YumaOrderService;
import com.hodanet.yuma.service.impl.YumaOrderServiceImpl;

public class HodanetServletContextListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("HodanetServletContextListener contextInitialized");
        SpringContextUtil.setServletContext(arg0.getServletContext());
    }

}
