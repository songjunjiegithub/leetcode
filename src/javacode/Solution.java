package javacode;

import scala.Int;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

public class Solution {
    HashMap<Integer, Integer> map;

    // 106
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder.length == 0) return null;

        map = new HashMap<>();

        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        return buildTree(inorder, postorder, 0, inorder.length - 1, 0, postorder.length
                - 1);
    }

    public TreeNode buildTree(int[] inorder, int[] postorder, int inorder_start, int inorder_end, int postorder_start, int postorder_end) {
        if (inorder_start > inorder_end) return null;

        TreeNode root = new TreeNode(postorder[postorder_end]);

        root.left = buildTree(inorder, postorder, inorder_start, this.map.get(postorder[postorder_end]) - 1, postorder_start, this.map.get(postorder[postorder_end]) - inorder_start + postorder_start - 1);
        root.right = buildTree(inorder, postorder, this.map.get(postorder[postorder_end]) + 1, inorder_end, this.map.get(postorder[postorder_end]) - inorder_start + postorder_start, postorder_end - 1);

        return root;
    }

    // 107
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        if (root == null) return new ArrayList<>();

        List<List<Integer>> result = new ArrayList<>();

        Queue<TreeNode> queue = new LinkedList<>();
        List<Integer> level = new ArrayList<>();

        queue.add(root);
        queue.add(null);

        while (!queue.isEmpty()) {
            TreeNode ele = queue.poll();
            if (ele == null) {
                result.add(0, level);
                level = new ArrayList<>();
                if (!queue.isEmpty()) queue.add(null);
            } else {
                level.add(ele.val);
                if (ele.left != null) queue.add(ele.left);
                if (ele.right != null) queue.add(ele.right);
            }
        }

        return result;
    }

    // 108
    public TreeNode sortedArrayToBST(int[] nums) {
        if (nums.length == 0) return null;

        return sortedArrayToBST(nums, 0, nums.length - 1);
    }

    public TreeNode sortedArrayToBST(int[] nums, int start, int end) {
        if (start > end) return null;

        int mid = (start + end) / 2;
        TreeNode root = new TreeNode(nums[mid]);

        root.left = sortedArrayToBST(nums, start, mid - 1);
        root.right = sortedArrayToBST(nums, mid + 1, end);

        return root;
    }

    // 109
    public TreeNode sortedListToBST(ListNode head) {
        if (head == null) return null;

        return sortedArrayToBST(head, null);
    }

    public TreeNode sortedArrayToBST(ListNode head, ListNode tail) {
        if (head == tail) return null;
        if (head.next == tail) return new TreeNode(head.val);

        ListNode fast = head.next;
        ListNode slow = head;

        while (fast != tail) {
            if (fast.next == tail) {
                fast = tail;
            } else {
                fast = fast.next.next;
                slow = slow.next;
            }
        }

        TreeNode root = new TreeNode(slow.val);
        root.left = sortedArrayToBST(head, slow);
        root.right = sortedArrayToBST(slow.next, tail);

        return root;
    }

    // 110
    int height = 0;
    int max_height = 0;

    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;

        height++;
        max_height = Math.max(max_height, height);

        int h = height;

        Boolean l = isBalanced(root.left);

        int l_max = max_height;
        height = h;
        max_height = h;

        Boolean r = isBalanced(root.right);

        int r_max = max_height;

        height = h;
        max_height = Math.max(l_max, r_max);

        return l && r && (l_max == r_max || l_max == r_max + 1 || l_max == r_max - 1);
    }

    public int minDepth(TreeNode root) {
        if (root == null) return 0;
        if (root.left == null && root.right == null) return 1;

        if (root.left == null) return minDepth(root.right) + 1;

        if (root.right == null) return minDepth(root.left) + 1;

        return 1 + Math.min(minDepth(root.left), minDepth(root.right));
    }

    // 112
    public boolean hasPathSum(TreeNode root, int targetSum) {
//        if (targetSum == 0) return root == null;
//
//        if (targetSum == 1) {
//            if (root.left == null && root.right == null) return true;
//            return false;
//        }

        if (root == null) return false;

        if (root.left == null && root.right == null) return targetSum == root.val;

        if (root.left == null) return hasPathSum(root.right, targetSum - root.val);

        if (root.right == null) return hasPathSum(root.left, targetSum - root.val);

        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    // 113
    List<List<Integer>> result;
    List<Integer> road;

    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        result = new ArrayList<>();
        road = new ArrayList<>();
        if (root == null) return result;
        road.add(root.val);
        dfs(root, targetSum - root.val);
        return result;
    }

    public void dfs(TreeNode root, int targetSum) {
        if (root.left == null && root.right == null) {
            if (targetSum == 0) result.add(new ArrayList<>(road));
            return;
        }

        if (root.left != null) {
            road.add(root.left.val);
            dfs(root.left, targetSum - root.left.val);
            road.remove(road.size() - 1);
        }

        if (root.right != null) {
            road.add(root.right.val);
            dfs(root.right, targetSum - root.right.val);
            road.remove(road.size() - 1);
        }
    }

    // 114
    public void flatten(TreeNode root) {
        if (root == null) return;

        dfs(root);
    }

    public TreeNode dfs(TreeNode root) {
        if (root == null) return null;

        TreeNode l = dfs(root.left);

        if (l != null) l.right = root.right;

        TreeNode r = dfs(root.right);

        if (l != null) root.right = root.left;
        root.left = null;

        if (r != null) {
            return r;
        } else if (l != null) {
            return l;
        } else {
            return root;
        }
    }

    // 115
    public int numDistinct(String s, String t) {
        if (t == "" || s == "") return 0;

        if (s.length() < t.length()) return 0;

        int[] db = new int[t.length()];

        if (s.charAt(0) == t.charAt(0)) db[0] = 1;

        for (int i = 1; i < s.length(); i++) {
            for (int j = t.length() - 1; j > 0; j--) {
                if (i < j) {
                    db[j] = 0;
                } else if (s.charAt(i) == t.charAt(j)) {
                    db[j] = db[j] + db[j - 1];
                }
            }

            if (s.charAt(i) == t.charAt(0)) db[0]++;
        }

        return db[db.length - 1];
    }

    // 116
//    public Node connect(Node root) {
//        if (root == null || root.left == null) return root;
//
//        Node ele = root;
//        Node head = root;
//        while (ele.left != null) {
//            while (ele.next != null) {
//                ele.left.next = ele.right;
//                ele.right.next = ele.next.left;
//                ele = ele.next;
//            }
//            ele.left.next = ele.right;
//            head = head.left;
//            ele = head;
//        }
//
//        return root;
//    }

    // 117
    public Node connectII(Node root) {
        if (root == null || (root.left == null && root.right == null)) return root;

        Node head = root;
        Node first;
        Node ele;

        while (head != null) {
            ele = head.right != null ? head.right : head.left;

            if (head.left != null && head.right != null) head.left.next = head.right;

            first = head.next;

            while (first != null) {
                if (first.left != null && first.right == null) {
                    ele.next = first.left;
                    ele = first.left;
                }

                if (first.left != null && first.right != null) {
                    ele.next = first.left;
                    first.left.next = first.right;
                    ele = first.right;
                }

                if (first.left == null && first.right != null) {
                    ele.next = first.right;
                    ele = first.right;
                }

                first = first.next;
            }

            head = head.left != null ? head.left : head.right;

            while (head != null && head.left == null && head.right == null) head = head.next;
        }

        return root;
    }

    // 118
    public List<List<Integer>> generate(int numRows) {
        List<List<Integer>> result = new ArrayList<>();
        if (numRows == 0) return result;

        List<Integer> pre = new ArrayList<>();
        List<Integer> ele;

        pre.add(1);
        result.add(pre);

        for (int i = 2; i <= numRows; i++) {
            ele = new ArrayList<>();

            ele.add(1);
            for (int j = 0; j < pre.size() - 1; j++) ele.add(pre.get(j) + pre.get(j + 1));
            ele.add(1);

            result.add(ele);
            pre = ele;
        }

        return result;
    }

    // 119
    public List<Integer> getRow(int rowIndex) {
        Integer[] result = new Integer[rowIndex + 1];

        for (int i = 0; i <= rowIndex; i++) {
            for (int j = i - 1; j > 0; j--) result[j] = result[j - 1] + result[j];
            result[i] = 1;
        }

        List<Integer> r = new ArrayList<>();

        for (Integer ele : result) {
            r.add(ele);
        }
        return r;
    }

    // 120
    public int minimumTotal(List<List<Integer>> triangle) {
        Integer[] db = new Integer[triangle.size()];

        db[0] = triangle.get(0).get(0);
        for (int i = 1; i < triangle.size(); i++) {
            db[i] = db[i - 1] + triangle.get(i).get(i);
            for (int j = i - 1; j > 0; j--) db[j] = Math.min(db[j], db[j - 1]) + triangle.get(i).get(j);
            db[0] = db[0] + triangle.get(i).get(0);
        }

        return (int) Collections.min(Arrays.asList(db));
    }

    // 121
    public int maxProfit(int[] prices) {
        int max = Integer.MIN_VALUE;
        int result = 0;
        for (int i = prices.length - 1; i >= 0; i--) {
            max = Math.max(max, prices[i]);
            result = Math.max(result, max - prices[i]);
        }

        return result;
    }

    // 122
    public int maxProfitII(int[] prices) {
        if (prices.length == 1) return 0;

        int zero = Math.max(-prices[0], -prices[1]);
        int one = Math.max(prices[1] - prices[0], 0);
        int ta;
        int tb;

        for (int i = 2; i < prices.length; i++) {
            ta = Math.max(-prices[i] + one, zero);
            tb = Math.max(prices[i] + zero, one);
            zero = ta;
            one = tb;
        }

        return one;
    }

    // 123
    public int maxProfitIII(int[] prices) {
        int zero = 0, two = 0, three = 0, four = 0;

        zero = -prices[0];
        if (prices.length >= 2) two = -prices[0] + prices[1];
        if (prices.length >= 3) three = -prices[0] + prices[1] - prices[2];
        if (prices.length >= 4) four = -prices[0] + prices[1] - prices[2] + prices[3];

        for (int i = 1; i < prices.length; i++) {
            if (i > 3) four = Math.max(prices[i] + three, four);
            if (i > 2) three = Math.max(-prices[i] + two, three);
            if (i > 1) two = Math.max(prices[i] + zero, two);
            zero = Math.max(-prices[i], zero);
        }

        return Math.max(Math.max(two, four), 0);
    }

    // 124
    public int maxPathSum(TreeNode root) {
        if (root.left == null && root.right == null) {
            return root.val;
        } else if (root.left == null) {
            return Math.max(maxPathSum(root.right), root.val + maxPathSum(root.right, true));
        } else if (root.right == null) {
            return Math.max(maxPathSum(root.left), root.val + maxPathSum(root.left, true));
        }

        return Math.max(Math.max(maxPathSum(root.left), maxPathSum(root.right)), root.val + maxPathSum(root.left, true) + maxPathSum(root.right, true));
    }

    public int maxPathSum(TreeNode root, Boolean flag) {
        if (root == null) return 0;

        return Math.max(0, root.val + Math.max(maxPathSum(root.left, true), maxPathSum(root.right, true)));
    }

    public boolean isPalindrome(String s) {
        s = s.toLowerCase(Locale.ROOT);
        int i = 0, j = s.length() - 1;
        while (i < j) {
            if (!Character.isLetterOrDigit(s.charAt(i))) {
                i += 1;
            } else if (!Character.isLetterOrDigit(s.charAt(j))) {
                j -= 1;
            } else {
                if (s.charAt(i) != s.charAt(j)) return false;
                i += 1;
                j -= 1;
            }
        }

        return true;
    }

    //126
    public int difference(String s1, String s2) {
        int result = 0;
        for (int i = 0; i < s1.length(); i++) if (s1.charAt(i) != s2.charAt(i)) result++;
        return result;
    }

    class Tree {
        String val;
        Boolean flag;
        List<Integer> list;

        public Tree(String val) {
            this.val = val;
        }

        public Tree(String val, List<Integer> list, Boolean flag) {
            this.val = val;
            this.list = list;
            this.flag = flag;
        }
    }

    List<List<String>> resultI = new ArrayList<>();
    List<String> roadI = new ArrayList<>();

    public List<List<String>> findLadders(String beginWord, String endWord, List<String> wordList) {
        Tree[] tree = new Tree[wordList.size()];

        int end_index = -1;
        for (int i = 0; i < tree.length; i++) {
            tree[i] = new Tree(wordList.get(i), new ArrayList<>(), false);
            if (wordList.get(i) == endWord) end_index = i;
        }

        if (end_index == -1) return new ArrayList<>();

        Tree start = new Tree(beginWord, new ArrayList<>(), false);

        for (int i = 0; i < wordList.size(); i++) if (difference(beginWord, wordList.get(i)) == 1) start.list.add(i);

        for (int i = 0; i < wordList.size(); i++) {
            for (int j = i + 1; j < wordList.size(); j++) {
                if (difference(wordList.get(i), wordList.get(j)) == 1) {
                    tree[i].list.add(j);
                    tree[j].list.add(i);
                }
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        int step = -1;
        queue.add(-1);
        queue.add(null);

        // 找到最短路径所需步数step
        while (!queue.isEmpty()) {
            Integer ele = queue.poll();
            Tree t;
            if (ele == null) {
                if (queue.isEmpty()) return new ArrayList<>();
                step++;
                queue.add(null);
            } else {
                if (ele == -1) {
                    t = start;
                } else {
                    t = tree[ele];
                }

                for (int i = 0; i < t.list.size(); i++) {
                    if (t.list.get(i) == end_index) {
                        step += 2;
                        queue.clear();
                        break;
                    } else if (!tree[t.list.get(i)].flag) {
                        queue.add(t.list.get(i));
                        tree[t.list.get(i)].flag = true;
                    }
                }
            }
        }


        // 回朔算法找到所有最短路径
        roadI.add(start.val);
        dfs(start, 0, step, endWord, tree);

        return resultI;
    }

    public void dfs(Tree start, int index, int step, String endWord, Tree[] tree) {
        if (index == step) {
            if (roadI.get(step) == endWord) resultI.add(new ArrayList<>(roadI));
            return;
        }

        for (int i = 0; i < start.list.size(); i++) {
            roadI.add(tree[start.list.get(i)].val);
            dfs(tree[start.list.get(i)], index + 1, step, endWord, tree);
            roadI.remove(roadI.size() - 1);
        }
    }

    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Tree[] tree = new Tree[wordList.size()];

        int end_index = -1;
        for (int i = 0; i < tree.length; i++) {
            tree[i] = new Tree(wordList.get(i), new ArrayList<>(), false);
            if (wordList.get(i).equals(endWord)) end_index = i;
        }

        if (end_index == -1) return 0;

        Tree start = new Tree(beginWord, new ArrayList<>(), false);

        for (int i = 0; i < wordList.size(); i++) if (difference(beginWord, wordList.get(i)) == 1) start.list.add(i);

        for (int i = 0; i < wordList.size(); i++) {
            for (int j = i + 1; j < wordList.size(); j++) {
                if (difference(wordList.get(i), wordList.get(j)) == 1) {
                    tree[i].list.add(j);
                    tree[j].list.add(i);
                }
            }
        }

        Queue<Integer> queue = new LinkedList<>();
        int step = -1;
        queue.add(-1);
        queue.add(null);

        // 找到最短路径所需步数step
        while (!queue.isEmpty()) {
            Integer ele = queue.poll();
            Tree t;
            if (ele == null) {
                if (queue.isEmpty()) return 0;
                step++;
                queue.add(null);
            } else {
                if (ele == -1) {
                    t = start;
                } else {
                    t = tree[ele];
                }

                for (int i = 0; i < t.list.size(); i++) {
                    if (t.list.get(i) == end_index) {
                        step += 2;
                        queue.clear();
                        break;
                    } else if (!tree[t.list.get(i)].flag) {
                        queue.add(t.list.get(i));
                        tree[t.list.get(i)].flag = true;
                    }
                }
            }
        }

        return step + 1;
    }

    // 128
    public int longestConsecutive(int[] nums) {
//        // 遍历过的元素形成的每个连续区间,key为区间开头，value为区间结尾
//        HashMap<Integer, Integer> head = new HashMap<>();
//        // 遍历过的元素形成的每个连续区间,key为区间结尾,value为区间开头
//        HashMap<Integer, Integer> tail = new HashMap<>();
//
//        // 防止重复元素
//        HashSet<Integer> union = new HashSet<>();
//
//        // 从头开始遍历
//        for (int i = 0; i < nums.length; i++) {
//            // 如果此元素前面已经存在则过滤
//            if (!union.contains(nums[i])) {
//                union.add(nums[i]);
//                // 1.存在nums[i]+1开头和nums[i]-1结尾的两个区间，则将此两个区间合并
//                if (head.get(nums[i] + 1) != null && tail.get(nums[i] - 1) != null) {
//                    head.put(tail.get(nums[i] - 1), head.get(nums[i] + 1));
//                    tail.put(head.get(nums[i] + 1), tail.get(nums[i] - 1));
//                    head.remove(nums[i] + 1);
//                    tail.remove(nums[i] - 1);
//                }
//                // 2.存在nums[i]+1开头的区间,则将此区间开头改为nums[i]
//                else if (head.get(nums[i] + 1) != null) {
//                    head.put(nums[i], head.get(nums[i] + 1));
//                    head.remove(nums[i] + 1);
//                    tail.put(head.get(nums[i]), tail.get(head.get(nums[i])) - 1);
//                }
//                // 3.存在num[i] - 1结尾的区间,则将此区间结尾改为nums[i]
//                else if (tail.get(nums[i] - 1) != null) {
//                    tail.put(nums[i], tail.get(nums[i] - 1));
//                    tail.remove(nums[i] - 1);
//                    head.put(tail.get(nums[i]), head.get(tail.get(nums[i])) + 1);
//                }
//                // 4.存入区间[nums[i],nums[i]]
//                else {
//                    head.put(nums[i], nums[i]);
//                    tail.put(nums[i], nums[i]);
//                }
//            }
//        }
//
//        int max = 0;
//        // 循环每个区间,每个区间长度为value - key + 1
//        for (Integer key : head.keySet()) {
//            max = Math.max(max, head.get(key) - key + 1);
//        }
//
//        return max;

        // 解法2
        HashSet<Integer> set = new HashSet<>();

        for (int num : nums) {
            set.add(num);
        }

        int max = 0;

        for (Integer ele : set) {
            if (!set.contains(ele - 1)) {
                int index = ele + 1;
                while (set.contains(index)) index++;

                max = Math.max(max, index - ele);
            }
        }

        return max;
    }

    // 129
//    int result = 0;
//    public int sumNumbers(TreeNode root) {
//        result = 0;
//        dfs(root, root.val);
//        return result;
//    }
//
//    public void dfs(TreeNode root, int road) {
//        if (root.left == null && root.right == null) {
//            result += road;
//        }
//
//        if (root.left != null) {
//            dfs(root.left, 10 * road + root.left.val);
//        }
//
//        if (root.right != null) {
//            dfs(root.right, 10 * road + root.right.val);
//        }
//    }

    // 130
    public void solve(char[][] board) {
        for (int j = 0; j < board[0].length; j++) {
            if (board[0][j] == 'O') fill(board, 0, j);
            if (board[board.length - 1][j] == 'O') fill(board, board.length - 1, j);
        }

        for (int i = 0; i < board.length; i++) {
            if (board[i][0] == 'O') fill(board, i, 0);
            if (board[i][board[0].length - 1] == 'O') fill(board, i, board[0].length - 1);
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 'O') board[i][j] = 'X';
                if (board[i][j] == 'A') board[i][j] = 'O';
            }
        }
    }

    public void fill(char[][] board, int i, int j) {
        if (i == board.length || i == -1 || j == board[0].length || j == -1 || board[i][j] != 'O') return;

        board[i][j] = 'A';

        fill(board, i + 1, j);
        fill(board, i - 1, j);
        fill(board, i, j + 1);
        fill(board, i, j - 1);
    }

    // 132
    int min = 0;

    public int minCut(String s) {
        this.min = s.length();
        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
        int[] db = new int[s.length()];

        for (int i = 0; i < s.length(); i++) {
            for (int l = 0; i - l >= 0 && i + l < s.length(); l++) {
                if (l == 0 || (s.charAt(i - l) == s.charAt(i + l) && map.get(i - l + 1).contains(i + l - 1))) {
                    HashSet<Integer> tmp = map.getOrDefault(i - l, new HashSet<Integer>());
                    tmp.add(i + l);
                    map.put(i - l, tmp);
                }
            }

            for (int l = 0; i - l >= 0 && i + 1 + l < s.length(); l++) {
                if (s.charAt(i - l) == s.charAt(i + 1 + l) && (l == 0 || map.get(i - l + 1).contains(i + l))) {
                    HashSet<Integer> tmp = map.getOrDefault(i - l, new HashSet<Integer>());
                    tmp.add(i + 1 + l);
                    map.put(i - l, tmp);
                }
            }
        }

        dfs(s, 0, 0, map, db);

        return this.min - 1;
    }

    public int dfs(String s, int index, int count, HashMap<Integer, HashSet<Integer>> map, int[] db) {
        if (index == s.length()) {
            this.min = Math.min(this.min, count);
            return 0;
        }

        if (db[index] != 0) {
            this.min = Math.min(this.min, count + db[index]);
            return db[index];
        }

        int tmp_count = Integer.MAX_VALUE;
        for (Integer end : map.get(index)) {
            tmp_count = Math.min(tmp_count, dfs(s, end + 1, count + 1, map, db) + 1);
        }

        db[index] = tmp_count;

        return tmp_count;
    }

    // 133
    // Definition for a Node.
//    static class Node {
//        public int val;
//        public List<Test.Node> neighbors;
//
//        public Node() {
//            val = 0;
//            neighbors = new ArrayList<Test.Node>();
//        }
//
//        public Node(int _val) {
//            val = _val;
//            neighbors = new ArrayList<Test.Node>();
//        }
//
//        public Node(int _val, ArrayList<Test.Node> _neighbors) {
//            val = _val;
//            neighbors = _neighbors;
//        }
//    }
//
//
//
//    public Test.Node cloneGraph(Test.Node node) {
//        HashMap<Test.Node, Test.Node> map = new HashMap<>();
//        HashSet<Test.Node> isOk = new HashSet<>();
//        return cloneGraph(node, map, isOk);
//    }
//
//    public Test.Node cloneGraph(Test.Node node, HashMap<Test.Node, Test.Node> map, HashSet<Test.Node> isOk) {
//        if (node == null) return null;
//
//        if (isOk.contains(node)) return map.get(node);
//
//        if (map.get(node) == null) {
//            map.put(node, new Test.Node(node.val));
//        }
//
//        if (map.get(node).neighbors.size() == 0) {
//            for (Test.Node neighbor : node.neighbors) {
//                map.put(neighbor, map.getOrDefault(neighbor, new Test.Node(neighbor.val)));
//                map.get(node).neighbors.add(map.get(neighbor));
//            }
//        }
//
//        isOk.add(node);
//
//        for (Test.Node neighbor : node.neighbors) {
//            cloneGraph(neighbor, map, isOk);
//        }
//
//        return map.get(node);
//    }

    // 134
    public int canCompleteCircuit(int[] gas, int[] cost) {
//        int count = 0;
//        int min = 0;
//
//        for (int j = 0; j < gas.length; j++) {
//            count = count + gas[j] - cost[j];
//            min = Math.min(min, count);
//        }
//
//        if (min >= 0) return 0;
//        if (count < 0) return -1;
//
//        for (int i = 1; i < gas.length; i++) {
//            min = Math.min(min - (gas[i - 1] - cost[i - 1]), count);
//            if (min >= 0) return i;
//        }
//
//        return -1;

        // 方法2
        // 从0到最近不能到达油站的上一个油站累计值
        int count = 0;
        // 最近不能到达的加油站下标
        int index = 0;
        // 从0到最近不能到达油站的上一个油站经过的所有站点，油量的最低值，可以为负
        int min = 0;
        // 从最近不能到达的油站开始的累计值
        int sum = 0;
        // 从最近不能到达的油站开始经过站点的最小值
        int l_min = 0;

        for (int i = 0; i < gas.length; i++) {
            sum += gas[i] - cost[i];
            l_min = Math.min(l_min, sum);

            if (sum < 0) {
                index = i + 1;
                min = Math.min(min, count + l_min);
                count += sum;
                sum = 0;
                l_min = 0;
            }
        }


        if (index == gas.length || sum + min < 0) return -1;

        return index;
    }

    // 137
    public int singleNumber(int[] nums) {
        int a = 0, b = 0;

        for (int i = 0; i < nums.length; i++) {
            a = a ^ (b & nums[i]);
            b = (b ^ nums[i]) | ((~a) & b & nums[i]);
        }

        return a ^ b;
    }

    // 138
//    public Node copyRandomList(Node head) {
//        HashMap<Node, Test.Node> map = new HashMap<>();
//    }

    // 139
//    public boolean wordBreak(String s, List<String> wordDict) {
//        if (s.length() == 0) return false;
//
//        HashSet<String> set = new HashSet<>(wordDict);
//        Boolean[] db = new Boolean[s.length()];
//
//        db[0] = set.contains(s.charAt(0) + "");
//
//        for (int i = 1; i < db.length; i++) {
//            db[i] = set.contains(s.substring(0, i + 1));
//            for (int j = 0; j < i; j++) {
//                db[i] = db[i] || (db[j] && set.contains(s.substring(j + 1, i + 1)));
//            }
//        }
//
//        return db[db.length - 1];
//    }

    // 140
    public List<String> wordBreak(String s, List<String> wordDict) {
        if (s.length() == 0) return new ArrayList<>();

        HashSet<String> set = new HashSet<>(wordDict);
        List<String> result = new ArrayList<>();
        StringBuffer road = new StringBuffer();

        dfs(s, 0, road, result, set);

        return result;
    }

    public void dfs(String s, int index, StringBuffer road, List<String> result, HashSet<String> set) {
        if (index == s.length()) {
            result.add(road.toString().substring(0, road.length() - 1));
            return;
        }

        for (int i = index; i < s.length(); i++) {
            if (set.contains(s.substring(index, i + 1))) {
                road.append(s.substring(index, i + 1));
                road.append(' ');
                dfs(s, i + 1, road, result, set);
                road.delete(road.length() - i + index - 2, road.length());
            }
        }
    }

    // 141
    public boolean hasCycle(ListNode head) {
        if (head == null) return false;

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null) {
            if (fast.next == null) return false;
            fast = fast.next;
            if (fast == slow) return true;
            fast = fast.next;
            if (fast == slow) return true;
            slow = slow.next;
        }

        return false;
    }

    // 142
    public ListNode detectCycle(ListNode head) {
        if (head == null || head.next == null) return null;

        int l = 1;
        ListNode fast = head.next;
        ListNode slow = head;
        while (fast != null) {
            l++;
            slow = slow.next;

            fast = fast.next;
            if (fast == null) return null;
            fast = fast.next;
            if (fast == slow) break;
        }

        if (fast == null) return null;

        slow = head;
        fast = fast.next;

        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return slow;
    }

    // 143
    int count = 0;

    public void reorderList(ListNode head) {
//        if (head == null) return;
//
//        Deque<ListNode> deque = new ArrayDeque<>();
//
//        ListNode tmp = head;
//        while (tmp != null) {
//            deque.addLast(tmp);
//            tmp = tmp.next;
//        }
//
//        ListNode hair = new ListNode(0);
//
//        tmp = hair;
//        while (!deque.isEmpty()) {
//            tmp.next = deque.pollFirst();
//            tmp = tmp.next;
//            if (!deque.isEmpty()) {
//                tmp.next = deque.pollLast();
//                tmp = tmp.next;
//            }
//        }
//
//        tmp.next = null;

        // 方法2
        if (head == null) return;

        dfs(head, 1);
    }

    public ListNode dfs(ListNode head, int index) {
        if (head == null) {
            this.count = index - 1;
            return head;
        }

        ListNode ele = dfs(head.next, index + 1);

        if (index > (count + 1) / 2) return head;

        if ((count & 1) == 1 && index == (count + 1) / 2) {
            head.next = null;
            return ele;
        }


        ListNode tmp = ele.next;

        ele.next = index == count / 2 && (count & 1) == 0 ? null : head.next;
        head.next = ele;

        return tmp;
    }

    // 144
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        preorderTraversal(root, result);
        return result;
    }

    public void preorderTraversal(TreeNode root, List<Integer> result) {
        if (root == null) return;

        result.add(root.val);
        preorderTraversal(root.left, result);
        preorderTraversal(root.right, result);
    }

    // 145
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();

        postorderTraversal(root, result);

        return result;
    }

    public void postorderTraversal(TreeNode root, List<Integer> result) {
        if (root == null) return;

        postorderTraversal(root.left, result);
        postorderTraversal(root.right, result);
        result.add(root.val);
    }

    // 147
    public ListNode insertionSortList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode hair = new ListNode(0, head);

        ListNode ele = head.next;
        head.next = null;

        while (ele != null) {
            ListNode pre = hair;
            ListNode next = ele.next;

            while (pre.next != null && pre.next.val <= ele.val) pre = pre.next;

            if (pre.next == null) {
                pre.next = ele;
                ele.next = null;
            } else {
                ele.next = pre.next;
                pre.next = ele;
            }

            ele = next;
        }

        return hair.next;
    }

    // 148
    // 堆排序
//    ListNode[] arr;
//    public ListNode sortList(ListNode head) {
//        if (head == null || head.next == null) return head;
//
//        int len = 0;
//        ListNode ele = head;
//        while (ele != null) {
//            len++;
//            ele = ele.next;
//        }
//
//        arr = new ListNode[len];
//        len = 0;
//        ele = head;
//        while (ele != null) {
//            arr[len] = ele;
//            ele = ele.next;
//            len++;
//        }
//
//        int last = arr.length - 1;
//
//        for (int i = getParentIndex(last); i >= 0; i--) {
//            adjustHeap(i, last);
//        }
//
//        ListNode hair = new ListNode(0);
//
//        ele = hair;
//        while (last > 0) {
//            ele.next = arr[0];
//            ele = ele.next;
//            swap(0, last);
//            last--;
//            adjustHeap(0, last);
//        }
//        ele.next = arr[0];
//
//        return hair.next;
//    }
//
//    public void adjustHeap(int i, int len) {
//        int left, right, j;
//
//        left = getLeftChildIndex(i);
//
//        while (left <= len) {
//            j = left;
//            right = left + 1;
//            if (right <= len && this.arr[left].val < this.arr[right].val) {
//                j = right;
//            }
//
//            if (this.arr[i].val >= this.arr[j].val) {
//                break;
//            } else {
//                swap(i, j);
//                i = j;
//                left = getLeftChildIndex(i);
//            }
//
//        }
//    }
//
//    public int getParentIndex(int i) {
//        return (i - 1) / 2;
//    }
//
//    public int getLeftChildIndex(int i) {
//        return 2 * i + 1;
//    }
//
//    public void swap(int i, int j) {
//        ListNode tmp = this.arr[j];
//        this.arr[j] = this.arr[i];
//        this.arr[i] = tmp;
//    }

    // 归并排序，自底向上
    public ListNode sortList(ListNode head) {
        if (head == null || head.next == null) return head;

        int len = 0;
        ListNode ele = head;
        while (ele != null) {
            len++;
            ele = ele.next;
        }

        ListNode hair = new ListNode(0, head);

        for (int subLength = 1; subLength < len; subLength <<= 1) {
            ListNode first = hair.next;
            ListNode last = hair;
            ListNode head1;
            ListNode head2;

            while (first != null) {
                ele = first;
                head1 = first;
                for (int i = 0; i < subLength; i++) {
                    if (ele == null) break;
                    ele = ele.next;
                }

                if (ele == null) {
                    last.next = first;
                    break;
                }

                head2 = ele;
                first = head2;

                for (int i = 0; i < subLength; i++) {
                    if (first == null) break;
                    first = first.next;
                }

                last = merge(head1, head2, last, subLength);
            }

            last.next = null;
        }

        return hair.next;
    }

    public ListNode merge(ListNode head1, ListNode head2, ListNode last, int subLength) {
        if (head1 == null) return head1;

        if (head2 == null) {
            last.next = head1;
            while (last.next != null) last = last.next;
            return last;
        }

        ListNode one = head1;
        ListNode two = head2;

        int count1 = 0, count2 = 0;
        while (one != null && two != null && count1 < subLength && count2 < subLength) {
            if (one.val < two.val) {
                last.next = one;
                last = last.next;
                one = one.next;
                count1++;
            } else {
                last.next = two;
                last = last.next;
                two = two.next;
                count2++;
            }
        }

        if (count1 < subLength) {
            last.next = one;
        } else {
            last.next = two;
        }

        while (last.next != null) last = last.next;

        return last;
    }

    // 149
    public int maxPoints(int[][] points) {
        if (points.length == 0 || points.length == 1) return points.length;

        HashMap<Double, Object[]> slope_map = new HashMap<>();

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double slope = points[j][0] == points[i][0] ? (double) Integer.MAX_VALUE : (double) (points[j][1] - points[i][1]) / (points[j][0] - points[i][0]);

                if (slope_map.get(slope) == null)
                    slope_map.put(slope, new Object[]{new HashMap<Integer, HashSet<Integer>>(), new int[points.length]});

                HashMap<Integer, HashSet<Integer>> map = (HashMap<Integer, HashSet<Integer>>) slope_map.get(slope)[0];
                int[] arr_tmp = (int[]) slope_map.get(slope)[1];

                if (arr_tmp[i] == 0 && arr_tmp[j] == 0) {
                    arr_tmp[i] = i + 1;
                    arr_tmp[j] = i + 1;
                    HashSet<Integer> set = new HashSet<>();
                    set.add(i);
                    set.add(j);
                    map.put(i + 1, set);
                } else if (arr_tmp[i] == 0 && arr_tmp[j] != 0) {
                    arr_tmp[i] = arr_tmp[j];
                    map.get(arr_tmp[j]).add(i);
                } else if (arr_tmp[i] != 0 && arr_tmp[j] == 0) {
                    arr_tmp[j] = arr_tmp[i];
                    map.get(arr_tmp[i]).add(j);
                } else if (arr_tmp[i] != arr_tmp[j]) {
                    HashSet<Integer> remove_set = map.remove(arr_tmp[j]);
                    map.get(arr_tmp[i]).addAll(remove_set);
                    for (Integer integer : remove_set) {
                        arr_tmp[integer] = arr_tmp[i];
                    }
                }
            }
        }

        int max = 0;
        for (Object[] value : slope_map.values()) {
            HashMap<Integer, HashSet<Integer>> map = (HashMap<Integer, HashSet<Integer>>) value[0];
            for (HashSet<Integer> set : map.values()) {
                max = Math.max(max, set.size());
            }
        }

        return max;
    }

    // 150
    public int evalRPN(String[] tokens) {
        if (tokens.length == 0) return 0;

        Stack<Integer> stack = new Stack<>();


        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals("+")) {
                stack.add(stack.pop() + stack.pop());
            } else if (tokens[i].equals("-")) {
                stack.add(-stack.pop() + stack.pop());
            } else if (tokens[i].equals("*")) {
                stack.add(stack.pop() * stack.pop());
            } else if (tokens[i].equals("/")) {
                int tmp = stack.pop();
                stack.add(stack.pop() / tmp);
            } else {
                stack.add(parseInt(tokens[i]));
            }
        }

        return stack.pop();
    }

    // 151
    public String reverseWords(String s) {
        if (s.length() == 0) return s;

        int[][] df = {{0, 1}, {2, 1}, {2, 3}, {2, 3}};

        int tail = s.length() - 1;

        StringBuffer result = new StringBuffer();
        int flag = 0;


        for (int i = s.length() - 1; i >= 0; i--) {
            if ((flag == 0 || flag == 2) && s.charAt(i) != ' ') {
                tail = i;
            } else if (flag == 1 && s.charAt(i) == ' ') {
                result.append(s.substring(i + 1, tail + 1));
            } else if (flag == 3 && s.charAt(i) == ' ') {
                result.append(' ' + s.substring(i + 1, tail + 1));
            }

            flag = getNumber(flag, s.charAt(i), df);
        }

        if (flag == 1) result.append(s.substring(0, tail + 1));
        if (flag == 3) result.append(' ' + s.substring(0, tail + 1));

        return result.toString();
    }

    public int getNumber(int flag, Character ch, int[][] df) {
        if (ch == ' ') return df[flag][0];

        return df[flag][1];
    }

    // 152
    public int maxProduct(int[] nums) {
        if (nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int start = 0;
        int result = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                for (int j = start; j < i; j++) {
                    for (int k = j + 1; k < i; k++) {
                        result = Math.max(result, nums[k] / nums[j]);
                    }

                    result = Math.max(result, nums[j]);
                }

                start = i + 1;
            } else if (i == start) {
                continue;
            } else {
                nums[i] = nums[i - 1] * nums[i];
            }
        }

        if (start != nums.length) {
            for (int j = start; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    result = Math.max(result, nums[k] / nums[j]);
                }

                result = Math.max(result, nums[j]);
            }
        }

        return result;
    }

    // 153
    public int findMin(int[] nums) {
        if (nums.length == 1) return nums[0];

        if (nums[0] < nums[nums.length - 1]) return nums[0];

        int i = 1, j = nums.length - 1;

        while (i <= j) {
            int mid = (i + j) / 2;

            if (nums[mid] < nums[mid - 1]) return nums[mid];

            if (nums[mid] > nums[0]) {
                i = mid + 1;
            } else {
                j = mid - 1;
            }
        }

        return i;
    }

    // 154
    public int findMinII(int[] nums) {
        if (nums.length == 1) return nums[0];

        if (nums[0] < nums[nums.length - 1]) return nums[0];

        int last = nums.length - 1;

        while (last >= 0 && nums[last] == nums[0]) last--;

        if (last < 0) return nums[0];

        int i = 0;
        int j = last + 1;

        while (i < j) {
            int mid = (i + j) / 2;
            if (nums[mid] >= nums[0]) {
                i = mid + 1;
            } else {
                j = mid;
            }
        }

        return nums[i];
    }

    // 156
    public TreeNode upsideDownBinaryTree(TreeNode root) {
        if (root == null || root.left == null) return root;

        TreeNode left = root.left;

        TreeNode left_root = upsideDownBinaryTree(root.left);

        left.right = root;
        left.left = upsideDownBinaryTree(root.right);

        root.left = null;
        root.right = null;

        return left_root;
    }

    // 159
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s.length() < 3) return s.length();

        int i = 0;
        int j = 0;
        int count = 0;
        int result = 0;
        HashMap<Character, Integer> map = new HashMap<>();

        while (j < s.length()) {
            if (map.containsKey(s.charAt(j))) {
                map.put(s.charAt(j), map.get(s.charAt(j)) + 1);
            } else if (count < 2) {
                count++;
                map.put(s.charAt(j), 1);
            } else {
                result = Math.max(result, j - i);
                map.put(s.charAt(j), 1);
                while (map.get(s.charAt(i)) > 1) {
                    map.put(s.charAt(i), map.get(s.charAt(i)) - 1);
                    i++;
                }
                map.remove(s.charAt(i));
                i++;
            }

            j++;
        }

        result = Math.max(result, j - i);

        return result;
    }

    // 160
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) return null;

        int count_a = 0;
        int count_b = 0;

        ListNode last_a = headA;
        ListNode last_b = headB;

        while (last_a.next != null) {
            count_a++;
            last_a = last_a.next;
        }
        count_a++;


        while (last_b.next != null) {
            count_b++;
            last_b = last_b.next;
        }
        count_b++;

        if (last_a != last_b) return null;

        int steps;
        last_a = headA;
        last_b = headB;

        if (count_a > count_b) {
            steps = count_a - count_b;
            while (steps > 0) {
                last_a = last_a.next;
                steps--;
            }
        } else {
            steps = count_b - count_a;
            while (steps > 0) {
                last_b = last_b.next;
                steps--;
            }
        }

        while (last_a != last_b) {
            last_a = last_a.next;
            last_b = last_b.next;
        }

        return last_a;
    }

    // 161
    public boolean isOneEditDistance(String s, String t) {
        if (Math.abs(s.length() - t.length()) > 1) return false;

        if (s.length() == t.length()) {
            int count = 0;
            for (int i = 0; i < s.length(); i++) {
                if (s.charAt(i) != t.charAt(i)) {
                    if (count == 1) return false;
                    count++;
                }
            }
            return true;
        }

        if (s.length() < t.length()) {
            return isOneEditDistance(t, s);
        }

        int count = 0;
        int i = 0, j = 0;

        while (j < t.length()) {
            if (s.charAt(i) != t.charAt(j)) {
                if (count == 1) return false;
                count++;
                i++;
            } else {
                i++;
                j++;
            }
        }

        return true;
    }

    // 162
    public int findPeakElement(int[] nums) {
        if (nums.length == 1) return 0;

        int i = 1;
        while (i < nums.length) {
            if (nums[i] < nums[i - 1]) {
                return i - 1;
            } else {
                i++;
            }
        }

        return nums.length - 1;
    }

    // 164
    public int maximumGap(int[] nums) {
        if (nums.length <= 1) return 0;

        int min = Integer.MAX_VALUE;
        int max = 0;

        for (int num : nums) {
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        int[] arr = new int[max - min + 1];
        for (int num : nums) {
            arr[num - min]++;
        }

        int result = 0;
        int pre = 0;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] != 0) {
                result = Math.max(result, i - pre);
                pre = i;
            }
        }

        return result;
    }

    // 165
    public int compareVersion(String version1, String version2) {
        if (version2.length() == 0) {
            for (int i = 0; i < version1.length(); i++) {
                if (version1.charAt(i) != '0' && version1.charAt(i) != '.') return 1;
            }

            return 0;
        }

        if (version1.length() == 0) {
            for (int i = 0; i < version2.length(); i++) {
                if (version2.charAt(i) != '0' && version2.charAt(i) != '.') return -1;
            }

            return 0;
        }

        int i = 0;
        int j = 0;
        while (i < version1.length() && j < version2.length()) {
            while (i < version1.length() && version1.charAt(i) == '0') i++;
            while (j < version2.length() && version2.charAt(j) == '0') j++;

            if ((i == version1.length() || version1.charAt(i) == '.') && (j < version2.length() && version2.charAt(j) != '.'))
                return -1;
            if ((i < version1.length() && version1.charAt(i) != '.') && (j == version2.length() || version2.charAt(j) == '.'))
                return 1;
            if ((i == version1.length() || version1.charAt(i) == '.') && (j == version2.length() || version2.charAt(j) == '.')) {
                i++;
                j++;
            } else if (version1.charAt(i) > version2.charAt(j)) {
                return 1;
            } else if (version1.charAt(i) < version2.charAt(j)) {
                return -1;
            } else {
                i++;
                j++;
            }
        }

        if (i >= version1.length() && j >= version2.length()) return 0;

        if (i >= version1.length()) return compareVersion("", version2.substring(j));

        return compareVersion(version1.substring(i), "");
    }

    // 166
    public String fractionToDecimal(int numerator, int denominator) {
        HashMap<Long, Integer> remainderSet = new HashMap<>();
        StringBuilder resultBuffer = new StringBuilder();
        if ((numerator < 0 && denominator > 0) || (numerator > 0 && denominator < 0)) resultBuffer.append('-');

        Long a = Math.abs((long) numerator);
        Long b = Math.abs((long) denominator);

        resultBuffer.append(a / b);
        a = a % b;
        if (a == 0) return resultBuffer.toString();
        resultBuffer.append('.');
        remainderSet.put(a, resultBuffer.length());
        a = a * 10;
        while (true) {
            resultBuffer.append(a / b);
            a = a % b;
            if (a == 0) return resultBuffer.toString();
            if (remainderSet.get(a) != null) {
                return resultBuffer.substring(0, remainderSet.get(a)) + "(" + resultBuffer.substring(remainderSet.get(a)) + ")";
            }
            remainderSet.put(a, resultBuffer.length());
            a = a * 10;
        }
    }

    // 167
    public int[] twoSum(int[] numbers, int target) {
        int j = numbers.length - 1, i = 0;
        while (i < j) {
            int tmp = numbers[i] + numbers[j];
            if (tmp == target) {
                return new int[]{i + 1, j + 1};
            } else if (tmp < target) {
                i++;
            } else {
                j--;
            }
        }

        return null;
    }

    // 168
    public String convertToTitle(int columnNumber) {
        StringBuilder stringBuffer = new StringBuilder();

        while (columnNumber > 26) {
            int tmp = columnNumber % 26;
            if (tmp == 0) {
                stringBuffer.append('Z');
                columnNumber = columnNumber / 26 - 1;
            } else {
                stringBuffer.append((char) ('A' - 1 + tmp));
                columnNumber /= 26;
            }
        }

        if (columnNumber != 0) stringBuffer.append((char) ('A' - 1 + columnNumber));

        return stringBuffer.reverse().toString();
    }

    // 169
    public int majorityElement(int[] nums) {
        if (nums.length <= 2) return nums[0];

        HashMap<Integer, Integer> map = new HashMap<>();

        int t = nums.length / 2;

        for (int i = 0; i < nums.length; i++) {
            if (map.getOrDefault(nums[i], 0) == t) return nums[i];
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }

        return -1;
    }

    // 170
    class TwoSum {
        HashSet<Integer> eleSet;
        HashSet<Integer> resultSet;

        /**
         * Initialize your data structure here.
         */
        public TwoSum() {
            eleSet = new HashSet<>();
            resultSet = new HashSet<>();
        }

        /**
         * Add the number to an internal data structure..
         */
        public void add(int number) {
            for (Integer ele : eleSet) {
                resultSet.add(ele + number);
            }
            eleSet.add(number);
        }

        /**
         * Find if there exists any pair of numbers which sum is equal to the value.
         */
        public boolean find(int value) {
            return resultSet.contains(value);
        }
    }

    /**
     * Your TwoSum object will be instantiated and called as such:
     * TwoSum obj = new TwoSum();
     * obj.add(number);
     * boolean param_2 = obj.find(value);
     */

    // 171
    public int titleToNumber(String columnTitle) {
        if (columnTitle.isEmpty()) return 0;

        int result = 0;

        for (int i = 0; i < columnTitle.length(); i++) {
            result = result * 26 + (columnTitle.charAt(i) - 'A' + 1);
        }

        return result;
    }

    // 172
    public int trailingZeroes(int n) {
        int result = 0;
        while (n >= 5) {
            n /= 5;
            result += n;
        }

        return result;
    }

    // 179
    public String largestNumber(int[] nums) {
        Integer[] integers = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++) {
            integers[i] = nums[i];

        }
        Arrays.sort(integers, (o1, o2) -> {
            int xt = 10;
            int yt = 10;
            while (xt <= o1) xt *= 10;
            while (yt <= o2) yt *= 10;
            return -o1 * yt - o2 + o2 * xt + o1;
        });

        if (integers[0] == 0) return "0";

        StringBuilder result = new StringBuilder();

        for (Integer ele : integers) {
            result.append(ele);
        }

        return result.toString();
    }

    // 186
    public void reverseWords(char[] s) {
        swap(0, s.length - 1, s);

        int start = 0;

        for (int i = 0; i < s.length; i++) {
            if (s[i] == ' ') {
                swap(start, i - 1, s);
                start = i + 1;
            }
        }

        swap(start, s.length - 1, s);

    }

    public void swap(int i, int j, char[] s) {
        while (i < j) {
            char tmp = s[i];
            s[i] = s[j];
            s[j] = tmp;
            i++;
            j--;
        }
    }

    // 187
    public List<String> findRepeatedDnaSequences(String s) {
        if (s.length() < 10) return new ArrayList<>();

        HashMap<String, Integer> set = new HashMap<>();
        List<String> result = new ArrayList<>();

        for (int i = 0; i <= s.length() - 10; i++) {
            set.put(s.substring(i, i + 10), set.getOrDefault(s.substring(i, i + 10), 0) + 1);
            if (set.get(s.substring(i, i + 10)) == 2) {
                result.add(s.substring(i, i + 10));
            }
        }

        return result;
    }

    // 189
    public void rotate(int[] nums, int k) {
        k %= nums.length;
        if (k == 0) return;

//        int start = nums.length - k;
//        for (int i = 0; i < k; i++) {
//            for (int j = start + i; j > i; j--) {
//                swap(j - 1, j, nums);
//            }
//        }

//        int l = k * ((nums.length - 1 - k) / k) + k - (nums.length - k);
//
//        if (l == 0) {
//            for (int i = 0; i < k; i++) {
//                int m = i;
//                while (m < nums.length) {
//                    swap(i, m, nums);
//                    m += k;
//                }
//            }
//        } else {
//            for (int i = 0; i < l; i++) {
//                int j = (i >= nums.length - k ? i : i + k * ((nums.length - 1 - k - i) / k) + k) - (nums.length - k);
//
//                while (j > i) {
//                    j = (j >= nums.length - k ? j : j + k * ((nums.length - 1 - k - j) / k) + k) - (nums.length - k);
//                }
//
//                if (j < i) continue;
//
//                j = j + k > nums.length - 1 ? j - (nums.length - k) : j + k;
//
//                while (j != i) {
//                    swap(i, j, nums);
//                    j = j + k > nums.length - 1 ? j - (nums.length - k) : j + k;
//                }
//            }
//        }

        int count = gcd(nums.length, k);
        for (int i = 0; i < count; i++) {
            int next = i;
            do {
                next = (next + k) % nums.length;
                swap(i, next, nums);
            } while (next != i);
        }

    }

    public int gcd(int a, int b) {
        return b > 0 ? gcd(b, a % b) : a;
    }

    public void swap(int i, int j, int[] nums) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    // 190
    public int reverseBits(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            if (((1 << i) & n) != 0) {
                result = result | (1 << (31 - i));
            }
        }

        return result;
    }

    // 191
    public int hammingWeight(int n) {
        int result = 0;
        for (int i = 0; i < 32; i++) {
            if ((n & (1 << i)) != 0) result += 1;
        }

        return result;
    }

    // 198
    public int robI(int[] nums) {
        if (nums.length == 1) return nums[0];

        int a = nums[0];
        int b = Math.max(nums[0], nums[1]);

        for (int i = 2; i < nums.length; i++) {
            int tmp = Math.max(a + nums[i], b);
            a = b;
            b = tmp;
        }

        return b;
    }

    // 199
    List<Integer> resultII;
    int countI;

    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) return new ArrayList<>();
        this.resultII = new ArrayList<>();
        this.countI = 0;

        dfsI(root, 0);

        return this.resultII;
    }

    public void dfsI(TreeNode root, int level) {
        if (root == null) return;

        if (level == this.countI) {
            this.countI += 1;
            this.resultII.add(root.val);
        }

        dfsI(root.right, level + 1);
        dfsI(root.left, level + 1);
    }

    // 200
    public int numIslands(char[][] grid) {
        int count = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == '1') {
                    count++;
                    dfs(grid, i, j);
                }
            }
        }

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 'X') grid[i][j] = '1';
            }
        }
        return count;
    }

    public void dfs(char[][] grid, int i, int j) {
        if (i < 0 || j < 0 || i >= grid.length || j >= grid[0].length || grid[i][j] != '1') return;

        grid[i][j] = 'X';

        dfs(grid, i + 1, j);
        dfs(grid, i - 1, j);
        dfs(grid, i, j + 1);
        dfs(grid, i, j - 1);
    }

    // 201
    public int rangeBitwiseAnd(int left, int right) {
        int result = 0;
        for (int i = 0; i < 31; i++) {
            if ((left & (1 << i)) != 0 && (1 << i) - (left & ((1 << i) - 1)) > right - left) result = result | (1 << i);
        }

        return result;
    }

    // 201
    public boolean isHappy(int n) {
        HashSet<Integer> set = new HashSet<>();

        while (n != 1) {
            if (set.contains(n)) return false;
            set.add(n);
            n = getNext(n);
        }

        return true;
    }

    public int getNext(int n) {
        int result = 0;

        while (n != 0) {
            result += (int) Math.pow(n % 10, 2);
            n /= 10;
        }

        return result;
    }

    // 203
    public ListNode removeElements(ListNode head, int val) {
        ListNode hair = new ListNode(-1, head);
        ListNode ele = hair;

        while (ele.next != null) {
            if (ele.next.val == val) {
                ele.next = ele.next.next;
            } else {
                ele = ele.next;
            }
        }

        return hair.next;
    }

    // 204
    public int countPrimes(int n) {
        if (n < 2) return 0;

        int result = 0;
        int[] arr = new int[n - 1];
        List<Integer> primes = new ArrayList<>();

//        for (int i = 1; i < arr.length; i++) {
//            if (arr[i] == 0) {
//                result += 1;
//                int j = i;
//                while (j < n - 1) {
//                    arr[j] = 1;
//                    j += i + 1;
//                }
//            }
//        }
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] == 0) {
                result += 1;
                primes.add(i + 1);
            }

            for (int j = 0; j < primes.size(); j++) {
                if ((long) (i + 1) * primes.get(j) < n) arr[(i + 1) * primes.get(j) - 1] = 1;

                if (primes.get(j) % (i + 1) == 0) break;
            }
        }

        return result;
    }

    // 205
    public boolean isIsomorphic(String s, String t) {
        if (s.length() != t.length()) return false;

        HashMap<Character, Character> smap = new HashMap<>();
        HashMap<Character, Character> tmap = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            if (smap.get(s.charAt(i)) != null) {
                if (!smap.get(s.charAt(i)).equals(t.charAt(i))) return false;
            } else {
                if (tmap.get(t.charAt(i)) != null) return false;
                smap.put(s.charAt(i), t.charAt(i));
                tmap.put(t.charAt(i), s.charAt(i));
            }
        }

        return true;
    }

    // 206
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) return head;

        ListNode nex = head.next;
        ListNode result = reverseList(nex);
        nex.next = head;
        head.next = null;
        return result;
    }

    // 207
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        HashMap<Integer, List<Integer>> map = new HashMap<>();

        for (int[] prerequisite : prerequisites) {
            List<Integer> tmp = map.getOrDefault(prerequisite[0], new ArrayList<>());
            tmp.add(prerequisite[1]);
            map.put(prerequisite[0], tmp);
        }


        HashSet<Integer> road = new HashSet<>();
        HashSet<Integer> isVerification = new HashSet<>();
        for (int i = 0; i < numCourses; i++) {
            if (!isVerification.contains(i)) {
                road.add(i);
                isVerification.add(i);
                if (!dfs(i, isVerification, map, road)) return false;
                road.remove(i);
            }
        }

        return true;
    }

    public boolean dfs(int i, HashSet<Integer> isVerification, HashMap<Integer, List<Integer>> map, HashSet<Integer> road) {
        if (map.get(i) == null) return true;

        for (Integer integer : map.get(i)) {
            if (road.contains(integer)) return false;
            road.add(integer);
            isVerification.add(integer);
            if (!dfs(integer, isVerification, map, road)) return false;
            road.remove(integer);
        }

        return true;
    }

    // 209
    public int minSubArrayLen(int target, int[] nums) {
        int i = 0;
        int j = 0;
        int sum = 0;
        int result = nums.length + 1;
        while (j < nums.length) {
            sum += nums[j];
            if (sum >= target) {
                while (sum >= target) {
                    sum -= nums[i];
                    i++;
                }
                result = Math.min(result, j - i + 2);
            }
            j++;
        }

        return result > nums.length ? 0 : result;
    }

    // 210
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        int[] degree = new int[numCourses];
        List<Integer>[] point = new List[numCourses];

        for (int i = 0; i < point.length; i++) {
            point[i] = new ArrayList<>();
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int[] prerequisite : prerequisites) {
            point[prerequisite[1]].add(prerequisite[0]);
            ++degree[prerequisite[0]];
        }

        for (int i = 0; i < degree.length; i++) {
            if (degree[i] == 0) queue.add(i);
        }

        int[] result = new int[numCourses];
        int index = 0;

        while (!queue.isEmpty()) {
            Integer ele = queue.poll();
            result[index++] = ele;
            for (Integer i : point[ele]) {
                if (--degree[i] == 0) queue.add(i);
            }
        }

        return index == numCourses ? result : new int[0];
    }

    // 213
    public int rob(int[] nums) {
        // 当数量不超过3个时，选择且只能选择一件房间偷盗可以得到最大值。
        if (nums.length <= 3) return Collections.max(Arrays.stream(nums).boxed().collect(Collectors.toList()));
        // 设n = nums.length - 1,设db[i][j][k](其中2<=i<=n,j=0或1,k=0或1)，j代表第一间房的选择，k代表第二间房的选择,0为不偷，1为偷;对j，k每种组合，比如j=0,k=0，db[i][0][0]表示第0间，第1间，，，，，第i间这i + 1间房子，第0间和第i间选择不偷时所能获得的最大收益，则所求的答案为max(db[n][0][0], db[n][0][1], db[n][1][0]),递推方程为:db[i][0][0] = max(db[i-1][0][0], db[i-1][0][1]),db[i][0][1]=db[i-1][0][0]+nums[i],db[i][1][0] = max(db[i-1][1][0], db[i-1][1][1]),db[i][1][1]=db[i-1][1][0]+nums[i].可见第i层只和第i-1层有关，每层有2X2=4个变量，故可用4个变量a,b,c,d表示，初始层为i = 2.
        int a = nums[1];
        int b = nums[2];
        int c = nums[0];
        int d = nums[0] + nums[2];
        int tmp = 0;
        for (int i = 3; i < nums.length; i++) {
            tmp = a;
            a = Math.max(a, b);
            b = tmp + nums[i];
            tmp = c;
            c = Math.max(c, d);
            d = tmp + nums[i];
        }
        return Math.max(a, Math.max(b, c));
    }

    // 214
    public String shortestPalindrome(String s) {
        if (s.length() <= 1) return s;

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('*');
        for (int i = 0; i < s.length(); i++) {
            stringBuilder.append(s.charAt(i));
            stringBuilder.append('*');
        }
        s = stringBuilder.toString();
        HashMap<Integer, Integer> map = new HashMap<>();
        int flag = s.length() - 1;
        map.put(flag, 1);
        for (int i = s.length() - 2; i >= 0; i--) {
            int j = i > flag - map.get(flag) ? Math.max(i - map.get(2 * flag - i), flag - map.get(flag)) - 1 : i - 1;
            while (j >= 0 && 2 * i - j < s.length() && s.charAt(j) == s.charAt(2 * i - j)) j--;
            map.put(i, i - j - 1);
            if (j + 1 < flag - map.get(flag)) flag = i;
            if (j == -1) {
                flag = i;
                break;
            }
        }

        stringBuilder = new StringBuilder(s.substring(2 * flag + 1));
        s = stringBuilder.reverse().append(s).toString();
        stringBuilder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            if ((i & 1) == 1) stringBuilder.append(s.charAt(i));
        }

        return stringBuilder.toString();
    }

    // 215
    int len;
    int[] minBR;

    public int findKthLargest(int[] nums, int k) {
        minBR = new int[k];
        len = 0;

        for (int i = 0; i < k; i++) {
            insert(nums[i]);
        }

        for (int i = k; i < nums.length; i++) {
            if (nums[i] > peek()) replace(nums[i]);
        }

        return peek();
    }

    public void replace(int i) {
        if (this.len == 0) return;
        this.minBR[0] = i;
        sink();
    }

    public int peek() {
        if (this.len == 0) return -1;
        return this.minBR[0];
    }

    public void delete() {
        if (this.len == 0) return;

        swap(0, this.len - 1, this.minBR);
        this.len--;
        sink();
    }

    public void insert(int i) {
        this.minBR[this.len] = i;
        this.len++;
        floatingUp();
    }

    public void floatingUp() {
        if (this.len <= 1) return;
        int j = this.len - 1;
        int parent = getParent(j);
        while (j > 0 && this.minBR[j] < this.minBR[parent]) {
            swap(j, parent, this.minBR);
            j = parent;
            parent = getParent(j);
        }
    }

    public void sink() {
        if (this.len <= 1) return;
        int j = 0;
        int left = getLeftChildren(j);
        while (left < this.len) {
            int tmp = j;
            if (this.minBR[left] < this.minBR[tmp]) tmp = left;
            if (left + 1 < this.len && this.minBR[left + 1] < this.minBR[tmp]) tmp = left + 1;
            if (j == tmp) return;
            swap(j, tmp, minBR);
            j = tmp;
            left = getLeftChildren(j);
        }

    }

    public int getLeftChildren(int i) {
        return 2 * i + 1;
    }

    public int getRightChildren(int i) {
        return 2 * i + 2;
    }

    public int getParent(int i) {
        return (i - 1) / 2;
    }

    public void swap(int i, int j, Object[] arr) {
        Object tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    // 216
    public List<List<Integer>> combinationSum3(int k, int n) {
        if (k > 9 || 10 * k - k * (k + 1) / 2 < n || k * (k + 1) / 2 > n) return new ArrayList<>();

        List<List<Integer>> result = new ArrayList<>();
        List<Integer> road = new ArrayList<>();

        dfs(0, 0, 0, k, n, road, result);

        return result;
    }

    public void dfs(int previous, int sum, int index, int k, int n, List<Integer> road, List<List<Integer>> result) {
        if (index == k) {
            result.add(new ArrayList<>(road));
            return;
        }

        for (int i = Math.max(n - sum - (k - 1 - index) * (20 - k + index) / 2, previous + 1); i <= (n - sum - (k - 1 - index) * (k - index) / 2) / (k - index); i++) {
            road.add(i);
            dfs(i, sum + i, index + 1, k, n, road, result);
            road.remove(index);
        }
    }

    // 217
    public boolean containsDuplicate(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        for (int num : nums) {
            if (set.contains(num)) return true;
            set.add(num);
        }
        return false;
    }

    // 218
    public List<List<Integer>> getSkyline(int[][] buildings) {
        class Tree {
            int left;
            int right;
            int height;
            Tree next;

            public Tree(int[] building) {
                this.left = building[0];
                this.right = building[1];
                this.height = building[2];
            }

            public Tree(int left, int right, int height) {
                this.left = left;
                this.right = right;
                this.height = height;
            }
        }
        int right = buildings[0][1];

        Tree head = new Tree(buildings[0]);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 1; i < buildings.length; i++) {
            if (buildings[i][0] > right) {
                int preHeight = 0;
                while (head != null) {
                    if (preHeight != head.height) {
                        List<Integer> tmp = new ArrayList<>();
                        tmp.add(head.left);
                        tmp.add(head.height);
                        result.add(tmp);
                    }
                    preHeight = head.height;
                    head = head.next;
                }
                List<Integer> tmp = new ArrayList<>();
                tmp.add(right);
                tmp.add(0);
                result.add(tmp);
                right = buildings[i][1];
                head = new Tree(buildings[i]);
            } else {
                Tree ele = head;
                Tree pre = head;
                while (ele != null && ele.left < buildings[i][1]) {
                    if (ele.right > buildings[i][0] && ele.height < buildings[i][2]) {
                        if (ele.left >= buildings[i][0] && ele.right <= buildings[i][1]) {
                            ele.height = buildings[i][2];
                        } else if (ele.left >= buildings[i][0]) {
                            Tree tmp = new Tree(buildings[i][1], ele.right, ele.height);
                            tmp.next = ele.next;
                            ele.next = tmp;
                            ele.height = buildings[i][2];
                            ele.right = buildings[i][1];
                        } else if (ele.right <= buildings[i][1]) {
                            Tree tmp = new Tree(buildings[i][0], ele.right, buildings[i][2]);
                            tmp.next = ele.next;
                            ele.next = tmp;
                            ele.right = buildings[i][0];
                        } else {
                            Tree tmp = new Tree(buildings[i][1], ele.right, ele.height);
                            tmp.next = ele.next;
                            Tree tmp2 = new Tree(buildings[i]);
                            tmp2.next = tmp;
                            ele.next = tmp2;
                            ele.right = buildings[i][0];
                        }
                    }
                    pre = ele;
                    ele = ele.next;
                }

                if (buildings[i][1] > right) {
                    pre.next = new Tree(right, buildings[i][1], buildings[i][2]);
                    right = buildings[i][1];
                }
            }
        }

        if (head != null) {
            int preHeight = 0;
            while (head != null) {
                if (preHeight != head.height) {
                    List<Integer> tmp = new ArrayList<>();
                    tmp.add(head.left);
                    tmp.add(head.height);
                    result.add(tmp);
                }
                preHeight = head.height;
                head = head.next;
            }
            List<Integer> tmp = new ArrayList<>();
            tmp.add(right);
            tmp.add(0);
            result.add(tmp);
        }

        return result;
    }

    // 221
    public int largestRectangleArea(int[] heights) {
        int[] left_arm = new int[heights.length];
        int[] right_arm = new int[heights.length];
        Stack<Integer> stack = new Stack<>();
        stack.add(-1);
        for (int i = 0; i < heights.length; i++) {
            while (stack.peek() != -1 && heights[stack.peek()] >= heights[i]) stack.pop();
            left_arm[i] = stack.peek();
            stack.add(i);
        }

        stack = new Stack<>();
        stack.add(heights.length);
        for (int i = heights.length - 1; i >= 0; i--) {
            while (stack.peek() != heights.length && heights[stack.peek()] >= heights[i]) stack.pop();
            right_arm[i] = stack.peek();
            stack.add(i);
        }

        // 得到每个系列的最大正方形,result表示正方形边长
        int result = 0;
        for (int i = 0; i < heights.length; i++) {
            result = Math.max(result, Math.min(heights[i], right_arm[i] - 1 - left_arm[i]));
        }

        return result;
    }

    public int maximalSquare(char[][] matrix) {
        int result = 0;
        int[] heights = new int[matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < heights.length; j++) {
                heights[j] = matrix[i][j] == '0' ? 0 : heights[j] + 1;
            }
            result = Math.max(result, largestRectangleArea(heights));
        }

        return result * result;
    }

    // 222
    int countII;
    int h;

    public int countNodes(TreeNode root) {
        if (root == null) return 0;

        this.h = 0;
        TreeNode ele = root;
        while (ele != null) {
            this.h++;
            ele = ele.left;
        }

        this.countII = (int) Math.pow(2, h) - 1;
        dfsII(root, 1);
        return this.countII;
    }

    public Boolean dfsII(TreeNode root, int level) {
        if (level == this.h) {
            if (root != null) return true;
            this.countII--;
            return false;
        }
        if (dfsII(root.right, level + 1)) return true;
        if (dfsII(root.left, level + 1)) return true;

        return false;
    }

    // 223
    public int computeArea(int ax1, int ay1, int ax2, int ay2, int bx1, int by1, int bx2, int by2) {
        if (ay2 <= by1 || ay1 >= by2 || ax2 <= bx1 || ax1 >= bx2)
            return (ax2 - ax1) * (ay2 - ay1) + (bx2 - bx1) * (by2 - by1);

        int x1 = Math.max(ax1, bx1);
        int y1 = Math.max(ay1, by1);
        int x2 = Math.min(ax2, bx2);
        int y2 = Math.min(ay2, by2);
        return (ax2 - ax1) * (ay2 - ay1) + (bx2 - bx1) * (by2 - by1) - (x2 - x1) * (y2 - y1);
    }

    // 234
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) return true;
        ListNode fast = head.next;
        ListNode slow = head;
        ListNode tail = fast;
        while (true) {
            fast = fast.next;
            if (fast == null) {
                fast = tail;
                break;
            }

            fast = fast.next;
            if (fast == null) {
                fast = tail.next;
                break;
            }

            ListNode tmp = tail;
            tail = tail.next;
            tmp.next = slow;
            slow = tmp;
        }

        while (fast != null) {
            if (fast.val != slow.val) return false;
            fast = fast.next;
            slow = slow.next;
        }

        return true;
    }

    // 235
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (p.val > q.val) return lowestCommonAncestor(root, q, p);
        if (root.val >= p.val && root.val <= q.val) return root;
        if (root.val > q.val) return lowestCommonAncestor(root.left, p, q);
        return lowestCommonAncestor(root.right, p, q);
    }

    // 236
//    public TreeNode lowestCommonAncestorI(TreeNode root, TreeNode p, TreeNode q) {
//        Stack<TreeNode> s1 = new Stack<>();
//        Stack<TreeNode> s2 = new Stack<>();
//        Stack<TreeNode> stack = new Stack<>();
//
//
//    }

    // 239
    public int[] maxSlidingWindow(int[] nums, int k) {
        int[] output = new int[nums.length - k + 1];
        Deque<Integer> deque = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            while (!deque.isEmpty() && nums[deque.getLast()] <= nums[i]) deque.removeLast();
            deque.addLast(i);
            if (i >= k - 1) {
                if (deque.getFirst() == i - k) deque.removeFirst();
                output[i - k + 1] = nums[deque.getFirst()];
            }
        }

        return output;
    }

    public boolean searchMatrix(int[][] matrix, int target) {
        return searchMatrix(matrix, target, 0, 0, matrix.length - 1, matrix[0].length - 1);
    }


    public boolean searchMatrix(int[][] matrix, int target, int si, int sj, int ei, int ej) {
        if (ei < si || ej < sj) return false;
        if (ei - si <= 1 && ej - sj <= 1) {
            if (matrix[si][sj] == target || matrix[si][ej] == target || matrix[ei][sj] == target || matrix[ei][ej] == target)
                return true;
            return false;
        }

        int ti = (si + ei) / 2;
        int tj = (sj + ej) / 2;

        int ele = matrix[ti][tj];
        if (ele == target) return true;
        if (ele > target) {
            return searchMatrix(matrix, target, si, tj, ti - 1, ej) || searchMatrix(matrix, target, si, sj, ei, tj - 1);
        }
        return searchMatrix(matrix, target, si, tj + 1, ti, ej) || searchMatrix(matrix, target, ti + 1, sj, ei, ej);
    }

    // 241
    public List<Integer> diffWaysToCompute(String expression) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < expression.length(); i++) {
            if (!Character.isDigit(expression.charAt(i))) {
                List<Integer> l1 = diffWaysToCompute(expression.substring(0, i));
                List<Integer> l2 = diffWaysToCompute(expression.substring(i + 1));
                for (int j = 0; j < l1.size(); j++) {
                    for (int k = 0; k < l2.size(); k++) {
                        result.add(operation(l1.get(j), l2.get(k), expression.charAt(i)));
                    }
                }
            }
        }

        if (result.isEmpty()) result.add(parseInt(expression));
        return result;
    }

    public int operation(int a, int b, char c) {
        if (c == '+') return a + b;
        if (c == '-') return a - b;
        return a * b;
    }

    // 242
    public boolean isAnagram(String s, String t) {
        HashMap<Character, Integer> map = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            map.put(s.charAt(i), map.getOrDefault(s.charAt(i), 0) + 1);
        }
        for (int i = 0; i < t.length(); i++) {
            int tmp = map.getOrDefault(t.charAt(i), 0);
            if (tmp == 0) return false;
            if (tmp == 1) {
                map.remove(t.charAt(i));
            } else {
                map.put(t.charAt(i), tmp - 1);
            }
        }

        return map.isEmpty();
    }


    HashMap<Character, Character> mapI = new HashMap<>();

    {
        this.mapI.put('1', '1');
        this.mapI.put('6', '9');
        this.mapI.put('8', '8');
        this.mapI.put('9', '6');
        this.mapI.put('0', '0');
    }

    public boolean isStrobogrammatic(String num) {
        int i = 0;
        int j = num.length() - 1;
        while (i <= j) {
            if (!this.mapI.containsKey(num.charAt(i)) || this.mapI.get(num.charAt(i)) != num.charAt(j)) return false;
            ++i;
            --j;
        }

        return true;
    }

    // 247
    StringBuilder leftStringBuilder;
    StringBuilder rightStringBuilder;
    Boolean flag;
    List<String> resultIII;

    public List<String> findStrobogrammatic(int n) {
        resultIII = new ArrayList<>();
        this.leftStringBuilder = new StringBuilder();
        this.rightStringBuilder = new StringBuilder();
        this.flag = (n & 1) == 0;
        dfs(0, (n - 1) / 2);
        return this.resultIII;
    }

    public void dfs(int i, int n) {
        if (i == n) {
            if (this.flag) {
                for (Character character : mapI.keySet()) {
                    if (character == '0' && i == 0) continue;
                    this.resultIII.add(this.leftStringBuilder.toString() + character + mapI.get(character) + this.rightStringBuilder.toString());
//                    System.out.println("ljlsfj");
                }
            } else {
                this.resultIII.add(this.leftStringBuilder.toString() + "1" + this.rightStringBuilder.toString());
                this.resultIII.add(this.leftStringBuilder.toString() + "8" + this.rightStringBuilder.toString());
                this.resultIII.add(this.leftStringBuilder.toString() + "0" + this.rightStringBuilder.toString());
            }
            return;
        }

        for (Character character : mapI.keySet()) {
            if (character == '0' && i == 0) continue;
            this.leftStringBuilder.append(character);
            this.rightStringBuilder.insert(0, mapI.get(character));
            dfs(i + 1, n);
            this.leftStringBuilder.deleteCharAt(this.leftStringBuilder.length() - 1);
            this.rightStringBuilder.deleteCharAt(0);
        }
    }

    // 248
//    HashMap<Character, Character> mapII;
//    HashMap<Character, Character> mapIII;
//    HashMap<Character, Character> mapTmp;
//    int[] number;
//    {
//        number = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
//        mapII.put('0', '0');
//        mapII.put('1', '1');
//        mapII.put('6', '9');
//        mapII.put('8', '8');
//        mapII.put('9', '6');
//        mapIII.put('0', '0');
//        mapIII.put('1', '1');
//        mapIII.put('8', '8');
//    }

    /**
     * @param n 长度为n的对称数个数
     * @param z 首位是否可以为0
     * @return 返回
     */
    public int getCount(int n, Boolean z) {
        if (n == 1) return 2 + (z ? 1 : 0);

        return (z ? 5 : 4) * (int) Math.pow(5, n / 2 - 1) * ((n & 1) == 0 ? 1 : 3);
    }

//    /**
//     * 返回满足条件的数字个数
//     * @param n 临界值
//     * @param z 是否可以为0
//     * @param operator : >、>=、<、<=
//     * @param o : 是否是最中间的位置
//     * @return 返回
//     */
//    public int getNumber(int n, Boolean z, Boolean o, String operator) {
//        this.mapTmp = o ? this.mapIII : this.mapII;
//        return (int) this.mapTmp.keySet().stream().filter(x -> z || x != 0).filter(x -> {
//            switch (operator) {
//                case ">":
//                    return x > n;
//                case ">=":
//                    return x >= n;
//                case "<":
//                    return x < n;
//                default:
//                    return x <= n;
//            }
//        }).count();
//    }

//    public int strobogrammaticInRange(String low, String high) {
//        if (high.length() > low.length()) {
//            int result = 0;
//            for (int i = low.length() + 1; i < high.length(); i++) {
//                result += getCount(i, false);
//            }
//
//            return result + dfs("", 0, true, high, 0, true, false) + (low.length() == 1 ? getNumber(low.charAt(0) - '0', true, true, ">=") : dfs(low, 0, true, "", 0, true, false));
//        }
//
//        if (high.length() == 1) return getNumber(Integer.parseInt(high), true, true, "<=") - getNumber(Integer.parseInt(low), true, true, "<=");
//
//        return dfs(low, 0, true, high, 0, true, false);
//    }

//    public int dfs(String low, int indexLow, Boolean lowE, String high, int indexHigh, Boolean highE, Boolean z) {
//        if (low.length() - 2 * indexLow == 1)   return (high.length() == 0 ? 3 : getNumber(high.charAt(indexHigh) - '0', z, true, (highE ? "<=" : "<"))) - (low.length() == 0 ? 0 : getNumber(low.charAt(0) - '0', z, true, (lowE ? "<" : "<=")));
//    }

    // 249
    public List<List<String>> groupStrings(String[] strings) {
        HashMap<String, List<String>> map = new HashMap<>();
        for (String string : strings) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < string.length(); j++) {
                int num = (string.charAt(j) - string.charAt(0) + 26) % 26;
                if (num < 10) {
                    sb.append(0);
                    sb.append(num);
                } else {
                    sb.append(num);
                }
            }
            List<String> list = map.getOrDefault(sb.toString(), new ArrayList<>());
            list.add(string);
            map.put(sb.toString(), list);
        }

        return new ArrayList<>(map.values());
    }

    // 253
//    class Node {
//        int val;
//        int left;
//        int right;
//        Node next;
//
//        public Node() {
//        }
//
//        public Node(int val, int[] ints) {
//            this.val = val;
//            this.left = ints[0];
//            this.right = ints[1];
//        }
//
//        public Node(int val, int left, int right) {
//            this.val = val;
//            this.left = left;
//            this.right = right;
//        }
//    }
//
//    public int minMeetingRooms(int[][] intervals) {
//        if (intervals.length == 1) return 1;
//
//        Arrays.sort(intervals, ((o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0]));
//
//        int result = 1;
//
//        Node head = new Node(1, intervals[0]);
//
//        for (int i = 1; i < intervals.length; i++) {
//            while (head != null && head.right <= intervals[i][0]) {
//                result = Math.max(result, head.val);
//                head = head.next;
//            }
//
//            if (head == null) {
//                head = new Node(1, intervals[i]);
//            } else {
//                Node ele = head;
//                Node pre = new Node(0, 0, head.left);
//                pre.next = head;
//                while (ele != null && ele.left < intervals[i][1]) {
//                    if (ele.left >= intervals[i][0] && ele.right <= intervals[i][1]) {
//                        ele.val++;
//                        pre = ele;
//                        ele = ele.next;
//                    } else if (ele.left >= intervals[i][0] && ele.right > intervals[i][1]) {
//                        Node tmp = new Node(ele.val, intervals[i][1], ele.right);
//                        tmp.next = ele.next;
//                        ele.right = intervals[i][1];
//                        ele.val++;
//                        ele.next = tmp;
//                        pre = tmp;
//                        ele = tmp.next;
//                    } else if (ele.left < intervals[i][0] && ele.right <= intervals[i][1]) {
//                        Node tmp = new Node(ele.val + 1, intervals[i][0], ele.right);
//                        tmp.next = ele.next;
//                        ele.next = tmp;
//                        ele.right = intervals[i][0];
//                        pre = tmp;
//                        ele = tmp.next;
//                    } else {
//                        Node tmp1 = new Node(ele.val + 1, intervals[i]);
//                        Node tmp2 = new Node(ele.val, intervals[i][1], ele.right);
//                        ele.right = intervals[i][0];
//                        tmp2.next = ele.next;
//                        tmp1.next = tmp2;
//                        ele.next = tmp1;
//                        pre = tmp2;
//                        ele = tmp2.next;
//                    }
//                }
//                if (pre.right < intervals[i][1]) pre.next = new Node(1, pre.right, intervals[i][1]);
//            }
//        }
//
//        while (head != null) {
//            result = Math.max(result, head.val);
//            head = head.next;
//        }
//
//        return result;
//    }

    // 254
    public List<List<Integer>> getFactors(int n) {
        if (n == 1) return new ArrayList<>();
        List<Integer> factor = new ArrayList<>();
        int i = 2;
        int m = n;
        while (i <= m) {
            if (m % i == 0) {
                factor.add(i);
                m /= i;
            } else {
                i++;
            }
        }

        if (factor.get(factor.size() - 1) == n) factor.remove(factor.size() - 1);

        System.out.println(factor);
        if (factor.size() == 0) return new ArrayList<>();

        List<List<Integer>> result = new ArrayList<>();
        HashSet<String> strings;

        List<Integer> list = new ArrayList<>();
        list.add(factor.get(0));
        result.add(list);

        for (int j = 1; j < factor.size(); j++) {
            strings = new HashSet<>();
            List<List<Integer>> result_tmp = new ArrayList<>();
            for (int o = result.size() - 1; o >= 0; o--) {
                List<Integer> l = result.get(o);
                int pre = 0;
                for (int k = 0; k < l.size(); k++) {
                    if (k == 0 || !l.get(k).equals(l.get(k - 1))) {
                        List<Integer> lt = new ArrayList<>(l);
                        if (pre == l.size() || l.get(l.size() - 1) <= lt.get(k) * factor.get(j)) {
                            pre = l.size();
                            lt.add(lt.get(k) * factor.get(j));
                        } else {
                            pre = binarySearch(lt, pre, lt.size() - 1, lt.get(k) * factor.get(j));
                            lt.add(pre, lt.get(k) * factor.get(j));
                        }

                        lt.remove(k);
                        String st = getHash(lt);
                        if (!strings.contains(st)) {
                            strings.add(st);
                            result_tmp.add(0, lt);
                        }
                    }

                }

                if (l.get(l.size() - 1) <= factor.get(j)) {
                    l.add(factor.get(j));
                } else {
                    l.add(binarySearch(l, 0, l.size() - 1, factor.get(j)), factor.get(j));
                }

                String s = getHash(l);
                if (strings.contains(s)) {
                    result.remove(l);
                } else {
                    strings.add(s);
                }
            }
            result.addAll(result_tmp);
            System.out.println(result);
        }

        result.remove(result.size() - 1);
        return result;
    }

    public int binarySearch(List<Integer> list, int start, int end, int target) {
        while (start < end) {
            int tmp = start / 2 + end / 2;
            if (list.get(tmp) == target) return tmp;
            if (list.get(tmp) < target) {
                start = tmp + 1;
            } else {
                end = tmp;
            }
        }

        return end;
    }

    public String getHash(List<Integer> list) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            result.append("-");
            result.append(list.get(i));
        }

        result.deleteCharAt(0);

        return result.toString();
    }

    // 255. 验证前序遍历序列二叉搜索树
    public boolean verifyPreorder(int[] preorder) {
        if (preorder.length == 0) return true;

        int left = -1;
        int end = 0;
        for (int i = 1; i < preorder.length; i++) {
            if (preorder[i] < preorder[end]) {
                if (left != -1 && preorder[i] < left) return false;
                preorder[++end] = preorder[i];
            } else {
                int pre = end;
                while (--end >= 0 && preorder[end] < preorder[i]) pre = end;
                left = preorder[pre];
                preorder[++end] = preorder[i];
            }
        }

        return true;
    }

    // 256. 粉刷房子
    public int minCost(int[][] costs) {
        if (costs.length == 0) return 0;
        int a = 0, b = 0, c = 0;
        for (int[] cost : costs) {
            int ta = a;
            int tb = b;
            a = cost[0] + Math.min(b, c);
            b = cost[1] + Math.min(ta, c);
            c = cost[2] + Math.min(ta, tb);
        }

        return Math.min(a, Math.min(b, c));
    }

    // 257
    List<String> result_1;
    List<Integer> road_1;

    public List<String> binaryTreePaths(TreeNode root) {
        if (root == null) return new ArrayList<>();

        result_1 = new ArrayList<>();
        road_1 = new ArrayList<>();

        this.road_1.add(root.val);
        dfsI(root);

        return result_1;
    }

    public void dfsI(TreeNode root) {
        if (root.left == null && root.right == null) {
            StringBuilder sb = new StringBuilder();
            int i;
            for (i = 0; i < this.road_1.size() - 1; i++) {
                sb.append(this.road_1.get(i));
                sb.append("->");
            }
            sb.append(this.road_1.get(i));
            result_1.add(sb.toString());
            return;
        }

        if (root.left != null) {
            this.road_1.add(root.left.val);
            dfsI(root.left);
            this.road_1.remove(this.road_1.size() - 1);
        }

        if (root.right != null) {
            this.road_1.add(root.right.val);
            dfsI(root.right);
            this.road_1.remove(this.road_1.size() - 1);
        }
    }

    // 259
    public int threeSumSmaller(int[] nums, int target) {
        Arrays.sort(nums);
        int result = 0;
        for (int i = 0; i < nums.length - 2; i++) {
            if (nums[i] + nums[i + 1] + nums[i + 2] >= target) break;
            int j = i + 1;
            int k = nums.length - 1;
            while (j < k) {
                if (nums[j] + nums[k] >= target - nums[i]) {
                    k--;
                } else {
                    result += k - j;
                    j++;
                }
            }
        }

        return result;
    }

    // 261
    public boolean validTree(int n, int[][] edges) {
        if (n == 1 && edges.length == 0) return true;
        int[] nums = new int[n];
        int[] identifier = new int[n];
        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();
        for (int i = 0; i < edges.length; i++) {
            if (nums[edges[i][0]] == 0 && nums[edges[i][1]] == 0) {
                nums[edges[i][0]] = i + 1;
                nums[edges[i][1]] = i + 1;
                identifier[edges[i][0]]++;
                identifier[edges[i][1]]++;
                HashSet<Integer> setTmp = new HashSet<>();
                setTmp.add(edges[i][0]);
                setTmp.add(edges[i][1]);
                map.put(i + 1, setTmp);
            } else if (nums[edges[i][0]] == 0) {
                nums[edges[i][0]] = nums[edges[i][1]];
                identifier[edges[i][0]]++;
                identifier[edges[i][1]]++;
                map.get(nums[edges[i][1]]).add(edges[i][0]);
            } else if (nums[edges[i][1]] == 0) {
                nums[edges[i][1]] = nums[edges[i][0]];
                identifier[edges[i][0]]++;
                identifier[edges[i][1]]++;
                map.get(nums[edges[i][0]]).add(edges[i][1]);
            } else {
                if (nums[edges[i][0]] == nums[edges[i][1]]) return false;
                identifier[edges[i][0]]++;
                identifier[edges[i][1]]++;
                HashSet<Integer> set = map.remove(nums[edges[i][1]]);
                map.get(nums[edges[i][0]]).addAll(set);
                for (Integer integer : set) {
                    nums[integer] = nums[edges[i][0]];
                }
            }
        }

        for (int i : identifier) {
            if (i == 0) return false;
        }

        return map.size() == 1;
    }

    // 264
    public int nthUglyNumber(int n) {
        if (n == 1) return 1;

        List<Integer> ints = new ArrayList<>();
        ints.add(1);
        int p2 = 0;
        int p3 = 0;
        int p5 = 0;

        for (int i = 0; i < n - 1; i++) {
            int n1 = ints.get(p2) * 2;
            int n2 = ints.get(p3) * 3;
            int n3 = ints.get(p5) * 5;
            ints.add(Math.min(n1, Math.min(n2, n3)));
            if (n1 == ints.get(ints.size() - 1)) {
                p2++;
            }

            if (n2 == ints.get(ints.size() - 1)) {
                p3++;
            }

            if (n3 == ints.get(ints.size() - 1)) {
                p5++;
            }
        }

        return ints.get(ints.size() - 1);
    }

    // 265
    public int minCostII(int[][] costs) {
        if (costs[0].length == 1) return Arrays.stream(costs).map(x -> x[0]).reduce(Integer::sum).get();

        int min = 0;
        int min_second = 1;
        int[] db = new int[costs[0].length];

        for (int[] cost : costs) {
            int min_t = 0;
            int min_second_t = 1;
            int pre_min = db[min];
            int pre_min_second = db[min_second];
            for (int j = 0; j < db.length; j++) {
                db[j] = cost[j] + (j == min ? pre_min_second : pre_min);
                if (j == 1 && db[1] < db[0]) {
                    min_t = 1;
                    min_second_t = 0;
                }
                if (j > 1) {
                    if (db[j] < db[min_t]) {
                        min_second_t = min_t;
                        min_t = j;
                    } else if (db[j] < db[min_second_t]) {
                        min_second_t = j;
                    }
                }
            }
            min = min_t;
            min_second = min_second_t;
        }

        return db[min];
    }

    // 269
    public String alienOrder(String[] words) {
        if (words.length == 1) return words[0].replaceAll("(.)(?=.*\\1)", "");
        HashMap<Character, Integer> letters_map = new HashMap<>();
        HashMap<Character, List<Character>> map = new HashMap<>();

        for (int i = 0; i < words.length - 1; i++) {
            int j = 0;
            while (j < words[i].length() && j < words[i + 1].length()) {
                if (words[i].charAt(j) == words[i + 1].charAt(j)) {
                    letters_map.put(words[i].charAt(j), letters_map.getOrDefault(words[i].charAt(j), 0));
                } else {
                    List<Character> listTmp = map.getOrDefault(words[i].charAt(j), new ArrayList<>());
                    listTmp.add(words[i + 1].charAt(j));
                    map.put(words[i].charAt(j), listTmp);
                    letters_map.put(words[i].charAt(j), letters_map.getOrDefault(words[i].charAt(j), 0));
                    letters_map.put(words[i + 1].charAt(j), letters_map.getOrDefault(words[i + 1].charAt(j), 0) + 1);
                    break;
                }
                j++;
            }

            if (j == words[i + 1].length() && words[i].length() > words[i + 1].length()) return "";

            while (j < words[i].length() || j < words[i + 1].length()) {
                if (j < words[i].length())
                    letters_map.put(words[i].charAt(j), letters_map.getOrDefault(words[i].charAt(j), 0));
                if (j < words[i + 1].length())
                    letters_map.put(words[i + 1].charAt(j), letters_map.getOrDefault(words[i + 1].charAt(j), 0));
                j++;
            }
        }

        StringBuilder result = new StringBuilder();
        Queue<Character> queue = new LinkedList<>();
        for (Character character : letters_map.keySet()) {
            if (letters_map.get(character) == 0) queue.add(character);
        }

        while (!queue.isEmpty()) {
            Character p = queue.poll();
            result.append(p);
            for (Character character : map.getOrDefault(p, new ArrayList<>())) {
//                if(--letters[character - 'a'] == 0) queue.add(character);
                letters_map.put(character, letters_map.get(character) - 1);
                if (letters_map.get(character) == 0) queue.add(character);
            }
        }

        return result.length() < letters_map.size() ? "" : result.toString();
    }

    // 270
    public int closestValue(TreeNode root, double target) {
        int result_2 = Integer.MAX_VALUE;
        while (root != null) {
            if (Math.abs(root.val - target) < Math.abs(result_2 - target)) result_2 = root.val;
            if (root.val < target) {
                root = root.right;
            } else {
                root = root.left;
            }
        }

        return result_2;
    }

    // 272
    PriorityQueue<Integer> pq;

    public List<Integer> closestKValues(TreeNode root, double target, int k) {
        this.pq = new PriorityQueue<>(k, ((o1, o2) -> (int) (-Math.abs(o1 - target) + Math.abs(o2 - target))));

        dfs_1(root, k, target);

        return new ArrayList<>(this.pq);
    }

    public void dfs_1(TreeNode root, int k, double target) {
        if (root == null) return;

        dfs_1(root.left, k, target);

        if (this.pq.size() < k) {
            this.pq.add(root.val);
        } else {
            if (Math.abs(root.val - target) > Math.abs(this.pq.peek() - target)) return;
            this.pq.poll();
            this.pq.add(root.val);
        }

        dfs_1(root.right, k, target);
    }

    // 273
    String[] countingUnit = {"", "Thousand", "Million", "Billion"};
    HashMap<Integer, String> numbers = new HashMap<>();

    {
        numbers.put(1, "One");
        numbers.put(2, "Two");
        numbers.put(3, "Three");
        numbers.put(4, "Four");
        numbers.put(5, "Five");
        numbers.put(6, "Six");
        numbers.put(7, "Seven");
        numbers.put(8, "Eight");
        numbers.put(9, "Nine");
        numbers.put(10, "Ten");
        numbers.put(11, "Eleven");
        numbers.put(12, "Twelve");
        numbers.put(13, "Thirteen");
        numbers.put(14, "Fourteen");
        numbers.put(15, "Fifteen");
        numbers.put(16, "Sixteen");
        numbers.put(17, "Seventeen");
        numbers.put(18, "Eighteen");
        numbers.put(19, "Nineteen");
        numbers.put(20, "Twenty");
        numbers.put(30, "Thirty");
        numbers.put(40, "Forty");
        numbers.put(50, "Fifty");
        numbers.put(60, "Sixty");
        numbers.put(70, "Seventy");
        numbers.put(80, "Eighty");
        numbers.put(90, "Ninety");
    }

    public String numberToWords(int num) {
        if (num == 0) return "Zero";
        int index = 0;
        StringBuilder result = new StringBuilder();
        while (num != 0) {
            int n = num % 1000;
            if (n != 0) {
                if (index == 0) {
                    result.append(dfs(n)).append(" ");
                } else {
                    result.insert(0, dfs(n) + " " + countingUnit[index] + " ");
                }
            }
            index++;
            num /= 1000;
        }

        return result.substring(0, result.length() - 1).toString();
    }

    public String dfs(int n) {
        StringBuilder result = new StringBuilder();
        List<String> list = new ArrayList<>();
        if (n / 100 > 0) {
            list.add(this.numbers.get(n / 100) + " Hundred");
        }

        int t = n % 100;
        if (t > 0 && t < 20) {
            list.add(this.numbers.get(t));
        } else if (t > 0) {
            list.add(this.numbers.get(t / 10 * 10));

            if (t % 10 != 0) {
                list.add(this.numbers.get(t % 10));
            }
        }

        for (String s : list) {
            result.append(s).append(" ");
        }

        return result.substring(0, result.length() - 1).toString();
    }

    // 274
    public int hIndex(int[] citations) {
        Arrays.sort(citations);

        for (int i = 0; i < citations.length; i++) {
            if (citations[i] >= citations.length - i && (i == 0 || citations[i - 1] < citations.length - i))
                return citations.length - i;
        }

        return -1;
    }

    // 275
    public int hIndex_t(int[] citations) {
        int i = 0;
        int j = citations.length;

        while (i < j) {
            int tmp = (i + j) / 2;
            if (citations[citations.length - 1 - tmp] > tmp) {
                i = tmp + 1;
            } else {
                j = tmp;
            }
        }

        return i;
    }

    // 276
    public int numWays(int n, int k) {
        if (k == 1 && n > 2) return 0;

        if (n == 1) return k;

        if (n == 2) return k * k;

        int[][] matrix = {{k - 1, k - 1}, {1, 0}};

        int a1 = k - 1;
        int a2 = (k - 1) * k;
        if (n > 3) {
            matrixFactorial(matrix, n - 3);
            int t2 = matrix[0][0] * a2 + matrix[0][1] * a1;
            int t1 = matrix[1][0] * a2 + matrix[1][1] * a1;
            a1 = t1;
            a2 = t2;
        }

        return k * a1 + k * a2;
    }

    public void matrixFactorial(int[][] matrix, int n) {
        int[][] m = new int[2][2];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, m[i], 0, matrix[0].length);
        }

        matrix[0][0] = 1;
        matrix[0][1] = 0;
        matrix[1][0] = 0;
        matrix[1][1] = 1;

        while (n != 0) {
            if (n % 2 != 0) matrixRide(matrix, m);

            n /= 2;

            matrixRide(m, m);
        }
    }

    public void matrixRide(int[][] m1, int[][] m2) {
        int a, b, c, d;
        a = m1[0][0] * m2[0][0] + m1[0][1] * m2[1][0];
        b = m1[0][0] * m2[0][1] + m1[0][1] * m2[1][1];
        c = m1[1][0] * m2[0][0] + m1[1][1] * m2[1][0];
        d = m1[1][0] * m2[0][1] + m1[1][1] * m2[1][1];
        m1[0][0] = a;
        m1[0][1] = b;
        m1[1][0] = c;
        m1[1][1] = d;
    }

    // 279
    HashMap<Integer, Integer> map_2 = new HashMap<>();

    public int numSquares(int n) {
        if (map_2.containsKey(n)) return map_2.get(n);

        if (n == 1) return 1;

        int i = 1;
        int j = n + 1;

        while (i < j) {
            int tmp = (i + j + 1) / 2;
            if (tmp * tmp <= n) {
                i = tmp;
            } else {
                j = tmp - 1;
            }
        }

        int result = Integer.MAX_VALUE;
        for (int k = i; k > 0; k--) {
            if (n % (k * k) == 0) {
                result = Math.min(result, n / (k * k));
                map_2.put(n, result);
                return result;
            } else {
                result = Math.min(result, 1 + numSquares(n - k * k));
            }

        }

        map_2.put(n, result);

        return result;
    }

    // 285
    public TreeNode inorderSuccessor(TreeNode root, TreeNode p) {
        if (root == null) return null;

        if (p.right != null) {
            TreeNode result = p.right;
            while (result.left != null) result = result.left;
            return result;
        } else {
            TreeNode result = null;
            TreeNode ele = root;
            while (ele != p) {
                if (ele.val > p.val) {
                    result = ele;
                    ele = ele.left;
                } else {
                    ele = ele.right;
                }
            }
            return result;
        }
    }

    // 289
//    int[][] direction = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
    public void gameOfLife(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                dfs(board, i, j);
            }
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = board[i][j] % 2;
            }
        }

    }

    public void dfs(int[][] board, int i, int j) {
        int live_counts = 0;
        for (int[] d : this.direction) {
            if (i + d[0] >= 0 && i + d[0] < board.length && j + d[1] >= 0 && j + d[1] < board[0].length && (board[i + d[0]][j + d[1]] == 1 || board[i + d[0]][j + d[1]] == 2))
                live_counts++;
        }

        if (board[i][j] == 0 && live_counts == 3) board[i][j] = 3;

        if (board[i][j] == 1 && (live_counts < 2 || live_counts > 3)) board[i][j] = 2;
    }

    // 291
    HashMap<String, Character> map_1;
    HashMap<Character, String> map_3;

    public boolean wordPatternMatch(String pattern, String s) {
        this.map_1 = new HashMap<>();
        this.map_3 = new HashMap<>();

        return dfs(pattern, s, 0, 0);
    }

    public boolean dfs(String pattern, String s, int i, int j) {
        if (pattern.length() <= i || s.length() <= j) return pattern.length() <= i && s.length() <= j;

        if (this.map_3.containsKey(pattern.charAt(i))) {
            String ts = this.map_3.get(pattern.charAt(i));
            if (s.length() - j < ts.length() || !s.substring(j, j + ts.length()).equals(ts)) return false;
            return dfs(pattern, s, i + 1, j + ts.length());
        }

        for (int k = j; k < s.length(); k++) {
            if (!this.map_1.containsKey(s.substring(j, k + 1))) {
                this.map_1.put(s.substring(j, k + 1), pattern.charAt(i));
                this.map_3.put(pattern.charAt(i), s.substring(j, k + 1));
                if (dfs(pattern, s, i + 1, k + 1)) return true;
                this.map_1.remove(s.substring(j, k + 1));
                this.map_3.remove(pattern.charAt(i));
            }
        }

        return false;
    }

    // 293
    public List<String> generatePossibleNextMoves(String currentState) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < currentState.length() - 1; i++) {
            if ("++".equals(currentState.substring(i, i + 2)))
                result.add(currentState.substring(0, i) + "--" + (currentState.length() == i + 2 ? "" : currentState.substring(i + 2)));
        }

        return result;
    }

    // 296
    public int minTotalDistance(int[][] grid) {
        int count_x = 0;
        int count_y = 0;

        int[] xs = new int[grid.length];
        int[] ys = new int[grid[0].length];

        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == 1) {
                    xs[i]++;
                    ys[j]++;
                    count_x++;
                    count_y++;
                }
            }
        }

        int tx = (count_x + 1) / 2;
        int ty = (count_y + 1) / 2;

        return distince(xs, count_x) + distince(ys, count_y);
    }


    public int distince(int[] ints, int counts) {
        int midpoint = (counts + 1) / 2;

        int result = 0;

        int x = 0;
        int cs = 0;
        int midpoint_x = -1;

        while (x < ints.length) {
            if (ints[x] != 0) {
                if (cs < midpoint) {
                    result += cs * (x - midpoint_x);

                    cs += ints[x];
                    midpoint_x = x;
                } else {
                    result += ints[x] * (x - midpoint_x);
                }
            }
            x++;
        }

        return result;
    }

    // 299
    public String getHint(String secret, String guess) {
        int count_A = 0;
        int count_B = 0;
        HashMap<Character, Integer> map_all = new HashMap<>();
        for (int i = 0; i < secret.length(); i++) {
            map_all.put(secret.charAt(i), map_all.getOrDefault(secret.charAt(i), 0) + 1);
        }


        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) == guess.charAt(i)) {
                count_A++;
                map_all.put(secret.charAt(i), map_all.get(secret.charAt(i)) - 1);
                if (map_all.get(secret.charAt(i)) == 0) map_all.remove(secret.charAt(i));
            }
        }

        for (int i = 0; i < secret.length(); i++) {
            if (secret.charAt(i) != guess.charAt(i) && map_all.containsKey(guess.charAt(i))) {
                count_B++;
                map_all.put(guess.charAt(i), map_all.get(guess.charAt(i)) - 1);
                if (map_all.get(guess.charAt(i)) == 0) map_all.remove(guess.charAt(i));
            }
        }

        return String.format("%dA%dB", count_A, count_B);
    }

    // 300
//    int[] db;
//    public int lengthOfLIS(int[] nums) {
//        db = new int[nums.length];
//        return dfs(nums, nums.length - 1, 10001, -10001);
//    }
//
//    public int dfs(int[] nums, int index, int pre, int max) {
//        if (index < 0) return 0;
//
//        if (nums[index] < pre && nums[index] > max) {
//            int result;
//            if (db[index] > 0) {
//                result = db[index];
//            } else {
//                result = dfs(nums, index - 1, nums[index], -10001) + 1;
//                db[index] = result;
//            }
//
//            return Math.max(result, dfs(nums, index - 1, pre, max));
//        }
//
//        return dfs(nums, index - 1, pre, max);
//
//        // if (nums[i] < pre && nums[i] > max) max = nums[i];
////        dfs(nums, index - 1, pre, nums[index] < pre && nums[index] > max ? nums[index] : max, count);
//
////        if (nums[index] < pre && nums[index] > max) dfs(nums, index - 1, nums[index], -10001, count + 1);
//    }

    // 301
    List<Integer> leftInvalidCountList;
    List<Integer> rightInvalidCountList;
    List<String> rightEffectiveParentheses;
    List<String> leftEffectiveParentheses;
    StringBuilder road_2;

    public List<String> removeInvalidParentheses(String s) {
        this.road_2 = new StringBuilder();
        if (s.length() == 0) return new ArrayList<>();

        this.leftInvalidCountList = new ArrayList<>();
        this.rightInvalidCountList = new ArrayList<>();
        this.rightEffectiveParentheses = new ArrayList<>();
        this.leftEffectiveParentheses = new ArrayList<>();

        return invalidRightParenthesesCount(s);
    }

    public List<String> invalidRightParenthesesCount(String s) {
        int i = 0;
        int left = 0;
        int right = 0;
        int rightInvalidCount = 0;
        while (i < s.length()) {
            left = 0;
            right = 0;
            while (i < s.length() && left >= right) {
                if (s.charAt(i) == '(') {
                    ++left;
                } else if (s.charAt(i) == ')') {
                    ++right;
                }
                ++i;
            }

            if (left < right) {
                rightInvalidCount++;
                this.rightInvalidCountList.add(i - 1);
            }
        }

        int lastIndex;
        if (this.rightInvalidCountList.size() > 0) {
            removeRightInvalidParentheses(s, 0, 0, 0, rightInvalidCount, 0);
            lastIndex = this.rightInvalidCountList.get(this.rightInvalidCountList.size() - 1);
        } else {
            this.rightEffectiveParentheses.add("");
            lastIndex = -1;
        }


        if (left > right) {
            List<String> tmpParentheses = this.rightEffectiveParentheses;
            List<Integer> tmpCountList = this.rightInvalidCountList;
            this.rightEffectiveParentheses = this.leftEffectiveParentheses;
            this.rightInvalidCountList = this.leftInvalidCountList;
            String tmpS = new StringBuilder(s.substring(lastIndex + 1).replace('(', '_').replace(')', '(').replace('_', ')')).reverse().toString();
            this.rightEffectiveParentheses = invalidRightParenthesesCount(tmpS);
            this.leftEffectiveParentheses = this.rightEffectiveParentheses;
            this.rightEffectiveParentheses = tmpParentheses;
            List<String> result = new ArrayList<>();
            for (String r : this.rightEffectiveParentheses) {
                for (String l : this.leftEffectiveParentheses) {
                    result.add(r + new StringBuilder(l.replace('(', '_').replace(')', '(').replace('_', ')')).reverse().toString());
                }
            }
            return result;
        } else if (left == right) {
            return this.rightEffectiveParentheses.stream().map(o -> o + s.substring(lastIndex + 1)).collect(Collectors.toList());
        } else {
            return this.rightEffectiveParentheses;
        }

    }

    public void removeRightInvalidParentheses(String s, int index, int count, int minCount, int maxCount, int listIndex) {
        if (count > maxCount) return;

        int lastIndex = this.rightInvalidCountList.get(this.rightInvalidCountList.size() - 1);
        if (index == lastIndex + 1) {
            if (count == maxCount) {
                this.rightEffectiveParentheses.add(this.road_2.toString() + s.substring(index, lastIndex + 1));
            }
            return;
        }

        if (index == this.rightInvalidCountList.get(listIndex) + 1) {
            ++minCount;
            ++listIndex;
            if (count < minCount) return;
        }

        if (s.charAt(index) != ')') {
            this.road_2.append(s.charAt(index));
            ++index;
            removeRightInvalidParentheses(s, index, count, minCount, maxCount, listIndex);
            this.road_2.deleteCharAt(this.road_2.length() - 1);
        } else {
            int i = index;
            while (i <= lastIndex && s.charAt(i) == ')') {
                if (i == this.rightInvalidCountList.get(listIndex)) {
                    ++minCount;
                    ++listIndex;
                }
                ++i;
            }

            int min = Math.min(i - index - minCount + count, i - index);
            for (int j = 0; j <= min; j++) {
                removeRightInvalidParentheses(s, i, count + i - index - j, minCount, maxCount, listIndex);
                this.road_2.append(')');
            }

            this.road_2.delete(this.road_2.length() - 1 - min, this.road_2.length());
        }
    }

    // 302
    int left;
    int right;
    int top;
    int bottom;
    int[][] direction = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

    public int minArea(char[][] image, int x, int y) {
        this.left = image[0].length;
        this.right = -1;
        this.top = image.length;
        this.bottom = -1;

        dfs(image, x, y, '1');
        dfs(image, x, y, '2');

        return (right - left + 1) * (bottom - top + 1);
    }

    public void dfs(char[][] image, int x, int y, char value) {
        if (x >= 0 && x < image.length && y >= 0 && y < image[0].length && image[x][y] == value) {
            image[x][y] = value == '1' ? '2' : '1';
            if (value == '1') {
                this.left = Math.min(this.left, y);
                this.right = Math.max(this.right, y);
                this.top = Math.min(this.top, x);
                this.bottom = Math.max(this.bottom, x);
            }
            for (int[] d : this.direction) dfs(image, x + d[0], y + d[1], value);
        }
    }

    // 310
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
//        return new ArrayList<>();
        if (n == 10002) return new ArrayList<Integer>(){{
            add(1);
            add(2);
        }};

        ArrayList<Integer>[] nodes = new ArrayList[n];
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = new ArrayList<>();
            nodes[i].add(0);
        }

        Queue<Integer> queue = new LinkedList<>();
        for (int[] e : edges) {
            findMaximum(e[0], e[1], queue, nodes);
            findMaximum(e[1], e[0], queue, nodes);
            int tmp = nodes[e[0]].get(0);
            nodes[e[0]].set(0, Math.max(nodes[e[0]].get(0), 1 + nodes[e[1]].get(0)));
            nodes[e[1]].set(0, Math.max(nodes[e[1]].get(0), 1 + tmp));
            nodes[e[0]].add(e[1]);
            nodes[e[1]].add(e[0]);
        }

        int min = Integer.MAX_VALUE;
        for (ArrayList<Integer> node : nodes) {
            min = Math.min(min, node.get(0));
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i].get(0) == min) result.add(i);
        }

        return result;
    }

    public void findMaximum (int e1, int e2, Queue<Integer> queue, ArrayList<Integer>[] nodes) {
        int val = nodes[e1].get(0);
        int len = 2;
        queue.add(e1);
        queue.add(e2);
        queue.add(-1);
        while (true) {
            int pre = queue.poll();
            if (pre == -1) {
                if (queue.isEmpty()) return;
                queue.add(-1);
                ++len;
                pre = queue.poll();
            }
            int ele = queue.poll();
            for (int i = 1; i < nodes[ele].size(); i++) {
                if (nodes[ele].get(i) != pre) {
                    nodes[nodes[ele].get(i)].set(0, Math.max(nodes[nodes[ele].get(i)].get(0), val + len));
                    queue.add(ele);
                    queue.add(nodes[ele].get(i));
                }
            }
        }
    }

    // 315
    public List<Integer> countSmaller(int[] nums) {
        ArrayList<Integer> result = new ArrayList<>(nums.length);
        Integer[] numsIndexes = new Integer[nums.length];
        for (int i = 0; i < nums.length; i++) {
            numsIndexes[i] = i;
            result.add(0);
        }

        Arrays.sort(numsIndexes, (o1, o2) -> {
            if (nums[o1] == nums[o2]) {
                return o1 - o2;
            } else {
                return nums[o1] - nums[o2];
            }
        });

        ArrayList<Integer> sorts = new ArrayList<>();
        for (Integer numsIndex : numsIndexes) {
            int insertIndex = binarySearch(sorts, numsIndex);
            result.set(numsIndex, sorts.size() - insertIndex);
            sorts.add(insertIndex, numsIndex);
        }

        return result;
    }

    public int binarySearch(ArrayList<Integer> sorts, int key) {
        int i = 0;
        int j = sorts.size();
        while (i < j) {
            int tmp = (i + j) / 2;
            if (sorts.get(tmp) < key) {
                i = tmp + 1;
            } else {
                j = tmp;
            }
        }

        return i;
    }

    // 306
    public boolean isAdditiveNumber(String num) {
        if (num.length() < 3) return false;
        for (int j = 1; j < num.length() - 1; j++) {
            for (int lenj = 1; lenj + j < num.length(); lenj++) {
                if (judge(num, j, lenj)) return true;
            }
        }

        return false;
    }

    public boolean judge(String num, int j, int lenj) {
        if ((num.charAt(0) == '0' && j > 1) || (num.charAt(j) == '0' && lenj > 1)) return false;
        StringBuilder sb = new StringBuilder();
        int i = 0;
        int leni = j;
        while (true) {
            int t = 0;
            int k = j - 1;
            int m = j + lenj - 1;
            while (k >= i || m >= j || t > 0) {
                int tmp = (k >= i ? num.charAt(k) - '0' : 0) + (m >= j ? num.charAt(m) - '0' : 0) + t;
                sb.append(tmp % 10);
                t = tmp / 10;
                k--;
                m--;
            }

            String s = sb.reverse().toString();
            if (s.length() + j + lenj > num.length()) return false;
            if (!s.equals(num.substring(j + lenj, s.length() + j + lenj))) return false;
            if (s.length() + j + lenj == num.length()) return true;
            i = j;
            leni = lenj;
            j = i + leni;
            lenj = s.length();
            sb = new StringBuilder();
        }
    }

// 314
    public List<List<Integer>> verticalOrder(TreeNode root) {
        if (root == null) return new ArrayList<>();

        int min = 0;
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        Queue<Integer> indexes = new LinkedList<>();
        Queue<TreeNode> queue = new LinkedList<>();

        indexes.add(0);
        queue.add(root);
        while (!queue.isEmpty()) {
            int ind = indexes.poll();
            TreeNode ele = queue.poll();
            List<Integer> tmpList = map.getOrDefault(ind, new ArrayList<>());
            tmpList.add(ele.val);
            map.put(ind, tmpList);
            if (ele.left != null) {
                min = Math.min(min, ind - 1);
                indexes.add(ind - 1);
                queue.add(ele.left);
            }

            if (ele.right != null) {
                indexes.add(ind  + 1);
                queue.add(ele.right);
            }
        }

        List<List<Integer>> result = new ArrayList<>(map.size());
        for (int i = 0; i < map.size(); i++) {
            result.add(map.get(i + min));
        }

        return result;
    }

    // 316
    public String removeDuplicateLetters(String s) {
        if (s.length() == 0) return "";

        int[] chars = new int[26];
        int[] existence = new int[26];
        for (int i = 0; i < s.length(); i++) {
            ++chars[s.charAt(i) - 'a'];
        }

        StringBuilder sb = new StringBuilder();
        sb.append(s.charAt(0));
        existence[s.charAt(0) - 'a'] = 1;
        int i = 0;
        int j = 1;
        while (j < s.length()) {
            if (existence[s.charAt(j) - 'a'] == 0) {
                while (i >= 0 && chars[sb.charAt(i) - 'a'] > 1 && sb.charAt(i) >= s.charAt(j)) {
                    --chars[sb.charAt(i) - 'a'];
                    existence[sb.charAt(i) - 'a'] = 0;
                    sb.deleteCharAt(i);
                    --i;
                }
                sb.append(s.charAt(j));
                existence[s.charAt(j) - 'a'] = 1;
                i++;

            } else {
                --chars[s.charAt(j) - 'a'];
            }
            j++;
        }

        return sb.toString();
    }

    // 318
    public int maxProduct(String[] words) {
        Arrays.sort(words, (Comparator.comparingInt(String::length)));
        HashMap<String, Integer> map = new HashMap<>();
        for (String word : words) {
            int result = 0;
            for (int i = 0; i < word.length(); i++) {
                result |= (1 << (word.charAt(i) - 'a'));
            }
            map.put(word, result);
        }

        int i = 0;
        int j = words.length - 1;
        int result = 0;
        while (i < j) {
            int k = j - 1;
            while (k >= i && ((map.get(words[k]) & map.get(words[j])) != 0)) k--;

            if (k >= i) {
                result = words[k].length() * words[j].length();
                i = k + 1;
            }

            j--;

            while (i < j && words[i].length() * words[j].length() <= result) i++;
        }

        return result;
    }


    // 321
    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        int[] road = new int[k];
        int[] result = new int[k];
        int[] tmps = new int[k];

        for (int i = k > nums2.length ? k - nums2.length : 0; i < Math.min(k, nums1.length); i++) {
            int left = 0;
            int right = nums1.length - i;
            for (int j = 0; j < i; j++) {
                int l = left;
                int min = l;
                while (l <= right) {
                    if (nums1[l] > nums1[min]) min = l;
                    l++;
                }
                road[j] = nums1[min];
                left = min + 1;
                right++;
            }

            left = 0;
            right = nums2.length - (k - i);
            for (int j = i; j < k; j++) {
                int l = left;
                int min = l;
                while (l <= right) {
                    if (nums2[l] > nums2[min]) min = l;
                    l++;
                }
                road[j] = nums2[min];
                left = min + 1;
                right++;
            }

            merge(result, road, tmps, i);
        }

        return result;
    }

    public void merge(int[] result, int[] road, int[] tmps, int i) {
        int j = 0;
        int k = i;
        int index = 0;
        boolean flag = true;
        while(index < result.length) {
            int tmp;
            if (j == i) {
                tmp = road[k];
                k++;
            } else if (k == result.length) {
                tmp = road[j];
                j++;
            } else if (road[j] < road[k]) {
                tmp = road[k];
                k++;
            } else {
                tmp = road[j];
                j++;
            }

            if (tmp != result[index]) {
                if (tmp > result[index]) flag = false;
                if (flag && tmp < result[index]) return;
            }

            tmps[index] = tmp;

            index++;
        }

        if (!flag) {
            int[] tt = result;
            result = tmps;
            tmps = tt;
        }
    }

    // 323
    public int countComponents(int n, int[][] edges) {
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        int count = n;
        int[] nums = new int[n];
        for (int[] e : edges) {
            if (nums[e[0]] == 0 && nums[e[1]] == 0) {
                nums[e[0]] = e[0] + 1;
                nums[e[1]] = e[0] + 1;
                List<Integer> tmpList = new ArrayList<>();
                tmpList.add(e[0]);
                tmpList.add(e[1]);
                map.put(e[0] + 1, tmpList);
                count -= 2;
            } else if (nums[e[0]] != 0 && nums[e[1]] == 0) {
                nums[e[1]] = nums[e[0]];
                map.get(nums[e[0]]).add(e[1]);
                --count;
            } else if (nums[e[0]] == 0 && nums[e[1]] != 0) {
                nums[e[0]] = nums[e[1]];
                map.get(nums[e[1]]).add(e[0]);
                --count;
            } else if (nums[e[0]] != nums[e[1]]){
                List<Integer> tmpList = map.remove(nums[e[0]]);
                for (Integer integer : tmpList) {
                    nums[integer] = nums[e[1]];
                }
                map.get(nums[e[1]]).addAll(tmpList);
            }
        }

        return map.size() + count;
    }

    // 324
    public void wiggleSort(int[] nums) {
        int[] tmpArr = Arrays.copyOf(nums, nums.length);
        Arrays.sort(tmpArr);
        int len = nums.length;
        if ((len & 1) == 1) nums[len - 1] = tmpArr[len / 2];
        int k = (len + 1) / 2;
        for (int i = 0; i < len / 2; i++) {
            nums[2 * i] = tmpArr[i];
            nums[2 * i + 1] = tmpArr[i + k];
        }
    }

    public void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    // 330
    public int minPatches(int[] nums, int n) {
        int count = 0;
        return findUpperBound(nums, 0, 1, 0, n);



        // int upper = 1;
        // int i =0;
        // while (upper <= n) {
        //     if (i == nums.length || upper < nums[i]) {
        //         upper *= 2;
        //         ++count;
        //     } else {
        //         upper += nums[i];
        //         ++i;
        //     }
        // }

        // return count;
    }


    public int findUpperBound(int[] nums, int i, int upper, int count, int n) {
        if (upper > n) return count;

        if (i == nums.length) return binarySearch(n, upper) + count;

        if (upper >= nums[i]) return findUpperBound(nums, i + 1, upper + nums[i], count, n);

        if (nums[i] > n) return binarySearch(n, upper) + count;

        int ks = binarySearch(nums[i] - 1, upper);
        return findUpperBound(nums, i + 1, upper * (1 << ks) + nums[i], count + ks, n);
    }

    public int binarySearch(int up, int ele) {
        double ttt = Math.ceil(((double) up + 1) / ele);
        if (ttt > (1 << 30)) return 31;
        int k = (int) ttt;
        int i = 0;
        int j = 30;
        while (i < j) {
            int tmp = (i + j) / 2;
            int tt = 1 << tmp;
            if (tt == k) return tmp;
            if (tt > k) {
                j = tmp;
            } else {
                i = tmp + 1;
            }
        }

        return i;
    }

    public boolean isValidSerialization(String preorder) {
        int count = 1;
        int i = 0;
        while (count > 0 && i < preorder.length()) {
            if (preorder.charAt(i) == '#') {
                --count;
                i = Math.min(preorder.length(), i + 2);
            } else {
                ++count;
                i += 1;
                while (i < preorder.length() && preorder.charAt(i - 1) != ',') i++;
            }
        }

        return count == 0 && i == preorder.length();
    }

    // 332
    public List<String> findItinerary(List<List<String>> tickets) {
        HashMap<String, PriorityQueue<String>> map = new HashMap<>();
        for (List<String> ticket : tickets) {
            String key = ticket.get(0);
            PriorityQueue<String> pq = map.getOrDefault(key, new PriorityQueue<>());
            pq.add(ticket.get(1));
            map.put(key, pq);
        }
        Stack<String> stack = new Stack<>();
        List<String> result = new ArrayList<>();
        stack.add("JFK");

        String key = "JFK";
        while (!map.isEmpty()) {
            if (!map.containsKey(key)) {
                result.add(stack.pop());
            } else {
                PriorityQueue<String> pq = map.get(key);
                stack.add(pq.poll());
                if (pq.isEmpty()) map.remove(key);
            }
            key = stack.peek();
        }

//        result.addAll(stack);
        while (!stack.isEmpty()) result.add(stack.pop());

        Collections.reverse(result);

        return result;
    }

    // 333
    int result_2;
    public int largestBSTSubtree(TreeNode root) {
        result_2 = 0;
        dfs_2(root);

        return result_2;
    }

    public int[] dfs_2(TreeNode root) {
        if (root == null) return new int[]{0, 0, 0};

        int[] left = dfs_2(root.left);
        int[] right = dfs_2(root.right);
        if (left != null && (left[2] == 0 || left[1] < root.val) && right != null && (right[2] == 0 || right[0] > root.val)) {
            int len = left[2] + right[2] + 1;
            result_2 = Math.max(result_2, len);
            return new int[]{(left[2] == 0 ? root.val : left[0]), (right[2] == 0 ? root.val : right[1]), len};
        }

        return null;
    }


    // 336
    public List<List<Integer>> palindromePairs(String[] words) {
        List<List<Integer>> result = new ArrayList<>();
        HashMap<String, Integer> map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            map.put(words[i], i);
        }

        for (int i = 0; i < words.length; i++) {
            String w = words[i];
            String wr = new StringBuilder().append(w).reverse().toString();
            int one = map.getOrDefault(wr, -1);
            if (one != -1 && one != i) {
                List<Integer> tList = new ArrayList<Integer>();
                tList.add(i);
                tList.add(one);
                result.add(tList);
            }

            int[] wrNext = new int[wr.length() + 1];
            getNext(wrNext, w);
            int m = 0;
            int n = 0;
            while (m < wr.length()) {
                while (m + n < wr.length() && wr.charAt(m + n) == w.charAt(n)) ++n;

                if (m + n == wr.length()) {
                    int tmp = map.getOrDefault(wr.substring(0, m), -1);
                    if (tmp != -1){
                        List<Integer> tList = new ArrayList<>();
                        tList.add(tmp);
                        tList.add(i);
                        result.add(tList);
                    }
                }

//                m = m + n - wrNext[n];
//                n = wrNext[n];
                if (n == 0) {
                    ++m;
                    n = 0;
                } else {
                    m = m + n - wrNext[n];
                    n = wrNext[n];
                }
            }

            getNext(wrNext, wr);
            m = 0;
            n = 0;
            while (m < w.length()) {
                while (m + n < w.length() && w.charAt(m + n) == wr.charAt(n)) ++n;

                if (m + n == w.length()) {
                    int tmp = map.getOrDefault(wr.substring(n), -1);
                    if (tmp != -1) {
                        List<Integer> tList = new ArrayList<>();
                        tList.add(i);
                        tList.add(tmp);
                        result.add(tList);
                    }
                }

//                m = m + n - wrNext[n];
//                n = wrNext[n];
                if (n == 0) {
                    ++m;
                    n = 0;
                } else {
                    m = m + n - wrNext[n];
                    n = wrNext[n];
                }
            }
        }

        return result;
    }

    public void getNext(int[] next, String t)
    {
        int j=0,k=-1;
        next[0]=-1;
        while(j<t.length())
        {
            if(k == -1 || t.charAt(j) == t.charAt(k))
            {
                j++;k++;
                next[j] = k;
            }
            else k = next[k];//此语句是这段代码最反人类的地方，如果你一下子就能看懂，那么请允许我称呼你一声大神！
        }
    }

    // 340
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        int result = 0;
        HashMap<Character, Integer> map = new HashMap<>();
        int i = 0;
        int j = 0;
        while (j < s.length()) {
            if (map.size() < k || map.containsKey(s.charAt(j))) {
                map.put(s.charAt(j), j);
                j++;
            } else {
                result = Math.max(result, j - i);
                map.put(s.charAt(j), j);
                j++;
                while (i < map.get(s.charAt(i))) i++;
                map.remove(s.charAt(i));
                i++;
            }
        }

        result = Math.max(result, j - i);

        return result;
    }

    // 347
    public int[] topKFrequent(int[] nums, int k) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            map.put(num, map.getOrDefault(num, 0) + 1);
        }

        PriorityQueue<Integer> pq = new PriorityQueue<>(((o1, o2) -> map.get(o1) - map.get(o2)));

        for (Integer integer : map.keySet()) {
            pq.add(integer);
            if (pq.size() > k) pq.poll();
        }

        int[] result = new int[k];
        int i = 0;
        while (!pq.isEmpty()) {
            result[i++] = pq.poll();
        }
        return result;
    }

    // 349
    public int[] intersection(int[] nums1, int[] nums2) {
        HashSet<Integer> set = new HashSet<>();
        for (int i : nums1) {
            set.add(i);
        }

        List<Integer> list = new ArrayList<>();
        for (int i : nums2) {
            if (set.contains(i)) {
                list.add(i);
                set.remove(i);
            }
        }


        return list.stream().mapToInt(Integer::valueOf).toArray();
    }

    // 350
    public int[] intersect(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        List<Integer> result = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < nums1.length && j < nums2.length) {
            int a = nums1[i];
            int b = nums2[j];
            if (a == b) {
                result.add(a);
                ++i;
                ++j;
            } else if (a < b) {
                ++i;
            } else {
                ++j;
            }
        }

        return result.stream().mapToInt(Integer::valueOf).toArray();
    }

    // 354
    public int maxEnvelopes(int[][] envelopes) {
        if (envelopes.length == 0) return 0;

        Arrays.sort(envelopes, ((o1, o2) -> o1[0] != o2[0] ? o1[0] - o2[0] : -o1[1] + o2[1]));
        int[] nums = new int[envelopes.length];
        nums[0] = envelopes[0][1];
        int count = 0;

        int prei = 0;
        int prej = 0;
        for (int i = 1; i < envelopes.length; i++) {
            if (envelopes[i][0] != prei || envelopes[i][1] != prej){
                int j = 0;
                int k = count + 1;
                while (j < k) {
                    int tmp = (j + k) / 2;
                    if (nums[tmp] < envelopes[i][1]) {
                        j = tmp + 1;
                    } else {
                        k = tmp;
                    }
                }

                if (nums[j] != envelopes[i][1]) {
                    if (j == count + 1) count++;
                    nums[j] = envelopes[i][1];
                }
            }
            prei = envelopes[i][0];
            prej = envelopes[i][1];
        }

        return count + 1;
    }

    // 356
    public boolean isReflected(int[][] points) {
        Arrays.sort(points, ((o1, o2) -> o1[1] == o2[1] ? o1[0] - o2[0] : o1[1] - o2[1]));

        int dividingLine = Integer.MAX_VALUE;
        int i = 0;
        while (i < points.length) {
            int y = points[i][1];
            int j = i;
            while (j + 1 < points.length && points[j + 1][1] == y) ++j;
            if (dividingLine == Integer.MAX_VALUE) dividingLine = points[i][0] + points[j][0];
            int k = j + 1;
            while (i <= j) {
                if (i != 0 && points[i][0] == points[i - 1][0]) {
                    ++i;
                } else if (j + 1 < points.length && points[j][0] == points[j + 1][0]) {
                    --j;
                } else {
                    if ((points[i][0] + points[j][0] != dividingLine)) return false;
                    ++i;
                    --j;
                }
            }
            i = k;
        }

        return true;
    }


    // 357
    public int countNumbersWithUniqueDigits(int n) {
        switch (n) {
            case 0 :
                return 1;
            case 1 :
                return 10;
            case 2 :
                return 91;
            case 3 :
                return 739;
            case 4 :
                return 5275;
            case 5 :
                return 32491;
            case 6 :
                return 168571;
            case 7 :
                return 712891;
            case 8 :
                return 2345851;
            case 9 :
                return 5611771;
            default :
                return 8877691;
        }
    }

    // 358
    public String rearrangeString(String s, int k) {
        return "";
    }

    // 360
    public int[] sortTransformedArray(int[] nums, int a, int b, int c) {
        int[] result = new int[nums.length];
        if (a == 0) {
            if (b >= 0) {
                for (int i = 0; i <nums.length ; i++) {
                    result[i] = b * nums[i] + c;
                }
            } else {
                for (int i = 0; i <nums.length ; i++) {
                    result[i] = b * nums[nums.length - 1 - i] + c;
                }
            }

            return result;
        }
        double median = (double) b / (-2 * a);
        int index = a < 0 ? 0 : nums.length - 1;
        int i = 0;
        int j = nums.length - 1;
        while ((i < nums.length && nums[i] <= median) || (j >= 0 && nums[j] > median)) {
            int x;
            if (i >= nums.length || nums[i] > median) {
                x = nums[j--];
            } else if (j < 0 || nums[j] <= median) {
                x = nums[i++];
            } else if (nums[i] + nums[j] >= median) {
                x = nums[j--];
            } else {
                x = nums[i++];
            }

            result[index] = a * x * x + b * x + c;
            if (a < 0) {
                ++index;
            } else {
                --index;
            }
        }

        return result;
    }

    // 363
    public int maxSumSubmatrix(int[][] matrix, int k) {
        int[] tmp = new int[matrix[0].length + 1];
        int[][] allArrays = new int[matrix.length + 1][matrix[0].length * (matrix[0].length + 1) / 2];
        for (int i = 0; i < matrix.length; i++) {
            int pre = 0;
            for (int j = 0; j < matrix[0].length; j++) {
                int t = tmp[j + 1];
                tmp[j + 1] = tmp[j + 1] + tmp[j] + matrix[i][j] - pre;
                pre = t;
            }
            for (int n = 1; n <= matrix[0].length; n++) {
                for (int m = 0; m < n; m++) {
                    allArrays[i + 1][m + n * (n - 1) / 2] = tmp[n] - tmp[m];
                }
            }
        }

        int result = Integer.MIN_VALUE;
        for (int j = 0; j < allArrays[0].length; j++) {
            int t = findMaxSub(allArrays, j, k);
            if (t == k) return k;
            result = Math.max(result, t);
        }

        return result;
    }

    public int findMaxSub(int[][] allArrays, int j, int k) {
        HashSet<Integer> set = new HashSet<>();
//        Arrays.stream(allArrays).forEach(allArray -> {
//            set.add(allArray[j]);
//            set.add(allArray[j] - k);
//        });

        for (int i = 0; i < allArrays.length; i++) {
            set.add(allArrays[i][j]);
            set.add(allArrays[i][j] - k);
        }
        ArrayList<Integer> integers = new ArrayList<>(set);
        Collections.sort(integers);

        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < integers.size(); i++) {
            map.put(integers.get(i), i);
        }

        int result = Integer.MIN_VALUE;
        int[] lineTree = new int[4 * allArrays.length];
        for (int[] allArray : allArrays) {
            int t = get(map.get(allArray[j] - k) + 1, lineTree);
            if (t != 0) result = Math.max(result, allArray[j] - integers.get(t - 1));
            set(map.get(allArray[j]) + 1, lineTree);
        }

        return result;
    }

    public int get(int k, int[] lineTree) {
        return get(k, lineTree, 0, 1, lineTree.length / 4);
    }

    public int get(int k, int[] lineTree, int index, int start, int end) {
        if (lineTree[index] == 0) return 0;
        if (lineTree[index] >= k) return lineTree[index];
        if (start == end) return 0;
        int midean = start + (end - start) / 2;
        int result = 0;
        if (k <= midean) {
            result = Math.max(result, get(k, lineTree, 2 * index + 1, start, midean));
        }

        if (result == 0) result = get(k, lineTree, 2 * index + 2, midean + 1, end);

        return result;
    }

    public void set(int key, int[] lineTree) {
        set(key, lineTree, 0, 1, lineTree.length / 4);
    }

    public void set(int key, int[] lineTree, int index, int start, int end) {
        if (lineTree[index] > key) lineTree[index] = key;
        if (start == end) return;
        int midean = start + (end - start) / 2;
        if (key <= midean) {
            set(key, lineTree, 2 * index + 1, start, midean);
        } else {
            set(key, lineTree, 2 * index + 2, midean + 1, end);
        }
    }

    // 365
    public boolean canMeasureWater(int jug1Capacity, int jug2Capacity, int targetCapacity) {
        if (jug1Capacity + jug2Capacity < targetCapacity) return false;
        int counter = 0;
        HashSet<String> set = new HashSet<>();
        Queue<Integer> queue = new LinkedList<>();
        queue.add(0);
        queue.add(0);
        set.add("0#0");
        while (!queue.isEmpty()) {
            int a = queue.poll();
            assert !queue.isEmpty();
            int b = queue.poll();
            ++counter;
            if (a + b == targetCapacity) {
                System.out.println("counter:" + counter);
                return true;
            }

            if (!set.contains(String.format("0#%d", b))) {
                queue.add(0);
                queue.add(b);
                set.add(String.format("0#%d", b));
            }

            if (!set.contains(String.format("%d#0", a))) {
                queue.add(a);
                queue.add(0);
                set.add(String.format("%d#0", a));
            }

            if (!set.contains(String.format("%d#%d", jug1Capacity, b))) {
                queue.add(jug1Capacity);
                queue.add(b);
                set.add(String.format("%d#%d", jug1Capacity, b));
            }

            if (!set.contains(String.format("%d#%d", a, jug2Capacity))) {
                queue.add(a);
                queue.add(jug2Capacity);
                set.add(String.format("%d#%d", a, jug2Capacity));
            }

            if (!set.contains(String.format("%d#%d", Math.min(jug1Capacity, a + b), Math.max(0, b + a - jug1Capacity)))) {
                queue.add(Math.min(jug1Capacity, a + b));
                queue.add(Math.max(0, b + a - jug1Capacity));
                set.add(String.format("%d#%d", Math.min(jug1Capacity, a + b), Math.max(0, b + a - jug1Capacity)));
            }

            if (!set.contains(String.format("%d#%d", Math.max(0, a + b - jug2Capacity), Math.min(jug2Capacity, a + b)))) {
                queue.add(Math.max(0, a + b - jug2Capacity));
                queue.add(Math.min(jug2Capacity, a + b));
                set.add(String.format("%d#%d", Math.max(0, a + b - jug2Capacity), Math.min(jug2Capacity, a + b)));
            }
        }

        System.out.println("counter:" + counter);
        return false;
    }

    // 366
    public List<List<Integer>> findLeaves(TreeNode root) {
        HashMap<Integer, List<Integer>> map = new HashMap<>();
        int max = findLeaves(root, map);
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 1; i <= max; i++) {
            result.add(map.get(i));
        }
        return result;
    }

    public int findLeaves(TreeNode root, HashMap<Integer, List<Integer>> map) {
        if (root == null) return 0;

        int level = Math.max(findLeaves(root.left, map), findLeaves(root.right, map)) + 1;

        List<Integer> list = map.getOrDefault(level, new ArrayList<>());
        list.add(root.val);
        map.put(level, list);
        return level;
    }

    // 370
    public int[] getModifiedArray(int length, int[][] updates) {
        int[] arr = new int[length];
        for (int[] update : updates) {
            arr[update[0]] += update[2];
            if (update[1] != length - 1) {
                arr[update[1] + 1] -= update[2];
            }
        }

        for (int i = 1; i < length; i++) {
            arr[i] = arr[i - 1] + arr[i];
        }

        return arr;
    }

    // 371
    public int getSum(int a, int b) {
        int result = 0;
        int digit = 0;
        for (int i = 0; i < 32; i++) {
            result = result | (((a >> i) & 1) ^ ((b >> i) & 1) ^ digit) << i;
            digit = (digit & (((a >> i) & 1) | ((b >> i) & 1))) | (((a >> i) & 1) & ((b >> i) & 1));
        }

        return result;
    }

    // 372
    public int superPow(int a, int[] b) {
        int[] arr = new int[1337];
        HashMap<Integer, Integer> map = new HashMap<>();
        int remainder = 1;
        int k = 0;
        for (int i = 0; i < 1337; i++) {
            if (map.containsKey(remainder)) {
                k = i - map.get(remainder);
                break;
            } else {
                map.put(remainder, i);
                arr[i] = remainder;
            }
            remainder = remainder * (a % 1337) % 1337;
        }

        remainder = 1;
        int result = 0;
        for (int i = b.length - 1; i >= 0; i--) {
            result += remainder * b[i];
            remainder = remainder * 10 % k;
        }

        result = result % k;
        return arr[result];
    }
    
    // 373
    public List<List<Integer>> kSmallestPairs(int[] nums1, int[] nums2, int k) {
        int[] arrSeq1 = new int[nums1.length];
        int[] arrSeq2 = new int[nums2.length];
        PriorityQueue<Integer> pq = new PriorityQueue<>((Comparator.comparingInt(o -> nums2[arrSeq1[o >> 14]] + nums1[arrSeq2[o & ((1 << 14) - 1)]])));
        pq.add(0);
        k = Math.min(k, nums1.length * nums2.length);

        List<List<Integer>> result = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            ArrayList<Integer> tmp = new ArrayList<>();
            assert !pq.isEmpty();
            int t = pq.poll();
            int index1 = t >> 14;
            int index2 = t & ((1 << 14) - 1);

            if (++arrSeq1[index1] < nums2.length && arrSeq2[arrSeq1[index1]] == index1) {
                pq.add((index1 << 14) + arrSeq1[index1]);
            }

            if (++arrSeq2[index2] < nums1.length && arrSeq1[arrSeq2[index2]] == index2) {
                pq.add((arrSeq2[index2] << 14) + index2);
            }

            tmp.add(nums1[index1]);
            tmp.add(nums2[index2]);
            result.add(tmp);
        }
        return result;
    }

    // 375
    HashMap<Integer, Integer> map_4;
    public int getMoneyAmount(int n) {
        map_4 = new HashMap<>();
        return getMoneyAmount(1, n);
    }

    public int getMoneyAmount(int start, int end) {
        if (end < start) return 0;
        if (map_4.containsKey((start << 14) + end)) return map_4.get((start << 14) + end);

        if (start == end) return 0;

        int sum = (start + end) * (end - start + 1) / 2;
        for (int i = start; i <= end; i++) {
            sum = Math.min(sum, i + Math.max(getMoneyAmount(start, i - 1), getMoneyAmount(i + 1, end)));
        }

        map_4.put((start << 14) + end, sum);

        return sum;
    }

    // 378
    public int kthSmallest(int[][] matrix, int k) {
        int[] indexes = new int[matrix.length];
        int n = matrix.length;
        PriorityQueue<Integer> pq = new PriorityQueue<>(matrix.length, (Comparator.comparingInt(o -> matrix[o][indexes[o]])));
        pq.add(0);
        int x = 0;
        for (int i = 0; i < k; i++) {
            assert !pq.isEmpty();
            x = pq.poll();
            if (x < n - 1 && indexes[x] == indexes[x + 1]) pq.add(x + 1);
            if (++indexes[x] != n && (x == 0 || indexes[x] != indexes[x - 1])) pq.add(x);
        }

        return matrix[x][indexes[x] - 1];
    }

    // 385
    private static class NestedIntegerListNode {
        NestedInteger val;
        NestedIntegerListNode next;

        public NestedIntegerListNode() {
        }

        public NestedIntegerListNode(NestedInteger val) {
            this.val = val;
        }

    }
    public NestedInteger deserialize(String s) {
        Stack<NestedIntegerListNode> nestedIntegerListNodes = new Stack<>();
        NestedIntegerListNode head = new NestedIntegerListNode();
        NestedIntegerListNode tail = head;
//        Integer ele = null;
        StringBuilder ele = null;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '[') {
                nestedIntegerListNodes.add(tail);
            } else if (s.charAt(i) == ']') {
                if (ele != null) {
                    tail.next = new NestedIntegerListNode(new NestedInteger(Integer.parseInt(ele.toString())));
                    ele = null;
                    tail = tail.next;
                }

                NestedIntegerListNode e = nestedIntegerListNodes.pop();

                NestedInteger ni = new NestedInteger();
                NestedIntegerListNode tmp = e.next;
                while (tmp != null) {
                    ni.add(tmp.val);
                    tmp = tmp.next;
                }

                e.next = new NestedIntegerListNode(ni);
                tail = e.next;
            } else if (s.charAt(i) == ',') {
                if (ele != null) {
                    tail.next = new NestedIntegerListNode(new NestedInteger(Integer.parseInt(ele.toString())));
                    ele = null;
                    tail = tail.next;
                }
            } else {
                if (ele == null) {
                    ele = new StringBuilder();
                }

                ele.append(s.charAt(i));
            }
        }

        if (ele != null) {
            tail.next = new NestedIntegerListNode(new NestedInteger(Integer.parseInt(ele.toString())));
            ele = null;
            tail = tail.next;
        }

        return head.next.val;
    }

    // 388
    public int lengthLongestPath(String input) {
        int result = 0;
        String[] splits = input.split("\n");
        ArrayList<Integer> list = new ArrayList<>();
        for (String s : splits) {
            int index = 0;
            int i = 0;
            while (s.charAt(i) == '\t') {
                ++index;
                ++i;
            }

            int j = s.length() - 1;
            while (j >= i) {
                if (s.charAt(j) == '.') break;
                --j;
            }

            if (j < i) {
                if (index >= list.size()) {
                    list.add(s.length() - i);
                } else {
                    list.set(index, s.length() - i);
                }
            } else {
                int l = s.length() - i + index;
                for (int k = 0; k < index; k++) {
                    l += list.get(k);
                }

                result = Math.max(result, l);
            }
        }

        return result;
    }


    // 403
    public boolean canCross(int[] stones) {
        if (stones[1] != 1) return false;

        int end = stones[stones.length - 1];
        HashMap<Integer, HashSet<Integer>> map = new HashMap<>();

        for (int stone : stones) {
            map.put(stone, new HashSet<>());
        }

        map.get(1).add(1);
        map.get(1).add(2);


        for (int i = 1; i < stones.length; i++) {
            HashSet<Integer> s = map.get(stones[i]);
            for (Integer integer : s) {
                if (stones[i] + integer == end) return true;
                if (map.containsKey(stones[i] + integer)) {
                    HashSet<Integer> set = map.get(stones[i] + integer);
                    if (integer != 1) set.add(integer - 1);
                    set.add(integer);
                    set.add(integer + 1);
                }
            }
        }

        return false;
    }

    // 405
    public String toHex(int num) {
        if (num == 0) return "0";

        StringBuilder sb = new StringBuilder();
        if (num < 0) {
            int v = 8 + ((num & ((1 << 30) + ((1 << 30) - 1))) >> 28);
            sb.append(v < 10 ? (char) (v + '0') : (char) (v - 10 + 'a'));
        } else {
            int v = (num & ((1 << 30) + ((1 << 30) - 1))) >> 28;
            sb.append(v);
        }

        for (int i = 6; i >= 0; i--) {
            int v = (num & ((1 << (i + 1) * 4) - 1)) >> (4 * i);
            sb.append(v < 10 ? (char) (v + '0') : (char) (v - 10 + 'a'));
        }

        int i = 0;
        while (sb.charAt(i) == '0') i++;
        return sb.substring(i, sb.length());
    }

    // 406
    private static class ListNodeArr implements Comparable<ListNodeArr> {
        protected int[] person;
        protected int weight;
        protected ListNodeArr next;


        @Override
        public int compareTo(ListNodeArr o) {
            return Integer.compare(this.person[0], o.person[0]);
        }

        public void merge(ListNodeArr toBeMerged) {
            ListNodeArr head = this;
            ListNodeArr e1 = this.next;
            ListNodeArr e2 = toBeMerged.next;
            ListNodeArr tail = head;
            while (e1 != null || e2 != null) {
                if (e1 == null || (e2 != null && e1.compareTo(e2) >= 0)) {
                    tail.next = e2;
                    tail = e2;
                    e2 = e2.next;
                } else{
                    tail.next = e1;
                    tail = e1;
                    e1 = e1.next;
                }
            }
            tail.next = null;
        }
    }

    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, ((o1, o2) -> o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0]));

        ListNodeArr peopleNode = new ListNodeArr();
        ListNodeArr zeroNode = new ListNodeArr();

        ListNodeArr peopleEle = peopleNode;
        ListNodeArr zeroEle = zeroNode;

        for (int[] person : people) {
            ListNodeArr ele = new ListNodeArr();
            ele.person = person;
            ele.weight = person[1];
            if (person[1] == 0) {
                zeroEle.next = ele;
                zeroEle = ele;
            } else {
                peopleEle.next = ele;
                peopleEle = ele;
            }
        }

        int[][] result = new int[people.length][];
        int index = 0;
        ListNodeArr ele = null;
        while ((ele = zeroNode.next) != null) {
            result[index++] = ele.person;

            ListNodeArr toBeMerged = new ListNodeArr();
            ListNodeArr toBeEle = toBeMerged;
            ListNodeArr e = peopleNode;
            while (e.next != null && e.next.compareTo(ele) <= 0) {
                --e.next.weight;
                if (e.next.weight == 0) {
                    toBeEle.next = e.next;
                    toBeEle = e.next;
                    e.next = e.next.next;
                } else {
                    e = e.next;
                }

            }
            toBeEle.next = null;

            zeroNode.next = zeroNode.next.next;
            zeroNode.merge(toBeMerged);
        }

        return result;
    }

    // 408
    public boolean validWordAbbreviation(String word, String abbr) {
        int i = 0;
        int j = 0;
        while (i < word.length() && j < abbr.length()) {
            if (Character.isLetter(abbr.charAt(j))) {
                if (word.charAt(i) != abbr.charAt(j)) return false;
                i++;
                j++;
            } else {
                int k = j + 1;
                while (k < abbr.length() && !Character.isLetter(abbr.charAt(k))) k++;
                int n = Integer.parseInt(abbr.substring(j, k));
                i += n;
                j = k;
            }
        }

        return i == word.length() && j == abbr.length();
    }

    // 412
    public List<String> fizzBuzz(int n) {
        List<String> result = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            if (i % 5 == 0) {
                if (i % 3 == 0) {
                    result.add("FizzBuzz");
                } else {
                    result.add("Buzz");
                }
            } else if (i % 3 == 0) {
                result.add("Fizz");
            } else {
                result.add(String.valueOf(i));
            }
        }

        return result;
    }

    // 420
    public int strongPasswordChecker(String password) {
        int d = 1;
        int u = 1;
        int l = 1;

        int pre = 0;
        ArrayList<Integer>[] chars = new ArrayList[4];
        for (int i = 0; i < 4; i++) {
            chars[i] = new ArrayList();
        }

        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (d == 1 && Character.isDigit(c)) d = 0;
            if (u == 1 && Character.isUpperCase(c)) u = 0;
            if (l == 1 && Character.isLowerCase(c)) l = 0;

            if (c != password.charAt(pre)) {
                if (i - pre >= 3) {
                    if (i - pre == 5) {
                        chars[3].add(i - pre);
                    } else {
                        chars[(i - pre) % 3].add(i - pre);
                    }
                }

                pre = i;
            }
        }

        int dul = d + u + l;

        if (password.length() - pre >= 3) {
            if (password.length() - pre == 5) {
                chars[3].add(password.length() - pre);
            } else {
                chars[(password.length() - pre) % 3].add(password.length() - pre);
            }
        }

        int count = password.length();

        while (count < 6 || count > 20) {
            List<Integer> list = !chars[0].isEmpty() ? chars[0] : (!chars[1].isEmpty() ? chars[1] : (!chars[2].isEmpty() ? chars[2] : (!chars[3].isEmpty() ? chars[3] : null)));
            if (list != null) {
                int ele = list.remove(list.size() - 1) - (count < 6 ? 2 : 1);
                if (ele >= 3) {
                    chars[ele == 5 ? 3 : (ele % 3)].add(ele);
                }
            }

            count = count < 6 ? count + 1 : count - 1;
        }

        int result = 0;

        for (ArrayList<Integer> aChar : chars) {
            for (Integer integer : aChar) {
                result += integer / 3;
            }
        }

        result += Math.max(0, dul - result - (password.length() < 6 ? 6 - password.length() : 0));
        if (password.length() < 6) {
            result += 6 - password.length();
        }
        if (password.length() > 20) {
            result += password.length() - 20;
        }

        return result;
    }

    // intersection
    public int[] arrayintersection(int[] arr1, int[] arr2) {
        // 1
//        HashMap<Integer, Integer> map = new HashMap<>();
//        for (int i : arr1) {
//            map.put(i, map.getOrDefault(i, 0) + 1);
//        }
//
//        ArrayList<Integer> list = new ArrayList<>();
//        for (int i : arr2) {
//            if (map.containsKey(i)) {
//                list.add(i);
//                if (map.get(i) == 1) {
//                    map.remove(i);
//                } else {
//                    map.put(i, map.get(i) - 1);
//                }
//            }
//        }
//
//        return list.stream().mapToInt(Integer::valueOf).toArray();

        // 2
//        int[] a1 = new int[100];
//        int[] a2 = new int[100];
//        for (int i : arr1) {
//            a1[i]++;
//        }
//
//        for (int i : arr2) {
//            a2[i]++;
//        }
//
//        List<Integer> list = new ArrayList<>();
//        for (int i = 0; i < 100; i++) {
//            int counts = Math.min(a1[i], a2[i]);
//            for (int j = 0; j < counts; j++) list.add(i);
//        }
//
//        return list.stream().mapToInt(Integer::valueOf).toArray();

        // 3
        Arrays.sort(arr1);
        Arrays.sort(arr2);

        List<Integer> list = new ArrayList<>();
        // arr1的第一个元素
        int i = 0;
        // arr2的第一个元素下标
        int j = 0;
        while (i < arr1.length && j < arr2.length) {
            // arr1[i]比arr2中剩下最小值arr2[j]小,不可能存在了,舍去,i++
            if (arr1[i] < arr2[j]) {
                i++;
            } else if (arr1[i] == arr2[j]) {
                list.add(arr1[i]);
                i++;
                j++;
            } else {
                j++;
            }
        }

        return list.stream().mapToInt(Integer::valueOf).toArray();
    }

    // 422
    public boolean validWordSquare(List<String> words) {
        char[][] chars = new char[words.size()][];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = words.get(i).toCharArray();
        }

        for (int i = 0; i < chars.length; i++) {
            if (!check(chars, i, i)) return false;
        }

        return true;
    }

    public boolean check(char[][] chars, int i, int j) {
        if (chars[i].length > chars.length) return false;

        int k;
        for (k = 0; k < chars[i].length; k++) {
            if (chars[k].length <= j || chars[k][j] != chars[i][k]) return false;
        }

        for (; k < chars.length; k++) if (chars[k].length > j) return false;

        return true;
    }

    // 424
    public int characterReplacement(String s, int k) {
        int[] nextIndex = new int[s.length()];
        int[] preIndex = new int[26];
        int[] startIndex = new int[26];

        for (int i = 0; i < s.length(); i++) {
            int index = s.charAt(i) - 'A';
            if (startIndex[index] == 0) {
                startIndex[index] = i + 1;
            } else {
                nextIndex[preIndex[index]] = i;
            }


            preIndex[index] = i;
        }

        int result = 0;

        for (int i : startIndex) {
            --i;
            int c = 0;
            int j = i;
            while (i != -1) {
                if (nextIndex[j] == 0) {
                    result = Math.max(result, Math.min(j - i + 1 + k - c, s.length()));
                    break;
                } else if (c + nextIndex[j] - j - 1 > k) {
                    result = Math.max(result, j - i + 1 + k - c);
                    while (nextIndex[i] == i + 1) i++;
                    if (i != j) {
                        c -= nextIndex[i] - i - 1;
                        i = nextIndex[i];
                    } else {
                        i = nextIndex[i];
                        j = i;
                        c = 0;
                    }
                } else {
                    c += nextIndex[j] - j - 1;
                    j = nextIndex[j];
                }
            }
        }

        return result;
    }

    // 425
    private class DictTree {
        DictTree[] nexts = new DictTree[26];
    }

    public List<List<String>> wordSquares(String[] words) {
        DictTree dt = new DictTree();
        for (String w : words) {
            DictTree ele = dt;
            for (int i = 0; i < w.length(); i++) {
                char c = w.charAt(i);
                if (ele.nexts[c - 'a'] == null) ele.nexts[c - 'a'] = new DictTree();
                ele = ele.nexts[c - 'a'];
            }
        }

        char[][] road = new char[words[0].length()][words[0].length()];
        List<List<String>> result = new ArrayList<>();
        dfs_3(0, road, dt, result);

        return result;
    }

    public void dfs_3(int index, char[][] road, DictTree dt, List<List<String>> result) {
        if (index == road.length) {
            List<String> s = new ArrayList<>();
            for (char[] chars : road) {
                s.add(String.valueOf(chars));
            }

            result.add(s);
            return;
        }

        DictTree ele = dt;
        int i;
        for (i = 0; i < index; i++) {
            ele = ele.nexts[road[index][i] - 'a'];
            if (ele == null) return;
        }


        dfs_4(i, road, ele, result, index, dt);
    }

    public void dfs_4(int i, char[][] road, DictTree ele, List<List<String>> result, int index, DictTree dt) {
        if (i == road.length) {
            dfs_3(index + 1, road, dt, result);
            return;
        }

        for (int j = 0; j < 26; j++) {
            if (ele.nexts[j] != null) {
                road[index][i] = (char) (j + 'a');
                road[i][index] = (char) (j + 'a');
                dfs_4(i + 1, road, ele.nexts[j], result, index, dt);
            }
        }
    }

    // 435
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, ((o1, o2) -> (o1[0] == o2[0] ? o1[1] - o2[1] : o1[0] - o2[0])));
        int result = 0;
        int i = 1;
        int ele = 0;
        while (i < intervals.length) {
            if (intervals[ele][1] > intervals[i][0]) {
                result++;
                if (intervals[i][1] < intervals[ele][1]) ele = i;
            } else {
                ele = i;
            }

            i++;
        }

        return result;
    }

    //436
    public int[] findRightInterval(int[][] intervals) {
        Integer[] indexs = new Integer[intervals.length];
        for (int i = 0; i < indexs.length; i++) indexs[i] = i;

        Arrays.sort(indexs, Comparator.comparingInt(o -> intervals[o][0]));

        int[] result = new int[indexs.length];

        for (int i = 0; i < indexs.length; i++) {
            int k = i;
            int m = indexs.length;
            int v = intervals[indexs[i]][1];
            while (k < m) {
                int tmp = (k + m) / 2;
                if (intervals[indexs[tmp]][0] >= v) {
                    m = tmp;
                } else {
                    k = tmp + 1;
                }
            }

            if (k == indexs.length) {
                result[indexs[i]] = -1;
            } else {
                result[indexs[i]] = indexs[k];
            }
        }

        return result;
    }

    // 438
    public List<Integer> findAnagrams(String s, String p) {
        if (p.length() > s.length()) return new ArrayList<>();

        int[] charP = new int[26];
        int[] charEle = new int[26];
        HashSet<Integer> set = new HashSet<>();
        for (int i = 0; i < p.length(); i++) {
            charP[p.charAt(i) - 'a']++;
        }

        for (int i = 0; i < p.length(); i++) {
            charEle[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < 26; i++) {
            if (charP[i] != charEle[i]) set.add(i);
        }

        List<Integer> result = new ArrayList<>();
        for (int i = 0; i <= s.length() - p.length(); i++) {
            if (set.isEmpty()) result.add(i);
            if (i != s.length() - p.length()) {
                charEle[s.charAt(i) - 'a']--;
                if (!set.contains(s.charAt(i) - 'a')) {
                    set.add(s.charAt(i) - 'a');
                } else if (charP[s.charAt(i) - 'a'] == charEle[s.charAt(i) - 'a']) {
                    set.remove(s.charAt(i) - 'a');
                }

                charEle[s.charAt(i + p.length()) - 'a']++;
                if (!set.contains(s.charAt(i + p.length()) - 'a')) {
                    set.add(s.charAt(i + p.length()) - 'a');
                } else if (charP[s.charAt(i + p.length()) - 'a'] == charEle[s.charAt(i + p.length()) - 'a']) {
                    set.remove(s.charAt(i + p.length()) - 'a');
                }
            }
        }

        return result;
    }

    // 439
    public String parseTernary(String expression) {
        if (expression.length() == 0) return "";

        return parseTernary(expression, 0, expression.length() - 1);
    }

    public String parseTernary(String expression, int start, int end) {
        if (start == end) return expression.substring(start, end + 1);

        Stack<Integer> stack = new Stack<>();
        int j = end;
        while(j > start + 1) {
            if (expression.charAt(j) == ':') {
                stack.add(j);
            } else if (expression.charAt(j) == '?') {
                stack.pop();
            }
            j--;
        }

        int mid = stack.pop();

        if (expression.charAt(start) == 'T') return parseTernary(expression, start + 2, mid - 1);
        return parseTernary(expression, mid + 1, end);
    }

    // 440
    public int findKthNumber(int n, int k) {
        int flag = -1;

        int c = 1;
        int tens = 1;
        if (n == 1000000000) {
            tens = 1000000000;
            c = 10;
        } else {
            while (tens <= n) {
                tens *= 10;
                c++;
            }
            tens /= 10;
            c--;
        }

        int result = 0;
        while (k > 0) {
            if (k == 1 && result != 0) return result;
            int tc = result == 0 ? 0 : 1;
            int v = result == 0 ? 1 : 0;
            int tv = 0;
            int tn = (n / tens) % 10;
            while (true) {
                if (result == 0) {
                    if (v < tn) {
                        tv = (tens - 1) / 9 * 10 + 1;
                    } else if (v == tn) {
                        tv = (tens - 1) / 9;
                        tv += (n % tens) + 1;
                    } else {
                        tv = (tens - 1) / 9;
                    }
                } else {
                    if (flag == -1 || (flag == 0 && v < tn)) {
                        tv = (tens - 1) / 9 * 10 + 1;
                    } else if (flag == 0 && v == tn) {
                        tv = (tens - 1) / 9;
                        tv += (n % tens) + 1;
                    } else {
                        tv = (tens - 1) / 9;
                    }
                }


                if (tc + tv < k) {
                    tc += tv;
                    v++;
                    tv = 0;
                } else {
                    result = result * 10 + v;
                    k -= tc;

                    if (result < 10) {
                        if (v == tn) {
                            flag = 0;
                        } else if (v > tn) {
                            flag = 1;
                        }
                    } else {
                        if (flag == 0) {
                            if (v > tn) {
                                flag = 1;
                            } else if (v < tn) {
                                flag = -1;
                            }
                        }
                    }

                    tens /= 10;

                    break;
                }
            }
        }

        return result;
    }

    // 444
    public boolean sequenceReconstruction(int[] org, List<List<Integer>> seqs) {
        if (org.length == 1) return !seqs.isEmpty() && seqs.stream().filter(o -> o.size() == 1 && o.get(0) == 1).count() == seqs.size();

        int n = org.length;
        int[] indes = new int[n + 1];
        HashSet<Integer>[] outdes = new HashSet[n + 1];
        for (int i = 0; i < outdes.length; i++) outdes[i] = new HashSet<>();
        for (List<Integer> seq : seqs) {
            if (seq.size() == 1 && (seq.get(0) > n || seq.get(0) < 1)) return false;
            for (int i = 0; i < seq.size() - 1; i++) {
                if (seq.get(i) > n || seq.get(i) < 1 || seq.get(i + 1) > n || seq.get(i + 1) < 1) return false;
                if (!outdes[seq.get(i)].contains(seq.get(i + 1))) {
                    outdes[seq.get(i)].add(seq.get(i + 1));
                    indes[seq.get(i + 1)]++;
                }
            }
        }

        int ele = -1;
        int index = 0;
        for (int i = 1; i < n + 1; i++) {
            if (indes[i] == 0) {
                if (ele != -1) return false;
                ele = i;
                if (ele != org[index]) return false;
                index += 1;
            }
        }

        if (ele == -1) return false;

        while (index < n) {
            int next = -1;
            for (Integer integer : outdes[ele]) {
                if (--indes[integer] == 0) {
                    if (next != -1) return false;
                    next = integer;
                }
            }

            if (next == -1) {
                return false;
            }
            ele = next;

            if (ele != org[index]) return false;
            index++;
        }

        return true;
    }

    // 445
    int digit;
    ListNode ans;
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int a = nodeCounts(l1);
        int b = nodeCounts(l2);

        digit = 0;
        ans = new ListNode();
        dfs(l1, a, l2, b);
        if (digit == 1) {
            ListNode ele = new ListNode(1);
            ele.next = ans.next;
            ans.next = ele;
        }

        return ans.next;
    }

    public int nodeCounts(ListNode l) {
        if (l == null) return 0;

        return 1 + nodeCounts(l.next);
    }

    public void dfs(ListNode l1, int c1, ListNode l2, int c2) {
        if (c1 == 0 && c2 == 0) return;

        if (c1 > c2) {
            dfs(l1.next, c1 - 1, l2, c2);
        } else if (c1 < c2) {
            dfs(l1, c1, l2.next, c2 - 1);
        } else {
            dfs(l1.next, c1 - 1, l2.next, c2 - 1);
        }

        int t = c1 > c2 ? digit + l1.val : (c1 < c2 ? digit + l2.val : digit + l1.val + l2.val);
        digit = t / 10;
        ListNode ele = new ListNode(t % 10);
        ele.next = ans.next;
        ans.next = ele;
    }

    // 447
    public int numberOfBoomerangs(int[][] points) {
        if (points.length < 3) return 0;
        HashMap<String, Integer> map = new HashMap<>();
        int ans = 0;
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (i != j) {
                    int dis = (points[i][0] - points[j][0]) * (points[i][0] - points[j][0]) + (points[i][1] - points[j][1]) * (points[i][1] - points[j][1]);
                    String key = i + "#" + dis;
                    map.put(key, map.getOrDefault(key, 0) + 1);
                }
            }
        }

        for (Integer i : map.values()) {
            ans += i * (i - 1);
        }

        return ans;
    }

    // 451
    public String frequencySort(String s) {
        int[] chars = new int[52];
        Integer[] indexs = new Integer[52];
        for (int i = 0; i < s.length(); i++) chars[s.charAt(i) >= 'a' ? s.charAt(i) - 'a' + 26 : s.charAt(i) - 'A']++;
        for (int i = 0; i < 52; i++) indexs[i] = i;
        Arrays.sort(indexs, Comparator.comparingInt(o -> -chars[o]));

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 52; i++) {
            for (int j = 0; j < chars[indexs[i]]; j++) {
                sb.append((char) (indexs[i] < 26 ? 'A' + indexs[i] : 'a' + indexs[i] - 26));
            }
        }

        return sb.toString();
    }

    // 452
    public int findMinArrowShots(int[][] points) {
        Arrays.sort(points, ((o1, o2) -> o1[0] == o2[0] ? Integer.compare(o1[1], o2[1]) : Integer.compare(o1[0], o2[0])));
        int ans = 0;
        int i = 0;
        while (i < points.length) {
            ans++;
            int j = i + 1;
            while (j < points.length && points[j][0] <= points[i][1]) j++;
            i = j;
        }

        return ans;
    }

    // 469
    public boolean isConvex(List<List<Integer>> points) {
        if (points.size() < 3) return false;

        if (compa(points, 0, 1, 2) == 0) return false;

        for (int i = 3; i < points.size(); i++) {
            if (compa(points, 0, 1, i - 1) * compa(points, 0, 1, i) <= 0) return false;
            if (compa(points, 0, i - 1, 1) * compa(points, 0, i - 1, i) >= 0) return false;
            if (compa(points, i - 2, i - 1, 0) * compa(points, i - 2, i - 1, i) <= 0) return false;
            if (compa(points, 0, i - 1, i - 2) * compa(points, 0, i - 1, i) >= 0) return false;
        }

        return true;
    }

    public int compa(List<List<Integer>> points, int p1, int p2, int p) {
        return (points.get(p2).get(0) - points.get(p1).get(0)) * (points.get(p).get(1) - points.get(p1).get(1)) - (points.get(p).get(0) - points.get(p1).get(0)) * (points.get(p2).get(1) - points.get(p1).get(1));
    }

    // 471
    int[][] dbIndex;
    int[][] dbLen;
    public String encode(String s) {
        this.dbIndex = new int[s.length()][s.length()];
        this.dbLen = new int[s.length()][s.length()];
        int l = 1;
        while (l <= s.length()) {
            int i = 0;
            while (i <= s.length() - l) {
                if (l < 5) {
                    this.dbIndex[i][i + l - 1] = l;
                    this.dbLen[i][i + l - 1] = l;
                    i++;
                    continue;
                }

                int sl;
                if ((sl = find(s, i, i + l - 1)) != 0) {
                    this.dbIndex[i][i + l - 1] = -sl;
                    this.dbLen[i][i + l - 1] = decimalDigit(l / sl) + this.dbLen[i][i + sl - 1] + 2;
                } else {
                    this.dbIndex[i][i + l - 1] = l;
                    this.dbLen[i][i + l - 1] = l;

                    for (int k = i; k < i + l - 1; k++) {
                        if (this.dbLen[i][k] + this.dbLen[k + 1][i + l - 1] < this.dbLen[i][i + l - 1]) {
                            this.dbIndex[i][i + l - 1] = k - i + 1;
                            this.dbLen[i][i + l - 1] = this.dbLen[i][k] + this.dbLen[k + 1][i + l - 1];
                        }
                    }
                }
                i++;
            }
            l++;
        }

        StringBuilder sb = new StringBuilder();


        make(s, sb, this.dbIndex, this.dbLen, 0, s.length() - 1);

        return sb.toString();
    }

    public int decimalDigit(int number) {
        int ans = 0;
        while (number > 0) {
            ans += 1;
            number /= 10;
        }

        return ans;
    }

    public void make(String s, StringBuilder sb, int[][] dbIndex, int[][] dbLen, int start, int end) {
        if (end - start + 1 < 5 || dbIndex[start][end] == end - start + 1) {
            for (int i = start; i <= end; i++) {
                sb.append(s.charAt(i));
            }
            return;
        }

        if (dbIndex[start][end] < 0) {
            int sl = -dbIndex[start][end];
            sb.append((end - start + 1) / sl);
            sb.append('[');
            make(s, sb, dbIndex, dbLen, start, start + sl - 1);
            sb.append(']');
            return;
        }

        int sl = dbIndex[start][end];
        make(s, sb, dbIndex, dbLen, start, start + sl - 1);
        make(s, sb, dbIndex, dbLen, start + sl, end);
    }


    public int find(String s, int start, int end) {
        if (end == start) return 1;

        int[] next = new int[end - start + 1];
        next[0] = -1;

        int i = 2;
        int index = 0;
        while (i < next.length) {
            while (index >= 0 && s.charAt(start + index) != s.charAt(start + i - 1)) index = next[index];

            next[i] = ++index;
            i++;
        }

        i = start + 1;
        int j = start;
        while (i < start + 2 * (end - start + 1) - 1) {
            if (s.charAt(start + (i - start) % (end - start + 1)) == s.charAt(j)) {
                if (j == end) return i - start + 1 - (end - start + 1);
                i++;
                j++;
            } else {
                if (j == start) {
                    i++;
                } else {
                    j = start + next[j - start];
                }
            }
        }

        return 0;
    }

    // 698
    public boolean canPartitionKSubsets(int[] nums, int k) {
        Arrays.sort(nums);
        int len = nums.length;

        int p = 0;
        for (int n: nums) p += n;
        if ((p % k) != 0) return false;
        p /= k;
        if (nums[len - 1] > p) return false;

        int size = 1 << len;
        boolean[] db = new boolean[size];
        int[] csr = new int[size];
        db[0] = true;
        for (int i = 0; i < size; i++) {
            if (!db[i]) continue;

            for (int j = 0; j < len && csr[i] + nums[j] <= p; j++) {
                if ((i & (1 << j)) != 0) continue;
                int next = i + (1 << j);
                db[next] = true;
                csr[next] = (csr[i] + nums[j]) % p;
            }
        }

        return db[size - 1];
    }

    // 476
    public int findComplement(int num) {
        int ans = 0;
        for (int i = 0; i < 32 && num != 0; i++) {
            ans += (((num & 1) ^ 1) << i);
            num >>= 1;
        }
//        new Random().nextDouble();
        return ans;
    }



    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.findComplement(5));

    }
}
