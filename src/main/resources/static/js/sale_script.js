// private Long id;
//
// private int totalCost;
// private String name;
// private String cat;
// private String email;
// private String description;
// id,totalCost,name,cat,email,description
function getIndex(list, id) {
    for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }

    return -1;
}

let customActions = {
    getAll: {method: 'GET', url: '/sale/all'},
    addOne: {method: 'POST', url: '/sale/add'},
    updateOne: {method: 'PUT', url: '/sale/update{/id}'},
    getOne: {method: 'GET', url: '/sale/get{/id}'},
    deleteOne: {method: 'DELETE', url: '/sale/delete{/id}'}
}

let saleApi = Vue.resource('/sale/all', {}, customActions);

Vue.component('sale-form', {
    props: ['sales', 'saleAttr'],
    data: function () {
        return {
            description: '',
            name: '',
            cat: '',
            totalCost: '',
            email: '',
            id: ''
        }
    },
    watch: {
        saleAttr: function (newVal, oldVal) {
            this.name = newVal.name;
            this.email = newVal.email;
            this.cat = newVal.cat;
            this.totalCost = newVal.totalCost;
            this.description = newVal.description;
            this.id = newVal.id;
        }
    },
    template:
        '<div style="border: 1px lightgray solid; margin: 10px; padding: 5px">' +
        '<input type="text" placeholder="Name:" v-model="name"/>' +
        '<input type="text" placeholder="Description:" v-model="description"/>' +
        '<input type="email" placeholder="Email:" v-model="email"/>' +
        '<input type="text" placeholder="Category:" v-model="cat"/>' +
        '<input type="text" placeholder="Price:" v-model="totalCost"/>' +
        '<input type="button" value="Save" v-on:click="save"/> ' +
        '</div>',
    methods: {
        save: function () {
            let sale = {
                name: this.name,
                description: this.description,
                id: this.id,
                email: this.email,
                cat: this.cat,
                totalCost: this.totalCost
            };

            if (this.id) {
                saleApi.updateOne({id: this.id}, sale).then(result =>
                    result.json().then(data => {
                        let index = getIndex(this.sales, data.id);
                        this.sales.splice(index, 1, data);
                        this.id = '';
                        this.name = '';
                        this.description = '';
                        this.cat = '';
                        this.email = '';
                        this.totalCost = '';
                    }))
            } else {
                saleApi.addOne({}, sale).then(result =>
                    result.json().then(data => {
                        this.sales.push(data);
                        this.name = '';
                        this.description = '';
                        this.cat = '';
                        this.email = '';
                        this.totalCost = '';
                    })
                )
            }
        }
    }
})

Vue.component('sale-row', {
    props: ['sale', 'editsale', 'sales'],
    template:
        '<tr>' +
        '<td><i>{{ sale.id }}</i></td>' +
        '<td> {{ sale.name }} </td>' +
        '<td> {{ sale.description }} </td>' +
        '<td> {{ sale.cat }} </td>' +
        '<td> {{ sale.email }}</td>' +
        '<td> {{ sale.totalCost }}</td>' +
        '<td style="border-bottom: 1px gray solid; border-radius: 10%">' +
        '<span style="position: absolute; right: 0">' +
        '<input type="button" value="Edit" v-on:click="edit">' +
        '<input type="button" value="X" v-on:click="del">' +
        '</span>' +
        '</td>' +
        '</tr>',
    methods: {
        edit: function () {
            this.editsale(this.sale)
        },
        del: function () {
            saleApi.deleteOne({id: this.sale.id}).then(result => {
                if (result.ok) {
                    this.sales.splice(this.sales.indexOf(this.sale), 1)
                }
            })
        }
    }
})

Vue.component('sales-list', {
    props: ['sales'],
    data: function () {
        return {
            sale: null
        };
    },
    template:
        '<div style="width: 50%;v-align: center">' +
        '<sale-form :sales="sales" :saleAttr="sale"/>' +
        '<table>' +
        '<tr>' +
        '<td>Id:</td>' +
        '<td>Name:</td>' +
        '<td>Description:</td>' +
        '<td>Price:</td>' +
        '<td>Category:</td>' +
        '<td>Email:</td>' +
        '<td style="border: none"></td>' +
        '</tr>' +
        '<sale-row v-for="sale in sales" :key="sale.id" :sale="sale" ' +
        ':sales="sales" :editsale="editsale"/>' +
        '</table>' +
        '</div>',
    created: function () {
        saleApi.get().then(result =>
            result.json().then(data =>
                data.forEach(sale => this.sales.push(sale))
            )
        )
    },
    methods: {
        editsale: function (sale) {
            this.sale = sale
        }
    }
})

var app = new Vue({
    el: '#app',
    template: '<sales-list :sales="sales"/>',
    data: {
        sales: []
    }
})