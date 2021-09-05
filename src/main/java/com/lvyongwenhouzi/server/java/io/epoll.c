#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdlib.h>
#include <assert.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <sys/epoll.h>

#define MAX_FD_NUM 1024
#define MAXLEN 1024

int buf_len = 0;

/**
 * Java-NIO分析-5-I-O多路复用之epoll系统调用
 * 参考链接：http://sound2gd.wang/2018/07/01/Java-NIO%E5%88%86%E6%9E%90-5-I-O%E5%A4%9A%E8%B7%AF%E5%A4%8D%E7%94%A8%E4%B9%8Bepoll%E7%B3%BB%E7%BB%9F%E8%B0%83%E7%94%A8/
 */
int main()
{
    // 1. 新建socket, 用于监听端口
    int listenfd = socket(AF_INET, SOCK_STREAM, 0);
    if (listenfd == -1) printf("创建socket失败， error: %s (errno: %d)\n", strerror(errno), errno);

    // 2. 绑定端口
    unsigned short listenPort = 8090;
    struct sockaddr_in server_addr;
    memset(&server_addr, 0, sizeof(server_addr));
    server_addr.sin_family = AF_INET;
    server_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    server_addr.sin_port = htons(listenPort);

    int on = 1;
    // 设置socket绑定的端口，再程序关闭之后可以重复使用
    if ((setsockopt(listenfd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(int))) < 0) {
        exit(1);
    }
    int bindRet = bind(listenfd, (struct sockaddr *) &server_addr, sizeof(server_addr));
    if (bindRet == -1) {
        printf("socket绑定地址失败， error: %s (errno: %d)\n", strerror(errno), errno);
        exit(1);
    }

    // 3. 监听端口
    int listenRet = listen(listenfd, 10);
    if (listenRet == -1) printf("socket监听端口失败， error: %s (errno: %d)\n", strerror(errno), errno);
    printf("socket 监听完毕, 地址: 127.0.0.1:%d", listenPort);

    struct sockaddr_in client_addr;
    socklen_t client_addr_len = sizeof(struct sockaddr_in);

    // 4. 创建一个 epfd，并且把 listenfd 注册到这个 epfd上。
    int epfd = epoll_create(1024);
    struct epoll_event ev,events[20];
    ev.data.fd = listenfd;
    ev.events = EPOLLIN;
    epoll_ctl(epfd, EPOLL_CTL_ADD, listenfd, &ev);

    int cur_fd_num = 1;
    char buf[MAXLEN]={0};

    while (1) {
        // 5. 调用epoll_wait获取IO事件
        // nReady 就是 events 数组的长度。
        int nready = epoll_wait(epfd, events, 20, 50);

        int i = 0;
        for (; i < nready; i++) {
            if (events[i].data.fd == listenfd) {
                int client_sockfd = accept(listenfd,(struct sockaddr*)&client_addr,&client_addr_len);

                if(client_sockfd < 0) {
                    perror("accept");
                }
                else {
                    printf("accept client_addr %s\n",inet_ntoa(client_addr.sin_addr));
                    ev.data.fd = client_sockfd;
                    ev.events=EPOLLIN;
                    epoll_ctl(epfd, EPOLL_CTL_ADD, client_sockfd, &ev);
                }
            }
            else if (events[i].events & EPOLLIN) {
                int connfd = events[i].data.fd;
                int n = recv(connfd, buf, MAXLEN, 0);
                if(n <= 0) {
                    if(ECONNRESET == errno) {
                        close(connfd);
                        epoll_ctl(epfd, EPOLL_CTL_DEL, connfd, 0);
                    }
                    else {
                        perror("recv");
                    }
                }

                printf("receive %s", buf);
                buf_len = n;

                ev.data.fd = connfd;
                ev.events = EPOLLOUT;
                epoll_ctl(epfd, EPOLL_CTL_MOD, connfd, &ev);
            }
            else if (events[i].events & EPOLLOUT) {
                int connfd = events[i].data.fd;
                write(connfd, buf, buf_len);

                ev.data.fd = connfd;
                ev.events = EPOLLIN;
                epoll_ctl(epfd, EPOLL_CTL_MOD, connfd, &ev);
            }
        }
    }

    return 0;
}
