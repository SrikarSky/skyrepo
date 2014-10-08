// Load the application once the DOM is ready, using `jQuery.ready`:
$(function(){
 
  var Todo = Backbone.Model.extend({
	  
    defaults: function() {
      return {
        address: "New York, NY",
        locNickName: "My Pad",
        order: Todos.nextOrder(),
        //done: false
      };
    },

    // Ensure that each todo created has `title`.
    initialize: function() {
      if (!this.get("address")) {
        this.set({"address": this.defaults().address});
      }
      if (!this.get("locNickName")) {
        this.set({"locNickName": this.defaults().locNickName});
      }
      this.set({"id": this.get("tingId")});
    },

    // Toggle the `done` state of this todo item.
    toggle: function() {
      //this.save({done: !this.get("done")});
    }

  });

  // Todo Collection
  // ---------------

  // The collection of todos is backed by *localStorage* instead of a remote
  // server.
  var TodoList = Backbone.Collection.extend({
	  
    // Reference to this collection's model.
    model: Todo,
    url:'/processTing',   
    // Save all of the todo items under the `"todos-backbone"` namespace.
    //localStorage: new Backbone.LocalStorage("todos-backbone"),

    // Filter down the list of all todo items that are finished.
    /*
    done: function() {
      return this.filter(function(todo){ return todo.get('done'); });
    },
    */
    // Filter down the list to only todo items that are still not finished.
    /*
    remaining: function() {
      return this.without.apply(this, this.done());
    },
    */
    // We keep the Todos in sequential order, despite being saved by unordered
    // GUID in the database. This generates the next order number for new items.
    nextOrder: function() {
      if (!this.length) return 1;
      return this.last().get('order') + 1;
    },

    // Todos are sorted by their original insertion order.
    comparator: function(todo) {
      return todo.get('order');
    },
    
    parse: function (response) {
    	   return response.data.dots;
    	}

  });

  // Create our global collection of **Todos**.
  var Todos = new TodoList;

  // Todo Item View
  // --------------

  // The DOM element for a todo item...
  var TodoView = Backbone.View.extend({

    //... is a list tag.
    tagName:  "li",

    // Cache the template function for a single item.
    template: _.template($('#item-template').html()),

    // The DOM events specific to an item.
    events: {
      "click .toggle"   : "toggleDone",
      "dblclick .view"  : "edit",
      "click a.destroy" : "clear",
      "touchstart a.destroy"      : "clear",
      "keypress .edit"  : "updateOnEnter",
      "blur .edit"      : "close",
    	  "click"           : "focusMap",
      "touchstart"      : "focusMap", 
    },

    // The TodoView listens for changes to its model, re-rendering. Since there's
    // a one-to-one correspondence between a **Todo** and a **TodoView** in this
    // app, we set a direct reference on the model for convenience.
    initialize: function() {
      this.listenTo(this.model, 'change', this.render);
      this.listenTo(this.model, 'destroy', this.remove);
    },

    // Re-render the titles of the todo item.
    render: function() {
    	  //alert(this.template(this.model.toJSON()));
    	  //Needs its own view - where you update
    	  //getCode(addr);
      this.$el.html(this.template(this.model.toJSON()));
      //this.$el.toggleClass('done', this.model.get('done'));
      this.input = this.$('.edit');
      return this;
    },

    // Toggle the `"done"` state of the model.
    toggleDone: function() {
      this.model.toggle();
    },
    
    // Focus the Google Map
    focusMap: function() {
    		geoCode(this.model.get("address"));
    		return true;
    },
    
    
    // Switch this view into `"editing"` mode, displaying the input field.
    edit: function() {
    	  //alert("In single edit");
      this.$el.addClass("editing");
      this.input.focus();
    },

    // Close the `"editing"` mode, saving changes to the todo.
    close: function() {
      //alert("Closing now"); 
      //alert(this.$el.html()); 
      var value = this.input.val();
      //alert(value);
      
      if (!value) {
        this.clear();
      } else {

        this.model.save({address: value});
        this.$el.removeClass("editing");
      }
    },

    // If you hit `enter`, we're through editing the item.
    updateOnEnter: function(e) {
      if (e.keyCode == 13) this.close();
    },

    // Remove the item, destroy the model.
    clear: function() {
      //alert("Destroying");		
      this.model.destroy();
      return false; 
    }

  });

  // The Application
  // ---------------

  // Our overall **AppView** is the top-level piece of UI.
  var AppView = Backbone.View.extend({

    // Instead of generating a new element, bind to the existing skeleton of
    // the App already present in the HTML.
    el: $("#todoapp"),

    // Our template for the line of statistics at the bottom of the app.
    statsTemplate: _.template($('#stats-template').html()),

    // Delegated events for creating new items, and clearing completed ones.
    events: {
      "keypress #new-addr":  "createOnEnter",
      "click #clear-completed": "clearCompleted",
      "click .button"   : "textHide", 
      "click #toggle-all": "toggleAllComplete"
    },

    // At initialization we bind to the relevant events on the `Todos`
    // collection, when items are added or changed. Kick things off by
    // loading any preexisting todos that might be saved in *localStorage*.
    initialize: function() {

      this.input = this.$("#new-addr");
      this.nick = this.$("#new-nick");
      this.allCheckbox = this.$("#toggle-all")[0];

      this.listenTo(Todos, 'add', this.addOne);
      this.listenTo(Todos, 'reset', this.addAll);
      this.listenTo(Todos, 'all', this.render);

      this.footer = this.$('footer');
      this.main = $('#main');
      
      this.exp = 1; 

      Todos.fetch();
    },

    // Re-rendering the App just means refreshing the statistics -- the rest
    // of the app doesn't change.
    render: function() {
      //var done = Todos.done().length;
      //var remaining = Todos.remaining().length;

      if (Todos.length) {
        this.main.show();
        this.footer.show();
        //this.footer.html(this.statsTemplate({done: done, remaining: remaining}));
      } else {
        this.main.hide();
        this.footer.hide();
      }

      //this.allCheckbox.checked = !1;
    },

    // Add a single todo item to the list by creating a view for it, and
    // appending its element to the `<ul>`.
    addOne: function(todo) {
      var view = new TodoView({model: todo});
      this.$("#todo-list").append(view.render().el);
    },

    // Toggle the text section 
    textHide: function() {
    	  this.exp = !(this.exp);
    	  if (this.exp == 0) {
    		  this.$("#main").hide(); 
    		  this.$("#B").html("+");
    	  }
    	  if (this.exp == 1) {
    		  this.$("#main").show();  
    		  this.$("#B").html("-");
    	  }
    },

    // Add all items in the **Todos** collection at once.
    addAll: function() {
      Todos.each(this.addOne, this);
    },

    // If you hit return in the main input field, create new **Todo** (One Dot)  model,
    // Sending it to the server 
    createOnEnter: function(e) {
      if (e.keyCode != 13) return;
      if (!this.input.val()) return;
      addr = this.input.val();
      geoCode(addr);
      Todos.create({address: this.input.val(), locNickName: this.nick.val()});
      
      this.input.val('');
      this.nick.val('');
    },

    // Clear all done todo items, destroying their models.
    clearCompleted: function() {
    	  //alert("Clear ALl");
      _.invoke(Todos.done(), 'destroy');
      return false;
    },

    toggleAllComplete: function () {
      var done = this.allCheckbox.checked;
      //alert("Take care of all");
      //Todos.each(function (todo) { todo.save({'done': done}); });
    }

  });

  // Finally, we kick things off by creating the **App**.
  var App = new AppView;

});
