// private Long id;
//
// private int totalCost;
// private String name;
// private String cat;
// private String email;
// private String description;
// id,totalCost,name,cat,email,description
function getIndex(list, id) {
    for (var i = 0; i < list.length; i++ ) {
        if (list[i].id === id) {
            return i;
        }
    }

    return -1;
}
let customActions = {
    getAll: {method: 'GET', url: '/sale/all'},
    addOne: {method: 'POST', url: '/sale/add'},
    updateOne: {method: 'PUT', url: '/sale/update'},
    getOne: {method: 'GET', url: '/sale/get{/id}'},
    deleteOne: {method: 'DELETE', url: '/sale/delete{/id}'}
}

let saleApi = Vue.resource('/horse/all', {}, customActions);

Vue.component('sale-form', {
    props: ['sales', 'saleAttr'],
    data: function() {
        return {
            name: '',
            id: ''
        }
    },
    watch: {
        saleAttr: function(newVal, oldVal) {
            this.name = newVal.name;
            this.id = newVal.id;
        }
    },
    template:
        '<div>' +
        '<input type="text" placeholder="Enter your name:" v-model="name" />' +
        '<input type="button" value="Save" @click="save" />' +
        '</div>',
    methods: {
        save: function() {
            var sale = { name: this.name };

            if (this.id) {
                saleApi.update({id: this.id}, sale).then(result =>
                    result.json().then(data => {
                        var index = getIndex(this.sales, data.id);
                        this.messages.splice(index, 1, data);
                        this.name = ''
                        this.id = ''
                    })
                )
            } else {
                saleApi.save({}, sale).then(result =>
                    result.json().then(data => {
                        this.sales.push(data);
                        this.name = ''
                    })
                )
            }
        }
    }
});

Vue.component('sale-row', {
    props: ['sale', 'editMethod', 'sales'],
    template: '<div>' +
        '<i>({{ sale.id }})</i> {{ sale.name }}' +
        '<span style="position: absolute; right: 0">' +
        '<input type="button" value="Edit" @click="edit" />' +
        '<input type="button" value="X" @click="del" />' +
        '</span>' +
        '</div>',
    methods: {
        edit: function() {
            this.editMethod(this.sale);
        },
        del: function() {
            messageApi.remove({id: this.sale.id}).then(result => {
                if (result.ok) {
                    this.sales.splice(this.sales.indexOf(this.sale), 1)
                }
            })
        }
    }
});

Vue.component('sale-list', {
    props: ['sales'],
    data: function() {
        return {
            sale: null
        }
    },
    template:
        '<div style="position: relative; width: 300px;">' +
        '<sale-form :sales="sales" :saleAttr="sale" />' +
        '<sale-row v-for="sale in sales" :key="sale.id" :sale="sale" ' +
        ':editMethod="editMethod" :sales="sales" />' +
        '</div>',
    created: function() {
        saleApi.get().then(result =>
            result.json().then(data =>
                data.forEach(sale => this.sales.push(sale))
            )
        )
    },
    methods: {
        editMethod: function(sale) {
            this.sale = sale;
        }
    }
});

var app = new Vue({
    el: '#app',
    template: '<sale-list :sales="sales" />',
    data: {
        sales: []
    }
});