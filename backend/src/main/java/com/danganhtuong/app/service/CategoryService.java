package com.danganhtuong.app.service;

import com.danganhtuong.app.entity.Category;
import com.danganhtuong.app.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategoryTree() {
        // Fetch top-level categories
        return categoryRepository.findByParentIsNull();
    }
}

