package com.mmc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mmc.model.Notice;
import com.mmc.repository.NoticeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceApplicationTests {
	
	@Autowired
	NoticeRepository noticeRepository;
	@Test
	public void contextLoads() {
	}
	@Test
	public void test1() {
		
	}
}
