package cn.itcast.service.order.impl;

import org.springframework.stereotype.Service;

import cn.itcast.bean.order.Message;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.order.MessageService;
@Service
public class MessageServiceBean extends DaoSupport<Message> implements MessageService {

}
