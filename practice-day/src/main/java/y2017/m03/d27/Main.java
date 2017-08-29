package y2017.m03.d27;


import javax.script.ScriptEngine;
import javax.script.ScriptEngineFactory;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.text.MessageFormat;
import java.util.*;

public class Main {
    private static final int EXIT_SUCCESS = 0;
    private static final int EXIT_CMD_NO_CLASSPATH = 1;
    private static final int EXIT_CMD_NO_FILE = 2;
    private static final int EXIT_CMD_NO_SCRIPT = 3;
    private static final int EXIT_CMD_NO_LANG = 4;
    private static final int EXIT_CMD_NO_ENCODING = 5;
    private static final int EXIT_CMD_NO_PROPNAME = 6;
    private static final int EXIT_UNKNOWN_OPTION = 7;
    private static final int EXIT_ENGINE_NOT_FOUND = 8;
    private static final int EXIT_NO_ENCODING_FOUND = 9;
    private static final int EXIT_SCRIPT_ERROR = 10;
    private static final int EXIT_FILE_NOT_FOUND = 11;
    private static final int EXIT_MULTIPLE_STDIN = 12;
    private static final String DEFAULT_LANGUAGE = "js";
    private static List<Main.Command> scripts = new ArrayList();
    private static ScriptEngineManager engineManager;
    private static Map<String, ScriptEngine> engines = new HashMap();
    private static ResourceBundle msgRes;
    private static String BUNDLE_NAME = "com.sun.tools.script.shell.messages";
    private static String PROGRAM_NAME = "jrunscript";

    public Main() {
    }

    public static void main(String[] args) {
//        args = new String[] {"-e", "print('hello world')"};
        String[] options = processOptions(args);
        Iterator iterator = scripts.iterator();

        while(iterator.hasNext()) {
            Main.Command command = (Main.Command)iterator.next();
            command.run(options);
        }

//        System.exit(EXIT_SUCCESS);
    }

    private static String[] processOptions(String[] args) {
        String language = DEFAULT_LANGUAGE;
        String charset = null;
        checkClassPath(args);
        boolean evaluate = false;
        boolean interactive = false;

        for(int i = 0; i < args.length; ++i) {
            String arg = args[i];
            if(!arg.equals("-classpath") && !arg.equals("-cp")) {
                int equalIndex;
                if(!arg.startsWith("-")) {
                    int evaluateIndex;
                    if(evaluate) {
                        evaluateIndex = args.length - i;
                        equalIndex = i;
                    } else {
                        evaluateIndex = args.length - i - 1;
                        equalIndex = i + 1;
                        ScriptEngine scriptEngine = getScriptEngine(language);
                        addFileSource(scriptEngine, args[i], charset);
                    }

                    String[] options = new String[evaluateIndex];
                    System.arraycopy(args, equalIndex, options, 0, evaluateIndex);
                    return options;
                }

                if(arg.startsWith("-D")) {
                    String param = arg.substring(2);
                    equalIndex = param.indexOf(61);
                    if(equalIndex != -1) {
                        System.setProperty(param.substring(0, equalIndex), param.substring(equalIndex + 1));
                    } else if(!param.equals("")) {
                        System.setProperty(param, "");
                    } else {
                        usage(EXIT_CMD_NO_PROPNAME);
                    }
                } else {
                    if(!arg.equals("-?") && !arg.equals("-help")) {
                        ScriptEngine scriptEngine;
                        if(arg.equals("-e")) {
                            evaluate = true;
                            ++i;
                            if(i == args.length) {
                                usage(EXIT_CMD_NO_SCRIPT);
                            }

                            scriptEngine = getScriptEngine(language);
                            addStringSource(scriptEngine, args[i]);
                            continue;
                        }

                        if(arg.equals("-encoding")) {
                            ++i;
                            if(i == args.length) {
                                usage(EXIT_CMD_NO_ENCODING);
                            }

                            charset = args[i];
                            continue;
                        }

                        if(arg.equals("-f")) {
                            evaluate = true;
                            ++i;
                            if(i == args.length) {
                                usage(EXIT_CMD_NO_FILE);
                            }

                            scriptEngine = getScriptEngine(language);
                            if(args[i].equals("-")) {
                                if(interactive) {
                                    usage(EXIT_MULTIPLE_STDIN);
                                } else {
                                    interactive = true;
                                }

                                addInteractiveMode(scriptEngine);
                            } else {
                                addFileSource(scriptEngine, args[i], charset);
                            }
                            continue;
                        }

                        if(arg.equals("-l")) {
                            ++i;
                            if(i == args.length) {
                                usage(EXIT_CMD_NO_LANG);
                            }

                            language = args[i];
                            continue;
                        }

                        if(arg.equals("-q")) {
                            listScriptEngines();
                        }
                    } else {
                        usage(0);
                    }

                    usage(EXIT_UNKNOWN_OPTION);
                }
            } else {
                ++i;
            }
        }

        if(!evaluate) {
            ScriptEngine scriptEngine = getScriptEngine(language);
            addInteractiveMode(scriptEngine);
        }

        return new String[0];
    }

    private static void addInteractiveMode(final ScriptEngine scriptEngine) {
        scripts.add(new Main.Command() {
            public void run(String[] options) {
                Main.setScriptArguments(scriptEngine, options);
                Main.processSource(scriptEngine, "-", (String)null);
            }
        });
    }

    private static void addFileSource(final ScriptEngine scriptEngine, final String filePath, final String charset) {
        scripts.add(new Main.Command() {
            public void run(String[] options) {
                Main.setScriptArguments(scriptEngine, options);
                Main.processSource(scriptEngine, filePath, charset);
            }
        });
    }

    private static void addStringSource(final ScriptEngine scriptEngine, final String scriptStr) {
        scripts.add(new Main.Command() {
            public void run(String[] options) {
                Main.setScriptArguments(scriptEngine, options);
                String scriptFileName = Main.setScriptFilename(scriptEngine, "<string>");

                try {
                    Main.evaluateString(scriptEngine, scriptStr);
                } finally {
                    Main.setScriptFilename(scriptEngine, scriptFileName);
                }

            }
        });
    }

    private static void listScriptEngines() {
        List engineFactories = engineManager.getEngineFactories();
        Iterator iterator = engineFactories.iterator();

        while(iterator.hasNext()) {
            ScriptEngineFactory scriptEngineFactory = (ScriptEngineFactory)iterator.next();
            getError().println(getMessage("engine.info", new Object[]{scriptEngineFactory.getLanguageName(), scriptEngineFactory.getLanguageVersion(), scriptEngineFactory.getEngineName(), scriptEngineFactory.getEngineVersion()}));
        }

        System.exit(0);
    }

    private static void processSource(ScriptEngine scriptEngine, String filePath, String charset) {
        if(filePath.equals("-")) {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(getIn()));
            boolean end = false;
            String prompt = getPrompt(scriptEngine);
            scriptEngine.put("javax.script.filename", "<STDIN>");

            while(!end) {
                getError().print(prompt);
                String str = "";

                try {
                    str = bufferedReader.readLine();
                } catch (IOException e) {
                    getError().println(e.toString());
                }

                if(str == null || "-q".equals(str)) {
                    end = true;
                    break;
                }

                Object result = evaluateString(scriptEngine, str, false);
                if(result != null) {
                    String stringResult = result.toString();
                    if(stringResult == null) {
                        stringResult = "null";
                    }

                    getError().println(stringResult);
                }
            }
        } else {
            FileInputStream fileInputStream = null;

            try {
                fileInputStream = new FileInputStream(filePath);
            } catch (FileNotFoundException e) {
                getError().println(getMessage("file.not.found", new Object[]{filePath}));
                System.exit(EXIT_FILE_NOT_FOUND);
            }

            evaluateStream(scriptEngine, fileInputStream, filePath, charset);
        }

    }

    private static Object evaluateString(ScriptEngine scriptEngine, String scriptStr, boolean exit) {
        try {
            return scriptEngine.eval(scriptStr);
        } catch (ScriptException e) {
            getError().println(getMessage("string.script.error", new Object[]{e.getMessage()}));
            if(exit) {
                System.exit(EXIT_SCRIPT_ERROR);
            }
        } catch (Exception exception) {
            exception.printStackTrace(getError());
            if(exit) {
                System.exit(EXIT_SCRIPT_ERROR);
            }
        }

        return null;
    }

    private static void evaluateString(ScriptEngine scriptEngine, String scriptStr) {
        evaluateString(scriptEngine, scriptStr, true);
    }

    private static Object evaluateReader(ScriptEngine scriptEngine, Reader reader, String filePath) {
        String oldScriptFileName = setScriptFilename(scriptEngine, filePath);

        try {
            Object result = scriptEngine.eval(reader);
            return result;
        } catch (ScriptException var9) {
            getError().println(getMessage("file.script.error", new Object[]{filePath, var9.getMessage()}));
            System.exit(EXIT_SCRIPT_ERROR);
        } catch (Exception var10) {
            var10.printStackTrace(getError());
            System.exit(EXIT_SCRIPT_ERROR);
        } finally {
            setScriptFilename(scriptEngine, oldScriptFileName);
        }

        return null;
    }

    private static Object evaluateStream(ScriptEngine scriptEngine, InputStream inputStream, String filePath, String charset) {
        BufferedReader bufferedReader = null;
        if(charset != null) {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream, charset));
            } catch (UnsupportedEncodingException e) {
                getError().println(getMessage("encoding.unsupported", new Object[]{charset}));
                System.exit(EXIT_NO_ENCODING_FOUND);
            }
        } else {
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        }

        return evaluateReader(scriptEngine, bufferedReader, filePath);
    }

    private static void usage(int exitStatus) {
        getError().println(getMessage("main.usage", new Object[]{PROGRAM_NAME}));
        System.exit(exitStatus);
    }

    private static String getPrompt(ScriptEngine scriptEngine) {
        List names = scriptEngine.getFactory().getNames();
        return (String)names.get(0) + "> ";
    }

    private static String getMessage(String key, Object[] formatParams) {
        return MessageFormat.format(msgRes.getString(key), formatParams);
    }

    private static InputStream getIn() {
        return System.in;
    }

    private static PrintStream getError() {
        return System.err;
    }

    private static ScriptEngine getScriptEngine(String language) {
        ScriptEngine scriptEngine = (ScriptEngine)engines.get(language);
        if(scriptEngine == null) {
            scriptEngine = engineManager.getEngineByName(language);
            if(scriptEngine == null) {
                getError().println(getMessage("engine.not.found", new Object[]{language}));
                System.exit(EXIT_ENGINE_NOT_FOUND);
            }

            initScriptEngine(scriptEngine);
            engines.put(language, scriptEngine);
        }

        return scriptEngine;
    }

    private static void initScriptEngine(ScriptEngine scriptEngine) {
        scriptEngine.put("engine", scriptEngine);
        List extensions = scriptEngine.getFactory().getExtensions();
        InputStream inputStream = null;
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        Iterator iterator = extensions.iterator();

        while(iterator.hasNext()) {
            String extension = (String)iterator.next();
            inputStream = contextClassLoader.getResourceAsStream("com/sun/tools/script/shell/init." + extension);
            if(inputStream != null) {
                break;
            }
        }

        if(inputStream != null) {
            evaluateStream(scriptEngine, inputStream, "<system-init>", (String)null);
        }

    }

    private static void checkClassPath(String[] args) {
        String classPath = null;

        for(int i = 0; i < args.length; ++i) {
            if(args[i].equals("-classpath") || args[i].equals("-cp")) {
                ++i;
                if(i == args.length) {
                    usage(EXIT_CMD_NO_CLASSPATH);
                } else {
                    classPath = args[i];
                }
            }
        }

        if(classPath != null) {
            ClassLoader classLoader = Main.class.getClassLoader();
            URL[] urls = pathToURLs(classPath);
            URLClassLoader urlClassLoader = new URLClassLoader(urls, classLoader);
            Thread.currentThread().setContextClassLoader(urlClassLoader);
        }

        engineManager = new ScriptEngineManager();
    }

    private static URL[] pathToURLs(String classPath) {
        String[] classPaths = classPath.split(File.pathSeparator);
        URL[] urls = new URL[classPaths.length];
        int index = 0;

        while(index < classPaths.length) {
            URL url = fileToURL(new File(classPaths[index]));
            if(url != null) {
                urls[index++] = url;
            }
        }

        // 过滤不存在的classpath路径
        if(urls.length != index) {
            URL[] uls = new URL[index];
            System.arraycopy(urls, 0, uls, 0, index);
            urls = uls;
        }

        return urls;
    }

    private static URL fileToURL(File file) {
        String filePath;
        try {
            filePath = file.getCanonicalPath();
        } catch (IOException var4) {
            filePath = file.getAbsolutePath();
        }

        filePath = filePath.replace(File.separatorChar, '/');
        if(!filePath.startsWith("/")) {
            filePath = "/" + filePath;
        }

        if(!file.isFile()) {
            filePath = filePath + "/";
        }

        try {
            return new URL("file", "", filePath);
        } catch (MalformedURLException var3) {
            throw new IllegalArgumentException("file");
        }
    }

    private static void setScriptArguments(ScriptEngine scriptEngine, String[] args) {
        scriptEngine.put("arguments", args);
        scriptEngine.put("javax.script.argv", args);
    }

    private static String setScriptFilename(ScriptEngine scriptEngine, String scriptFileName) {
        String oldScriptFileName = (String)scriptEngine.get("javax.script.filename");
        scriptEngine.put("javax.script.filename", scriptFileName);
        return oldScriptFileName;
    }

    static {
        msgRes = ResourceBundle.getBundle(BUNDLE_NAME, Locale.getDefault());
    }

    private interface Command {
        void run(String[] options);
    }
}
