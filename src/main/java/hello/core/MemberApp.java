package hello.core;

import hello.core.member.Grade;
import hello.core.member.Member;
import hello.core.member.MemberService;
import hello.core.member.MemberServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MemberApp {
    public static void main(String[] args) {
        // MemberService memberService = new MemberServiceImpl();

        // AppConfig appConfig = new AppConfig();
        // MemberService memberService = appConfig.memberService();

        // ApplicationContext를 보통 스프링 컨테이너라고 한다. (ApplicationContext는 인터페이스)
        // 스프링 컨테이너는 XML 기반으로 만들 수도 있고, 애노테이션 기반의 자바 설정 클래스로 만들 수도 있다.
        // 빈 이름은 항상 다른 이름을 부여해야 한다. 같은 이름의 빈들이 등록되면 다른 빈이 무시되거나, 기존 빈을 덮어버리거나, 설정에 따라 오류가 발생한다.
        // AppConfig에 있는 Bean들을 스프링 컨테이너에 넣어서 관리함.
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
        MemberService memberService = applicationContext.getBean("memberService", MemberService.class);

        Member member = new Member(1L, "memberA", Grade.VIP);
        memberService.join(member);

        Member findMember = memberService.findMember(1L);
        System.out.println("new Member = " + member.getName());
        System.out.println("findMember = " + findMember.getName());
    }
}
