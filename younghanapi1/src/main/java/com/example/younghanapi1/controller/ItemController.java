package com.example.younghanapi1.controller;

import com.example.younghanapi1.controller.form.BookForm;
import com.example.younghanapi1.controller.form.ItemForm;
import com.example.younghanapi1.entity.item.Book;
import com.example.younghanapi1.entity.item.Item;
import com.example.younghanapi1.service.ItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

	private final ItemService itemService;

	@GetMapping("/items/new")
	public String createForm(Model model) {
		model.addAttribute("bookForm", new BookForm());
		return "items/createItemForm";
	}

	@PostMapping("/items/new")
	public String create(@Valid BookForm form, BindingResult result) {

		if (result.hasErrors()) {
            return "items/createItemForm";
        }

		Book book = new Book();
		book.setAuthor(form.getAuthor());
		book.setIsbn(form.getIsbn());
		book.setPrice(form.getPrice());
		book.setStockQuantity(form.getStockQuantity());
		book.setName(form.getName());

		itemService.saveItem(book);
        return "redirect:/items";
	}

	@GetMapping("/items")
	public String list(Model model) {
		model.addAttribute("items", itemService.findItems());
		return "items/itemList";
	}

	@GetMapping("/items/{itemId}/edit")
	public String updateForm(@PathVariable(name = "itemId") Long itemId, Model model) {
		Book book = (Book) itemService.findOne(itemId);

		BookForm bookForm = new BookForm();
		bookForm.setName(book.getName());
		bookForm.setPrice(book.getPrice());
		bookForm.setStockQuantity(book.getStockQuantity());
		bookForm.setAuthor(book.getAuthor());
		bookForm.setIsbn(book.getIsbn());
		model.addAttribute("bookForm", bookForm);
		return "items/updateItemForm";
	}

	@PostMapping("/items/{id}/edit")
	public String update(@PathVariable(name = "id") Long itemId, @ModelAttribute(name = "form") ItemForm form) {

		itemService.updateItem(itemId, form);
		return "redirect:/items";
	}
}
