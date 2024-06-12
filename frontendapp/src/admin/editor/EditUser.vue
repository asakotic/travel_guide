<template>
    <div class="new-article-form-container">
        <AdminNav />
        <h2>Edit user</h2>
        <form @submit.prevent="handleSubmit">
            <h2>Edit this user</h2>
            <div class="input-group">
                <label for="firstName">First name:</label>
                <input type="text" id="firstName" v-model="user.firstName" required>
            </div>
            <div class="input-group">
                <label for="lastName">Last Name:</label>
                <input type="text" id="lastName" v-model="user.lastName" required />
            </div>
            <div class="input-group">
                <label for="email">Email:</label>
                <input type="text" id="email" v-model="user.email" required />
            </div>
            <button type="submit">Submit</button>
        </form>
    </div>
</template>
  
<script>
import AdminNav from '../../navigations/AdminNav.vue'

export default {
    data() {
        return {
            destinations: [],
            role: null,
            user: {
                userId: '',
                firstName: '',
                lastName: '',
                email: '',
            },
        };
    },
    components: {
        AdminNav,
    },
    methods: {
        async fetch() {
            this.user = this.$route.params.user;
        },
        handleSubmit() {
            let jwt = localStorage.getItem("jwtic");
            fetch('http://localhost:8080/api/users/editInfo', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + jwt,
                },
                body: JSON.stringify({
                    userId: this.user.userId,
                    firstName: this.user.firstName,
                    lastName: this.user.lastName,
                    email: this.user.email,
                })
            }).then(response => {
                if (!response.ok) {
                    alert("Bad input!");
                    return;
                }
                this.$router.push('/users');
            })
        },
    },
    mounted() {
        this.fetch();
    }
};
</script>
  
<style scoped>
.new-article-form-container {
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

form {
    background: #fff;
    padding: 2rem;
    border-radius: 8px;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
    width: 300px;
}

.input-group {
    margin-bottom: 1.5rem;
}

label {
    display: block;
    margin-bottom: 0.5rem;
    color: #ff69b4;
}

input,
textarea,
select {
    width: 100%;
    padding: 0.5rem;
    border: 1px solid #ff69b4;
    border-radius: 4px;
    margin-bottom: 0.5rem;
}

input:focus,
textarea:focus,
select:focus {
    outline: none;
    border-color: #ff1493;
}

textarea {
    height: 100px;
}

button {
    width: 100%;
    padding: 0.75rem;
    border: none;
    border-radius: 4px;
    background-color: #ff69b4;
    color: white;
    font-size: 1rem;
    cursor: pointer;
}

button:hover {
    background-color: #ff1493;
}
</style>