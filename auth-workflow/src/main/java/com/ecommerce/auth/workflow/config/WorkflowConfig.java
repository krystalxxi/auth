package com.ecommerce.auth.workflow.config;

import org.activiti.engine.*;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WorkflowConfig {
    @Bean
    ProcessEngine processEngine(){
        return ProcessEngines.getDefaultProcessEngine();
    }

    @Bean
    RuntimeService runtimeService(){
        return processEngine().getRuntimeService();
    }

    @Bean
    RepositoryService repositoryService(){
        return processEngine().getRepositoryService();
    }

    @Bean
    TaskService taskService(){
        return processEngine().getTaskService();
    }

    @Bean
    HistoryService historyService(){
        return processEngine().getHistoryService();
    }
}
