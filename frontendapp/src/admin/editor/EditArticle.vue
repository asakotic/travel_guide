<template>
    <div class="new-article-form-container">
        <AdminNav v-if="role === 'ADMIN'" />
        <EditorNav v-else />
        <h2>Edit article</h2>
        <form @submit.prevent="handleSubmit">
            <h2>Edit this article</h2>
            <div class="input-group">
                <label for="title">Title:</label>
                <input type="title" id="title" v-model="article.title" required>
            </div>
            <div class="input-group">
                <label for="text">Text:</label>
                <textarea type="text" id="text" v-model="article.text" required></textarea>
            </div>
            <div class="input-group">
                <select name="destination" id="destionation" v-model="article.destinationName" required>
                    <option v-for="(destination, index) in destinations" :key="index" :value="destination.name">
                    {{ destination.name }}
                    </option>
                </select>
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
            destinations: [],
            role: null,
            article: {
                articleId: 0,
                title: '',
                text: '',
                destinationName: '',
            },
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
            this.destinations = await (await fetch('http://localhost:8080/api/destinations/allDestinations', { headers: { Authorization: "Bearer " + jwt } })).json();
            this.article = this.$route.params.article;
        },
        handleSubmit() {
            let jwt = localStorage.getItem("jwtic");
            fetch('http://localhost:8080/api/articles/edit', {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer '+ jwt,
                },
                body: JSON.stringify({
                    articleId : this.article.articleId,
                    title: this.article.title,
                    text: this.article.text,
                    destinationName: this.article.destinationName,
                })
            }).then(response => {
                if (!response.ok) {
                    alert("Bad input!");
                    return;
                }
                this.$router.push({ name: 'articlesDev', params: { destination: this.article.destinationName } });
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