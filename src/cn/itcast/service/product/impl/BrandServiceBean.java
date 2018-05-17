package cn.itcast.service.product.impl;

import org.springframework.stereotype.Service;

import cn.itcast.bean.product.Brand;
import cn.itcast.service.base.DaoSupport;
import cn.itcast.service.product.BrandService;
@Service
public class BrandServiceBean extends DaoSupport<Brand> implements BrandService {

}
