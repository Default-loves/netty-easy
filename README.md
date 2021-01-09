# Lean Netty
### 资源

公众号资源：https://github.com/fuzhengwei/itstack-demo-netty

案例：https://hub.fastgit.org/waylau/netty-4-user-guide-demos.git

### 总述

Netty是一个高性能的异步事件驱动的网络通信框架，Netty对JDK原生NIO进行封装，提供更加易于使用的API，简化了网络服务的开发。

![image](F:\GithubMy\my\netty-easy\pic\netty.png)





### 优点

- 易用性
- 性能很好
- 稳定
- 可扩展
- 资源消耗低
  - Netty通过复用对象，避免频繁创建和销毁对象的开销。
  - 零拷贝技术。减少了在缓冲区之间的拷贝

### Linux的5中IO模型

- 同步阻塞IO（BIO）
- 同步非阻塞IO（NIO）
- IO多路复用
- 信号驱动IO
- 异步IO

一个多路复用器 Selector 可以同时轮询多个 Channel，采用 epoll 模式后，只需要一个线程负责 Selector 的轮询，就可以接入成千上万的客户端。

事件分发器（Event Dispather），它负责将读写事件分发给对应的读写事件处理器（Event Handler）





### Netty整体架构

![components](F:\GithubMy\my\netty-easy\pic\components.png)





### ByteBuf

1. 向不可写入的Buffer写入，会尝试扩容，但是capacity不会超过maxCapacity，如果写入的数据超过了maxCapacity，那么会报IndexOutOfBoundsException的错误
2. read/write系列方法会改变readIndex/writeIndex的位置，而get/set系列的方法则不会





### 零拷贝

 Java 中也使用了零拷贝技术，它就是 NIO FileChannel 类中的 transferTo() 方法，transferTo() 底层就依赖了操作系统零拷贝的机制，它可以将数据从 FileChannel 直接传输到另外一个 Channel。

在Linux系统中，Linux 2.4 版本之后，开发者对 Socket Buffer 追加一些 Descriptor 信息来进一步减少内核数据的复制。DMA 引擎读取文件内容并拷贝到内核缓冲区，然后并没有再拷贝到 Socket 缓冲区，只是将数据的长度以及位置信息被追加到 Socket 缓冲区，然后 DMA 引擎根据这些描述信息，直接从内核缓冲区读取数据并传输到协议引擎中，从而消除最后一次 CPU 拷贝。

而Netty主要的是实现



### 对象池技术

对象的频繁创建和销毁容易耗费CPU，而对象往往是可以重用的，和线程类似，我们也可以创建一个对象池，将不再使用的对象不执行销毁操作而是放回对象池中，等待再次使用



### HashedWheelTimer

NioEventLoop 不仅负责处理 I/O 事件，而且兼顾执行任务队列中的任务，其中就包括定时任务。



### 阻塞队列

Netty使用了JCTools中的Mpsc（ Multi Producer Single Consumer）Queue，相比较于JDK原生的并发队列，性能更好。其能够保证多个生产者访问队列是线程安全的，而只有一个消费者从队列中获取内容

Mpsc Queue 为了解决伪共享问题填充了大量的 long 类型变量，入队和出队操作中都大量使用了 UNSAFE 系列方法





### HttpObjectAggregator

HttpObjectAggregator 是 Netty 提供的 HTTP 消息聚合器，通过它可以把 HttpMessage 和 HttpContent 聚合成一个 FullHttpRequest 或者 FullHttpResponse(取决于是处理请求还是响应），方便我们使用。

另外，消息体比较大的话，可能还会分成好几个消息体来处理，HttpObjectAggregator 可以将这些消息聚合成一个完整的，方便我们处理。

常用的使用方法：

```java
ChannelPipeline p = ...;
...
p.addLast("decoder", new HttpRequestDecoder());
p.addLast("encoder", new HttpResponseEncoder());
p.addLast("aggregator", new HttpObjectAggregator(1024 * 1024));
...
p.addLast("handler", new HttpRequestHandler());
```

