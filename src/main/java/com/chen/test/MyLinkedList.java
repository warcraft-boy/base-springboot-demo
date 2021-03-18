package com.chen.test;

/**
 * @Description: 手写一个双向链表
 * @Author chenjianwen
 * @Date 2021/3/10
 **/
public class MyLinkedList<E> {

    private Node<E> first; //该链表的首节点
    private Node<E> last; //该链表的尾节点
    private int size; //改链表的大小

    /**
     * 从首部节点插入数据，那么该数据成为新的首节点
     * @param e
     */
    public void linkFirst(E e){
        Node<E> firstNode = first; //将成员变量固化为一个局部变量，方便赋值操作
        //新建一个新的节点，并赋值，该节点从头位置插入的，所以他是新的头节点，他的下一个节点一定是原来链表的头节点
        Node<E> newNode = new Node<>();
        newNode.e = e;
        newNode.next = firstNode;
        first = newNode;
        //原来头节点有可能为空
        //若为空，则该链表只有这一个插入的节点，既是首节点，也是尾节点
        //若不为空，则建立原来首节点指向新的首节点的指针
        if(firstNode == null){
            last = newNode;
        }else{
            firstNode.pre = newNode;
        }
        size++;
    }

    /**
     *从尾部节点插入数据，那么该数据成为新的尾节点
     * @param e
     */
    public void linkLast(E e){
        Node<E> lastNode = last;
        Node<E> newNode = new Node<>();
        newNode.e = e;
        newNode.pre = lastNode;
        last = newNode;
        if(lastNode == null){
            first = newNode;
        }else{
            lastNode.next = newNode;
        }
        size++;
    }

    /**
     * 在某个已知节点之前插入一个数据
     * @param e
     * @param one
     */
    public void linkBefore(E e, Node<E> one){
        Node<E> before = one.pre; //该已知节点之前的节点
        Node<E> newNode = new Node<>();
        newNode.e = e;
        newNode.pre = before;
        newNode.next = one;
        one.pre = newNode;
        if(before == null){
            first = newNode;
        }else{
            before.next = newNode;
        }
        size++;
    }

    /**
     * 节点内部类
     * @param <E>
     */
    private static class Node<E> {
        E e; //该节点的数据
        Node<E> pre; //该节点的上一个节点
        Node<E> next; //该节点的下一个节点
    }
}
