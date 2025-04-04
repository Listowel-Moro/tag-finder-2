package com.listo.tag_finder.service;

import com.listo.tag_finder.model.Tag;
import com.listo.tag_finder.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public void addTag(Tag tag) {
        tagRepository.save(tag);
    }

    @Override
    public Page<Tag> findAll(int page, int size) {
        return tagRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Tag findById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public Tag save(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public void deleteById(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        tagRepository.deleteAll();
    }
}
