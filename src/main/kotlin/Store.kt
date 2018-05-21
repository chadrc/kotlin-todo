import react.*
import kotlin.reflect.KClass

typealias StoreListener = () -> Unit

class Store {
    var todoCollections: ArrayList<TodoCollection> = ArrayList()
    var selectedTodoCollectionIndex: Int = -1

    private val listeners: ArrayList<StoreListener> = ArrayList()

    init {
        todoCollections = ArrayList()
        val startingTodos: ArrayList<Todo> = ArrayList()

        startingTodos.add(Todo("Make Coffee"))
        startingTodos.add(Todo("Eat Breakfast"))
        startingTodos.add(Todo("Pack Lunch"))

        todoCollections.add(TodoCollection("Morning Routine", startingTodos))
    }

    val selectedTodoCollection: TodoCollection?
        get() = if (selectedTodoCollectionIndex == -1) null else todoCollections[selectedTodoCollectionIndex]

    fun addListener(listener: StoreListener) = listeners.add(listener)
    fun removeListener(listener: StoreListener) = listeners.remove(listener)

    fun selectTodoCollection(index: Int) = action({
        selectedTodoCollectionIndex = index
    })

    fun toggleComplete(index: Int) = action({
        val collection = selectedTodoCollection
        if (collection != null) {
            val todo = collection.todos[index]
            todo.completed = !todo.completed
        }
    })

    private fun notifyListeners() {
        for (listener in listeners) {
            listener()
        }
    }

    private fun action(func: () -> Unit) {
        func()
        notifyListeners()
    }
}

val store: Store = Store()

class ConnectedComponent : RComponent<ConnectedComponentProps, RState>() {
    private val listener: StoreListener = { update() }

    init {
        store.addListener(listener)
    }

    override fun componentWillUnmount() {
        super.componentWillUnmount()
        store.removeListener(listener)
    }

    private fun update() {
        this.forceUpdate()
    }

    override fun RBuilder.render() {
        val childProps = props.mapFunc(store)
        child(props.kClass.js, childProps) {}
    }
}

typealias MapFunction<P> = (store: Store) -> P

class ConnectedComponentProps(var mapFunc: MapFunction<dynamic>, var kClass: KClass<dynamic>): RProps

fun <P: RProps, T: Component<P, *>> RBuilder.connect(c: KClass<T>, mapFunc: MapFunction<P>): ReactElement = child(ConnectedComponent::class) {
    attrs.mapFunc = mapFunc
    attrs.kClass = c
}