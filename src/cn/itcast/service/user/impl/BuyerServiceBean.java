package cn.itcast.service.user.impl;

import org.springframework.stereotype.Service;
import cn.itcast.bean.user.Buyer;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.user.BuyerService;

@Service
public class BuyerServiceBean extends DaoSupport<Buyer> implements BuyerService {

}
