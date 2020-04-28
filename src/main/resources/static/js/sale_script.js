function getIndex(list, id) {
    for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
            return i;
        }
    }
    return -1;
}

let export_commands = {
    export: {method: 'GET', url: '/export/table'}
}

let nav_export = Vue.resource('/export/table', {}, export_commands);

let customActions = {
    getAll: {method: 'GET', url: '/sale/all'},
    addOne: {method: 'POST', url: '/sale/add'},
    updateOne: {method: 'PUT', url: '/sale/update{/id}'},
    getOne: {method: 'GET', url: '/sale/get{/id}'},
    deleteOne: {method: 'DELETE', url: '/sale/delete{/id}'},
    clear: {method: 'DELETE', url: '/sale/clear'}
};

let saleApi = Vue.resource('/sale/all', {}, customActions);

Vue.component('nav-bar', {
    props: {
        sales: {
            type: Array,
            required: true
        }
    },
    template: `
        <nav>
        <ul>
            <li><input class="btn-change1" type="button" value="Export as PDF" @click="export_pdf"></li>
            <li><input class="btn-change1" type="button" value="Delete all" @click="clear"/></li>
        </ul>
    </nav>`,
    methods: {
        export_pdf() {
            nav_export.export().then(result => {
                if (result.ok) {
                    alert("Export SUCCESS!\n You can find it in project folder by current time.")
                } else {
                    alert("Export Failed!\n Please contact creator for future notice.")
                }
            })
        },
        clear() {
            if (confirm("You really want to delete all entries?")) {
                saleApi.clear().then(result => {
                    if (result.ok) {
                        this.sales.splice(0)
                    }
                })
            }
        }
    }
})

Vue.component('sale-form', {
    props: ['sales', 'saleAttr'],
    data: function () {
        return {
            description: '',
            name: '',
            cat: '',
            totalCost: '',
            email: '',
            id: '',
            errors: []
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
        '<div class="dform">' +
        '<div v-show="this.errors.length >= 1">' +
        '<span style="color: darkred">Correct your input please:</span>' +
        '<ul>' +
        '<li v-for="(error,index) in errors" :key="index"> {{error}}</li>' +
        '</ul>' +
        '</div>' +
        '<input type="text" placeholder="Name:" v-model="name"/>' +
        '<input type="email" placeholder="Email:" v-model="email"/>' +
        '<input type="text" placeholder="Category:" v-model="cat"/>' +
        '<input type="text" placeholder="Price:" v-model="totalCost"/>' +
        '<input type="text" placeholder="Description:" v-model="description"/></br>' +
        '<input class="btn-change1" type="button" value="Save" v-on:click="save"/> ' +
        '</div>',
    methods: {
        save: function () {
            this.errors = [];
            // if (this.name) {
            if (this.name && this.totalCost && (parseInt(this.totalCost).length != this.totalCost.length) && parseInt(this.totalCost)) {
                let sale = {
                    name: this.name,
                    description: this.description,
                    id: this.id,
                    email: this.email,
                    cat: this.cat,
                    totalCost: parseInt(this.totalCost)
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
            } else {
                if (!this.name) this.errors.push("Please add the Name");
                if (this.totalCost && !parseInt(this.totalCost)) this.errors.push("Price should consist of numbers: 0-9");
                if (!this.totalCost) this.errors.push("Please add the price");
                if (!this.email) this.email = 'anonymous';
            }
        }
    }
});

Vue.component('sale-row', {
    props: ['sale', 'editsale', 'sales', 'counter', 'index', 'filtered'],
    template:
        '<tr>' +
        '<td>{{index}}</td>' +
        '<td> {{ sale.name }} </td>' +
        '<td> {{ sale.email }} </td>' +
        '<td> {{ sale.cat }} </td>' +
        '<td> {{ sale.totalCost }}</td>' +
        '<td> {{ sale.description }}</td>' +
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
            if (confirm("Are you sure you want to delete " + this.sale.name + " ?")) {
                saleApi.deleteOne({id: this.sale.id}).then(result => {
                    if (result.ok) {
                        this.sales.splice(this.sales.indexOf(this.sale), 1)
                    }
                })
            }
        }
    }
})

Vue.component('sales-list', {
    props: ['sales', 'filtered'],
    data: function () {
        return {
            sale: null
        };
    },
    template:
        '<div style="width: 50%;v-align: center">' +
        '<sale-form :sales="sales" :saleAttr="sale"/>' +
        '<table id="myTable">' +
        '<tr>' +
        '<td>#</td>' +
        '<td>Name</td>' +
        '<td>Email</td>' +
        '<td>Category</td>' +
        '<td>Price</td>' +
        '<td>Description</td>' +
        '<td style="border: none"></td>' +
        '</tr>' +
        '<sale-row v-for="(sale,index) in filtered" :key="sale.id" :sale="sale" ' +
        ':sales="sales" :filtered="filtered" :editsale="editsale" :index="index+1"/>' +
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
});

var app = new Vue({
    el: '#app',
    template: `
    <div>
        <nav-bar :sales="sales" />
        <input style="position: relative;bottom: 100px; left: 15px" class="form-control" type="text" v-model="n_query" placeholder="Search by Name..." />
        <input style="position: relative;bottom: 100px; left: 15px" class="form-control" type="text" v-model="c_query" placeholder="Search by Category..." />
        <sales-list :sales="sales" :filtered="filtered"/>
    </div>`,
    data: {
        n_query: '',
        c_query: '',
        sales: []
    },
    computed: {
        filtered() {
            if (this.n_query && this.c_query) {
                return this.sales.filter((sale) =>{
                    return sale.name.toLowerCase().includes(this.n_query.toLowerCase()) && sale.cat.toLowerCase().includes(this.c_query.toLowerCase())
                })
            } else if (this.n_query) {
                return this.sales.filter((sale) => {
                    return sale.name.toLowerCase().includes(this.n_query.toLowerCase())
                });
            } else if (this.c_query) {
                return this.sales.filter((sale) => {
                    return sale.cat.toLowerCase().includes(this.c_query.toLowerCase())
                });
            } else {
                return this.sales;
            }
        }
    }
});















