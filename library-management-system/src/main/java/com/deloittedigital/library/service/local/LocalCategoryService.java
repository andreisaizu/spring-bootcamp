package com.deloittedigital.library.service.local;

import com.deloittedigital.library.model.domain.Category;
import com.deloittedigital.library.service.ICategoryService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Profile("local")
public class LocalCategoryService implements ICategoryService {

    public static List<Category> categoryList = initCategories();

    public List<Category> getAll() {
        return categoryList;
    }

    public Optional<Category> get(Long id) {
        return categoryList.stream().filter(category -> category.getId().equals(id)).findFirst();
    }

    public Category add(Category category) {
        category.setCreatedAt(LocalDate.now());
        categoryList.add(category);
        return category;
    }

    public Category update(Category category) {
        Category foundCategory = categoryList.stream().filter(cat -> cat.getId().equals(category.getId())).findFirst().get();
        int foundCategoryIdx = categoryList.indexOf(foundCategory);
        categoryList.set(foundCategoryIdx, category);
        return category;
    }

    public void delete(Category category) {
        Category foundCategory = categoryList.stream().filter(cat -> cat.getId().equals(category.getId())).findFirst().get();
        categoryList.remove(foundCategory);
    }

    public static List<Category> initCategories() {
        List<Category> categories = new ArrayList<>();
        Category thriller = new Category();
        thriller.setId(0L);
        thriller.setName("Thriller");
        thriller.setDescription("Thrillers are a genre of fiction in which tough, resourceful, but essentially ordinary heroes are pitted against villains determined to destroy them, their country, or the stability of the free world.");
        categories.add(thriller);

        Category romance = new Category();
        romance.setId(1L);
        romance.setName("Romance");
        romance.setDescription("A romance book is a genre fiction which places its primary focus on the relationship and romantic love between two people, and usually has an emotionally satisfying and optimistic ending.");
        categories.add(romance);

        Category horror = new Category();
        horror.setId(2L);
        horror.setName("Horror");
        horror.setDescription("Meant to cause discomfort and fear for both the character and readers, horror writers often make use of supernatural and paranormal elements in morbid stories that are sometimes a little too realistic.");
        categories.add(horror);
        return categories;
    }

}
