<template>
    <div class="new-article-form-container">
        <AdminNav />
        <h2>Your new user!</h2>
        <form @submit.prevent="handleSubmit">
            <h2>New User</h2>
            <div class="input-group">
                <label for="name">First Name:</label>
                <input type="text" id="firstName" v-model="firstName" required>
            </div>
            <div class="input-group">
                <label for="lastName">Enter last name:</label>
                <input type="text" id="lastName" v-model="lastName" required />
            </div>
            <div class="input-group">
                <label for="email">Enter email:</label>
                <input type="text" id="email" v-model="email" required />
            </div>
            <div class="input-group">
                <label for="password">Enter password:</label>
                <input type="text" id="password" v-model="password" required />
            </div>
            <div class="input-group">
                <label for="passworda">Enter password again:</label>
                <input type="text" id="passworda" v-model="passworda" required />
            </div>
            <div class="input-group">
                <select name="userType" id="userType" v-model="userType" required>
                    <option value="ADMIN"> admin</option>
                    <option value="EDITOR"> editor</option>
                </select>
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
            firstName: '',
            lastName: '',
            email: '',
            userType: '',
            password: '',
            passworda: '',
        };
    },
    components: {
        AdminNav,
    },
    methods: {
        selectButton(button) {
            this.selectedButton = button;
        },
        handleSubmit() {
            let jwt = localStorage.getItem("jwtic");
            if (this.password !== this.passworda) {
                alert("Passwords aren't matched")
                return;
            }
            fetch('http://localhost:8080/api/users/addNew', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + jwt,
                },
                body: JSON.stringify({
                    firstName: this.firstName,
                    lastName: this.lastName,
                    email: this.email,
                    userType: this.userType,
                    password: this.password,
                })
            }).then(response => {
                if (!response.ok) {
                    alert("Bad input!");
                    return;
                }
                return response.json();
            })
            .then(res => {
                    if (!res.ok) {
                        alert("That email already exists!");
                        return;
                    }
                    this.$router.push('/users');
                })
        },
    },
    mounted() {
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