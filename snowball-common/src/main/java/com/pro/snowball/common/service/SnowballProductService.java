package com.pro.snowball.common.service;

import com.pro.framework.mybatisplus.BaseService;
import com.pro.snowball.api.model.db.SnowballProduct;
import com.pro.snowball.common.dao.SnowballProductDao;
import org.springframework.stereotype.Service;

@Service
public class SnowballProductService extends BaseService<SnowballProductDao, SnowballProduct> {
}
