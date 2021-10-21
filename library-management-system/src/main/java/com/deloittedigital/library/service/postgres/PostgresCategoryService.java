package com.deloittedigital.library.service.postgres;

import com.deloittedigital.library.model.domain.Category;
import com.deloittedigital.library.repository.CategoryRepository;
import com.deloittedigital.library.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Profile("postgres")
@Service
public class PostgresCategoryService implements ICategoryService {

    private final CategoryRepository categoryRepository;

    public PostgresCategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryRepository.deleteAll();
        this.saveCategories();
    }


    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    public Optional<Category> get(Long id) {
        return categoryRepository.findById(id);
    }

    public Category add(Category category) {
        category.setCreatedAt(LocalDate.now());
        return categoryRepository.save(category);
    }

    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    public void delete(Category category) {
        categoryRepository.delete(category);
    }


    private List<Category> saveCategories() {
        List<Category> categories = new ArrayList<>();

        Category thriller = new Category();
        thriller.setName("Thriller");
        thriller.setDescription("Thrillers are a genre of fiction in which tough, resourceful, but essentially ordinary heroes are pitted against villains determined to destroy them, their country, or the stability of the free world.");
        categories.add(thriller);

        Category romance = new Category();
        romance.setName("Romance");
        romance.setDescription("A romance book is a genre fiction which places its primary focus on the relationship and romantic love between two people, and usually has an emotionally satisfying and optimistic ending.");
        categories.add(romance);

        Category horror = new Category();
        horror.setName("Horror");
        horror.setDescription("Meant to cause discomfort and fear for both the character and readers, horror writers often make use of supernatural and paranormal elements in morbid stories that are sometimes a little too realistic.");
        categories.add(horror);
        return categoryRepository.saveAll(categories);
    }

}
