package store

open class Store {

    private val listeners: ArrayList<StoreListenerCallback> = ArrayList()

    fun addListener(listenerCallback: StoreListenerCallback) = listeners.add(listenerCallback)
    fun removeListener(listenerCallback: StoreListenerCallback) = listeners.remove(listenerCallback)

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