package com.ecommerce.auth.workflow.service;

import org.activiti.engine.RuntimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkflowService {
    @Autowired
    RuntimeService runtimeService;


}
