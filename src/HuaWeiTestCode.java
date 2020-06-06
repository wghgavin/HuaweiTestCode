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

/**
 *
 */
public class HuaWeiTestCode {
    public static void main(String[] args) {
        int result = lengthOfLongestSubstring();
        System.out.println(result);
    }

    /**
     * 【九宫格】
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
     * 【相加】华老师让你们计算一个等式，s=a+aa+aaa+aaaa+aa…a(总共有n个这样的数相加)的值，其中a是一个1～9 的数字。
     * 例如当a ＝ 2，n ＝ 5时，s ＝ 2+22+222+2222+22222。
     * 输入描述：输入一行包含两个整数a(1<=a<=9)和n(1<=n<=9)，
     * 其中n为几个这样的数字相加（如描述中的例子n是5）。a和n用空格隔开
     *
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
     * 【寻找最长字符串】
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
     * 【找出不同的字母】给定两个字符串 s 和 t，它们只包含小写字母。 字符串 t 由字符串 s 随机重排，然后在随机位置添加一个字母。 请找出在 t 中被添加的字母。
     * 输入描述： 输出描述： 示例1： 输入 考生信息 考生成绩 知识点技能图谱 其他知识点 1/1 编码能力 第一行输入源字符串s，第二行输入目标字符串t 找到新添加的字符，并输出
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
     * 【字符串处理】
     * 让我们来玩个字符消除游戏吧, 给定一个只包含大写字母的字符串s，消除过程是如下进行的： 1)如果s包含长度为2的由相同字母组成的子串，那么这些子串会被消除，
     * 余下的子串拼成新的字符串。 例如”ABCCBCCCAA”中”CC”,”CC”和”AA”会被同时消除，余下”AB”, “C”和”B”拼成新的字符串”ABBC”。 2)
     * 上述消除会反复一轮一轮进行，直到新的字符串不包含相邻的相同字符为止。 例如”ABCCBCCCAA”经过一轮消除得到”ABBC”，
     * 再经过一轮消除得到”AC” 输入描述：输入由大写字母组成的字符串s，长度不超过100.  输
     * 出描述：若最后可以把整个字符串全部消除, 就输出 Yes, 否则输出 No.
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
     * 【航班预定座位数】(使用差分序列做法  O(n),贼快)
     * 这里有 n 个航班，它们分别从 1 到 n 进行编号。
     * <p>
     * 我们这儿有一份航班预订表，表中第 i 条预订记录 bookings[i] = [i, j, k] 意味着我们在从 i 到 j 的每个航班上预订了 k 个座位。
     * 请你返回一个长度为 n 的数组 answer，按航班编号顺序返回每个航班上预订的座位数
     * 输入：bookings = [[1,2,10],[2,3,20],[2,5,25]], n = 5
     * 输出：[10,55,45,25,25]
     * 思路:
     * 初始航班预订数量数组 answer = [0,0,0,0,0]，差分数组d = [0,0,0,0,0]
     * 当遍历到bookings[0] = [1,2,10]的时候，差分数组第1位加10，第3位减10，变成d = [10,0,-10,0,0]
     * 同理，当遍历到bookings[1] = [2,3,20]的时候，差分数组变成d = [10,20,-10,-20,0]
     * 当遍历到bookings[2] = [2,5,25]的时候，差分数组变成d = [10,45,-10,-20,0]，第6位要减25，我们也不需要了
     * 最后计算answer数组的值，answer[0] = d[0] = 10，answer[1] = d[1] + answer[0] = 45 + 10 = 55，answer[2] = d[2] + answer[1] = -10 + 55 = 45…
     * 最最后发现，只申请一个数组表示d[]和answer[]就可以了，over！
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
     * 【奥运会排行榜】
     * 2012伦敦奥运会即将到来，大家都非常关注奖牌榜的情况，现在我们假设奖牌榜的排名规则如下： 1、首先gold med al数量多的排在前面；
     * 2、其次silver medal数量多的排在前面；
     * 3、然后bronze medal数量多的排在前面；
     * 4、若以上三个条件仍无法区分名次，则以国家名称的 字典序排定。 我们假设国家名称不超过20个字符、各种奖牌数不超过100，且大于等于0
     * 示例
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
     * 【字符串分割】
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
     * 【猜数字游戏】
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

    /**
     * 【服务器广播问题】服务器连接方式包括直接相连，间接连接。A和B直接连接，B和C直接连接，则A和C间接连接。直接连接和间接连接 都可以发送广播。
     * 给出一个N*N数组，代表N个服务器，matrix[i][j] == 1，则代表i和j直接连接；不等于1时，代表i和j不直接连接。matrix[i][i] == 1，
     * 即自己和自己直接连接。Matrix[i][j]==matrix[j][i]计算初始需要给几台服务器广播，才可以使每个服务器都收到广播。
     */
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
     * 【新输出句子】
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
     * 【字符串匹配 】b>=a
     * 14.给你两个字符串 a 和 b ，要求输出从 b 中能找出多少和 a 相同的子串，允许有部分重叠。
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
     * 给定一个正整数n，我们取出前n小的正整数，即1~n这n个数字，将他们排列，就一共有n!种排列方 案，
     * 所有的排列方案统称为n的全排列，现在你需要做的事情就是把n的全排列都输出出来。
     * 输入描述：输入只有一个数字n(2 <= n <= 7) 输出描述：输出n的全排列。输出n!个数字的一维数组，输出的顺序按照数字从小到大输出
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
            int[] tempArray = new int[array.length - 1];//还有剩下的可选择，给递归
            System.arraycopy(array, 0, tempArray, 0, i);//把i之前元素放进数组
            System.arraycopy(array, i + 1, tempArray, i, array.length - i - 1);//把i之后元素放进数组
            stack.push(array[i]);
            perm(tempArray, stack);//执行完后，才会再走下一步，同步的，所以pop不会改变值
            stack.pop();
        }
    }
    //endregion

    /**
     * 【计算立方和】
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
     * 【判断一组数字是否连续】
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
     * 【报数游戏】
     * 19.100个人围成一圈，每个人有一个编码，编号从1开始到100。他们从1开始依次报数，报到为M的人自动退出圈圈，然 后下一个人接着从1开始报
     * 数，直到剩余的人数小于M。请问最后剩余的人在原先的编号为多少？ 例如输入M=3时，输出为： “58,91” ，输入M=4时，输出为： “34,45,97”。
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
     * 【正数翻转】
     * 给出一个 32 位的有符号整数，你需要将这个整数中每位上的数字进行反转。输出结果超出有效范围的输入，则返回0
     * 输入描述：输入为符合要求的整数，无需校验
     * 输出描述：输出翻转后的整数，即一个32位的有符号整数，超出范围返回0
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
     * 【幸运数字】
     * 我们这么定义幸运数：若k能被x、y、z任意一个数整除就可认为k是幸运数。现在你的任务是计算区间 [1,n]之间一共有多少个幸运数。
     * 输入描述：输入只有一行，包含四个整数n,x,y,z(1<= 10^9,1< x,y,z
     * 输出描述：输出只有一行，即幸运数的个数
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
     * 【分子弹】
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
     * 【元音字母请大写】
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


    /**
     * 【跳跃比赛】
     * 25.给出一组正整数，你从第一个数向最后一个数方向跳跃，每次至少跳跃1格，每个数的值表示你从这个 位置可以跳跃的最大长度。计算如何以最少的跳跃次数跳到最后一个数。
     * <p>
     * 输入描述：第一行表示有多少个数n 第二行开始依次是1到n个数，一个数一行
     * 输出描述：输出一行，表示最少跳跃的次数。
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
     * 【单词反转】
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
     * 【无重复字符的最长字串】
     * 给定一个字符串，请你找出其中不含有重复字符的 最长子串 的长度
     * 输出不含有重复字符的最长子串的长度
     */
    //双重指针写法!!!!,aaacccc
    static int lengthOfLongestSubstring() {
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();
        int[] hash = new int[500];
        int startIndex = 0;
        int max = 0;
        int start = 0, end = 0;
        while (end < s.length()) {
            char startCh = s.charAt(start);
            char endCh = s.charAt(end);//拿到Ascii码
            if (hash[endCh] == 1) {//代表字符连续
                end++;//
                if (end - start > max) {
                    //说明后面的比前面要长了，
                    max = end - start;
                    startIndex = start;
                }

            } else {//代表该字符不连续
                hash[startCh] = 0;
                hash[endCh] = 1;
                start = end;
            }
        }
        //先输出最长子串
        System.out.println(s.substring(startIndex, startIndex + max));
        return max;
    }

    /**
     * 【集五福】
     * 集五福作为近年来大家喜闻乐见迎新春活动，集合爱国福、富强福、和谐福、友善福、敬业福即可分享超大红包 题目：
     * 以0和1组成的长度为5的字符串代表每个人所得到的福卡，每一位代表一种福卡，1表示已经获得该福卡，单类 型福卡不超过1张，
     * 随机抽取一个小于10人团队，求该团队最多可以集齐多少套五福
     * 输入描述：输入指定个数类似11010,00110，由0、1组成的长度小于5的字符串，代表指定团队中每个人福卡获得情况
     * 输出描述：输出该团队能凑齐多少套五福
     */
    static void jiWuFu() {
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
                        map.put(j, map.get(j) + (chs[j] == '0' ? 0 : 1));
                    } else {
                        map.put(j, (chs[j] == '0' ? 0 : 1));
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
     * 30.磁盘的容量单位常用的有有M，G，T这三个等级，它们之间的换算关系是1T = 1000G，1G = 1000M，
     * 现在给定n块磁盘的容量，请对它们按从小 到大的顺序进行排序，例如给定3块盘的容量，1T，20M，3G，排序后的结果未20M，3G，1T
     * 输入描述：
     * 1、每个测试用例第一行包含一个整数n(2 <= n <= 1000)，表示磁盘的个数，接下的n行，每行一个字符串（长度大于2，小于10），表示磁盘的容量，格式为：mv ，其中m表示容量大小，v表示容量单位，例如20M，1T，30G
     * 2、磁盘容量m表示十进制数范围为1到1000的正整数，容量单位v的范围只包含题目中提到的M，G，T三种，换算关系如题目描述
     * 输出描述：
     * 输出n行，表示n块磁盘容量排序后的结果
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
     * 【括号生成】
     * 给定一个无重复元素的数组 和一个目标数 target ，
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
        for (int i = startIndex; i < array.length; i++) {
            if (sum + array[i] <= target) {
                stack.push(array[i]);
                kuoHaoShengCheng(array, i, target, stack);
                stack.pop();
            }
        }
    }

    /**
     * 【连续字母】
     * 给你一个字符串，只包含大写字母，求同一字母连续出现的最大次数。例如”AAAABBCDHHH”，
     * 同一字 母连续出现的最大次数为4，因为一开始A连续出现了4次。 输入描述：输入一个子串(1<长度<=100)。
     *          输出描述：输出对应每个子串同一字母连续出现的最大次数。
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
     * 【乘最多水的容器】
     * 给定 n 个非负整数 a1，a2，...，an，每个数代表坐标中的一个点 (i, ai) 。
     * 在坐标内画 n 条垂直 线，垂直线 i 的两个端点分别为 (i, ai) 和 (i, 0)。找出其中的两条线，使得它们与 x 轴共同构成的容器可以容纳最多的水
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
     *【 单词压缩编码】
     * 给定一个单词列表，我们将这个列表编码成一个索引字符串 S 与一个索引列表 A。 例如，如果这个列表是 ["time", "me", "bell"]，我们就可以将其表示为 S = "time#bell#" 和 indexes = [0, 2, 5]。 对于每一个索引，我们可以通过从字符串 S 中索引的位置开始读取字符串，
     * 直到 "#" 结束，来恢复我们之前的单词列 表。 那么成功对给定单词列表进行编码的最小字符串长度是多少呢？输入描述：输入: words = ["time","me", "bell"] 输出: 10 说明: S = "time#bell#"，indexes = [0, 2, 5]。输出描述：编码长度
     */
    static void danCiYasuoBianMa() {
        String[] inputs = new String[]{"time", "me", "bell"};
        StringBuilder sb = new StringBuilder();
        for (String str : inputs
        ) {
            if (sb.indexOf(str + "#") == -1) {
                sb.append(str + "#");
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
     * 【最长公共子串长度】
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
     * 【字符统计及重排】
     * 给出一个仅包含字母的字符串，不包含空格，统
     * 计字符串中各个字母（区分大小写）出现的次 数，并按照字母出现
     * 次数从大到小的顺序输出各个字母及其出现次数 信息，如果次数相同，按照自然顺序进行排序。
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
     * 【字符串数组压缩】
     * 对一个字符串数组进行压缩，保留头部和尾部字符，中间填写省略的长度，即internet可以缩写 为i6t。当两个字符串缩写一致时，增加头部保留的字符数，
     * 例如：aabbcc和abbbcc可以缩写为aa3c和ab3c。如果缩 写之后的长度不小于原始的长度，则保留原始的字符串，不进行缩写
     */
    static void stringArrayZip(String[] strList) {
        Map<String, String> set = new HashMap<>();
        for (String str : strList
        ) {
            StringBuilder sb = new StringBuilder();
            sb.append(str.substring(0, 1));
            sb.append(str.length() - 2);
            sb.append(str.substring(str.length() - 1, str.length()));
            int count = 1;
            while (set.containsKey(sb.toString())) {
                count++;
                String getStr = set.get(sb.toString());
                set.remove(sb.toString());
                StringBuilder tempSb = new StringBuilder();
                tempSb.append(getStr.substring(0, count));
                tempSb.append(getStr.length() - count - 1);
                tempSb.append(getStr.substring(getStr.length() - 1, getStr.length()));
                set.put(tempSb.toString(), getStr);

                sb.delete(0, sb.length());//删除之后新增
                sb.append(str.substring(0, count));
                sb.append(str.length() - count - 1);
                sb.append(str.substring(str.length() - 1, str.length()));
            }
            //跳出循环，就是不存在了,判断长度
            if (sb.length() >= str.length()) {
                sb.delete(0, sb.length());
                sb.append(str);
            }
            set.put(sb.toString(), str);
        }
        set.forEach((k, v) -> System.out.println(k));
    }

    /**
     * 以下非华为测试题，但也可参考
     */

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

    /**
     * 分苹果问题
     * m个苹果分给n个小朋友，有几种分法
     * @param m
     * @param n
     * @return
     */
    static int divideApple(int m, int n) {
        if (n == 1 || m == 0) return 1;
        if (m < n) {
            return divideApple(m, m);
        }
        return divideApple(m - n, n) + divideApple(m, n - 1);
    }


}
