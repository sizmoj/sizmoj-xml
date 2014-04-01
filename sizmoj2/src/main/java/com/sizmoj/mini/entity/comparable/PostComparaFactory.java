package com.sizmoj.mini.entity.comparable;

import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import com.sizmoj.mini.entity.Post;
import com.sizmoj.mini.entity.PostView;

public class PostComparaFactory {
	public static final String ORDER_BY_DATE = "ORDER_BY_DATE";
	public static final String COMPARE_BY_DATE_YEAR_MONTH = "ORDER_BY_DATE";
	
	public static Comparator<Post> getPostInstance(String orderBy) {
		if(StringUtils.equals(ORDER_BY_DATE, orderBy))
			return new ComparaByDate();

		return null;
	}
	
	public static Comparator<PostView> getPostViewInstance(String orderBy) {
		if(StringUtils.equals(COMPARE_BY_DATE_YEAR_MONTH, orderBy))
			return new ComparaByDateYearMonth();
		return null;
	}
	
	
	private static class ComparaByDate implements Comparator<Post>{		
		@Override
		public int compare(Post p1,Post p2)  {
			DateTime dt1 = new DateTime(p1.getPublicDate());
			DateTime dt2 = new DateTime(p2.getPublicDate());
			return dt1.compareTo(dt2);
		}		
	}
	/**
	 * 用于显示前端所有博客
	 * @author Administrator
	 *
	 */
	private static class ComparaByDateYearMonth implements Comparator<PostView>{		

		@Override
		public int compare(PostView o1, PostView o2) {
			DateTime dt1 = new DateTime(o1.getDateTime());
			DateTime dt2 = new DateTime(o2.getDateTime());
			return dt1.compareTo(dt2);
		}		
	}
}
