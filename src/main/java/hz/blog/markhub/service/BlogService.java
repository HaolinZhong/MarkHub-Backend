package hz.blog.markhub.service;

import hz.blog.markhub.domain.BlogDo;
import hz.blog.markhub.domain.Pager;
import hz.blog.markhub.exception.ServiceException;

import java.util.List;

public interface BlogService {
    Pager<BlogDo> listBlogByPager(Integer currentPage, Integer pageSize);

    BlogDo getBlogById(Long blogId);

    BlogDo createBlog(BlogDo blogDo);

    Boolean updateBlog(BlogDo blogDo);

    Boolean deleteBlog(Long blogId) throws ServiceException;

    Pager<BlogDo> findBlogByPager(Integer page, Integer pageSize, String title);
}
