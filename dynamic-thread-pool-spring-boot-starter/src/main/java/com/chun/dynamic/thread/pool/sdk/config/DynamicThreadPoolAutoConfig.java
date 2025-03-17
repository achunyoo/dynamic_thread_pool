package com.chun.dynamic.thread.pool.sdk.config;

import com.alibaba.fastjson2.JSON;
import com.chun.dynamic.thread.pool.sdk.domain.DynamicThreadPoolService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description: 动态配置入口
 * @author: chun
 **/
@Configuration
public class DynamicThreadPoolAutoConfig {
    private final Logger logger = LoggerFactory.getLogger(DynamicThreadPoolAutoConfig.class);

    @Bean("dynamicThreadPoolService")
    public DynamicThreadPoolService dynamicThreadPoolService(ApplicationContext applicationContext, Map<String,ThreadPoolExecutor> threadPoolExecutorMap) {
        String applicationName = applicationContext.getEnvironment().getProperty("spring.application.name");

        if(StringUtils.isBlank(applicationName)){
            applicationName = "缺省的";
            logger.warn("动态线程池，启动提示。SpringBoot应用未配置 spting.application.name 无法获取应用名称");
        }

        return new DynamicThreadPoolService(applicationName,threadPoolExecutorMap);
    }
}
