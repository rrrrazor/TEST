package com.rongrong.shop.service;

import com.rongrong.shop.bean.Article;
import com.rongrong.shop.bean.ArticleType;
import com.rongrong.shop.bean.User;
import com.rongrong.shop.repository.ArticleMapper;
import com.rongrong.shop.repository.ArticleTypeMapper;
import com.rongrong.shop.repository.UserMapper;
import com.rongrong.shop.utils.Pager;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("shopService")
public class ShopServiceImpl implements ShopService {
    @Resource
    private ArticleTypeMapper articleTypeMapper;

    @Resource
    private ArticleMapper articleMapper;

    @Resource
    private UserMapper userMapper;

    @Override
    public List<ArticleType> getArticleTypes() {
        return articleTypeMapper.getArticleTypes();
    }

    @Override
    public Map<String, Object> login(String loginName, String passWord) {
        Map<String, Object> results = new HashMap<>();
        if(StringUtils.isEmpty(loginName) || StringUtils.isEmpty(passWord)){
            results.put("code", 1);
            results.put("msg", "参数为空了");
        }else {
            User user = userMapper.login(loginName);
            if (user != null){
                if(user.getPassword().equals(passWord)){
                    results.put("code", 0);
                    results.put("msg", user);
                }else {
                    results.put("code", 2);
                    results.put("msg", "密码错误了");
                }
            }else {
                results.put("code", 3);
                results.put("msg", "登录名不存在");
            }
        }
        return results;
    }

    @Override
    public List<ArticleType> loadFirstArticleTypes() {
        List<ArticleType> articleTypes = articleTypeMapper.getFirstArticleTypes();
        return articleTypes;
    }

    @Override
    public List<Article> searchArticles(String typeCode, String secondType, String title, Pager pager) {
        int count = articleMapper.count(typeCode, secondType, title);
        pager.setTotalCount(count);
        return articleMapper.searchArticles(typeCode,secondType,title,pager);
    }

    @Override
    public List<ArticleType> loadSecondTypes(String typeCode) {
        List<ArticleType> articleTypes = articleTypeMapper.loadSecondTypes(typeCode+"%" , typeCode.length()+4);
        return articleTypes;
    }

    @Override
    public void deleteById(String id) {
        articleMapper.deleteById(id);
    }

    @Override
    public Article getArticleById(String id) {
        return articleMapper.getArticleById(id);
    }

    @Override
    public void updateArticle(Article article) {
        articleMapper.update(article);
    }

    @Override
    public void saveArticle(Article article) {
        article.setCreateDate(new Date());
        articleMapper.save(article);
    }
}
