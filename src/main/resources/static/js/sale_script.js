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
    template:
    `<div class="controlls">
        <input class="custom_button" type="button" value="Export PDF" @click="export_pdf">
        <input class="custom_button" type="button" value="Delete all" @click="clear"/>
    </div>`
    ,
    methods: {
        export_pdf() {
            alert("This feature currently available only for local version.")
            // nav_export.export().then(result => {
            //     if (result.ok) {
            //         alert("Export SUCCESS!\n You can find it in 'Exported' folder by current time stamp.")
            //     } else {
            //         alert("Export Failed!\n Please contact administrator for help.")
            //     }
            // })
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
            completed: false,
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
            this.completed = Boolean(newVal.completed);
            this.id = newVal.id;
        }
    },
    template:
        '<div id="navbar" class="input_form">' +
        '<div style="padding-left: 10px;border: 3px double red;" v-show="this.errors.length >= 1">' +
        '<h4>Correct your input please:</h4>' +
        '<ul>' +
        '<li v-for="(error,index) in errors" :key="index"> {{error}}</li>' +
        '</ul>' +
        '</div>' +
        '<input type="text" placeholder="Name:" v-model="name"/>' +
        '<input type="email" placeholder="Email:" v-model="email"/>' +
        '<input type="text" placeholder="Category:" v-model="cat"/>' +
        '<input type="text" placeholder="Price:" v-model="totalCost"/>' +
        '<input type="text" size="50" placeholder="Description:" v-model="description"/></br>' +
        '<input type="checkbox" id="checkbox" v-model="completed"/><label for="checkbox">Is Paid</label> ' +
        '<input class="custom_button" type="button" value="Save" v-on:click="save"/> ' +
        '</div>',
    methods: {
        save: function () {
            this.errors = [];
            if (this.name && this.totalCost && parseInt(this.totalCost)) {
                let sale = {
                    name: this.name,
                    description: this.description,
                    id: this.id,
                    email: this.email,
                    cat: this.cat,
                    totalCost: parseInt(this.totalCost),
                    completed: Boolean(this.completed),
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
                            this.completed = false;
                        }))
                } else {
                    saleApi.addOne({}, sale).then(result =>
                        result.json().then(data => {
                            this.sales.push(data);
                            this.name = '';
                            this.description = '';
                            this.cat = '';
                            this.email = '';
                            this.completed = false;
                            this.totalCost = '';
                        })
                    )
                }
            } else {
                if (!this.name) this.errors.push("Please add the Name");
                if (this.totalCost && !parseInt(this.totalCost)) this.errors.push("Price should consist of numbers: 0-9");
                if (!this.totalCost) this.errors.push("Please add the price");
            }
        }
    }
});

Vue.component('sale-row', {
    props: ['sale', 'editsale', 'sales', 'counter', 'index', 'filtered'],
    template:
        `
        <tr v-bind:style="!sale.completed ? 'background-color: inherited;' : 'background-color: #80ff80;'">
        <td style="text-align: center;border-right: 1px solid #31708f;">{{index}}</td>
        <td>{{ sale.name }}</td>
        <td>{{ sale.email }}</td>
        <td>{{ sale.cat }}</td>
        <td style="text-align:center">{{ sale.totalCost }} €</td>
        <td>{{ sale.description }}</td>
        <td class="hard_buttons">
        <input class="edit_button" type="button" value="Edit" v-on:click="edit">
        <input class="delete_button" type="button" value="X" v-on:click="del">
        </td>
        </tr>`,
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
        '<div class="output_table">' +
        '<sale-form :sales="sales" :saleAttr="sale"/>' +
        '<table>' +
        '<tr style="background-color: #f2f2f2">' +
        '<td style="width: 10px;text-align: center;border-right: 1px solid #31708f;">#</td>' +
        '<td style="width: 170px;border-right: 1px solid #31708f">Name</td>' +
        '<td style="width: 270px;border-right: 1px solid #31708f">Email</td>' +
        '<td style="width: 137px;text-align:center;border-right: 1px solid #31708f">Category</td>' +
        '<td style="border-right: 1px solid #31708f">Price</td>' +
        '<td style="">Description</td>' +
        '<td style="border: none"> </td>' +
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
        <input class="name_filter" type="text" v-model="n_query" placeholder="Filter by Name..." />
        <input class="category_filter" type="text" v-model="c_query" placeholder="Filter by Category..." />
        <nav-bar :sales="sales" />
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















