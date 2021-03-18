package com.chen.test;

/**
 * @Description: 手写一个单向链表
 * @Author chenjianwen
 * @Date 2021/3/11
 **/
public class MySingleLinkedList<E> {

    private Node<E> first; //该链表的头节点
    private Node<E> last; //该链表的尾节点
    private int size; //该链表的大小

    /**
     * 从头部加入
     * @param e
     */
    public void linkFirst(E e){
        Node<E> firstNode = first;
        Node<E> newNode = new Node<>();
        newNode.e = e;
        first = newNode;
        newNode.next = firstNode;
        if(firstNode == null){
            last = newNode;
        }
        size++;
    }

    /**
     * 从尾部加入
     * @param e
     */
    public void linkLast(E e){
        Node<E> lastNode = last;
        Node<E> newNode = new Node<>();
        newNode.e = e;
        last = newNode;
        if(lastNode == null){
            first = newNode;
        }else{
            lastNode.next = newNode;
        }
        size++;
    }

    /**
     * 从某个节点后面加入
     * @param e
     * @param one
     */
    public void linkAfter(E e, Node<E> one){
        Node<E> after = one.next;
        Node<E> newNode = new Node<>();
        newNode.e = e;
        one.next = newNode;
        if(after == null){
            last = newNode;
        }else{
            newNode.next = after;
        }
        size++;
    }

    /**
     * 单向链表的每一个节点只有指向下一个节点的next，没有指向上一个节点的pre;
     * @param <E>
     */
    private static class Node<E> {
        E e;
        Node<E> next;
    }
}
