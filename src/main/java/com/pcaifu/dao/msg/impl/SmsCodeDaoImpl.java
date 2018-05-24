package com.pcaifu.dao.msg.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.paicaifu.news.entity.UcSmsCode;
import com.pcaifu.commonutils.base.JpaBaseDao;
import com.pcaifu.dao.msg.SmsCodeDao;

@Repository
public class SmsCodeDaoImpl implements SmsCodeDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private JpaBaseDao jpaBaseDao;

	@Override
	public void saveSmsCode(UcSmsCode smsCode) {
		jpaBaseDao.saveOrUpdate(smsCode);
	}

	@Override
	public void useSmsCode(UcSmsCode smsCode) throws Exception {
		jpaBaseDao.update(smsCode);
	}

	@Override
	public UcSmsCode querySms(UcSmsCode smsCode) throws Exception {

		String hql = " from UcSmsCode  where  ucSmsSeq=?  and  ucSmsType=?  and  ucPhoneNo=?   and  ucSmsCode=?   ";
		Object[] values = { smsCode.getUcSmsSeq(), smsCode.getUcSmsType(), smsCode.getUcPhoneNo(),
				smsCode.getUcSmsCode() };
		UcSmsCode ucSmsCode = jpaBaseDao.findSingle(hql, values);
		return ucSmsCode;
	}

}
