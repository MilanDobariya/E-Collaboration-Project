package com.niit.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.niit.model.BlogPost;
import com.niit.model.BlogPostLikes;
import com.niit.model.User;

@Repository
@Transactional
public class BlogPostLikesDaoImpl implements BlogPostLikesDao{
	@Autowired
	private SessionFactory sessionFactory;

	public BlogPostLikes hasUserLikedBlogPost(int blogpostId, String email) {
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("from BlogPostLikes where blogPost.id=? and user.email=?");
		query.setInteger(0, blogpostId);
		query.setString(1, email);
		BlogPostLikes blogPostLikes=(BlogPostLikes)query.uniqueResult();
		return blogPostLikes;//either null or 1 blogpostlikes object
	}

	public BlogPost updateBlogPostLikes(int blogPostId, String email) {
		Session session=sessionFactory.getCurrentSession();
		BlogPostLikes blogPostLikes=hasUserLikedBlogPost(blogPostId, email);
		BlogPost blogPost=(BlogPost)session.get(BlogPost.class, blogPostId);
		
		//create a new BlogPostLikes object and set values for Blogpost and user
		if(blogPostLikes==null){//insert record into blogpostlikes and increment the likes
			//insert and increment
			blogPostLikes=new BlogPostLikes();
			User user=(User)session.get(User.class, email);
			blogPostLikes.setBlogPost(blogPost);
			blogPostLikes.setUser(user);
			session.save(blogPostLikes);
			blogPost.setLikes(blogPost.getLikes() + 1);
			session.update(blogPost);
		}else{//delete record from th blogpostlikes and decrement the count
			session.delete(blogPostLikes);
			blogPost.setLikes(blogPost.getLikes()-1);
			session.update(blogPost);
		}
		return blogPost;//updated likes
	}

}
