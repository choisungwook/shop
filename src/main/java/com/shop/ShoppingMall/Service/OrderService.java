package com.shop.ShoppingMall.Service;

import com.shop.ShoppingMall.Repository.ItemRepository;
import com.shop.ShoppingMall.Repository.MemberRepository;
import com.shop.ShoppingMall.Repository.OrderRepository;
import com.shop.ShoppingMall.Repository.OrderSearch;
import com.shop.ShoppingMall.domain.Delivery;
import com.shop.ShoppingMall.domain.Member;
import com.shop.ShoppingMall.domain.Order;
import com.shop.ShoppingMall.domain.OrderItem;
import com.shop.ShoppingMall.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /***
     * 주문생성
     * @param memberId
     * @param itemId
     * @param count
     * @return
     */
    @Transactional
    public Long order(Long memberId,Long itemId, int count){
        //회원을 찾고
        Member findMemberId = memberRepository.findMemberById(memberId);
        Item findItem = itemRepository.findItemById(itemId);

        //배송정보 설정
        Delivery delivery = new Delivery();
        delivery.setAddress(findMemberId.getAddress());
        
        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(findItem, findItem.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(findMemberId, delivery, orderItem);

        //주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(long orderId){
        Order findOrder = orderRepository.findOrderById(orderId);
        findOrder.cancel();
    }

    /***
     * 이름으로 모든 주문 검색
     * @param orderSearch
     * @return
     */
    public List<Order> findOrders(OrderSearch orderSearch){
        return orderRepository.findAllByString(orderSearch);
    }
}
