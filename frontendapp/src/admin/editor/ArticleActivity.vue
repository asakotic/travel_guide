<template>
    <div class="destination-table-container">
        <AdminNav v-if="role === 'ADMIN'" />
        <EditorNav v-else />
        <h2>Articles for {{ this.$route.params.activity }}</h2>
        <button @click="addArticle" class="add-button">Add New Article</button>

        <table>
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Date</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(article, index) in articles" :key="index">
                    <td @click="thisArticle(article)">{{ article.title }}</td>
                    <td>{{ article.author.firstName + " " + article.author.lastName }}</td>
                    <td>{{ article.createTime }}</td>
                    <td>
                        <button @click="editArticle(article)">Edit</button>
                        <button @click="deleteArticle(article)">Delete</button>
                    </td>
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
import AdminNav from '../../navigations/AdminNav.vue'
import EditorNav from '../../navigations/EditorNav.vue'
import { parseJwt, jacksonDateToDate } from '@/util';

export default {
    data() {
        return {
            currPage: 1,
            articles: [],
            role: null,

            form: {
                name: '',
                description: ''
            },
        };
    },
    components: {
        AdminNav,
        EditorNav,
    },
    methods: {
        async fetchAllArticles() {
            let jwt = localStorage.getItem("jwtic");
            this.role = parseJwt(jwt).type;
            this.articles = await (await fetch('http://localhost:8080/api/articles/articlesUser/1/' +
                this.$route.params.activity, { headers: { Authorization: "Bearer " + jwt } })).json();

            for (let article of this.articles)
                article.createTime = jacksonDateToDate(article.createTime).toLocaleString();
        },
        async changePage(num) {
            let jwt = localStorage.getItem("jwtic");
            this.currPage += num;
            if (this.currPage < 1) this.currPage = 1;
            let tmp = await (await fetch(`http://localhost:8080/api/articles/articlesUser/${this.currPage}/` +
                this.$route.params.acitivty, { headers: { Authorization: "Bearer " + jwt } })).json();
            if (tmp.length > 0) {
                for (let article of tmp) {
                    article.createTime = jacksonDateToDate(article.createTime).toLocaleString();
                }
                this.articles = tmp;
            }
            else
                this.currPage -= 1
            console.log(this.currPage)


        },
        thisArticle(article) {
            let jwt = localStorage.getItem("jwtic");
            fetch(`http://localhost:8080/api/articles/increment/${article.articleId}`, { headers: { Authorization: "Bearer " + jwt } });
            this.$router.push({ name: 'articlePage', params: { article: article } });
        },
        addArticle() {
            this.$router.push('/newArticle');
        },
        editArticle(article) {
            this.$router.push({ name: 'editArticle', params: { article: article } });
        },
        async deleteArticle(article) {
            let jwt = localStorage.getItem("jwtic");
            this.currPage = 1;
            fetch('http://localhost:8080/api/articles/delete/' + article.articleId, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': "Bearer " + jwt,
                },
            })
            this.fetchAllArticles();
        },
    },
    mounted() {
        this.fetchAllArticles();
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