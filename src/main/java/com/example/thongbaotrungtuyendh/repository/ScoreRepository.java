package com.example.thongbaotrungtuyendh.repository;

import com.example.thongbaotrungtuyendh.entity.EExamSubject;
import com.example.thongbaotrungtuyendh.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {

}
