package com.ecommerce.auth.workflow;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;
import java.util.zip.ZipInputStream;

@SpringBootTest
class AuthWorkflowApplicatonTests {
    @Autowired
    RuntimeService runtimeService;

    @Autowired
    RepositoryService repositoryService;

    @Autowired
    HistoryService historyService;

    @Autowired
    TaskService taskService;

    @Test
    void contextLoads() {
    }

    @Test
    public void addBusinessKey() {
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myEvection", "1001");
    }

    /**
     * 流程定义信息查询
     */
    @Test
    public void queryProcessDefinition() {
        ProcessDefinitionQuery definitionQuery = repositoryService.createProcessDefinitionQuery();
        List<ProcessDefinition> definitionList = definitionQuery.processDefinitionKey("myEvection")
                .orderByProcessDefinitionVersion()
                .desc()
                .list();
    }

    /**
     * 查看历史信息
     */
    @Test
    public void findHistoryInfo() {
        HistoricActivityInstanceQuery instanceQuery = historyService.createHistoricActivityInstanceQuery();
        List<HistoricActivityInstance> activityInstances = instanceQuery
                .processInstanceId("2501")
                .list();
    }

    /**
     * 全部挂起、激活
     */
    @Test
    public void activateOrSuspend() {
        // 1.查询流程定义，获取流程定义 查询对象
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("myEvetion")
                .singleResult();
        // 2.获取当前流程定义的实例是否都是挂起状态
        boolean suspended = processDefinition.isSuspended();
        // 3.获取流程定义的id
        String definitionId = processDefinition.getId();
        // 4.如果是挂起状态，改为激活状态
        if (suspended) {
            // 如果是挂起，可以执行激活的操作，参数1：流程定义id  参数2：是否激活  参数3：激活时间
            repositoryService.activateProcessDefinitionById(definitionId, true, null);
        } else {
            // 如果是激活状态，改为挂起状态，参数1：流程定义id 参数2：是否暂停 参数3：暂停的时间
            repositoryService.suspendProcessDefinitionById(definitionId, true, null);
        }
    }

    /**
     * 单个流程实例挂起与激活
     */
    @Test
    public void suspendSingleProcessInstance() {
        // 查询流程实例
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId("15001").singleResult();

        // 得到当前流程实例是否为暂停状态
        boolean suspended = processInstance.isSuspended();
        // 流程实例id
        String processInstanceId = processInstance.getId();

        // 判断是否暂停
        if (suspended) {
            runtimeService.activateProcessInstanceById(processInstanceId);
        } else {
            runtimeService.suspendProcessInstanceById(processInstanceId);
        }
    }

    /**
     * 删除流程部署信息
     * act_ge_bytearray
     * act_re_deployment
     * act_re_procdef
     * 以前的流程如果没有完成，想要删除的话需要使用特殊方式，原理就是级联删除
     */
    @Test
    public void deleteDeployMent() {
        String deploymentId = "12501";
        // 参数2 表示是否级联删除
        repositoryService.deleteDeployment(deploymentId, true);
    }

    /**
     * 完成任务
     */
    @Test
    public void completeTask() {
        // 任务id
        String taskId = "15005";
        // 任务负责人
        String assignee = "战三";
        // 完成任务前，需要校验该负责人是否可以完成当前任务
        // 校验方法：根据任务id和任务负责人查询当前任务，如果查到该用户有权限，就完成
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .taskAssignee(assignee)
                .singleResult();
        if (null != task) {
            taskService.complete(taskId);
        }
    }

    /**
     * 下载流程资源文件
     *
     * @throws Exception
     */
    @Test
    public void downloadProcessResource() throws Exception {
        // 1.查询流程定义，获取流程定义 查询对象
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionKey("myEvetion")
                .singleResult();
        String deploymentId = processDefinition.getDeploymentId();
        // 2.获取png图片的值
        String pngName = processDefinition.getDiagramResourceName();
        InputStream pngInput = repositoryService.getResourceAsStream(deploymentId, pngName);
        // 3.获取bpmn的流
        String bpmnName = processDefinition.getResourceName();
        InputStream bpmnInput = repositoryService.getResourceAsStream(deploymentId, bpmnName);
        // 4.构造outputstream流
        File pngFile = new File("d:/evectionflowo1.png");
        File bpmnFile = new File(("d:/evectionflow01.bpmn"));
        FileOutputStream pngOutputStream = new FileOutputStream(pngFile);
        FileOutputStream bpmnOutStream = new FileOutputStream(bpmnFile);
        // 5.输入流、输出流的转换
        IOUtils.copy(pngInput, pngOutputStream);
        IOUtils.copy(bpmnInput, bpmnOutStream);
        // 6.关闭流
        pngOutputStream.close();
        bpmnOutStream.close();
        pngInput.close();
        bpmnInput.close();
    }

    /**
     * 个人任务查询
     */
    @Test
    public void findPersonalTaskList() {
        // 根据流程key和任务负责人查询任务
        List<Task> tasks = taskService.createTaskQuery()
                .processDefinitionKey("myEvection")
                .taskAssignee("zhangsan")
                .list();

    }

    /**
     * 拾取组任务
     */
    @Test
    public void claimTask() {
        // 要拾取的任务id
        String taskId = "6302";
        // 任务候选人id
        String userId = "lisi";
        // 拾取任务
        // 即使该用户不是候选人也能拾取（建议拾取时校验是否有资格）
        // 校验该用户有没有拾取任务的资格
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .taskCandidateUser(userId) // 根据候选人查询
                .singleResult();
        if (null != task) {
            // 拾取任务
            taskService.claim(taskId, userId);
        }
    }

    /**
     * 组任务查询
     */
    @Test
    public void findGroupTaskList() {
        // 流程定义的key
        String key = "testCandidate";
        // 任务候选人
        String candidateUser = "wangwu";
        // 组任务查询
        List<Task> tasks = taskService.createTaskQuery()
                .processDefinitionKey(key)
                .taskCandidateUser(candidateUser) // 根据候选人查询任务
                .list();

    }

    /**
     * 交接任务
     */
    @Test
    public void testAssigneeToCandidateUser() {
        // 当前任务id
        String taskId = "67502";
        // 任务负责人
        String assignee = "wangwu";
        // 任务候选人
        String candidateUser = "lisi";
        // 根据key和负责人来查询任务
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .taskAssignee(assignee) // 根据负责人查询
                .singleResult();
        if (null != task) {
            // 交接任务，将责任人设置为候选人
            taskService.setAssignee(taskId, candidateUser);
        }

    }


    /**
     * 归还任务
     */
    @Test
    public void testAssigneeToGroup() {
        // 当前任务id
        String taskId = "67502";
        // 任务负责人
        String assignee = "wangwu";
        // 任务候选人
        String candidateUser = "lisi";
        // 根据key和负责人来查询任务
        Task task = taskService.createTaskQuery()
                .taskId(taskId)
                .taskAssignee(assignee) // 根据负责人查询
                .singleResult();
        if (null != task) {
            // 归还任务，将责任人设置为null
            taskService.setAssignee(taskId, null);
        }

    }

    /**
     * 使用zip包进行批量的部署
     */
    @Test
    public void deployProcessByZip() {
        // 读取资源包文件，构造inputstream
        InputStream inputStream = this.getClass()
                .getClassLoader()
                .getResourceAsStream("bpmn/evection.zip");
        // 用inputstream构造ZipInputStream
        ZipInputStream zipInputStream = new ZipInputStream(inputStream);
        // 使用压缩包的流进行流程的部署
        Deployment deploy = repositoryService.createDeployment()
                .addZipInputStream(zipInputStream)
                .deploy();
    }


}
