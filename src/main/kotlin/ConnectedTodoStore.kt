import react.*
import store.*
import kotlin.reflect.KClass

val todoStore = TodoStore()

class ConnectedComponent : RComponent<ConnectedComponentProps, RState>() {
    private val listener: StoreListener = { update() }

    init {
        todoStore.addListener(listener)
    }

    override fun componentWillUnmount() {
        super.componentWillUnmount()
        todoStore.removeListener(listener)
    }

    private fun update() {
        this.forceUpdate()
    }

    override fun RBuilder.render() {
        val childProps = props.mapFunc(todoStore)
        child(props.kClass.js, childProps) {}
    }
}

typealias MapFunction<P> = (store: TodoStore) -> P

class ConnectedComponentProps(var mapFunc: MapFunction<dynamic>, var kClass: KClass<dynamic>): RProps

fun <P: RProps, T: Component<P, *>> RBuilder.connect(c: KClass<T>, mapFunc: MapFunction<P>): ReactElement = child(ConnectedComponent::class) {
    attrs.mapFunc = mapFunc
    attrs.kClass = c
}