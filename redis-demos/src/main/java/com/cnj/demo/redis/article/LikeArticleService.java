package com.cnj.demo.redis.article;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author czz
 * @since 2023/1/31 下午9:31
 */
@Service
public class LikeArticleService {

    public static final String LIKE_ARTICLE_PREFIX ="like:article:";

    private final StringRedisTemplate redisTemplate;

    public LikeArticleService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 点赞
     * @param articleId
     * @param userId
     */
    public void like(String articleId, String userId){
        redisTemplate.opsForSet().add(LIKE_ARTICLE_PREFIX + articleId, userId);
    }

    /**
     * 取消点赞
     * @param articleId
     * @param userId
     */
    public void unlike(String articleId, String userId){
        redisTemplate.opsForSet().remove(LIKE_ARTICLE_PREFIX + articleId, userId);
    }

    /**
     * 是否点赞了
     * @param articleId
     * @param userId
     * @return
     */
    public boolean userIsLike(String articleId, String userId){
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(LIKE_ARTICLE_PREFIX + articleId, userId));
    }

    /**
     * 统计点赞数
     * @param articleId
     * @return
     */
    public Long countLike(String articleId){
        return redisTemplate.opsForSet().size(LIKE_ARTICLE_PREFIX + articleId);
    }

}
