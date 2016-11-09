package com.kusion.vote;

import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ApplicationConfigTest {

    @Test
    public void bootstrapAppFromXml() {
        ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
        assertNotNull(context);
    }
}
