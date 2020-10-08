package com.codeup.demo.repository;

import com.codeup.demo.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Param;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByTitle(String title);
    Post findFirstByTitle(String title);
    @Query("from Post p where p.id like ?1")
    Post getPostById(long id);

    @Query("from Post p where p.title like %:term%")
    List<Post> searchByTitleLike(@Param("term") String term);

}
