package jpabook.jpashop.controller;

import jpabook.jpashop.request.CreateBook;
import jpabook.jpashop.request.CreateItem;
import jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/items")
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/new")
    public String newItem(Model model){
        model.addAttribute("itemForm" );
        return "";
    }

}
