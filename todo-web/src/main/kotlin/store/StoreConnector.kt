package store

import react.*
import kotlin.reflect.KClass

class StoreConnector<S : Store>(private val store: S) {

    inner class ConnectedComponent : RComponent<ConnectedComponentProps, RState>() {
        private val listener: StoreListener = { update() }

        override fun componentDidMount() {
            props.store.addListener(listener)
        }

        override fun componentWillUnmount() {
            props.store.removeListener(listener)
        }

        private fun update() {
            this.forceUpdate()
        }

        override fun RBuilder.render() {
            val childProps = props.mapFunc(props.store)
            child(props.kClass.js, childProps) {}
        }
    }

    inner class ConnectedComponentProps(
            var store: S,
            var mapFunc: (store: S) -> dynamic,
            var kClass: KClass<dynamic>
    ) : RProps

    fun <P : RProps, C : Component<P, *>> connect(
            receiver: RBuilder,
            c: KClass<C>,
            mapFunc: (store: S) -> P
    ): ReactElement = receiver.child<dynamic, dynamic>(ConnectedComponent::class) {
        attrs.mapFunc = mapFunc
        attrs.kClass = c
        attrs.store = store
    }
}