package com.mengtu.letcode.string;

import com.mengtu.letcode.tree.TreeNode;

/**
 * 另一棵树的子树
 */
public class _572_SubTree {
    public boolean isSubtree(TreeNode s, TreeNode t){

        return false;
    }

    /**
     * 需用后续遍历的方式进行序列化
     * @param root
     * @return
     */
    private String serialize(TreeNode root){
        if (root == null) return "";
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }

    private void serialize(TreeNode node,StringBuilder sb){
        if (node.left == null){
            sb.append("#!");
        }else {
            serialize(node.left,sb);
        }
        if (node.right == null){
            sb.append("#!");
        }else {
            serialize(node.right,sb);
        }
        sb.append(node.val).append("!");
    }
}
