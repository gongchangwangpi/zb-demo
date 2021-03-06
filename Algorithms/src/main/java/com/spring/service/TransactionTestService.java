package com.spring.service;

/**
 * 脏读（Dirty Read）          不可重复读（NonRepeatable Read）     幻读（Phantom Read）
 *
 * 脏读:就是指当一个事务正在访问数据，并且对数据进行了修改，而这种修改还没有提交到数据库中，
 *      这时，另外一个事务也访问这个数据，然后使用了这个数据。
 *
 * 不可重复读:是指在一个事务内，多次读同一数据。在这个事务还没有结束时，另外一个事务也访问该同一数据。
 *      那么，在第一个事务中的两次读数据之间，由于第二个事务的修改，那么第一个事务两次读到的的数据可能是不一样的。
 *      这样就发生了在一个事务内两次读到的数据是不一样的，因此称为是不可重复读。
 *
 * 幻读:第一个事务对一个表中的数据进行了修改，这种修改涉及到表中的全部数据行。
 *      同时，第二个事务也修改这个表中的数据，这种修改是向表中插入一行新数据。
 *      那么，以后就会发生操作第一个事务的用户发现表中还有没有修改的数据行，就好象发生了幻觉一样。
 *
 *
 * 隔离级别               脏读（Dirty Read）          不可重复读（NonRepeatable Read）     幻读（Phantom Read）
 ===========================================================================================
 未提交读（Read uncommitted）        可能                            可能                       可能
 已提交读（Read committed）          不可能                          可能                       可能
 可重复读（Repeatable read）         不可能                          不可能                     可能
 可串行化（Serializable ）           不可能                          不可能                     不可能
 ===========================================================================================
 ·未提交读(Read Uncommitted)：允许脏读，也就是可能读取到其他会话中未提交事务修改的数据
 ·提交读(Read Committed)：只能读取到已经提交的数据。Oracle等多数数据库默认都是该级别 (不重复读)
 ·可重复读(Repeated Read)：可重复读。在同一个事务内的查询都是事务开始时刻一致的，InnoDB默认级别。在SQL标准中，该隔离级别消除了不可重复读，但是还存在幻象读
 ·串行读(Serializable)：完全串行化的读，每次读都需要获得表级共享锁，读写相互都会阻塞
 *
 * Created by books on 2017/4/19.
 *
 */
public interface TransactionTestService {

    /**
     * 脏读（Dirty Read）
     * 不可重复读（NonRepeatable Read）
     * 幻读（Phantom Read）
     *
     */
    void readUncommitted();

    /**
     * 不可重复读（NonRepeatable Read）
     * 幻读（Phantom Read）
     */
    void readCommitted();

    /**
     * 幻读（Phantom Read）
     */
    void repeatableRead();

    /**
     * 不会造成以上情况，但性能最差
     */
    void serializable ();

}
