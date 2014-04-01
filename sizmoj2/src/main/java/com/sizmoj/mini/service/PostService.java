package com.sizmoj.mini.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springside.modules.mapper.JaxbMapper;

import com.sizmoj.mini.common.ServletContextPathResolver;
import com.sizmoj.mini.entity.Post;
import com.sizmoj.mini.entity.PostView;
import com.sizmoj.mini.entity.comparable.PostComparaFactory;
@Service
public class PostService {
	
	@Autowired
	private AccountService accountService;
	@Autowired
	private ServletContextPathResolver servletContextPathResolver;
	
	private static final String ENCODING = "UTF-8";
	private static final String DATE_FORMATE = "yyyy-MM-dd";
	private static final String STATUS_MORMAL = "0";
	private static final String[] FILE_FILTER_NAME = {"xml"};
	private static final String POST_STATUS_DEL = "1";
	private static final String POST_STATUS_NORMAL = "0";
	
	public void savePost(Post post) {
		DateTime dt = new DateTime();
		post.setPublicDate(dt.toString(DATE_FORMATE));
		post.setAuthor(accountService.getUser());
		post.setStatus(STATUS_MORMAL);
		post.setCounter(POST_STATUS_NORMAL);
		StringBuilder saveDir = new StringBuilder();		
		saveDir.append(servletContextPathResolver.getPath("WEB-INF"+File.separatorChar+"post"));
		saveDir.append(File.separatorChar).append(dt.getYear()).append("-").append(dt.getMonthOfYear());
		saveDir.append(File.separatorChar + post.getUrlName() + ".xml");		
		try {
			FileUtils.writeStringToFile(new File(saveDir.toString()), JaxbMapper.toXml(post, ENCODING));
		} catch (IOException e) {
			e.printStackTrace();
		}				
	}
	
	public void deletePost(String urlName) {
		 Post post = getPost(urlName);
		 post.setStatus(POST_STATUS_DEL);
		 updatePost(post);
	}
	
	public void updatePost(Post post) {
		savePost(post);	
	}
	
	public Post getPost(String urlName) {		 
		try {
			return JaxbMapper.fromXml(
					FileUtils.readFileToString(getPostLocation(urlName)), Post.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public List<Post> getAllPost() {
		Iterator<File> files = getAllPostFile();
		List<Post> posts = new ArrayList<Post>();
		Post tempPost;
		while(files.hasNext()) {
			try {				
				tempPost = JaxbMapper.fromXml(FileUtils.readFileToString(files.next()), Post.class);
				if(!StringUtils.equals(tempPost.getStatus(),POST_STATUS_DEL))
						posts.add(tempPost);						
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		}
		return posts;			
	}
	
	public List<PostView> getAllPostViewDate() {
		List<Post> posts = getAllPost();
		List<PostView> postViews = new ArrayList<PostView>(); 
		PostView postView = new PostView();
		Map<String, List<String>> viewposts = new HashMap<String,  List<String>>();
		for(Post post : posts) {
			if(viewposts.containsKey(getYearMonth(post.getPublicDate()))) {
				viewposts.get(getYearMonth(post.getPublicDate())).add(post.getUrlName());
				viewposts.put(getYearMonth(post.getPublicDate()), 
						viewposts.get(getYearMonth(post.getPublicDate())));
			} else {
				List<String> ps = new ArrayList<String>();
				ps.add(post.getUrlName());
				viewposts.put(getYearMonth(post.getPublicDate()), ps);
			}				
		}
		for(String key : viewposts.keySet()) {
			postView.setDateTime(key);
			postView.setUrlname(viewposts.get(key));
			postViews.add(postView);			
		}
		Collections.sort(postViews, PostComparaFactory.getPostViewInstance(
				PostComparaFactory.COMPARE_BY_DATE_YEAR_MONTH));
		return postViews;
	}
	
	/**
	 * 获取最近文章 limit 20
	 * @return
	 */
	public List<Post> getNewPosts() {
		List<Post> posts = getAllPost();
		Collections.sort(posts, 
				PostComparaFactory.getPostInstance(PostComparaFactory.ORDER_BY_DATE));
		return posts.subList(0, 20);
	}
	
	/**
	 * 通过Tag获取文章,这里为防止加载过多进行的保护最多200
	 * @param tagName
	 * @return
	 */
	public List<Post> getPostsByTag(String tagName) {
		List<Post> posts = getAllPost();
		List<Post> postsByTag = new ArrayList<Post>();
		for(Post post : posts) {
			String[] tagskeys = StringUtils.splitByWholeSeparator(post.getTags(), ";");
			Arrays.sort(tagskeys);
			if(Arrays.binarySearch(tagskeys,tagName) != -1)
				postsByTag.add(post);
			if(postsByTag.size() > 200)
				break;
		}
		Collections.sort(postsByTag, 
				PostComparaFactory.getPostInstance(PostComparaFactory.ORDER_BY_DATE));
		return postsByTag;
	}
	
	/**
	 * 通过Term获取文章,这里为防止加载过多进行的保护最多200
	 * @param termName
	 * @return
	 */
	public List<Post> getPostsByTerm(String termName) {
		List<Post> posts = getAllPost();
		List<Post> postsByTerm = new ArrayList<Post>();
		for(Post post : posts) {
			if(StringUtils.equals(post.getTerm(), termName))
				postsByTerm.add(post);
			if(postsByTerm.size() >=200) 
				break;
		}
		Collections.sort(postsByTerm, 
				PostComparaFactory.getPostInstance(PostComparaFactory.ORDER_BY_DATE));
		return postsByTerm;
	}
	
	/**
	 * 获取所有tag
	 * @return
	 */
	public HashMap<String, Integer> getAllTag() {		 
		List<Post> posts = getAllPost();
		HashMap<String, Integer> tags = new HashMap<String, Integer>();
		for(Post post : posts) {
			if (StringUtils.isNoneBlank(post.getTags())) {
				String[] tagskeys = StringUtils.splitByWholeSeparator(post.getTags(), ";");
				for(String tagskey : tagskeys) {
					if(StringUtils.isNoneBlank(tagskey)) {
						if(tags.containsKey(tagskey)) {
							tags.put(tagskey, tags.get(tagskey) + 1);
						} else {
							tags.put(tagskey,1);
						}
					}
				}
			}
		}	
		return tags;
	}
	/**
	 * 获取所有分类
	 * @return
	 */
	public HashMap<String, Integer> getAllTerm() {		 
		List<Post> posts = getAllPost();
		HashMap<String, Integer> terms = new HashMap<String, Integer>();
		for(Post post : posts) {
			if (StringUtils.isNoneBlank(post.getTerm())) {
				if(terms.containsKey(post.getTerm())) {
					terms.put(post.getTerm(), terms.get(post.getTerm()) + 1);
				} else {
					terms.put(post.getTerm(), 1);
				}
				
			}
		}	
		return terms;
	}
	/***
	 * 通过urlName 获取文章File
	 * @param urlName
	 * @return
	 */ 
	private File getPostLocation(String urlName) {
		Iterator<File> files = getAllPostFile();
		File tempFile;
		while(files.hasNext()) {
			tempFile = files.next();
			if(StringUtils.equals(tempFile.getName(), urlName + ".xml"));
				return tempFile;
		}	
		return null;
	}
	/**
	 * 获取所有文章File
	 * @return
	 */
	private Iterator<File> getAllPostFile() {
		StringBuilder saveDir = new StringBuilder();		
		saveDir.append(servletContextPathResolver.getPath("WEB-INF"+File.separatorChar+"post"));
		return FileUtils.iterateFiles(new File(saveDir.toString()), FILE_FILTER_NAME, true);
	}
	/**
	 * 把 2012-01-01 转换成2012-01
	 * @param date
	 * @return
	 */
	private String getYearMonth(String date) {
		DateTime dt = new DateTime(date);
		StringBuilder sb = new StringBuilder(dt.getYear());
		sb.append(dt.getMonthOfYear());
		return sb.toString();
	}
}
