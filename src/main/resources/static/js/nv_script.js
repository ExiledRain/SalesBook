let export_commands = {
    export: {method: 'GET', url: '/export/table'}
}

let nav_export = Vue.resource('/export/table', {},export_commands);

let nav = new Vue({
    el: '#nv',
    template: `
    <nav>
        <ul>
            <li><input class="btn-change1" type="button" value="Export as PDF" @click="export_pdf"></li>
            <li><input class="btn-change1" type="button" value="Delete all" @click="alert('hi')"/></li>
        </ul>
    </nav>
    `,
    methods: {
        export_pdf() {
            if (confirm("Are you want to create PDF file?")) {
                nav_export.export().then(result => {
                    if (result.ok) {
                        alert("Export SUCCESS!\n You can find it in project folder by current time.")
                    } else {
                        alert("Export Failed!\n Please contact creator for future notice.")
                    }
                })
            }
        }
    }
})













