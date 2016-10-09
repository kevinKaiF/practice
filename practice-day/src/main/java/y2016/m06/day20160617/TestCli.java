package y2016.m06.day20160617;

import org.apache.commons.cli.*;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @author : kevin
 * @version : Ver 1.0
 * @date : 2016-06-17 AM09:35
 */
public class TestCli {
    private static Options opts = new Options();
    private static String helpInfo = "canal[-h/--help][-add][-start][-stop][exit]";

    static{
        opts.addOption("h", false, "查看命令帮助.");
        opts.addOption("h", "help",  false, "查看命令帮助.");
        opts.addOption("add", true, "添加Canal实例<name,host,userName,password,slaveId>.");
        opts.addOption("start", true, "启用Canal实例<name>.");
        opts.addOption("stop", true, "停用Canal实例<name>.");
        opts.addOption("check", true, "检查Canal实例<name>.");
    }

    public static void main(String[] args) {
        HelpFormatter formatter = new HelpFormatter();
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = null;
        formatter.printHelp(helpInfo, opts);
        Scanner scanner = new Scanner(System.in);
        while(scanner.hasNext()){
            String line = scanner.nextLine();
            if(StringUtils.isEmpty(line)){
                continue;
            }
            if(line.trim().equalsIgnoreCase("exit")){
                break;
            }

            args = line.split(" ");
            for (String arg : args) {
                arg = arg.trim();
            }



            try {
                cmd = parser.parse(opts, args);
            } catch (ParseException e) {
                formatter.printHelp(helpInfo, opts);
            }

            if (cmd.hasOption("h")) {
                formatter.printHelp(helpInfo, "", opts, "");
                continue;
            }

            if (cmd.hasOption("add")) {
                String name = cmd.getOptionValue("add");
                String[] params = cmd.getArgs();
                System.out.println(name + ":" + Arrays.toString(params));
            }

            if (cmd.hasOption("start")) {
                String name = cmd.getOptionValue("start");
                String[] params = cmd.getArgs();
                System.out.println(name + ":" + Arrays.toString(params));
            }

            if (cmd.hasOption("stop")) {
                String name = cmd.getOptionValue("stop");
                String[] params = cmd.getArgs();
                System.out.println(name + ":" + Arrays.toString(params));
            }

            if (cmd.hasOption("check")) {
                String name = cmd.getOptionValue("check");
                String[] params = cmd.getArgs();
                System.out.println(name + ":" + Arrays.toString(params));
            }
        }

    }
}
