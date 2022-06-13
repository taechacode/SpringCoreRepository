package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor // 자신의 객체에 final에 붙은 필드를 기준으로 생성자를 만들어준다. (생성자 주입 방식)
public class OrderServiceImpl implements OrderService {

    // OrderServiceImpl는 DiscountPolicy 인터페이스만 의존하는 것이 아니라 구현 클래스도 의존하고 있다.
    // 만약 정액(FixDiscountPolicy)에서 정률(RateDiscountPolicy) 정책으로 바꾸려면 코드를 변경해야 함.
    // 인터페이스만 의존하지 않으므로 DIP 위반.
    // 기능을 확장해서 변경하면, 클라이언트 코드에 영향을 주므로 OCP도 위반.

    // 만약 인터페이스에만 의존하게 만들어버리면?
    // private DiscountPolicy discountPolicy; (구현체가 없어서 NullPointerException 발생)

    // 이 문제를 해결하려면 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해주어야 함. -> AppConfig로 해결

    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    // private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    // MemberRepository 또한 구현체까지 의존한다.
    // private final MemberRepository memberRepository = new MemoryMemberRepository();

    // AppConfig을 통해 외부에서 구현 클래스를 주입해준다.
    private final MemberRepository memberRepository;
    private final DiscountPolicy discountPolicy;
    
    // 필드 주입 방식
    // @Autowired private MemberRepository memberRepository;
    // @Autowired private DiscountPolicy discountPolicy;

    // @Autowired
    // public OrderServiceImpl(MemberRepository memberRepository, DiscountPolicy discountPolicy) {
        // this.memberRepository = memberRepository;
        // this.discountPolicy = discountPolicy;
    // }
    
    // 수정자 방식
    // private MemberRepository memberRepository;
    // private DiscountPolicy discountPolicy;
    
    // @Autowired
    // public void setMemberRepository(MemberRepository memberRepository) {
        // this.memberRepository = memberRepository;
    // }

    // @Autowired
    // public void setDiscountPolicy(DiscountPolicy discountPolicy) {
        // this.discountPolicy = discountPolicy;
    // }

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

    // 테스트 용도
    public MemberRepository getMemberRepository() {
        return memberRepository;
    }

}
