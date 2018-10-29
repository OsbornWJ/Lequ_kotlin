@file:Suppress("PackageName")

package com.example.lequ_core.net.cache

import java.util.*


/**
 * ================================================
 * LRU 即 Least Recently Used, 最近最少使用, 也就是说, 当缓存满了, 会优先淘汰那些最近最不常访问的数据
 * 此种缓存策略为框架默认提供, 可自行实现其他缓存策略, 如磁盘缓存
 */
class LruCache<K, V>(private var initialMaxSize: Int)  : Cache<K, V> {

    var maxSize: Int = 0

    init {
        maxSize = initialMaxSize
    }

    private val cache = LinkedHashMap<K, V>(100, 0.75f, true)
    private var currentSize = 0

    /**
     * 返回每个 `item` 所占用的 size,默认为1,这个 size 的单位必须和构造函数所传入的 size 一致
     * 子类可以重写这个方法以适应不同的单位,比如说 bytes
     *
     * @param item 每个 `item` 所占用的 size
     * @return 单个 item 的 `size`
     */
    private fun getItemSize(item: V): Int {
        return 1
    }

    /**
     * 当缓存中有被驱逐的条目时,会回调此方法,默认空实现,子类可以重写这个方法
     *
     * @param key   被驱逐条目的 `key`
     * @param value 被驱逐条目的 `value`
     */
    private fun onItemEvicted(key: K, value: V) {
        // optional override
    }

    /**
     * 设置一个系数应用于当时构造函数中所传入的 size, 从而得到一个新的 [.maxSize]
     * 并会立即调用 [.evict] 开始清除满足条件的条目
     *
     * @param multiplier 系数
     */
    @Synchronized
    fun setSizeMultiplier(multiplier: Float) {
        if (multiplier < 0) {
            throw IllegalArgumentException("Multiplier must be >= 0")
        }
        maxSize = Math.round(initialMaxSize * multiplier)
        evict()
    }

    /**
     * 当缓存中已占用的总 size 大于所能允许的最大 size ,会使用  [.trimToSize] 开始清除满足条件的条目
     */
    private fun evict() {
        trimToSize(maxSize)
    }

    /**
     * 当指定的 size 小于当前缓存已占用的总 size 时,会开始清除缓存中最近最少使用的条目
     *
     * @param size `size`
     */
    @Synchronized protected fun trimToSize(size: Int) {
        var last: MutableMap.MutableEntry<K, V>
        while (currentSize > size) {
            last = cache.entries.iterator().next()
            val toRemove = last.value
            currentSize -= getItemSize(toRemove)
            val key = last.key
            cache.remove(key)
            onItemEvicted(key, toRemove)
        }
    }

    override fun size(): Int {
        return currentSize
    }

    override fun get(key: K): V? {
        return cache[key]
    }

    override fun put(key: K, value: V): V? {
        val itemSize = getItemSize(value)
        if (itemSize >= maxSize) {
            onItemEvicted(key, value)
            return null
        }
        val result = cache.put(key, value)
        if (value != null) {
            currentSize += getItemSize(value)
        }
        if (result != null) {
            currentSize -= getItemSize(result)
        }
        evict()
        return result
    }

    override fun remove(key: K): V? {
        val value = cache.remove(key)
        if (value != null) {
            currentSize -= getItemSize(value)
        }
        return value
    }

    override fun containsKey(key: K): Boolean {
        return cache.containsKey(key)
    }

    override fun keySet(): Set<K> {
        return cache.keys
    }

    override fun clear() {
        trimToSize(0)
    }

}

