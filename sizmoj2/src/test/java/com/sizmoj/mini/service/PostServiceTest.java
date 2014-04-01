package com.sizmoj.mini.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springside.modules.test.spring.Profiles;

import com.sizmoj.mini.entity.Post;
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/application.xml" })
@ActiveProfiles(Profiles.UNIT_TEST)
public class PostServiceTest{
	@Autowired
	private PostService postService;
	
	//@Test
	public void testGetNewPosts() {
		
	}

	//@Test
	public void testSavePost() {
		Post p = new Post();
		p.setUrlName("dsadsadas");
		postService.savePost(p);
	}
	
	@Test
	public void getPostLocation() {
		Post p = new Post();
		p.setUrlName("0002");
		Post p1 = postService.getPost("0002.xml");
		System.out.println(p1.getAuthor());
	}
}
