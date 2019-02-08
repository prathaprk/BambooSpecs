package com.mycompany.app;

import com.atlassian.bamboo.specs.api.builders.plan.Plan;
import com.atlassian.bamboo.specs.api.exceptions.PropertiesValidationException;
import com.atlassian.bamboo.specs.api.util.EntityPropertiesBuilders;


import org.junit.Test;

public class BambooTest {
    //@Test
    public void checkPlanOffline() throws PropertiesValidationException {
        Plan plan = new Bambooplan().createPlan();

        EntityPropertiesBuilders.build(plan);
    }

    @Test
    public void springBootPlanOffline() throws PropertiesValidationException {
        Plan plan = new SpringBootPlan().createPlan();

        EntityPropertiesBuilders.build(plan);
    }
}
