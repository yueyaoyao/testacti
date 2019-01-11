package com.acti.demo.acti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class activitiTest {
    /**
     * 1，部署流程
     * 2，启动流程实例
     * 3，请假人发出请假申请
     * 4，班主任查看任务
     * 5，班主任审批
     * 6，最终的教务处Boss审批
     */

    @Test
    public void creatActivitiTask(){
        ProcessEngine processEngine= ProcessEngines.getDefaultProcessEngine();
        processEngine.getRepositoryService()
                .createDeployment()
                .addClasspathResource("test.bpmn")
                .addClasspathResource("test.png")
                .deploy();
    }

    //启动实例流程
    @Test
    public void testSatartProcessInstance(){
        ProcessEngine processEngine=ProcessEngines.getDefaultProcessEngine();
        processEngine.getRuntimeService().startProcessInstanceById("test:1:4");
    }
    /**
     * 完成请假申请
     */
    @Test
    public void testQingjia(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("104"); //查看act_ru_task表
    }

    /**
     * 小明学习的班主任小毛查询当前正在执行任务
     */
    @Test
    public void testQueryTask(){
        //下面代码中的小毛，就是我们之前设计那个流程图中添加的班主任内容
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        List<Task> tasks = processEngine.getTaskService()
                .createTaskQuery()
                .taskAssignee("小毛")
                .list();
        for (Task task : tasks) {
            System.out.println(task.getName());
        }
    }

    /**
     * 班主任小毛完成任务
     */
    @Test
    public void testFinishTask_manager(){
        ProcessEngine engine = ProcessEngines.getDefaultProcessEngine();
        engine.getTaskService()
                .complete("202"); //查看act_ru_task数据表
    }

    /**
     * 教务处的大毛完成的任务
     */
    @Test
    public void testFinishTask_Boss(){
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
        processEngine.getTaskService()
                .complete("302");  //查看act_ru_task数据表
    }



}