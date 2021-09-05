#include <sys/poll.h>
#include <sys/socket.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <unistd.h>
#include <sys/syslimits.h>

/**
 * Java-NIO分析-4-I-O多路复用之poll系统调用
 * 参考链接：http://sound2gd.wang/2018/06/28/Java-NIO%E5%88%86%E6%9E%90-3-I-O%E5%A4%9A%E8%B7%AF%E5%A4%8D%E7%94%A8%E4%B9%8Bselect%E7%B3%BB%E7%BB%9F%E8%B0%83%E7%94%A8/
 */
int main() {
    // 1. 新建tcp流式socket
    int listenfd = socket(AF_INET, SOCK_STREAM, 0);
    if (listenfd == -1) printf("创建socket失败， error: %s (errno: %d)\n", strerror(errno), errno);

    // 2. 绑定端口
    unsigned short listenPort = 8090;
    struct sockaddr_in servaddr;
    memset(&servaddr, 0, sizeof(servaddr));
    servaddr.sin_family = AF_INET;
    servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
    servaddr.sin_port = htons(listenPort);

    int on = 1;
    if ((setsockopt(listenfd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(int)))) {
        exit(1);
    }
    int bindRet = bind(listenfd, (struct sockaddr *) &servaddr, sizeof(servaddr));
    if (bindRet == -1) {
        printf("socket绑定地址失败， error: %s (errno: %d)\n", strerror(errno), errno);
        exit(1);
    }

    // 3. 监听端口
    int listenRet = listen(listenfd, 10);
    if (listenRet == -1) printf("socket监听端口失败， error: %s (errno: %d)\n", strerror(errno), errno);
    printf("socket 监听完毕, 地址: 127.0.0.1:%d", listenPort);

    // 3. 新建pollfd数组
    struct pollfd client[OPEN_MAX];
    client[0].fd = listenfd;
    client[0].events = POLL_IN;
    for (int i = 1; i < OPEN_MAX; i++) {
        client[i].fd = -1;
    }
    int maxi = 0, i = 0, MAXLEN = 1024;
    char buf[MAXLEN];

    // 4. 使用poll
    for (;;) {
        int nready = poll(client, (maxi + 1), -1);

        if (client[0].revents & POLL_IN) {
            // 接受新连接
            struct sockaddr_in clientaddr;
            int clilen = sizeof(clientaddr);
            int connfd = accept(listenfd, (struct sockaddr *) &clientaddr, &clilen);

            // 找到pollfd数组里第一个可用的pollfd
            for (i = 0; i < OPEN_MAX; i++) {
                if (client[i].fd < 0) {
                    client[i].fd = connfd;
                    break;
                }
            }

            if (i == OPEN_MAX) exit(1);
            // 设置fd感兴趣的事件
            client[i].events = POLL_MSG;
            if (i > maxi) maxi = i;
            if (--nready <= 0) continue;
        }

        for (i = 1; i <= maxi; i++) {
            struct pollfd sockfd = client[i];
            if (sockfd.fd < 0) continue;

            if (sockfd.revents & (POLL_MSG | POLL_ERR)) {
                int n = recv(sockfd.fd, buf, MAXLEN, 0);
                if (n == 0 || n < 0) {// 0表示连接关闭 <0表示连接重置
                    close(sockfd.fd);
                    client[i].fd = -1;
                } else write(sockfd.fd, buf, n);

                if (--nready < 0) break; // 没有更多的可读fd
            }
        }
    }
}
