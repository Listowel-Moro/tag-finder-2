package com.listo.tag_finder.service;

import java.util.List;
import com.listo.tag_finder.model.Tag;
import org.springframework.stereotype.Service;

@Service
public interface TagService {
    void addTag(Tag tag);
    List<Tag> findAll();
    Tag findById(Long id);
    Tag save(Tag tag);
    void deleteById(Long id);
}
