package com.cnj.demo.redis.article;

import com.cnj.demo.redis.article.LikeArticleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author czz
 * @since 2023/1/31 下午9:39
 */
@Slf4j
@SpringBootTest
public class LikeArticleServiceTest {

    @Autowired
    LikeArticleService likeArticleService;

    private final String articleId = "100";

    /**
     * 点赞
     */
    @Test
    public void like() {
        log.info("user1 点赞了");
        likeArticleService.like(articleId, "user1");
        log.info("user2 点赞了");
        likeArticleService.like(articleId, "user2");
        log.info("user3 点赞了");
        likeArticleService.like(articleId, "user3");
        log.info("user4 点赞了");
        likeArticleService.like(articleId, "user4");
        log.info("当前点赞数: {}", likeArticleService.countLike(articleId));
    }

    /**
     * 取消点赞
     */
    @Test
    public void unlike() {
        log.info("当前点赞数: {}", likeArticleService.countLike(articleId));
        log.info("user3 取消了点赞");
        likeArticleService.unlike(articleId, "user3");
        log.info("当前点赞数: {}", likeArticleService.countLike(articleId));
    }

    /**
     * 是否点赞了
     */
    @Test
    public void userIsLike() {
        log.info("user2 点赞了吗? {}", likeArticleService.userIsLike(articleId, "user2") ? "点了" : "没有");
        log.info("user3 点赞了吗? {}", likeArticleService.userIsLike(articleId, "user3") ? "点了" : "没有");
    }

    /**
     * 统计点赞数
     */
    @Test
    public void countLike() {
        log.info("当前点赞数: {}", likeArticleService.countLike(articleId));
    }

}
