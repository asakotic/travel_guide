<template>
    <div class="destination-table-container">
        <UserNav />
        <h2>Popular articles</h2>

        <table>
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Text preview</th>
                    <th>Date</th>
                    <th>Destination</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="(article, index) in articles" :key="index">
                    <td @click="thisArticle(article)">{{ article.title }}</td>
                    <td>{{ article.author.firstName + " " + article.author.lastName }}</td>
                    <td>{{ truncatedText(article.text) }}</td>
                    <td>{{ article.createTime }}</td>
                    <td>{{ article.destinationName }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>
  
<script>
import UserNav from '../navigations/UserNav.vue'
import { jacksonDateToDate } from '@/util';

export default {
    data() {
        return {
            articles: [],
        };
    },
    components: {
        UserNav,
    },
    methods: {
        async fetchAllArticles() {
            this.articles = await (await fetch('http://localhost:8080/api/articles/mostPopular')).json();
            console.log(this.articles);

            for (let article of this.articles)
                article.createTime = jacksonDateToDate(article.createTime).toLocaleString();
        },
        thisArticle(article) {
            fetch(`http://localhost:8080/api/articles/increment/${article.articleId}`);
            this.$router.push({ name: 'articleUser', params: { article: article } });
        },
        truncatedText(fullText) {
            return fullText.length > 7 ? fullText.substring(0, 7) + '...' : fullText;
        }
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