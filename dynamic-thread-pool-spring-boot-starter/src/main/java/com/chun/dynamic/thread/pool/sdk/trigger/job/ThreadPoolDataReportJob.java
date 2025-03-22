package com.chun.dynamic.thread.pool.sdk.trigger.job;

import com.alibaba.fastjson.JSON;
import com.chun.dynamic.thread.pool.sdk.domain.IDynamicThreadPoolService;
import com.chun.dynamic.thread.pool.sdk.domain.model.entity.ThreadPoolConfigEntity;
import com.chun.dynamic.thread.pool.sdk.registry.IRegistry;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import java.util.List;

/**
 * @description: 线程池数据上报任务
 * @author: chun
 **/

public class ThreadPoolDataReportJob {

    private Logger logger = LoggerFactory.getLogger(ThreadPoolDataReportJob.class);

    private final IDynamicThreadPoolService dynamicThreadPoolService;

    private final IRegistry registry;

    public ThreadPoolDataReportJob(IDynamicThreadPoolService dynamicThreadPoolService, IRegistry registry) {
        this.dynamicThreadPoolService = dynamicThreadPoolService;
        this.registry = registry;
    }

    @Scheduled(cron = "0/20 * * * * ?")
    public void execReportThreadPoolList(){
        List<ThreadPoolConfigEntity> threadPoolConfigEntities = dynamicThreadPoolService.queryThreadPoolList();
        registry.reportThreadPool(threadPoolConfigEntities);
        logger.info("动态线程池，上报线程信息：{}", JSON.toJSONString(threadPoolConfigEntities));

        for(ThreadPoolConfigEntity threadPoolConfigEntity : threadPoolConfigEntities){
            registry.reportThreadPoolConfigParameter(threadPoolConfigEntity);
            logger.info("动态线程池，上报线程配置：{}", JSON.toJSONString(threadPoolConfigEntity));
        }
    }
}
