//package com.pro.snowball.common.service;
//
//import com.baomidou.mybatisplus.core.mapper.BaseMapper;
//import com.pro.common.modules.api.dependencies.CommonConst;
//import com.pro.framework.mybatisplus.BaseService;
//import com.pro.snowball.api.model.db.ExecuteOrderStep;
//import com.pro.snowball.common.dao.ExecuteOrderStepDao;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * 执行订单的步骤服务
// */
//@Service
//@Slf4j
//public class ExecuteOrderStepService extends BaseService<ExecuteOrderStepDao, ExecuteOrderStep> {
//    public List<ExecuteOrderStep> getActiveList(ExecuteOrderStep executeOrderStep) {
//        return this.lambdaQuery().setEntity(executeOrderStep)
//                .list();
//    }
//}
