package com.hodanet.common.dao;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;

public class MySQL5LocalDialect extends MySQL5Dialect{
	
	/**
	 * 注册一个中文排序方法给hql
	 */
	@SuppressWarnings("deprecation")
	public MySQL5LocalDialect() {
		super();
		registerFunction("convert", new SQLFunctionTemplate(Hibernate.STRING, "convert(?1 using ?2)"));
	}
}
