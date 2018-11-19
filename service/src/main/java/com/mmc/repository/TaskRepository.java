package com.mmc.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.mmc.model.Task;

public interface TaskRepository extends JpaRepository<Task,Integer> {
	@Query(value="SELECT * FROM TASK WHERE NAME LIKE %?1% AND TID = ?2 AND END_NUM=USER_NUM",
	  nativeQuery=true)
	public Page<Task> findAllEnd(String name,int tid, Pageable pageable);
	@Query(value="SELECT * FROM TASK WHERE NAME LIKE %?1% AND END_NUM=USER_NUM",
	  nativeQuery=true)
	public Page<Task> findAllEnd(String name, Pageable pageable);
	@Query(value="SELECT * FROM TASK WHERE NAME LIKE %?1% AND TID = ?2 AND END_NUM<USER_NUM",
	  nativeQuery=true)
	public Page<Task> findAllNOEnd(String name,int tid, Pageable pageable);
	@Query(value="SELECT * FROM TASK WHERE NAME LIKE %?1% AND END_NUM<USER_NUM",
	  nativeQuery=true)
	public Page<Task> findAllNOEnd(String name,Pageable pageable);
	@Query(value="SELECT * FROM TASK WHERE NAME LIKE %?1% AND TID = ?2",
	  nativeQuery=true)
	public Page<Task> findAll(String name,int tid,Pageable pageable);
	@Query(value="SELECT * FROM TASK WHERE NAME LIKE %?1%",
	  nativeQuery=true)
	public Page<Task> findAll(String name,Pageable pageable);
}