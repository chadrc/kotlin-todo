package store

open class Store {

    private val listeners: ArrayList<StoreListener> = ArrayList()

    fun addListener(listener: StoreListener) = listeners.add(listener)
    fun removeListener(listener: StoreListener) = listeners.remove(listener)

    protected fun action(func: () -> Unit) {
        func()
        notifyListeners()
    }

    private fun notifyListeners() {
        for (listener in listeners) {
            listener()
        }
    }
}