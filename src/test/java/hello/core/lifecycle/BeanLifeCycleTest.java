package hello.core.lifecycle;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class BeanLifeCycleTest {

    @Test
    public void lifeCycleTest() {
        // ApplicationContext에서는 Connection close를 제공하지 않음.
        // 그래서 하위 ConfigurableApplicationContext를 사용.
        // 그 하위에는 AnnotationConfigApplicationContext가 있어서 해당 객체 생성 가능.
        ConfigurableApplicationContext ac = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        NetworkClient client = ac.getBean(NetworkClient.class);
        // System.out.println(client.getUrl());
        ac.close();
    }

    @Configuration
    static class LifeCycleConfig {
        // destroyMethod는 추론 기능이 있어서 'close', 'shutdown' 등의 이름을 가진 메서드를 알아서 찾아서 사용한다.
        // 만약 추론 기능을 사용하기 싫으면 'destroyMethod = ""'처럼 빈 공백을 지정하면 된다.
        // @Bean(initMethod = "init", destroyMethod = "close")
        @Bean
        public NetworkClient networkClient() {
            NetworkClient networkClient = new NetworkClient();
            networkClient.setUrl("http://hello-spring.dev");
            return networkClient;
        }
    }

}
