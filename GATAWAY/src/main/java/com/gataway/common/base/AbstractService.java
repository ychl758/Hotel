/**
 *
 */
package com.gataway.common.base;

import com.gataway.common.jdbc.JdbcDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 */
public abstract class AbstractService {
	private static final Logger log = LoggerFactory.getLogger(AbstractService.class);

	@Autowired
	protected JdbcDao jdbcDao;

}
