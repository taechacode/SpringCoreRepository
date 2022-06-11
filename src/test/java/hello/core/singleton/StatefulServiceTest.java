package hello.core.singleton;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

public class StatefulServiceTest {

    @Test
    void statefulServiceSingleton() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(TestConfig.class);
        StatefulService statefulService1 = ac.getBean(StatefulService.class);
        StatefulService statefulService2 = ac.getBean(StatefulService.class);

        // ThreadA : 사용자A 10000원 주문
        // statefulService1.order("userA", 10000);
        int userAPrice = statefulService1.order("userA", 10000);

        // ThreadB : 사용자B 20000원 주문
        // statefulService2.order("userB", 20000);
        int userBPrice = statefulService2.order("userB", 20000);

        // ThreadA : 사용자A 주문 금액 조회
        // int price = statefulService1.getPrice();

        // 기대한 값 : 10000, 실제로 나온 값 : 20000
        // 이유는 싱글톤 패턴으로 인해 statefulService1과 statefulService2가 같은 인스턴스를 사용하기 때문
        // System.out.println("price = " + price);

        // 기대한 값 : 10000, 실제로 나온 값 : 10000
        System.out.println("price = " + userAPrice);

        // Assertions.assertThat(statefulService1.getPrice()).isEqualTo(20000);
    }

    static class TestConfig {

        @Bean
        public StatefulService statefulService() {
            return new StatefulService();
        }

    }

}
