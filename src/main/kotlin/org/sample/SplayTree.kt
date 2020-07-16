package org.sample

class SplayTree(var root: Node?) {

    fun find(value: Int): Node? {
        if (root == null) return null

        var found = root
        var current = root
        while (current != null) {
            found = current
            if (current.key > value) {
                current = current.left
            } else if (current.key < value) {
                current = current.right
            } else {
                break
            }
        }
        splay(found!!)
        root = found
        return found
    }

    fun add(value: Int) {
        val node = Node(value)

        if (root == null) {
            root = node
            return
        }

        val trees = split(value)

        node.left = trees.first
        node.right = trees.second
        trees.first?.parent = node
        trees.second?.parent = node

        root = node
    }

    fun remove(value: Int) {
        val found = find(value)
        root = merge(found?.left, found?.right)
    }

    private fun merge(tree1: Node?, tree2: Node?): Node? {
        if (tree1 == null) return tree2
        if (tree2 == null) return tree1

        var r = tree1
        while (r?.right != null) {
            r = r.right!!
        }
        splay(r!!)
        r.right = tree2
        tree2.parent = r
        return r
    }

    private fun split(x: Int): Pair<Node?, Node?> {
        val found = find(x)
        return if (found!!.key > x) {
            val l = found.left
            l?.parent = null
            found.left = null
            Pair(l, found)
        } else {
            val r = found.right
            r?.parent = null
            found.right = null
            Pair(found, r)
        }
    }

    private fun p(v: Node): Node? {
        return v.parent
    }

    private fun g(v: Node): Node? {
        v.parent?.let { par -> return par.parent }
        return null
    }

    private fun splay(v: Node) {
        while (p(v) != null) {
            if (v == p(v)!!.left) {
                when {
                    g(v) == null -> {
                        rotate_right(p(v)!!)
                    }
                    p(v) == g(v)!!.left -> {
                        rotate_right(g(v)!!)
                        rotate_right(p(v)!!)
                    }
                    else -> {
                        rotate_right(p(v)!!)
                        rotate_left(p(v)!!)
                    }
                }
            } else {
                when {
                    g(v) == null -> {
                        rotate_left(p(v)!!)
                    }
                    p(v) == g(v)!!.right -> {
                        rotate_left(g(v)!!)
                        rotate_left(p(v)!!)
                    }
                    else -> {
                        rotate_left(p(v)!!)
                        rotate_right(p(v)!!)
                    }
                }
            }
        }
    }

    private fun rotate_left(v: Node) {
        val p = p(v)
        val r = v.right
        p?.let {
            if (it.left == v) {
                it.left = r
            } else {
                it.right = r
            }
        }
        val tmp = r!!.left
        r.left = v
        v.right = tmp
        v.parent = r
        r.parent = p
        v.right?.let {
            it.parent = v
        }
    }

    private fun rotate_right(v: Node) {
        val p = p(v)
        val l = v.left
        p?.let {
            if (it.left == v) {
                it.left = l
            } else {
                it.right = l
            }
        }
        val tmp = l!!.right
        l.right = v
        v.left = tmp
        v.parent = l
        l.parent = p
        v.left?.let {
            it.parent = v
        }
    }
}