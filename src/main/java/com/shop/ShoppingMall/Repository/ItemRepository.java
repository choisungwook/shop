package com.shop.ShoppingMall.Repository;
import com.shop.ShoppingMall.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    /***
     * item 저장
     * @param item item 객체
     */
    public void save(Item item){
        Long id = item.getId();
        if(id == null){
            //아이템 처음 등록
            em.persist(item);
        }else{
            em.merge(item);
        }
    }

    /***
     * 아이템 찾기(기준: ID)
     * @param id  찾을 아이템 ID
     */
    public Item findItemById(Long id){
        return em.find(Item.class, id);
    }

    /***
     * 모든 아이템 조회
     * @return 모든 아이템의 리스트
     */
    public List<Item> findALL(){
        return em.createQuery("select i from Item as i", Item.class).getResultList();
    }
}