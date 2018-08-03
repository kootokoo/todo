package com.koo.link.infra;

import com.koo.link.domain.Link;
import com.koo.link.domain.LinkRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkJpaRepository extends JpaRepository<Link, Long>, LinkRepository {

}
