<template>
    <div class="destination-table-container">
        <UserNav />
        <h2>Destinations</h2>
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Description</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(destination, index) in destinations" :key="index">
                    <td @click="goToArticles(destination.name)">{{ destination.name }}</td>
                    <td>{{ destination.description }}</td>
                </tr>
            </tbody>
        </table>
        <div>
            <button @click="changePage(1)">Next</button>
            <button @click="changePage(-1)">Previous</button>
        </div>
    </div>
</template>
  
<script>
import UserNav from '../navigations/UserNav.vue'

export default {
    data() {
        return {
            currPage: 1,
            destinations: [],

            form: {
                name: '',
                description: ''
            },
            showAddForm: false,
            isEditing: false,
            editingIndex: null
        };
    },
    components: {
        UserNav,
    },
    methods: {
        goToArticles(destinationName) {
            this.$router.push({ name: 'articlesUser', params: { destination: destinationName } });
        },
        async fetchAllDestinations() {
            this.destinations = await (await fetch('http://localhost:8080/api/destinations/destinationsPP/1')).json();
            console.log(this.destinations);
        },
        async changePage(num) {
            this.currPage += num;
            if (this.currPage < 1) this.currPage = 1;
            let tmp = await (await fetch('http://localhost:8080/api/destinations/destinationsPP/' + this.currPage)).json();
            if (tmp.length > 0)
                this.destinations = tmp;
            else
                this.currPage -= 1
            console.log(this.currPage)
        },
    },
    mounted() {
        this.fetchAllDestinations();
    }

};
</script>
  
<style scoped>
.destination-table-container {
    margin: 2rem;
}

h2 {
    color: #ff69b4;
}

.add-button {
    background-color: #ff69b4;
    color: white;
    padding: 0.5rem 1rem;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    margin-bottom: 1rem;
}

table {
    width: 100%;
    border-collapse: collapse;
}

th,
td {
    border: 1px solid #ddd;
    padding: 0.5rem;
    text-align: left;
}

th {
    background-color: #ff69b4;
    color: white;
}

button {
    margin-right: 0.5rem;
    background-color: #ff69b4;
    color: white;
    padding: 0.25rem 0.5rem;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}

button:hover {
    background-color: #ff1493;
}

.form-container {
    margin-top: 1rem;
    padding: 1rem;
    border: 1px solid #ddd;
    border-radius: 8px;
    background-color: #ffe6f0;
}

.input-group {
    margin-bottom: 1rem;
}

label {
    display: block;
    margin-bottom: 0.5rem;
    color: #ff69b4;
}

input {
    width: 100%;
    padding: 0.5rem;
    border: 1px solid #ff69b4;
    border-radius: 4px;
}
</style>