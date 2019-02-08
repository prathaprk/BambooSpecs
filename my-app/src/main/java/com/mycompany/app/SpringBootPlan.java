package com.mycompany.app;

import java.util.concurrent.TimeUnit;

import com.atlassian.bamboo.specs.api.BambooSpec;
import com.atlassian.bamboo.specs.api.builders.plan.Job;
import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.builders.plan.Stage;
import com.atlassian.bamboo.specs.api.builders.plan.artifact.Artifact;
import com.atlassian.bamboo.specs.api.builders.project.Project;
import com.atlassian.bamboo.specs.builders.repository.git.GitRepository;
import com.atlassian.bamboo.specs.builders.repository.git.UserPasswordAuthentication;
import com.atlassian.bamboo.specs.builders.task.MavenTask;
import com.atlassian.bamboo.specs.builders.task.VcsCheckoutTask;
import com.atlassian.bamboo.specs.builders.trigger.RepositoryPollingTrigger;
import com.atlassian.bamboo.specs.util.BambooServer;

@BambooSpec
public class SpringBootPlan
{

    /**
   * Run main to publish plan on Bamboo
   */
  public static void main(final String[] args) throws Exception {
   System.out.println("Starting to create a plan in SpringBootPlan");
    //By default credentials are read from the '.credentials' file.
    BambooServer bambooServer = new BambooServer("http://localhost:8085");

    Plan plan = new SpringBootPlan().createPlan();

    bambooServer.publish(plan);
    System.out.println("completed plan creation in SpringBootPlan");
  }

  Project project() {
    return new Project()
        .name("SpringBootApp")
        .description("SpringBoot Application")
        .key("SBA");
  }

  Plan createPlan() {
    return new Plan(
        project(),
        "SpringBootDev", "DEV")
        .description("Plan for Dev environment")
        .planRepositories(new GitRepository()
            .url("https://github.com/prathaprk/spring-boot-maven-example-helloworld.git")
            .name("spring-boot-maven-example-helloworld")
            .authentication(new UserPasswordAuthentication("prathaprk").password("Lenis@2018"))
            //.withoutAuthentication()
            .branch("master"))
            .triggers(new RepositoryPollingTrigger()
            .description("Polling")
            .pollEvery(30, TimeUnit.MINUTES))        
        .stages(
            new Stage("DEV")
                .jobs(new Job("Build", "BUILD")
                    .tasks(
                        new VcsCheckoutTask()
                        .description("Checkout")
                        .addCheckoutOfDefaultRepository(),
                        new MavenTask()
                        .goal("clean install")
                        .hasTests(false)
                        .version3()
                        .jdk("JDK 1.8")
                        .executableLabel("Maven 3.6")                                      
                    )
                    .artifacts(new Artifact("WAR")
                    .location("target")
                    .copyPattern("*.war")
                    )
                    
                )
        );
  }
}
