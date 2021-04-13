package com.简单;

import java.util.Scanner;

public class 两数之和 {
    ListNode xhead = new ListNode(0);
    ListNode yhead = new ListNode(0);
    class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
    public void init() {

        Scanner sc = new Scanner(System.in);
        System.out.println("请输入第一个数");
        int x = sc.nextInt();
        System.out.println("请输入第二个数");
        int y = sc.nextInt();
        System.out.println("初始化开始...");
        char[] a = String.valueOf(x).toCharArray();
        char[] b = String.valueOf(y).toCharArray();
        ListNode t = xhead;
        for(char temp : a){
            t.next = new ListNode(Integer.parseInt(String.valueOf(temp)));
            t = t.next;
        }
        ListNode tt = yhead;
        for(char temp : b){
            tt.next = new ListNode(Integer.parseInt(String.valueOf(temp)));
            tt = tt.next;
        }
    }
    public void showTwoNode(){
        ListNode x = xhead;
        while(x.next != null){
            System.out.print(x.next.val+ " ");
            x = x.next;
        }
        System.out.println();
        ListNode y = yhead;
        while(y.next != null){
            System.out.print(y.next.val + " ");
            y = y.next;
        }
    }
    public void showOneNode(ListNode xhead){
        ListNode x = xhead;
        while(x.next != null){
            System.out.print(x.next.val+ " ");
            x = x.next;
        }
        System.out.println();
    }

    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode newListNode = new ListNode(0);
        ListNode q = newListNode;
        boolean flag = false;
        int temp;
        while(l1.next != null && l2.next != null){
            int addTwoTemp = l1.next.val + l2.next.val;
            if(flag) {
                temp = 1;
            }else {
                temp = 0;
            }
            if(addTwoTemp >= 10){
                q.next = new ListNode(addTwoTemp-10 + temp);
                q = q.next;
                flag =true;
            }else if(addTwoTemp == 9){
                q.next = new ListNode(0);
                q = q.next;
                flag = true;
            } else {
                q.next = new ListNode(addTwoTemp + temp);
                q = q.next;

                flag = false;
            }
            l1 = l1.next;
            l2 = l2.next;


        }
        return newListNode;
    }

    public static void main(String[] args) {
        两数之和 h = new 两数之和();
        h.init();
        h.showTwoNode();
        ListNode addTwo = h.addTwoNumbers(h.xhead,h.yhead);
        h.showOneNode(addTwo);
    }
}
