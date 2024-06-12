import Vue from 'vue'
import App from './App.vue'
import VueRouter from 'vue-router'
import AllDestinations from './admin/editor/AllDestinations.vue'
import AllArticles from './admin/editor/AllArticles.vue'
import AllUsers from './admin/editor/AllUsers.vue'
import LoginForm from './LoginForm'
import ArticlesOnDest from './admin/editor/ArticlesOnDest.vue'
import ArtOnDestUser from './user/ArtOnDestUser.vue'
import NewArticle from './admin/editor/NewArticle.vue'
import EditArticle from './admin/editor/EditArticle.vue'
import NewDestination from './admin/editor/NewDestination.vue'
import EditDestination from './admin/editor/EditDestination.vue'
import ArticlePage from './admin/editor/ArticlePage.vue'
import ArticleUser from './user/ArticleUser.vue'
import EditUser from './admin/editor/EditUser.vue'
import NewUser from './admin/editor/NewUser.vue'
import HomePage from './user/HomePage.vue'
import MostPopular from './user/MostPopular.vue'
import DestinationsUser from './user/DestinationsUser.vue'
import ArticleActivity from './admin/editor/ArticleActivity.vue'
import ActivityUser from './user/ActivityUser.vue'

Vue.use(VueRouter)

Vue.config.productionTip = false


const routes = [
  { path: '/destinations', component: AllDestinations },
  { path: '/destinationsUser', component: DestinationsUser },
  { path: '/articles', component: AllArticles },
  { path: '/users', component: AllUsers },
  { path: '/login', component: LoginForm },
  { path: '/articlesDev/:destination', component: ArticlesOnDest , name : 'articlesDev'},
  { path: '/articlesUser/:destination', component: ArtOnDestUser , name : 'articlesUser'},
  { path: '/editArticle/:article', component: EditArticle , name : 'editArticle'},
  { path: '/articleActivity/:article', component: ArticleActivity , name : 'articleActivity'},
  { path: '/articleUser/:article', component: ArticleUser , name : 'articleUser'},
  { path: '/editDestination/:destination', component: EditDestination , name : 'editDestination'},
  { path: '/editUser/:user', component: EditUser , name : 'editUser'},
  { path: '/articlePage/:article', component: ArticlePage , name : 'articlePage'},
  { path: '/activityUser/:article', component: ActivityUser , name : 'activityUser'},
  { path: '/newArticle', component: NewArticle},
  { path: '/newDestination', component: NewDestination},
  { path: '/newUser', component: NewUser},
  { path: '/', component: HomePage},
  { path: '/popular', component: MostPopular},
]

const router = new VueRouter({
  routes 
})


new Vue({
  router,
  render: h => h(App),
}).$mount('#app')
