#include <sys/select.h>
#include <sys/socket.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include <netinet/in.h>
#include <stdlib.h>
#include <unistd.h>
/**
 * Java-NIO分析-3-I-O多路复用之select系统调用
 * 参考链接：http://sound2gd.wang/2018/06/28/Java-NIO%E5%88%86%E6%9E%90-3-I-O%E5%A4%9A%E8%B7%AF%E5%A4%8D%E7%94%A8%E4%B9%8Bselect%E7%B3%BB%E7%BB%9F%E8%B0%83%E7%94%A8/
 */
int main() {
    // 1. 新建socket, 用于监听端口
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
    // 设置socket绑定的端口，再程序关闭之后可以重复使用
    if ((setsockopt(listenfd, SOL_SOCKET, SO_REUSEADDR, &on, sizeof(int))) < 0) {
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

    // 4. 初始化fd_set
    int client[FD_SETSIZE]; // 保存已连接fd
    for (int i = 0; i < FD_SETSIZE; ++i) {
        client[i] = -1;
    }
    int maxfd = listenfd, i, maxi = -1, MAXLEN = 1024;
    char buf[MAXLEN];

    fd_set rset, allset; // fd_set代表的是描述符集，本质上是一个整数, 根据操作系统的不同有可能是64位或者32位，通过每位的0或者1来判断fd是否就绪
    FD_ZERO(&allset); // 清零
    FD_SET(listenfd, &allset); // 把listenfd加入到allset
    printf("ready");

    while (1) {
        rset = allset;
        // 函数签名int select(int maxfdpl, fd_set *readset, fd_set *writeset, fd_set *exceptset, const struct timeval *timeout)
        // 返回值表示fd_set里就绪的元素总个数,包括读,写,异常fd_set
        // 第一个参数表示待测试的fd个数,值一般是待测试的fd总数+1
        // 中间仨代表要监听的读set, 写set和异常set
        // 最后一个参数代表select每个fd经历的时间
        // 5. select获取 连接fd,断开连接fd, 有数据可读fd
        int nready = select(maxfd + 1, &rset, NULL, NULL, NULL);
        if (FD_ISSET(listenfd, &rset)) { // 接受新连接
            struct sockaddr_in clientaddr;
            int clilen = sizeof(clientaddr);
            int connfd = accept(listenfd, (struct sockaddr *) &clientaddr, &clilen);

            // 保存
            for (i = 0; i < FD_SETSIZE; ++i) {
                if (client[i] < 0) {
                    client[i] = connfd;
                    break;
                }
            }

            if (i == FD_SETSIZE) exit(1);

            FD_SET(connfd, &allset);
            if (connfd > maxfd) maxfd = connfd;
            if (i > maxi) maxi = i;
            if (--nready <= 0) continue;
        }

        for (i = 0; i <= maxi; i++) {
            int sockfd = client[i];
            if (sockfd < 0) continue;

            if (FD_ISSET(sockfd, &rset)) {
                int n = recv(sockfd, buf, MAXLEN, 0);
                if (n == 0) { // 读到0表示连接关闭
                    close(sockfd);
                    FD_CLR(sockfd, &allset);
                    client[i] = -1;
                } else write(sockfd, buf, n);

                if (--nready == 0) break;
            }
        }
    }
}
