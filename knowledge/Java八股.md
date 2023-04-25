# 集合类八股

1.Java的集合类有哪些，是如何分类、实现的？

2.Collection与Collections有什么区别？

3.Java的Collection如何实现遍历/迭代？

4.Iterable与Iterator如何使用？

5.为什么不把Iterator与Iterable合并在一起使用？

6.你能说出集合的几种排序方式？

7.为什么有了Comparable还需要Comparator？

8.compareTo和equals的实现场景有何区别？

9.既然Set是无序的，还怎么排序？

10.Set真的插入无序吗？

11.什么是fail-fast，什么是fail-safe？

12.集合中fail-fast的异常原理？

13.什么是Copy-on-Write？

14.Copy-on-Write既然已经复制副本了，为什么修改方法还要加锁？

15.Copy-on-Write容器与Vector的区别？

16.遍历的同时修改一个list有几种方式？

17.Set是如何保证元素不重复的？

18.HashSet、TreeSet、LinkedHashSet、BitSet有什么区别？

19.BitSet有什么作用，它的缺点是什么？

20.ArrayList、LinkedList与Vector有什么区别？

21.ArrayList是如何扩容的？

22.如何用List实现LRU？

23.ArrayList的SubList方法有什么需要注意的地方吗？

24.如何通过原list拷贝一个新的list？

25.ArrayList的序列化是怎么实现的？

26.为什么底层数组要使用transient?

27.hash冲突怎么解决？

28.HashMap的数据结构是怎样的？

29.HashMap、HashTable、ConcurrentHashMap有什么区别？

30.HashMap在进行get或put会进行哪些步骤？

31.HashMap如何定位key？

32.HashMap定位tableIndex的骚操作？

33.HashMap的key值为空，没有hashcode怎么存储？

34.HashMap的value为空吗？有什么缺点？

35.为什么HashMap的容量是2^n，如何保证？

36.负载因子和容量有什么关系？

37.为什么HashMap的负载因子设置为0.75？

38.HashMap容量设置为多少合适？

39.HashMap是如何扩容的？

40.除了rehash外，还有哪些操作会取消树化？

41.为什么JDK8中的链表要转化为红黑树？

42.HashMap为什么不冲突直接转红黑树？

43.HashMap为什么长度大于8的时候再转？

44.为什么长度为6的时候转回来？

45.HashMap的双向链表是怎么回事？

46.HashMap没有比较能力，为什么红黑树可以比较？

47.HashMap用跳表代替红黑树是否可以？

48.HashMap的hash方法如何实现的？

49.HashMap的remove方法是如何实现的？

50.ConcurrentHashMap是如何保证线程安全的？

51.ConcurrentHashMap在哪些方面进行了并发控制？

52.ConcurrentSkipList和ConcurrentHashMap有什么区别？

53.SynchnoizedList和Vetcor有什么区别？

54.ConcurrentHashMap是如何保证fail-safe的？

55.如何将集合变成线程安全的？

56.HashMap在并发场景中有什么问题？

57.JDK1.8是如何解决循环引用问题的？

58.什么是COW，如何保证线程安全的？

59.Java8中的Stream是什么，都能做什么？