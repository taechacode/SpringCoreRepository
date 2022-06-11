package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.MemberRepository;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// AppConfig는 애플리케이션의 실제 동작에 필요한 구현 객체를 생성한다.
public class AppConfig {

    // @Bean memberService -> new MemoryMemberRepository();
    // @Bean orderService -> new MemoryMemberRepository();
    // MemoryMemberRepository 객체가 2번 생성되지 않을까?

    @Bean
    // AppConfig는 생성한 객체 인스턴스의 참조(레퍼런스)를 [생성자를 통해서 주입(연결)] 해준다.
    public MemberService memberService() {
        System.out.println("call AppConfig.memberService");
        return new MemberServiceImpl(memberRepository());
    }

    @Bean
    // 리팩토링 이전에는 new MemoryMemberRepository()가 중복되고, 역할과 구현 클래스가 한눈에 들어오지 않았다.
    // 하지만 MemberRepository과 DiscountPolicy를 분리시킴으로써 역할과 구현 클래스가 한눈에 들어오고, new MemoryMemberRepositorty 중복이 제거되었다.
    // 이제 MemberRepository를 다른 구현체로 바꾸고 싶다면 아래의 메소드만 변경하면 된다.
    public MemberRepository memberRepository() {
        System.out.println("call AppConfig.memberRepository");
        return new MemoryMemberRepository();
    }

    /*
    public MemberService memberService() {
        return new MemberServiceImpl(new MemoryMemberRepository());
    }
    */

    // 스프링 빈은 @Bean이 붙은 메서드의 이름(orderService)을 스프링 빈의 이름으로 사용한다.
    // Bean을 붙이면 스프링 컨테이너에 이름은 orderService(Key), 값은 new OrderServiceImpl(memberRepository(), discountPolicy())(Value)로 저장.
    // 클라이언트(~App)에서 applicationContext.getBean 메서드를 통해서 스프링 컨테이너에 등록한 Bean을 불러옴.
    @Bean
    public OrderService orderService() {
        System.out.println("call AppConfig.orderService");
        return new OrderServiceImpl(memberRepository(), discountPolicy());
    }

    @Bean
    public DiscountPolicy discountPolicy() {
        // return new FixDiscountPolicy();
        return new RateDiscountPolicy();
    }

    /*
    public OrderService orderService() {
        return new OrderServiceImpl(new MemoryMemberRepository(), new FixDiscountPolicy());
    }
    */

}
