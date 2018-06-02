package store

import TodoStore
import react.RComponent
import react.RProps
import react.RState

interface StoreListener {
    fun update()
}

abstract class RStoreListener<P : RProps, S : RState> : RComponent<P, S>(), StoreListener {
    private val _store = TodoStore
    protected val store get() = _store

    override fun update() {
        this.forceUpdate()
    }

    override fun componentDidMount() {
        _store.addListener(::update)
    }

    override fun componentWillUnmount() {
        _store.removeListener(::update)
    }
}