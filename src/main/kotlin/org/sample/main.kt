package org.sample

fun main(args: Array<String>) {
    var tree = SplayTree(Node(5))
    tree.add(6)
    tree.add(4)
    print(tree.root?.key)
}