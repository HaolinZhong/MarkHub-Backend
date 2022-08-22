package hz.blog.markhub.service.impl;

import hz.blog.markhub.domain.BlogDo;
import hz.blog.markhub.domain.Pager;
import hz.blog.markhub.exception.ServiceException;
import hz.blog.markhub.exception.ServiceExceptionEnum;
import hz.blog.markhub.mapper.BlogDoMapper;
import hz.blog.markhub.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class BlogServiceImpl implements BlogService {

    private final BlogDoMapper blogDoMapper;

    @Override
    public Pager<BlogDo> listBlogByPager(Integer currentPage, Integer pageSize) {

        List<BlogDo> blogDos = blogDoMapper.selectByPager((currentPage - 1) * pageSize, pageSize);
        Integer total = blogDoMapper.count();

        Pager<BlogDo> pager = new Pager<>();
        pager.setCurrentPage(currentPage);
        pager.setPageSize(pageSize);
        pager.setRows(blogDos);
        pager.setTotal(total);

        return pager;
    }

    @Override
    public BlogDo getBlogById(Long blogId) {
        BlogDo blogDo = blogDoMapper.selectByPrimaryKey(blogId);
        return blogDo;
    }

    @Override
    public BlogDo createBlog(BlogDo blogDo) {
        blogDoMapper.insertSelective(blogDo);
        return blogDo;
    }

    @Override
    public Boolean updateBlog(BlogDo blogDo) {
        Integer rowAffected = blogDoMapper.updateByPrimaryKeySelective(blogDo);
        return rowAffected == 1;
    }

    @Transactional
    @Override
    public Boolean deleteBlog(Long blogId) throws ServiceException {
        int res = blogDoMapper.deleteByPrimaryKey(blogId);
        if (res != 1) throw new ServiceException(ServiceExceptionEnum.UNKNOWN_ERROR);
        return true;
    }

    @Override
    public Pager<BlogDo> findBlogByPager(Integer page, Integer pageSize, String title) {
        List<BlogDo> blogDos = blogDoMapper.findByPager((page - 1) * pageSize, pageSize, title);
        Integer total = blogDoMapper.resultCount(title);

        Pager<BlogDo> pager = new Pager<>();
        pager.setCurrentPage(page);
        pager.setPageSize(pageSize);
        pager.setRows(blogDos);
        pager.setTotal(total);

        return pager;
    }
}
