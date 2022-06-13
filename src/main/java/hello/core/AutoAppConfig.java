package hello.core;

import hello.core.discount.DiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import hello.core.order.OrderService;
import hello.core.order.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(
        // basePackages = "hello.core.member", // member 하위의 컴포넌트만 스캔
        // basePackageClasses = AutoAppConfig.class, // AutoAppconfig.class가 속한 패키지의 하위 컴포넌트만을 스캔
        excludeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Configuration.class)
)
public class AutoAppConfig {

    // memoryMemberRepository가 2개 등록 되면? (수동 빈과 자동 빈)
    // 이 경우 [수동 빈]이 우선권을 가진다. (수동 빈이 자동 빈을 오버라이딩해버림)

    // 최근 스프링 부트에서는 수동 빈 등록과 자동 빈 등록이 충돌하면 오류가 발생하도록 기본 값을 변경하였다.
    // @Bean(name = "memoryMemberRepository")
    // MemberRepository memberRepository() {
        // return new MemoryMemberRepository();
    // }

}
