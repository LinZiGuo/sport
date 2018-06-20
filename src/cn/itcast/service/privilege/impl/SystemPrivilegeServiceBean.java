package cn.itcast.service.privilege.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import cn.itcast.bean.privilege.SystemPrivilege;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.privilege.SystemPrivilegeService;
@Service
public class SystemPrivilegeServiceBean extends DaoSupport<SystemPrivilege> implements SystemPrivilegeService {

	@Override
	public void batchSave(List<SystemPrivilege> privileges) {
		for(SystemPrivilege p : privileges){
			save(p);
		}
	}

}
