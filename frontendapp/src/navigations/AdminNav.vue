<template>
    <nav class="navbar">
      <router-link to="/destinations">Destinations</router-link>
      <router-link to="/articles">Articles</router-link>
      <router-link to="/users">Users</router-link>
      <a @click="logout">Log Out</a>
    </nav>
  </template>
  
  <script>
  export default {
    methods: {
      logout(){
        localStorage.removeItem("jwtic");
        this.$router.push('/');
      }
    }
  };
  </script>
  
  <style scoped>
  .navbar {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #ff69b4;
    padding: 1rem;
  }
  
  a {
    display: block;
    text-decoration: none;
    background-color: white;
    color: #ff69b4;
    padding: 0.75rem 1.5rem;
    border: 2px solid #ff69b4;
    border-radius: 4px;
    margin: 0 1rem;
    cursor: pointer;
    font-size: 1rem;
  }
  
  a:hover {
    background-color: #ff1493;
    color: white;
    border-color: #ff1493;
  }
  </style>