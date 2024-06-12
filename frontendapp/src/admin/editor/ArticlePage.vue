<template>
    <div class="article-page-container">
        <AdminNav v-if="role === 'ADMIN'" />
        <EditorNav v-else-if="role === 'EDITOR'" />
        <h1>{{ article.title }}</h1>
        <h3>By: {{ article.author.firstName }} {{ article.author.lastName }}</h3>
        <p>{{ article.text }}</p>

        <h4>Activities</h4>
        <ul>
            <li v-for="(activity, index) in activities" :key="index" @click="goTo(activity)">{{ activity }}</li>
        </ul>

        <h4>Comments</h4>
        <ul>
            <li v-for="(comment, index) in comments" :key="index">
                
                <strong>{{ comment.author }}:</strong> {{ comment.content }} {{ formatDate(comment.createTime) }}
            </li>
        </ul>

        <h4>Add a Comment</h4>
        <form @submit.prevent="addComment">
            <div class="input-group">
                <label for="comment-author">Author:</label>
                <input type="text" id="comment-author" v-model="newCommentAuthor" required>
            </div>
            <div class="input-group">
                <label for="comment-text">Comment:</label>
                <textarea id="comment-text" v-model="newCommentText" required></textarea>
            </div>
            <button type="submit">Submit</button>
        </form>
    </div>
</template>

<script>
import AdminNav from '../../navigations/AdminNav.vue'
import EditorNav from '../../navigations/EditorNav.vue'
import { parseJwt, jacksonDateToDate } from '@/util';
export default {
    data() {
        return {
            role: '',
            article: {
                articleId: 0,
                title: '',
                text: '',
                destinationName: '',
                author: {
                    firstName: '',
                    lastName: '',
                },
                createTime: null,
            },
            activities: [],
            comments: [],
            newCommentAuthor: '',
            newCommentText: '',
            createTime:''
        };
    },
    components: {
        AdminNav,
        EditorNav,
    },
    methods: {
        formatDate(date){
            return jacksonDateToDate(date).toLocaleString();
        },
        async fetchAll() {
            let jwt = localStorage.getItem("jwtic");
            this.role = parseJwt(jwt).type;
            this.article = this.$route.params.article;
            this.activities = await (await fetch('http://localhost:8080/api/articles/activities/' + this.article.articleId,
                { headers: { Authorization: "Bearer " + jwt } })).json();
            this.comments = await (await fetch('http://localhost:8080/api/articles/comments/' + this.article.articleId,
                { headers: { Authorization: "Bearer " + jwt } })).json();
            console.log(this.article);
        },
        goTo(acitivty){
            this.$router.push({name: 'articleActivity', params: {activity: acitivty}});
        },
        addComment() {
            console.log("pozzzzicicicic");
            let jwt = localStorage.getItem("jwtic");
            fetch('http://localhost:8080/api/articles/newComment/'+this.article.articleId, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer '+ jwt,
                },
                body: JSON.stringify({
                    content: this.newCommentText,
                    author: this.newCommentAuthor,
                    createTime: new Date().toJSON(),
                })
            }).then(response => {
                if (!response.ok) {
                    alert("Bad input!");
                    return;
                }
                this.fetchAll();
            })
        }
    },
    mounted() {
        this.fetchAll();
    }
};
</script>

<style scoped>
.article-page-container {
    display: flex;
    flex-direction: column;
    align-items: center;
    background-color: #ffe6f0;
    padding: 2rem;
    border-radius: 8px;
}

h1 {
    color: #ff69b4;
    margin-bottom: 1rem;
}

h3 {
    color: #ff69b4;
    margin-bottom: 1.5rem;
}

p {
    color: #333;
    margin-bottom: 1.5rem;
}

h4 {
    color: #ff69b4;
    margin-top: 2rem;
    margin-bottom: 1rem;
}

ul {
    list-style-type: none;
    padding: 0;
    margin-bottom: 1.5rem;
}

li {
    background: #fff;
    border: 1px solid #ff69b4;
    border-radius: 4px;
    padding: 0.75rem;
    margin-bottom: 0.5rem;
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
textarea {
    width: 100%;
    padding: 0.5rem;
    border: 1px solid #ff69b4;
    border-radius: 4px;
    margin-bottom: 0.5rem;
}

input:focus,
textarea:focus {
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