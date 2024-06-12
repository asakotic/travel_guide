<template>
    <div class="new-article-form-container">
        <AdminNav v-if="role === 'ADMIN'" />
        <EditorNav v-else />
        <h2>Edit article</h2>
        <form @submit.prevent="handleSubmit">
            <h2>Edit this destination</h2>
            <div class="input-group">
                <label for="name">Name:</label>
                <input type="text" id="name" v-model="destination.name" required>
            </div>
            <div class="input-group">
                <label for="description">Description:</label>
                <textarea type="text" id="description" v-model="destination.description" required></textarea>
            </div>
            <button type="submit">Submit</button>
        </form>
    </div>
</template>
  
<script>
import AdminNav from '../../navigations/AdminNav.vue'
import EditorNav from '../../navigations/EditorNav.vue'
import { parseJwt } from '@/util';

export default {
    data() {
        return {
            role: null,
            destination: {
                name: '',
                description: '',
            },
            oldName: '',
        };
    },
    components: {
        AdminNav,
        EditorNav,
    },
    methods: {
        async fetch() {
            let jwt = localStorage.getItem("jwtic");
            this.role = parseJwt(jwt).type;
            this.destination = this.$route.params.destination;
            this.oldName = this.destination.name;
        },
        handleSubmit() {
            let jwt = localStorage.getItem("jwtic");
            fetch('http://localhost:8080/api/destinations/edit', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + jwt,
                },
                body: JSON.stringify({
                    oldName: this.oldName,
                    newName: this.destination.name,
                    description: this.destination.description,
                })
            }).then(response => {
                if (!response.ok) {
                    alert("Bad input!");
                    return;
                }
                return response.json()
            }).then(res => {
                if (!res.ok) {
                    alert("vec postoji to ime, sry");
                    return;
                }
                this.$router.push('/destinations');
            });
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