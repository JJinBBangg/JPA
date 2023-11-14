package jpabook.jpashop.service;

import jpabook.jpashop.entity.item.Album;
import jpabook.jpashop.entity.item.Book;
import jpabook.jpashop.entity.item.Item;
import jpabook.jpashop.entity.item.Movie;
import jpabook.jpashop.repository.ItemRepository;
import jpabook.jpashop.request.CreateAlbum;
import jpabook.jpashop.request.CreateBook;
import jpabook.jpashop.request.CreateItem;
import jpabook.jpashop.request.CreateMovie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void join(CreateItem createItem) {
        if (createItem instanceof CreateBook) itemRepository.save(Book.builder()
                .name(createItem.getName())
                .price(createItem.getPrice())
                .stockQuantity(createItem.getStockQuantity())
                .isbn(((CreateBook) createItem).getIsbn())
                .author(((CreateBook) createItem).getAuthor())
                .build());

        if (createItem instanceof CreateMovie) itemRepository.save(Movie.builder()
                .name(createItem.getName())
                .price(createItem.getPrice())
                .stockQuantity(createItem.getStockQuantity())
                .actor(((CreateMovie) createItem).getActor())
                .director(((CreateMovie) createItem).getDirector())
                .build());

        if (createItem instanceof CreateAlbum) itemRepository.save(Album.builder()
                .name(createItem.getName())
                .price(createItem.getPrice())
                .stockQuantity(createItem.getStockQuantity())
                .artist(((CreateAlbum) createItem).getArtist())
                .etc(((CreateAlbum) createItem).getEtc())
                .build());
    }

    public List<Item> findItem() {
        return itemRepository.findAll();
    }
    public Item findOne(Long itemId){
        return itemRepository.findOne(itemId);
    }

}
