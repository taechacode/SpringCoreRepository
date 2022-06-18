package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

// public class NetworkClient implements InitializingBean, DisposableBean {
public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출, url = " + url);
        // connect();
        // call("초기화 연결 메시지");
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return this.url;
    }

    // 서비스 시작 시 호출
    public void connect() {
        System.out.println("connect : " + url);
    }

    public void call(String message) {
        System.out.println("call : " + url + " message : " + message);
    }

    // 서비스 종료 시 호출
    public void disconnect() {
        System.out.println("close : " + url);
    }

    // 54강 인터페이스 InitializingBean, DisposableBean

    /* @Override
    public void afterPropertiesSet() throws Exception { // 의존관계 주입이 끝나면 호출
        System.out.println("NetworkClient.afterPropertiesSet");
        connect();
        call("초기화 연결 메시지");
    } */

    /* @Override
    public void destroy() throws Exception { // 빈이 종료될 때 호출
        System.out.println("NetworkClient.destroy");
        disconnect();
    } */

    // 55강 빈 등록 초기화, 소멸 메서드
    
    // BeanLifeCycleTest.java의 LifeCycleConfig 클래스에서
    // init()과 close()를 initMethod와 destroyMethod로 등록

    // 56강 애노테이션 @PostConstruct, @PreDestroy
    // -> 지금까지 3가지 방법 중에 이 56강 애노테이션을 사용하면 된다.

    @PostConstruct
    public void init() {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메시지");
    }

    @PreDestroy
    public void close() {
        System.out.println("NetworkClient.close");
        disconnect();
    }

}
