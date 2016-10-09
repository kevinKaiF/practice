/*
 * Copyright (c) 2001-2016 Bidlink(Beijing) E-Biz Tech Co.,Ltd.
 * All rights reserved.
 * 必联（北京）电子商务科技有限公司 版权所有
 */

package y2016.m07.day20160704;

import org.apache.commons.cli.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import java.text.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

/**
 * Canal命令行工具
 *
 * @author : <a href="mailto:liujie@ebnew.com">刘杰</a>
 * @version : 0.0.1
 * @date : 16-6-20 15:55
 * @since : 0.0.1
 */
public class CanalCli {
    public CanalCli() {
        try {
            afterPropertiesSet();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Commons Cli Option.
     *
     * @author :<a href="mailto:liujie@ebnew.com">刘杰</a>
     * @date :2016-06-20 15:56:26
     */
    private Options opts = new Options();

    /**
     * Commons-Cli帮助格式化对像.
     *
     * @author :<a href="mailto:liujie@ebnew.com">刘杰</a>
     * @date :2016-06-20 15:57:41
     */
    private HelpFormatter formatter = new HelpFormatter();

    /**
     * 处于交互模式中.
     *
     * @author :<a href="mailto:liujie@ebnew.com">刘杰</a>
     * @date :2016-06-20 15:55:02
     */
    private static boolean interacting = false;

    /**
     * Commons-Cli命令行解析器.
     *
     * @author :<a href="mailto:liujie@ebnew.com">刘杰</a>
     * @date :2016-06-20 16:00:58
     */
    private CommandLineParser parser = new DefaultParser();


    public void afterPropertiesSet() throws Exception {
        //添加命令
        opts.addOption("h", "help", false, "查看命令帮助.");
        opts.addOption("i", "interactive", false, "进入Canal客户端交互模式.");
        opts.addOption("q", "quit", false, "退出Canal客户端交互模式.");
        opts.addOption("c", "check", true, "检查Canal实例.");
        opts.addOption("a", "add", true, "添加Canal实例.");
        opts.addOption("s", "start", true, "启动Canal实例.");
        opts.addOption("S", "stop", true, "停用Canal实例.");
        opts.addOption("swd", "startWithDelay", false, "后移binlog解析时间(h)，启动Canal所有实例.\n时间格式支持yyyy-MM-dd_HH:mm和yyyy-MM-dd_HH:mm:ss");
        opts.addOption("SD", "shutdown", false, "关闭Canal，停用所有实例.");
        opts.addOption("d", "destroy", true, "禁用Canal实例，以后不会启动");
        opts.addOption("e", "enable", true, "启用Canal实例");
    }

    /**
     * 打印帮助信息.
     *
     * @author :<a href="mailto:liujie@ebnew.com">刘杰</a>
     * @date :2016-06-20 15:59:40
     */
    protected void printHelp() {
        String helpInfo = "[-h/--help][-c/--check][-a/--add][-s/--start][-S/--stop][-i/--interactive]" +
                "[-q/--quit][-swd/--startWithDelay][-SD/--shutdown][-d/--destroy][-e/--enable]";
        formatter.printHelp(helpInfo, "", opts, "");
    }

    /**
     * 解析命令.
     *
     * @param args the args
     * @author :<a href="mailto:liujie@ebnew.com">刘杰</a>
     * @date :2016-06-20 16:32:48
     */
    protected void parseCommand(String[] args) {
        try {
            CommandLine cmd = parser.parse(opts, args);

            if (ArrayUtils.isEmpty(cmd.getOptions())) {
                printHelp();
            } else {
                if (cmd.hasOption("h")) {
                    printHelp();
                } else if (cmd.hasOption("i")) {
                    interactive();
                } else if (cmd.hasOption("q")) {
                    interactiveQuit();
                } else if (cmd.hasOption("c")) {
                    check(cmd);
                } else if (cmd.hasOption("a")) {
                    add(cmd);
                } else if (cmd.hasOption("s")) {
                    start(cmd);
                } else if (cmd.hasOption("S")) {
                    stop(cmd);
                } else if(cmd.hasOption("SD")) {
                    shutdown(cmd);
                } else if(cmd.hasOption("swd")) {
                    startWithDelay(cmd);
                } else if (cmd.hasOption("d")) {
                    destroy(cmd);
                } else if(cmd.hasOption("e")) {
                    enable(cmd);
                }else {
                    printHelp();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 退出交互模式.
     *
     * @author :<a href="mailto:liujie@ebnew.com">刘杰</a>
     * @date :2016-06-20 16:19:47
     */
    private void interactiveQuit() {
        interacting = false;
    }

    /**
     * 进入交互模式.
     *
     * @author :<a href="mailto:liujie@ebnew.com">刘杰</a>
     * @date :2016-06-20 16:10:42
     */
    private void interactive() {
        if (!interacting) {
            System.out.println("--进入Canal命令行交互模式--");
            interacting = true;
            try (Scanner scanner = new Scanner(System.in)) {
                while (interacting) {
                    try {
                        System.out.print("canal-cli交互模式(-q退出): ");
                        String cmdLine = scanner.nextLine();
                        if (StringUtils.isNotBlank(cmdLine)) {
                            System.out.println(cmdLine);
                            String[] args = StringUtils.split(cmdLine, " ");
                            parseCommand(args);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("--退出Canal命令行交互模式，再见--");
        } else {
            System.out.println("当前已经处于交互模式中!");
        }
    }

    /**
     * 关闭canal，停掉所有的实例
     *
     * @param cmd the cmd
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-07-01 13:21:14
     */
    public void shutdown(CommandLine cmd) {
        execution("shutdown");
    }

    /**
     * 启动，并后移binlog解析时间.
     *
     * @param cmd the cmd
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-07-01 13:16:54
     */
    private void startWithDelay(CommandLine cmd) {
        String[] params = cmd.getArgs();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(params[0]);
        } catch (java.text.ParseException e) {
            sdf = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
            try {
                date = sdf.parse(params[0]);
            } catch (java.text.ParseException e1) {
                throw new RuntimeException("请输入合法的时间参数");
            }
        }
        execution(date, "startWithDate");
    }

    /**
     * 添加.
     *
     * @param cmd the cmd
     * @author :<a href="mailto:liujie@ebnew.com">刘杰</a>
     * @date :2016-06-20 16:08:00
     */
    private void add(CommandLine cmd) {
        String name = cmd.getOptionValue("add");
        String[] params = cmd.getArgs();
        System.out.println(name + ":" + Arrays.toString(params));
    }

    /**
     * 启动.
     *
     * @param cmd the cmd
     * @author :<a href="mailto:liujie@ebnew.com">刘杰</a>
     * @date :2016-06-20 16:10:26
     */
    private void start(CommandLine cmd) {
        String name = cmd.getOptionValue("start");
        System.out.println(name);
    }

    /**
     * 停止.
     *
     * @param cmd the cmd
     * @author :<a href="mailto:liujie@ebnew.com">刘杰</a>
     * @date :2016-06-20 16:07:51
     */
    private void stop(CommandLine cmd) {
        String name = cmd.getOptionValue("stop");
        System.out.println(name);
    }


    /**
     * 检测.
     *
     * @param cmd the cmd
     * @author :<a href="mailto:liujie@ebnew.com">刘杰</a>
     * @date :2016-06-20 16:07:44
     */
    private void check(CommandLine cmd) {
        String name = cmd.getOptionValue("enable");
        System.out.println(name);
    }

    /**
     * 销毁.
     *
     * @param cmd the cmd
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-07-01 17:58:27
     */
    private void destroy(CommandLine cmd) {
        String name = cmd.getOptionValue("destroy");
        System.out.println(name);
    }

    /**
     * 启用.
     * NOTE:这个启用会将销毁掉的Canal实例启动
     *
     * @param cmd the cmd
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-07-04 10:25:13
     */
    private void enable(CommandLine cmd) {
        String name = cmd.getOptionValue("enable");
        System.out.println(name);
    }



    /**
     * 执行客户端命令
     *
     * @param methodName the method name
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-07-01 15:34:35
     */
    private void execution(String methodName) {
    }

    /**
     * 执行客户端命令.
     *
     * @param delayTimeStamp the delay time stamp
     * @param methodName     the method name
     * @author : <a href="mailto:zikaifeng@ebnew.com">冯子恺</a>
     * @date : 2016-07-01 15:34:58
     */
    private void execution(Date delayTimeStamp, String methodName) {
    }

    public static void main(String[] args) {
        CanalCli canalCli = new CanalCli();
        if(args.length > 0) {
            canalCli.parseCommand(args);
        } else {
            canalCli.parseCommand(new String[] {"-i"});
        }
    }
}
