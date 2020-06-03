# rabbitmq-demo

RabbitMQ是使用Erlang语言来编写的，并且RabbitMQ是基于AMQP协议的。
Erlang语言在数据交互方面性能优秀，有着和原生Socket一样的延迟，这也是RabbitMQ高性能的原因所在。可谓“人如其名”，RabbitMQ像兔子一样迅速。

RabbitMQ除了像兔子一样跑的很快以外，还有这些特点：

    开源、性能优秀，稳定性保障
    提供可靠性消息投递模式、返回模式
    与Spring AMQP完美整合，API丰富
    集群模式丰富，表达式配置，HA模式，镜像队列模型
    保证数据不丢失的前提做到高可靠性、可用性

MQ典型应用场景：

    异步处理。把消息放入消息中间件中，等到需要的时候再去处理。
    流量削峰。例如秒杀活动，在短时间内访问量急剧增加，使用消息队列，当消息队列满了就拒绝响应，跳转到错误页面，这样就可以使得系统不会因为超负载而崩溃。
    日志处理
    应用解耦。假设某个服务A需要给许多个服务（B、C、D）发送消息，当某个服务（例如B）不需要发送消息了，服务A需要改代码再次部署；当新加入一个服务（服务E）需要服务A的消息的时候，也需要改代码重新部署；另外服务A也要考虑其他服务挂掉，没有收到消息怎么办？要不要重新发送呢？是不是很麻烦，使用MQ发布订阅模式，服务A只生产消息发送到MQ，B、C、D从MQ中读取消息，需要A的消息就订阅，不需要了就取消订阅，服务A不再操心其他的事情，使用这种方式可以降低服务或者系统之间的耦合。

