package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jpabook.jpashop.entity.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public Long save(Item item){
        if(item.getId()==null){
            em.persist(item);
        } else {
            em.merge(item);
        }
        return item.getId();
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("select i from Item i")
                .getResultList();
    }

    public List<Item> findByName(String name){
        return em.createQuery("select i from Item i where i.name = :name")
                .setParameter("name", name)
                .getResultList();
    }

    public void deleteAll(){
        em.createQuery("delete from Item ").executeUpdate();
    }
}
