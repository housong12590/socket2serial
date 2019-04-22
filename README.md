
### winsw 命令如下

- install将服务安装到Windows服务控制器。此命令需要安装指南中描述的一些初步步骤。
- uninstall卸载服务。与上述相反的操作。
- start开始服务。该服务必须已安装。
- stop 停止服务。
- restart重启服务。如果该服务当前未运行，则此命令的作用如下start。
- status 检查服务的当前状态。此命令将一行打印到控制台。
    - NonExistent 表示当前未安装该服务
    - Started 表示该服务当前正在运行
    - Stopped 表示该服务已安装但当前未运行。