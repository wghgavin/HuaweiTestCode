import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Pattern;

public class HuaWeiTestCode {
    public static void main(String[] args) {
        danCiYasuoBianMa();
    }

    /**
     * 九宫格
     * 判断输出，有英文和数字两个模式，
     * 默认是数字模式，数字模式直接输出数 字，
     * 英文模式连续按同一个按键会依次出现这个按键上的字母，
     * 如果输入"/"或者其他字符，
     * 则循环中断 1（,.）2（abc）3（def） 4（ghi）5（jkl）6（mno） 7（pqrs）8（tuv）9（wxyz） # 0（空格）/
     * 要求输入一串按键，输出屏幕显示
     * 输入范围为数字0~9和字符 '#'、’/’，
     * 输出屏幕显示，例如，
     * 在数字模式下，
     * 输入1234，显示 1234
     * 在英文模式下，输入1234，显示 ,adg 示例1：
     * 1、#用于切换模式，默认是数字模式，执行#后切换为英文模式；
     * 2、/表示延迟，例如在英文模式下，
     * 输入22/222，显示为bc； 3、英文模式下，多次按同一键，例如输入22222，显示为b
     */
    static void jiuGongGe() {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> list = new ArrayList<>();
        Collections.addAll(list, " ", ",.", "abc", "def", "ghi"
                , "jkl", "mno", "pqrs", "tuv", "wxyz");
        boolean isEngMode = false;
        while (sc.hasNext()) {
            String input = sc.nextLine();
            //先判断是否改变模式
            if (input.equals("#")) {
                isEngMode = !isEngMode;
                continue;//防止进入下面判断
            }
            //再判断模式是什么
            if (!isEngMode) {
                String outPut = input.replaceAll("/", "");
                System.out.println(outPut);
            } else {//否则执行英文处理逻辑
                char[] chs = input.toCharArray();
                StringBuilder outPutStr = new StringBuilder();//存储输出字符数组
                int index = 0;//字符索引
                char ch = ' ';//当前匹配数字
                //逐一匹配
                for (char c : chs
                ) {
                    if (c == '/') {//如果是延迟字符
                        //重置index和ch
                        index = 0;
                        ch = ' ';
                        continue;//跳去下一步
                    }
                    String strCurrent = list.get(c - '0');
                    if (ch != c) {//如果当前数字不是匹配的数字
                        //重置index和ch
                        index = 0;
                        ch = c;
                    } else {//如果匹配到当前数字
                        if (index >= strCurrent.length() - 1) index = 0;
                        else index++;
                        outPutStr.deleteCharAt(outPutStr.length() - 1);
                    }
                    outPutStr.append(strCurrent.charAt(index));
                }
                //最后输出字符串
                System.out.println(outPutStr);
            }

        }

    }

     /**
     * 相加
     * s=a+aa+aaa+aaaa+aa…a(总共有n个这样的数相加)的值
     * 例如当a ＝ 2，n ＝ 5时，s ＝ 2+22+222+2222+22222
     * 输出结果s
     */
    static void xiangJia() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] spiltInput = input.split(" ");
        int a = Integer.parseInt(spiltInput[0]);
        int n = Integer.parseInt(spiltInput[1]);
        int sum = 0;
        int numLast = 0;
        for (int i = 0; i < n; i++) {
            numLast = numLast * 10 + a;
            sum += numLast;
        }
        System.out.println(sum);
    }

    /**
     * 寻找最长字符串
     * 给定一串字符，里面有些字符有连续出现的特点，
     * 请寻找这些连续出现字符中最长的串，如果 最长的字串有多个，
     * 请输出字符ASCII码最小的那一串。
     * 输入描述： 输出描述：输入一串字符 t输出其中最长的字串
     */
    static void maxLengthString() {
        Scanner sc = new Scanner(System.in);
        ArrayList<StringBuilder> list = new ArrayList<>();
        String input = sc.nextLine();
        char lastCh = ' ';//记录上一个字符
        char[] inputChs = input.toCharArray();
        for (char currentCh : inputChs
        ) {
            if (currentCh != lastCh) {//如果当前字符不等于上一个字符
                list.add(new StringBuilder(String.valueOf(currentCh)));
                //更新字符记录
                lastCh = currentCh;
            } else {//如果相同
                list.get(list.size() - 1).append(currentCh);
            }
        }
        //循环结束,排序，找出最长那个
        Collections.sort(list, (sb1, sb2) -> {
            if (sb2.length() == sb1.length()) {//如果相同，就判断ASCII码从小到大排序
                return sb1.charAt(0) - sb2.charAt(0);
            }
            return sb2.length() - sb1.length();
        });
        //最后直接输出第一个元素
        System.out.println(list.get(0));
    }

    /**
     * 找出不同字母
     * 输入
     * Abc
     * bAdc
     * 输出
     * d
     */
    static void findDifferentChar() {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        String c = sc.nextLine();
        char[] inputChs = s.toCharArray();
        char[] resultChs = c.toCharArray();
        char result = inputChs[0];
        for (int i = 1; i < inputChs.length; i++) {
            result ^= inputChs[i];
        }
        for (char ch : resultChs
        ) {
            result ^= ch;
        }
        System.out.println(result);
    }

    //region 字符串处理

    /**
     * 字符串处理
     * 演示
     * 输入
     * ABCCBA
     * 输出
     * Yes
     * 输入
     * ABBC
     * 输出
     * NO
     */
    static void stringDealing() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        StringBuilder sb = new StringBuilder(input);
        String output = diGuiStringDealing(sb);
        System.out.println(output);
    }

    static String diGuiStringDealing(StringBuilder sb) {
        if (sb.length() == 0) return "Yes";
        char currentCh = ' ';//储存上一个char用来比对
        for (int i = 0; i < sb.length(); i++) {
            if (sb.charAt(i) == currentCh) {
                //把当前字符和前面一字符去掉
                sb = sb.delete(i - 1, i + 1);
                //继续递归
                return diGuiStringDealing(sb);


            } else {
                currentCh = sb.charAt(i);
            }
        }
        //比较完了都没有就return "No";
        return "No";
    }
    //endregion

    /**
     * 航班预定座位数(使用差分序列做法  O(n),贼快)
     *
     * @param bookings 预定信息 [i,j,k] 代表从i号到j号有k人订票
     * @param n        代表一共几个航班
     *                 bookings中的一维代表预定的信息数量，二维的0,1,2下标值代表i,j,k
     */
    static void flightOrderSeatCount(int[][] bookings, int n) {
        int[] src = new int[n];
        int[] dif = new int[n];
        for (int i = 0; i < bookings.length; i++) {
            int start = bookings[i][0];//代表上面的i
            int end = bookings[i][1];//代表上面的j
            int value = bookings[i][2];//代表上面的k
            //进行差分处理
            dif[start - 1] += value;
            if (end < n) {
                dif[end] -= value;
            }
        }
        //处理完差分后，加到src上
        src[0] = dif[0];
        for (int i = 1; i < n; i++) {
            src[i] = src[i - 1] + dif[i];
        }
        for (int result : src
        ) {
            System.out.println(result);
        }
    }

    /**
     * 奥运会排行榜
     * 示例
     * 5
     * China 32 28 34
     * England 12 34 22
     * France 23 33 2
     * Japan 12 34 25
     * Rusia 23 43 0
     * 输出
     * China
     * Rusia
     * France
     * Japan
     * England
     * 采用局部内部类  特点(不能使用任何访问修饰符,局部内部类只能访问方法中声明的final变量)
     */
    static void auYunHuiPaiHang() {
        class Country {
            String name;
            int goldMedal;
            int silverMdeal;
            int bronzeMedal;

            public Country(String name, int goldMedal, int silverMdeal, int bronzeMedal) {
                this.name = name;
                this.goldMedal = goldMedal;
                this.silverMdeal = silverMdeal;
                this.bronzeMedal = bronzeMedal;
            }
        }
        List<Country> countries = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        while (n > 0) {
            n--;
            String input = sc.nextLine();
            String[] spiltInputs = input.split(" ");
            countries.add(new Country(spiltInputs[0], Integer.parseInt(spiltInputs[1]),
                    Integer.parseInt(spiltInputs[2]), Integer.parseInt(spiltInputs[3])));
        }
        //直接进行sort排序
        Collections.sort(countries, (c1, c2) -> {
            if (c2.goldMedal != c1.goldMedal) {
                return c2.goldMedal - c1.goldMedal;
            } else if (c2.silverMdeal != c1.silverMdeal) {
                return c2.silverMdeal - c1.silverMdeal;
            } else if (c2.bronzeMedal != c1.bronzeMedal) {
                return c2.bronzeMedal - c1.bronzeMedal;
            } else {
                return c1.name.charAt(0) - c2.name.charAt(0);
            }
        });
        for (Country c : countries
        ) {
            System.out.println(c.name);
        }
    }

    //region 字符串分隔

    /**
     * 字符串分割
     * 给定一个非空字符串S，其被N个'-'分隔成N+1的子串，给定正整数K，
     * 要求除第一个子串外，其余的 串每K个用'-'分隔，并将小写字母转换为大写。
     */
    static void strSplitFunc() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        int k = sc.nextInt();
        sc.nextLine();
        String[] strChilds = input.split("-");
        for (int i = 1; i < strChilds.length; i++) {
            strChilds[i] = divideStr(strChilds[i], k);
        }
        StringBuilder sb = new StringBuilder();
        for (String s : strChilds
        ) {
            sb.append(s + "-");
        }
        sb.deleteCharAt(sb.length() - 1);
        System.out.println(sb);
    }

    static String divideStr(String str, int n) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i += n) {
            if (i + n <= str.length()) {
                sb.append(str.substring(i, i + n) + "-");
            } else {
                sb.append(str.substring(i, str.length()) + "-");
            }

        }
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString().toLowerCase();
    }
    //endregion

    /**
     * 猜数字游戏
     * 10.xAyB 猜数字游戏，输入两组数字，每组数字包含4个非负整型数字，
     * 同一组中的4个数字互不相 同，数字间以空格分隔。
     * 第一组数字为猜数字游戏的正确答案，第二组数字为玩家所猜的答案，
     * 根据如下规则，输出猜数字的结果xAyB。 规
     * 则1：如果数字相同，且位置相同，则得到一个A
     * 规则2：如果数字相同，但位置不同，则得到一个B
     * 如：正确答案为1 2 3 4，玩家猜测1 2 5 3，则最后的猜测结果为2A1B，其
     * 中数字1,2满足规则1，数字3满足规则2
     */
    static void guessNumGame() {
        Scanner sc = new Scanner(System.in);
        String input1 = sc.nextLine();
        String input2 = sc.nextLine();
        char[] chs1 = input1.toCharArray();
        char[] chs2 = input2.toCharArray();
        Map<Character, Integer> mapInput = new HashMap<>();
        Map<Character, Integer> mapOutput = new HashMap<>();
        for (int i = 0; i < chs1.length; i++) {
            mapInput.put(chs1[i], i);
        }
        int aCount = 0;
        int bCount = 0;
        for (int i = 0; i < chs2.length; i++) {
            if (Objects.equals(mapInput.get(chs2[i]), i)) {
                aCount++;
            } else if (mapInput.get(chs2[i]) != null) {
                bCount++;
            }
        }
        System.out.println(aCount + "A" + bCount + "B");
        //注意这种写法没有的时候会输出 0A0B
    }

    //region 服务器广播问题

    /**
     * @param match 一维和二维分辨表示第几号和第几号相连，值1为直接，0为不直接
     */
    static void serverBroadcast(int[][] match) {


    }

    class Server {
        int num;

        public Server(Server server) {
            this.connectedServer = server;
        }

        private Server connectedServer;

        public Server getConnectedServer() {
            return connectedServer;
        }
    }

    static boolean diguiBroadcast() {
        return false;
    }
    //endregion

    /**
     * 重新输出句子
     * 输入一个英文句子，句子中仅包含英文字母，数字，
     * 空格和标点符号，其中数字、空格和标点符号将句子划分成一个 个独立的单词
     * ，除去句子中的数字、空格和标点符号，将句子中的每个单词的首字母大写，然
     * 后输出句子，输出时各 个单词之间以一个空格隔开，句子以“.”结束
     */
    static void reOutputEnglishSentence() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String output = input.replaceAll("[^a-zA-Z]", " ");
        String[] s = output.split("[ ]+");
        StringBuilder sb = new StringBuilder();
        for (String str : s
        ) {
            str = str.substring(0, 1).toUpperCase().concat(str.substring(1));
            sb.append(str + " ");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(".");
        System.out.println(sb);
    }

    //region 字符串匹配

    /**
     * 字符串匹配 b>=a
     */
    static void stringMatch() {
        Scanner sc = new Scanner(System.in);
        String a = sc.nextLine();
        String b = sc.nextLine();
        int result = diguiStringMatch(a, b, 0);
        System.out.println(result);

    }

    static int diguiStringMatch(String child, String parent, int startIndex) {
        int childIndex = 0;
        for (int i = startIndex; i < parent.length(); i++) {
            if (parent.charAt(i) == child.charAt(childIndex)) {//逐个比对子字符串
                childIndex++;
            } else {//如果下一个不是了 就把childIndex置0
                childIndex = 0;
            }
            if (childIndex == child.length()) {//如果到了最后匹配成功，childIndex置0
                childIndex = 0;
                System.out.println("匹配到从" + (i - child.length() + 1) + "到" + i);
                return 1 + diguiStringMatch(child, parent, i - child.length() + 2);
            }
        }
        //循环结束都没有匹配上的那就给个0
        return 0;
    }
    //endregion

    //region 重点:全排列算法

    /**
     * 全排列算法
     * 输入1,2,3,4
     */
    static void quanPaiLie() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] splitInputs = input.split(",");
        int[] array = new int[splitInputs.length];
        for (int i = 0; i < splitInputs.length; i++) {
            array[i] = Integer.parseInt(splitInputs[i]);
        }
        perm(array, new Stack<Integer>());
    }

    static void perm(int[] array, Stack<Integer> stack) {
        if (array.length == 0) {
            System.out.println(stack);
            return;
        }
        for (int i = 0; i < array.length; i++) {
            int[] tempArray = new int[array.length - 1];//还有两个可选择，给递归
            System.arraycopy(array, 0, tempArray, 0, i);//把i之前元素放进数组
            System.arraycopy(array, i + 1, tempArray, i, array.length - i - 1);//把i之后元素放进数组
            stack.push(array[i]);
            perm(tempArray, stack);//执行完后，才会再走下一步，同步的，所以pop不会改变值
            stack.pop();
        }
    }
    //endregion

    /**
     * 计算立方和
     */
    static void jiSuanLiFangHe() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] splitInputs = input.split(" ");
        int a = Integer.parseInt(splitInputs[0]);
        int b = Integer.parseInt(splitInputs[1]);
        int sum = 0;
        for (int i = a; i <= b; i++) {
            sum += Math.pow(i, 3);
        }
        System.out.println(sum);
    }

    /**
     * 判断一组数字是否连续
     * 判断一组数字是否连续 当出现连续数字的时候以‘-’输出，比如：
     * 对于数组[1, 2, 3, 4, 6, 8, 9, 10]，期望输出：["1-4", 6, "8-10"]
     */
    static void checkConcatString() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] splitInputs = input.split(",");
        int[] num = new int[splitInputs.length];
        for (int i = 0; i < splitInputs.length; i++) {
            num[i] = Integer.parseInt(splitInputs[i]);
        }
        List<String> listStr = new ArrayList<>();
        int currentIndex = 0;
        int currentNum = num[0];
        for (int i = 1; i < num.length; i++) {
            if (num[i] - num[i - 1] == 1) {//如果能循环下去
                currentIndex++;
            } else {//如果不行就输出前面的
                //如果currentIndex>1,说明有循环
                if (currentIndex > 0) {
                    listStr.add(currentNum + "-" + (currentNum + currentIndex));
                } else {//如果不是直接输出currentNum
                    listStr.add(String.valueOf(currentNum));
                }
                currentIndex = 0;//需要重置
                currentNum = num[i];
            }
        }
        //最后还需要把最后的一个加进去
        if (currentIndex > 0) {
            listStr.add(currentNum + "-" + (currentNum + currentIndex));
        } else {//如果不是直接输出currentNum
            listStr.add(String.valueOf(currentNum));
        }
        System.out.println(listStr);
    }

    /**
     * 报数游戏
     */
    static void reportNumGame() {
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        sc.nextLine();
        List<Integer> queue = new ArrayList<>();
        //生成队列
        for (int i = 1; i <= 100; i++) {
            queue.add(i);
        }
        int maxIndex = queue.size() - 1;
        int currentIndex = 0;
        while (queue.size() >= input) {//一直进行到队列的数量小于input
            for (int i = 1; i <= input; i++) {
                if (currentIndex > maxIndex) {
                    //当前索引大于了最大索引,已经超出queue
                    currentIndex = 0;
                }
                if (i == input) {//已经达到了input数量，让这个队列的人出去
                    maxIndex--;
                    queue.remove(currentIndex);
                } else {//这里注意必须用else,remove掉index会改变
                    currentIndex++;
                }

            }
        }
        System.out.println(queue);
    }

    /**
     * 正数翻转
     */
    static void zhengShuFanZhuan() {
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        sc.nextLine();
        StringBuffer sbf = new StringBuffer();
        sbf.append(input);
        sbf.reverse();
        if (String.valueOf(sbf.charAt(sbf.length() - 1)).equals("-")) {
            sbf.deleteCharAt(sbf.length() - 1);
            sbf.insert(0, "-");
        }
        try {
            int output = Integer.parseInt(sbf.toString());
            System.out.println(output);
        } catch (Exception ex) {
            System.out.println(0);
        }
    }

    /**
     * 打印全排列，同上
     */
    static void daYinQuanPaiLie() {

    }

    /**
     * 幸运数字
     */
    static void xingYunShuZi() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] splitInputs = input.split(",");
        int n = Integer.parseInt(splitInputs[0]);
        int x = Integer.parseInt(splitInputs[1]);
        int y = Integer.parseInt(splitInputs[2]);
        int z = Integer.parseInt(splitInputs[3]);
        int count = 0;
        for (int i = 1; i <= n; i++) {
            if (i % x == 0 || i % y == 0 || i % z == 0) {
                count++;
            }
        }
        System.out.println(count);
    }

    /**
     * 分子弹
     * 射击训练，需要给每个士兵发子弹，
     * 发子弹的个数规则是根据士兵的训练成绩给定的，
     * 传入士兵的训练成 绩，要求相邻士兵中，成绩好的士兵的子弹数必须更多，
     * 每个士兵至少分配一个子弹。输入描述：输入每个士兵的训练成绩，
     * 如1,2,3，代表3位士兵的成绩分别为1,2,3输出描述：最少分配的子弹数
     */
    static void fenZiDan() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] splitInputs = input.split(",");
        int[] list = new int[splitInputs.length];
        for (int i = 0; i < splitInputs.length; i++) {
            list[i] = Integer.parseInt(splitInputs[i]);
        }
        Arrays.sort(list);
        int ziDanCount = 1;
        int currentZidanNum = 1;
        for (int i = 1; i < list.length; i++) {
            if (list[i] > list[i - 1]) {
                currentZidanNum++;
            }
            ziDanCount += currentZidanNum;
        }
        System.out.println(ziDanCount);
    }

    /**
     * 元音字母请大写
     * solo从小就对英文字母非常感兴趣，尤其是元音字母(a,e,i,o,u,A,E,I,O,U)，他在写日记的时候都会把元音字母写成大 写的，
     * 辅音字母则都写成小写，虽然别人看起来很别扭，但是solo却非常熟练。
     * 你试试把一个句子翻译成solo写日记的习惯吧。
     * 输入描述：输入一个字符串S(长度不超过100，只包含大小写的英文字母和空格)。
     * 输出描述：按照solo写日记的习惯输出翻译后的字符串S
     */
    static void yuanYinZifuDaXie() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        char[] chs = new char[]{'a', 'e', 'i', 'o', 'u'};
        StringBuilder sb = new StringBuilder(input);
        for (int i = 0; i < sb.length(); i++) {
            if (Arrays.binarySearch(chs, sb.charAt(i)) < 0) {
                continue;
            }
            sb.replace(i, i + 1, sb.substring(i, i + 1).toUpperCase());
        }
        System.out.println(sb);
    }

    //region 跳跃比赛

    /**
     * 跳跃比赛
     */
    static void tiaoYueBiSai() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        int[] list = new int[n];
        for (int i = 0; i < n; i++) {
            int input = sc.nextInt();
            sc.nextLine();
            list[i] = input;
        }
        int result = diGuiTiaoYue(list.length - 1, list);
        System.out.println(result);
    }

    static int diGuiTiaoYue(int end, int[] array) {
        if (end == 0) {
            return 0;
        }
        int i = 0;
        while (array[i] < end - i) i++;
        return 1 + diGuiTiaoYue(i, array);
    }
    //endregion

    /**
     * 单词反转
     */
    static void danCiFanZhuan() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        String[] splitInputs = input.split(" ");
        StringBuilder output = new StringBuilder();
        for (String str : splitInputs
        ) {
            for (int i = str.length() - 1; i >= 0; i--) {
                output.append(str.charAt(i));
            }
            output.append(" ");
        }
        output.deleteCharAt(output.length() - 1);
        System.out.println(output);
    }

    /**
     * 无重复字符的最长字串
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度
     * 输出不含有重复字符的最长子串的长度
     */
    //双重指针写法!!!!
    static int lengthOfLongestSubstring() {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int[] hash = new int[500];
        int startIndex = 0;
        int max = 0;
        int i = 0, j = 0;

        while (i < s.length() && j < s.length()) {
            char iCh = s.charAt(i);
            char jCh = s.charAt(j);
            if (hash[s.charAt(j)] == 0) {
                hash[s.charAt(j)] = 1;
                j++;
                // max = (j - i) > max ? (j - i) : max;
                // 这里优化一下 输出最长字符串
                if (j - i > max) {
                    //说明后面的比前面要长了，
                    max = j - i;
                    //从i开始，i不是索引是长度
                    startIndex = i;
                }

            } else {
                hash[s.charAt(i)] = 0;
                i++;
            }
        }
        //先输出最长子串
        System.out.println(s.substring(startIndex, max + 1));
        return max;
    }

    /**
     * 集五福
     */
    static void jiWuFu(){
        Random r = new Random();
        int ran;
        while ((ran = r.nextInt(10)) != 0) {
            Map<Integer, Integer> map = new LinkedHashMap<>();
            Scanner sc = new Scanner(System.in);
            for (int i = 0; i < ran; i++) {
                String input = sc.nextLine();
                char[] chs = input.toCharArray();
                for (int j = 0; j < chs.length; j++) {
                    if (map.get(j) != null) {
                        map.put(j, map.get(j) +(chs[j]=='0'?0:1));
                    } else {
                        map.put(j, (chs[j]=='0'?0:1));
                    }
                }
            }
            List<Integer> list = new ArrayList<>(map.values());
            Collections.sort(list);
            System.out.println(list.get(0));
        }
    }

    /**
     * 磁盘容量排序
     */
    static void ciPanRoliangPaiXu() {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        sc.nextLine();
        List<String> list = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String input = sc.nextLine();
            list.add(input);
        }
        Collections.sort(list, (s1, s2) -> {
            int value1 = Integer.parseInt(s1.replaceAll("[^0-9]+", ""));
            String unit1 = s1.replaceAll("[0-9+]", "");
            int value2 = Integer.parseInt(s2.replaceAll("[^0-9]+", ""));
            String unit2 = s2.replaceAll("[0-9+]", "");
            return (int) (getGValue(unit1, value1) - getGValue(unit2, value2));
        });
        list.forEach(l ->
                System.out.println(l)
        );
    }

    static double getGValue(String unit, int num) {
        switch (unit) {
            case "M":
                return num / 1000;
            case "G":
                return num;
            case "T":
                return num * 1000;
            default:
                return 0;
        }
    }

    /**
     * 括号生成
     * 31.给定一个无重复元素的数组 和一个目标数 target ，
     * 找出 数组中所有可以使数字和为 target 的组合。数组中的数字可以无限制被选取。
     * 说明： 所有数字（包括 target）都是正整数。 解集不能包含重复的组合
     */
    static void kuoHaoShengCheng(int[] array, int startIndex, int target, Stack<Integer> stack) {
        Iterator<Integer> iterator = stack.iterator();
        int sum = 0;
        while (iterator.hasNext()) {
            sum += iterator.next();
        }
        if (sum == target) {
            System.out.println(stack.toString());
        }
//        for (int value:array
//             ) {
//            if(sum+value<=target){
//                stack.push(value);
//                kuoHaoShengCheng(array,,target,stack);
//                stack.pop();
//            }
//        }
        for (int i = startIndex; i < array.length; i++) {
            if (sum + array[i] <= target) {
                stack.push(array[i]);
                kuoHaoShengCheng(array, i, target, stack);
                stack.pop();
            }
        }
    }

    /**
     * 连续字母
     */
    static void lianXuZiMu() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        int max = 0;
        int length = 1;
        char currentCh = ' ';
        for (int i = 0; i < input.length(); i++) {
            if (currentCh != input.charAt(i)) {
                currentCh = input.charAt(i);
                length = 1;
            } else {
                length++;
            }
            max = length > max ? length : max;
        }
        System.out.println(max);
    }

    /**
     * 乘最多水的容器
     */
    static void zuiDuoShuiRongQi(int[] array) {
        int left = 0;
        int right = array.length - 1;
        int maxArea = 0;
        int height = 0;
        while (left < right) {
            //height根据木桶效应取值
            height = array[left] > array[right] ? array[right] : array[left];
            maxArea = Math.max(maxArea, height * (right - left));
            if (array[left] < array[right])
                left++;
            else
                right--;
        }
        System.out.println(maxArea);
    }

    /**
     * 第k个排列
     *
     * @param array  数组
     * @param sb     存储当前的字符串
     * @param sbList 存储字符串数组
     *               给出集合 [1,2,3,…,n]，其所有元素共有 n! 种排列。
     *               按大小顺序列出所有排列情况，并一一标记，当 n = 3 时,
     *               所有排列如下："123" "132" "213" "231" "312" "321" 给定 n 和 k，返回第 k 个排列
     */
    static void diGuiDiKGePaiLie(int[] array, StringBuilder sb, List<StringBuilder> sbList) {
        if (array.length == 0) {
            //!!!!这里一定要new 否则会报错 进去同一对象
            StringBuilder sbTemp = new StringBuilder(sb);
            sbList.add(sbTemp);
        }
        for (int i = 0; i < array.length; i++) {
            sb.append(array[i]);
            int[] tempArray = new int[array.length - 1];
            System.arraycopy(array, 0, tempArray, 0, i);
            System.arraycopy(array, i + 1, tempArray, i, array.length - i - 1);
            diGuiDiKGePaiLie(tempArray, sb, sbList);
            sb.deleteCharAt(sb.length() - 1);
        }
    }

    static void diKGePaiLie() {
        int[] array = {1, 2, 3};
        int k = 3;
        List<StringBuilder> sbList = new ArrayList<>();
        diGuiDiKGePaiLie(array, new StringBuilder(), sbList);
        System.out.println(sbList.get(k));
    }

    /**
     * 单词压缩编码
     */
    static void danCiYasuoBianMa() {
        String[] inputs = new String[]{"time", "me", "bell"};
        StringBuilder sb = new StringBuilder();
        for (String str:inputs
             ) {
            if(sb.indexOf(str+"#")==-1){
                sb.append(str+"#");
            }
        }
        System.out.println(sb.length());
    }

    /**
     * 素数(判断n是否是一个质数)
     */
    static void isSuShu() {
        Scanner sc = new Scanner(System.in);
        int input = sc.nextInt();
        if (input == 2) System.out.println("Yes");
        ;//2特殊处理
        if (input < 2 || input % 2 == 0) System.out.println("No");
        ;//识别小于2的数和偶数
        for (int i = 3; i <= Math.sqrt(input); i += 2) {
            if (input % i == 0) {//识别被奇数整除
                System.out.println("No");
            }
        }
        System.out.println("Yes");
    }

    /**
     * 最长公共子串长度
     * 请根据输入两个字符串，求它们的最长公共子串，输出最长公共子串的长度
     * 用动态规划来做求dp
     */
    static void zuiChangGonggongZiChuanChangdu() {
        Scanner sc = new Scanner(System.in);
        String s1 = sc.nextLine();
        String s2 = sc.nextLine();
        char[] arr1 = s1.toCharArray();
        char[] arr2 = s2.toCharArray();
        int[][] dp = getDp(arr1, arr2);
        int end = 0;
        int maxLen = 0;
        for (int i = 0; i < arr1.length; i++) {
            for (int j = 0; j < arr2.length; j++) {
                if (dp[i][j] > maxLen) {//所在值大于maxlen
                    end = i;//end即为结束子串的位置下标
                    maxLen = dp[i][j];//更新maxlen
                }
            }
        }
        String subs = s1.substring(end - maxLen + 1, end + 1);
        System.out.println(subs);
        System.out.println(maxLen);
    }

    static int[][] getDp(char[] arr1, char[] arr2) {
        int[][] dp = new int[arr1.length][arr2.length];
        for (int i = 0; i < arr1.length; i++)//第一列赋值
        {
            if (arr1[i] == arr2[0]) dp[i][0] = 1;
        }
        for (int j = 1; j < arr2.length; j++) {//第一行赋值
            if (arr2[j] == arr1[0]) dp[0][j] = 1;
        }
        for (int i = 1; i < arr1.length; i++) {//其余位置相等的赋值为左上角加一，就是当前子串长度加一
            for (int j = 1; j < arr2.length; j++) {
                if (arr1[i] == arr2[j]) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                }
            }
        }
        return dp;
    }

    /**
     * 字符统计及重排
     */
    static void ziFuTongJiChongPai() {
        Scanner sc = new Scanner(System.in);
        String input = sc.nextLine();
        Map<Character, Integer> map = new HashMap<>();
        char[] chs = input.toCharArray();
        for (int i = 0; i < chs.length; i++) {
            if (map.get(chs[i]) == null) {//如果是空的，就放进去
                map.put(chs[i], 1);
            } else {//如果存在
                int count = map.get(chs[i]);
                map.put(chs[i], ++count);
            }
        }
        //最后排序
        map.entrySet().stream().sorted((m1, m2) ->
        {
            if (m1.getValue() == m2.getValue()) {
                return m1.getKey() - m2.getKey();
            }
            return m2.getValue() - m2.getValue();
        }).forEach(m ->
                System.out.println(m.getKey() + " " + m.getValue())
        );
    }

    /**
     * 字符串数组压缩
     */
    static void stringArrayZip(String[] strList) {
        Map<String,String> set = new HashMap<>();
        for (String str : strList
        ) {
            StringBuilder sb = new StringBuilder();
            sb.append(str.substring(0, 1));
            sb.append(str.length() - 2);
            sb.append(str.substring(str.length() - 1, str.length()));
            int count =1;
            while (set.containsKey(sb.toString())) {
                count++;
                String getStr = set.get(sb.toString());
                set.remove(sb.toString());
                StringBuilder tempSb = new StringBuilder();
                tempSb.append(getStr.substring(0,count));
                tempSb.append(getStr.length()-count-1);
                tempSb.append(getStr.substring(getStr.length()-1,getStr.length()));
                set.put(tempSb.toString(),getStr);

                sb.delete(0, sb.length());//删除之后新增
                sb.append(str.substring(0,count));
                sb.append(str.length()-count-1);
                sb.append(str.substring(str.length()-1,str.length()));
            }
            //跳出循环，就是不存在了,判断长度
            if(sb.length()>=str.length()){
                sb.delete(0,sb.length());
                sb.append(str);
            }
            set.put(sb.toString(),str);
        }
        set.forEach((k,v)-> System.out.println(k));
    }





    static void fileStreamTestMethod() {
        try {
            long start = System.currentTimeMillis();
            FileInputStream input = new FileInputStream("C:\\Users\\Administrator\\Desktop\\java学习.txt");
            FileOutputStream output = new FileOutputStream("F:\\学习资料\\2.txt");
//            int len =0;
//            while((len=input.read())!=-1){
//                output.write(len);
//            }
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = input.read(b)) != -1) {
                output.write(b, 0, len);
            }
            output.close();
            input.close();
            long end = System.currentTimeMillis();
            System.out.println("普通字节流" + (end - start) + "毫秒");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static void bufferStreamTestMethod() {
        try {
            long start = System.currentTimeMillis();
            BufferedInputStream input = new BufferedInputStream(new FileInputStream("C:\\Users\\Administrator\\Desktop\\java学习.txt"));

            BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream("F:\\学习资料\\1.txt"));
//            int len =0;
//            while((len=input.read())!=-1){
//                output.write(len);
//            }
            byte[] b = new byte[1024];
            int len = 0;
            while ((len = input.read(b)) != -1) {
                output.write(b, 0, len);
            }
            output.close();
            input.close();
            long end = System.currentTimeMillis();
            System.out.println("缓冲字节流" + (end - start) + "毫秒");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    static void propertiedTestMethod() {
        Properties prop = new Properties();
        prop.setProperty("第一项", "1");
        prop.setProperty("第二项", "2");
        //Set<String> set = prop.stringPropertyNames();
        FileWriter fw = null;
        try {
            fw = new FileWriter("a.txt");
            prop.store(fw, "Save data");
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void propertiesLoadTestMethod() {
        Properties p = new Properties();
        try {
            p.load(new FileReader("a.txt"));
            Set<Map.Entry<Object, Object>> entries = p.entrySet();
            Iterator<Map.Entry<Object, Object>> iterator = entries.iterator();
            while (iterator.hasNext()) {
                var result = iterator.next();
                System.out.println(result);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static int divideApple(int m, int n) {
        if (n == 1 || m == 0) return 1;
        if (m < n) {
            return divideApple(m, m);
        }
        return divideApple(m - n, n) + divideApple(m, n - 1);
    }


}
