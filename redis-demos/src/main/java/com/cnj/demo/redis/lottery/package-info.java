/**
 * redis实现抽奖
 * 曾几何时，抽奖是互联网APP热衷的一种推广、拉新的方式，节假日没有好的策划，那就抽个奖吧！一堆用户参与进来，然后随机抽取几个幸运用户给予实物/虚拟的奖品；
 * 此时，开发人员就需要写上一个抽奖的算法，来实现幸运用户的抽取；其实我们完全可以利用Redis的集合（Set），就能轻松实现抽奖的功能；
 *
 * 功能实现需要的API
 * SADD key member1[member2]： 添加一个或多个参与用户
 * SRANDMEMBER key [count]: 随机返回一个或多个用户
 * SPOP key: 随机删除一个或多个用户,并返回删除的用户
 * SRANDMEMBER 适用于可以重复抽取的情况。
 * SPOP 适用于只能抽取一次的情况，用的比较多。
 * @author czz
 * @since 2023/1/30 下午9:05
 */
package com.cnj.demo.redis.lottery;

/*
 * redis-cli 命令
 * 1.添加元素
 * sadd users user1 user2 user3 user4 user5 user6 user7
 * > (integer) 7
 * 2.抽取用户
 * srandmember users 2
 * 1) "user2"
 * 2) "user3"
 * 3.删除元素
 * spop users 2
 * 1) "user2"
 * 2) "user3"
 *
 */