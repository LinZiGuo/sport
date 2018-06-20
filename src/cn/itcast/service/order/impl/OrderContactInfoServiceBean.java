package cn.itcast.service.order.impl;

import org.springframework.stereotype.Service;

import cn.itcast.bean.order.OrderContactInfo;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.order.OrderContactInfoService;
@Service
public class OrderContactInfoServiceBean extends DaoSupport<OrderContactInfo> implements OrderContactInfoService {

}
