package tot.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@Component
public class test {

    @Autowired
    private ApplicationContext applicationContext;

    public void printBeans() {
        String[] beans = applicationContext.getBeanNamesForType(tot.dao.CourseDAO.class);
        System.out.println("CourseDAO beans: " + Arrays.toString(beans));
    }

    public void printAllBeans() {
        String[] allBeanNames = applicationContext.getBeanDefinitionNames();
        System.out.println("All registered beans: " + Arrays.toString(allBeanNames));
    }
}
