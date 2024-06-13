package com.react.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.react.model.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, Integer>{

	public List<Story> findByUserId(Integer userId);
}
