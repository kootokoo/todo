package com.koo.link.application;

import com.koo.link.application.exception.AlreadLinkedTodoExist;
import com.koo.link.domain.Link;
import com.koo.link.domain.LinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LinkService {
    @Autowired
    private LinkRepository linkRepository;

    public List<Long> getLinkedTodoList(Long linkId) {
//        List<Link> byLinkId = linkRepository.findByLinkId(linkId).get();
//        return byLinkId.stream().map(link -> link.getLinkedId()).collect(Collectors.toList());
        return null;
    }

    public void deleteLink(Long linkId) {
//        Optional<List<Link>> byLinkId = linkRepository.findByLinkId(linkId);

//        byLinkId.ifPresent(() -> new AlreadLinkedTodoExist("앞선 todor가 존재합니다."));
    }

}
