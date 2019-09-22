<template id="todo-overview">
  <div>
    <form id="add-todo" @submit.prevent="add">
      <input v-model="newTodo"/>
      <button type="submit">Add</button>
    </form>
    <ul class="todo-overview-list">
      <li v-for="todo in todos">
        <p v-bind:class="{ 'todo-done': todo.done }">
          <input type="checkbox" :checked="todo.done" @input="toggleDone(todo, $event)">
          {{todo.body}}
        </p>
      </li>
    </ul>
  </div>
</template>
<script>
  Vue.component("todo-overview", {
    template: "#todo-overview",
    data: () => ({
      newTodo: '',
      todos: [],
    }),
    created() {
      this.list();
    },
    methods: {
      toggleDone(item) {
        item.done = !item.done;
        fetch("/api/todo/" + item.id, {
          method: 'POST',
          body: JSON.stringify(item)
        })
          .then(res => this.list())
          .catch(() => alert("Error while fetching todos"));
      },
      list(e) {
        this.newTodo = '';
        fetch("/api/todo")
          .then(res => res.json())
          .then(res => this.todos = res)
          .catch(() => alert("Error while fetching todos"));
      },
      add(e) {
        fetch("/api/todo", {
          method: 'POST',
          body: JSON.stringify({body: this.newTodo})
        })
          .then(res => this.list())
          .catch(() => alert("Error while fetching todos"));
      }
    }
  });
</script>
<style>
  #add-todo {
    width: 100%;
  }
  #add-todo input {
    width : calc(100% - 110px);
  }
  #add-todo button {
    width: 100px;
  }
  ul.todo-overview-list {
    padding: 0;
    list-style: none;
  }
  ul.todo-overview-list p {
    display: block;
    padding: 16px;
    border-bottom: 1px solid #ddd;
  }
  ul.todo-overview-list p.todo-done {
    background-color: #ddd;
  }
</style>
