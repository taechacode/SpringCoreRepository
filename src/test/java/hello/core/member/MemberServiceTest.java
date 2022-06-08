package hello.core.member;

import hello.core.AppConfig;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MemberServiceTest {

    // MemberService memberService = new MemberServiceImpl();
    MemberService memberService;
    
    // Test를 실행하기 전에 MemberService에 AppConfig의 구현 클래스를 할당
    @BeforeEach
    public void beforeEach() {
        AppConfig appConfig = new AppConfig();
        memberService = appConfig.memberService();
    }

    @Test
    void join() {
        // given "~이러한 것이 주어졌을 때"
        Member member = new Member(1L, "memberA", Grade.VIP);

        // when "~이렇게 했을 때"
        memberService.join(member);
        Member findMember = memberService.findMember(1L);

        // then "~이렇게 된다"
        Assertions.assertThat(member).isEqualTo(findMember);

    }
}
