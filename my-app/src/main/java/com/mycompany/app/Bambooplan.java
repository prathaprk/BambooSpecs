package com.mycompany.app;

import com.atlassian.bamboo.specs.api.BambooSpec;
import com.atlassian.bamboo.specs.api.builders.plan.Job;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.builders.plan.Stage;
import com.atlassian.bamboo.specs.api.builders.project.Project;
import com.atlassian.bamboo.specs.builders.task.ScriptTask;
import com.atlassian.bamboo.specs.util.BambooServer;

/**
 * Hello world!
 *
 */
//@BambooSpec
public class Bambooplan 
{

    /**
   * Run main to publish plan on Bamboo
   */
  public static void main(final String[] args) throws Exception {
   System.out.println("Starting to create a plan in bamboo");
    //By default credentials are read from the '.credentials' file.
    BambooServer bambooServer = new BambooServer("http://localhost:8085");

    Plan plan = new Bambooplan().createPlan();

    bambooServer.publish(plan);
    System.out.println("completed plan creation in bamboo");
  }

  Project project() {
    return new Project()
        .name("Sample Bamboo Project")
        .description("This is a sample Bamboo project")
        .key("SAPR");
  }

  Plan createPlan() {
    return new Plan(
        project(),
        "Plan Name", "PLANKEY")
        .description("Plan created from (enter repository url of your plan)")
        .stages(
            new Stage("Test Stage")
                .jobs(new Job("Build and Run", "RUN")
                    .tasks(
                        new ScriptTask().inlineBody("echo Hello World")
                    )
                )
        );
  }
}
