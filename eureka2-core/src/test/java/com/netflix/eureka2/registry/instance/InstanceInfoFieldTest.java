package com.netflix.eureka2.registry.instance;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;

/**
 * @author David Liu
 */
public class InstanceInfoFieldTest {

    @Test(timeout = 60000)
    public void shouldHaveSameNumberOfFieldsAsInstanceInfoVariablesWithGetters() throws Exception {
        Field[] allFields = InstanceInfo.class.getDeclaredFields();
        Set<String> expectedFields = new HashSet<String>();
        for (Field field : allFields) {
            if (!field.isSynthetic()) {
                expectedFields.add(field.getName());
            }
        }

        // remove non-settable fields
        expectedFields.remove("id");
        expectedFields.remove("version");

        Field[] instanceInfoFields = InstanceInfoField.class.getFields();  // get only public ones
        Set<String> actualFields = new HashSet<String>();
        for (Field field : instanceInfoFields) {
            InstanceInfoField iif = (InstanceInfoField) field.get(null);
            String name = iif.getFieldName().name();
            actualFields.add(Character.toLowerCase(name.charAt(0)) + name.substring(1));
        }

        assertThat(actualFields.size(), equalTo(expectedFields.size()));
        assertThat(actualFields, containsInAnyOrder(expectedFields.toArray()));
    }
}
