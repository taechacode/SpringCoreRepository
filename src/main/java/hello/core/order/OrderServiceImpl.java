package hello.core.order;

import hello.core.discount.DiscountPolicy;
import hello.core.discount.FixDiscountPolicy;
import hello.core.discount.RateDiscountPolicy;
import hello.core.member.Member;
import hello.core.member.MemberRepository;
import hello.core.member.MemoryMemberRepository;

public class OrderServiceImpl implements OrderService {

    private final MemberRepository memberRepository = new MemoryMemberRepository();

    // OrderServiceImpl는 DiscountPolicy 인터페이스만 의존하는 것이 아니라 구현 클래스도 의존하고 있다.
    // 만약 정액(FixDiscountPolicy)에서 정률(RateDiscountPolicy) 정책으로 바꾸려면 코드를 변경해야 함.
    // 인터페이스만 의존하지 않으므로 DIP 위반.
    // 기능을 확장해서 변경하면, 클라이언트 코드에 영향을 주므로 OCP도 위반.

    // 만약 인터페이스에만 의존하게 만들어버리면?
    // private DiscountPolicy discountPolicy; (구현체가 없어서 NullPointerException 발생)

    // 이 문제를 해결하려면 클라이언트인 OrderServiceImpl에 DiscountPolicy의 구현 객체를 대신 생성하고 주입해주어야 함.

    // private final DiscountPolicy discountPolicy = new FixDiscountPolicy();
    private final DiscountPolicy discountPolicy = new RateDiscountPolicy();

    @Override
    public Order createOrder(Long memberId, String itemName, int itemPrice) {
        Member member = memberRepository.findById(memberId);
        int discountPrice = discountPolicy.discount(member, itemPrice);

        return new Order(memberId, itemName, itemPrice, discountPrice);
    }

}
