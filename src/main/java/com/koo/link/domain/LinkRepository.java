package com.koo.link.domain;

import java.util.List;
import java.util.Optional;

public interface LinkRepository {
    Link save(Link link);
//    Optional<List<Link>> findByLinkId(Long linkId);
}
