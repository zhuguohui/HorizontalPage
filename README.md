# HorizontalPage
一行代码让RecyclerView变身ViewPager
#序言

我曾经写过一个[使用RecycleView打造水平分页GridView](http://blog.csdn.net/qq_22706515/article/details/52266641)。当时用到的是对数据的重排序，但是这样处理还是有些问题，比如用户数据更新以后还需要继续重排序，包括对滑动事件的处理也不是很好。当时主要因为时间比较匆忙，写的不是很好，这一次我将采用自定义LayoutManger的方式实现水平分页的排版，使用一个工具类实现一行代码就让RecycleView具有分页滑动的特性。

#效果

1.水平分页的效果(采用了自定义LayoutManger+滑动工具类实现)，关键是不需要修改Adapter，可以用来实现表情列表，或者是商品列表。

![这里写图片描述](http://img.blog.csdn.net/20161115152537126)

2.垂直方向的分页显示，可以实现读报的功能，或者其他需要一页一页阅读的功能，采用了LinearLayoutManger+滑动工具类实现，比使用LinearLayout布局的优势在于实现了View的复用。

![这里写图片描述](http://img.blog.csdn.net/20161115152758593)

3.水平分页，这是使用LinearLayoutManger+分页滑动工具类实现的，这样LinearLayout就可以横向的一页一页显示，用这个实现Banner要比ViewPager要简单很多，性能也会有所提高。因为ViewPager自己并没有缓存机制。

![这里写图片描述](http://img.blog.csdn.net/20161115153028518)

其实还可以实现很多其他的功能，限于我的想象力有限就先举这些例子吧。

#使用

1.要想数据按一页一页的排列就使用HorizontalPageLayoutManager，在构造方法中传入行数和列数就行了
```java
   //构造HorizontalPageLayoutManager,传入行数和列数
   horizontalPageLayoutManager = new HorizontalPageLayoutManager(3,4);
   //这是我自定义的分页分割线，样式是每一页的四周没有分割线。大家喜欢可以拿去用
   pagingItemDecoration = new PagingItemDecoration(this, horizontalPageLayoutManager);

```
2.分页滚动，上一步的HorizontalPageLayoutManager只负责Item分页的排列和回收，而要实现分页滚动需要使用PagingScrollHelper 这个工具类。注意这个工具类很强的，使用其他的LayoutManger也可以和这个工具类共同使用实现分页效果。

```
  PagingScrollHelper scrollHelper = new PagingScrollHelper();
  scrollHelper.setUpRecycleView(recyclerView);
  //设置页面滚动监听
  scrollHelper.setOnPageChangeListener(this);
```
滑动监听类

```

    public interface onPageChangeListener {
        void onPageChange(int index);
    }
```
**注意**

1。用于使用了RecyclerView的OnFlingListener，所以RecycleView的版本必须要25以上。

![这里写图片描述](http://img.blog.csdn.net/20161115154731900)

2。如果想使用自定义的LayoutManger实现分页滑动，则必须实现LayoutManger的这两个方法之一，因为工具类是通过这两个方法判断应该怎么滚动的。

```

        /**
         * Query if horizontal scrolling is currently supported. The default implementation
         * returns false.
         *
         * @return True if this LayoutManager can scroll the current contents horizontally
         */
        public boolean canScrollHorizontally() {
            return false;
        }

        /**
         * Query if vertical scrolling is currently supported. The default implementation
         * returns false.
         *
         * @return True if this LayoutManager can scroll the current contents vertically
         */
        public boolean canScrollVertically() {
            return false;
        }
```
