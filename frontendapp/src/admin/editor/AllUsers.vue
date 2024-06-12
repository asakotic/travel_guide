<template>
    <div class="user-table-container">
        <AdminNav />
        <h2>Users</h2>
        <button @click="addUser" class="action-button edit">Add New User</button>
        <table>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Email</th>
                    <th>User Type</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(user, index) in users" :key="index">
                    <td>{{ user.firstName }} {{ user.lastName }}</td>
                    <td>{{ user.email }}</td>
                    <td>{{ user.userType }}</td>
                    <td>
                        <button @click="editUser(user)" class="action-button edit">Edit</button>
                        <button @click="toggleActivation(user,index)" v-if="user.userType === 'EDITOR'" class="action-button"
                            :class="{ 'deactivate': (user.status === 'DEACTIVATED'), 'activate': (user.status === 'ACTIVATED') }">
                            {{ (user.status === 'ACTIVATED') ? 'Deactivate' : 'Activate' }}
                        </button>
                    </td>
                </tr>
            </tbody>
        </table>
        <div>
            <button @click="changePage(1)" class="action-button edit">Next</button>
            <button @click="changePage(-1)" class="action-button edit">Previous</button>
        </div>
    </div>
</template>

<script>
import AdminNav from '../../navigations/AdminNav.vue'
export default {
    data() {
        return {
            users: [],
        };
    }, 
    components: {
        AdminNav,
    },
    methods: {
        async fetchAllUsers() {
            let jwt = localStorage.getItem("jwtic");
            this.users = await (await fetch('http://localhost:8080/api/users/usersPP/1',
                { headers: { Authorization: "Bearer " + jwt } })).json();
        },
        async changePage(num) {
            let jwt = localStorage.getItem("jwtic");
            this.currPage += num;
            if (this.currPage < 1) this.currPage = 1;
            let tmp = await (await fetch('http://localhost:8080/api/users/usersPP/' + this.currPage,
                { headers: { Authorization: "Bearer " + jwt } })).json();
            if (tmp.length > 0)
                this.destinations = tmp;
            else
                this.currPage -= 1
            console.log(this.currPage)
        },
        addUser(){
            this.$router.push('/newUser');
        },
        editUser(user) {
            this.$router.push({ name: 'editUser', params: { user: user } });
        },
        toggleActivation(user,index) {
            if (user.status === 'DEACTIVATED')
                this.users[index].status = "ACTIVATED"
            else
                this.users[index].status = "DEACTIVATED"
            
             let jwt = localStorage.getItem("jwtic");
            fetch('http://localhost:8080/api/users/editStatus', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + jwt,
                },
                body: JSON.stringify({
                    userId: user.userId,
                    status: user.status,
                })
            }).then(response => {
                if (!response.ok) {
                    alert("Bad input!");
                    if (user.status === 'DEACTIVATED')
                        this.users[index].status = "ACTIVATED"
                    else
                        this.users[index].status = "DEACTIVATED"
                    return;
                }
                
            })
        }
    },
    mounted() {
        this.fetchAllUsers();
    }
};
</script>

<style scoped>
.user-table-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: #ffe6f0;
    padding: 2rem;
    border-radius: 8px;
}

h2 {
    color: #ff69b4;
    margin-bottom: 1.5rem;
}

table {
    width: 100%;
    border-collapse: collapse;
    margin-bottom: 1.5rem;
    margin-top: 1.5rem;
}

th,
td {
    border: 1px solid #ff69b4;
    padding: 0.75rem;
    text-align: left;
}

th {
    background-color: #ff69b4;
    color: white;
}

td {
    background-color: #fff;
    color: #ff69b4;
}

.action-button {
    margin-right: 0.5rem;
    padding: 0.5rem 1rem;
    border: none;
    border-radius: 4px;
    cursor: pointer;
    font-size: 1rem;
    color: white;
}


.edit {
    background-color: #ff69b4;
}

.edit:hover {
    background-color: #ff1493;
}

.activate {
    background-color: #32CD32;
    /* LimeGreen */
}

.activate:hover {
    background-color: #228B22;
    /* ForestGreen */
}

.deactivate {
    background-color: #ff6347;
    /* Tomato */
}

.deactivate:hover {
    background-color: #ff4500;
    /* OrangeRed */
}
</style>
