package hello.core.scope;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Provider;

import static org.assertj.core.api.Assertions.*;

public class SingletonWithPrototypeTest1 {

    @Test
    void prototypeFind() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class);
        PrototypeBean prototypeBean1 = ac.getBean(PrototypeBean.class);
        prototypeBean1.addCount();
        assertThat(prototypeBean1.getCount()).isEqualTo(1);

        PrototypeBean prototypeBean2 = ac.getBean(PrototypeBean.class);
        prototypeBean2.addCount();
        assertThat(prototypeBean2.getCount()).isEqualTo(1);
    }

    @Test
    void singletonClientUsePrototype() {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(ClientBean.class, PrototypeBean.class);

        ClientBean clientBean1 = ac.getBean(ClientBean.class);
        int count1 = clientBean1.logic();
        assertThat(count1).isEqualTo(1);

        ClientBean clientBean2 = ac.getBean(ClientBean.class);
        int count2 = clientBean2.logic();
        // assertThat(count2).isEqualTo(2);
        assertThat(count2).isEqualTo(1);

    }

    @Scope("singleton")
    static class ClientBean {

        // private final PrototypeBean prototypeBean; // 생성 시점에 주입 x01

        // @Autowired
        // ObjectFactory<T>를 상속받음.
        // ObjectFactory<T>는 getObject() 하나만 있지만, ObjectProvider<T>는 편의기능이 추가됨.
        // 스프링 컨테이너에 있는 빈을 쉽게 대신 찾아주는 역할. (ApplicationContext를 사용해서 찾아오면 번거로워지므로)
        // private ObjectProvider<PrototypeBean> prototypeBeanProvider;

        @Autowired
        private Provider<PrototypeBean> prototypeBeanProvider;

        // @Autowired
        // public ClientBean(PrototypeBean prototypeBean) {
            // this.prototypeBean = prototypeBean;
        // }
        
        // 생성 시점에 주입된 객체를 재활용
        public int logic() {
            // PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
            PrototypeBean prototypeBean = prototypeBeanProvider.get();
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("singleton")
    static class ClientBean2 {
        private final PrototypeBean prototypeBean; // 생성 시점에 주입 x02

        @Autowired
        public ClientBean2(PrototypeBean prototypeBean) {
            this.prototypeBean = prototypeBean;
        }

        // 생성 시점에 주입된 객체를 재활용
        public int logic() {
            prototypeBean.addCount();
            int count = prototypeBean.getCount();
            return count;
        }
    }

    @Scope("prototype")
    static class PrototypeBean {
        private int count = 0;

        public void addCount() {
            count++;
        }

        public int getCount() {
            return count;
        }

        @PostConstruct
        public void init() {
            System.out.println("PrototypeBean.init" + this);
        }

        @PreDestroy
        public void destroy() {
            System.out.println("PrototypeBean.destroy");
        }
    }

}
